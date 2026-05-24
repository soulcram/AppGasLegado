package br.com.m3Tech.appGasLegado.utils;

import br.com.m3Tech.appGasLegado.Conectar;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AtualizarBanco {

    public static void atualizar(){
        atualizarTabelaConfig();
        atualizarTabelaPedidos();
        atualizarTabelaEndereco();
        atualizarTabelaClientes();
    }

    private static void atualizarTabelaConfig(){
        novaColunaTelIniTbConfig();
        novaColunaTelFimTbConfig();
        novaColunaNomeLojaTbConfig();
        novaColunaContextServiceTbConfig();
        novaColunaUrlServiceTbConfig();
        novaColunaServicoTbConfig();
        novaColunaValorPedidos();
    }

    private static void atualizarTabelaPedidos(){
        alterarColunaObservacaoPedidos();
        alterarColunaPedidoPedidos();
    }

    private static void atualizarTabelaEndereco(){
        alterarColunaLogradouroEndereco();
        alterarColunaReferenciaEndereco();
    }

    private static void atualizarTabelaClientes(){
        alterarColunaNomeClientes();
        alterarColunaObservacaoClientes();
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

    private static void novaColunaServicoTbConfig() {
        try {
            String sqlValida = "SELECT 1 \n" +
                    "FROM SYS.SYSCOLUMNS c\n" +
                    "JOIN SYS.SYSTABLES t ON c.REFERENCEID = t.TABLEID\n" +
                    "WHERE t.TABLENAME = 'CONFIG' AND c.COLUMNNAME = 'SERVICO'";
            ResultSet rs = Conectar.pesquisar(sqlValida);

            assert rs != null;
            if(!rs.next()) {
                System.out.println("Adicionando nova coluna SERVICO na tb CONFIG");
                Conectar.alterar("ALTER TABLE CONFIG ADD COLUMN SERVICO BOOLEAN");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void novaColunaValorPedidos() {
        try {
            String sqlValida = "SELECT 1 \n" +
                    "FROM SYS.SYSCOLUMNS c\n" +
                    "JOIN SYS.SYSTABLES t ON c.REFERENCEID = t.TABLEID\n" +
                    "WHERE t.TABLENAME = 'PEDIDOS' AND c.COLUMNNAME = 'VALOR'";
            ResultSet rs = Conectar.pesquisar(sqlValida);

            assert rs != null;
            if(!rs.next()) {
                System.out.println("Adicionando nova coluna VALOR na tb PEDIDOS");
                Conectar.alterar("ALTER TABLE PEDIDOS ADD COLUMN VALOR DECIMAL(10,2)");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void alterarColunaObservacaoPedidos() {
        ResultSet rs = null;

        try {
            String sqlValida =
                    "SELECT COLUMNDATATYPE " +
                            "FROM SYS.SYSCOLUMNS C " +
                            "INNER JOIN SYS.SYSTABLES T ON C.REFERENCEID = T.TABLEID " +
                            "WHERE T.TABLENAME = 'PEDIDOS' " +
                            "AND C.COLUMNNAME = 'OBSERVACAO' " +
                            "AND CAST(COLUMNDATATYPE AS VARCHAR(50)) <> 'VARCHAR(1000)'";

            rs = Conectar.pesquisar(sqlValida);

            boolean precisaAlterar = rs.next();

            rs.close();

            if (precisaAlterar) {
                System.out.println("Alterando coluna OBSERVACAO na tb PEDIDOS");

                Conectar.alterar(
                        "ALTER TABLE PEDIDOS " +
                                "ALTER COLUMN OBSERVACAO " +
                                "SET DATA TYPE VARCHAR(1000)"
                );
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void alterarColunaPedidoPedidos() {
        ResultSet rs = null;

        try {
            String sqlValida =
                    "SELECT COLUMNDATATYPE " +
                            "FROM SYS.SYSCOLUMNS C " +
                            "INNER JOIN SYS.SYSTABLES T ON C.REFERENCEID = T.TABLEID " +
                            "WHERE T.TABLENAME = 'PEDIDOS' " +
                            "AND C.COLUMNNAME = 'PEDIDO' " +
                            "AND CAST(COLUMNDATATYPE AS VARCHAR(50)) <> 'VARCHAR(1000)'";

            rs = Conectar.pesquisar(sqlValida);

            boolean precisaAlterar = rs.next();

            rs.close();

            if (precisaAlterar) {
                System.out.println("Alterando coluna PEDIDO na tb PEDIDOS");

                Conectar.alterar(
                        "ALTER TABLE PEDIDOS " +
                                "ALTER COLUMN PEDIDO " +
                                "SET DATA TYPE VARCHAR(1000)"
                );
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void alterarColunaLogradouroEndereco() {
        ResultSet rs = null;

        try {
            String sqlValida =
                    "SELECT COLUMNDATATYPE " +
                            "FROM SYS.SYSCOLUMNS C " +
                            "INNER JOIN SYS.SYSTABLES T ON C.REFERENCEID = T.TABLEID " +
                            "WHERE T.TABLENAME = 'ENDERECO' " +
                            "AND C.COLUMNNAME = 'LOGRADOURO' " +
                            "AND CAST(COLUMNDATATYPE AS VARCHAR(50)) <> 'VARCHAR(255)'";

            rs = Conectar.pesquisar(sqlValida);

            boolean precisaAlterar = rs.next();

            rs.close();

            if (precisaAlterar) {
                System.out.println("Alterando coluna LOGRADOURO na tb ENDERECO");

                Conectar.alterar(
                        "ALTER TABLE ENDERECO " +
                                "ALTER COLUMN LOGRADOURO " +
                                "SET DATA TYPE VARCHAR(255)"
                );
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void alterarColunaReferenciaEndereco() {
        ResultSet rs = null;

        try {
            String sqlValida =
                    "SELECT COLUMNDATATYPE " +
                            "FROM SYS.SYSCOLUMNS C " +
                            "INNER JOIN SYS.SYSTABLES T ON C.REFERENCEID = T.TABLEID " +
                            "WHERE T.TABLENAME = 'ENDERECO' " +
                            "AND C.COLUMNNAME = 'REFERENCIA' " +
                            "AND CAST(COLUMNDATATYPE AS VARCHAR(50)) <> 'VARCHAR(1000)'";

            rs = Conectar.pesquisar(sqlValida);

            boolean precisaAlterar = rs.next();

            rs.close();

            if (precisaAlterar) {
                System.out.println("Alterando coluna REFERENCIA na tb ENDERECO");

                Conectar.alterar(
                        "ALTER TABLE ENDERECO " +
                                "ALTER COLUMN REFERENCIA " +
                                "SET DATA TYPE VARCHAR(1000)"
                );
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void alterarColunaNomeClientes() {
        ResultSet rs = null;

        try {
            String sqlValida =
                    "SELECT COLUMNDATATYPE " +
                            "FROM SYS.SYSCOLUMNS C " +
                            "INNER JOIN SYS.SYSTABLES T ON C.REFERENCEID = T.TABLEID " +
                            "WHERE T.TABLENAME = 'CLIENTES' " +
                            "AND C.COLUMNNAME = 'NOME' " +
                            "AND CAST(COLUMNDATATYPE AS VARCHAR(50)) <> 'VARCHAR(255)'";

            rs = Conectar.pesquisar(sqlValida);

            boolean precisaAlterar = rs.next();

            rs.close();

            if (precisaAlterar) {
                System.out.println("Alterando coluna NOME na tb CLIENTES");

                Conectar.alterar(
                        "ALTER TABLE CLIENTES " +
                                "ALTER COLUMN NOME " +
                                "SET DATA TYPE VARCHAR(255)"
                );
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void alterarColunaObservacaoClientes() {
        ResultSet rs = null;

        try {
            String sqlValida =
                    "SELECT COLUMNDATATYPE " +
                            "FROM SYS.SYSCOLUMNS C " +
                            "INNER JOIN SYS.SYSTABLES T ON C.REFERENCEID = T.TABLEID " +
                            "WHERE T.TABLENAME = 'CLIENTES' " +
                            "AND C.COLUMNNAME = 'OBSERVACAO' " +
                            "AND CAST(COLUMNDATATYPE AS VARCHAR(50)) <> 'VARCHAR(255)'";

            rs = Conectar.pesquisar(sqlValida);

            boolean precisaAlterar = rs.next();

            rs.close();

            if (precisaAlterar) {
                System.out.println("Alterando coluna OBSERVACAO na tb CLIENTES");

                Conectar.alterar(
                        "ALTER TABLE CLIENTES " +
                                "ALTER COLUMN OBSERVACAO " +
                                "SET DATA TYPE VARCHAR(1000)"
                );
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
