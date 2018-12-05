package io.github.evacchi;

import java.util.Collections;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.drools.core.RuleBaseConfiguration;
import org.drools.core.SessionConfigurationImpl;
import org.drools.core.common.ProjectClassLoader;
import org.drools.core.definitions.impl.KnowledgePackageImpl;
import org.drools.core.impl.EnvironmentFactory;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseImpl;
import org.drools.core.impl.StatefulKnowledgeSessionImpl;
import org.drools.model.Model;
import org.drools.model.PatternDSL;
import org.drools.model.Query;
import org.drools.model.impl.ModelImpl;
import org.drools.modelcompiler.CanonicalKiePackages;
import org.drools.modelcompiler.KiePackagesBuilder;
import org.kie.api.runtime.Environment;
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

    public Stream<Object> execute() {
        return StreamSupport.stream(
                kieSession.getQueryResults("$goal").spliterator(), false)
                .map(q -> q.get("$result"));
    }

    public Droolog predicates(Query... queries) {
        ModelImpl model = new ModelImpl();
        for (Query query : queries) {
            model.addQuery(query);
        }

        session(model);

        this.facts.forEach(kieSession::insert);
        return this;
    }

    public void session(ModelImpl model) {
//        CanonicalKiePackages packages = new MyKiePackagesBuilder(model).build();
        KnowledgeBaseImpl kBase = new KnowledgeBaseImpl("mykbase", new RuleBaseConfiguration());
        kBase.addPackage(new KnowledgePackageImpl());
//        kBase.addPackages(packages.getKiePackages());

        StatefulKnowledgeSessionImpl session =
                new StatefulKnowledgeSessionImpl(
                        0,
                        kBase,
                        true,
                        new SessionConfigurationImpl(),
                        EnvironmentFactory.newEnvironment());

        kieSession = session;

//        return session;
//
//
//        InternalKnowledgeBase kBase = new KnowledgeBaseImpl("defaultname", kieBaseConf );
//        kBase.addPackages( packages.getKiePackages() );
//
//
//        this.kieSession =
//                kBase.newKieSession();
    }

    // patch packages builder
    static class MyKiePackagesBuilder extends KiePackagesBuilder {

        public MyKiePackagesBuilder(Model model) {
            super(null, Collections.singleton(model));
        }

        @Override
        public ClassLoader getClassLoader() {
            return getClass().getClassLoader();
        }
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
