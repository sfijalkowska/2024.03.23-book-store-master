package pl.comarch.camp.it.book.store.controllers.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.comarch.camp.it.book.store.model.Book;
import pl.comarch.camp.it.book.store.model.User;

@RestController
public class SimpleRestController {

    @RequestMapping(path = "/rest/test1", method = RequestMethod.GET)
    public Book test1() {
        Book book = new Book(1, "Java. Podręcznik na start",
                "Krzysztof Krocz", "978-83-283-9783-5", 44.00, 10);
        return book;
    }

    @RequestMapping(path = "/rest/test2", method = RequestMethod.GET)
    public void test2() {
        System.out.println("metoda sie wywolala !!!");
    }

    @RequestMapping(path = "/rest/test3/{imie}/{nazwisko}", method = RequestMethod.GET)
    public User test3(@PathVariable String imie,
                      @PathVariable String nazwisko,
                      @RequestParam int wiek) {
        User user = new User();
        user.setName(imie);
        user.setSurname(nazwisko);
        System.out.println(wiek);

        user.setId(10);
        user.setRole(User.Role.ADMIN);
        user.setLogin(imie.toLowerCase());
        user.setPassword("SDFG7896sd8f798s7df987gsdf");
        return user;
    }

    @RequestMapping(path = "/rest/test4/{imie}/{nazwisko}", method = RequestMethod.POST)
    public Book test4(@PathVariable String imie,
                      @PathVariable String nazwisko,
                      @RequestParam int wiek,
                      @RequestParam int id,
                      @RequestHeader String naglowek1,
                      @RequestHeader String naglowek2,
                      @RequestBody User user) {
        System.out.println(imie);
        System.out.println(nazwisko);
        System.out.println(wiek);
        System.out.println(id);
        System.out.println(naglowek1);
        System.out.println(naglowek2);
        System.out.println(user);

        Book book = new Book(1, "Java. Kompendium programisty. Wydanie XII",
                "Herbert Schildt", "978-83-832-2156-4", 129.35, 10);
        return book;
    }

    @RequestMapping(path = "/rest/test5", method = RequestMethod.GET)
    public ResponseEntity<String> test5() {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("naglowek1", "jakas wartosc 1")
                .header("naglowek 2", "jakas wartosc 2")
                .body("jakies body");
    }

    @RequestMapping(path = "/rest/test6", method = RequestMethod.POST)
    public User test6(@RequestBody User user) {
        user.setId(100);
        user.setRole(User.Role.ADMIN);

        return user;
    }
}
