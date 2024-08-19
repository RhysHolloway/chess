package rhys.moves.steps.pawn;

import rhys.board.Board;
import rhys.pieces.PieceType;
import rhys.util.Vec2;
import rhys.moves.PieceStep;
import rhys.pieces.Piece;

public abstract class PawnStep extends PieceStep {

    public PawnStep(boolean target, Vec2... direction) {
        super(1, target, direction);
    }

    @Override
    public Piece moveWith(Board board, Piece piece) {
        if (piece.position.y() == piece.side.other().origin()) {
            piece.type = PieceType.Queen;
        }
        return piece;
    }
}
