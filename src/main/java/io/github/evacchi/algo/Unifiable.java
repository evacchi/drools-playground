package io.github.evacchi.algo;

interface Unifiable extends DroologExpression {

    SubstitutionSet unify(Unifiable p, SubstitutionSet s);

    Unifiable replaceVariables(SubstitutionSet s);
}