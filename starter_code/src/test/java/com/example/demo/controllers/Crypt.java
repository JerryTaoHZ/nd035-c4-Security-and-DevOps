package com.example.demo.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Crypt {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void bCryptTest() {
        BCryptPasswordEncoder bc1 = new BCryptPasswordEncoder(10);
        BCryptPasswordEncoder bc2 = new BCryptPasswordEncoder(11);
        BCryptPasswordEncoder bc3 = new BCryptPasswordEncoder(12);
        BCryptPasswordEncoder bc4 = new BCryptPasswordEncoder(13);
        System.out.println(bc1.encode("abc"));
        System.out.println(bc2.encode("abc"));
        System.out.println(bc3.encode("abc"));
        System.out.println(bc4.encode("abc"));
    }
}
