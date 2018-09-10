package io.github.evacchi.meta;

import io.github.evacchi.meta.lib.Generated;
import io.github.evacchi.meta.lib.Term;

// the private/internal interface with the getters for
@Generated
class PersonTerm extends PersonObject implements Term.ObjectTerm {

    PersonMeta.Structure $term = PersonMeta.Instance.createCompoundTerm();

    {
        $term.bind(this);
    }

    @Override
    public PersonMeta.Structure $getTerm() {
        return $term;
    }

    @Override
    public void $getTerm(Term.Structure $structure) {
        this.$term = (PersonMeta.Structure) $structure;
    }

}