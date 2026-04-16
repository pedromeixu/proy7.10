package com.example.myapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.example.myapp.domain.Cuenta;
import com.example.myapp.domain.Movimiento;
import com.example.myapp.repositories.CuentaRepository;

@Primary
@Service
public class CuentaServiceImplBD implements CuentaService {

    @Autowired
    CuentaRepository cuentaRepository;

    public Cuenta añadir(Cuenta cuenta) {
        cuenta.setSaldo(0);
        return cuentaRepository.save(cuenta);
    }

    public List<Cuenta> obtenerTodos() {
        return cuentaRepository.findAll();
    }

    public Cuenta obtenerPorId(String IBAN) throws RuntimeException {
        return cuentaRepository.findById(IBAN).orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
    }

    public Cuenta editar(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    public void borrar(String iban) throws RuntimeException {
        Cuenta cuenta = this.obtenerPorId(iban); //Si no la encuentra lanza una excepción
        if (cuenta.getSaldo() != 0)
            throw new RuntimeException("No se puede borrar una cuenta con saldo distinto de 0");
        cuentaRepository.delete(cuenta);
    }

    public void actualizarSaldo(Movimiento movimiento) {
        Cuenta cuenta = movimiento.getCuenta();
        if (cuenta != null) {
            cuenta.setSaldo(cuenta.getSaldo() + movimiento.getImporte());
            // No es necesario, ya que la cuenta se guarda en con el movimiento
            // cuentaRepository.save(cuenta);
        }

    }

}
