package stream;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class Stream {
    public static void main(String[] args) {
        testFunctionalInterface();
    }
    // https://winterbe.com/posts/2014/03/16/java-8-tutorial/
    @FunctionalInterface
    interface Converter<F, T> {
        T convert(F from);
    }

    public static void testFunctionalInterface () {
        Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
        Integer converted = converter.convert("123");
        System.out.println(converted);    // 123
    }

    public static void testStaticMethod () {
        Converter<String, Integer> converter = Integer::valueOf;
        Integer converted = converter.convert("123");
        System.out.println(converted);
    }

    public static void testMap() {
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.putIfAbsent(i, "val" + i);
        }
        map.forEach((id, val) -> System.out.println(val));
    }

    public static void testStream1() {
        List<String> stringCollection = new ArrayList<>();
        stringCollection.add("ddd2");
        stringCollection.add("aaa2");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        stringCollection.add("bbb3");
        stringCollection.add("ccc");
        stringCollection.add("bbb2");
        stringCollection.add("ddd1");
        stringCollection
                .stream()
                .filter((s) -> s.startsWith("b") || s.startsWith("a"))
                .forEach(System.out::println);

        System.out.println("TEST--SORT()--------------------");
        stringCollection
                .stream()
                .sorted()
                .filter((s) -> s.startsWith("a"))
                .forEach(System.out::println);

        System.out.println("TEST--MAP()--------------------");
        stringCollection
                .stream()
                .map(String::toUpperCase)
                .sorted((a, b) -> b.compareTo(a))
                .forEach(System.out::println);

        System.out.println("TEST--MATCH()--------------------");
        boolean anyStartsWithA =
                stringCollection
                        .stream()
                        .anyMatch((s) -> s.startsWith("a"));

        System.out.println(anyStartsWithA);      // true
        boolean allStartsWithA =
                stringCollection
                        .stream()
                        .allMatch((s) -> s.startsWith("a"));

        System.out.println(allStartsWithA);      // false
        boolean noneStartsWithZ =
                stringCollection
                        .stream()
                        .noneMatch((s) -> s.startsWith("z"));
        System.out.println(noneStartsWithZ);      // true

        System.out.println("TEST--COUNT()--------------------");
        long startsWithB =
                stringCollection
                        .stream()
                        .filter((s) -> s.startsWith("b"))
                        .count();
        System.out.println(startsWithB);    // 3

        System.out.println("TEST--REDUCTION()--------------------");
        Optional<String> reduced =
                stringCollection
                        .stream()
                        .sorted()
                        .reduce((s1, s2) -> s1 + " " + s2);
        reduced.ifPresent(System.out::println);
// "aaa1#aaa2#bbb1#bbb2#bbb3#ccc#ddd1#ddd2"

    }


    // https://winterbe.com/posts/2014/07/31/java8-stream-tutorial-examples/
    public static void testStream2() {
        Arrays.asList("a1", "a2", "a3")
                .stream()
                .findFirst()
                .ifPresent(System.out::println);

        // Just use Stream.of() to create a stream from a bunch of object references.
        java.util.stream.Stream.of("a1", "a2", "a3")
                .findFirst()
                .ifPresent(System.out::println);  // a1

        // primitive streams:
        Arrays.stream(new int[] {1, 2, 3})
                .map(n -> 2 * n + 1)
                .average()
                .ifPresent(System.out::println);  // 5.0

        // transform a regular object stream to a primitive stream or vice versa
        java.util.stream.Stream.of("a132", "a213", "a321")
                .map(s -> s.substring(2))
                .mapToInt(Integer::parseInt)
                .max()
                .ifPresent(System.out::println);  // 32

        IntStream.range(1, 4)
                .mapToObj(i -> "a" + i)
                .forEach(System.out::println);

        // Combined example:
        java.util.stream.Stream.of(1.0, 2.0, 3.0)
                .mapToInt(Double::intValue)
                .mapToObj(i -> "a" + i)
                .forEach(System.out::println);
    }

    // Vertical processing: This behavior can reduce the actual number of operations performed on each element
    public static void processingOrderTest() {
        //map has only to be executed twice in this case
        java.util.stream.Stream.of("d2", "a2", "b1", "b3", "c")
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .anyMatch(s -> {
                    System.out.println("anyMatch: " + s);
                    return s.startsWith("A");
                });
        // map:      d2
        // anyMatch: D2
        // map:      a2
        // anyMatch: A2
    }

    // why order matters:
    public static void orderMattersTest() {
        // run five times
        java.util.stream.Stream.of("d2", "a2", "b1", "b3", "c")
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("A");
                })
                .forEach(s -> System.out.println("forEach: " + s));
        // map:     d2
        // filter:  D2
        // map:     a2
        // filter:  A2
        // forEach: A2
        // map:     b1
        // filter:  B1
        // map:     b3
        // filter:  B3
        // map:     c
        // filter:  C
        System.out.println("================================");
        // change function order: We can greatly reduce the actual number of executions if we change the order of the operations, moving filter to the beginning of the chain
        java.util.stream.Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("a");
                })
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.println("forEach: " + s));

// filter:  d2
// filter:  a2
// map:     a2
// forEach: A2
// filter:  b1
// filter:  b3
// filter:  c
    }

    // Sorting is stateful operation since in order to sort a collection of elements you have to maintain state during ordering.
    public static void sortedTest() {
        java.util.stream.Stream.of("d2", "a2", "b1", "b3", "c")
                .sorted((s1, s2) -> {
                    System.out.printf("sort: %s; %s\n", s1, s2);
                    return s1.compareTo(s2);
                })
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("a");
                })
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.println("forEach: " + s));
        /*
sort:    a2; d2
sort:    b1; a2
sort:    b1; d2
sort:    b1; a2
sort:    b3; b1
sort:    b3; d2
sort:    c; b3
sort:    c; d2
filter:  a2
map:     a2
forEach: A2
filter:  b1
filter:  b3
filter:  c
filter:  d2
         */
    }

    // Reuse stream
    public static void reuseStreamTest() throws IllegalStateException{
        java.util.stream.Stream<String> stream =
                java.util.stream.Stream.of("d2", "a2", "b1", "b3", "c")
                        .filter(s -> s.startsWith("a"));

//        stream.anyMatch(s -> true);    // ok
//        stream.noneMatch(s -> true);   // exception: stream has already been operated upon or closed

        Supplier<java.util.stream.Stream<String>> streamSupplier = () -> java.util.stream.Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> s.startsWith("a"));
        streamSupplier.get().anyMatch(s -> true);   // ok
        streamSupplier.get().noneMatch(s -> true);  // ok
    }



    // FlatMap
    class Foo {
        String name;
        List<Bar> bars = new ArrayList<>();

        Foo(String name) {
            this.name = name;
        }
    }
    class Bar {
        String name;

        Bar(String name) {
            this.name = name;
        }
    }
}
