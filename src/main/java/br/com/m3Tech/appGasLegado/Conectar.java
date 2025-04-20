package br.com.m3Tech.appGasLegado;


import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.derby.impl.drda.NetworkServerControlImpl;
import programagas.ProgramaGas;

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
            Connection con = DriverManager.getConnection("jdbc:derby:C:\\BancoDeDados\\ProgramaGas", "soulcram", "p4r4tud0");
            Statement stm = con.createStatement();
            rs = stm.executeQuery(sql);
            return rs;
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException var3) {
            ProgramaGas.salvarErro(var3.getMessage() + "  Local:  " + var3.getLocalizedMessage());
        }
        return null;
    }

    public static void alterar(String sql) throws SQLException {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:derby:C:\\BancoDeDados\\ProgramaGas", "soulcram", "p4r4tud0");
            Statement stm = con.createStatement();
            stm.executeUpdate(sql);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException var3) {
            ProgramaGas.salvarErro(var3.getMessage() + "  Local:  " + var3.getLocalizedMessage());
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