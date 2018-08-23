package com.ghn1712.guiabolso.books.injection.modules;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class InjectorProvider {

    private InjectorProvider() {
    }

    private static final Injector injector = Guice.createInjector(new BooksModule());

    public static Injector getInjector() {
        return injector;
    }
}
