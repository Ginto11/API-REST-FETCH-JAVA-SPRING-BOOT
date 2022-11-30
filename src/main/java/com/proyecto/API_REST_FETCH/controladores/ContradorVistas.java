package com.proyecto.API_REST_FETCH.controladores;

import com.proyecto.API_REST_FETCH.entidades.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContradorVistas {
        
        @Autowired
        ControladorAuth auth;
        
        @GetMapping("/")
        public String getHome(){
                return "login";
        }
        
        @PostMapping("/")
        public String autenticar(@ModelAttribute Usuario usuario, Model model){
                String valor = auth.verificarUsuario(usuario, model);
                if(valor.equals("login")){
                        return valor;
                }
                model.addAttribute("token", valor);
                return "index";
        }
}
