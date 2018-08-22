package com.ghn1712.guiabolso.books.application;

import com.ghn1712.guiabolso.books.injection.modules.BooksModule;
import com.ghn1712.guiabolso.books.server.Server;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Application {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new BooksModule());
        Server server = injector.getInstance(Server.class);
        server.start(4567);
    }
}
