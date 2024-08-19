package rhys.util;

import rhys.pieces.Piece;

public enum Side {
    BLACK, WHITE;
    
    public Side other() {
        return switch(this) {
            case BLACK -> WHITE;
            case WHITE -> BLACK;
        };
    }

    public int origin() {
        return switch (this) {
            case BLACK -> 7;
            case WHITE -> 0;
        };
    }

    public int forward() {
        return switch (this) {
            case BLACK -> -1;
            case WHITE -> 1;
        };
    }

    public boolean same(Piece piece) {
        return piece.side.equals(this);
    }

    public boolean other(Piece piece) {
        return !same(piece);
    }

    public int offset(int y) {
        return origin() + forward() * y;
    }

    public static class Turn {
        public Side side;
        public int move;
        public Turn() {
            this(Side.WHITE, 0);
        }
        public Turn(Side side, int move) {
            this.side = side;
            this.move = move;
        }
        public void increment() {
            this.side = this.side.other();
            if (this.side == Side.WHITE)
                move++;
        }

        public void decrement() {
            this.side = this.side.other();
            if (this.side == Side.BLACK)
                move--;
        }
    }

}
