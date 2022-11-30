package com.proyecto.API_REST_FETCH.servicios;

import com.proyecto.API_REST_FETCH.entidades.Usuario;
import com.proyecto.API_REST_FETCH.repositorios.RepositorioUsuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioUsuarioImple implements ServicioUsuario{
        
        @Autowired
        RepositorioUsuario repositorioUsuario;
        
        @PersistenceContext
        EntityManager entityManager;

        @Override
        public Usuario registrarUsuario(Usuario usuario) {
                return repositorioUsuario.save(usuario);
        }

        @Override
        public Usuario verificarUsuario(Usuario usuario) {
               String query = "FROM Usuario WHERE nombre = :nombre";
               
               Usuario usuarioEncontrado = (Usuario) entityManager.createQuery(query)
                       .setParameter("nombre", usuario.getNombre())
                       .getSingleResult();
               
               if(usuarioEncontrado == null){
                       return null;
               }
               
                       
               String passwordHashed = usuarioEncontrado.getPassword();
               
               Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
               
               if(argon2.verify(passwordHashed, usuario.getPassword())){
                       return usuarioEncontrado;
               }
               
               return null;
        }
        
        
}
