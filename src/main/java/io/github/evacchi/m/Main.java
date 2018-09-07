package io.github.evacchi.m;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        PersonTerm paul = new PersonTerm();
        paul.setName("Paul");
        paul.setAge(50);
        paul.$sentence().bind(paul);

        PersonTerm X = new PersonTerm();
        PersonMeta m = X.$meta();
        PersonMeta.Sentence sentence = X.$sentence();
        Term[] terms = sentence.terms();
        terms[m.index.name] = m.variable();
        terms[m.index.age] = m.variable();
        sentence.bind(X);

        SubstitutionSet s = unify(paul.$sentence(), X.$sentence(), new SubstitutionSet());
        Term r = replaceVariables(X.$sentence(), s);

        System.out.println(r);
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
            if (x.terms().length != ss.terms().length) {
                return null;
            }

            SubstitutionSet sNew = new SubstitutionSet(s);
            for (int i = 0; i < x.terms().length; i++) {
                sNew = unify(x.terms()[i], ss.terms()[i], sNew);
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
