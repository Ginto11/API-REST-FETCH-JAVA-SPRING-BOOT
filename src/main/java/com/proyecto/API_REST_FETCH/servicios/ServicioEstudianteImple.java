package com.proyecto.API_REST_FETCH.servicios;

import com.proyecto.API_REST_FETCH.entidades.Estudiante;
import com.proyecto.API_REST_FETCH.entidades.Universidad;
import com.proyecto.API_REST_FETCH.repositorios.RepositorioEstudiante;
import com.proyecto.API_REST_FETCH.repositorios.RepositorioUniversidad;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ServicioEstudianteImple implements ServicioEstudiantes{
        
        @Autowired
        RepositorioEstudiante repositorioEstudiante;
        
        @Autowired
        RepositorioUniversidad repositorioUniversidad;

        @Override
        public Estudiante registrarEstudianteUniversidad(Integer idUniversidad, Estudiante estudiante) throws Exception{
                Estudiante estudianteEnviado = estudiante;
                
                Universidad universidad = repositorioUniversidad.findById(idUniversidad).orElseThrow(() -> new Exception("No se encuentra ninguna universidad asociada a ese id " + idUniversidad));
                
                estudianteEnviado.setUniversidad(universidad);
                
                Estudiante estudianteNuevo =  repositorioEstudiante.save(estudianteEnviado);
                return estudianteNuevo;
        }

        @Override
        public void eliminarEstudianteUniversidad(Integer idUniversidad, Integer idEstudiante) throws Exception {
                Estudiante estudiante = repositorioEstudiante.findById(idEstudiante).orElseThrow(() -> new Exception("No se encuentra ningun estudiante asociado al id " + idEstudiante));
                repositorioEstudiante.delete(estudiante);
                
        }

        @Override
        public Estudiante actualizarEstudianteUniversidad(Integer idUniversidad, Integer idEstudiante, Estudiante estudiante) throws Exception {
                Estudiante estudianteEnviado = repositorioEstudiante.findById(idEstudiante).orElseThrow(() -> new Exception("No se encontro ningun estudiante asociado al id " + idEstudiante));

                Universidad universidad = repositorioUniversidad.findById(idUniversidad).orElseThrow(() -> new Exception("No se encontro ninguna universidad asociada al id " + idUniversidad));
        
                if(!estudianteEnviado.getUniversidad().getIdUniversidad().equals(universidad.getIdUniversidad())){
                        throw new Exception("El estudiante no pertenece a la universidad");
                }

                estudianteEnviado.setNombre(estudiante.getNombre());
                estudianteEnviado.setApellido(estudiante.getApellido());
                estudianteEnviado.setEdad(estudiante.getEdad());


                Estudiante estudianteActualizado = repositorioEstudiante.save(estudianteEnviado);

                return estudianteActualizado;

        }

        @Override
        public List<Estudiante> listarEstudiantePorUniversidad(Integer idUniversidad) {
                List<Estudiante> estudiantes = repositorioEstudiante.findByUniversidadIdUniversidad(idUniversidad);
                return estudiantes.stream().collect(Collectors.toList());
        }

        @Override
        public Estudiante buscarEstudiantePorId(Integer idUniversidad, Integer idEstuadiante) throws Exception {
                Universidad universidad = repositorioUniversidad.findById(idUniversidad).orElseThrow(() -> new Exception("No se encuentra ninguna universidad asociada al id "+ idUniversidad));
                
                Estudiante estudiante = repositorioEstudiante.findById(idEstuadiante).orElseThrow(() -> new Exception("No se encontro un estudiaante asociado al id" + idEstuadiante));

                if(!estudiante.getUniversidad().getIdUniversidad().equals(universidad.getIdUniversidad())){
                        throw new Exception("El estudiante no pertenece a la universidad");
                }

                return estudiante;
        }
        
       
        
        
}
