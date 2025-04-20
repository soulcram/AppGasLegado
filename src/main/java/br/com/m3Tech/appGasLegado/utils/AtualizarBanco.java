package br.com.m3Tech.appGasLegado.utils;

import br.com.m3Tech.appGasLegado.Conectar;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AtualizarBanco {

    public static void atualizar(){
        atualizarTabelaConfig();


    }

    private static void atualizarTabelaConfig(){
        novaColunaTelIniTbConfig();
        novaColunaTelFimTbConfig();
        novaColunaNomeLojaTbConfig();
        novaColunaContextServiceTbConfig();
        novaColunaUrlServiceTbConfig();
    }

    private static void novaColunaTelIniTbConfig() {
        try {
            String sqlValida = "SELECT 1 \n" +
                    "FROM SYS.SYSCOLUMNS c\n" +
                    "JOIN SYS.SYSTABLES t ON c.REFERENCEID = t.TABLEID\n" +
                    "WHERE t.TABLENAME = 'CONFIG' AND c.COLUMNNAME = 'TEL_INI'";
            ResultSet rs = Conectar.pesquisar(sqlValida);

            assert rs != null;
            if(!rs.next()) {
                System.out.println("Adicionando nova coluna TEL_INI na tb CONFIG");
                Conectar.alterar("ALTER TABLE CONFIG ADD COLUMN TEL_INI INT");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void novaColunaTelFimTbConfig() {
        try {
            String sqlValida = "SELECT 1 \n" +
                    "FROM SYS.SYSCOLUMNS c\n" +
                    "JOIN SYS.SYSTABLES t ON c.REFERENCEID = t.TABLEID\n" +
                    "WHERE t.TABLENAME = 'CONFIG' AND c.COLUMNNAME = 'TEL_FIM'";
            ResultSet rs = Conectar.pesquisar(sqlValida);

            assert rs != null;
            if(!rs.next()) {
                System.out.println("Adicionando nova coluna TEL_FIM na tb CONFIG");
                Conectar.alterar("ALTER TABLE CONFIG ADD COLUMN TEL_FIM INT");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void novaColunaNomeLojaTbConfig() {
        try {
            String sqlValida = "SELECT 1 \n" +
                    "FROM SYS.SYSCOLUMNS c\n" +
                    "JOIN SYS.SYSTABLES t ON c.REFERENCEID = t.TABLEID\n" +
                    "WHERE t.TABLENAME = 'CONFIG' AND c.COLUMNNAME = 'NOME_LOJA'";
            ResultSet rs = Conectar.pesquisar(sqlValida);

            assert rs != null;
            if(!rs.next()) {
                System.out.println("Adicionando nova coluna NOME_LOJA na tb CONFIG");
                Conectar.alterar("ALTER TABLE CONFIG ADD COLUMN NOME_LOJA VARCHAR(100)");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void novaColunaContextServiceTbConfig() {
        try {
            String sqlValida = "SELECT 1 \n" +
                    "FROM SYS.SYSCOLUMNS c\n" +
                    "JOIN SYS.SYSTABLES t ON c.REFERENCEID = t.TABLEID\n" +
                    "WHERE t.TABLENAME = 'CONFIG' AND c.COLUMNNAME = 'CONTEXT_SERVICE'";
            ResultSet rs = Conectar.pesquisar(sqlValida);

            assert rs != null;
            if(!rs.next()) {
                System.out.println("Adicionando nova coluna CONTEXT_SERVICE na tb CONFIG");
                Conectar.alterar("ALTER TABLE CONFIG ADD COLUMN CONTEXT_SERVICE VARCHAR(100)");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void novaColunaUrlServiceTbConfig() {
        try {
            String sqlValida = "SELECT 1 \n" +
                    "FROM SYS.SYSCOLUMNS c\n" +
                    "JOIN SYS.SYSTABLES t ON c.REFERENCEID = t.TABLEID\n" +
                    "WHERE t.TABLENAME = 'CONFIG' AND c.COLUMNNAME = 'URL_SERVICE'";
            ResultSet rs = Conectar.pesquisar(sqlValida);

            assert rs != null;
            if(!rs.next()) {
                System.out.println("Adicionando nova coluna URL_SERVICE na tb CONFIG");
                Conectar.alterar("ALTER TABLE CONFIG ADD COLUMN URL_SERVICE VARCHAR(100)");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
