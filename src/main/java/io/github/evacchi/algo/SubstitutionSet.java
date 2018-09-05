package io.github.evacchi.algo;

import java.util.HashMap;

final class SubstitutionSet {

    private final HashMap<Variable, Unifiable> bindings;

    SubstitutionSet(HashMap<Variable, Unifiable> bindings) {
        this.bindings = bindings;
    }

    public SubstitutionSet(SubstitutionSet other) {
        this(other.bindings);
    }

    public SubstitutionSet() {
        this(new HashMap<>());
    }

    public SubstitutionSet put(Variable v, Unifiable exp) {
        this.bindings.put(v, exp);
        return this;
    }

    public Unifiable get(Variable v) {
        return this.bindings.get(v);
    }

    public boolean isBound(Variable v) {
        return this.bindings.containsKey(v);
    }

    @Override
    public String toString() {
        return "Bindings:" + bindings;
    }
}
