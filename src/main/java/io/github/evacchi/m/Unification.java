package io.github.evacchi.m;

import java.util.Optional;

public class Unification {

    private final Object XX;
    private final Term.Meta<?, ?, ?> meta;

    public Unification(Object goal, Term.Meta<?, ?, ?> m) {
        this.XX = goal;
        meta = m;
    }



    public Optional<Term> query(Term.Sentence k, Term.Sentence goal) {
        return Optional.ofNullable(unify(k, goal, new SubstitutionSet()))
                .map(s -> replaceVariables(goal, s));
    }

    private SubstitutionSet unify(Term x, Term y, SubstitutionSet s) {
        if (x instanceof Term.Variable) return unifyVariable((Term.Variable) x, y, s);
        if (x instanceof Term.Atom) return unifyAtom((Term.Atom) x, y, s);
        if (x instanceof Term.Sentence) return unifySentence((Term.Sentence) x, y, s);
        throw new IllegalArgumentException();
    }
    private SubstitutionSet unifyVariable(Term.Variable x, Term y, SubstitutionSet s) {
//        if (x == y) return s;
        if (x.equals(y)) return s;
        if (s.isBound(x)) unify(s.get(x), y, s);
        return new SubstitutionSet(s).put(x, y);
    }
    private SubstitutionSet unifyAtom(Term.Atom x, Term y, SubstitutionSet s) {
//        if (x == y) return new SubstitutionSet(s);
        if (x.equals(y)) return new SubstitutionSet(s);
        if (y instanceof Term.Variable) return unify(y, x, s);
        return null;
    }
    private SubstitutionSet unifySentence(Term.Sentence x, Term y, SubstitutionSet s) {
        if (y instanceof Term.Sentence) {
            Term.Sentence ss = (Term.Sentence) y;
            // cannot unify when arity differs
            if (x.size() != ss.size()) {
                return null;
            }

            SubstitutionSet sNew = new SubstitutionSet(s);
            for (int i = 0; i < x.size(); i++) {
                sNew = unify(x.term(i), ss.term(i), sNew);
                if (sNew == null) {
                    return null;
                }
            }
            return sNew;
        }

        if (y instanceof Term.Variable) {
            return unify(x, y, s);
        }

        return null;
    }



    Term replaceVariables(Term t, SubstitutionSet s) {
        if (t instanceof Term.Variable) return replaceVariables((Term.Variable) t, s);
        if (t instanceof Term.Atom)     return replaceVariables((Term.Atom) t, s);
        if (t instanceof Term.Sentence) return replaceVariables((Term.Sentence) t, s);
        throw new IllegalArgumentException();
    }

    Term replaceVariables(Term.Variable t, SubstitutionSet s) {
        if (s.isBound(t)) return replaceVariables(s.get(t), s);
        else return t;
    }

    Term replaceVariables(Term.Atom t, SubstitutionSet s) {
        Term.Atom a = meta.atom(t);
        a.bind(XX);
        a.setValue(t.getValue());
        return a;
    }

    Term replaceVariables(Term.Sentence s, SubstitutionSet ss) {
        for (int i = 0; i < s.size(); i++) {
            s.term(i, replaceVariables(s.term(i), ss));
        }
        return s;
    }
}
