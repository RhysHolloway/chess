package rhys.moves;

import rhys.board.Board;
import rhys.board.BoardState;
import rhys.pieces.PieceType;
import rhys.util.Move;
import rhys.util.Side;
import rhys.util.Vec2;
import rhys.pieces.Piece;

import java.util.Arrays;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.BiPredicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class PieceStep {
    private final Vec2[] direction;
    private final boolean target;
    private final OptionalInt limit;


    public PieceStep(boolean target, Stream<Vec2> direction) {
        this(target, direction.toArray(Vec2[]::new));
    }

    public PieceStep(int limit, boolean target, Stream<Vec2> direction) {
        this(limit, target, direction.toArray(Vec2[]::new));
    }

    public PieceStep(boolean target, Vec2... direction) {
        this.direction = direction;
        this.target = target;
        this.limit = OptionalInt.empty();
    }

    public PieceStep(int limit, boolean target, Vec2... direction) {
        this.direction = direction;
        this.target = target;
        this.limit = OptionalInt.of(limit);
    }

    private int limit() {
        return limit.orElse(8);
    }



    protected abstract boolean moveCondition(BoardState state, Move move, Side side);

    public Piece moveWith(Board board, Piece piece) {
        return piece;
    }

    public Optional<Piece> takeWith(Board board, Piece piece) {
        return board.take(piece.position, piece.side);
    }

    @Deprecated(since = "bi predicate -> predicate")
    private Stream<Vec2> with(Vec2 position, Side side, BiPredicate<Vec2, Integer> pred) {
        return Arrays.stream(direction).map(d -> d.scale(side.forward())).flatMap(direction -> IntStream.rangeClosed(1, limit()).takeWhile(step -> pred.test(direction, step)).mapToObj(i -> position.add(direction.scale(i))));
    }

    public final Stream<Vec2> targets(BoardState state, Vec2 position, Side side) {
        return with(position, side, (direction, step) -> {
            Vec2 move = position.add(direction.scale(step));
            return target && on(move) && previousUnoccupied(state, position, direction, step);
        });
    }

    public final Stream<Vec2> moves(BoardState state, Vec2 position, Side side) {
        return with(position, side, (direction, step) -> {
            Vec2 dest = position.add(direction.scale(step));
            return on(dest) &&
                    previousUnoccupied(state, position, direction, step) &&
                    moveCondition(state, new Move(position, dest), side);
        }).filter(move -> preventsCheck(state, new Move(position, move)));
    }

    protected static boolean empty(BoardState state, Vec2 move) {
        return state.pieces().noneMatch(move::matches);
    }

    protected static boolean take(BoardState state, Vec2 move, Side side) {
        return state.pieces().anyMatch(other -> other.sided(move, side.other()) && !(other.type == PieceType.King));
    }

    protected static boolean emptyOrTake(BoardState state, Vec2 move, Side side) {
        return empty(state, move) || take(state, move, side);
    }

    protected static boolean on(Vec2 position) {
        return position.x() >= 0 && position.x() < 8 && position.y() >= 0 && position.y() < 8;
    }

    private static boolean preventsCheck(BoardState state, Move move) {
        Side side = state.at(move.from()).orElseThrow().side;
        return state.withMove(move).filter(side::other)
                .flatMap(p -> p.targets(state)).noneMatch(target -> state.pieces().anyMatch(p -> p.isKing(target, side)));
    }

    @Deprecated
    private static boolean previousUnoccupied(BoardState state, Vec2 position, Vec2 direction, int count) {
        return count <= 1 || state.pieces().noneMatch(position.add(direction.scale(count - 1))::matches);
    }
}

