package io.github.evacchi.m;

interface Term {

    void bind(Object value);
    void setIndex(int index);
    int getIndex();

    interface Atom extends Term, Cloneable {
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

    interface ObjectTerm {
        Sentence $sentence();
    }

    interface Meta<A extends Atom, V extends Variable, S extends Sentence> {
        A atom();

        A atom(Term.Atom orig);

        A atom(Term.Variable orig);

        V variable();

        S sentence(Term name, Term age);

    }

}
