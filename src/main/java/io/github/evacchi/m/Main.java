package io.github.evacchi.m;

public class Main {


    public static void main(String[] args) {
        PersonMeta m = PersonMeta.Instance;

        // person(paul, X)
        PersonObject paul = m.createPerson();
        paul.setName("Paul");
        m.sentenceOf(paul).terms(
                m.createAtom(),
                m.createVariable());

        // person(Y, 50)
        PersonObject X = m.createPerson();
        X.setAge(50);
        m.sentenceOf(X).terms(
                m.createVariable(),
                m.createAtom());

        new Unification().unify(
                m.sentenceOf(paul),
                m.sentenceOf(X));

        System.out.println(paul);
        System.out.println(m.sentenceOf(paul));

        System.out.println(X);
        System.out.println(m.sentenceOf(X));

    }


}
