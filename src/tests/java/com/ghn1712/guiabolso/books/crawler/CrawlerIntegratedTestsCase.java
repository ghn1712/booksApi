package com.ghn1712.guiabolso.books.crawler;

import static org.junit.Assert.assertEquals;

import java.io.UncheckedIOException;
import java.util.List;

import org.junit.Test;

import com.ghn1712.guiabolso.books.entities.Book;
import com.ghn1712.guiabolso.books.gateways.BooksListGateway;
import com.ghn1712.guiabolso.books.injection.modules.BooksModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class CrawlerIntegratedTestsCase {

    BooksListGateway crawler;

    @Test
    public void should_return_kotlin_language_website_list() {
        Injector injector = Guice.createInjector(new BooksModule());
        crawler = injector.getInstance(BooksListGateway.class);
        List<Book> response = crawler.listBooks();
        assertEquals(24, response.size());
    }

    @Test
    public void should_return_empty_list() {
        Injector injector = Guice.createInjector(new CrawlerModule());
        crawler = injector.getInstance(BooksListGateway.class);
        List<Book> response = crawler.listBooks();
        assertEquals(0, response.size());
    }

    @Test(expected = UncheckedIOException.class)
    public void should_throw_exception_when_cant_connect_to_url() {
        Injector injector = Guice.createInjector(new CrawlerExceptionModule());
        crawler = injector.getInstance(BooksListGateway.class);
        crawler.listBooks();
    }
}
