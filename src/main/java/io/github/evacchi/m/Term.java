package io.github.evacchi.m;

interface Term {

    void bind(Object value);
    ObjectTerm parentObject();
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
        Meta<?,?,?> meta();
    }

    interface ObjectTerm {
        Sentence $sentence();
    }

    interface Meta<A extends Atom, V extends Variable, S extends Sentence> {
        A createAtom();

        A createAtom(Term.Atom orig);

        A createAtom(Term.Variable orig);

        V createVariable();

        S createSentence();

    }

}
