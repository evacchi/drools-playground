package io.github.evacchi.m;

import java.util.Arrays;

abstract class AbstractTerm<T extends Term.ObjectTerm> implements Term {
    int index = -1;
    T parent;

    public T parentObject() {
        return parent;
    }

    @Override
    public void setParentObject(ObjectTerm parentObject) {
        this.parent = (T) parentObject;
    }

    @Override
    public void bind(Object value) {
        this.parent = (T) value;
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public int getIndex() {
        return this.index;
    }
}

abstract class AbstractSentence<T extends Term.ObjectTerm>  extends AbstractTerm<T> implements Term.Sentence {
    private final Term[] terms;

    public AbstractSentence(Term[] terms) {
        this.terms = terms;
    }

    @Override
    public Term[] terms() {
        return terms;
    }

    @Override
    public Term term(int i) {
        return terms[i];
    }

    @Override
    public void term(int i, Term t) {
        terms[i] = t;
        t.bind(parent);
        t.setIndex(i);
    }

    @Override
    public int size() {
        return terms.length;
    }

    public void bind(Object o) {
        T tt = (T) o;
        this.parent = tt;
        tt.$setSentence(this);
        for (int i = 0; i < terms.length; i++) {
            Term t = terms[i];
            t.setIndex(i);
            t.bind(o);
        }
    }

    @Override
    public String toString() {
        return Arrays.asList(terms).toString();
    }


}

