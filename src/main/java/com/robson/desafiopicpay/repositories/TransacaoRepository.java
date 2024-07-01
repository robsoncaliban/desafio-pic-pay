package com.robson.desafiopicpay.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.robson.desafiopicpay.entities.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long>{

    @Query(value = "SELECT * FROM public.transacoes t WHERE t.destino_id = :destinoId", nativeQuery = true)
    Page<Transacao> buscarTransacoesRecebidasByUsuarioId(@Param("destinoId") Long contaId, Pageable page);
    
    @Query(value = "SELECT * FROM public.transacoes t WHERE t.origem_id = :origemId", nativeQuery = true)
    Page<Transacao> buscarTransacoesEnviadaByUsuarioId(@Param("origemId") Long contaId, Pageable page);
}
