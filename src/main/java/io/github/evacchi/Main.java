package io.github.evacchi;

import org.kie.api.KieServices;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class Main {
    static List list = new List(1, new List(2, new List(3, null)));

    public void execute() {
        KieContainer kc = KieServices.Factory.get().getKieClasspathContainer();
        KieSession kieSession = kc.newKieSession( "KStateful");
        kieSession.insert(new Query(new Last(null, list)));
        kieSession.fireAllRules();
        kieSession.getObjects(new ClassObjectFilter(Result.class)).forEach(System.out::println);
    }

    public static void main(String[] args) {
        new Main()
                .execute();
    }
}
