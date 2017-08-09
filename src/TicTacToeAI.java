import java.util.Scanner;

/**
 * This is the driver class
 *
 * @author Yash Jain
 *         SBU ID: 109885836
 *         email: yash.jain@stonybrook.edu
 *         HW 5 CSE 214
 *         Section 10 Daniel Scanteianu
 *         Grading TA: Anand Aiyer
 */
public class TicTacToeAI {
    static Box winner;

    public static void main(String[] args) {
        //Game Tree
        GameTree gameTree = new GameTree();
        GameBoard board = new GameBoard();

        //Set the root of the tree
        GameBoardNode gameBoardNode = new GameBoardNode(board, Box.X);
        gameBoardNode.setUpConfig();

        //Build tree
        gameTree.setRoot(gameTree.buildTree(gameBoardNode, Box.X));
        gameTree.setCursor(gameTree.getRoot());

        //System.out.println(gameTree.checkWin(gameTree.getCursor()));
        System.out.println(gameTree.getCursor().getGameBoard().toString());
        gameTree.getCursor().setProbabilities();
        System.out.println("The probability of a win: " + gameTree.cursorProbability());
        System.out.println("win leaves: " + gameTree.getCursor().getWinningLeaves());
        System.out.println("total leaves: " + gameTree.getCursor().getTotalLeaves());

        //Play game until the end
        while (!gameTree.getCursor().getIsEnd())
            playGame(gameTree);

        //Print out the results
        if (winner == Box.EMPTY)
            System.out.println("It's a draw!");
        else
            System.out.println("The winner is: " + winner);

    }

    /**
     * Provides a user interface allowing a player to play against the Tic-Tac-Toe AI represented by tree.
     *
     * @param tree GameTree
     */
    public static void playGame(GameTree tree) {
        //Scanner
        Scanner input = new Scanner(System.in);

        //User - Make a move
        while (true) {
            System.out.println("Please make a move");
            try {
                //Human's move
                tree.makeMove(input.nextInt());
                input.nextLine();
                winner = tree.checkWin(tree.getCursor());  //Check winner
                break;
            } catch (Exception e) {
                System.out.println("Illegal Argument, please try again.");
                input.nextLine();
            }
        }

        //AI - move
        if (!tree.getCursor().getIsEnd())
            while (true) {
                try {
                    //Computer's turn
                    double AI = 0;
                    int aiMove = tree.getCursor().miniMax()[1] + 1;

                    tree.makeMove(aiMove);
                    winner = tree.checkWin(tree.getCursor());  //Check winner
                    break;
                } catch (Exception e) {
                    System.out.println("Illegal Argument, please try again. AI");
                }
            }

        //Print out the board at the cursor
        System.out.println(tree.getCursor().getGameBoard().toString());
        System.out.println("The probability of a win: " + tree.getCursor().getWinProb()/*tree.cursorProbability()*/);
        System.out.println("The probability of a loss: " + tree.getCursor().getLoseProb());
        System.out.println("The probability of a draw: " + tree.getCursor().getDrawProb());
        System.out.println("win leaves: " + tree.getCursor().getWinningLeaves());
        System.out.println("total leaves: " + tree.getCursor().getTotalLeaves());
    }


}
