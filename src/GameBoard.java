/**
 * This class represents the board of the game Tic-Tac-Toe is being played
 * on.
 *
 * @author Yash Jain
 *         SBU ID: 109885836
 *         email: yash.jain@stonybrook.edu
 *         HW 5 CSE 214
 *         Section 10 Daniel Scanteianu
 *         Grading TA: Anand Aiyer
 */
public class GameBoard {
    //Data fields
    private Box[] board;
    private final int boardSize = 9;

    //Constructor

    /**
     * Empty constructor for GameBoard.
     * <dd>Postconditions:</dd>
     * <db>Initializes the array of Boxes to size boardSize (9)</db>
     */
    public GameBoard(){
        board = new Box[boardSize];

        for(int i = 0; i < 9; i++){
            board[i] = Box.EMPTY;
        }
    }

    //Accessor

    /**
     * Returns the game Board.
     * @return
     *      Returns a Box array, which represents a game board.
     */
    public Box[] getBoard() {
        return board;
    }

    /**
     * Get the amount of boxes that are empty in the GameBoard and return the
     * value
     * @return
     *      Indicates amount of boxes that are empty (int)
     */
    public int getEmptyBoxesLeft(){
        //Counter
        int boxesLeft = 0;

        //Iterate through each Box in the GameBoard
        for(int i = 0; i < 9; i++){
            if(board[i] == Box.EMPTY)
                boxesLeft++;
        }

        return boxesLeft;
    }

    //Mutator

    /**
     * Sets the local Game Board to the Game board object passed.
     * @param board
     *      Box array object that is passed.
     */
    public void setBoard(Box[] board) {
        this.board = board;
    }

    /**
     * To string method that prints out how the GameBoard object looks like.
     * @return
     *      String representation of the board
     */
    @Override
    public String toString(){
        String board = "|";

        for(int i = 0; i < 9; i++){
            if(this.board[i] == Box.EMPTY)
                board += "_|";
            else
                board += this.board[i] + "|";
            if((i+1) % 3 == 0 && i != 8)
                board += "\n|";
        }

        return board;
    }

    /**
     * Clones the GameBoard
     * @return
     *      (GameBoard) - cloned GameBoard
     */
    public GameBoard clone() {
        GameBoard cloneBoard = new GameBoard();
        Box[] cloneBox = new Box[9];

        for(int i = 0; i < 9; i++)
            cloneBox[i] = board[i];

        cloneBoard.setBoard(cloneBox);
        return cloneBoard;
    }
}
