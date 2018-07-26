package io.github.evacchi;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.Variable;

public class Main {

    static List list = new List(1, new List(2, new List(3, List.Empty)));

    public void execute() {
        KieContainer kc = KieServices.Factory.get().getKieClasspathContainer();
        KieSession kieSession = kc.newKieSession("KStateful");
        kieSession.getQueryResults("last", Variable.v, list)
                .forEach(q -> System.out.println(q.get("x")));
    }

    public static void main(String[] args) {
        new Main()
                .execute();
    }
}
