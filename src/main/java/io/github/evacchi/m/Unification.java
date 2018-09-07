package io.github.evacchi.m;

public class Unification {

    public void unify(Term.Sentence left, Term.Sentence right) {
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
            } else if (lt instanceof Term.Sentence && rt instanceof Term.Variable) {
                copySentence((Term.Sentence) lt, right, i);
            } else if (lt instanceof Term.Variable && rt instanceof Term.Sentence) {
                copySentence((Term.Sentence) rt, left, i);
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

    private void copySentence(Term.Sentence sentence, Term.Sentence parent, int index) {
        // fixme not working/tested yet
        Term.Meta<?,?,?> meta = sentence.meta();
        Term.Sentence s = meta.createSentence();
        s.bind(parent.parentObject());
        for (int i = 0; i < sentence.size(); i++) {
            sentence.term(i, meta.createVariable());
        }
        parent.term(index, s);

    }

    private Term.Atom createAtom(Term.Variable variable, Term.Sentence sentence, int index) {
        Term.Meta<?,?,?> meta = sentence.meta();
        Term.Atom a1 = meta.createAtom(variable);
        a1.bind(sentence.parentObject());
        sentence.term(index, a1);
        return a1;
    }

    private void copyAtom(Term.Atom atom, Term.Sentence sentence, int index) {
        Term.Meta<?,?,?> meta = sentence.meta();
        Term.Atom copy = meta.createAtom(atom);
        copy.bind(sentence.parentObject());
        copy.setValue(atom.getValue());
        sentence.term(index, copy);
    }
}

