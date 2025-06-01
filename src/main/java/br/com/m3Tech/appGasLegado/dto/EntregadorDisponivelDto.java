package br.com.m3Tech.appGasLegado.dto;

import lombok.Data;
import lombok.ToString;

@Data
public class EntregadorDisponivelDto {

    private Integer idEntregador;
    private String nome;
    private String status;
    private String localizacao;
    private String distanciaDaEntrega;

    @Override
    public String toString() {
        return idEntregador + " | " + nome +  " | " + status + " | " + localizacao + " | " + distanciaDaEntrega;
    }
}
