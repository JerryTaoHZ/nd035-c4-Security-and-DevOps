package com.example.demo;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class thisKeywordCallParent {
    public static void main(String[] args) {
        current current = new current();
        current.cAct();
    }

}

class current extends parent{

    void cAct() {
        System.out.println("this method is from current.");
        this.pAct();
    }

}

class parent {

    void pAct() {
        System.out.println("this method is from parent.");
    }
}
