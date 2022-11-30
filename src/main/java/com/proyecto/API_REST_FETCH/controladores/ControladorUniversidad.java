package com.proyecto.API_REST_FETCH.controladores;

import com.proyecto.API_REST_FETCH.entidades.Universidad;
import com.proyecto.API_REST_FETCH.servicios.ServicioUniversidadImple;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/universidades")
@RestController
public class ControladorUniversidad {
        
        @Autowired
        private ServicioUniversidadImple servicioUniversidad;
        
        @GetMapping
        public List<Universidad> listarUniversidades(){
                return servicioUniversidad.listar();
        }
        
        @GetMapping("/{idUniversidad}")
        public Universidad buscarUniversidadPorId(@PathVariable("idUniversidad") Integer idUniversidad) throws Exception{
                return servicioUniversidad.buscarPorId(idUniversidad);
        }
        
        @PostMapping
        public Universidad registrarUniversidad(@RequestBody Universidad universidad){
                return servicioUniversidad.registrar(universidad);
        }
        
        @PutMapping
        public Universidad actualizarUniversidad(@RequestBody Universidad universidad){
                return servicioUniversidad.actualizar(universidad);
        }
        
        @DeleteMapping
        public void eliminarUniversidad(@PathVariable("idUniversidad") Integer idUniversidad){
                servicioUniversidad.eliminar(idUniversidad);
        }
}
