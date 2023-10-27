package com.udacity.examples.Testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ParameterizedTest {
    private String input;
    private String output;

    public ParameterizedTest(String input) {
        super();
        this.input = input;
//        this.output = output;
    }

    @Parameters
    public static Collection initData() {
        String[][] empNames = {{"Jerry", "Jerry"},{"Harry", "Potter"}};
        List<String[]> names = Arrays.asList(empNames);
        return names;
    }

    @Test
    public void VerifyNamesNotEqual() {
        assertNotEquals(input, output);
    }

}
