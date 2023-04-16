package com.devjunior.cuestionario;

import com.devjunior.cuestionario.repository.Message;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TestMessage {

    @Test
    public void testName(){
        Message obj = new Message();
        Assertions.assertEquals("Hello Daily Code Buffer!", obj.getMessage("Daily Code Buffer"));
    }

    @Test
    public void testNameBlank(){
        Message obj = new Message();
        Assertions.assertEquals("Please provide a name!", obj.getMessage(""));
    }
}
