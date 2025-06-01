package br.com.m3Tech.appGasLegado.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AlterarEntregadorDto {

    private Integer idPedido;
    private Integer idEntregador;
    private String nomeEntregador;

}
