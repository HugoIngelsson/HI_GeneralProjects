using ChessChallenge.API;
using System;
using System.Collections.Generic;

public class MiniBotV6 : IChessBot
{
   static double eval, phase, operations, opsLimit, keepZob;
   static ulong[] baseVals = {108, 580, 772, 1245, 2533, 0, 206, 854, 915, 1380, 2682, 0};
   static Dictionary<ulong, Move> alreadyExplored;
   static Move[] killerMoves;

   // saves on some tokens
   static Move nil = Move.NullMove;

   public Move Think(Board board, Timer timer)
   {
       operations = 0;
       opsLimit = Math.Max(80000, timer.MillisecondsRemaining*16.66667);

       alreadyExplored = new Dictionary<ulong, Move>();
       killerMoves = new Move[50];
       for (int i=0; i<50; i++) killerMoves[i] = nil;
      
       Move bestKeep = nil;
       EvaluatePosition(board);
       keepZob = board.ZobristKey;

       for (int depth=1; depth<100; depth++) {
           negamax(board, 0, depth, -999999999999, 999999999999, board.IsWhiteToMove ? 1 : -1, false);

           Console.WriteLine(depth + ": " + operations + " " + opsLimit + " " + phase);
           if (operations <= opsLimit) {
                bestKeep = alreadyExplored[board.ZobristKey];
                if (operations > opsLimit * 0.2) return bestKeep;
           }
           else break;
       }

       return bestKeep;
   }

   public void EvaluatePosition(Board board) {
       eval = 0.0;
       double temp = 0.0;
       foreach (PieceList pieceList in board.GetAllPieceLists()) {
           foreach (Piece p in pieceList) {
               eval += EvaluatePiece(p);
               if (!p.IsPawn) temp += baseVals[(int)p.PieceType - 1];
           }
       }
       phase = (Math.Max(3915, Math.Min(15258, temp)) - 3915) * 0.01128449263;
   }

   public Move[] Concat(Move[] a1, Move[] a2, Move best, Move killer) {
       Move[] a3 = new Move[a1.Length + a2.Length + 2];

       a3[0] = best;
       a3[1] = killer;
       Array.Copy(a1, 0, a3, 2, a1.Length);
       Array.Copy(a2, 0, a3, a1.Length+2, a2.Length);

       return a3;
   }

   public double negamax(Board board, int depth, int depthLimit, double alpha, double beta, int white, bool quiesce) {
       operations++;
       if (board.IsDraw()) return 0;
       if (board.IsInCheckmate()) return depth - 999999999;

       if (board.IsInCheck() && depthLimit < 15) depthLimit++;

       if (quiesce) {
           double quiEval = eval * white + 28;

           if (quiEval >= beta) return beta;
           if (quiEval < alpha - 700) return alpha;

           if (depth == depthLimit) return quiEval;
           if (alpha < quiEval) alpha = quiEval;
       }
       else if (depth == depthLimit) return negamax(board, depth, depthLimit+2, alpha, beta, white, true);

       Move[] legalMoves = board.GetLegalMoves(quiesce && !board.IsInCheck());
       Move best = alreadyExplored.ContainsKey(board.ZobristKey) ? alreadyExplored[board.ZobristKey] : nil;

       foreach (Move m in Concat(board.GetLegalMoves(true), legalMoves, best, killerMoves[depth])) {
           if (operations > opsLimit) return alpha;

           if (Array.IndexOf(legalMoves, m) >= 0 && !m.IsNull) {
               // avoid exploring the same move twice
               legalMoves[Array.IndexOf(legalMoves, m)] = nil;
               
               double delta = EvaluatePiece(board.GetPiece(m.StartSquare));
               if (m.IsCapture) {
                   int id = m.TargetSquare.Index;
                   if (m.IsEnPassant) id += white * -8;
                  
                   delta += EvaluatePiece(board.GetPiece(new Square(id)));
               }
               board.MakeMove(m);
               delta -= EvaluatePiece(board.GetPiece(m.TargetSquare));
               eval -= delta;

               double score = -negamax(board, depth+1, depthLimit, -beta, -alpha, -white, quiesce);
              
               board.UndoMove(m);
               eval += delta;

               if (score >= beta) {
                   killerMoves[depth] = m;
                   
                   return beta;
               }
               if (score > alpha) {
                   alpha = score;
                   if (depth == 0 || board.ZobristKey != keepZob) alreadyExplored[board.ZobristKey] = m;
               }
           }           
       }

       return alpha;
   }

   public double EvaluatePiece(Piece piece) {
       double[] scales = {3.03, 17.27, 6.13, 3.27, 1.27, 2.87, 3.07, 9.27, 4.93, 2.20, 6.60, 13.20};
       ulong[,] packedTables = {
           {0x0358305008132370,0x02339AA00477FDB0}, // pawns middlegame
           {0x08BAA8727ADCCB92,0x8CFEECA7AEFFECB7}, // knights middlegame
           {0x1667886096ADACA8,0x699CD8C759AEFB95}, // bishops middlegame
           {0x493162304D958663,0x9EB88975CFDA8AB8}, // rooks middlegame
           {0x2014722629CF8980,0x5C9DBEA02AA8A9D7}, // queen middlegame
           {0x346789DC46789CEF,0x234568BC02135689}, // king middlegame
           {0x06B8420001B64130,0x089644700BF86750}, // pawns endgame
           {0x035676401569B854,0x559CCA969CDFEEC9}, // knights endgame
           {0x2558884038DBAB95,0x4BC9CB847CDFFEC9}, // bishops endgame
           {0xE8943902686A6220,0xFF392551C4A39352}, // rooks endgame
           {0x04678531379AB973,0x58ADDA846ACFFCB7}, // queen endgame
           {0x1377874049DDCA83,0x59EFDDA66AEFDDA6}  // king endgame
       };

       int row = piece.Square.Rank;
       if (!piece.IsWhite) row = 7 - row;

       int col = piece.Square.File;
       if (col > 3) col = 7 - col;

       int id = (int)piece.PieceType - 1;

       // a bit of saving on tokens
       int rep1 = row*4+col%2*32, rep2 = piece.IsWhite ? 1 : -1;
       double mg = (baseVals[id] + (packedTables[id, col/2] >> rep1) % 16 * scales[id]) * rep2;
       id += 6;
       double eg = (baseVals[id] + (packedTables[id, col/2] >> rep1) % 16 * scales[id]) * rep2;
       return ((mg * phase) + (eg * (128.0 - phase))) / 128.0;
   }
}