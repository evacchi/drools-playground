package io.github.evacchi;

import org.kie.api.definition.type.Position;

public class Last {
    @Position(0)
    private final Value value;
    @Position(1)
    private final List list;

    public Last(Object value, List list) {
        this.value = new Value(value);
        this.list = list;
    }

    public Value getValue() {
        return value;
    }

    public List getList() {
        return list;
    }

    @Override
    public String toString() {
        return "Last{" +
                "value=" + value +
                ", list=" + list +
                '}';
    }
}
