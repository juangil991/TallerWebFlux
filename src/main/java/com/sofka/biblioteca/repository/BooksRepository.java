package com.sofka.biblioteca.repository;

import com.sofka.biblioteca.models.Books;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends MongoRepository<Books,String>  {
    Books findByEstado(String estado);
    Books findByArea(String area);
    Books findByTipo(String tipo);
    Books findByTipoAndArea(String tipo,String area);
}
