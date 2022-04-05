package com.sofka.biblioteca.services;

import com.sofka.biblioteca.dto.BooksDto;
import com.sofka.biblioteca.models.Books;
import com.sofka.biblioteca.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

@Service
public class BooksService {
    @Autowired
    BooksRepository booksRepository;

    private ModelMapper modelMapper= new ModelMapper();

    public BooksDto createBook(BooksDto book){
        Books newBook = modelMapper.map(book,Books.class);
        return modelMapper.map(booksRepository.save(newBook),BooksDto.class);
    }

    public BooksDto searchBook(String id){
        return modelMapper.map(booksRepository.findById(id),BooksDto.class);
    }

    public BooksDto getAllBooks(){
        return modelMapper.map(booksRepository.findAll(),BooksDto.class);
    }

    public BooksDto findByEstado(String estado){
        return modelMapper.map(booksRepository.findByEstado(estado),BooksDto.class);
    }

    public BooksDto lendBook(String id){
        BooksDto book = searchBook(id);
        book.setFecha(LocalDate.now());
        book.setEstado("Lend");
        return modelMapper.map(createBook(book),BooksDto.class);
    }

    public BooksDto returnBook(String id){
        BooksDto book = searchBook(id);
        book.setEstado("Free");
        return modelMapper.map(createBook(book),BooksDto.class);
    }

    public BooksDto findByArea(String area){
        return modelMapper.map(booksRepository.findByArea(area),BooksDto.class);
    }

    public BooksDto findByTipo(String tipo){
        return modelMapper.map(booksRepository.findByArea(tipo),BooksDto.class);
    }

    public BooksDto findByTipoAndArea(String tipo, String area){
        return modelMapper.map(booksRepository.findByTipoAndArea(tipo,area),BooksDto.class);
    }
}
