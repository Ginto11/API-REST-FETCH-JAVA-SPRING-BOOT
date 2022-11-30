package com.proyecto.API_REST_FETCH.servicios;

import com.proyecto.API_REST_FETCH.entidades.Usuario;

public interface ServicioUsuario {
        
        public Usuario registrarUsuario(Usuario usuario);
        public Usuario verificarUsuario(Usuario usuario);
}
