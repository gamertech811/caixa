package com.kauecaco.caixa.services;

import com.kauecaco.caixa.models.Movimento;
import com.kauecaco.caixa.models.Saldo;
import com.kauecaco.caixa.repositories.MovimentoRepository;
import com.kauecaco.caixa.repositories.SaldoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MovimentoService {
    @Autowired
    MovimentoRepository movimentoRepository;
    @Autowired
    SaldoRepository saldoRepository;
    public Movimento criarMovimento(Movimento movimento){

        movimentoRepository.save(movimento);
        if (!saldoRepository.existsByData(movimento.getData())){
            Saldo saldoAnt = saldoRepository.findTopByDataLessThanOrderByDataDesc(movimento.getData()).orElse(new Saldo(0,movimento.getData()));
            Saldo saldoNovo = new Saldo(saldoAnt.getValor(), movimento.getData());
            saldoRepository.save(saldoNovo);
        }
        double val;
        if (movimento.getTipo() == 1){
            val = movimento.getValor();
        }
        else if (movimento.getTipo() ==2){
            val = movimento.getValor()*(-1);
        }
        else {
            return null;
        }
        saldoRepository.atualizaSaldo(movimento.getData(), val);
        return movimento;
    }

    public List<Movimento> getMovimentos(LocalDate data){
        if (!movimentoRepository.existsByData(data)){
            return null;
        }
        return movimentoRepository.findAllByData(data);
    }

    public Saldo getSaldo(LocalDate data){
        if (saldoRepository.existsByData(data)){
            return saldoRepository.findByData(data);
        }
        else {
            Saldo saldoNovo =new Saldo(0,data);
            saldoRepository.save(saldoNovo);
            return saldoNovo;
        }

    }


}
