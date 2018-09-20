package com.ghn1712.guiabolso.books.crawler;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UnavailableIsbnIntegratedTestsCase {

    @Test
    public void should_return_unavailable() {
        assertEquals("Unavailable", IsbnRetriever.getIsbn(
                "https://docs.gradle.org/current/userguide/userguide_single.html?_ga=2.136842420.542867609.1534655620-1469846802.1526948723#java_testing"));
    }
}
