package com.example.demo.security;

import org.junit.jupiter.api.Test;

public class OuterClassTest {

    public static void main(String[] args) {
        OuterClassTest.still();

        OuterClassTest.Inner inner = new Inner();
        System.out.println(inner.getClass());
//        inner.sign();

    }

    @Test
    public void test1() {
        OuterClassTest.still();

        Inner inner = new Inner();
        System.out.println(inner.getClass());

        inner.method(inner).test();
    }

    public static class Inner {
        void test() {


        }

        Inner method(Inner inner) {
            System.out.println("Inner");

            return inner;
        }
    }

    public void sign() {
        System.out.println("Outer");
    }

    public static void still() {
        System.out.println("still");
    }
}
