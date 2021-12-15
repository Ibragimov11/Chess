package main.java.chess;

public class WinnerPlayer implements Player {
    private enum WayOfCheckmate {
        Horizontal, Vertical, Unknown
    }

    private WayOfCheckmate way = WayOfCheckmate.Unknown;
    private boolean wrVisitedCorner = false;
    private char columnCorner;
    private int rowCorner;
    private int countMoves = 0;

    @Override
    public void makeMove(final ChessBoard board) {
        System.out.println("\nWhite:");

        char columnBQ = board.getColumn(Figure.BlackKing);
        int rowBQ = board.getRow(Figure.BlackKing);
        char columnWQ = board.getColumn(Figure.WhiteKing);
        int rowWQ = board.getRow(Figure.WhiteKing);
        char columnWR = board.getColumn(Figure.WhiteRook);
        int rowWR = board.getRow(Figure.WhiteRook);

        countMoves++;

        if (countMoves == 1) {
            columnCorner = columnBQ >= 'e' ? 'a' : 'h';
            rowCorner = rowBQ >= 4 ? 0 : 7;
        }

        if (!wrVisitedCorner && columnWR == columnCorner && rowWR == rowCorner
                && !(columnWQ == columnCorner || rowWQ == rowCorner)) {
            wrVisitedCorner = true;
        }

        if (wrVisitedCorner && way == WayOfCheckmate.Unknown) {
            if (Math.abs(rowBQ - rowWQ) > 1) {
                way = WayOfCheckmate.Horizontal;
            } else {
                way = WayOfCheckmate.Vertical;
            }
        }

        if (wrVisitedCorner) {
            if (way == WayOfCheckmate.Horizontal) { // Horizontal checkmate with a rook
                if (rowWR >= rowWQ && rowWR >= rowBQ || rowWR <= rowWQ && rowWR <= rowBQ) {
                    if (Math.abs(columnWR - columnBQ) == 1) {
                        way = WayOfCheckmate.Unknown;
                        board.move(Figure.WhiteRook, columnWR == 'a' ? 'h' : 'a', rowWR);
                    } else {
                        board.move(Figure.WhiteRook, columnWR, rowBQ > rowWQ ? rowBQ - 1 : rowBQ + 1);
                    }
                } else {
                    if (Math.abs(columnWR - columnBQ) == 1) {
                        if (Math.abs(rowWQ - rowBQ) == 2 && (columnWR == 'b' || columnWR == 'g')) {
                            board.move(Figure.WhiteRook, columnWR == 'b' ? 'g' : 'b', rowWR);
                        } else {
                            board.move(Figure.WhiteRook, columnWR <= 'd' ? 'h' : 'a', rowWR);
                        }
                    } else {
                        if (Math.abs(rowWQ - rowBQ) == 2 && (columnWQ == columnBQ
                                || Math.abs(columnWQ - columnBQ) == 1 && (columnBQ == 'a' || columnBQ == 'h'))) {
                            board.move(Figure.WhiteRook, columnWR, rowBQ);
                        } else {
                            if (Math.abs(rowWQ - rowBQ) > 2) {
                                if (Math.abs(rowWR - rowBQ) > 1) {
                                    board.move(Figure.WhiteRook, columnWR, rowBQ > rowWQ ? rowBQ - 1 : rowBQ + 1);
                                } else if (columnWR == 'b' || columnWR == 'g') {
                                    board.move(Figure.WhiteRook, columnBQ >= 'e' ? 'a' : 'h', rowWR);
                                } else {
                                    board.move(Figure.WhiteKing, columnWQ, rowBQ > rowWQ ? rowWQ + 1 : rowWQ - 1);
                                }
                            } else {
                                if (Math.abs(columnBQ - columnWQ) == 1
                                        && Math.abs(columnWR - columnBQ) < Math.abs(columnWR - columnWQ)
                                        && Math.abs(columnWR - columnBQ) < 5
                                        && (columnWR == 'a' || columnWR == 'h')) {
                                    board.move(Figure.WhiteRook, columnWR == 'a' ? 'h' : 'a', rowWR);
                                } else if (Math.abs(columnWR - columnBQ) == 5
                                        && Math.abs(columnWR - columnWQ) == 6
                                        && (columnWR == 'a' || columnWR == 'h')) {
                                    board.move(Figure.WhiteRook, columnWR == 'a' ? 'b' : 'g', rowWR);
                                } else if ((columnWR == 'b' || columnWR == 'g')
                                        && Math.abs(columnWR - columnWQ) < Math.abs(columnWR - columnBQ)) {
                                    board.move(Figure.WhiteRook, columnWR == 'b' ? 'a' : 'h', rowWR);
                                } else {
                                    board.move(Figure.WhiteKing,
                                            (char) (columnBQ > columnWQ ? columnWQ + 1 : columnWQ - 1), rowWQ);
                                }
                            }
                        }
                    }
                }
            } else { // Vertical checkmate with a rook
                if (columnWR >= columnWQ && columnWR >= columnBQ
                        || columnWR <= columnWQ && columnWR <= columnBQ) {
                    if (Math.abs(rowWR - rowBQ) == 1) {
                        way = WayOfCheckmate.Unknown;
                        board.move(Figure.WhiteRook, columnWR, rowWR == 0 ? 7 : 0);
                    } else {
                        board.move(Figure.WhiteRook,
                                (char) (columnBQ > columnWQ ? columnBQ - 1 : columnBQ + 1), rowWR);
                    }
                } else {
                    if (Math.abs(rowWR - rowBQ) == 1) {
                        if (Math.abs(columnWQ - columnBQ) == 2 && (rowWR == 1 || rowWR == 6)) {
                            board.move(Figure.WhiteRook, columnWR, rowWR == 1 ? 6 : 1);
                        } else {
                            board.move(Figure.WhiteRook, columnWR, rowWR <= 3 ? 7 : 0);
                        }
                    } else {
                        if (Math.abs(columnWQ - columnBQ) == 2 && (rowWQ == rowBQ
                                || Math.abs(rowWQ - rowBQ) == 1 && (rowBQ == 0 || rowBQ == 7))) {
                            board.move(Figure.WhiteRook, columnBQ, rowWR);
                        } else {
                            if (Math.abs(columnWQ - columnBQ) > 2) {
                                if (Math.abs(columnWR - columnBQ) > 1) {
                                    board.move(Figure.WhiteRook,
                                            (char) (columnBQ > columnWQ ? columnBQ - 1 : columnBQ + 1), rowWR);
                                } else if (rowWR == 1 || rowWR == 6) {
                                    board.move(Figure.WhiteRook, columnWR, rowBQ >= 4 ? 0 : 7);
                                } else {
                                    board.move(Figure.WhiteKing,
                                            (char) (columnBQ > columnWQ ? columnWQ + 1 : columnWQ - 1), rowWQ);
                                }
                            } else {
                                if (Math.abs(rowBQ - rowWQ) == 1
                                        && Math.abs(rowWR - rowBQ) < Math.abs(rowWR - rowWQ)
                                        && Math.abs(rowWR - rowBQ) < 5
                                        && (rowWR == 0 || rowWR == 7)) {
                                    board.move(Figure.WhiteRook, columnWR, rowWR == 0 ? 7 : 0);
                                } else if (Math.abs(rowWR - rowBQ) == 5
                                        && Math.abs(rowWR - rowWQ) == 6
                                        && (rowWR == 0 || rowWR == 7)) {
                                    board.move(Figure.WhiteRook, columnWR, rowWR == 0 ? 1 : 6);
                                } else if ((rowWR == 1 || rowWR == 6)
                                        && Math.abs(rowWR - rowWQ) < Math.abs(rowWR - rowBQ)) {
                                    board.move(Figure.WhiteRook, columnWR, rowWR == 1 ? 0 : 7);
                                } else {
                                    board.move(Figure.WhiteKing, columnWQ, rowBQ > rowWQ ? rowWQ + 1 : rowWQ - 1);
                                }
                            }
                        }
                    }
                }
            }
        } else {
            goToTheCorner(board);
        }
    }

    private void goToTheCorner(ChessBoard board) {
        char columnBQ = board.getColumn(Figure.BlackKing);
        int rowBQ = board.getRow(Figure.BlackKing);
        char columnWQ = board.getColumn(Figure.WhiteKing);
        int rowWQ = board.getRow(Figure.WhiteKing);
        char columnWR = board.getColumn(Figure.WhiteRook);
        int rowWR = board.getRow(Figure.WhiteRook);

        if (columnWR != columnCorner && rowWR != rowCorner) {
            if ((rowBQ == 0 || rowBQ == 7) && (columnBQ == 'a' || columnBQ == 'h')
                    && Math.abs(rowBQ - rowWR) == 1 && Math.abs(columnBQ - columnWR) == 1
                    && (rowWQ == rowBQ && Math.abs(columnWQ - columnBQ) == 2
                    || columnWQ == columnBQ && Math.abs(rowWQ - rowBQ) == 2)) {
                if (columnWQ == columnBQ) {
                    board.move(Figure.WhiteRook, columnCorner, rowWR);
                } else {
                    board.move(Figure.WhiteRook, columnWR, rowCorner);
                }
            } else {
                if (columnWR != columnWQ) {
                    board.move(Figure.WhiteRook, columnWR, rowCorner);
                } else {
                    board.move(Figure.WhiteRook, columnCorner, rowWR);
                }
            }
        } else {
            if (columnWQ == columnCorner || rowWQ == rowCorner) {
                int newWQRow = rowWQ;
                char newWQColumn = columnWQ;

                if (rowWQ == rowCorner && columnWQ == columnCorner) {
                    newWQRow = rowCorner == 0 ? 1 : 6;
                    newWQColumn = columnCorner == 'a' ? 'b' : 'g';
                } else if (rowWQ == rowCorner) {
                    newWQRow = rowCorner == 0 ? 1 : 6;

                    if (columnWQ == 'a') {
                        newWQColumn = 'b';
                    } else if (columnWQ == 'h') {
                        newWQColumn = 'g';
                    }
                } else {
                    newWQColumn = columnCorner == 'a' ? 'b' : 'g';

                    if (rowWQ == 0) {
                        newWQRow = 1;
                    } else if (rowWQ == 7) {
                        newWQRow = 6;
                    }
                }

                board.move(Figure.WhiteKing, newWQColumn, newWQRow);
            } else {
                board.move(Figure.WhiteRook, columnCorner, rowCorner);
            }
        }
    }
}
