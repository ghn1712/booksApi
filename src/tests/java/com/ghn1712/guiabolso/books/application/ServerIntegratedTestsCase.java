package com.ghn1712.guiabolso.books.application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.jetty.http.HttpStatus;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ghn1712.guiabolso.books.entities.Book;
import com.ghn1712.guiabolso.books.entities.BooksListResponse;
import com.ghn1712.guiabolso.books.gateways.BooksGateway;
import com.ghn1712.guiabolso.books.injection.modules.BooksModule;
import com.ghn1712.guiabolso.books.serializer.Serializer;
import com.ghn1712.guiabolso.books.server.Server;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ServerIntegratedTestsCase {

    static Server server;
    BooksGateway gateway;
    static Injector injector;

    @BeforeClass
    public static void start_server() {
        injector = Guice.createInjector(new BooksModule());
        server = injector.getInstance(Server.class);
        server.start(4567);
    }

    @Before
    public void set_up() {
        gateway = injector.getInstance(BooksGateway.class);
        gateway.truncate();
    }

    @Test
    public void should_return_404_when_get_books_that_doesnt_exists() throws UnirestException {
        HttpResponse<String> request = Unirest.get("http://localhost:4567/books/1").asString();
        assertEquals(HttpStatus.NOT_FOUND_404, request.getStatus());
    }

    @Test
    public void should_return_200_and_book_when_get_books_that_exists() throws UnirestException {
        Book book = new Book("title", "description", "1", "isbn", "lg");
        gateway.addBook(book);
        HttpResponse<String> request = Unirest.get("http://localhost:4567/books/1").asString();
        assertEquals(HttpStatus.OK_200, request.getStatus());
        assertEquals(book, Serializer.deserialize(request.getBody(), Book.class));
    }

    @Test
    public void should_return_400_when_add_book_without_all_fields() throws UnirestException {
        HttpResponse<String> request = Unirest.post("http://localhost:4567/books").body("{\"description\":\"lalala\"}")
                .asString();
        assertEquals(HttpStatus.BAD_REQUEST_400, request.getStatus());
    }

    @Test
    public void should_return_400_when_add_book_without_body() throws UnirestException {
        HttpResponse<String> request = Unirest.post("http://localhost:4567/books").asString();
        assertEquals(HttpStatus.BAD_REQUEST_400, request.getStatus());
    }

    @Test
    public void should_return_400_when_add_book_with_invalid_json() throws UnirestException {
        HttpResponse<String> request = Unirest.post("http://localhost:4567/books").body("\"description\"lalala")
                .asString();
        assertEquals(HttpStatus.BAD_REQUEST_400, request.getStatus());
    }

    @Test
    public void should_return_201_and_location_header_when_book_added() throws UnirestException {
        HttpResponse<String> request = Unirest.post("http://localhost:4567/books")
                .body(Serializer.serialize(new Book("title", "description", "isbn", "lg"))).asString();
        assertEquals(HttpStatus.CREATED_201, request.getStatus());
        assertEquals("localhost:4567/books/1", request.getHeaders().get("Location").get(0));
        assertEquals(HttpStatus.OK_200,
                Unirest.get("http://" + request.getHeaders().get("Location").get(0)).asString().getStatus());
    }

    @Test
    public void should_return_200_when_list_books() throws UnirestException {
        HttpResponse<String> request = Unirest.get("http://localhost:4567/books").asString();
        assertEquals(HttpStatus.OK_200, request.getStatus());
        BooksListResponse booksListResponse = Serializer.deserialize(request.getBody(), BooksListResponse.class);
        assertEquals(26, booksListResponse.getNumberBooks());
        // TODO assert content
        // assertTrue(booksListReponse.getBooks().containsAll(expected);
    }

    @Test
    public void should_return_200_and_empty_list() throws UnirestException {
        Injector serverInjector = Guice.createInjector(new ServerModule());
        serverInjector.getInstance(Server.class).start(4568);
        HttpResponse<String> request = Unirest.get("http://localhost:4568/books").asString();
        assertEquals(HttpStatus.OK_200, request.getStatus());
        BooksListResponse booksListResponse = Serializer.deserialize(request.getBody(), BooksListResponse.class);
        assertEquals(0, booksListResponse.getNumberBooks());
        assertTrue(booksListResponse.getBooks().isEmpty());
    }

    @AfterClass
    public static void tear_down() {
        injector.getInstance(BooksGateway.class).truncate();
    }
}
