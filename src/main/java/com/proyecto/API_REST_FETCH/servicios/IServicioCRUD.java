package com.proyecto.API_REST_FETCH.servicios;

import java.util.List;

public interface IServicioCRUD<Objeto> {
        
        public Objeto registrar(Objeto objeto);
        public void eliminar(Integer id);
        public Objeto buscarPorId(Integer id) throws Exception;
        public List<Objeto> listar();
        public Objeto actualizar(Objeto objeto);
        
}
