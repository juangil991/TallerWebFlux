package com.sofka.biblioteca.repository;

import com.sofka.biblioteca.dto.BooksDto;
import com.sofka.biblioteca.models.Books;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface BooksRepository extends ReactiveMongoRepository<Books,String> {
    Flux<Books> findByEstado(String estado);
    Flux<Books> findByArea(String area);
    Flux<Books> findByTipo(String tipo);
    Flux<Books> findByTipoAndArea(String tipo,String area);
}
