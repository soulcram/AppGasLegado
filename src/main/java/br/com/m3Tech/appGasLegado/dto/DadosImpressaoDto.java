package br.com.m3Tech.appGasLegado.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public DadosImpressaoDto(PedidoServicoDto pedido){
        this.telefone = pedido.getTelefoneCliente();
        this.nome = pedido.getNomeCliente();
        this.tp_logradouro = "Rua";
        this.logradouro = pedido.getLogradouro();
        this.numCasa = pedido.getNumeroResidencia();
        this.bairro = pedido.getBairro();
        this.obs = pedido.getObservacao();
        this.pedido = pedido.getPedido();
        this.total = pedido.getValorTotal() != null ? pedido.getValorTotal().toString() : "";
        this.formaPagamento = pedido.getFormaPagamento();
    }
}
