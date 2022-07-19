package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardGame.board;
import boardGame.piece;
import boardGame.position;
import chess.pieces.king;
import chess.pieces.rook;

public class chessMatch {
    private int turn;
    private color currentPlayer;
    private board board;
    private List<piece> piecesOnTheBoard = new ArrayList<>();
    private List<piece> capturedPieces = new ArrayList<>();
    private boolean check;
    private boolean checkMate;
    public chessMatch(){
        board = new board(8, 8);

        turn = 1;
        currentPlayer = color.WHITE;
        initialSetup();
    }

    public boolean getCheckMate(){
        return this.checkMate;
    }

    private boolean testCheckMate(color color){
        if(!testCheck(color)){
            return false;
        }

        List<piece> list = piecesOnTheBoard.stream().filter(x ->((chessPiece) x).getColor() == color).collect(Collectors.toList());
        
        for(piece p: list){
            boolean[][] mat = p.possibleMoves();
            for(int i = 0; i < board.getRows(); i++){
                for(int j = 0; j < board.getColumns(); j++){
                    if(mat[i][j]){
                        position source = ((chessPiece)p).getChessPosition().toPosition();
                        position target = new position(i, j);
                        piece capturedPiece = makeMove(source, target);
                        boolean testCheck = testCheck(color);
                        undoMove(source, target, capturedPiece);
                        if(!testCheck){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }   

    public int getTurn(){
        return this.turn;
    }

    public color getCurrentPlayer(){
        return this.currentPlayer;
    }

    public boolean getCheck(){
        return this.check;
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

    public boolean[][] possibleMoves(chessPosition sourcePosition){
        position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }

    private void placeNewPiece(char column, int row, chessPiece piece){
        board.placePiece(piece, new chessPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece);
    }

    private void initialSetup(){
        
        placeNewPiece('h', 7, new rook(board, color.WHITE));
        placeNewPiece('d', 1, new rook(board, color.WHITE));
        placeNewPiece('e', 1, new king(board, color.WHITE));

        
        placeNewPiece('b', 8, new rook(board, color.BLACK));
        placeNewPiece('a', 8, new king(board, color.BLACK));
    }

    public chessPiece performChessMove(chessPosition sourcePosition, chessPosition targetPosition){
        position source = sourcePosition.toPosition();
        position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        piece capturedPiece = makeMove(source, target);

        if(testCheck(currentPlayer)){
            undoMove(source, target, capturedPiece);
            throw new chessException("You can't put yourself in check");
        }

        check = (testCheck(oponnent(currentPlayer)) ? true : false);

        if(testCheckMate(oponnent(currentPlayer))){
            checkMate = true;
        }else{
            nextTurn();
        }
        
        return (chessPiece) capturedPiece;
    }

    private void validateTargetPosition(position source, position target){
        if(!board.piece(source).possibleMove(target)){
            throw new chessException("The chosen piece can't move to target position");
        }
    }

    private void validateSourcePosition(position position){
        if(!board.thereIsAPiece(position)){
            throw new chessException("There is no piece on source position");
        }
        if(currentPlayer != ((chessPiece) board.piece(position)).getColor()){
            throw new chessException("The chosen piece is not yours");
        }
        if(!board.piece(position).isThereAnyPossibleMove()){
            throw new chessException("There is no possible moves for the choosen piece.");
        }
    }

    private piece makeMove(position source, position target){
        piece p = board.removePiece(source);
        piece capturedPiece = board.removePiece(target);
        board.placePiece(p, target);

        if(capturedPiece != null){
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }
        return capturedPiece;
    }

    private void undoMove(position source, position target, piece capturedPiece){
        piece p = board.removePiece(target);
        board.placePiece(p, source);

        if(capturedPiece != null){
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }
    }

    private void nextTurn(){
        turn++;
        currentPlayer = (currentPlayer == color.WHITE) ? color.BLACK: color.WHITE;
    }

    private color oponnent(color color){
        return (color == color.WHITE) ? color.BLACK : color.WHITE;
    }

    private chessPiece king(color color){
        List<piece> list = piecesOnTheBoard.stream().filter(x ->((chessPiece) x).getColor() == color).collect(Collectors.toList());
        for(piece p: list){
            if(p instanceof king){
                return (chessPiece) p;
            }
        }

        throw new IllegalStateException("There is no " + color + "king on the board");
    }

    private boolean testCheck(color color){
        position kingPosition = king(color).getChessPosition().toPosition();
        List<piece> oponnentPieces = piecesOnTheBoard.stream().filter(x ->((chessPiece) x).getColor() == oponnent(color)).collect(Collectors.toList());
        
        for(piece p: oponnentPieces){
            boolean[][] mat = p.possibleMoves();
            if(mat[kingPosition.getRow()][kingPosition.getColumn()]){
                return true;
            }
        }
        return false;
    }
}
