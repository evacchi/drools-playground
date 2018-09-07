package io.github.evacchi.m;

import java.util.HashMap;

public class Main {


    public static void main(String[] args) {
        PersonMeta m = PersonMeta.Instance;

        PersonTerm paul = new PersonTerm();
        paul.setName("Paul");
        paul.setAge(50);

        PersonTerm X = new PersonTerm();
        X.setAge(510);
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
