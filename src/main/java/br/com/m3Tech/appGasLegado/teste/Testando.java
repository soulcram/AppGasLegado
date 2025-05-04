package br.com.m3Tech.appGasLegado.teste;

import br.com.m3Tech.appGasLegado.Conectar;
import br.com.m3Tech.appGasLegado.utils.AtualizarBanco;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Testando {

    public static void main(String[] args) throws SQLException {

        String exibirTabelas = "SELECT TABLENAME FROM SYS.SYSTABLES WHERE TABLETYPE = 'T'";

        String exibirColunas = "SELECT c.COLUMNNAME, c.COLUMNNUMBER, c.COLUMNDATATYPE \n" +
                "FROM SYS.SYSCOLUMNS c\n" +
                "JOIN SYS.SYSTABLES t ON c.REFERENCEID = t.TABLEID\n" +
                "WHERE t.TABLENAME = 'PEDIDOS'";

        String configs = "SELECT ID_CONFIG, DATA, IMPRESSORA, NOMEPC, PORTA, TEL_INI, TEL_FIM FROM CONFIG order by ID_CONFIG FETCH FIRST 1 ROWS ONLY";

        String inserindoColuna = "ALTER TABLE CONFIG ADD COLUMN TEL_INI INT";

        String sqlPedido = "select  * from pedidos as p INNER JOIN CLIENTES as c ON ID_CLIENTE = ID_CLIENTEp INNER JOIN ENDERECO ON ID_CEP = ID_ENDERECO where ID_PEDIDO = " + 234173;

//        Conectar.alterar(inserindoColuna);

//          AtualizarBanco.atualizar();
        ResultSet rs = Conectar.pesquisar(exibirColunas);
       if(rs == null){
           System.out.println("Result Set nulo.");
           return;
       }
       while(rs.next()){
//           System.out.println(rs.getString("TABLENAME"));
           System.out.println(rs.getString("COLUMNNAME"));
           System.out.println(rs.getString("COLUMNNUMBER"));
           System.out.println(rs.getString("COLUMNDATATYPE"));
//           System.out.println(rs.getString("TEL_INI"));
//           System.out.println(rs.getString("TEL_FIM"));

//           System.out.println(rs.getString("REFERENCIA"));
           System.out.println();
       }
    }
}
