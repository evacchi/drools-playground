package io.github.evacchi.m.lib;

import java.util.Arrays;

public abstract class AbstractCompound<T extends Term.ObjectTerm> extends AbstractTerm<T> implements Term.Compound {
    private final Term[] terms;

    public AbstractCompound(Term[] terms) {
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
        tt.$getTerm(this);
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

