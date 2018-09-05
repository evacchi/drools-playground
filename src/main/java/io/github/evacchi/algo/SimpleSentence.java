package io.github.evacchi.algo;

import java.util.stream.Collectors;
import java.util.stream.Stream;

final class SimpleSentence implements Unifiable {

    private final Unifiable[] terms;

    SimpleSentence(Constant predicate, Unifiable... args) {
        this.terms = new Unifiable[args.length + 1];
        terms[0] = predicate;
        System.arraycopy(args, 0, terms, 1, args.length);
    }

    SimpleSentence(Unifiable... args) {
        this.terms = args;
    }

    public int length() {
        return terms.length;
    }

    public Unifiable termAt(int i) {
        return terms[i];
    }

    @Override
    public SubstitutionSet unify(Unifiable e, SubstitutionSet s) {
        if (e instanceof SimpleSentence) {
            SimpleSentence ss = (SimpleSentence) e;
            // cannot unify when arity differs
            if (this.length() != ss.length()) {
                return null;
            }

            SubstitutionSet sNew = new SubstitutionSet(s);
            for (int i = 0; i < this.length(); i++) {
                sNew = this.termAt(i).unify(ss.termAt(i), sNew);
                if (sNew == null) {
                    return null;
                }
            }
            return sNew;
        }

        if (e instanceof Variable) {
            return e.unify(this, s);
        }

        return null;
    }

    public Unifiable replaceVariables(SubstitutionSet s) {
        Unifiable[] newTerms = new Unifiable[terms.length];
        for (int i = 0; i < length(); i++) {
            newTerms[i] = terms[i].replaceVariables(s);
        }
        return new SimpleSentence(newTerms);
    }

    @Override
    public String toString() {
        return Stream.of(terms)
                .map(Unifiable::toString)
                .collect(Collectors.joining(",", "(", ")"));
    }
}