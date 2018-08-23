package com.ghn1712.guiabolso.books.injection.modules;

import javax.inject.Singleton;

import com.ghn1712.guiabolso.books.config.ConfigProvider;
import com.ghn1712.guiabolso.books.config.ConfigProviderImpl;
import com.ghn1712.guiabolso.books.config.CrawlerConfig;
import com.ghn1712.guiabolso.books.config.DatabaseConfig;
import com.ghn1712.guiabolso.books.controllers.BooksController;
import com.ghn1712.guiabolso.books.controllers.BooksControllerImpl;
import com.ghn1712.guiabolso.books.crawler.AmazonStrategy;
import com.ghn1712.guiabolso.books.crawler.FundamentalKotlinStrategy;
import com.ghn1712.guiabolso.books.crawler.IsbnRetrieverStrategy;
import com.ghn1712.guiabolso.books.crawler.KuramkitapStrategy;
import com.ghn1712.guiabolso.books.crawler.ManningStrategy;
import com.ghn1712.guiabolso.books.crawler.PacktpubStrategy;
import com.ghn1712.guiabolso.books.crawler.UnavailableStrategy;
import com.ghn1712.guiabolso.books.database.DatabaseConnectionHandler;
import com.ghn1712.guiabolso.books.database.DatabaseConnectionHandlerPostgres;
import com.ghn1712.guiabolso.books.gateways.BooksCrawlerGateway;
import com.ghn1712.guiabolso.books.gateways.BooksGateway;
import com.ghn1712.guiabolso.books.gateways.BooksListGateway;
import com.ghn1712.guiabolso.books.gateways.BooksRepositoryGateway;
import com.ghn1712.guiabolso.books.server.Server;
import com.ghn1712.guiabolso.books.server.ServerImpl;
import com.ghn1712.guiabolso.books.usecases.BooksUsecase;
import com.ghn1712.guiabolso.books.usecases.BooksUsecaseImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;

public class BooksModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(BooksController.class).to(BooksControllerImpl.class);
        bind(BooksUsecase.class).to(BooksUsecaseImpl.class);
        bind(ConfigProvider.class).to(ConfigProviderImpl.class).in(Singleton.class);
        bind(DatabaseConnectionHandler.class).to(DatabaseConnectionHandlerPostgres.class).in(Singleton.class);
        bind(BooksGateway.class).to(BooksRepositoryGateway.class);
        bind(BooksListGateway.class).to(BooksCrawlerGateway.class);
        bind(Server.class).to(ServerImpl.class);
        bind(IsbnRetrieverStrategy.class).annotatedWith(Names.named("amazon")).to(AmazonStrategy.class);
        bind(IsbnRetrieverStrategy.class).annotatedWith(Names.named("packtpub")).to(PacktpubStrategy.class);
        bind(IsbnRetrieverStrategy.class).annotatedWith(Names.named("kuramkitap")).to(KuramkitapStrategy.class);
        bind(IsbnRetrieverStrategy.class).annotatedWith(Names.named("manning")).to(ManningStrategy.class);
        bind(IsbnRetrieverStrategy.class).annotatedWith(Names.named("unavailable")).to(UnavailableStrategy.class);
        bind(IsbnRetrieverStrategy.class).annotatedWith(Names.named("fundamental-kotlin"))
                .to(FundamentalKotlinStrategy.class);
    }

    @Provides
    public DatabaseConfig provideDatabaseConfig(ConfigProvider configProvider) {
        return configProvider.getConfig().getDatabaseConfig();
    }

    @Provides
    public CrawlerConfig provideCrawlerConfig(ConfigProvider configProvider) {
        return configProvider.getConfig().getCrawlerConfig();
    }
}
