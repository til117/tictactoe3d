
import java.util.*;
//import java.util.concurrent.ThreadLocalRandom;

public class Player {
    /**
     * Performs a move
     *
     * @param gameState
     *            the current state of the board
     * @param deadline
     *            time before which we must have returned
     * @return the next state the board is in after our move
     */
    private final int dim = 4;
    private GameState nextMove;
    private int player;
    private int opponent;
    private final int level = 4;
    private static enum MODE{
        HORIZONTAL , VERTICAL , DIAGONAL
    }
    private static enum FACE{
        FRONT, SIDE
    }
    private static enum DIAG{
        LEFT, RIGHT, TOPL, TOPR, CROSSL, CROSSR
    }
    
    public GameState play(final GameState gameState, final Deadline deadline) {

        if(gameState.getNextPlayer() == Constants.CELL_X){
            player = Constants.CELL_O;
            opponent = Constants.CELL_X;
        }else{
            player = Constants.CELL_X;
            opponent = Constants.CELL_O;
        }
        minimax(gameState);
        return nextMove;
    }    
    private void minimax(GameState state){
        int x = minimaxAB(state,level,player, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    private int minimaxAB(GameState state,int depth, int player, int alpha, int beta){
        if(depth == 0 || state.isEOG()){
            return heuristic(state);
        }
        Vector<GameState> nextStates = new Vector<>();
        state.findPossibleMoves(nextStates);
        int bestH;
        int currentH;
        GameState best = null;
        if(player == this.player){
            bestH = Integer.MIN_VALUE;
            for(GameState next : nextStates){
                currentH = minimaxAB(next, depth -1, opponent, alpha, beta);
                if(currentH > bestH){
                    best = next;
                    bestH = currentH;
                }
                alpha = Math.max(alpha, bestH);
                if(beta <= alpha){
                    break;
                }
            }
            nextMove = best;
            return bestH;
        }else{
            bestH = Integer.MAX_VALUE;
            for(GameState next : nextStates){
                currentH = minimaxAB(next, depth -1, this.player, alpha, beta);
                if(currentH < bestH){
                    best = next;
                    bestH = currentH;
                }
                beta = Math.min(beta, bestH);
                if(beta <= alpha){
                    break;
                }
            }
            nextMove = best;
            return bestH;
        }
    }
    private int heuristic(GameState state){
        int res = 0;
        if( (state.isOWin() && player == Constants.CELL_O) || (state.isXWin() && player == Constants.CELL_X) ){
            return Integer.MAX_VALUE;
        }else if((state.isOWin() && player == Constants.CELL_X) || (state.isXWin() && player == Constants.CELL_O)){
            return Integer.MIN_VALUE;
        }else if(state.isEOG()){
            return 0;
        }else{
            for (int i = 0; i < dim; i++) {
                for (MODE mode : MODE.values()) {
                    for (FACE face : FACE.values()) {
                        res+= line(state, i, mode, face);
                    }
                }
            }
        }
        return res;
    }
    private int line(GameState state, int layer, MODE mode, FACE face){
        int res =0;
        int current = 0;
        int partial;
        if(mode!=MODE.DIAGONAL){
            for (int i = 0; i < dim; i++) {
                partial =0;
                for (int j = 0; j < dim; j++) {
                    if(mode == MODE.HORIZONTAL){
                        if(face ==FACE.FRONT){
                            current = state.at(i, j, layer);
                        }else if(face ==FACE.SIDE){
                            current = state.at(i, layer, j);
                        }else{
                            current = Constants.CELL_INVALID;
                        }
                    }else if(mode == MODE.VERTICAL){
                        if(face ==FACE.FRONT){
                            current = state.at(j, i, layer);
                        }else{
                            current = Constants.CELL_INVALID;
                        }
                    }else{
                        current = Constants.CELL_INVALID;
                    }
                    if(current == player){
                        if(partial >= 0){
                            partial++;
                        }else{
                            j =dim;
                            partial = 0;
                        }
                    }else if(current == opponent){
                        if(partial <= 0){
                            partial--;
                        }else{
                            j = dim;
                            partial = 0;
                        }
                    }
                }
                res+=partial;
            }
        }else{
            for (DIAG diag : DIAG.values()) {
                partial =0;
                for (int i = 0; i < dim; i++) {
                    if(face ==FACE.FRONT){
                        if(diag == DIAG.LEFT){
                            current = state.at(i,i,layer);
                        }else if(diag == DIAG.RIGHT){
                            current = state.at(i,dim -1-i,layer);
                        }else if(diag == DIAG.TOPL){
                            current = state.at(layer,i,i);
                        }else if(diag == DIAG.CROSSL && layer ==0){
                            current = state.at(i,i,i);
                        }else if(diag == DIAG.CROSSL && layer ==1){
                            current = state.at(dim -1 -i,i,i);
                        }else{
                            current = Constants.CELL_INVALID;
                        }
                    }else if(face ==FACE.SIDE){
                        if(diag == DIAG.LEFT){
                            current = state.at(i,layer,i);
                        }else if(diag == DIAG.RIGHT){
                            current = state.at(i,layer,dim -1-i);
                        }else if(diag == DIAG.TOPR){
                            current = state.at(layer,dim -1-i, i);
                        }else if(diag == DIAG.CROSSR && layer == 0){
                            current = state.at(i,dim -1-i,i);
                        }else if(diag == DIAG.CROSSR && layer == 1){
                            current = state.at(dim -1-i,dim -1-i,i);
                        }else{
                            current = Constants.CELL_INVALID;
                        }
                    }
                    if(current == player){
                        if(partial >= 0){
                            partial++;
                        }else{
                            i = dim;
                            partial = 0;
                        }
                    }else if(current == opponent){
                        if(partial <= 0){
                            partial--;
                        }else{
                            i = dim;
                            partial = 0;
                        }
                    }
                }
                res+=partial;
            }
        }
        return res;
    }
}

