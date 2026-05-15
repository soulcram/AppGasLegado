package br.com.m3Tech.appGasLegado;


import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import lombok.extern.slf4j.Slf4j;
import org.apache.derby.impl.drda.NetworkServerControlImpl;
import programagas.ProgramaGas;

@Slf4j
public class Conectar {
    public static ResultSet rs;
    private static final String url = "jdbc:derby:C:\\BancoDeDados\\ProgramaGas";
    private static final String usuario = "soulcram";
    private static final String senha = "p4r4tud0";

    public Conectar() {
    }

    public static ResultSet pesquisar(String sql) throws SQLException {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            Connection con = DriverManager.getConnection(url, usuario, senha);
            Statement stm = con.createStatement();
            rs = stm.executeQuery(sql);
            return rs;
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage() + "  Local:  " + e.getLocalizedMessage());
            throw new SQLException("Erro ao conectar ao banco de dados: " + e.getMessage(), e);
        }
    }

    public static void alterar(String sql) throws SQLException {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            Connection con = DriverManager.getConnection(url, usuario, senha);
            Statement stm = con.createStatement();
            stm.executeUpdate(sql);
            stm.close();
            con.close();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            log.error(e.getMessage() + "  Local:  " + e.getLocalizedMessage());
            throw new SQLException("Erro ao conectar ao banco de dados: " + e.getMessage(), e);
        }
    }

    public static void startBd() {
        try {
            System.setProperty("derby.system.home", "/home/usuario/derby");
            NetworkServerControlImpl networkServer = new NetworkServerControlImpl();
            networkServer.start(new PrintWriter(System.out));
            System.out.println("Conectado ao banco de dados.");
        } catch (Exception var1) {
            ProgramaGas.salvarErro(var1.getMessage() + "  Local:  " + var1.getLocalizedMessage());
        }

    }
}