import java.util.*;
import java.io.*;

public class App {
    static SearchTreeMember baseNode;
    static DictionaryNode baseDictionaryNode;
    static ArrayList<Identity> identities;
    static int[][][] boardBonuses;
    static char[][] board;
    static boolean[][][][] parallelDescriptions;
    static boolean[][] isBlank;
    static int[] charValues = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10,0};
    static int[] staticLetterBag = {9,2,2,4,12,2,3,2,9,1,1,4,2,6,8,2,1,6,4,6,4,2,2,1,2,1,2};
    static int bagSize = 100;
    static int totalScore;

    static boolean turn;
    static int[] scores;
    static String[] letterRacks;
    static int cleanOutBonus;
    static int test;

    public static void main(String[] args) throws IOException {
        importIdentities("data/identityData.txt");
        getBoardBonuses("data/scrabble board.txt");
        createDictionary("data/Collins Scrabble Words (2019).txt");
        baseNode = developTree(null, 0, 247490, 0);

        PrintWriter pw = new PrintWriter(new File("data/significance test.txt"));
        PrintWriter pw2 = new PrintWriter(new File("data/significance test 2.txt"));
        for (int test=0; test<100; test++) {
            resetBoard("data/input board.txt");
            int counter = 0;
            while (true) {
                getBoardBonuses("data/scrabble board.txt");
                readBoard("data/input board.txt");

                if (bagSize == letterRacks[0].length() || bagSize == letterRacks[1].length() || counter == 4) {
                    break;
                }

                String letterRack;
                cleanOutBonus = 0;
                if (turn) {
                    removeFromBag(letterRacks[0]);
                    removeFromBag(letterRacks[1]);
                    letterRack = pickTiles(letterRacks[1]);

                    clearTiles(letterRacks[0], 0);
                    if (bagSize == letterRacks[0].length()) setCleanOutBonus();
                }
                else {
                    removeFromBag(letterRacks[0]);
                    removeFromBag(letterRacks[1]);
                    letterRack = pickTiles(letterRacks[0]);

                    clearTiles(letterRacks[1], 0);
                    if (bagSize == letterRacks[1].length()) setCleanOutBonus();
                }
                
                System.out.println("LETTER RACK " + letterRack);

                double bestValue = Integer.MIN_VALUE;
                Placement bestPlacement = null;
                for (Placement p : getWordOptions(letterRack)) {
                    if (p.word != null) {
                        String lettersUsed = "";
                        for (int i=0; i<p.word.length(); i++) {
                            if (p.vector.down) {
                                if (board[p.vector.y+i][p.vector.x] == '\u0000') {
                                    lettersUsed = lettersUsed + p.word.charAt(i);

                                    int id=(int)p.word.charAt(i)-65;
                                    if (id > 26) {
                                        id -= 32;
                                        board[p.vector.y+i][p.vector.x] = (char)(id+65);
                                    }
                                    else board[p.vector.y+i][p.vector.x] = p.word.charAt(i);
                                }
                                else lettersUsed = lettersUsed + " ";
                            }
                            else {
                                if (board[p.vector.y][p.vector.x+i] == '\u0000') {
                                    lettersUsed = lettersUsed + p.word.charAt(i);
                                    
                                    int id=(int)p.word.charAt(i)-65;
                                    if (id > 26) {
                                        id -= 32;
                                        board[p.vector.y][p.vector.x+i] = (char)(id+65);
                                    }
                                    else board[p.vector.y][p.vector.x+i] = p.word.charAt(i);
                                }
                                else lettersUsed = lettersUsed + " ";
                            }
                        }
                        String lettersExcluding = letterRackExcluding(letterRack, lettersUsed.toUpperCase());
                        if (lettersExcluding.length() == 0 && bagSize < 7) p.value += cleanOutBonus;
                        
                        if (p.value + 0.5*scoreRack(lettersExcluding)*test > bestValue) {
                            bestValue = p.value + 0.5*scoreRack(lettersExcluding)*test;
                            bestPlacement = p;

                            if (turn) letterRacks[1] = lettersExcluding;
                            else letterRacks[0] = lettersExcluding;
                        }

                        for (int i=0; i<p.word.length(); i++) {
                            int id=(int)p.word.charAt(i)-65;
                            if (id > 26) id -= 32;

                            if (p.vector.down) {
                                if (lettersUsed.charAt(i) != ' ') {
                                    board[p.vector.y+i][p.vector.x] = '\u0000';
                                }
                            }
                            else {
                                if (lettersUsed.charAt(i) != ' ') {
                                    board[p.vector.y][p.vector.x+i] = '\u0000';
                                }
                            }
                        }
                    }
                }

                if (bestPlacement == null || bestPlacement.word == null) {
                    counter++;
                    if (turn) System.out.print("Player 2 has no legal moves");
                    else System.out.print("Player 1 has no legal moves");
                }
                else {
                    counter = 0;

                    if (turn) System.out.print("Player 2 plays ");
                    else System.out.print("Player 1 plays ");
                    System.out.println(bestPlacement.word + " " + bestPlacement.vector + ", worth " + bestPlacement.value);
                    for (int i=0; i<bestPlacement.word.length(); i++) {
                        if (bestPlacement.vector.down) {
                            if (board[bestPlacement.vector.y+i][bestPlacement.vector.x] == '\u0000') {
                                board[bestPlacement.vector.y+i][bestPlacement.vector.x] = bestPlacement.word.charAt(i);
                            }
                        }
                        else {
                            if (board[bestPlacement.vector.y][bestPlacement.vector.x+i] == '\u0000') {
                                board[bestPlacement.vector.y][bestPlacement.vector.x+i] = bestPlacement.word.charAt(i);
                            }
                        }
                    }

                    if (turn) scores[1] += bestPlacement.value;
                    else scores[0] += bestPlacement.value;

                    if (turn) {
                        removeFromBag(letterRacks[0]);
                        letterRacks[1] = pickTiles(letterRacks[1]);
                        clearTiles(letterRacks[0], 0);
                    }
                    else {
                        removeFromBag(letterRacks[1]);
                        letterRacks[0] = pickTiles(letterRacks[0]);
                        clearTiles(letterRacks[1], 0);
                    }
                }

                outputBoard("data/input board.txt");
                
                // long time = System.nanoTime();
                // while (System.nanoTime() - time < 500000000);
            }

            pw.println(scores[0]);
            pw2.println(scores[1]);
            pw.flush();
            pw2.flush();
        }

        pw.close();
        pw2.close();
    }

    public static String letterRackExcluding(String letterRack, String lettersUsed) {
        int[] charCounts = getAllowances(letterRack);
        String ret = "";

        for (int i=0; i<lettersUsed.length(); i++) {
            if (lettersUsed.charAt(i) != ' ') {
                if (charCounts[(int)lettersUsed.charAt(i)-65] == 0) charCounts[26]--;
                else charCounts[(int)lettersUsed.charAt(i)-65]--;
            }
        }

        for (int i=0; i<27; i++) {
            ret = ret + new String(new char[charCounts[i]]).replace("\0", "" + (char)(i+65));
        }

        return ret;
    }

    public static int scoreRack(String rack) {
        int leftToPick = 0;
        int leftTotalScore = 0;
        int ret = 0;
        int count = 0;

        for (int i=0; i<27; i++) {
            leftTotalScore += letterBag[i]*charValues[i];
            leftToPick += letterBag[i];
        }

        while (rack.length() > 0) {
            ret += charValues[(int)rack.charAt(0)-65];
            rack = rack.substring(1);
            count++;
        }

        return ret - count * leftTotalScore / leftToPick;
    }

    // returns a summary of possible good plays, as follows
    // -- the highest scoring 2 letter play
    // -- the highest scoring 3-4 letter play
    // -- the two highest scoring 5-6 letter play
    // -- the two highest scoring 7+ letter plays
    // -- the highest scoring play with 1 wildcard (if applicable)
    // -- the highest scoring play with 2 wildcards (if applicable)
    public static Placement[] getWordOptions(String letterRack) {
        Placement[] ret = new Placement[8];
        for (int i=0; i<8; i++) ret[i] = new Placement(null, null, 0);

        boolean[] trueFalse = {true, false};
        
        for (int wildcards=0; wildcards<=getAllowances(letterRack)[26]; wildcards++) {
            for (boolean b : trueFalse) {
                for (int i=0; i<15; i++) {
                    for (MaskPackage mp : getFusingPlacements(i, b)) {
                        ArrayList<String> availableWords = new ArrayList<String>();
                        int[] allowances = getAllowances(mp.lettersAvailable + letterRack);
                        for (Identity id : traceTree(allowances, 0, baseNode, wildcards)) {
                            if (wildcards > 0) for (String s : id.getWords()) availableWords.addAll(wildcardify(s, wildcards, allowances, getAllowances(s), 0));
                            else availableWords.addAll(id.getWords());
                        }

                        for (String s : availableWords) {
                            for (Vector v : getLegalPlacements(mp.frame, s, mp.vector.x, mp.vector.y, mp.vector.down, mp.minDir, mp.maxDir)) {
                                int val = calculatePlayValue(s, v);
                                if (val > 0 && s.length() == letterRack.length()) val += cleanOutBonus;

                                handleRanking(ret, v, val, s, wildcards);
                            }
                        }
                    }

                    ArrayList<String> availableWords = new ArrayList<String>();
                    int[] allowances = getAllowances(letterRack);
                    for (Identity id : traceTree(allowances, 0, baseNode, wildcards)) {
                        if (wildcards > 0) for (String s : id.getWords()) availableWords.addAll(wildcardify(s, wildcards, allowances, getAllowances(s), 0));
                        else availableWords.addAll(id.getWords());
                    }

                    for (String s : availableWords) {
                        for (int j=0; j<=15-s.length(); j++) {
                            if (b) {
                                if (parallelDescriptions[1][j][i][s.length()]) {
                                    int val = calculatePlayValue(s, new Vector(i,j,true));
                                    if (val > 0 && s.length() == letterRack.length()) val += cleanOutBonus;

                                    handleRanking(ret, new Vector(i,j,true), val, s, wildcards);
                                }
                            }
                            else if (parallelDescriptions[0][i][j][s.length()]) {
                                int val = calculatePlayValue(s, new Vector(j,i,false));
                                if (s.length() == letterRack.length()) val += cleanOutBonus;

                                handleRanking(ret, new Vector(j,i,false), val, s, wildcards);
                            }
                        }
                    }
                }
            }
        }

        return ret;
    }

    public static void handleRanking(Placement[] ret, Vector v, int val, String s, int wc) {
        if (wc == 1) {
            if (ret[6].value < val) ret[6] = new Placement(v, s, val);
        }
        else if (wc == 2) {
            if (ret[7].value < val) ret[7] = new Placement(v, s, val);
        }
        
        
        if (s.length() == 2) {
            if (ret[0].value < val) ret[0] = new Placement(v, s, val);
        }
        else if (s.length() <= 4) {
            if (ret[1].value < val) ret[1] = new Placement(v, s, val);
        }
        else if (s.length() <= 6) {
            if (ret[2].value < val) {
                ret[3] = ret[2];
                ret[2] = new Placement(v, s, val);
            }
            else if (ret[3].value < val) {
                ret[3] = new Placement(v, s, val);
            }
        }
        else {
            if (ret[4].value < val) {
                ret[5] = ret[4];
                ret[4] = new Placement(v, s, val);
            }
            else if (ret[5].value < val) {
                ret[5] = new Placement(v, s, val);
            }
        }
    }

    public static String bestWord(String letterRack) {
        boolean[] trueFalse = {true, false};

        String bestPlacement = "";
        int bestValue = 0;
        for (int wildcards=0; wildcards<=getAllowances(letterRack)[26]; wildcards++) {
            for (boolean b : trueFalse) {
                for (int i=0; i<15; i++) {
                    for (MaskPackage mp : getFusingPlacements(i, b)) {
                        ArrayList<String> availableWords = new ArrayList<String>();
                        int[] allowances = getAllowances(mp.lettersAvailable + letterRack);
                        for (Identity id : traceTree(allowances, 0, baseNode, wildcards)) {
                            if (wildcards > 0) for (String s : id.getWords()) availableWords.addAll(wildcardify(s, wildcards, allowances, getAllowances(s), 0));
                            else availableWords.addAll(id.getWords());
                        }

                        for (String s : availableWords) {
                            for (Vector v : getLegalPlacements(mp.frame, s, mp.vector.x, mp.vector.y, mp.vector.down, mp.minDir, mp.maxDir)) {
                                int val = calculatePlayValue(s, v);

                                if (val > bestValue) {
                                    bestPlacement = s + " at (" + v.x + "," + v.y + "): " + val;
                                    bestValue = val;
                                }
                            }
                        }
                    }

                    ArrayList<String> availableWords = new ArrayList<String>();
                    int[] allowances = getAllowances(letterRack);
                    for (Identity id : traceTree(allowances, 0, baseNode, allowances[26])) {
                        if (wildcards > 0) for (String s : id.getWords()) availableWords.addAll(wildcardify(s, wildcards, allowances, getAllowances(s), 0));
                        else availableWords.addAll(id.getWords());
                    }

                    for (String s : availableWords) {
                        for (int j=0; j<=15-s.length(); j++) {
                            if (b) {
                                if (parallelDescriptions[1][j][i][s.length()]) {
                                    int val = calculatePlayValue(s, new Vector(i,j,true));
                                    if (val > bestValue) {
                                        bestPlacement = s + " at (" + i + "," + j + "): " + val;
                                        bestValue = val;
                                    }
                                }
                            }
                            else if (parallelDescriptions[0][i][j][s.length()]) {
                                int val = calculatePlayValue(s, new Vector(j,i,false));
                                if (val > bestValue) {
                                    bestPlacement = s + " at (" + j + "," + i + "): " + val;
                                    bestValue = val;
                                }
                            }
                        }
                    }
                }
            }
        }

        totalScore += bestValue;
        return bestPlacement;
    }

    // '[' == wildcard
    public static int[] getAllowances(String lettersAvailable) {
        int[] ret = new int[27];

        for (int i=0; i<lettersAvailable.length(); i++) {
            ret[(char)lettersAvailable.charAt(i)-65]++;
        }

        return ret;
    }

    public static ArrayList<String> wildcardify(String s, int wildcards, int[] allowances, int[] sumCounts, int location) {
        ArrayList<String> ret = new ArrayList<String>();

        if (wildcards < 0) return ret;
        if (location == s.length()) {
            if (wildcards == 0) ret.add(s);

            return ret;
        }

        int id = (int)s.charAt(location)-65;

        if (sumCounts[id] > allowances[id]) {
            sumCounts[id]--;
            ret.addAll(wildcardify(s.substring(0,location) + (char)(id+97) + s.substring(location+1), wildcards-1, allowances, sumCounts, location+1));
            sumCounts[id]++;
        }
        ret.addAll(wildcardify(s, wildcards, allowances, sumCounts, location+1));

        return ret;
    }

    public static int calculatePlayValue(String word, Vector vector) {
        int ret = 0;
        int wordMultiplier = 1;
        int addValue;

        for (int i=0; i<word.length(); i++) {
            int id = (int)word.charAt(i)-65;
            if (id > 25) addValue = 0;
            else addValue = charValues[id];

            if (vector.down) {
                String crossword = crossWord(vector.x, vector.y+i, vector.down, word.charAt(i));
                if (board[vector.y+i][vector.x] == '\u0000' && crossword.length() > 1) { 
                    if (!isWord(crossword)) return -1;
                    ret += pureWordValue(crossword)*boardBonuses[0][vector.y+i][vector.x];
                    ret += addValue*(boardBonuses[1][vector.y+i][vector.x]-1);
                }

                ret += addValue*boardBonuses[1][vector.y+i][vector.x];
                wordMultiplier *= boardBonuses[0][vector.y+i][vector.x];
            }
            else {
                String crossword = crossWord(vector.x+i, vector.y, vector.down, word.charAt(i));
                if (board[vector.y][vector.x+i] == '\u0000' && crossword.length() > 1) { 
                    if (!isWord(crossword)) return -1;
                    ret += pureWordValue(crossword)*boardBonuses[0][vector.y][vector.x+i];
                    ret += addValue*(boardBonuses[1][vector.y][vector.x+i]-1);
                }

                ret += addValue*boardBonuses[1][vector.y][vector.x+i];
                wordMultiplier *= boardBonuses[0][vector.y][vector.x+i];
            }
        }

        return ret * wordMultiplier;
    }

    public static int pureWordValue(String word) {
        int ret = 0;

        for (int i=0; i<word.length(); i++) {
            if ((int)word.charAt(i) < 97) ret += charValues[(int)word.charAt(i)-65];
        }

        return ret;
    }

    public static String pickTiles(String s) {
        String ret = s;

        while (ret.length() < 7 && bagSize > 0) {
            int rand = (int)(Math.random() * bagSize);
            int id = 0;

            while (rand >= 0) rand -= letterBag[id++];

            ret = ret + (char)(id+64);
            letterBag[id-1]--;
            bagSize--;
        }

        return ret;
    }

    public static void removeFromBag(String s) {
        for (int i=0; i<s.length(); i++) letterBag[(int)s.charAt(i)-65]--;
        bagSize -= s.length();
    }

    public static void clearTiles(String s, int start) {
        while (s.length() > start) {
            letterBag[(int)s.charAt(start)-65]++;
            bagSize++;
            s = s.substring(1);
        }
    }

    public static String crossWord(int x, int y, boolean down, char addOn) {
        String crossword = "" + addOn;

        if (down) {
            for (int i=x-1; i>=0 && board[y][i] != '\u0000'; i--) crossword = board[y][i] + crossword;
            for (int i=x+1; i<15 && board[y][i] != '\u0000'; i++) crossword = crossword + board[y][i];
        }
        else {
            for (int i=y-1; i>=0 && board[i][x] != '\u0000'; i--) crossword = board[i][x] + crossword;
            for (int i=y+1; i<15 && board[i][x] != '\u0000'; i++) crossword = crossword + board[i][x];
        }

        return crossword;
    }

    public static boolean isWord(String s) {
        if (s.length() < 2) return true;

        return searchDictionary(baseDictionaryNode, s);
    }

    public static boolean searchDictionary(DictionaryNode parent, String cur) {
        if (cur.length() == 0) return parent.getIdentity() != null;

        if (parent.getChildren() == null) return false;

        int id = (int)cur.charAt(0)-65;
        if (id > 26) id -= 32;
        if (parent.getChildren()[id] == null) return false;

        return searchDictionary(parent.getChildren()[id], cur.substring(1));
    }

    public static ArrayList<MaskPackage> getFusingPlacements(int id, boolean down) {
        ArrayList<MaskPackage> ret = new ArrayList<MaskPackage>();
        ArrayList<String> segments = new ArrayList<String>();
        ArrayList<Integer> startPoints = new ArrayList<Integer>(), endPoints = new ArrayList<Integer>();

        endPoints.add(-2);
        String searchSegment = "";
        int searchState = 0;        
        for (int i=0; i<16; i++) {
            char comp = (i==15 ? '\u0000' : (down ? board[i][id] : board[id][i]));
            if (comp != '\u0000') {
                if (searchState == 0) startPoints.add(i);
                searchSegment = searchSegment + comp;

                searchState = 1;
            }
            else {
                if (searchState == 1) {
                    segments.add(searchSegment);
                    endPoints.add(i-1);
                }

                searchState = 0;
                searchSegment = "";
            }
        }
        startPoints.add(16);

        for (int i=0; i<segments.size(); i++) {
            String frame = "";
            String lettersAvailable = "";
            Vector v = new Vector(down ? id : startPoints.get(i), down ? startPoints.get(i) : id, down);

            for (int j=i; j<segments.size(); j++) {
                frame = frame + segments.get(j);
                lettersAvailable = lettersAvailable + segments.get(j);

                ret.add(new MaskPackage(v, frame, lettersAvailable, endPoints.get(i)+2, startPoints.get(j+1)-2));

                frame = frame + new String(new char[startPoints.get(j+1)-endPoints.get(j+1)-1]).replace("\0", " ");
            }
        }

        return ret;
    }

    //x,y,down describe the placement of our frame
    public static ArrayList<Vector> getLegalPlacements(String frame, String word, int x, int y, boolean down, int minDir, int maxDir) {
        ArrayList<Vector> ret = new ArrayList<Vector>();
        if (frame.equals(word)) return ret;

        for (int i=0; i<=word.length() - frame.length(); i++) {
            int lim = down ? y : x;

            if (lim >= minDir && lim+word.length()-1 <= maxDir) {
                    for (int j=0; j<frame.length(); j++) {
                        if (frame.charAt(j) != ' ' && frame.charAt(j) != word.charAt(i+j)) break;
                        if (j==frame.length()-1) ret.add(new Vector(x,y,down));
                    }
            }

            if (down) y--;
            else x--;
        }


        return ret;
    }


    public static ArrayList<Identity> traceTree(int[] allowances, int alpha, SearchTreeMember current, int wildcards) {
        ArrayList<Identity> ret = new ArrayList<Identity>();
        if (wildcards < 0) return ret;

        if (current.getChildren() == null) {
            if (alpha == 26) {
                if (wildcards == 0) ret.add(current.getIdentityObject());
            }
            else if ((char)current.getIdentity().charAt(alpha)-48 <= allowances[alpha]) {
                ret = traceTree(allowances, alpha+1, current, wildcards);
            }
            else ret = traceTree(allowances, alpha+1, current, wildcards-(char)current.getIdentity().charAt(alpha)+48+allowances[alpha]);

            return ret;
        }

        for (int i=0; i<=allowances[alpha]+wildcards && i<8; i++) {
            if (current.getChildren()[i] != null)
                ret.addAll(traceTree(allowances, alpha+1, current.getChildren()[i], wildcards-Math.max(0, i-allowances[alpha])));
        }

        return ret;
    }

    public static void importIdentities(String fileName) throws IOException {
        identities = new ArrayList<Identity>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String in;

        while ((in = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(in);
            Identity toAdd = new Identity(st.nextToken());
            identities.add(toAdd);

            while (st.hasMoreTokens()) toAdd.addWord(st.nextToken());
        }

        br.close();
    }

    public static SearchTreeMember developTree(Node parent, int start, int end, int alpha) {
        if (start == end) return new Leaf(parent, identities.get(start));

        Node splitNode = new Node();
        splitNode.setParent(parent);
        SearchTreeMember[] children = new SearchTreeMember[8];
        int maxID = (char)identities.get(end).getIdentity().charAt(alpha)-48;

        for (int i=0; i<maxID; i++) {
            if ((char)identities.get(start).getIdentity().charAt(alpha)-48 != i) continue;

            int tempStart = start, tempMid, tempEnd = end;
            while (tempStart != tempEnd-1) {
                tempMid = (tempStart+tempEnd) / 2;
                if ((char)identities.get(tempMid).getIdentity().charAt(alpha)-48 == i) {
                    tempStart = tempMid;
                }
                else {
                    tempEnd = tempMid;
                }
            }

            children[i] = developTree(splitNode, start, tempStart, alpha+1);
            start = tempEnd;
        }
        children[maxID] = developTree(splitNode, start, end, alpha+1);

        splitNode.setChildren(children);
        return splitNode;
    }

    public static void getBoardBonuses(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        boardBonuses = new int[2][15][15];

        for (int r=0; r<15; r++) {
            String in = br.readLine();
            for (int c=0; c<15; c++) {
                if (in.charAt(c) == '$') boardBonuses[0][r][c] = 3;
                else if (in.charAt(c) == '#') boardBonuses[0][r][c] = 2;
                else boardBonuses[0][r][c] = 1;


                if (in.charAt(c) == '3') boardBonuses[1][r][c] = 3;
                else if (in.charAt(c) == '2') boardBonuses[1][r][c] = 2;
                else boardBonuses[1][r][c] = 1;
            }
        }

        br.close();
    }

    public static void setCleanOutBonus() {
        cleanOutBonus = 0;

        for (int i=0; i<26; i++) cleanOutBonus += charValues[i]*letterBag[i]*2;
    }

    static int[] letterBag;
    public static void readBoard(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        board = new char[15][15];
        isBlank = new boolean[15][15];
        scores = new int[2];
        letterRacks = new String[2];
        bagSize = 100;
        letterBag = staticLetterBag.clone();

        // boolean[down][row][col][length]
        parallelDescriptions = new boolean[2][15][15][16];

        for (int r=0; r<15; r++) {
            String in = br.readLine();
            for (int c=0; c<15; c++) {
                if (in.charAt(c) != '.') {
                    if ((int)in.charAt(c) >= 97) {
                        board[r][c] = (char)((int)in.charAt(c)-32);
                        isBlank[r][c] = true;
                        letterBag[26]--;
                    }
                    else {
                        board[r][c] = in.charAt(c);
                        letterBag[(int)in.charAt(c)-65]--;
                    }
                    bagSize--;

                    boardBonuses[0][r][c] = 1;
                    boardBonuses[1][r][c] = 1;
                }
            }
        }
        if (br.readLine().equals("0")) turn = false;
        else turn = true;

        test = 0;
        if (turn) test = 1;

        scores[0] = Integer.parseInt(br.readLine());
        scores[1] = Integer.parseInt(br.readLine());

        letterRacks[0] = br.readLine();
        if (letterRacks[0] == null) letterRacks[0] = "";

        letterRacks[1] = br.readLine();
        if (letterRacks[1] == null) letterRacks[1] = "";

        for (int r=0; r<15; r++) {
            for (int c=0; c<15; c++) {
                boolean possibleParallelAcross = false;
                boolean possibleParallelDown = false;

                if ((c==0 || board[r][c-1] == '\u0000') && board[r][c] == '\u0000') {
                    for (int i=0; c+i<15; i++) {
                        if (c+i+1<15 && board[r][c+i+1] != '\u0000') break;
                        if (r>0 && board[r-1][c+i] != '\u0000') possibleParallelAcross = true;
                        if (r<14 && board[r+1][c+i] != '\u0000') possibleParallelAcross = true;

                        parallelDescriptions[0][r][c][i+1] = possibleParallelAcross;
                    }
                }

                if ((r==0 || board[r-1][c] == '\u0000') && board[r][c] == '\u0000') {
                    for (int i=0; r+i<15; i++) {
                        if (r+i+1<15 && board[r+i+1][c] != '\u0000') break;
                        if (c>0 && board[r+i][c-1] != '\u0000') possibleParallelDown = true;
                        if (c<14 && board[r+i][c+1] != '\u0000') possibleParallelDown = true;

                        parallelDescriptions[1][r][c][i+1] = possibleParallelDown;
                    }
                }
            }
        }

        if (board[7][7] == '\u0000') for (int i=0; i<7; i++) for (int j=i; j<7; j++) parallelDescriptions[0][7][7-i][j+1] = true;

        br.close();
    }

    public static void createDictionary(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        baseDictionaryNode = new DictionaryNode(null);
        String in;

        while ((in = br.readLine()) != null) {
            DictionaryNode current = baseDictionaryNode;
            while (in.length() > 0) {
                if (current.getChildren() == null) current.setChildren(new DictionaryNode[26]);
                if (current.getChildren()[(int)in.charAt(0)-65] == null) 
                    current.getChildren()[(int)in.charAt(0)-65] = new DictionaryNode(current);

                current = current.getChildren()[(int)in.charAt(0)-65];
                in = in.substring(1);
            }
            current.setIdentity("");
        }

        br.close();
    }

    public static void outputBoard(String fileName) throws IOException {
        PrintWriter pw = new PrintWriter(new File(fileName));

        for (int r=0; r<15; r++) {
            for (int c=0; c<15; c++) {
                if (board[r][c] == '\u0000') pw.print(".");
                else if (isBlank[r][c]) pw.print((char)((int)board[r][c]+32));
                else pw.print(board[r][c]);
            }
            pw.println();
        }

        if (turn) pw.println("0");
        else pw.println("1");

        pw.println(scores[0] + "\n" + scores[1]);
        pw.println(letterRacks[0] + "\n" + letterRacks[1]);

        pw.close();
    }

    public static void resetBoard(String fileName) throws IOException {
        PrintWriter pw = new PrintWriter(new File(fileName));

        for (int r=0; r<15; r++) {
            for (int c=0; c<15; c++) pw.print(".");
            pw.println();
        }

        pw.print("1\n0\n0\n\n");

        pw.close();
    }
}