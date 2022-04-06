package com.sofka.biblioteca.services;

import com.sofka.biblioteca.dto.BooksDto;
import com.sofka.biblioteca.models.Books;
import com.sofka.biblioteca.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;

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
        return modelMapper.map(booksRepository.findById(id).get(),BooksDto.class);
    }

    public List<BooksDto> getAllBooks(){
        return modelMapper.map(booksRepository.findAll(),List.class);

    }


    public BooksDto findByEstado(String estado){
        return modelMapper.map(booksRepository.findByEstado(estado),BooksDto.class);
    }

    public String lendBook(String id){
        BooksDto book= searchBook(id);
        String result=(book.getEstado().equals("Free"))? "Prestado con exito":
                "El recurso no se encuentra en Libre";
        book.setFecha(LocalDate.now());
        book.setEstado("Lend");
        modelMapper.map(createBook(book),BooksDto.class);
        return result;
    }

    public String returnBook(String id){
        BooksDto book = searchBook(id);
        String result=(book.getEstado().equals("Lend"))? "Devuelto con exito":
                "El recurso no se encuentra en prestamo";
        book.setEstado("Free");
        createBook(book);
        return result;
    }

    public String statusBook(String id){
        BooksDto book = searchBook(id);
        return book.getEstado().equals("Lend")?"Prestado el :"+book.getFecha().toString():"Disponible";
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
