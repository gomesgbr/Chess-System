package chess;

import boardGame.piece;
import boardGame.board;

public abstract class chessPiece extends piece{

    private color color;

    public chessPiece(board board, color color) {
        super(board);
        this.color = color;
    }

    public color getColor() {
        return color;
    }
    
    
    
}
