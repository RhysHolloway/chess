package rhys.pieces;

import rhys.board.Board;
import rhys.board.BoardFrontend;
import rhys.util.Side;
import rhys.util.Vec2;
import rhys.board.BoardState;
import rhys.moves.PieceStep;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public final class Piece {

    // TODO: seperate postion from type and side

    public PieceType type;
    public Vec2 position;
    public final Side side;

    public Piece(PieceType type, Vec2 position, Side side) {
        this.type = type;
        this.position = position;
        this.side = side;
    }

    public Piece(Piece piece) {
        this.type = piece.type;
        this.position = piece.position;
        this.side = piece.side;
    }

    public Piece(Vec2 position) {
        this.position = position;
        this.side = null;
        this.type = null;
    }

    public Optional<Piece> move(Board board, PieceStep step, Vec2 move, BoardFrontend frontend) {
        frontend.update(new Piece(this.position));
        this.position = move;
        Optional<Piece> taken = step.takeWith(board, this);
        taken.ifPresent(t -> frontend.update(new Piece(t.position)));
        Piece piece = step.moveWith(board, this);
        frontend.update(new Piece(piece));
        return taken;
    }

    public Optional<PieceStep> canMove(BoardState state, Vec2 move) {
        return Arrays.stream(this.type.steps).filter(s -> s.moves(state, this.position, this.side).anyMatch(move::equals)).findFirst();
    }

    public Stream<Vec2> moves(BoardState state) {
        return Arrays.stream(this.type.steps).flatMap(s -> s.moves(state, this.position, this.side));
    }

    public Stream<Vec2> targets(BoardState state) {
        return Arrays.stream(this.type.steps).flatMap(s -> s.targets(state, this.position, this.side));
    }

    public boolean sided(Vec2 at, Side side) {
        return this.side.equals(side) && this.position.equals(at);
    }

    public boolean isKing(Side side) {
        return this.type == PieceType.King && this.side.equals(side);
    }

    public boolean isKing(Vec2 at, Side side) {
        return isKing(side) && this.position.equals(at);
    }

    @Override
    public String toString() {
        return type.name() + position;
    }

    public boolean position(Vec2 other) {
        return this.position.equals(other);
    }

}
