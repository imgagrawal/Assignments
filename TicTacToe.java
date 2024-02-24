package Assignment;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


public class Temp {


    /**
     * This is the method that starts one round of game
     */
    public static void startGame(){

        Scanner scan = new Scanner(System.in);
        Random random = new Random();

        // initialise the game board with .
        char board[][] = new char[3][3];
        for (char[] row : board) {
            Arrays.fill(row, '.');
        }

        //decide the symbol for player and computer
        char userChoice, userSymbol, player1 = 'X', player2 = 'O';

        //decide the first move
        System.out.println("Do you want to go first? (Y/N): ");
        while (true) {
            userChoice = scan.next().charAt(0);
            if (userChoice == 'Y'  || userChoice == 'N') {
                break;
            } else {
                System.out.println("Invalid choice. Please Enter 'Y' if yes is the response or 'N' if no is the response");
            }
        }



        // is the choice is yes that then ask the user for symbol
        if (userChoice == 'Y') {
            System.out.println("Select your symbol among ");
            while (true) {
                userSymbol = scan.next().charAt(0);
                if (userSymbol == 'X' || userSymbol == 'O') {
                    player1 = userSymbol == 'X' ? 'X' : 'O';
                    player2 = player1 == 'X' ? 'O' : 'X';
                    break;
                } else {
                    System.out.println("Invalid choice. Choose among 'X' or 'O");
                }
            }
        }


        boolean isGameOver = false;
        char symbol;
        int row = -1;
        int column = -1;


        for (int chance = 1; chance <= 9; chance++) {

            // if user choice is y then player 1 is user and player 2 is computer
            if (userChoice == 'Y') {

                // player 1 is user and its user chance
                if (chance % 2 != 0) {

                    symbol = player1;
                    System.out.println("Your turn");
                    System.out.println("Enter the coordinates (row followed by column)");

                    while (true) {

                        //row = inputRow();
                        row = takeInputIndex("row"); // take row input
                        column = takeInputIndex("column"); // take column input
                        //column = inputColumn();

                        // if the place is vacant then add at that place
                        if (isPlaceVacant(row, column, board)) {
                            fillPlace(row, column, board, symbol);
                            break;
                        }

                        // if place is not vacant then ask to user to reenter the new choices
                        else {
                            System.out.println("The coordinates " + row + "," + column + " is not available.");
                            System.out.println("Please enter new coordinates");
                        }
                    }

                } else {

                    // if user has opted to start first then now its computers turn
                    symbol = player2;
                    System.out.println("Computer's turn");

                    // using random.class to select random coordinates from 0-2
                    while (true) {
                        row = random.nextInt(3);
                        column = random.nextInt(3);

                        // if place is not available then dont add into the board and search for new possible coordinates
                        if (isPlaceVacant(row, column, board)) {
                            fillPlace(row, column, board, symbol);
                            break;
                        }
                    }
                }
            } else {

                // player 1 is AI and its computers turn
                if (chance % 2 != 0) {
                    symbol = player1;
                    System.out.println("Computer's turn");
                    while (true) {
                        row = random.nextInt(3);
                        column = random.nextInt(3);
                        if (isPlaceVacant(row, column, board)) {
                            fillPlace(row, column, board, symbol);
                            break;
                        }
                    }

                } else {
                    symbol = player2;
                    System.out.println("Your turn");
                    System.out.println("Enter the coordinates (row followed by column)");
                    while (true) {

                        row = takeInputIndex("row"); //take row input
                        column = takeInputIndex("column"); //take column input

                        if (isPlaceVacant(row, column, board)) {
                            fillPlace(row, column, board, symbol);
                            break;
                        } else {
                            System.out.println("The coordinates " + row + "," + column + " is not available.");
                            System.out.println("Please enter new coordinates");
                        }
                    }
                }
            }
            displayBoard(board);
            if (chance >= 5) {
                if (isPlayerWinning(board, chance)) {
                    isGameOver = true;
                    if (((chance % 2 == 0) && (userChoice == 'N')) || (chance % 2 != 0 && userChoice == 'Y'))
                        System.out.println("Congratulations, You win..!! ");
                    else
                        System.out.println("Computer Wins!!.. Better luck next time.");
                    break;
                }
            }
        }
        // if till now no one has won the game then its a draw
        if (!isGameOver) {
            System.out.println("Game Over... It's a Draw");
        }

        //closing the scanner object
        scan.close();
    }

    /**
     * This method is used to display the board
     * @param board The current game board
     */
    public static void displayBoard(char[][] board) {

        // printing the rows
        for (char[] row : board) {

            // printing the columns
            for (char column : row) {
                System.out.print(column + " ");
            }

            System.out.println("");
        }
    }


    //driver function
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        String index = "";

        //start game (enter -1 to end game)
        while(true) {
            startGame();
            System.out.println("Do you want to play again ( any input to continue and -1 to end) ");
            index = scan.nextLine();
            if(index.equals("-1"))
                break;
        }
    }

    /**
     * This method is used to take the input for row or column and also checks for valid input
     * @param str The string that represents for what it is taking input
     * @return int The inputted number
     */
    private static int takeInputIndex(String str) {
        Scanner scan = new Scanner(System.in);
        int index = -1;
        System.out.println("Enter " + str);
        while (true) {
            try {
                index = scan.nextInt();
                if ((index > 2 || index < 0))
                    System.out.println("Please Enter valid coordinates (0 -2 )!");
                else
                    break;
            } catch (Exception e) {
                System.out.println("Invalid input.. Please enter valid coordinates (0-2).");
                scan.nextLine();
            }
        }
        return index;
    }


    /**
     * This method is used to fill the place.
     * @param row The row index
     * @param column The column index
     * @param board The board
     * @param symbol The character symbol that is to be placed
     */
    private static void fillPlace(int row, int column, char[][] board, char symbol) {
        board[row][column] = symbol;
    }

    /**
     * This method is used to check whether the place is vacant or not.
     * @param row The row index
     * @param column The column index
     * @param board The board
     * @return boolean true is place is available else false
     */
    private static boolean isPlaceVacant(int row, int column, char[][] board) {
        return (board[row][column] == '.') ? true : false;
    }


    /**
     * This method is used to check whether any player is winning or not till now.
     * @param board The board
     * @param chance The chance of player
     * @return boolean true if the player is winning or else returns false
     */
    private static boolean isPlayerWinning(char[][] board, int chance) {
        char toBePlaced = (chance % 2 == 0) ? 'O' : 'X';
        for (int row = 0; row < 3; row++) {
            if (board[row][0] == toBePlaced && board[row][1] == toBePlaced && board[row][2] == toBePlaced)
                return true;
            if (board[0][row] == toBePlaced && board[1][row] == toBePlaced && board[2][row] == toBePlaced)
                return true;
        }
        if (board[0][0] == toBePlaced && board[1][1] == toBePlaced && board[2][2] == toBePlaced)
            return true;
        if (board[0][2] == toBePlaced && board[1][1] == toBePlaced && board[2][0] == toBePlaced)
            return true;
        return false;
    }
}
