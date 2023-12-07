# Scrabble
A script that looks at a current scrabble game state (found in "data/input board.txt") and finds the best playable word
with the current player's tile rack. Currently, it's configured to play several games against itself rapidly.

## Reading the input
The first 15 lines contain the current scrabble board. Periods ('.') represent unplaced positions, uppercase letters 
(i.e. 'A') represent regular tiles, and lowercase letters (i.e. 'a') represent blanks.

The next line shows whose turn it is: 0 means it's Player 1's turn, and 1 means it's Player 2's turn

The next two lines show each player's score. The first line is Player 1's score, the second line is Player 2's score

The last two lines show each player's letter rack.
