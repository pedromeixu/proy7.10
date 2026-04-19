package com.example.myapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.myapp.domain.Cuenta;
import com.example.myapp.domain.Rol;
import com.example.myapp.domain.Usuario;
import com.example.myapp.services.CuentaService;
import com.example.myapp.services.UsuarioService;

@SpringBootApplication
public class Main implements CommandLineRunner{

	@Autowired
	private UsuarioService usuarioService;
	
    @Autowired
    private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Bean
	CommandLineRunner initData(CuentaService cuentaService) {
		return args -> {
			cuentaService.añadir(new Cuenta("iban1", "alias1", 0));
			cuentaService.añadir(new Cuenta("iban2", "alias2", 0));
		};
	}

    @Override
    public void run(String... args) throws Exception {

        // Usuario ADMIN
        Usuario admin = new Usuario();
        admin.setNombre("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRol(Rol.ADMIN);
        usuarioService.saveUsuario(admin);

        // Usuario TITULAR
        Usuario titular = new Usuario();
        titular.setNombre("juan");
        titular.setPassword(passwordEncoder.encode("juan1234"));
        titular.setRol(Rol.TITULAR);
        usuarioService.saveUsuario(titular);

        // Usuario USUARIO
        Usuario usuario = new Usuario();
        usuario.setNombre("maria");
        usuario.setPassword(passwordEncoder.encode("maria123"));
        usuario.setRol(Rol.USUARIO);
        usuarioService.saveUsuario(usuario);

        System.out.println("Usuarios iniciales creados");
    }

}
