# Scrabble
A script that looks at a current scrabble game state (found in "data/input board.txt") and finds the best playable word
with the current player's tile rack. Currently, it's configured to play several games against itself rapidly.

## Reading the input
The first 15 lines contain the current scrabble board. Periods ('.') represent unplaced positions, uppercase letters 
(i.e. 'A') represent regular tiles, and lowercase letters (i.e. 'a') represent blanks.

The next line shows whose turn it is: 0 means it's Player 1's turn, and 1 means it's Player 2's turn

The next two lines show each player's score. The first line is Player 1's score, the second line is Player 2's score

The last two lines show each player's letter rack (note that the program only considers the current player's rack when
making decisions).

## How the Program Works
First, the program creates a search tree of what I call "identities." Each node is either: 1) a branch, or 2) a leaf
- Each branch contains an array of children nodes.
- Each leaf contains an Identity object. This object contains a String "identity," which is the frequencies of characters of the word(s) the object contains, and an ArrayList<String> of the words the object contains.

The tree is created in a sort of binary-search iterative deepening, using a preprocessed, sorted data file of "identities" (i.e. character frequency strings along with the words they describe). There's a recursive function that keeps track of a start index, end index, and the index of the alphabet (0-25) we're at. The program continues to iteratively deepen while there's more than one identity at each layer; this means that there are two identities that have the same character frequencies for the first couple of letters in the alphabet and diverge later. Once start == end, it can create a Leaf node at the current node and be sure that we'll be able to find this identity uniquely later.

Once it's created the search tree, the program can examine each placement location. Using the current rack and the letters already on the board, it can find all possible words covered by their combined identity; it then checks the validity of each placement (including words across) and score it if it's a legal move.

Finally, the program applies a slight point bonus/deduction based on the letter rack left behind. I had a version of the program that would simulate potential point follow-ups from the opponent, but I realized that it took too long (a couple of seconds) per move, which wasn't what I wanted from the project. Now we can score each move and make a final decision of what to play.

## Why I chose to use "identities"
Based on what I described so far, this system of "identities" probably sounds very convoluted. Why not just check every word and see where they could be played?

First, this would be fairly inefficient--there are many words that we could never hope to play with our current rack. Second, the identities handle blanks very efficiently. At each search step, when you can't access a certain branch of the search tree with a current identity rack, but you have a wildcard, you could access the branch by subtracting one (or more) from a "wildcards" parameter in the recursive search function. Then, if the wildcards parameter becomes negative, simply exit. This means that blanks aren't handled exponentially (only in the sense that there are exponentially more available words for each additional blank), which was a problem I faced during my Boggle project.
