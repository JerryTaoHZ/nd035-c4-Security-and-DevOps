package com.udacity.examples.Testing;

import org.junit.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class HelperTest {

    List<String> empNames = Arrays.asList("sareeta", "", "john","");

    @BeforeClass
    public static void setup() {
        System.out.println("runs before each class");
    }

    @Before
    public void init() {
        System.out.println("runs before each method");
    }

    @After
    public void initEnd() {
        System.out.println("runs after each method");
    }

    @AfterClass
    public static void teardown() {
        System.out.println("runs after each class");
    }


    @Test
    public void TestGetListNameCount() {
        long actual = Helper.getCount(empNames);
        List<String> expected = Arrays.asList("sareeta", "", "john","");

        assertEquals(2, actual);

        assertEquals(expected, empNames);
    }

    @Test
    public void DiffsBetweenArrayNCollections1() {
        Integer[] yrs = {10,14,2};
        int[] expected = {10,14,2};
        List<Integer> yrsList = Arrays.asList(yrs);
        Integer[] IntegerArray = Arrays.stream(expected).boxed().toArray(Integer[]::new);
        List<Integer> expectedList = Arrays.stream(expected).boxed().collect(Collectors.toList());
        assertEquals(yrsList, expectedList);
    }
    @Ignore
    @Test
    public void DiffsBetweenArrayNCollections2() {
        int[] yrs = {10,14,2};
        int[] expected = {10,14,2};
        assertEquals(yrs, expected);
    }
    @Test
    public void DiffsBetweenArrayNCollections3() {
        Integer[] yrs = {10,14,2};
        Integer[] expected = {10,14,2};
        assertEquals(yrs, expected);
    }
    @Test
    public void DiffsBetweenArrayNCollections4() {
        Integer[] yrs = {10,14,2};
        Integer[] expected = {10,14,2};
        assertArrayEquals(yrs, expected);
    }

    @Test
    public void TestGetMergedList() {
        String mergedList = Helper.getMergedList(empNames);
        String expected = "sareeta, john";
        assertEquals(expected, mergedList);

    }
	
}
