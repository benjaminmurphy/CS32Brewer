#Brewer Tutorial

1. #### Getting Started

    Welcome to Brewer! With Brewer, you can watch your programs come to life! In your browser, you can see three main areas of the brewer app: the block menu on the left, the playground in the center, and the console on the right. 

    The Block Menu allows you to select different blocks to create your programs. By dragging various blocks from the menu to the Playground, you can build a virtually unlimited set of programs. Then, in the Console, you can run, stop, save, and load the programs you create. 

    ![](tutorial-images/1-overview..png)

2. #### Your First Program

    Let’s create your first program! Go to the block menu, and drag a “Print Block” onto the main area in the center. This block takes an expression as an argument, and prints its value. In this case, our value will be the string “Hello!”. 

    Drag a String Literal block from the menu into the field of the print block you already added. Literal blocks represent values, such as numbers, strings, or booleans. Then, in this literal block type the text “Hello!”. 

    Now, you’re ready to run your program. On the console, click the “Run” button. This will execute your program and output the results. As you can see, your program has one output: It printed the text “Hello!”

    Now, try replacing the “Hello!” with other literals, such as a number or a boolean.

    ![](tutorial-images/1-overview..png)

3. #### Variables

    Brewer supports not only literal values, but also variables that can store values and change. Click the “Add Variable” button to add a new variable. Now, you can reference variables by dragging variable blocks to the main area, and using the dropdown box to select specific variables. 

    In order to assign values to variables, one uses Set block. Set blocks take a variable block in the first field, and an expression of the appropriate type in the second. The value of the expression is then assigned to the variable. 

    Try creating a program that sets a numerical variable to 20 and prints it out. 

    ![](tutorial-images/1-overview..png)

4. #### Manipulating Variables and Values
    Several blocks can be used to manipulate the values of both literals and variables. For numerical values, one can use the Arithmetic block to do arithmetic operations on pairs of numbers. One can add, subtract, multiply, or divide numbers, as well as find the modulus. For Boolean values, one can use the binary logical operator block to AND or OR two values. One can also use the NOT block to flip a boolean value. For strings, numbers, or booleans, one can compare them using the comparison block. This checks for equality, less-than, or greater-than and returns a boolean value. 

    Try creating a program that computes whether the square of a number is even. 

    ![](tutorial-images/1-overview..png)

5. #### Program Flow

    There are two ways of controlling program flow: If (and If/Else) statements, and While loops. An If statement takes a boolean expression in the first field, and if it is true, it evaluates the list of expressions in the second field. An If/Else statement is similar, but it executes a list of statements (in a third field) if the conditional evaluates to false. 

    A While loop, on the other hand, repeatedly executes the expressions in its second field until the boolean expression in the first field evaluates to false. (Note: if the conditional is initially false, the loop will never execute.)

    Try creating a program that computes the factorial of a number.

    ![](tutorial-images/1-overview..png)

6. #### Using the Console

    There are several things one can use the console for, other than simply running programs and viewing their output. What if a program had an infinite loop? For instance, you could create a program that, while 5 is less than 6, would set a variable to 1. This program would never finish looping, since 5 is always less than 6. However, if one creates an infinite loop, one can always stop execution with the “Kill” button. This tells the server to stop running the program, even if it is in a loop. 

    One can also save a program by clicking the “Save” button. This will produce a special ID number that corresponds to the program stored on the Brewer Server. Then, one can enter this ID into the textbox at the bottom of the console and click “Load”. This will load the program from memory. Every time one saves a program, a new copy is stored, so one does not have to worry about making changes to existing programs.

    One can also hide and show the console by toggling the “Console” button.

7. #### Explore!

    Now that you’ve covered all of the basics, see what you can make! Happy Brewing!
