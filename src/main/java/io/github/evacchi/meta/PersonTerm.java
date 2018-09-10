package io.github.evacchi.meta;

import io.github.evacchi.meta.lib.Generated;
import io.github.evacchi.meta.lib.Term;

// the private/internal interface with the getters for structure representation
@Generated
class PersonTerm extends PersonObject implements Term.ObjectTerm {

    PersonMeta.Structure $struct = PersonMeta.Instance.createStructure();

    {
        $struct.bind(this);
    }

    @Override
    public PersonMeta.Structure $getStructure() {
        return $struct;
    }

    @Override
    public void $setStructure(Term.Structure $structure) {
        this.$struct = (PersonMeta.Structure) $structure;
    }

}