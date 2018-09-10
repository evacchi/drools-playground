package io.github.evacchi.meta;

import io.github.evacchi.meta.lib.AbstractAtom;
import io.github.evacchi.meta.lib.AbstractMeta;
import io.github.evacchi.meta.lib.AbstractCompound;
import io.github.evacchi.meta.lib.AbstractTerm;
import io.github.evacchi.meta.lib.Generated;
import io.github.evacchi.meta.lib.Term;

@Generated
final class PersonMeta extends AbstractMeta<
        PersonMeta.Atom, PersonMeta.Variable, PersonMeta.Compound> {

    public static final PersonMeta Instance = new PersonMeta();

    public PersonObject createPerson() {
        return new PersonTerm();
    }
    public Compound termOf(PersonObject term) {
        return ((PersonTerm)term).$getTerm();
    }

    @Override
    public Atom createAtom() {
        return new Atom();
    }

    @Override
    public Variable createVariable() {
        return new Variable();
    }

    @Override
    public Compound createCompoundTerm() {
        return new Compound();
    }

    final static class Index {
        public static final int $predicate = 0;
        public static final int name = 1;
        public static final int age = 2;
    }

    final static class Atom extends AbstractAtom<PersonTerm> implements Term.Atom {

        @Override
        public void setValue(Object value) {
            switch (getIndex()) {
                case Index.$predicate:
                    if (parentObject().getClass() != value) {
                        throw new IllegalArgumentException();
                    }
                    return;
                case Index.name:
                    parentObject().setName((String) value);
                    return;
                case Index.age:
                    parentObject().setAge((int) value);
                    return;
                default:
                    throw new ArrayIndexOutOfBoundsException(getIndex());
            }
        }

        @Override
        public Object getValue() {
            switch (getIndex()) {
                case Index.$predicate:
                    return parentObject().getClass();
                case Index.name:
                    return parentObject().getName();
                case Index.age:
                    return parentObject().getAge();
                default:
                    throw new ArrayIndexOutOfBoundsException(getIndex());
            }
        }

    }

    final static class Variable extends AbstractTerm<PersonTerm> implements Term.Variable {}

    final static class Compound extends AbstractCompound<PersonTerm> implements Term.Compound {

        public Compound() {
            super(new Term[] {
                    Instance.createAtom(),
                    Instance.createAtom(),
                    Instance.createAtom(),
            });
        }

        public PersonMeta.Compound terms(Term name, Term age) {
            term(Index.name, name);
            term(Index.age, age);
            return this;
        }

        @Override
        public PersonMeta meta() {
            return PersonMeta.Instance;
        }

    }
}