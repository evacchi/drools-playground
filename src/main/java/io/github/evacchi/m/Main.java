package io.github.evacchi.m;

import java.util.HashMap;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        PersonMeta m = PersonMeta.Instance;

        PersonTerm paul = new PersonTerm();
        paul.setName("Paul");
        paul.setAge(50);

        PersonTerm X = new PersonTerm();
        X.setAge(50);
        X.$sentence().terms(
                m.variable(),
                m.atom());


        query(paul.$sentence(), X.$sentence())
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("False"));

        System.out.println(X);
        System.out.println(X.$sentence());
    }

    public static Optional<Term> query(Term.Sentence k, Term.Sentence goal) {
        return Optional.ofNullable(unify(k, goal, new SubstitutionSet()))
                .map(s -> replaceVariables(goal, s));
    }

    private static SubstitutionSet unify(Term x, Term y, SubstitutionSet s) {
        if (x instanceof Term.Variable) return unifyVariable((Term.Variable) x, y, s);
        if (x instanceof Term.Atom) return unifyAtom((Term.Atom) x, y, s);
        if (x instanceof Term.Sentence) return unifySentence((Term.Sentence) x, y, s);
        throw new IllegalArgumentException();
    }
    private static SubstitutionSet unifyVariable(Term.Variable x, Term y, SubstitutionSet s) {
//        if (x == y) return s;
        if (x.equals(y)) return s;
        if (s.isBound(x)) unify(s.get(x), y, s);
        return new SubstitutionSet(s).put(x, y);
    }
    private static SubstitutionSet unifyAtom(Term.Atom x, Term y, SubstitutionSet s) {
//        if (x == y) return new SubstitutionSet(s);
        if (x.equals(y)) return new SubstitutionSet(s);
        if (y instanceof Term.Variable) return unify(y, x, s);
        return null;
    }
    private static SubstitutionSet unifySentence(Term.Sentence x, Term y, SubstitutionSet s) {
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



    static Term replaceVariables(Term t, SubstitutionSet s) {
        if (t instanceof Term.Variable) return replaceVariables((Term.Variable) t, s);
        if (t instanceof Term.Atom)     return replaceVariables((Term.Atom) t, s);
        if (t instanceof Term.Sentence) return replaceVariables((Term.Sentence) t, s);
        throw new IllegalArgumentException();
    }

    static Term replaceVariables(Term.Variable t, SubstitutionSet s) {
        if (s.isBound(t)) return replaceVariables(s.get(t), s);
        else return t;
    }

    static Term replaceVariables(Term.Atom t, SubstitutionSet s) {
        return t;
    }

    static Term replaceVariables(Term.Sentence s, SubstitutionSet ss) {
        Term[] ts = s.terms();
        for (int i = 0; i < ts.length; i++) {
            ts[i] = replaceVariables(ts[i], ss);
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
