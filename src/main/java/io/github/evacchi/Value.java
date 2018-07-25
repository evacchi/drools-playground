package io.github.evacchi;

public class Value {
    private final Object value;

    public Value(Object value) {
        this.value = value;
    }

    public Object unwrap() {
        return value;
    }
}
