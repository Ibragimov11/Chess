package main.java.chess;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChessBoard {
    private static final Map<Character, Integer> columnsToInt = Map.of(
            'a', 0, 'b', 1,
            'c', 2, 'd', 3,
            'e', 4, 'f', 5,
            'g', 6, 'h', 7
    );
    private static final Map<Figure, Character> SYMBOLS = Map.of(
            Figure.BlackKing, '♚',
            Figure.WhiteKing, '♔',
            Figure.WhiteRook, '♖',
            Figure.Empty, '.'
    );

    private final Figure[][] board = new Figure[8][8];
    private final Map<Figure, Map<String, Integer>> coordinates = new HashMap<>();

    public ChessBoard() {
        for (Figure[] row : board) {
            Arrays.fill(row, Figure.Empty);
        }
    }

    public boolean correctCoordinates(String stringColumn, String stringRow) {
        if (stringColumn.length() != 1) {
            System.out.println("Incorrect coordinates. Try again:");
            return false;
        }

        char column = stringColumn.charAt(0);
        int row;

        try {
            row = Integer.parseInt(stringRow);
        } catch (Exception e) {
            System.out.println("Incorrect coordinates. Try again:");
            return false;
        }

        boolean correct = 1 <= row && row <= 8 && 'a' <= column && column <= 'h';

        if(!correct) {
            System.out.println("Incorrect coordinates. Try again:");
        }

        return correct;
    }

    public boolean correctStartPosition(List<Figure> figures, List<Character> columns, List<Integer> rows) {
        for (int i = 0; i < figures.size(); i++) {
            for (int j = i + 1; j < figures.size(); j++) {
                if (rows.get(i).equals(rows.get(j)) && columns.get(i).equals(columns.get(j))) {
                    System.out.println("\nThe start position is incorrect. \nTry again:\n");
                    return false;
                }
            }
        }

        char columnBQ = 0;
        int rowBQ = 0;
        char columnWQ = 0;
        int rowWQ = 0;
        char columnWR = 0;
        int rowWR = 0;

        for (int i = 0; i < figures.size(); i++) {
            switch (figures.get(i)) {
                case BlackKing -> {
                    columnBQ = columns.get(i);
                    rowBQ = rows.get(i);
                }
                case WhiteKing -> {
                    columnWQ = columns.get(i);
                    rowWQ = rows.get(i);
                }
                case WhiteRook -> {
                    columnWR = columns.get(i);
                    rowWR = rows.get(i);
                }
            }
        }

        boolean correct = !(Math.abs(rowBQ - rowWQ) < 2 && Math.abs(columnBQ - columnWQ) < 2)
                && rowBQ != rowWR && columnBQ != columnWR;

        if (!correct) {
            System.out.println("\nThe start position is incorrect. \nTry again:\n");
        }

        return correct;
    }

    public void setStartPosition(List<Figure> figures, List<Character> columns, List<Integer> rows) {
        for (int i = 0; i < figures.size(); i++) {
            board[rows.get(i)][columnsToInt.get(columns.get(i))] = figures.get(i);
            coordinates.put(figures.get(i), new HashMap<>());
            coordinates.get(figures.get(i)).put("c", columns.get(i) - 'a');
            coordinates.get(figures.get(i)).put("r", rows.get(i));
        }
    }

    public Boolean correctBlackKingMove(final char newColumnWQ, final int newRowWQ) {
        char columnBQ = getColumn(Figure.BlackKing);
        int rowBQ = getRow(Figure.BlackKing);
        char columnWQ = getColumn(Figure.WhiteKing);
        int rowWQ = getRow(Figure.WhiteKing);
        char columnWR = getColumn(Figure.WhiteRook);
        int rowWR = getRow(Figure.WhiteRook);

        boolean correct = !(rowBQ == newRowWQ && columnBQ == newColumnWQ)
                && !(Math.abs(newRowWQ - rowWQ) < 2 && Math.abs(newColumnWQ - columnWQ) < 2)
                && Math.abs(newRowWQ - rowBQ) <= 1
                && Math.abs(newColumnWQ - columnBQ) <= 1
                && (newRowWQ != rowWR && newColumnWQ != columnWR || newRowWQ == rowWR && newColumnWQ == columnWR);

        if(!correct) {
            System.out.println("\nYour input is incorrect. Try again:");
        }

        return correct;
    }

    public void move(Figure figure, char column, int row) {
        board[coordinates.get(figure).get("r")][coordinates.get(figure).get("c")] = Figure.Empty;
        board[row][columnsToInt.get(column)] = figure;
        coordinates.get(figure).put("c", column - 'a');
        coordinates.get(figure).put("r", row);

        System.out.println(this);
    }

    public Boolean whiteWin() {
        char columnBQ = getColumn(Figure.BlackKing);
        int rowBQ = getRow(Figure.BlackKing);
        char columnWQ = getColumn(Figure.WhiteKing);
        int rowWQ = getRow(Figure.WhiteKing);
        char columnWR = getColumn(Figure.WhiteRook);
        int rowWR = getRow(Figure.WhiteRook);

        return (columnBQ == columnWQ || Math.abs(columnBQ - columnWQ) == 1 && (columnBQ == 'a' || columnBQ == 'h'))
                && Math.abs(rowWQ - rowBQ) == 2
                && (rowBQ == 0 || rowBQ == 7)
                && rowBQ == rowWR
                && Math.abs(columnsToInt.get(columnWR) - columnsToInt.get(columnBQ)) > 1
                || (rowWQ == rowBQ ||Math.abs(rowBQ - rowWQ) == 1 && (rowBQ == 0 || rowBQ == 7))
                && Math.abs(columnsToInt.get(columnWQ) - columnsToInt.get(columnBQ)) == 2
                && (columnBQ == 'a' || columnBQ == 'h')
                && columnBQ == columnWR
                && Math.abs(rowWR - rowBQ) > 1;
    }

    public char getColumn (Figure figure) {
        return (char) (coordinates.get(figure).get("c") + 'a');
    }

    public int getRow (Figure figure) {
        return coordinates.get(figure).get("r");
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            stringBuilder.append(8 - i);
            for (int j = 0; j < 8; j++) {
                stringBuilder.append(SYMBOLS.get(board[i][j]));
            }
            stringBuilder.append("\n");
        }
        stringBuilder.append(" abcdefgh");

        return stringBuilder.toString();
    }
}
