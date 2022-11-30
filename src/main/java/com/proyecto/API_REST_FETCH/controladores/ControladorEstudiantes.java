package com.proyecto.API_REST_FETCH.controladores;

import com.proyecto.API_REST_FETCH.entidades.Estudiante;
import com.proyecto.API_REST_FETCH.servicios.ServicioEstudianteImple;
import com.proyecto.API_REST_FETCH.utilerias.JWTUtil;
import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/universidad")
public class ControladorEstudiantes {
        
        @Autowired
        private ServicioEstudianteImple servicioEstudiante;
        
        @Autowired
        private JWTUtil jwt;
        
       @PostMapping("/{idUniversidad}/estudiantes")
       public Estudiante universidadRegistrarEstudiante(@PathVariable("idUniversidad") Integer idUniversidad, @RequestBody Estudiante estudiante) throws Exception{
               return servicioEstudiante.registrarEstudianteUniversidad(idUniversidad, estudiante);
       }

       @GetMapping("/{idUniversidad}")
       public List<Estudiante> listarEstudiantesUniversidad(@PathVariable("idUniversidad") Integer idUniversidad){
               
               
        return servicioEstudiante.listarEstudiantePorUniversidad(idUniversidad);
       }

       @GetMapping("/{idUniversidad}/estudiante/{idEstudiante}")
       public Estudiante buscarEstudiantePorId(@PathVariable("idUniversidad") Integer idUniversidad, @PathVariable("idEstudiante") Integer idEstudiante, @RequestHeader(value = "Authorization") String token) throws Exception{
               if(verificarToken(token)){ return null; } 
               return servicioEstudiante.buscarEstudiantePorId(idUniversidad, idEstudiante);
       }


       @PutMapping("/{idUniversidad}/estudiante/{idEstudiante}")
       public Estudiante actualizarEstudianteUniversidad(@PathVariable("idUniversidad") Integer idUniversidad, @PathVariable("idEstudiante") Integer idEstudiante,  @RequestBody Estudiante estudiante, @RequestHeader(value = "Authorization") String token) throws Exception{
               if(verificarToken(token)){ return null; } 
               return servicioEstudiante.actualizarEstudianteUniversidad(idUniversidad, idEstudiante, estudiante);
       }
       
       @DeleteMapping("/{idUniversidad}/estudiante/{idEstudiante}")
       public void eliminarEstudianteUniversidad(@PathVariable("idUniversidad") Integer idUniversidad, @PathVariable("idEstudiante") Integer idEstudiante, @RequestHeader(value = "Authorization") String token) throws Exception{
               if(verificarToken(token)){ return; }
               servicioEstudiante.eliminarEstudianteUniversidad(idUniversidad, idEstudiante);
       }
       
       private boolean verificarToken(String token){
               String usuarioId = jwt.getKey(token);
               return usuarioId != null;
       }
}
