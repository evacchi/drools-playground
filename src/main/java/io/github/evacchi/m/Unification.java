package io.github.evacchi.m;

import java.util.HashMap;
import java.util.Optional;

public class Unification {

    private final Object goal;
    private final Term.Meta<?, ?, ?> meta;

    public Unification(Object goal, Term.Meta<?, ?, ?> m) {
        this.goal = goal;
        meta = m;
    }

    public Optional<Term> query(Term.Sentence k, Term.Sentence goal) {
        return Optional.ofNullable(unify(k, goal, new SubstitutionSet()))
                .map(s -> replaceVariables(goal, s));
    }

    private SubstitutionSet unify(Term t1, Term t2, SubstitutionSet s) {
        if (t1 instanceof Term.Variable) return unifyVariable((Term.Variable) t1, t2, s);
        if (t1 instanceof Term.Atom) return unifyAtom((Term.Atom) t1, t2, s);
        if (t1 instanceof Term.Sentence) return unifySentence((Term.Sentence) t1, t2, s);
        throw new IllegalArgumentException();
    }
    private SubstitutionSet unifyVariable(Term.Variable v, Term t, SubstitutionSet s) {
        if (v.equals(t)) return s;
        if (s.isBound(v)) unify(s.get(v), t, s);
        return //new SubstitutionSet(s)
                s.put(v, t);
    }
    private SubstitutionSet unifyAtom(Term.Atom a, Term t, SubstitutionSet s) {
        if (a.equals(t)) return s;//new SubstitutionSet(s);
        if (t instanceof Term.Variable) return unify(t, a, s);
        return null;
    }
    private SubstitutionSet unifySentence(Term.Sentence s1, Term t, SubstitutionSet s) {
        if (t instanceof Term.Sentence) {
            Term.Sentence s2 = (Term.Sentence) t;
            // cannot unify when arity differs
            if (s1.size() != s2.size()) {
                return null;
            }

            SubstitutionSet sNew = s;//new SubstitutionSet(s);
            for (int i = 0; i < s1.size(); i++) {
                sNew = unify(s1.term(i), s2.term(i), sNew);
                if (sNew == null) {
                    return null;
                }
            }
            return sNew;
        }

        if (t instanceof Term.Variable) {
            return unify(s1, t, s);
        }

        return null;
    }



    Term replaceVariables(Term t, SubstitutionSet s) {
        if (t instanceof Term.Variable) return replaceVariables((Term.Variable) t, s);
        if (t instanceof Term.Atom)     return replaceVariables((Term.Atom) t, s);
        if (t instanceof Term.Sentence) return replaceVariables((Term.Sentence) t, s);
        throw new IllegalArgumentException();
    }

    Term replaceVariables(Term.Variable v, SubstitutionSet s) {
        if (s.isBound(v)) return replaceVariables(s.get(v), s);
        else return v;
    }

    Term replaceVariables(Term.Atom a, SubstitutionSet s) {
        Term.Atom a2 = meta.atom(a);
        a2.bind(goal);
        a2.setValue(a.getValue());
        return a2;
    }

    Term replaceVariables(Term.Sentence s, SubstitutionSet ss) {
        for (int i = 0; i < s.size(); i++) {
            s.term(i, replaceVariables(s.term(i), ss));
        }
        return s;
    }
}


final class SubstitutionSet {

    private final HashMap<Term.Variable, Term> bindings;

    SubstitutionSet(HashMap<Term.Variable, Term> bindings) {
        this.bindings = bindings;
    }

    public SubstitutionSet(SubstitutionSet other) {
        this(other.bindings);
    }

    public SubstitutionSet() {
        this(new HashMap<>());
    }

    public SubstitutionSet put(Term.Variable v, Term exp) {
        this.bindings.put(v, exp);
        return this;
    }

    public Term get(Term.Variable v) {
        return this.bindings.get(v);
    }

    public boolean isBound(Term.Variable v) {
        return this.bindings.containsKey(v);
    }

    @Override
    public String toString() {
        return "Bindings:" + bindings;
    }
}
