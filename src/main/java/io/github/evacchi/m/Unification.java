package io.github.evacchi.m;

import io.github.evacchi.m.lib.Term;

public class Unification {

    public void unify(Term.Compound left, Term.Compound right) {
        if (left.size() != right.size()) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < left.size(); i++) {
            Term lt = left.term(i), rt = right.term(i);
            if (lt instanceof Term.Atom && rt instanceof Term.Atom) {
                if (!lt.equals(rt)) throw new IllegalArgumentException();
            } else if (lt instanceof Term.Atom && rt instanceof Term.Variable) {
                copyAtom((Term.Atom) lt, right, i);
            } else if (lt instanceof Term.Variable && rt instanceof Term.Atom) {
                copyAtom((Term.Atom) rt, left, i);
            } else if (lt instanceof Term.Compound && rt instanceof Term.Variable) {
                copyCompound((Term.Compound) lt, right, i);
            } else if (lt instanceof Term.Variable && rt instanceof Term.Compound) {
                copyCompound((Term.Compound) rt, left, i);
            } else if (lt instanceof Term.Variable && rt instanceof Term.Variable) {
                Term.Atom la = createAtom((Term.Variable) lt, left, i);
                Term.Atom ra = createAtom((Term.Variable) rt, right, i);
                // we set both "manually" to an arbitrary valid value
                la.setValue(ra.getValue());
            } else {
                throw new UnsupportedOperationException();
            }
        }
    }

    private void copyCompound(Term.Compound compound, Term.Compound parent, int index) {
        // fixme not working/tested yet
        Term.Meta<?,?,?> meta = compound.meta();
        Term.Compound s = meta.createCompoundTerm();
        s.bind(parent.parentObject());
        for (int i = 0; i < compound.size(); i++) {
            compound.term(i, meta.createVariable());
        }
        parent.term(index, s);

    }

    private Term.Atom createAtom(Term.Variable variable, Term.Compound compound, int index) {
        Term.Meta<?,?,?> meta = compound.meta();
        Term.Atom a1 = meta.createAtom(variable);
        a1.bind(compound.parentObject());
        compound.term(index, a1);
        return a1;
    }

    private void copyAtom(Term.Atom atom, Term.Compound term, int index) {
        Term.Meta<?,?,?> meta = term.meta();
        Term.Atom copy = meta.createAtom(atom);
        copy.bind(term.parentObject());
        copy.setValue(atom.getValue());
        term.term(index, copy);
    }
}

