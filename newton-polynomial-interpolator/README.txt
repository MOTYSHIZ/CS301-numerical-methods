The input file for the program can be changed via the first line of code in the main method: File file = new File("input.txt");

There are some known issues with the program.
For inputs of 15 and below, this program outputs the simplified polynomial in reasonable time. 
At around 20 inputs, the program takes a long time to calculate the simplified polynomial, but it eventually puts it out.
Unfortunately, as the number of inputs approaches 30, the program exceeds the GC overhead, and is unable to simplify the polynomial.

Additionally, the divided difference table seems to output a lot of 0 values for larger input sizes. This is because my DecimalFormat keeps rounding them off to 0 before displaying them. 
However, if I take out the DecimalFormat, whole numbers are not displayed properly in the table for smaller input sizes.

Please have mercy in grading this program, because my graduation this quarter depends on whether or not I pass this class.
I ran out of time to optimize the program because I desperately need to study for another exam. I spent a long time trying to get polynomial simplification to work
through regex, but that turned out to be too complicated, so I ended up wasting a lot of time. It ended up being a much more difficult problem than I expected.
It was my mistake for not testing the larger inputs earlier.

Thank you
-Justin Ordonez