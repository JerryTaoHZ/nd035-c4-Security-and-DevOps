package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MockitoBiteTests {

    @Test
    public void mockList() {
        List mockedList = mock(List.class);
// or even simpler with Mockito 4.10.0+
// List mockedList = mock();

// using mock object - it does not throw any "unexpected interaction" exception
        mockedList.add("one");
        mockedList.clear();

// selective, explicit, highly readable verification
        verify(mockedList, times(1)).add("one");
        verify(mockedList).clear();
    }

    @Test
    public void stubMethodCalls() {
        // you can mock concrete classes, not only interfaces
        LinkedList<String> mockedList = mock(LinkedList.class);
// or even simpler with Mockito 4.10.0+
// LinkedList mockedList = mock();

// stubbing appears before the actual execution
        when(mockedList.get(0)).thenReturn("first");

// the following prints "first"
        System.out.println(mockedList.get(0));

// the following prints "null" because get(999) was not stubbed
        System.out.println(mockedList.get(999));
    }

    @Test
    public void mockMapMatch() {
        Map mockMap = mock(Map.class);
        when(mockMap.get("sareeta Panda")).thenReturn(7).thenReturn(5);
        assertEquals(7, mockMap.get("sareeta Panda"));
        assertEquals(5, mockMap.get("sareeta Panda"));
    }

}
