package main.java.chess;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final WinnerPlayer winner;
    private final HumanPlayer human;
    private final ChessBoard chessBoard;

    public Game() {
        winner = new WinnerPlayer();
        human = new HumanPlayer();
        chessBoard = new ChessBoard();
    }

    public void play() {
        List<Figure> figures = new ArrayList<>();

        figures.add(Figure.BlackKing);
        figures.add(Figure.WhiteKing);
        figures.add(Figure.WhiteRook);

        System.out.println("Enter the correct start placement of the main.java.chess pieces.");
        System.out.println("For each figure enter a letter a-h and a number 1-8, separated by a space.\n");

        human.setStartPosition(chessBoard, figures);

        System.out.println("\nStart position:");
        System.out.println(chessBoard);

        if (chessBoard.whiteWin()) {
            System.out.println("There is a checkmate on the board.");
            return;
        }

        winner.makeMove(chessBoard);
        while (!chessBoard.whiteWin()) {
            human.makeMove(chessBoard);
            winner.makeMove(chessBoard);
        }

        System.out.println("\nThe black king has been checkmated.");
    }
}
