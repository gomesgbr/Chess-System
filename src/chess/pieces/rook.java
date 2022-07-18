package chess.pieces;

import chess.chessPiece;
import boardGame.board;
import chess.color;

public class rook extends chessPiece{

    public rook(board board, color color) {
        super(board, color);
        
    }

    @Override
    public String toString(){
        return "R";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        return mat;
    }
    
}
