package com.example.myapp.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.example.myapp.domain.Cuenta;
import com.example.myapp.domain.Movimiento;
import com.example.myapp.repositories.MovimientoRepository;

@Primary
@Service
public class MovimientoServiceImplBD implements MovimientoService {

    private static final double IMP_MINIMO = -300D;
    private static final double IMP_MAXIMO = 1000D;

    @Autowired
    MovimientoRepository movimientoRepository;

    @Autowired
    CuentaService cuentaService;

    public void añadir(Movimiento movimiento) throws RuntimeException {
        if (movimiento.getImporte() <= IMP_MINIMO)
            throw new RuntimeException("Importe de movimiento menor que lo permitido");
        if (movimiento.getImporte() >= IMP_MAXIMO)
            throw new RuntimeException("Importe de movimiento mayor que lo permitido");
        movimiento.setFecha(LocalDateTime.now());
        cuentaService.actualizarSaldo(movimiento);
        movimientoRepository.save(movimiento); // guadar movim (y cuenta en cascada)
    }

    public List<Movimiento> obtenerPorCuenta(Cuenta cuenta) throws RuntimeException {
        if (cuenta == null)
            throw new RuntimeException("Cuenta no existe");
        return movimientoRepository.findByCuenta(cuenta);
    }
}
