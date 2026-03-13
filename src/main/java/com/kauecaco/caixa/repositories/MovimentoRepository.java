package com.kauecaco.caixa.repositories;

import com.kauecaco.caixa.models.Movimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MovimentoRepository extends JpaRepository<Movimento, Integer> {
    public List<Movimento> findAllByData(LocalDate data);
    boolean existsByData(LocalDate data);
}
//rafael baba