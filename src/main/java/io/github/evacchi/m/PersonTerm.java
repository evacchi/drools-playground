package io.github.evacchi.m;

class PersonTerm extends PersonObject implements Term.ObjectTerm {

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

}