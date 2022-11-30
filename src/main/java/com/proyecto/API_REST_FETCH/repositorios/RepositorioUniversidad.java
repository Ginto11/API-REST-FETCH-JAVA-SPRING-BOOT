package com.proyecto.API_REST_FETCH.repositorios;

import com.proyecto.API_REST_FETCH.entidades.Universidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioUniversidad extends JpaRepository<Universidad, Integer>{
        
}
