package io.github.evacchi.m.lib;

public abstract class AbstractMeta<A extends Term.Atom, V extends Term.Variable, S extends Term.Compound> implements Term.Meta<A,V,S> {

    @Override
    public A createAtom(Term.Atom orig) {
        A o = (A) orig;
        A a = createAtom();
        a.setIndex(o.getIndex());
        a.setParentObject(o.parentObject());
        return a;
    }

    @Override
    public A createAtom(Term.Variable orig) {
        V o = (V) orig;
        A a = createAtom();
        a.setIndex(o.getIndex());
        a.setParentObject(o.parentObject());
        return a;
    }


}
