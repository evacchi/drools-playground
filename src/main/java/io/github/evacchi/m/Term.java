package io.github.evacchi.m;

interface Term {

    void bind(Object value);

    interface Atom extends Term {
        void setValue(Object value);
        Object getValue();
    }
    interface Variable extends Term {}
    interface Sentence extends Term {
        Term[] terms();
    }

    interface Meta {

        PersonMeta $meta();

        Sentence $sentence();
    }

}
