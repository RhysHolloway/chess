package rhys.pieces;

import rhys.moves.PieceStep;
import rhys.moves.steps.KingStep;
import rhys.moves.steps.KnightStep;
import rhys.moves.steps.MultiStepAndTake;
import rhys.moves.steps.pawn.DoubleStep;
import rhys.moves.steps.pawn.EnPassant;
import rhys.moves.steps.pawn.PawnTake;
import rhys.moves.steps.pawn.SingleStep;
import rhys.util.Vec2;

import java.util.stream.Stream;

public enum PieceType {

    Pawn(new SingleStep(), new DoubleStep(), new PawnTake(), new EnPassant()),
    Rook(new MultiStepAndTake(Vec2.directions())),
    Knight(new KnightStep()),
    Bishop(new MultiStepAndTake(Vec2.diagonals())),
    Queen(new MultiStepAndTake(Stream.concat(Vec2.directions(), Vec2.diagonals()))),
    King(new KingStep());

    public final PieceStep[] steps;

    PieceType(PieceStep... steps) {
        this.steps = steps;
    }
}
