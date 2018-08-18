package com.ghn1712.guiabolso.books.server;

import static spark.Spark.after;
import static spark.Spark.get;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.eclipse.jetty.http.HttpStatus;

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
                    res.status(HttpStatus.NOT_FOUND_404);
                    return StringUtils.EMPTY;
                }));
        after((req, res) -> res.type(ContentType.APPLICATION_JSON.toString()));
    }
}
