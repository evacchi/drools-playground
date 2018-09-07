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
        Term term(int i);
        Sentence term(int i, Term t);
        int size();
    }

    interface Meta {
        Sentence $sentence();
    }

}
