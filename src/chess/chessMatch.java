package chess;

import boardGame.board;
import boardGame.position;
import chess.pieces.king;
import chess.pieces.rook;

public class chessMatch {
    private board board;

    public chessMatch(){
        board = new board(8, 8);
        initialSetup();
    }

    public chessPiece[][] getPieces(){
        chessPiece[][] mat = new chessPiece[board.getRows()][board.getColumns()];

        for(int i = 0; i < board.getRows(); i++){
            for(int j = 0; j < board.getColumns(); j++){
                mat[i][j] = (chessPiece) board.piece(i, j);
            }
        }

        return mat; 
    }

    private void initialSetup(){
        board.placePiece(new rook(board, color.WHITE), new position(2, 1));
        board.placePiece(new king(board, color.BLACK), new position(0, 4));
    }
}
