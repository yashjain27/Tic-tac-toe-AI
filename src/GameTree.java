/**
 * This class represents the tree of possible moves in the game of Tic-Tac-Toe.
 *
 * @author Yash Jain
 *         SBU ID: 109885836
 *         email: yash.jain@stonybrook.edu
 *         HW 5 CSE 214
 *         Section 10 Daniel Scanteianu
 *         Grading TA: Anand Aiyer
 */
public class GameTree {
    //Data fields
    private GameBoardNode root;
    private GameBoardNode cursor;

    //Constructor

    /**
     * Constructor for the GameTree
     * <dd><b>Postconditions:</b></dd>
     * <db>Created a new GameTree object, sets up the root of the tree and
     * sets the cursor equal to root.</db>
     */
    public GameTree(){
        //GameBoard object that is to be push in the GameBoardNode
        GameBoard gameBoard = new GameBoard();

        //New GameBoardNode object
        root = null;

        //Set the cursor to root
        cursor = root;
    }

    //Accessors

    /**
     * Return the Root of the tree
     * @return
     *       GameBoardNode (root)
     */
    public GameBoardNode getRoot(){
        return root;
    }

    /**
     * Returns the cursor of the tree
     * @return
     *      GameBoardNode (cursor)
     */
    public GameBoardNode getCursor(){
        return cursor;
    }

    //Mutators

    /**
     * Sets the root of the tree
     * @param root
     *      <b>(GameBoardNode)</b> Root of the tree
     */
    public void setRoot(GameBoardNode root){
        this.root = root;
    }

    /**
     * Sets the cursor of the tree
     * @param cursor
     *      <b>(GameBoardNode)</b> Cursor of the tree
     */
    public void setCursor(GameBoardNode cursor){
        this.cursor = cursor;
    }

    //Methods

    /**
     * Move to be made by the player
     * <dd><b>Preconditions:</b></dd>
     * <db>position is not less than 0 or greater than 1</db>
     * @param position
     *       Position where the player wants to play the move
     * <dd><b>Postconditions:</b></dd>
     * <db>Move made by the player</db>
     */
    public void makeMove(int position){
        //Check if the position is out of bounds
        if(position < 1 || position > 9)
            throw new IllegalArgumentException();
        if(cursor.getConfig(position - 1) != null) {
            cursor = cursor.getConfig(position - 1);
        }
        else {
            System.out.println("Illegal move, try again");
            throw new IllegalArgumentException();
        }
    }

    /**
     * Builds the GameTree
     * @param root
     *      <b>(GameBoardNode)</b> The root of the tree
     * @param turn
     *      <b>(Box)</b> The player's turn
     * @return
     *      GameBoardNode (root)
     * <dd><db>Postconditions:</db></dd>
     * <db>Tree made</db>
     */
    public static GameBoardNode buildTree(GameBoardNode root, Box turn){
        //nodeCounter++;
        for(int i = 0; i < 9; i++){
            if(root.getConfig(i) != null ){
                //Initialize the children of the current node (parent)
                GameBoard board = root.getConfig(i).getGameBoard().clone(); //Root's board

                //Set up the nextTurn
                Box nextTurn;
                if(turn == Box.X)
                    nextTurn = Box.O;
                else
                    nextTurn = Box.X;

                GameBoardNode gameBoardNode = new GameBoardNode(board, nextTurn);
                gameBoardNode.setUpConfig();
                checkWin(gameBoardNode);

                //Recursively set up the grandchildren
                if(!gameBoardNode.getIsEnd())
                    buildTree(gameBoardNode, nextTurn);

                //Set the parent's children
                root.setConfig(i, gameBoardNode);
            }
        }

        return root;
    }

    /**
     * Checks who the winner of the game is
     * @param node
     *      <b>(GameBoardNode)</b> GameBoardNode to be check to see if it won
     * @return
     *      <b>Box</b> - what player won
     */
    public static Box checkWin(GameBoardNode node){
        GameBoard board = node.getGameBoard();
        Box[] box = board.getBoard();

        if(box[0] == Box.X && box[1] == Box.X && box[2] == Box.X || box[3] == Box.X && box[4] == Box.X &&
                box[5] == Box.X || box[6] == Box.X && box[7] == Box.X && box[8] == Box.X ||
                box[0] == Box.X && box[3] == Box.X && box[6] == Box.X || box[1] == Box.X && box[4] == Box.X &&
                box[7] == Box.X || box[2] == Box.X && box[5] == Box.X && box[8] == Box.X ||
                box[0] == Box.X && box[4] == Box.X && box[8] == Box.X ||
                box[2] == Box.X && box[4] == Box.X && box[6] == Box.X) {
           node.setIsEnd(true);
           node.setWinner(Box.X);
           return Box.X;
        }
        else if(box[0] == Box.O && box[1] == Box.O && box[2] == Box.O || box[3] == Box.O && box[4] == Box.O &&
                box[5] == Box.O || box[6] == Box.O && box[7] == Box.O && box[8] == Box.O ||
                box[0] == Box.O && box[3] == Box.O && box[6] == Box.O || box[1] == Box.O && box[4] == Box.O &&
                box[7] == Box.O || box[2] == Box.O && box[5] == Box.O && box[8] == Box.O ||
                box[0] == Box.O && box[4] == Box.O && box[8] == Box.O ||
                box[2] == Box.O && box[4] == Box.O && box[6] == Box.O) {
            node.setIsEnd(true);
            node.setWinner(Box.O);
            return Box.O;
        }
        else if(board.getEmptyBoxesLeft() == 0) {
            node.setIsEnd(true);
            node.setWinner(Box.EMPTY);
            return Box.EMPTY;
        }
        else
            return null;
    }

    /**
     * Returns the probability of a win at the cursor
     * @return
     *      double
     */
    public double cursorProbability(){
        return cursor.getWinProb();
    }

}
