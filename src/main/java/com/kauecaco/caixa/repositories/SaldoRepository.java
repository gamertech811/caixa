package com.kauecaco.caixa.repositories;

import com.kauecaco.caixa.models.Saldo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SaldoRepository extends JpaRepository<Saldo, Integer> {
    boolean existsByData(LocalDate data);
    Optional<Saldo> findTopByDataLessThanOrderByData(LocalDate data);
    Saldo findByData(LocalDate data);



    @Transactional
    @Modifying
    @Query("update Saldo s set s.valor=s.valor+:valor where s.data>=:data")
    public void atualizaSaldo(@Param("data") LocalDate data,@Param("valor") double valor);
}
