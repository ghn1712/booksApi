package com.ghn1712.guiabolso.books.crawler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.UncheckedIOException;
import java.util.regex.Pattern;

import org.junit.BeforeClass;
import org.junit.Test;

public class KuramkitapStrategyIntegratedTests {

    static KuramkitapStrategy strategy;
    static Pattern pattern;
    static String regex = "^[0-9]{13}$";

    @BeforeClass
    public static void set_up() {
        strategy = new KuramkitapStrategy();
        pattern = Pattern.compile(regex);
    }

    @Test
    public void should_return_book_isbn_when_connecting_to_kuramkitap_website() {
        String isbn = strategy.execute("https://www.kuramkitap.com/android-proglamlama-ve-uygulama-rehberi");
        assertTrue(pattern.matcher(isbn).matches());
        assertEquals("9786058352735", isbn);
    }

    @Test
    public void should_return_unavailable_when_connecting_to_google_website() {
        String isbn = strategy.execute("https://www.google.com");
        assertEquals("Unavailable", isbn);
    }

    @Test(expected = UncheckedIOException.class)
    public void should_throw_exception_when_cant_connect_to_website() {
        strategy.execute("http://localhost:4568");
    }
}
