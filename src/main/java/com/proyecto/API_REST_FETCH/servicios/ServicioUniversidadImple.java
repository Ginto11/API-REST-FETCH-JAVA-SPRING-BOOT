package com.proyecto.API_REST_FETCH.servicios;

import com.proyecto.API_REST_FETCH.entidades.Universidad;
import com.proyecto.API_REST_FETCH.repositorios.RepositorioUniversidad;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioUniversidadImple implements IServicioCRUD<Universidad>{
        
        
        @Autowired
        private RepositorioUniversidad repositorioUniversidad;

        @Override
        public Universidad registrar(Universidad objeto) {
                return repositorioUniversidad.save(objeto);
        }

        @Override
        public void eliminar(Integer id) {
                repositorioUniversidad.deleteById(id);
        }

        @Override
        public Universidad buscarPorId(Integer id) throws Exception {
                return repositorioUniversidad.findById(id).orElseThrow(() ->new Exception("No se encuentra ninguna universidad asociada al id " + id));
        }

        @Override
        public List<Universidad> listar() {
                return repositorioUniversidad.findAll();
        }

        @Override
        public Universidad actualizar(Universidad objeto) {
                return repositorioUniversidad.save(objeto);
        }
        
}
