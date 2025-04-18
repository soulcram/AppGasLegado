package br.com.m3Tech.appGasLegado;


import programagas.Conectar;
import programagas.PrimeiroAcesso;
import programagas.Trava;
import programagas.Vendas;
import programagas.Warning;

import java.awt.Component;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

public class ProgramaGas {
    public static String Porta;
    public static String NomePc;
    public static String DataLimite;
    public static String Impressora;
    public static int DiasRestantes;

    public ProgramaGas() {
    }

    public static void main(String[] args) {
        Conectar.startBd();
        CarregarConfiguracoes();
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String hoje = dateFormat.format(date);
        DiasRestantes = defineData(hoje, DataLimite);

        try {
            label45: {
                label39: {
                    if (NomePc == null) {
                        if (InetAddress.getLocalHost().getHostName() == null) {
                            break label39;
                        }
                    } else if (NomePc.equals(InetAddress.getLocalHost().getHostName())) {
                        break label39;
                    }

                    PrimeiroAcesso.main(args);
                    break label45;
                }

                if (DiasRestantes <= 5 & DiasRestantes > 0) {
                    Warning.main(args);
                } else if (DiasRestantes <= 0) {
                    Trava.main(args);
                } else if (DiasRestantes > 5) {
                    Vendas.main(args);
                }
            }
        } catch (UnknownHostException var5) {
            salvarErro(var5.getMessage() + "  Local:  " + var5.getLocalizedMessage());
        }

        System.out.println("Dias restantes " + defineData(hoje, DataLimite));
    }

    public static int defineData(String data1, String data2) {
        int diasRestantes;
        try {
            int dia1 = Integer.parseInt(data1.substring(0, 2));
            int dia2 = Integer.parseInt(data2.substring(0, 2));
            int mes1 = Integer.parseInt(data1.substring(3, 5));
            int mes2 = Integer.parseInt(data2.substring(3, 5));
            int ano1 = Integer.parseInt(data1.substring(6, 10));
            int ano2 = Integer.parseInt(data2.substring(6, 10));
            diasRestantes = (ano2 - ano1) * 360 + (mes2 - mes1) * 30 + (dia2 - dia1);
        } catch (NullPointerException var10) {
            diasRestantes = 5;
        }

        return diasRestantes;
    }

    public static void CarregarConfiguracoes() {
        try {
            String sql = "SELECT * FROM CONFIG where id_config = 1";
            Conectar.pesquisar(sql);
            if (Conectar.rs.next()) {
                Porta = Conectar.rs.getString("PORTA");
                NomePc = Conectar.rs.getString("NOMEPC");
                DataLimite = Conectar.rs.getString("DATA");
                Impressora = Conectar.rs.getString("IMPRESSORA");
            }

            Conectar.rs.close();
        } catch (SQLException var1) {
            salvarErro(var1.getMessage());
        }

    }

    public static void salvarErro(String text) {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        String hoje = dateFormat.format(date);

        try {
            FileWriter fw = new FileWriter("C:/BancoDeDados/ProgramaGas/logErros/log " + hoje + ".txt", true);
            BufferedWriter BW = new BufferedWriter(fw);
            BW.write(text);
            BW.newLine();
            BW.close();
        } catch (IOException var6) {
            JOptionPane.showMessageDialog((Component)null, var6.getStackTrace());
        }

    }
}
