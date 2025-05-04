package br.com.m3Tech.appGasLegado.service;

import br.com.m3Tech.appGasLegado.Conectar;
import br.com.m3Tech.appGasLegado.dto.DadosImpressaoDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PedidoService {
    public DadosImpressaoDto getPedido(Integer idPedido){

        DadosImpressaoDto dadosImpressaoDto = new DadosImpressaoDto();

        Integer idCliente = null;
        Integer idEndereco = null;

        String sqlPedido = "select * from PEDIDOS as p where ID_PEDIDO = " + idPedido;

        try {
            Conectar.pesquisar(sqlPedido);

            if(Conectar.rs.next()) {



                dadosImpressaoDto.setPedido(Conectar.rs.getString("pedido"));
                dadosImpressaoDto.setFormaPagamento(Conectar.rs.getString("formadepagamento"));
                dadosImpressaoDto.setTotal(Conectar.rs.getString("VALOR"));

                idCliente = Conectar.rs.getInt("ID_CLIENTEp");

            }
            Conectar.rs.close();
        } catch (Exception var10) {
            log.error(var10.getMessage() + "  Local:  " + var10.getLocalizedMessage());
            return null;
        }

        String sqlCliente = "select c.OBSERVACAO, c.NUMERO, c.NOME, c.telefone, c.ID_ENDERECO from CLIENTES as c WHERE ID_CLIENTE = " + idCliente;

        try {
            Conectar.pesquisar(sqlCliente);

            if(Conectar.rs.next()) {
                dadosImpressaoDto.setObs(Conectar.rs.getString("OBSERVACAO"));
                dadosImpressaoDto.setNumCasa(Conectar.rs.getString("numero"));
                dadosImpressaoDto.setNome(Conectar.rs.getString("nome"));
                dadosImpressaoDto.setTelefone(Conectar.rs.getString("telefone"));
                idEndereco = Conectar.rs.getInt("ID_ENDERECO");

            }
            Conectar.rs.close();
        } catch (Exception var10) {
            log.error(var10.getMessage() + "  Local:  " + var10.getLocalizedMessage());
            return null;
        }

        String sqlEndereco = "select e.tp_logradouro, e.logradouro, e.BAIRRO from ENDERECO as e WHERE ID_CEP = " + idEndereco;

        try {
            Conectar.pesquisar(sqlEndereco);

            if(Conectar.rs.next()) {

                dadosImpressaoDto.setTp_logradouro(Conectar.rs.getString("tp_logradouro"));
                dadosImpressaoDto.setLogradouro(Conectar.rs.getString("logradouro"));
                dadosImpressaoDto.setBairro(Conectar.rs.getString("bairro"));

            }
            Conectar.rs.close();
        } catch (Exception var10) {
            log.error(var10.getMessage() + "  Local:  " + var10.getLocalizedMessage());
            return null;
        }

        return dadosImpressaoDto;

    }

}
