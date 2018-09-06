package io.github.evacchi.meta;

class Main {

    public static void main(String[] args) {
        PersonTerm paul = new PersonTerm("paul", 10);
        PersonMeta meta = paul.meta();
    }
}

@interface Imperfect {}
@interface ObjectTerm {}

@ObjectTerm @Imperfect
public interface Person {
    String name();
    int age();
}

interface Term {
    final class Atom implements Term {}
    final class Variable implements Term {}
    final class Sentence implements Term {}
}

interface Meta<T> {
    T meta();
}

final class PersonMeta {
    final static class Attribute {
        private float degree;
        private Term term;

        public float getDegree() {
            return degree;
        }

        public void setDegree(float degree) {
            this.degree = degree;
        }

        public Term getTerm() {
            return term;
        }

        public void setTerm(Term term) {
            this.term = term;
        }
    }

    private final Attribute name = new Attribute();
    private final Attribute age  = new Attribute();

    public Attribute getName() {
        return name;
    }

    public Attribute getAge() {
        return age;
    }
}

class PersonTerm implements Person, Meta<PersonMeta> {
    private final String name;
    private final int age;
    private final PersonMeta personMeta = new PersonMeta();

    public PersonTerm(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public int age() {
        return age;
    }

    @Override
    public PersonMeta meta() {
        return personMeta;
    }
}
