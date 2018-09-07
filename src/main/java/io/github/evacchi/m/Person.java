package io.github.evacchi.m;

import java.util.Arrays;
import java.util.Objects;

public abstract class Person {
    protected String name;
    protected int age;
}

final class PersonMeta implements Term.Meta<
        PersonMeta.Atom, PersonMeta.Variable, PersonMeta.Sentence> {

    public static final PersonMeta Instance = new PersonMeta();

    public PersonObject createPerson() {
        return new PersonTerm();
    }
    public Sentence sentenceOf(PersonObject term) {
        return ((PersonTerm)term).$sentence();
    }

    @Override
    public Atom createAtom() {
        return new Atom();
    }

    @Override
    public Atom createAtom(Term.Atom orig) {
        Atom o = (Atom) orig;
        Atom a = new Atom();
        a.index = o.index;
        a.parent = o.parent;
        return a;
    }

    @Override
    public Variable createVariable() {
        return new Variable();
    }

    @Override
    public Sentence createSentence() {
        return new Sentence();
    }

    @Override
    public Atom createAtom(Term.Variable orig) {
        Variable o = (Variable) orig;
        Atom a = new Atom();
        a.index = o.index;
        a.parent = o.parent;
        return a;
    }

    final class Index {
        public static final int $predicate = 0;
        public static final int name = 1;
        public static final int age = 2;
    }

    abstract static class AbstractTerm implements Term {
        int index = -1;
        PersonTerm parent;

        public PersonTerm parentObject() {
            return parent;
        }

        @Override
        public void bind(Object value) {
            this.parent = (PersonTerm) value;
        }

        @Override
        public void setIndex(int index) {
            this.index = index;
        }

        @Override
        public int getIndex() {
            return this.index;
        }


    }

    final static class Atom extends AbstractTerm implements Term.Atom {

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

    final static class Variable extends AbstractTerm implements Term.Variable {
    }

    final static class Sentence extends AbstractTerm implements Term.Sentence {
        private final Term[] terms;

        public Sentence() {
            this.terms = new Term[] {
                    Instance.createAtom(),
                    Instance.createAtom(),
                    Instance.createAtom(),
            };
        }

        @Override
        public Term[] terms() {
            return terms;
        }

        @Override
        public Term term(int i) {
            return terms[i];
        }

        public PersonMeta.Sentence terms(Term name, Term age) {
            term(Index.name, name);
            term(Index.age, age);
            return this;
        }

        @Override
        public PersonMeta.Sentence term(int i, Term t) {
            terms[i] = t;
            t.bind(parent);
            t.setIndex(i);
            return this;
        }

        @Override
        public int size() {
            return terms.length;
        }

        public void bind(Object o) {
            PersonTerm tt = (PersonTerm) o;
            this.parent = tt;
            tt.$sentence = this;
            for (int i = 0; i < terms.length; i++) {
                Term t = terms[i];
                t.setIndex(i);
                t.bind(o);
            }
        }

        @Override
        public PersonMeta meta() {
            return PersonMeta.Instance;
        }

        @Override
        public String toString() {
            return Arrays.asList(terms).toString();
        }
    }
}

final class PersonTerm extends Person implements Term.ObjectTerm,
                                                 PersonObject {

    PersonMeta.Sentence $sentence;

    {
        PersonMeta.Sentence sentence =
                PersonMeta.Instance.createSentence();
        sentence.bind(this);
    }

    @Override
    public PersonMeta.Sentence $sentence() {
        return $sentence;
    }

    private String name;
    private int age;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "PersonTerm{" +
                "name='" + name + '\'' +
                ", age=" + age +
                "} ";
    }
}