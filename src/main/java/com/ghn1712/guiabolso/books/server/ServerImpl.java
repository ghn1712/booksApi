package com.ghn1712.guiabolso.books.server;

import static spark.Service.ignite;
import static spark.Spark.halt;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpStatus;

import com.ghn1712.guiabolso.books.controllers.BooksController;
import com.ghn1712.guiabolso.books.entities.Book;
import com.ghn1712.guiabolso.books.entities.BooksListResponse;
import com.ghn1712.guiabolso.books.serializer.Serializer;
import com.google.gson.JsonParseException;

import spark.Service;
import spark.route.HttpMethod;

public class ServerImpl implements Server {

    private static final String BOOKS_PATH = "/books";
    private static final String BAD_REQUEST_MESSAGE = "{\"message\": \"Book json sent is invalid, check fields an schema\"}";
    private BooksController controller;

    @Inject
    public ServerImpl(BooksController controller) {
        this.controller = controller;
    }

    @Override
    public void start(int port) {
        Service http = ignite().port(port);
        http.before(BOOKS_PATH, (req, res) -> {
            if (req.requestMethod().equalsIgnoreCase(HttpMethod.post.toString()) && checkBookIsInvalid(req.body())) {
                halt(HttpStatus.BAD_REQUEST_400, BAD_REQUEST_MESSAGE);
            }
        });
        http.get(BOOKS_PATH, (req, res) -> Serializer.serialize(createBooksListResponse(controller.listBooks())));
        http.get("/books/:id",
                (req, res) -> controller.getBookById(req.params("id")).map(Serializer::serialize).orElseGet(() -> {
                    res.status(HttpStatus.NOT_FOUND_404);
                    return StringUtils.EMPTY;
                }));
        http.post(BOOKS_PATH, (req, res) -> {
            String createdId = controller.addBook(Serializer.deserialize(req.body(), Book.class));
            res.header(HttpHeader.LOCATION.toString(), req.host() + "/books/" + createdId);
            res.status(HttpStatus.CREATED_201);
            return StringUtils.EMPTY;
        });
        http.after((req, res) -> res.type(ContentType.APPLICATION_JSON.toString()));
    }

    private static BooksListResponse createBooksListResponse(List<Book> booksList) {
        return new BooksListResponse(booksList.size(), booksList);
    }

    private static boolean checkBookIsInvalid(String stringBook) {
        try {
            Book book = Serializer.deserialize(stringBook, Book.class);
            return (Objects.isNull(book) || Objects.isNull(book.getDescription()) || Objects.isNull(book.getIsbn())
                    || Objects.isNull(book.getLanguage()) || Objects.isNull(book.getTitle()));
        }
        catch (JsonParseException e) {
            return true;
        }
    }
}
