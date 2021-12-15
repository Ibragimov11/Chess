package main.java.chess;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private final Scanner in;

    public HumanPlayer(Scanner in) {
        this.in = in;
    }

    public HumanPlayer() {
        this(new Scanner(System.in));
    }

    @Override
    public void makeMove(final ChessBoard chessBoard) {
        System.out.println("\nEnter one of the letters a-h and one of the numbers 1-8 separated by a space:");
        int row;
        char column;

        do {
            Object[] pairRowColumn = inputRowColumn(chessBoard);
            row = (int) pairRowColumn[0];
            column = (char) pairRowColumn[1];
        } while (!chessBoard.correctBlackKingMove(column, row));

        System.out.println("\nBlack:");
        chessBoard.move(Figure.BlackKing, column, row);
    }

    public void setStartPosition(ChessBoard chessBoard, List<Figure> figures) {
        List<Integer> rows = new ArrayList<>();
        List<Character> columns = new ArrayList<>();

        do {
            rows.clear();
            columns.clear();

            for (Figure figure : figures) {
                System.out.println("Enter the coordinates of the " + figure + ":");

                Object[] pairRowColumn = inputRowColumn(chessBoard);
                int row = (int) pairRowColumn[0];
                char column = (char) pairRowColumn[1];

                columns.add(column);
                rows.add(row);
            }
        } while (!chessBoard.correctStartPosition(figures, columns, rows));

        chessBoard.setStartPosition(figures, columns, rows);
    }

    private Object[] inputRowColumn(ChessBoard chessBoard) {
        String row;
        String column;

        do {
            column = in.next();
            row = in.next();
        } while (!chessBoard.correctCoordinates(column, row));

        return new Object[]{8 - Integer.parseInt(row), column.charAt(0)};
    }
}
