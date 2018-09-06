package io.github.evacchi.meta1;

@interface Metable {}
@interface Imperfect {}
@interface ObjectTerm {}

@Metable @Imperfect @ObjectTerm
class Person {
    public static final PersonMeta meta = new PersonMeta();

    private String name;
    private String hairColor;
}

// matching setters ommmitted, but to be added.

 interface PersonMeta1 {
    PersonMeta2 name();
    PersonMeta2 hairColor();

    public static interface PersonMeta2 {
        public int getDegree();

        public String getTermState();

        public PersonMetaMulti3 getMulti();
    }

    public static interface PersonMetaMulti3 {
        public int getP1();
        public String getP2();
    }
}

class PersonMeta implements PersonMeta1, PersonMeta1.PersonMeta2, PersonMeta1.PersonMetaMulti3 {
    private int position;

    private int name_degree;
    private int hairColor_degree;

    private int name_multi_p1;
    private int hairColor_multi_p1;

    private String name_multi_p2;
    private String hairColor_multi_p2;

    private String name_termState;
    private String hairColor_termState;

    public PersonMeta2 name() {
        position = 0;
        return (PersonMeta2) this;
    }

    public PersonMeta2 hairColor() {
        position = 1;
        return (PersonMeta2) this;
    }

    @Override
    public int getDegree() {
        switch(position) {
            case 0:
                return name_degree;
            case 1:
                return hairColor_degree;
        }
        throw new IllegalStateException("Property Position does not exist");
    }

    @Override
    public String getTermState() {
        switch(position) {
            case 0:
                return name_termState;
            case 1:
                return hairColor_termState;
        }
        throw new IllegalStateException("Property Position does not exist");
    }

    @Override public PersonMetaMulti3 getMulti() {
        return this;
    }

    @Override
    public int getP1() {
        switch(position) {
            case 0:
                return name_multi_p1;
            case 1:
                return hairColor_multi_p1;
        }
        throw new IllegalStateException("Property Position does not exist");
    }

    @Override
    public String getP2() {
        switch(position) {
            case 0:
                return name_multi_p2;
            case 1:
                return hairColor_multi_p2;
        }
        throw new IllegalStateException("Property Position does not exist");
    }
}