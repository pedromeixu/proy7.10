package com.example.myapp.services;

import java.util.List;

import com.example.myapp.domain.Cuenta;
import com.example.myapp.domain.Movimiento;

public interface CuentaService {
    Cuenta añadir(Cuenta cuenta);

    List<Cuenta> obtenerTodos();

    Cuenta obtenerPorId(String iban) throws RuntimeException;

    Cuenta editar(Cuenta cuenta);

    void borrar(String iban) throws RuntimeException;

    void actualizarSaldo(Movimiento movimiento);

}
