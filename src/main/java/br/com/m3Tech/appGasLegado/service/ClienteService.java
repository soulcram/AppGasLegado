package br.com.m3Tech.appGasLegado.service;

import br.com.m3Tech.appGasLegado.Conectar;
import br.com.m3Tech.appGasLegado.dto.ClienteDto;
import br.com.m3Tech.appGasLegado.dto.ClienteEndereco;
import br.com.m3Tech.appGasLegado.dto.Endereco;
import br.com.m3Tech.utils.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteService {
    public void salvarCliente(ClienteDto cliente){

        try {
            String sqlClienteByTelefone = "SELECT * FROM CLIENTES WHERE TELEFONE = '" + cliente.getTelefone() + "'";

            Conectar.pesquisar(sqlClienteByTelefone);

            if(Conectar.rs.next()){
                alterarCliente(cliente);
            }else{
                salvarNovoCliente(cliente);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void salvarNovoCliente(ClienteDto cliente) {
        try {
            ClienteEndereco clienteEndereco = cliente.getClienteEnderecos().get(0);

            String idEndereco = getIdEndereco(clienteEndereco.getEndereco());

            String querySalvarIni = "INSERT INTO CLIENTES( ";
            String querySalvarFim = " VALUES( ";

            if(!StringUtils.emptyOrNull(cliente.getNome())){
                querySalvarIni += " NOME, ";
                querySalvarFim += " '"+cliente.getNome()+ "' , ";
            }

            if(!StringUtils.emptyOrNull(clienteEndereco.getNumero())){
                querySalvarIni += " NUMERO, ";
                querySalvarFim += " '"+clienteEndereco.getNumero()+ "' , ";
            }

            if(!StringUtils.emptyOrNull(cliente.getObservacao())){
                querySalvarIni += " OBSERVACAO, ";
                querySalvarFim += " '"+cliente.getObservacao()+ "' , ";
            }

            if(!StringUtils.emptyOrNull(cliente.getTelefone())){
                querySalvarIni += " TELEFONE, ";
                querySalvarFim += " '"+cliente.getTelefone()+ "' , ";
            }


            querySalvarIni += " ID_ENDERECO) ";
            querySalvarFim += " "+idEndereco+") ";

            Conectar.alterar(querySalvarIni + querySalvarFim);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void alterarCliente(ClienteDto cliente) {
        try {
            ClienteEndereco clienteEndereco = cliente.getClienteEnderecos().get(0);

            String idEndereco = getIdEndereco(clienteEndereco.getEndereco());

            String queryAlterarCliente = "UPDATE CLIENTES SET ";

            if(!StringUtils.emptyOrNull(cliente.getNome())){
                queryAlterarCliente += " NOME = '"+cliente.getNome()+ "' , ";
            }

            if(!StringUtils.emptyOrNull(clienteEndereco.getNumero())){
                queryAlterarCliente += " NUMERO = '"+clienteEndereco.getNumero()+ "' , ";
            }

            if(!StringUtils.emptyOrNull(cliente.getObservacao())){
                queryAlterarCliente += " OBSERVACAO = '"+cliente.getObservacao()+ "' , ";
            }

            if(!StringUtils.emptyOrNull(cliente.getObservacao())){
                queryAlterarCliente += " OBSERVACAO = '"+cliente.getObservacao()+ "' , ";
            }

            queryAlterarCliente += " ID_ENDERECO = " + idEndereco + " WHERE TELEFONE = '" + cliente.getTelefone() + "'";

            Conectar.alterar(queryAlterarCliente);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String getIdEndereco(Endereco endereco) {
        try {
            String sqlQuery = "SELECT * FROM ENDERECO WHERE LOGRADOURO = '" + endereco.getLogradouro() + "'";

            ResultSet rs = Conectar.pesquisar(sqlQuery);

            assert rs != null;
            if(rs.next()){
                return rs.getString("ID_CEP");
            }else{
                String sqlInsertIni = "INSERT INTO ENDERECO(";
                String sqlInsertFim = " VALUES(";

                if(!StringUtils.emptyOrNull(endereco.getCep())){
                    sqlInsertIni += " CEP, ";
                    sqlInsertFim += " '"+endereco.getCep()+ "' , ";
                }

                if(!StringUtils.emptyOrNull(endereco.getCidade())){
                    sqlInsertIni += " CIDADE, ";
                    sqlInsertFim += " '"+endereco.getCidade()+ "' , ";
                }

                if(!StringUtils.emptyOrNull(endereco.getBairro())){
                    sqlInsertIni += " BAIRRO, ";
                    sqlInsertFim += " '"+endereco.getBairro()+ "' , ";
                }

                if(!StringUtils.emptyOrNull(endereco.getLogradouro())){
                    sqlInsertIni += " LOGRADOURO, ";
                    sqlInsertFim += " '"+endereco.getLogradouro()+ "' , ";
                }
                sqlInsertIni += " TP_LOGRADOURO) ";
                sqlInsertFim += " 'Rua') ";

                Conectar.alterar(sqlInsertIni + sqlInsertFim);
                return getIdEndereco(endereco.getLogradouro());
            }


        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return "1";
    }

    private String getIdEndereco(String logradouro) {
        try {
            String sqlQuery = "SELECT * FROM ENDERECO WHERE LOGRADOURO = '" + logradouro + "'";

            ResultSet rs = Conectar.pesquisar(sqlQuery);

            assert rs != null;
            if(rs.next()){
                return rs.getString("ID_CEP");
            }

            return "1";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
