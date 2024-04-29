package com.robson.desafiopicpay.entities;

import java.io.Serializable;
import java.time.LocalDate;

import com.robson.desafiopicpay.entities.usuarios.Usuario;
import com.robson.desafiopicpay.entities.usuarios.UsuarioComum;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity(name = "Transacao")
@Table(name = "Transacoes")
public class Transacao implements Serializable{
    private static  final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate data;
    private double valor;

    @ManyToOne
    @JoinColumn(name = "origem_id")
    private UsuarioComum origem;
    
    @ManyToOne
    @JoinColumn(name = "destino_id")
    private Usuario destino;

}
