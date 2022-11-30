package com.proyecto.API_REST_FETCH.servicios;

import com.proyecto.API_REST_FETCH.entidades.Estudiante;
import java.util.List;

public interface ServicioEstudiantes {
      
        public Estudiante registrarEstudianteUniversidad(Integer idUniversidad, Estudiante estudiante) throws Exception;
        public void eliminarEstudianteUniversidad(Integer idUniversidad, Integer idEstudiante) throws Exception;
        public Estudiante actualizarEstudianteUniversidad(Integer idUniversidad, Integer idEstudiante, Estudiante estudiante) throws Exception;
        public List<Estudiante> listarEstudiantePorUniversidad(Integer idUniversidad);
        public Estudiante buscarEstudiantePorId(Integer idPublicacion, Integer idEstuadiante) throws Exception;
}
