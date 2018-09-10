package io.github.evacchi.meta;

import io.github.evacchi.meta.lib.Generated;
import io.github.evacchi.meta.lib.Term;

// the private/internal interface with the getters for
@Generated
class PersonTerm extends PersonObject implements Term.ObjectTerm {

    PersonMeta.Compound $term = PersonMeta.Instance.createCompoundTerm();

    {
        $term.bind(this);
    }

    @Override
    public PersonMeta.Compound $getTerm() {
        return $term;
    }

    @Override
    public void $getTerm(Term.Compound $compound) {
        this.$term = (PersonMeta.Compound) $compound;
    }

}