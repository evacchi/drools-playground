package io.github.evacchi.m;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        PersonTerm paul = new PersonTerm();
        paul.setName("Paul");
        paul.setAge(50);

        PersonTerm X = new PersonTerm();
        PersonMeta m = X.$meta();
        PersonMeta.Sentence sentence = X.$sentence();
        Term[] terms = sentence.terms();
        terms[m.index.name] = m.variable();
        terms[m.index.age] = m.variable();
        sentence.bind(X);

        SubstitutionSet r = unify(paul.$sentence(), X.$sentence(), new SubstitutionSet());
    }

    private static SubstitutionSet unify(Term x, Term y, SubstitutionSet s) {
        if (x instanceof Term.Variable) return unify((Term.Variable) x, y, s);
        if (x instanceof Term.Atom) return unify((Term.Atom) x, y, s);
        if (x instanceof Term.Sentence) return unify((Term.Sentence) x, y, s);
    }
    private static SubstitutionSet unify(Term.Variable x, Term y, SubstitutionSet s) {
        if (x == y) return s;
        if (s.isBound(x)) unify(s.get(x), y, s);
        return new SubstitutionSet(s).put(x, y);
    }
    private static SubstitutionSet unify(Term.Atom x, Term y, SubstitutionSet s) {
        if (x == y) return new SubstitutionSet(s);
        if (y instanceof Term.Variable) unify(y, x, s);
        return null;
    }
    private static SubstitutionSet unify(Term.Sentence x, Term y, SubstitutionSet s) {

        return null;
    }



    static Term replaceVariables(Term t, SubstitutionSet s) {
        if (t instanceof Term.Variable) return replaceVariables((Term.Variable) t, s);
    }

    static Term replaceVariables(Term.Variable t, SubstitutionSet s) {
        if (s.isBound(t)) return replaceVariables(s.get(t), s);
        else return t;
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