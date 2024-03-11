package com.example.myapplication;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MainActivityUnitTest {

    private MainActivity activity;

    @Before
    public void setUp() {
        activity = new MainActivity();
    }

    @Test
    public void sortiereMatrikelnummer_sortsCorrectly() {
        String input = "593824617";
        String expected = "246835179";
        Assert.assertEquals(expected, activity.sortiereMatrikelnummer(input));
    }

    @Test
    public void sortiereMatrikelnummer_withAllEvenNumbers() {
        String input = "2468";
        String expected = "2468";
        Assert.assertEquals(expected, activity.sortiereMatrikelnummer(input));
    }

    @Test
    public void sortiereMatrikelnummer_withAllOddNumbers() {
        String input = "7531";
        String expected = "1357";
        Assert.assertEquals(expected, activity.sortiereMatrikelnummer(input));
    }

    @Test
    public void sortiereMatrikelnummer_withEmptyString() {
        String input = "";
        String expected = "";
        Assert.assertEquals(expected, activity.sortiereMatrikelnummer(input));
    }

    @Test
    public void sortiereMatrikelnummer_withSingleNumber() {
        String input = "5";
        String expected = "5";
        Assert.assertEquals(expected, activity.sortiereMatrikelnummer(input));
    }

    @Test
    public void sortiereMatrikelnummer_withRepeatedNumbers() {
        String input = "22224444";
        String expected = "22224444";
        Assert.assertEquals(expected, activity.sortiereMatrikelnummer(input));
    }

    @Test
    public void sortiereMatrikelnummer_mixOfEvenOddAndRepeated() {
        String input = "224466335577";
        String expected = "2244663557";
        Assert.assertEquals(expected, activity.sortiereMatrikelnummer(input));
    }
}
