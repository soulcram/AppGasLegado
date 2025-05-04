package br.com.m3Tech.appGasLegado.dto;

import lombok.Data;

import java.util.List;

@Data
public class DadosImpressaoDto {
    private String telefone;
    private String nome;
    private String tp_logradouro;
    private String logradouro;
    private String numCasa;
    private String bairro;
    private String obs;
    private String pedido;
    private String total;
    private String formaPagamento;
}
