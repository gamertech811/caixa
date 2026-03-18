package com.kauecaco.caixa.controller;

import com.kauecaco.caixa.models.Movimento;
import com.kauecaco.caixa.models.Saldo;
import com.kauecaco.caixa.repositories.SaldoRepository;
import com.kauecaco.caixa.services.MovimentoService;
import com.kauecaco.caixa.services.PDFService;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/api")
public class MovimentoController {
    @Autowired
    private PDFService pdfService;
    @Autowired
    private MovimentoService movimentoService;

    @GetMapping("/extrato")
    public ResponseEntity<List<Movimento>> pegarExtrato(@RequestParam LocalDate data){
        List<Movimento> extrato = movimentoService.getMovimentos(data);
        if (extrato == null){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(extrato);
    }
    @GetMapping
    public ResponseEntity<Saldo> pegarSaldoAtual(@RequestParam LocalDate data){
        Saldo saldo = movimentoService.getSaldo(data);
        return ResponseEntity.ok(saldo);
    }

    @PostMapping
    public ResponseEntity<Movimento> novoMovimento(@RequestBody Movimento m){
        return ResponseEntity.ok(movimentoService.criarMovimento(m));
    }

    @GetMapping("/gerarpdf")
    public ResponseEntity<byte[]> gerarPDF(@RequestParam LocalDate data) throws Exception {

        List<Movimento> lista = movimentoService.getMovimentos(data);

        ByteArrayOutputStream pdf = pdfService.extrato(lista);

        return ResponseEntity.ok()
                .header("Content-Disposition", "inline; filename=extrato.pdf")
                .header("Content-Type", "application/pdf")
                .body(pdf.toByteArray());
    }


}
