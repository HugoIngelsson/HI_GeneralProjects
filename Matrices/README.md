# Matrices
This is another one of those tiny projects I just had an idea for and went for it. 

We were learning about row reduction and its applications in linear algebra, and I thought it might be nice to have a helper program that 
could quiz you on the inverses of different matrices. This program does just that: it generates a random matrix (or a predetermined one, 
if you change the code slightly) and computes the inverse through row reduction; it then gives you the option to compute it on your 
own before showing you the inverse.

To help with human readability, I made the row reduction happen in terms of fractions (instead of floating point values), for which I
implemented my own Fraction object. This makes it much less tedious to check your answer and maintains perfect precision with the answer.
