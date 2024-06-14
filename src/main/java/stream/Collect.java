package stream;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Collect {

    public static void main(String[] args) {
        Collect.collectTest();
    }

    // Advanced options:  I leave it up to you to discover all other available operations
    static class Person {
        String name;
        int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    static List<Person> persons =
            Arrays.asList(
                    new Person("Max", 18),
                    new Person("Peter", 23),
                    new Person("Pamela", 23),
                    new Person("David", 12));

    // Collect is an extremely useful terminal operation to transform the elements of the stream into a different kind of result, e.g. a List, Set or Map.
    // Collect accepts a Collector which consists of four different operations: a supplier, an accumulator, a combiner and a finisher
    public static void collectTest() {

        // Collectors.toList()
        List<Person> filtered =
                persons
                        .stream()
                        .filter(p -> p.name.startsWith("P"))
                        .collect(Collectors.toList());
        System.out.println(filtered);

        // Collectors.groupingBy
        Map<Integer, List<Person>> personsByAge = persons
                .stream()
                .collect(Collectors.groupingBy(p -> p.age));
        personsByAge.forEach((age, p) -> System.out.format("age %s: %s\n", age, p));
        /*
age 18: [Max]
age 23: [Peter, Pamela]
age 12: [David]
         */

        // Collectors.toMap()
        Map<Integer, String> map = persons
                .stream()
                .collect(Collectors.toMap(
                        p -> p.age,
                        p -> p.name,
                        (name1, name2) -> name1 + ";" + name2));
        System.out.println(map);
// {18=Max, 23=Peter;Pamela, 12=David}

        // build our own special collector:   ???????????????
        Collector<Person, StringJoiner, String> personNameCollector =
                Collector.of(
                        () -> new StringJoiner(" | "),  // supplier: Since strings in Java are immutable, we need a helper class like StringJoiner to let the collector construct our string.
                        (j, p) -> j.add(p.name.toUpperCase()),  // accumulator
                        (j1, j2) -> j1.merge(j2),               // combiner
                        StringJoiner::toString);                // finisher
        String names = persons
                .stream()
                .collect(personNameCollector);
        System.out.println(names);  // MAX | PETER | PAMELA | DAVID
    }
}
