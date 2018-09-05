package io.github.evacchi.algo;

final class Variable implements Unifiable {

    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public SubstitutionSet unify(Unifiable e, SubstitutionSet s) {
        if (this == e) {
            return s;
        }
        if (s.isBound(this)) {
            s.get(this).unify(e, s);
        }
        return new SubstitutionSet(s)
                .put(this, e);
    }

    @Override
    public Unifiable replaceVariables(SubstitutionSet s) {
        if (s.isBound(this)) {
            return s.get(this).replaceVariables(s);
        } else {
            return this;
        }
    }

    @Override
    public String toString() {
        return "V:" + name;
    }
}
