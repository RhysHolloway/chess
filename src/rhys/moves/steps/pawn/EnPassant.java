package rhys.moves.steps.pawn;

import rhys.board.Board;
import rhys.pieces.PieceType;
import rhys.util.Move;
import rhys.util.Side;
import rhys.util.Vec2;
import rhys.board.BoardState;
import rhys.moves.PieceStep;
import rhys.pieces.Piece;

import java.util.Optional;

public class EnPassant extends PieceStep {

    public EnPassant() {
        super(1, false, new Vec2(-1, 1), new Vec2(1, 1));
    }

    @Override
    protected boolean moveCondition(BoardState state, Move move, Side side) {
        return emptyOrTake(state, move.to(), side) && state.at(move.to().add(0, -side.forward())).filter(p -> p.type == PieceType.Pawn && state.history(p).size() == 2).isPresent();
    }

    @Override
    public Optional<Piece> takeWith(Board board, Piece piece) {
        return board.take(piece.position.add(0, -piece.side.forward()), piece.side);
    }

}
