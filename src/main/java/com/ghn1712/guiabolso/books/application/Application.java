package com.ghn1712.guiabolso.books.application;

import com.ghn1712.guiabolso.books.injection.modules.InjectorProvider;
import com.ghn1712.guiabolso.books.server.Server;

public class Application {

    public static void main(String[] args) {
        InjectorProvider.getInjector().getInstance(Server.class).start(4567);
    }
}
