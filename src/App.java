
import java.util.Scanner;

import chess.chessMatch;
import chess.chessPiece;
import chess.chessPosition;

public class App {
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        chessMatch chessMatch = new chessMatch();
        while(true){
            UI.printBoard(chessMatch.getPieces());
            System.out.println();
            System.out.println("Source: ");
            chessPosition source = UI.readChessPosition(sc);

            System.out.println("Target: ");
            chessPosition target = UI.readChessPosition(sc);

            chessPiece capturedPiece = chessMatch.performChessMove(source, target);

        }
        
    }
}
