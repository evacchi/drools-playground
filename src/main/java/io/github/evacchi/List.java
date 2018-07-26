package io.github.evacchi;

import java.util.Objects;

import org.kie.api.definition.type.Position;

public class List {

    public static class Empty extends List {

        private Empty() {
            super(null, null);
        }

        @Override
        public boolean isEmpty() {
            return true;
        }
    }

    public static List Empty = new List.Empty();

    @Position(0)
    private final Object head;
    @Position(1)
    private final List tail;

    public List(Object head, List tail) {
        this.head = head;
        this.tail = tail;
    }

    public Object getHead() {
        return head;
    }

    public List getTail() {
        return tail;
    }

    public boolean isEmpty() { return false; }

    @Override
    public String toString() {
        return "[" + head + (tail == null ? "" : tail.toString()) + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof List) {
            List that = (List) obj;
            return Objects.equals(this.head, that.head) &&
                    Objects.equals(this.tail, that.tail);
        }
        return false;
    }

}
