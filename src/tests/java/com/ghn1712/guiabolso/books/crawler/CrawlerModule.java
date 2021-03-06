package com.ghn1712.guiabolso.books.crawler;

import com.ghn1712.guiabolso.books.config.CrawlerConfig;
import com.ghn1712.guiabolso.books.gateways.BooksCrawlerGateway;
import com.ghn1712.guiabolso.books.gateways.BooksListGateway;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class CrawlerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(BooksListGateway.class).to(BooksCrawlerGateway.class);
    }

    @Provides
    public CrawlerConfig provideCrawlerConfig() {
        CrawlerConfig crawlerConfig = new CrawlerConfig();
        crawlerConfig.setUrl("http://google.com");
        crawlerConfig.setCache(false);
        crawlerConfig.setStartupCache(false);
        return crawlerConfig;
    }
}
