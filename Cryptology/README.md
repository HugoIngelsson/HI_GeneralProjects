# Cryptology
This is a folder containing some scripts I made after being inspired by my crytography class (UCSD Lign 17).

ReadableScramble.java contains a script that scrambles an input in such a way that the output is still generally readable, even though in reality all the words are a bunch of nonsense. It's based on the concept that humans generally tend to read only the first and last letters of a word and then figure it out from that, even if the middle is slightly off.

FindVariableCompression.java is my take at data compression, which I created the day before our professor went over the provably most efficient compression algorithm (the Huffman code). I tackled it like a dynamic programming question, only I made a false assumption that turns out to not always give you the best compression. It's not optimal, but it's relatively close to the Huffman code in terms of efficiency.

Huffman.java contains the aforementioned Huffman code, which is the most efficient way to compress data (in this particular way). It greedily constructs a binary tree that can then be used to assign codes to each character in a text. (Now looking at it, it seems like I never actually got to the part of assigning the representations of each character? Oops...)

The rest of the files are helper files to allow the two above to run.
