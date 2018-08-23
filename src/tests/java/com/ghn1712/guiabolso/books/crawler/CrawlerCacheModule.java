package com.ghn1712.guiabolso.books.crawler;

import com.ghn1712.guiabolso.books.config.CrawlerConfig;
import com.ghn1712.guiabolso.books.gateways.BooksCrawlerGateway;
import com.ghn1712.guiabolso.books.gateways.BooksListGateway;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class CrawlerCacheModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(BooksListGateway.class).to(BooksCrawlerGateway.class);
    }

    @Provides
    public CrawlerConfig provideCrawlerConfig() {
        CrawlerConfig crawlerConfig = new CrawlerConfig();
        crawlerConfig.setUrl("https://kotlinlang.org/docs/books.html");
        crawlerConfig.setCache(true);
        crawlerConfig.setStartupCache(false);
        return crawlerConfig;
    }
}
