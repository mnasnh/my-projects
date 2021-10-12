package com.myprojects;

import com.myprojects.exception.LogParsingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;


@RunWith(JUnit4.class)
public class MostActiveCookieServiceTest {

    @Test
    public void shouldGetMostActiveCookiesList_Sample_1() throws LogParsingException {
        String[] activeCookiesExpected = new String[]{"AtY0laUfhglK3lC7"};
        List<String> activeCookiesActual = new MostActiveCookieService().getMostActiveCookiesList("sample1.csv", "2018-12-09");
        assertThat("List equality without order",
                activeCookiesActual, containsInAnyOrder(activeCookiesExpected));

    }

    @Test
    public void shouldGetMostActiveCookiesList_Sample_2() throws LogParsingException {
        String[] activeCookiesExpected = new String[]{"SAZuXPGUrfbcn5UA"};
        List<String> activeCookiesActual = new MostActiveCookieService().getMostActiveCookiesList("sample2.csv", "2018-12-09");
        assertThat("List equality without order",
                activeCookiesActual, containsInAnyOrder(activeCookiesExpected));

    }

    @Test
    public void shouldGetMostActiveCookiesList_Sample_3() throws LogParsingException {
        String[] activeCookiesExpected = new String[]{"SAZuXPGUrfbcn5UA", "4sMM2LxV07bPJzwf", "fbcn5UAVanZf6UtG"};
        List<String> activeCookiesActual = new MostActiveCookieService().getMostActiveCookiesList("sample3.csv", "2018-12-08");
        assertThat("List equality without order",
                activeCookiesActual, containsInAnyOrder(activeCookiesExpected));

    }
    @Test
    public void shouldGetMostActiveCookiesList_Sample_4() throws LogParsingException {
        List<String> activeCookiesActual = new MostActiveCookieService().getMostActiveCookiesList("sample4.csv", "2018-12-09");
        System.out.println("Active cookies:" + activeCookiesActual);

    }

}