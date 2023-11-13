package com.example.demo;

import java.lang.reflect.Field;

public class TestUtils {

    public static void injectObjects(Object target, String fieldName, Object toInject) {
        boolean wasPrivate = false;

        try {
            Field f = target.getClass().getDeclaredField(fieldName);

            if(!f.isAccessible()) {
                f.setAccessible(true);
                wasPrivate = true;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


    }
}
