package io.github.evacchi.m;

import java.util.Arrays;
import java.util.Objects;

public abstract class Person {
    protected String name;
    protected int age;
}

final class PersonMeta {

    public final Index index = new Index();

    public Atom atom() {
        return new Atom();
    }

    public Variable variable() {
        return new Variable();
    }

    public Sentence sentence(Term name, Term age) {
        return new Sentence(name, age);
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
                case Index.name:
                    parent.setName((String) value);
                case Index.age:
                    parent.setAge((int) value);
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

        public void bind(Object o) {
            PersonTerm tt = (PersonTerm) o;
            Term[] terms = tt.$sentence().terms();
            for (int i = 0; i < terms.length; i++) {
                Term t = terms[i];
                if (t instanceof PersonMeta.Atom) ((PersonMeta.Atom) t).sentenceIndex = i;
                t.bind(o);
            }
        }

        @Override
        public String toString() {
            return Arrays.asList(terms).toString();
        }
    }
}

final class PersonTerm extends Person implements Term.Meta {

    private final PersonMeta $meta = new PersonMeta();
    private final PersonMeta.Sentence $sentence;

    {
        PersonMeta.Sentence sentence =
                $meta.sentence($meta.atom(), $meta.atom());
//        sentence.bind(this);
        this.$sentence = sentence;
    }


    @Override
    public PersonMeta $meta() {
        return $meta;
    }


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
}