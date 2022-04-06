package com.sofka.biblioteca.controllers;

import com.sofka.biblioteca.dto.BooksDto;
import com.sofka.biblioteca.services.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.SpringServletContainerInitializer;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class BooksController {

    @Autowired
    BooksService booksService;

    @GetMapping("/books")
    private Flux<BooksDto> getbooks(){
        return booksService.getAllBooks();
    }

    @GetMapping("/id/{id}")
    private Mono<BooksDto> getById(@PathVariable("id")String id){
        return booksService.searchBook(id);
    }

    @GetMapping("/name/{estado}")
    private Flux<BooksDto> getbooksByEstado(@PathVariable("estado") String estado){
        return booksService.findByEstado(estado);
    }

    @PostMapping("/save")
    private Mono<BooksDto> createBook(@RequestBody BooksDto book){
        return booksService.createBook(book);
    }

    @PutMapping("/lend/{id}")
    private String lendBooks(@PathVariable("id")String id){
        return booksService.lendBook(id);
    }

    @PutMapping("/return/{id}")
    private String returnBooks(@PathVariable("id")String id){
        return booksService.returnBook(id);
    }

    @GetMapping("/tipo/{tipo}")
    private Flux<BooksDto> getByTipo(@PathVariable("tipo") String tipo){
        return booksService.findByTipo(tipo);
    }
    @GetMapping("estado/{id}")
    private String getEstado(@PathVariable("id")String id){
        return booksService.statusBook(id);
    }

    @GetMapping("/area/{area}")
    private Flux<BooksDto> getByArea(@PathVariable("area")String area){
        return booksService.findByArea(area);
    }

    @GetMapping("/areatipo/{area}/{tipo}")
    private Flux<BooksDto> getByAreaAndTipo(@PathVariable("area")String area,@PathVariable("tipo")String tipo){
        return booksService.findByTipoAndArea(area,tipo);
    }

}
