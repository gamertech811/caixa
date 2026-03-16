package com.kauecaco.caixa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String home() {
        return "movimentacao";
    }

    @GetMapping("/movimentacao")
    public String movimentacao() {
        return "movimentacao";
    }

    @GetMapping("/extrato")
    public String extrato() {
        return "extrato";
    }
}
