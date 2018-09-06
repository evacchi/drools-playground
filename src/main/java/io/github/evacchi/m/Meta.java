package io.github.evacchi.m;

public interface Meta {
    Term.Sentence sentence();
    static Term.Sentence sentence(Meta term) {
        return term.sentence();
    }
}
