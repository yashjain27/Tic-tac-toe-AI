/**
 * This class represents a possible configuration of the board.
 *
 * @author Yash Jain
 *         SBU ID: 109885836
 *         email: yash.jain@stonybrook.edu
 *         HW 5 CSE 214
 *         Section 10 Daniel Scanteianu
 *         Grading TA: Anand Aiyer
 */
public class GameBoardNode {
    //Data fields
    private GameBoard board;
    private boolean isEnd;
    private Box currentTurn;
    private Box winner;
    private GameBoardNode config[] = new GameBoardNode[9];
    private double winProb;
    private double loseProb;
    private double drawProb;
    private int totalLeaves = 0;
    private int winningLeaves = 0;
    private int losingLeaves = 0;
    private int drawLeaves = 0;
    private int miniMax = 0;

    //Constructor

    /**
     * Constructor for a GameBoardNode object.
     * @param board
     *      Board object passed.
     * @param currentTurn
     *      Current Turn, either Box.O or Box.X
     * <dd><b>Postconditions:</b></dd>
     * <db>Creates a GameBoardNode object, sets up the configuration for the node. </db>
     */
    public GameBoardNode(GameBoard board, Box currentTurn){
        //Check if the currentTurn doesn't equal Box.EMPTY
        if(currentTurn == Box.EMPTY)
            throw new IllegalArgumentException();

        //Check if the GameBoard object is not null.
        if(board == null)
            throw new NullPointerException();

        //Else go on with the construction.
        this.board = board;
        this.currentTurn = currentTurn;

    }

    /**
     * Empty GameBoardNode constructor
     */
    public GameBoardNode(){
        //Empty constructor
    }
    /**
     * Sets up the config array for the GameBoardNode
     */
    public void setUpConfig(){
        //Initialize config[]
        for(int i = 0; i < 9; i++){
            //Local Box array
            GameBoard gBoard = board.clone(); //Children Gameboard
            Box[] box = gBoard.getBoard(); //Box for children

            //Check if the Box is empty, yes push the current iteration of GameBoardNode to config[]
            if(box[i] == Box.EMPTY){
                box[i] = currentTurn;   //Set the Box to the value of currentTurn

                //Create GameBoard for GameBoardNode
                gBoard.setBoard(box);

                //Create GameBoard node for the config[] array
                GameBoardNode gameBoardNode = new GameBoardNode(gBoard, currentTurn);
                config[i] = gameBoardNode;
            } //Else push a null object to config[]
            else{
                config[i] = null;
            }
        }

    }

    //Accessor

    /**
     * Get the GameBoard
     * @return
     *      Returns the Gameboard object
     */
    public GameBoard getGameBoard() {
        return board;
    }

    /**
     * Returns whether the game has ended or not.
     * @return
     *      boolean
     */
    public boolean getIsEnd(){
        return isEnd;
    }

    /**
     * Returns the winner of the node
     * @return
     *      Box
     */
    public Box getWinner(){
        return winner;
    }

    /**
     * Returns the probability of winning in this node
     * @return
     *      double
     */
    public double getWinProb() {
        return winProb;
    }

    /**
     * Returns the probability of losing
     * @return
     *      double
     */
    public double getLoseProb() {
        return loseProb;
    }

    /**
     * Returns the probability of drawing
     * @return
     *      double
     */
    public double getDrawProb() {
        return drawProb;
    }

    /**
     * Returns the total amount of leaves
     * @return
     *      int
     */
    public int getTotalLeaves() {
        return totalLeaves;
    }

    /**
     * Returns the total amount of winning leaves
     * @return
     *      int
     */
    public int getWinningLeaves() {
        return winningLeaves;
    }

    /**
     * Returns the total amount of losing leaves
     * @return
     *      int
     */
    public int getLosingLeaves() {
        return losingLeaves;
    }

    /**
     * Returns the total amount of draw leaves
     * @return
     *      int
     */
    public int getDrawLeaves() {
        return drawLeaves;
    }

    /**
     * Returns the value of the leaves for AI, win = 1, draw = 0, loss = -1
     * @return
     *      int
     */
    public int getMiniMax(){
        return miniMax;
    }
    //Mutator

    /**
     * Sets the config at index i to a new GameBoardNode object passed
     * @param i
     *        index of the config array
     * @param gameBoardNode
     *        GameBoardNode object passed that should override previous config at index i
     */
    public void setConfig(int i, GameBoardNode gameBoardNode) {
        config[i] = gameBoardNode;
    }

    /**
     * Sets the winner of the GameBoardNode
     * @param box
     *       Player who won (draw, X, or O)
     */
    public void setWinner(Box box){
        this.winner = box;
    }

    /**
     * Sets whether the current node is a leave or not.
     * @param bool
     *       <b>(Boolean)</b> - value indicating whether the node is a leave or not
     */
    public void setIsEnd(boolean bool){
        this.isEnd = bool;
    }

    //Methods

    /**
     * Returns the node indicated by the move played by the player.
     * @return
     *      GameBoardNode returned indicated by the move played by the user
     */
    public GameBoardNode getConfig(int i){
        return config[i];
    }

    /**
     * Sets the probabilities of winning, losing, and drawing for the particular node.
     */
    public void setProbabilities(){
       for(int i = 0; i < 9; i++){
           if(config[i] != null){
               if(!config[i].isEnd){
                   config[i].setProbabilities();
                   totalLeaves += config[i].getTotalLeaves();
                   winningLeaves += config[i].getWinningLeaves();
                   losingLeaves += config[i].getLosingLeaves();
                   drawLeaves += config[i].getDrawLeaves();
                   winProb = 1.0 * winningLeaves / totalLeaves;
                   loseProb = 1.0 * losingLeaves / totalLeaves;
                   drawProb = 1.0 * drawLeaves / totalLeaves;
               } else{
                   totalLeaves++;
                   if(config[i].getWinner() == Box.X)
                       winningLeaves++;
                   else if(config[i].getWinner() == Box.O)
                       losingLeaves++;
                   else
                       drawLeaves++;
               }
           }
           else if(this.getIsEnd()){
               totalLeaves++;
               if(this.getWinner() == Box.X)
                   winningLeaves++;

               else if(this.getWinner() == Box.O)
                   losingLeaves++;
               else
                   drawLeaves++;
               winProb = 1.0 * winningLeaves / totalLeaves;
               loseProb = 1.0 * losingLeaves / totalLeaves;
               drawProb = 1.0 * drawLeaves / totalLeaves;
               break;
           }
       }
       return;
    }

    public int[] miniMax(){
        int[] valueAndMove = {-11, 0};
        int[] tempHolder;

        if((board.getBoard()[0] == Box.X || board.getBoard()[2] == Box.X || board.getBoard()[6] == Box.X
                || board.getBoard()[8] == Box.X) && board.getBoard()[4] == Box.EMPTY) {
            valueAndMove[1] = 4;
            return valueAndMove;
        }

        for(int i = 0; i < 9; i++){
            if(config[i] != null){
                if(!config[i].isEnd){
                        tempHolder = config[i].miniMax();
                    if(tempHolder[0] > valueAndMove[0]) {
                        valueAndMove[0] = tempHolder[0];
                        valueAndMove[1] = tempHolder[1];
                    }
                }else{
                    if(getWinner() == Box.X) {
                        miniMax = -10;
                        if(miniMax > valueAndMove[0]) {
                            valueAndMove[0] = miniMax;
                            valueAndMove[1] = i;
                        }
                    }
                    else if(getWinner() == Box.O) {
                        miniMax = 10;
                        if(miniMax > valueAndMove[0]) {
                            valueAndMove[0] = miniMax;
                            valueAndMove[1] = i;
                        }
                    }
                    else {
                        miniMax = 0;
                        if(miniMax > valueAndMove[0]) {
                            valueAndMove[0] = 0;
                            valueAndMove[1] = i;
                        }
                    }
                }
            }
            if(i == 8)
                valueAndMove[0]--;
        }
        return valueAndMove;
    }

    /**
     * To string method that prints out the children of the node
     * @return
     *      String
     */
    @Override
    public String toString(){
        String print = "";

        for(int i = 0; i < 9; i++){
            if(config[i] != null) {
                print += config[i].getGameBoard().toString() + i + "\n\n";
            }
        }
        return print;
    }



}
