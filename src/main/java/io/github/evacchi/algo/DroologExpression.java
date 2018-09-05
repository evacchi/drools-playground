package io.github.evacchi.algo;

import java.util.stream.Stream;

public interface DroologExpression {

    static final Constant
            friend = new Constant("friend"),
            bill = new Constant("bill"),
            george = new Constant("george"),
            kate = new Constant("kate"),
            merry = new Constant("merry");
    static final Variable
            X = new Variable("X"),
            Y = new Variable("Y");

    public static Stream<Unifiable> expressions() {
        return Stream.of(
                new SimpleSentence(friend, bill, george),
                new SimpleSentence(friend, bill, kate),
                new SimpleSentence(friend, bill, merry),
                new SimpleSentence(friend, george, bill),
                new SimpleSentence(friend, george, kate),
                new SimpleSentence(friend, kate, merry));
    }

    public static void main(String[] args) {
        test(expressions(), new SimpleSentence(friend, X, Y));
        test(expressions(), new SimpleSentence(friend, bill, Y));
    }

    static void test(Stream<Unifiable> unifiables, SimpleSentence goal) {
        System.out.println("Goal = " + goal);
        unifiables.map(e -> e.unify(goal, new SubstitutionSet())).forEach(s -> {
            if (s != null) {
                System.out.println(
                        goal.replaceVariables(s));
            } else {
                System.out.println("False");
            }
        });
    }
}
