package io.github.evacchi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;

import static org.kie.api.runtime.rule.Variable.v;

public class Rules2 {

    public static void main(String[] args) {
        KieContainer kc = KieServices.Factory.get().getKieClasspathContainer();
        KieSession ksession = kc.newKieSession( "KStateful");
//
//        final KieBase kbase = KieBaseUtil.getKieBaseFromKieModuleFromDrl("backward-chaining-test", kieBaseTestConfiguration, drl);
//        KieSession ksession = kbase.newKieSession();
        try {
            final List<String> list = new ArrayList<>();
            ksession.setGlobal("list", list);

            // grand parents
            ksession.insert(new Man("john"));
            ksession.insert(new Woman("janet"));

            // parent
            ksession.insert(new Man("adam"));
            ksession.insert(new Parent("john",
                                       "adam"));
            ksession.insert(new Parent("janet",
                                       "adam"));

            ksession.insert(new Man("stan"));
            ksession.insert(new Parent("john",
                                       "stan"));
            ksession.insert(new Parent("janet",
                                       "stan"));

            // grand parents
            ksession.insert(new Man("carl"));
            ksession.insert(new Woman("tina"));
            //
            // parent
            ksession.insert(new Woman("eve"));
            ksession.insert(new Parent("carl",
                                       "eve"));
            ksession.insert(new Parent("tina",
                                       "eve"));
            //
            // parent
            ksession.insert(new Woman("mary"));
            ksession.insert(new Parent("carl",
                                       "mary"));
            ksession.insert(new Parent("tina",
                                       "mary"));

            ksession.insert(new Man("peter"));
            ksession.insert(new Parent("adam",
                                       "peter"));
            ksession.insert(new Parent("eve",
                                       "peter"));

            ksession.insert(new Man("paul"));
            ksession.insert(new Parent("adam",
                                       "paul"));
            ksession.insert(new Parent("mary",
                                       "paul"));

            ksession.insert(new Woman("jill"));
            ksession.insert(new Parent("adam",
                                       "jill"));
            ksession.insert(new Parent("eve",
                                       "jill"));

//            ksession = SerializationHelper.getSerialisedStatefulKnowledgeSession(ksession, true);

            list.clear();
            QueryResults results = ksession.getQueryResults("woman", v);
            for (final QueryResultsRow result : results) {
                list.add((String) result.get("name"));
            }
            System.out.println(list);
            //assertEquals(5, list.size());
            //assertContains(new String[]{"janet", "mary", "tina", "eve", "jill"}, list);

            list.clear();
            results = ksession.getQueryResults("man", v);
            for (final QueryResultsRow result : results) {
                list.add((String) result.get("name"));
            }
            //assertEquals(6, list.size());
            //assertContains(new String[]{"stan", "john", "peter", "carl", "adam", "paul"}, list);

            list.clear();
            results = ksession.getQueryResults("father", v, v);
            for (final QueryResultsRow result : results) {
                list.add(result.get("father") + ", " + result.get("child"));
            }
            //assertEquals(7, list.size());
            //assertContains(new String[]{"john, adam", "john, stan",
//                    "carl, eve", "carl, mary",
//                    "adam, peter", "adam, paul",
//                    "adam, jill"}, list);

            list.clear();
            results = ksession.getQueryResults("mother", v, v);
            for (final QueryResultsRow result : results) {
                list.add(result.get("mother") + ", " + result.get("child"));
            }
            //assertEquals(7, list.size());
            //assertContains(new String[]{"janet, adam", "janet, stan",
//                                   "mary, paul", "tina, eve",
//                                   "tina, mary", "eve, peter",
//                                   "eve, jill"},
//                           list);

            list.clear();
            results = ksession.getQueryResults("son",
                                               v, v);
            for (final QueryResultsRow result : results) {
                list.add(result.get("son") + ", " + result.get("parent"));
            }
            //assertEquals(8,
//                         list.size());
            //assertContains(new String[]{"stan, john", "stan, janet",
//                    "peter, adam", "peter, eve",
//                    "adam, john", "adam, janet",
//                    "paul, mary", "paul, adam"}, list);

            list.clear();
            results = ksession.getQueryResults("daughter", v, v);
            for (final QueryResultsRow result : results) {
                list.add(result.get("daughter") + ", " + result.get("parent"));
            }
            //assertEquals(6, list.size());
            //assertContains(new String[]{"mary, carl", "mary, tina",
//                    "eve, carl", "eve, tina",
//                    "jill, adam", "jill, eve"}, list);

            list.clear();
            results = ksession.getQueryResults("siblings", v, v);
            for (final QueryResultsRow result : results) {
                list.add(result.get("c1") + ", " + result.get("c2"));
            }
            //assertEquals(16, list.size());
            //assertContains(new String[]{"eve, mary", "mary, eve",
//                    "adam, stan", "stan, adam",
//                    "adam, stan", "stan, adam",
//                    "peter, paul", "peter, jill",
//                    "paul, peter", "paul, jill",
//                    "jill, peter", "jill, paul",
//                    "peter, jill", "jill, peter",
//                    "eve, mary", "mary, eve"}, list);

            list.clear();
            results = ksession.getQueryResults("fullSiblings", v, v);
            for (final QueryResultsRow result : results) {
                list.add(result.get("c1") + ", " + result.get("c2"));
            }
            //assertEquals(12, list.size());
            //assertContains(new String[]{"eve, mary", "mary, eve",
//                    "adam, stan", "stan, adam",
//                    "adam, stan", "stan, adam",
//                    "peter, jill", "jill, peter",
//                    "peter, jill", "jill, peter",
//                    "eve, mary", "mary, eve"}, list);

            list.clear();
            results = ksession.getQueryResults("fullSiblings", v, v);
            for (final QueryResultsRow result : results) {
                list.add(result.get("c1") + ", " + result.get("c2"));
            }
            //assertEquals(12, list.size());
            //assertContains(new String[]{"eve, mary", "mary, eve",
//                    "adam, stan", "stan, adam",
//                    "adam, stan", "stan, adam",
//                    "peter, jill", "jill, peter",
//                    "peter, jill", "jill, peter",
//                    "eve, mary", "mary, eve"}, list);

            list.clear();
            results = ksession.getQueryResults("uncle", v, v);
            for (final QueryResultsRow result : results) {
                list.add(result.get("uncle") + ", " + result.get("n"));
            }
            //assertEquals(6, list.size());
            //assertContains(new String[]{"stan, peter",
//                    "stan, paul",
//                    "stan, jill",
//                    "stan, peter",
//                    "stan, paul",
//                    "stan, jill"}, list);

            list.clear();
            results = ksession.getQueryResults("aunt", v, v);
            for (final QueryResultsRow result : results) {
                list.add(result.get("aunt") + ", " + result.get("n"));
            }
            //assertEquals(6, list.size());
            //assertContains(new String[]{"mary, peter",
//                    "mary, jill",
//                    "mary, peter",
//                    "mary, jill",
//                    "eve, paul",
//                    "eve, paul"}, list);

            list.clear();
            results = ksession.getQueryResults("grantParents", v, v);
            for (final QueryResultsRow result : results) {
                list.add(result.get("gp") + ", " + result.get("gc"));
            }
            //assertEquals(12, list.size());
            //assertContains(new String[]{"carl, peter",
//                    "carl, jill",
//                    "carl, paul",
//                    "john, peter",
//                    "john, paul",
//                    "john, jill",
//                    "janet, peter",
//                    "janet, paul",
//                    "janet, jill",
//                    "tina, peter",
//                    "tina, jill",
//                    "tina, paul",}, list);
        } finally {
            ksession.dispose();
        }
    }
    

    public static class Man
            implements
            Serializable {

        private String name;

        public Man(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Man [name=" + name + "]";
        }
    }

    public static class Woman
            implements
            Serializable {

        private String name;

        public Woman(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Woman [name=" + name + "]";
        }
    }

    public static class Parent
            implements
            Serializable {

        private String parent;
        private String child;

        public Parent(final String parent,
                      final String child) {
            this.parent = parent;
            this.child = child;
        }

        public String getParent() {
            return parent;
        }

        public void setParent(final String parent) {
            this.parent = parent;
        }

        public String getChild() {
            return child;
        }

        public void setChild(final String child) {
            this.child = child;
        }

        @Override
        public String toString() {
            return "Parent [parent=" + parent + ", child=" + child + "]";
        }
    }
}