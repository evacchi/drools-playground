package io.github.evacchi;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.drools.model.PatternDSL;
import org.drools.model.Query;
import org.drools.model.impl.ModelImpl;
import org.drools.modelcompiler.builder.KieBaseBuilder;
import org.kie.api.runtime.KieSession;

import static org.drools.model.DSL.declarationOf;
import static org.drools.model.PatternDSL.pattern;

public class Droolog {

    private Stream<Object> facts = Stream.empty();
    private KieSession kieSession;

    public Droolog facts(Object... facts) {
        this.facts = Stream.of(facts);
        return this;
    }

    public Droolog predicates(Query... queries) {
        var model = new ModelImpl();
        Stream.of(queries).forEach(model::addQuery);

        this.kieSession =
                KieBaseBuilder
                        .createKieBaseFromModel(model)
                        .newKieSession();

        this.facts.forEach(kieSession::insert);
        return this;
    }

    public Stream<Object> execute() {
        return StreamSupport.stream(
                kieSession.getQueryResults("$goal").spliterator(), false)
                .map(q -> q.get("$result"));
    }

    public static void main(String[] args) {
        new Droolog()
                .facts(
                        new Person("Mark", 39),
                        new Person("Mario", 41))
                .predicates(
                        PatternDSL.query("$goal").build(
                                pattern(declarationOf(Person.class, "$result"))
                                        .expr(p -> p.getAge() > 30)))
                .execute()
                .forEach(System.out::println);
    }
}
