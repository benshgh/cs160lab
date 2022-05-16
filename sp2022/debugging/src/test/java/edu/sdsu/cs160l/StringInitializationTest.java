package edu.sdsu.cs160l;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringInitializationTest {

    String testString;

    @BeforeEach
    public void before() {
        testString = "mz";
    }

    @Test
    public void createSimpleInstance() {
        String name = "String";
        System.out.println(name);
    }

    @Test
    public void createStringUsingConstructor() {
        String name = new String("String");
        System.out.println(name);
    }


    @Test
    public void create2StringWithSameValue() {
        String a = "hey";
        String b = "hey";
        assertEquals(a, b);
    }

    @Test
    public void create2StringArrays() {
        String[] a = {"java", "c++", "javascript"};
        String[] b = {"java", "c++", "javascript"};

        assertArrayEquals(a, b);

    }

    @Test
    public void equalityOfStrings() {
        String[] data1 = {"mz", "my", "my", "mx", "mz", "mx", "my", "mz"};
        String[] data2 = {"mz", "mz", "mz", "mx", "mx", "my", "my", "my"};

        assertEquals(data1[0], "mz");
        assertEquals(data1[0], data2[2]);
        assertEquals(data1[0], new String(data2[0]));
    }
}
