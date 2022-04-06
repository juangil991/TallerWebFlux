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
        return booksRepository.save(newBook).
                flatMap(p->{return Mono.just(modelMapper.map(p,BooksDto.class));});
    }

    public Mono<BooksDto> searchBook(String id){
        return booksRepository.findById(id).
                flatMap(p->{return Mono.just(modelMapper.map(p,BooksDto.class));});
    }

    public Flux<BooksDto> getAllBooks(){
        return booksRepository.findAll().flatMap(p->{return Flux.just(modelMapper.map(p,BooksDto.class));});

    }


    public Flux<BooksDto> findByEstado(String estado){
        return booksRepository.findByEstado(estado)
                .flatMap(p->{return Mono.just(modelMapper.map(p,BooksDto.class));});
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
        return booksRepository.findByArea(area)
                .flatMap(p->{return Flux.just(modelMapper.map(p,BooksDto.class));});
    }

    public Flux<BooksDto> findByTipo(String tipo){
        return booksRepository.findByTipo(tipo)
                .flatMap(p->{return Flux.just(modelMapper.map(p,BooksDto.class));});
    }

    public Flux<BooksDto> findByTipoAndArea(String area, String tipo){

        return booksRepository.findByTipoAndArea(tipo,area)
                .flatMap(p->{return Flux.just(modelMapper.map(p,BooksDto.class));});
    }
}
