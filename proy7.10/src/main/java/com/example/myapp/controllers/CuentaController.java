package com.example.myapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.myapp.domain.Cuenta;
import com.example.myapp.services.CuentaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    public CuentaService cuentaService;

    //http://localhost:9000/cuentas
    @GetMapping({ "/", "" })
    public String showList(@RequestParam(required = false) Integer err, Model model) {
        if (err != null) {
            if (err == 1)
                model.addAttribute("msgError", "Error en ecuenta");
            if (err == 2)
                model.addAttribute("msgError", "Error en movimiento");
        }
        model.addAttribute("listaCuentas", cuentaService.obtenerTodos());
        return "accountListView";
    }

     //http://localhost:9000/cuentas/new
    @GetMapping("/new")
    public String showNew(Model model) {
        model.addAttribute("cuentaForm", new Cuenta());
        return "accountNewView";
    }

    //http://localhost:9000/cuentas/submit
    @PostMapping("/new/submit")
    public String showNewSubmit(
            @Valid @ModelAttribute("cuentaForm") Cuenta nuevaCuenta,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "redirect:/cuentas/?err=1";
        cuentaService.añadir(nuevaCuenta);
        return "redirect:/cuentas/";
    }

     //http://localhost:9000/edit/{iban}
    @GetMapping("/edit/{iban}")
    public String showEditForm(@PathVariable String iban, Model model) {
        Cuenta cuenta = cuentaService.obtenerPorId(iban);
        if (cuenta != null) {
            model.addAttribute("cuentaForm", cuenta);
            return "accountEditView";
        } else {
            return "redirect:/cuentas/err=1";
        }
    }

    //http://localhost:9000/edit/submit
    @PostMapping("/edit/submit")
    public String showEditSubmit(@Valid @ModelAttribute("cuentaForm") Cuenta cuenta,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "redirect:/cuentas/?err=1";
        } else {
            cuentaService.editar(cuenta);
            return "redirect:/cuentas/";
        }
    }

    //http://localhost:9000/delete/{iban}
    @GetMapping("/delete/{iban}")
    public String showDelete(@PathVariable String iban) {
        try {
            cuentaService.borrar(iban); //Puede lanzar una excepción si el saldo no es cero o no encuentra la cuenta
            return "redirect:/cuentas/";
        } catch (RuntimeException e) {
            return "redirect:/cuentas/?err=1";
        }
    }
}
