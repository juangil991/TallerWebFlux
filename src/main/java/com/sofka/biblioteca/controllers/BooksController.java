package com.sofka.biblioteca.controllers;

import com.sofka.biblioteca.dto.BooksDto;
import com.sofka.biblioteca.services.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class BooksController {

    @Autowired
    BooksService booksService;

    @GetMapping("/books")
    private BooksDto getbooks(){
        return booksService.getAllBooks();
    }

    @GetMapping("/{estado}")
    private BooksDto getbooksByEstado(@PathVariable("estado") String estado){
        return booksService.findByEstado(estado);
    }

    @PostMapping("/save")
    private BooksDto createBook(@RequestBody BooksDto book){
        return booksService.createBook(book);
    }

    @PutMapping("/lend/{id}")
    private BooksDto lendBooks(@PathVariable("id")String id){
        return booksService.lendBook(id);
    }
}
