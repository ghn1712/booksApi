package com.ghn1712.guiabolso.books.server;

import static spark.Spark.get;

import org.apache.commons.lang3.StringUtils;

import com.ghn1712.guiabolso.books.controllers.BooksController;
import com.ghn1712.guiabolso.books.injection.modules.BooksModule;
import com.ghn1712.guiabolso.books.serializer.Serializer;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Application {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new BooksModule());
        BooksController controller = injector.getInstance(BooksController.class);
        get("/books", (req, res) -> Serializer.serialize(controller.listBooks()));
        get("/books/:id",
                (req, res) -> controller.getBookById(req.params("id")).map(Serializer::serialize).orElseGet(() -> {
                    res.status(404);
                    return StringUtils.EMPTY;
                }));
    }
}
