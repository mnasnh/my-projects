package com.myprojects;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.List;

@RunWith(JUnit4.class)
public class MostActiveCookieServiceTest {

    @Test
    public void shouldGetMostActiveCookiesList_Sample_1() {
        List<String> activeCookiesExpected = Arrays.asList("AtY0laUfhglK3lC7");
        List<String> activeCookiesActual = new MostActiveCookieService().getMostActiveCookiesList("sample1.csv", "2018-12-09");
        Assert.assertEquals(activeCookiesExpected, activeCookiesActual);

    }

    @Test
    public void shouldGetMostActiveCookiesList_Sample_2() {
        List<String> activeCookiesExpected = Arrays.asList("SAZuXPGUrfbcn5UA");
        List<String> activeCookiesActual = new MostActiveCookieService().getMostActiveCookiesList("sample2.csv", "2018-12-09");
        Assert.assertEquals(activeCookiesExpected, activeCookiesActual);

    }

    @Test
    public void shouldGetMostActiveCookiesList_Sample_3() {
        List<String> activeCookiesExpected = Arrays.asList("SAZuXPGUrfbcn5UA", "4sMM2LxV07bPJzwf", "fbcn5UAVanZf6UtG");
        List<String> activeCookiesActual = new MostActiveCookieService().getMostActiveCookiesList("sample3.csv", "2018-12-08");
        Assert.assertEquals(activeCookiesExpected, activeCookiesActual);

    }
    @Test
    public void shouldGetMostActiveCookiesList_Sample_4() {
        List<String> activeCookiesActual = new MostActiveCookieService().getMostActiveCookiesList("sample4.csv", "2018-12-09");
        System.out.println("Active cookies:" + activeCookiesActual);

    }

}