package chess;

import boardGame.piece;
import boardGame.board;
import boardGame.position;

public abstract class chessPiece extends piece{

    private color color;

    public chessPiece(board board, color color) {
        super(board);
        this.color = color;
    }

    public color getColor() {
        return color;
    }

    protected boolean isThereOponnentPiece(position position){
        chessPiece p = (chessPiece) getBoard().piece(position);
        return p != null && p.getColor() != color;
    }
    
    
    
}
