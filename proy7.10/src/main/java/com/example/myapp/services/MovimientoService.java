package com.example.myapp.services;

import java.util.List;

import com.example.myapp.domain.Cuenta;
import com.example.myapp.domain.Movimiento;

public interface MovimientoService {
    void añadir(Movimiento movimiento);

    List<Movimiento> obtenerPorCuenta(Cuenta cuenta);

    // No es necesario ya que Cuenta: cascade = CascadeType.REMOVE
    // void deleteByIban(String IBAN);
}
