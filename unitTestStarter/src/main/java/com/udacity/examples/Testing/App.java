package com.udacity.examples.Testing;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        App app = new App();

        app.testThis();
    }

    void testThis() {
        System.out.println("this: " + this);
    }


}
