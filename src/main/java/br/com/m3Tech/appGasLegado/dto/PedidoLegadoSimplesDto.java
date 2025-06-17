package br.com.m3Tech.appGasLegado.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@ToString
public class PedidoLegadoSimplesDto {

    private String pedido;
    private String formaPagamento;
    private String entregador;
    private String status;
    private LocalDate data;
    private BigDecimal valorTotal;
    private String loja;
    private String lojaOriginal;
}
