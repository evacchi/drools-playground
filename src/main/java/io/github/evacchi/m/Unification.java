package io.github.evacchi.m;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class Unification {

    private final Term.Meta<?, ?, ?> meta;
    private Term.ObjectTerm t1;
    private Term.ObjectTerm t2;

    public Unification(Term.Meta<?, ?, ?> m, Term.ObjectTerm t1, Term.ObjectTerm t2) {
        meta = m;
        this.t1 = t1;
        this.t2 = t2;
    }

    public void unify() {
        ArrayList<Term> leftList = new ArrayList<>();
        ArrayList<Term> rightList = new ArrayList<>();

        Term.Sentence left = t1.$sentence();
        Term.Sentence right = t2.$sentence();

        if (left.size() != right.size()) {
            return;
        }

        linearizeSentence(left, leftList);
        linearizeSentence(right, rightList);

        if (leftList.size() != rightList.size()) {
            return;
        }

        for (int i = 0; i < leftList.size(); i++) {
            _unify(i, leftList, rightList);
        }


        System.out.println(leftList);
        System.out.println(rightList);

        return;
    }

    private void _unify(int i, ArrayList<Term> leftList, ArrayList<Term> rightList) {
        Term lt = leftList.get(i), rt = rightList.get(i);
        if (lt instanceof Term.Atom && rt instanceof Term.Atom) {
            if (!lt.equals(rt)) throw new IllegalArgumentException();
        } else if (lt instanceof Term.Atom && rt instanceof Term.Variable) {
            copyAtom(rightList, i, (Term.Atom) lt, t1);
        } else if (lt instanceof Term.Variable && rt instanceof Term.Atom) {
            copyAtom(leftList, i, (Term.Atom) rt, t2);
        } else if (lt instanceof Term.Variable && rt instanceof Term.Variable) {
            Term.Atom a1 = meta.atom((Term.Variable) lt);
            a1.bind(t1);
            leftList.set(i, a1);
            Term.Atom a2 = meta.atom((Term.Variable) rt);
            a2.bind(t2);
            rightList.set(i, a2);
        } else {
            throw new IllegalArgumentException();
        }

    }


    private void copyAtom(ArrayList<Term> terms, int i, Term.Atom orig, Object object) {
        Term.Atom ra = meta.atom(orig);
        ra.bind(object);
        ra.setValue(orig.getValue());
        terms.set(i, ra);
    }

    private void linearizeSentence(Term.Sentence sentence, ArrayList<Term> acc) {
        for (Term term : sentence.terms()) {
            linearizeTerm(term, acc);
        }
    }

    private void linearizeTerm(Term term, ArrayList<Term> acc) {
        if (term instanceof Term.Atom) acc.add(term);
        if (term instanceof Term.Variable) acc.add(term);
        if (term instanceof Term.Sentence) linearizeSentence((Term.Sentence)term, acc);
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
