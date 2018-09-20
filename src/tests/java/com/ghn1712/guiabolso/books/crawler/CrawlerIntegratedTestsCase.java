package com.ghn1712.guiabolso.books.crawler;

import static org.awaitility.Awaitility.with;
import static org.junit.Assert.assertEquals;

import java.io.UncheckedIOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        assertEquals(26, response.size());
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

    @Test
    public void should_return_fast_when_cache_is_on() {
        Injector injector = Guice.createInjector(new CrawlerCacheModule());
        crawler = injector.getInstance(BooksListGateway.class);
        crawler.listBooks();
        with().pollDelay(5, TimeUnit.MILLISECONDS).await().atMost(50, TimeUnit.MILLISECONDS).untilAsserted(() -> {
            List<Book> secondResponse = crawler.listBooks();
            assertEquals(26, secondResponse.size());
        });
    }

    @Test
    public void should_return_fast_when_cache_on_startup_is_on() {
        Injector injector = Guice.createInjector(new CrawlerCacheStartupModule());
        crawler = injector.getInstance(BooksListGateway.class);
        with().pollDelay(5, TimeUnit.MILLISECONDS).await().atMost(50, TimeUnit.MILLISECONDS).untilAsserted(() -> {
            List<Book> secondResponse = crawler.listBooks();
            assertEquals(26, secondResponse.size());
        });
    }
}
