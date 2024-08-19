package rhys.board;

import rhys.util.Move;
import rhys.moves.PieceStep;
import rhys.pieces.Piece;
import rhys.pieces.*;
import rhys.util.Side;
import rhys.util.Vec2;

import java.util.*;
import java.util.stream.IntStream;

public final class Board {

    // state of board
    // list of moves made
    public final List<Piece> pieces = new ArrayList<>();
    final State state = new State();
    public final BoardHistory history = new BoardHistory();
    public static final int SIZE = 8;

    public Board() {
        this.reset();
    }

    public void reset() {
        this.pieces.clear();
        this.state.reset();
        this.history.reset();
        fill(Side.WHITE);
        fill(Side.BLACK);
    }

    private void fill(Side side) {
        IntStream.range(0, SIZE).forEach(x -> pieces.add(new Piece(PieceType.Pawn, new Vec2(x, side.offset(1)), side)));
        mirror(side, 0, PieceType.Rook);
        mirror(side, 1, PieceType.Knight);
        mirror(side, 2, PieceType.Bishop);
        pieces.add(new Piece(PieceType.Queen, new Vec2(3, side.origin()), side));
        pieces.add(new Piece(PieceType.King, new Vec2(4, side.origin()), side));
    }

    private void mirror(Side side, int x, PieceType type) {
        pieces.add(new Piece(type, new Vec2(x, side.origin()), side));
        pieces.add(new Piece(type, new Vec2(7 - x, side.origin()), side));
    }

    public Optional<Piece> take(Vec2 position, Side side) {
        List<Piece> taken = this.pieces.stream().filter(position::matches).filter(side::other).toList();
        pieces.removeAll(taken);
        assert(taken.size() <= 1);
        return taken.stream().findFirst();
    }

    public Optional<Piece> at(Vec2 position) {
        return pieces.stream().filter(piece -> piece.position.equals(position)).findFirst();
    }

    public BoardState state() {
        return new BoardState(this);
    }

    public Side turn() {
        return state.turn.side;
    }

    //TODO: change consumer to an interface for updating frontend
    public boolean move(Move move, BoardFrontend frontend) {
        Optional<Map.Entry<Piece, PieceStep>> space = this.at(move.from()).filter(state.turn.side::same).flatMap(p -> p.canMove(this.state(), move.to()).map(s -> Map.entry(p, s)));
        space.ifPresent(entry -> {

            Piece piece = entry.getKey();

            Optional<Piece> taken = piece.move(this, entry.getValue(), move.to(), frontend);

            history.addMove(move, taken);

            List<Piece> kings = pieces.stream().filter(p -> p.isKing(piece.side.other())).toList();

            boolean check = pieces.stream().filter(piece.side::same).flatMap(p -> p.targets(state())).anyMatch(target -> kings.stream().anyMatch(target::matches));

            state.check = check ? kings.stream().flatMap(king -> king.moves(state())).toList() : null;

            if (state.check != null) {
                System.out.println("Check!");
                if (state.check.isEmpty()) {
                    System.out.println("Mate!");
                }
            }

            this.state.turn.increment();
        });
        return space.isPresent();
    }

    public void reset(BoardFrontend frontend) {
        for (Piece piece : pieces) {
            frontend.update(new Piece(piece.position));
        }
        this.reset();
        for (Piece piece : pieces) {
            frontend.update(new Piece(piece));
        }
    }

    public boolean undo(BoardFrontend frontend) {
        BoardHistory.PreviousMove prev = history.undoMove(this);
        if (prev == null)
            return false;
        frontend.update(new Piece(at(prev.move().from()).orElseThrow()));
        frontend.update(prev.taken().map(Piece::new).orElseGet(() -> new Piece(prev.move().to())));
        return true;
    }

    public static class State {
        public Side.Turn turn;
        public List<Vec2> check;

        public State() {
            this.reset();
        }

        public void reset() {
            turn = new Side.Turn();
            check = null;
        }
    }
}
