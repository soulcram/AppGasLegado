package br.com.m3Tech.appGasLegado.service;

import br.com.m3Tech.appGasLegado.Conectar;
import br.com.m3Tech.appGasLegado.entity.Config;
import br.com.m3Tech.utils.BooleanUtils;
import br.com.m3Tech.utils.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfigService {

    public Config getConfig(){
        try {
            String sqlQuery = "SELECT ID_CONFIG, DATA, IMPRESSORA, NOMEPC, PORTA, TEL_INI, TEL_FIM, NOME_LOJA, SERVICO, " +
                    " CONTEXT_SERVICE, URL_SERVICE " +
                    " FROM CONFIG order by ID_CONFIG FETCH FIRST 1 ROWS ONLY";

            ResultSet rs = Conectar.pesquisar(sqlQuery);

            if(rs == null){
                System.out.println("Result Set nulo.");
                return null;
            }
            while(rs.next()){
                Config config = new Config();
                config.setData(rs.getString("DATA"));
                config.setImpressora(rs.getString("IMPRESSORA"));
                config.setPortaCom(rs.getString("PORTA"));
                config.setNomeloja(rs.getString("NOME_LOJA"));
                config.setContextService(rs.getString("CONTEXT_SERVICE"));
                config.setUrlService(rs.getString("URL_SERVICE"));
                config.setIniTel(rs.getString("TEL_INI"));
                config.setFimTel(rs.getString("TEL_FIM"));
                config.setServico(rs.getBoolean("SERVICO"));
                System.out.println(config);
                return config;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void salvarConfig(Config config){

        if(config == null){
            return;
        }

        String sql = "UPDATE CONFIG set ";

        if(!StringUtils.emptyOrNull(config.getData())){
            sql += " DATA = '" + config.getData().trim() + "', ";
        }

        if(!StringUtils.emptyOrNull(config.getImpressora())){
            sql += " IMPRESSORA = '" + config.getImpressora().trim() + "',";
        }

        if(!StringUtils.emptyOrNull(config.getPortaCom())){
            sql += " PORTA = '" + config.getPortaCom().trim() + "',";
        }

        if(!StringUtils.emptyOrNull(config.getNomeloja())){
            sql += " NOME_LOJA = '" + config.getNomeloja().trim() + "',";
        }

        if(!StringUtils.emptyOrNull(config.getUrlService())){
            sql += " URL_SERVICE = '" + config.getUrlService().trim() + "',";
        }

        if(!StringUtils.emptyOrNull(config.getIniTel())){
            sql += " TEL_INI = " + config.getIniTel().trim() + " ,";
        }

        if(!StringUtils.emptyOrNull(config.getFimTel())){
            sql += " TEL_FIM = " + config.getFimTel().trim() + " ,";
        }


         sql += " SERVICO = " + config.getServico() + " ,";


        if(!StringUtils.emptyOrNull(config.getContextService())){
            sql += " CONTEXT_SERVICE = '" + config.getContextService().trim() + "'";
        }

        sql += " where id_config = 1";

        try {
            Conectar.alterar(sql);
            System.out.println("Config alterada com sucesso.");
        } catch (SQLException var5) {
            programagas.ProgramaGas.salvarErro(var5.getMessage() + "  Local:  " + var5.getLocalizedMessage());
            System.err.println(var5.getMessage());
        }


    }
}
