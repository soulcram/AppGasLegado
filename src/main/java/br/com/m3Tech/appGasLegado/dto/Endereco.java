package br.com.m3Tech.appGasLegado.dto;

import br.com.m3Tech.utils.StringUtils;
import lombok.Data;

@Data
public class Endereco {
    private Integer idEndereco;
    private String logradouro;
    private String cep;
    private String bairro;
    private String cidade;
    private String estado;

    public void setCep(String cep) {
        if(StringUtils.emptyOrNull(cep)){
            return;
        }
        this.cep = cep.replaceAll("-", "");
    }
}
