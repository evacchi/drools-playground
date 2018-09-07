package io.github.evacchi.m;

import java.util.HashMap;

public class Main {


    public static void main(String[] args) {
        PersonMeta m = PersonMeta.Instance;

        PersonTerm paul = new PersonTerm();
        paul.setName("Paul");
        paul.$sentence().terms(
                m.atom(),
                m.variable()
        );

        PersonTerm X = new PersonTerm();
        X.setAge(50);
        X.$sentence().terms(
                m.variable(),
                m.atom());

        Unification unification = new Unification(m, paul, X);
        unification.unify();

        System.out.println(paul);
        System.out.println(paul.$sentence());

        System.out.println(X);
        System.out.println(X.$sentence());

    }


}
