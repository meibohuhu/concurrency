package stream;

import java.util.Arrays;
import java.util.List;

public class Reduce {
    public static void main(String[] args) {
//        reduceTest1();
        reduceTest2();
    }

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

    // The first one reduces a stream of elements to exactly one element of the stream. BinaryOperator accumulator
    public static void reduceTest1() {
        persons
                .stream()
                .reduce((p1, p2) -> p1.age > p2.age ? p1 : p2)
                .ifPresent(System.out::println);    // Pamela
    }

    // construct a new Person with the aggregated names and ages from all other persons in the stream
    // accepts both an identity value and a BinaryOperator accumulator
    public static void reduceTest2() {
        Person result =
                persons
                        .stream()
                        .reduce(new Person("", 0), (p1, p2) -> {
                            p1.age += p2.age;
                            p1.name += p2.name;
                            return p1;
                        });

        System.out.format("name=%s; age=%s", result.name, result.age);
// name=MaxPeterPamelaDavid; age=76
    }

}
