package io.github.evacchi.m;

import java.util.Objects;

final class PersonMeta extends AbstractMeta<
        PersonMeta.Atom, PersonMeta.Variable, PersonMeta.Sentence> {

    public static final PersonMeta Instance = new PersonMeta();

    public PersonObject createPerson() {
        return new PersonTerm();
    }
    public Sentence sentenceOf(PersonObject term) {
        return ((PersonTerm)term).$getSentence();
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
    public Sentence createSentence() {
        return new Sentence();
    }

    final static class Index {
        public static final int $predicate = 0;
        public static final int name = 1;
        public static final int age = 2;
    }

    final static class Atom extends AbstractTerm<PersonTerm> implements Term.Atom {

        @Override
        public void setValue(Object value) {
            switch (index) {
                case Index.$predicate:
                    if (parent.getClass() != value) {
                        throw new IllegalArgumentException();
                    }
                    return;
                case Index.name:
                    parent.setName((String) value);
                    return;
                case Index.age:
                    parent.setAge((int) value);
                    return;
                default:
                    throw new ArrayIndexOutOfBoundsException(index);
            }
        }

        @Override
        public Object getValue() {
            switch (index) {
                case Index.$predicate:
                    return parent.getClass();
                case Index.name:
                    return parent.getName();
                case Index.age:
                    return parent.getAge();
                default:
                    throw new ArrayIndexOutOfBoundsException(index);
            }
        }

        @Override
        public String toString() {
            return getValue().toString();
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Atom &&
                    Objects.equals(getValue(), ((Atom) obj).getValue());
        }
    }

    final static class Variable extends AbstractTerm<PersonTerm> implements Term.Variable {}

    final static class Sentence extends AbstractSentence<PersonTerm> implements Term.Sentence {

        public Sentence() {
            super(new Term[] {
                    Instance.createAtom(),
                    Instance.createAtom(),
                    Instance.createAtom(),
            });
        }

        public PersonMeta.Sentence terms(Term name, Term age) {
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