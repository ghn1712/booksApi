package com.ghn1712.guiabolso.books.crawler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.regex.Pattern;

import org.junit.BeforeClass;
import org.junit.Test;

public class IsbnRetrieverContextIntegratedTestCase {

    static AmazonStrategy strategy;
    static Pattern pattern;
    static String regex = "^[0-9]{13}$";

    @BeforeClass
    public static void set_up() {
        strategy = new AmazonStrategy();
        pattern = Pattern.compile(regex);
    }

    @Test
    public void should_return_book_isbn_when_connecting_to_amazon_website() {
        String isbn = IsbnRetrieverContext.getIsbn(
                "https://www.amazon.com/Clean-Code-Handbook-Software-Craftsmanship/dp/0132350882/ref=sr_1_1_sspa?ie=UTF8&qid=1534625054&sr=8-1-spons&keywords=clean+code&psc=1",
                strategy);
        assertTrue(pattern.matcher(isbn).matches());
        assertEquals("9780132350884", isbn);
    }
}
