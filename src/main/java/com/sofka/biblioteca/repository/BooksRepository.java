package com.sofka.biblioteca.repository;

import com.sofka.biblioteca.dto.BooksDto;
import com.sofka.biblioteca.models.Books;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends MongoRepository<Books,String>  {
    List<Books> findByEstado(String estado);
    List<Books> findByArea(String area);
    List<Books> findByTipo(String tipo);
    List<Books> findByTipoAndArea(String tipo,String area);
}
