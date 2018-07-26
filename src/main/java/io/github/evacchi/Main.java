package io.github.evacchi;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.Variable;

public class Main {

    static List list = new List(10, new List(20, new List(30, List.Nil)));

    public void execute() {
        KieContainer kc = KieServices.Factory.get().getKieClasspathContainer();
        KieSession kieSession = kc.newKieSession("KStateful");
        kieSession.getQueryResults("last", Variable.v, list)
                .forEach(q -> System.out.println(q.get("x")));

        kieSession.getQueryResults("last_but_one", Variable.v, list)
                .forEach(q -> System.out.println(q.get("x")));

        kieSession.getQueryResults("element_at", Variable.v, list, 2)
                .forEach(q -> System.out.println(q.get("x")));

        kieSession.getQueryResults("length", list, Variable.v)
                .forEach(q -> System.out.println(q.get("len")));

    }

    public static void main(String[] args) {
        new Main()
                .execute();
    }
}
