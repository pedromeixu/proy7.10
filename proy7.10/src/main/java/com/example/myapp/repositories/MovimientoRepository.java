package com.example.myapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myapp.domain.Cuenta;
import com.example.myapp.domain.Movimiento;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    //Puesto que tengo un atributo que es una clase Cuenta, dentro de mi repositorio, puedo buscar por su identificador

    List<Movimiento> findByCuenta(Cuenta cuenta);

    // El método anterior por  @Query podría ser asi:
    // @Query("select m from Movimiento m where m.iban=?1")
    // List<Movimiento> findByCuenta(String iban);


    // No es necesario borrado de mov. de una cuenta ya que:
    // @OnDelete(action = OnDeleteAction.CASCADE)
    // @Query("delete from Movimiento 
}