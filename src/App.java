
import chess.chessMatch;

public class App {
    public static void main(String[] args) {
        

        chessMatch chessMatch = new chessMatch();
        UI.printBoard(chessMatch.getPieces());
    }
}
