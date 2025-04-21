package br.com.m3Tech.appGasLegado.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PedidoProdutoDto {
    private String nome;
    private BigDecimal valor;
    private Integer quantidade;
    
}
