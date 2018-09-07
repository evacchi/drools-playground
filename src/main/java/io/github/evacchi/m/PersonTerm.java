package io.github.evacchi.m;

final class PersonTerm extends Person implements Term.ObjectTerm,
                                                 PersonObject {

    PersonMeta.Sentence $sentence;

    {
        PersonMeta.Sentence sentence =
                PersonMeta.Instance.createSentence();
        sentence.bind(this);
    }

    @Override
    public PersonMeta.Sentence $getSentence() {
        return $sentence;
    }

    @Override
    public void $setSentence(Term.Sentence $sentence) {
        this.$sentence = (PersonMeta.Sentence) $sentence;
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