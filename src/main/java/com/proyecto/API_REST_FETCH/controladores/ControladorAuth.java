package com.proyecto.API_REST_FETCH.controladores;

import com.proyecto.API_REST_FETCH.entidades.Usuario;
import com.proyecto.API_REST_FETCH.servicios.ServicioUsuarioImple;
import com.proyecto.API_REST_FETCH.utilerias.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class ControladorAuth {
        
        @Autowired
        private ServicioUsuarioImple servicioUsuario;
        
        @Autowired
        private JWTUtil jwt;
        
        @PostMapping("/usuario")
        public Usuario registrarUsuario(@RequestBody Usuario usuario){
                
                Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
                
                String hash = argon2.hash(1, 1024, 1, usuario.getPassword());
                
                usuario.setPassword(hash);
                
                return servicioUsuario.registrarUsuario(usuario);
        }
        
        
        @PostMapping("/verificar")
        public String verificarUsuario(@ModelAttribute Usuario usuario, Model model){
                Usuario usuarioEncontrado = servicioUsuario.verificarUsuario(usuario);
                if(usuarioEncontrado == null){
                        model.addAttribute("error", "Usuario no encontrado");
                        return "login";
                }
                
                String token = jwt.create(String.valueOf(usuarioEncontrado.getId()), usuarioEncontrado.getNombre());
                
                return token;
        }
}
