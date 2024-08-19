package rhys.util;

public record Move(Vec2 from, Vec2 to) {

    public boolean to(Vec2 position) {
        return to.equals(position);
    }

    @Override
    public String toString() {
        return from + " " + to;
    }
}