package io.github.evacchi.m;

import java.util.Arrays;
import java.util.Objects;

public abstract class Person {
    protected String name;
    protected int age;
}

final class PersonMeta implements Term.Meta<PersonMeta.Atom, PersonMeta.Variable, PersonMeta.Sentence> {

    public static final PersonMeta Instance = new PersonMeta();

    @Override
    public Atom atom() {
        return new Atom();
    }

    @Override
    public Atom atom(Term.Atom orig) {
        Atom o = (Atom) orig;
        Atom a = new Atom();

        a.sentenceIndex = o.sentenceIndex;
        a.parent = o.parent;
        return a;
    }

    @Override
    public Variable variable() {
        return new Variable();
    }

    @Override
    public Sentence sentence(Term name, Term age) {
        return new Sentence(name, age);
    }

    static PersonMeta.Sentence ground(PersonTerm t) {
        PersonMeta $m = PersonMeta.Instance;
        PersonMeta.Sentence s = $m.sentence(
                $m.atom(),
                $m.atom());
        s.bind(t);
        return s;
    }

    final class Index {
        public static final int $predicate = 0;
        public static final int name = 1;
        public static final int age = 2;
    }

    final static class Atom implements Term.Atom {

        int sentenceIndex = -1;
        PersonTerm parent;

        @Override
        public void setValue(Object value) {
            switch (sentenceIndex) {
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
                    throw new ArrayIndexOutOfBoundsException(sentenceIndex);
            }
        }

        @Override
        public Object getValue() {
            switch (sentenceIndex) {
                case Index.$predicate:
                    return parent.getClass();
                case Index.name:
                    return parent.getName();
                case Index.age:
                    return parent.getAge();
                default:
                    throw new ArrayIndexOutOfBoundsException(sentenceIndex);
            }
        }

        @Override
        public void bind(Object value) {
            this.parent = (PersonTerm) value;
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

    final static class Variable implements Term.Variable {

        @Override
        public void bind(Object value) {

        }
    }

    final static class Sentence implements Term.Sentence {
        private final Term[] terms;
        PersonTerm parent;

        public Sentence(Term name, Term age) {
            PersonMeta.Atom head = new PersonMeta.Atom();
            this.terms = new Term[] {
                    head,
                    name,
                    age
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
            term(1, name);
            term(2, age);
            return this;
        }



        @Override
        public PersonMeta.Sentence term(int i, Term t) {
            terms[i] = t;
            t.bind(parent);
            if (t instanceof PersonMeta.Atom) {
                ((PersonMeta.Atom) t).sentenceIndex = i;
            }
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
                if (t instanceof PersonMeta.Atom) {
                    ((PersonMeta.Atom) t).sentenceIndex = i;
                }

                t.bind(o);
            }
        }

        @Override
        public String toString() {
            return Arrays.asList(terms).toString();
        }
    }
}

final class PersonTerm extends Person implements Term.ObjectTerm {

    PersonMeta.Sentence $sentence = PersonMeta.ground(this);

    @Override
    public PersonMeta.Sentence $sentence() {
        return $sentence;
    }

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "PersonTerm{" +
                "name='" + name + '\'' +
                ", age=" + age +
                "} " + super.toString();
    }
}