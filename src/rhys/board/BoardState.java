package rhys.board;

import rhys.util.Move;
import rhys.pieces.Piece;
import rhys.util.Vec2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class BoardState {

    private final List<Piece> pieces;
    private final BoardHistory history;

    public BoardState(Board board) {
        this.pieces = board.pieces;
        this.history = board.history;
    }

    public Stream<Piece> withMove(Move move) {
        Piece copy = new Piece(at(move.from()).orElseThrow());
        copy.position = move.to();
        return Stream.concat(pieces.stream().filter(p -> !(move.to().matches(p) || move.from().matches(p))), Stream.of(copy));
    }

    public Optional<Piece> at(Vec2 position) {
        return pieces.stream().filter(position::matches).findAny();
    }

    public Stream<Piece> pieces() {
        return pieces.stream();
    }

    public ArrayList<Vec2> history(Piece piece) {
        return history.movesOf(piece);
    }

}
