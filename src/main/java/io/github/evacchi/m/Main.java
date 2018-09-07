package io.github.evacchi.m;

import java.util.HashMap;

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

        Unification unification = new Unification(X, m);
        unification.query(paul.$sentence(), X.$sentence())
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("False"));

        System.out.println(X);
        System.out.println(X.$sentence());

        Term.Atom atom = (Term.Atom) X.$sentence().term(1);
        atom.setValue("John");

        System.out.println(X);
        System.out.println(X.$sentence());
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
