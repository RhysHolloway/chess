package rhys.util;

import rhys.pieces.Piece;

import java.util.stream.Stream;

public record Vec2(int x, int y) {
    public Vec2 add(Vec2 vec) {
        return add(vec.x, vec.y);
    }

    public Vec2 add(int x, int y) {
        return new Vec2(this.x + x, this.y + y);
    }

    public Vec2 mul(Vec2 vec) {
        return mul(vec.x, vec.y);
    }

    public Vec2 mul(int x, int y) {
        return new Vec2(this.x * x, this.y * y);
    }

    public Vec2 scale(int scalar) {
        return mul(scalar, scalar);
    }

    public static Stream<Vec2> directions() {
        return Stream.of(new Vec2(1, 0), new Vec2(-1, 0), new Vec2(0, 1), new Vec2(0, -1));
    }

    public static Stream<Vec2> diagonals() {
        return Stream.of(new Vec2(1, 1), new Vec2(-1, -1), new Vec2(1, -1), new Vec2(-1, 1));
    }

    public boolean matches(Piece piece) {
        return piece.position.equals(this);
    }

    @Override
    public String toString() {
        return "" + ((char)('a' + x)) + ((char) ('1' + y));
    }
}