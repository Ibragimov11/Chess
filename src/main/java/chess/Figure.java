package main.java.chess;

import java.util.Map;

public enum Figure {
    BlackKing, WhiteKing, WhiteRook, Empty;

    private static final Map<Figure, String> figuresToString = Map.of(
            Figure.BlackKing, "black king",
            Figure.WhiteKing, "white king",
            Figure.WhiteRook, "white rook"
    );

    @Override
    public String toString() {
        return figuresToString.get(this);
    }
}
