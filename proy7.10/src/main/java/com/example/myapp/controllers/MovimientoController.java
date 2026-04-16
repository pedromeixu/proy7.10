package com.example.myapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.myapp.domain.Cuenta;
import com.example.myapp.domain.Movimiento;
import com.example.myapp.services.CuentaService;
import com.example.myapp.services.MovimientoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/movimientos")
public class MovimientoController {

    @Autowired
    public MovimientoService movimientoService;
    @Autowired
    public CuentaService cuentaService;

    //http://localhost:9000/movimientos/{iban}
    //Quiero mostrar todos los movimientos de una determinada cuenta identificada por su iban
    @GetMapping("/{iban}")
    public String showMovCuenta(@PathVariable String iban, Model model) {
        try {
            Cuenta cuenta = cuentaService.obtenerPorId(iban);
            //Por ser unidireccional tengo que ir al servicio de Movimientos (movimientoService) y llamar al método 
            // que me proporciona la lista de Movimientos --> obtener porPorCuenta
            model.addAttribute("listaMovimientos", movimientoService.obtenerPorCuenta(cuenta));
            model.addAttribute("cuenta", cuenta);
            return "movementListView";
        } catch (RuntimeException e) {
            return "redirect:/cuentas?err=2";
        }
    }

    //http://localhost:9000/movimientos/new/{iban}
    //Añado un movimiento a una cuenta
    @GetMapping("/new/{iban}")
    public String showNew(@PathVariable String iban, Model model) {
        Movimiento movimiento = new Movimiento();
        try {
            movimiento.setCuenta(cuentaService.obtenerPorId(iban));
            model.addAttribute("movimientoForm", movimiento);
            return "movementNewView";
        } catch (RuntimeException e) {
            return "redirect:/cuentas?err=2";
        }
    }

    //http://localhost:9000/movimientos/new/submit
    @PostMapping("/new/submit")
    public String showNewSubmit(
            @Valid Movimiento nuevoMovimiento,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "redirect:/movimientos/new";
        try {
            movimientoService.añadir(nuevoMovimiento);
            return "redirect:/cuentas/";
        } catch (RuntimeException e) {
            return "redirect:/cuentas?err=2";
        }
    }

}
