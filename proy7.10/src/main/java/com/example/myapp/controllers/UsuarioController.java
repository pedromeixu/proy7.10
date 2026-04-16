package com.example.myapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.myapp.domain.Usuario;
import com.example.myapp.services.UsuarioService;

import org.springframework.ui.Model;


@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    
    @Autowired
    public UsuarioService usuarioService;

    @GetMapping("/listAll")
    public String showAllUsers(Model model) {
        model.addAttribute("listaUsuarios", usuarioService.getAllUsuarios());
        return "usuarios/listUsuarios";
    }

    @GetMapping("/new")
    public String newUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/formUsuario";
    }

    @PostMapping("/save")
    public String saveUsuario(@ModelAttribute("usuario") Usuario usuario) {
        usuarioService.saveUsuario(usuario);
        return "redirect:/usuarios/listAll";
    }

    @GetMapping("/edit/{id}")
    public String editUsuario(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.getById(id)
            .orElseThrow(() -> new IllegalArgumentException("ID de usuario no válido " + id));

        model.addAttribute("usuario", usuario);
        return "usuarios/formUsuario";
    }

    @GetMapping("/delete/{id}")
    public String deleteUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.getById(id)
            .orElseThrow(() -> new IllegalArgumentException("ID de usuario no válido " + id));

        usuarioService.deleteUsuario(usuario);
        return "redirect:/usuarios/listAll";
    }
}
