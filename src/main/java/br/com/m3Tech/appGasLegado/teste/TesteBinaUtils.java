package br.com.m3Tech.appGasLegado.teste;

import br.com.m3Tech.appGasLegado.Conectar;
import br.com.m3Tech.appGasLegado.utils.BinaUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TesteBinaUtils {

    public static void main(String[] args) throws SQLException {

        String numeroGeradoNaBina = "0111199328422933333";

        String s = BinaUtils.encontrarTelefone(numeroGeradoNaBina);

        System.err.println(s);
    }
}
