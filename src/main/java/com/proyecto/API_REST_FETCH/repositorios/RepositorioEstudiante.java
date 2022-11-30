package com.proyecto.API_REST_FETCH.repositorios;

import com.proyecto.API_REST_FETCH.entidades.Estudiante;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioEstudiante extends JpaRepository<Estudiante, Integer>{
        public List<Estudiante> findByUniversidadIdUniversidad(Integer idUniversidad);
}
