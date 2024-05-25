package com.robson.desafiopicpay.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.robson.desafiopicpay.entities.Transacao;

public interface TransacaoRepository extends  JpaRepository<Transacao, Long>{

}
