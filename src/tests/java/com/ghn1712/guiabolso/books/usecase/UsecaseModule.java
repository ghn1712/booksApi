package com.ghn1712.guiabolso.books.usecase;

import javax.inject.Singleton;

import com.ghn1712.guiabolso.books.config.ConfigProvider;
import com.ghn1712.guiabolso.books.config.ConfigProviderImpl;
import com.ghn1712.guiabolso.books.config.CrawlerConfig;
import com.ghn1712.guiabolso.books.config.DatabaseConfig;
import com.ghn1712.guiabolso.books.database.DatabaseConnectionHandler;
import com.ghn1712.guiabolso.books.database.DatabaseConnectionHandlerPostgres;
import com.ghn1712.guiabolso.books.gateways.BooksCrawlerGateway;
import com.ghn1712.guiabolso.books.gateways.BooksGateway;
import com.ghn1712.guiabolso.books.gateways.BooksListGateway;
import com.ghn1712.guiabolso.books.gateways.BooksRepositoryGateway;
import com.ghn1712.guiabolso.books.usecases.BooksUsecase;
import com.ghn1712.guiabolso.books.usecases.BooksUsecaseImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class UsecaseModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(BooksUsecase.class).to(BooksUsecaseImpl.class);
        bind(ConfigProvider.class).to(ConfigProviderImpl.class).in(Singleton.class);
        bind(DatabaseConnectionHandler.class).to(DatabaseConnectionHandlerPostgres.class).in(Singleton.class);
        bind(BooksGateway.class).to(BooksRepositoryGateway.class);
        bind(BooksListGateway.class).to(BooksCrawlerGateway.class);
    }

    @Provides
    public DatabaseConfig provideDatabaseConfig(ConfigProvider configProvider) {
        return configProvider.getConfig().getDatabaseConfig();
    }

    @Provides
    public CrawlerConfig provideCrawlerConfig(ConfigProvider configProvider) {
        CrawlerConfig crawlerConfig = new CrawlerConfig();
        crawlerConfig.setUrl("http://google.com");
        return crawlerConfig;
    }
}
