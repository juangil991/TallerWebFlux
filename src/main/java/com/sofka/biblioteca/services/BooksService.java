package com.sofka.biblioteca.services;

import com.sofka.biblioteca.dto.BooksDto;
import com.sofka.biblioteca.models.Books;
import com.sofka.biblioteca.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.List;

@Service
public class BooksService {
    @Autowired
    BooksRepository booksRepository;

    private ModelMapper modelMapper= new ModelMapper();

    public Mono<BooksDto> createBook(BooksDto book){
        Books newBook = modelMapper.map(book,Books.class);
        return Mono.just(modelMapper.map(booksRepository.save(newBook),BooksDto.class));
    }

    public Mono<BooksDto> searchBook(String id){
        return Mono.just(modelMapper.map(booksRepository.findById(id).get(),BooksDto.class));
    }

    public Flux<BooksDto> getAllBooks(){
        return Flux.just(modelMapper.map(booksRepository.findAll(),BooksDto.class));

    }


    public Mono<BooksDto> findByEstado(String estado){
        return Mono.just(modelMapper.map(booksRepository.findByEstado(estado),BooksDto.class));
    }

    public String lendBook(String id){
        Mono<BooksDto> book= searchBook(id);
        String result=(book.block().getEstado().equals("Free"))? "Prestado con exito":
                "El recurso no se encuentra en Libre";
        book.block().setFecha(LocalDate.now());
        book.block().setEstado("Lend");
        modelMapper.map(createBook(book.block()),BooksDto.class);
        return result;
    }

    public String returnBook(String id){
        Mono<BooksDto> book = searchBook(id);
        String result=(book.block().getEstado().equals("Lend"))? "Devuelto con exito":
                "El recurso no se encuentra en prestamo";
        book.block().setEstado("Free");
        createBook(book.block());
        return result;
    }

    public String statusBook(String id){
        Mono<BooksDto> book = searchBook(id);
        return book.block().getEstado().equals("Lend")?"Prestado el :"+book.block().getFecha().toString():"Disponible";
    }

    public Flux<BooksDto> findByArea(String area){
        return Flux.just(modelMapper.map(booksRepository.findByArea(area),BooksDto.class));
    }

    public Flux<BooksDto> findByTipo(String tipo){
        return Flux.just(modelMapper.map(booksRepository.findByTipo(tipo),BooksDto.class));
    }

    public Flux<BooksDto> findByTipoAndArea(String area, String tipo){

        return Flux.just(modelMapper.map(booksRepository.findByTipoAndArea(tipo,area),BooksDto.class));
    }
}
