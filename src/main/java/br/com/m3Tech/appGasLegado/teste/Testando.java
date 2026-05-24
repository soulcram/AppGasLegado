package br.com.m3Tech.appGasLegado.teste;

import br.com.m3Tech.appGasLegado.CadastrarNovoCliente;
import br.com.m3Tech.appGasLegado.Conectar;
import br.com.m3Tech.appGasLegado.TelaPedidos;
import br.com.m3Tech.appGasLegado.dto.ClienteDto;
import br.com.m3Tech.appGasLegado.utils.AtualizarBanco;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Testando {

    public static void main(String[] args) throws SQLException {

        String exibirTabelas = "SELECT TABLENAME FROM SYS.SYSTABLES WHERE TABLETYPE = 'T'";

        String exibirColunas = "SELECT c.COLUMNNAME, c.COLUMNNUMBER, c.COLUMNDATATYPE, C.* \n" +
                "FROM SYS.SYSCOLUMNS c\n" +
                "JOIN SYS.SYSTABLES t ON c.REFERENCEID = t.TABLEID\n" +
                "WHERE t.TABLENAME = 'CLIENTES'";

        String verificarColuna = "SELECT COLUMNDATATYPE\n" +
                "FROM SYS.SYSCOLUMNS C\n" +
                "INNER JOIN SYS.SYSTABLES T \n" +
                "    ON C.REFERENCEID = T.TABLEID\n" +
                "WHERE T.TABLENAME = 'PEDIDOS'\n" +
                "  AND C.COLUMNNAME = 'OBSERVACAO' " +
                "AND CAST(COLUMNDATATYPE AS VARCHAR(50)) <> 'VARCHAR(50)'";

        String configs = "SELECT ID_CONFIG, DATA, IMPRESSORA, NOMEPC, PORTA, TEL_INI, TEL_FIM FROM CONFIG order by ID_CONFIG FETCH FIRST 1 ROWS ONLY";

        String inserindoColuna = "ALTER TABLE CONFIG ADD COLUMN TEL_INI INT";

        String sqlPedido = "select  * from pedidos as p INNER JOIN CLIENTES as c ON ID_CLIENTE = ID_CLIENTEp INNER JOIN ENDERECO ON ID_CEP = ID_ENDERECO where ID_PEDIDO = " + 234173;


        String encontrarCliente = "SELECT * FROM CLIENTES where telefone =  '11930082137'";

        String encontrarPedidoCliente = "SELECT * FROM PEDIDOS where ID_CLIENTEp = 92428 ";

//        Conectar.alterar(inserindoColuna);

//          AtualizarBanco.atualizar();
        ResultSet rs = Conectar.pesquisar(exibirTabelas);
       if(rs == null){
           System.out.println("Result Set nulo.");
           return;
       }
       while(rs.next()){
//           System.out.println(rs.getString("TABLENAME"));
          System.out.println(rs.getString(1));
//           System.out.println(rs.getString(2));
//           System.out.println(rs.getString(3));
//           System.out.println(rs.getString(4));
//           System.out.println(rs.getString(5));
//           System.out.println(rs.getString(6));

//           System.out.println(rs.getString("TEL_INI"));
//           System.out.println(rs.getString("TEL_FIM"));

//           System.out.println(rs.getString("REFERENCIA"));

           //TB CLIENTES
//           System.out.println(
//                   "\n==============================\n" +
//                           "ID_CLIENTE : " + rs.getString("ID_CLIENTE") + "\n" +
//                           "ID_ENDERECO: " + rs.getString("ID_ENDERECO") + "\n" +
//                           "NOME        : " + rs.getString("NOME") + "\n" +
//                           "NUMERO      : " + rs.getString("NUMERO") + "\n" +
//                           "OBSERVACAO  : " + rs.getString("OBSERVACAO") + "\n" +
//                           "TELEFONE    : " + rs.getString("TELEFONE") + "\n" +
//                           "=============================="
//           );

           //TB PEDIDOS
//           System.out.println(
//                   "\n==============================\n" +
//                           "ID_CLIENTEP : " + rs.getString("ID_CLIENTEP") + "\n" +
//                           "ID_PEDIDO   : " + rs.getString("ID_PEDIDO") + "\n" +
//                           "OBSERVACAO : " + rs.getString("OBSERVACAO") + "\n" +
//                           "PEDIDO      : " + rs.getString("PEDIDO") + "\n" +
//                           "STATUS      : " + rs.getString("STATUS") + "\n" +
//                           "=============================="
//           );
           System.out.println();
       }
    }
}
