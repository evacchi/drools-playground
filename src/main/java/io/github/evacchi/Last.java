package io.github.evacchi;

import org.kie.api.definition.type.Position;

public class Last {
    @Position(0)
    private final Object value;
    @Position(1)
    private final List list;

    public Last(Object value, List list) {
        this.value = value;
        this.list = list;
    }

    public Object getValue() {
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
