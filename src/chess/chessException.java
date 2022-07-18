package chess;

import boardGame.boardException;

public class chessException extends boardException {
     
    private static final long serialVersionUID = 1L;

    public chessException(String msg){
        super(msg);
    }
}
