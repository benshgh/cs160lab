package edu.sdsu.cs160l.algorithm.search;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static edu.sdsu.cs160l.TestUtils.printExecutionTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LinearSearchTest {
    private LinearSearch linearSearch;
    private List<Integer> numbers;

    @BeforeEach
    public void init() {
        linearSearch = new LinearSearch();
        numbers = IntStream
                .range(0, 100000) //IntStream.range() creates an IntStream of numbers in a range passed
                .filter(e -> e % 2 == 0) //IntStream
                .boxed() //Stream<Integer>
                .collect(Collectors.toList()); //List<Integer>
    }

    @Test
    public void search() {
        int index = printExecutionTime(() -> linearSearch.search(numbers, 10000));
        assertEquals(5000, index);
        index = printExecutionTime(() -> linearSearch.search(numbers, 1));
        assertEquals(-1, index);
    }


}