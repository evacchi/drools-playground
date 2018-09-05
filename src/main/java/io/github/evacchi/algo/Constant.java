package io.github.evacchi.algo;

final class Constant implements Unifiable {

    private final String name;

    public Constant(String name) {
        this.name = name;
    }

    @Override
    public SubstitutionSet unify(Unifiable e, SubstitutionSet s) {
        if (this == e) {
            return new SubstitutionSet(s);
        }
        if (e instanceof Variable) {
            return e.unify(this, s);
        }
        return null;
    }

    @Override
    public Unifiable replaceVariables(SubstitutionSet s) {
        return this;
    }

    @Override
    public String toString() {
        return "C:" + name;
    }
}
