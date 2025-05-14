package br.com.m3Tech.appGasLegado;


import br.com.m3Tech.appGasLegado.dto.PedidoServicoDto;
import br.com.m3Tech.appGasLegado.entity.Config;
import br.com.m3Tech.appGasLegado.service.ConfigService;
import com.google.common.collect.Lists;
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
import java.util.List;
import javax.swing.*;

public class ProgramaGas {
    public static String Porta;
    public static String nomeLoja;
    public static String DataLimite;
    public static String Impressora;

    public static String contextoService;
    public static String urlService;
    public static Boolean servico;

    public static JTable tabela1;

    public ProgramaGas() {
    }

    public static void CarregarConfiguracoes() {
        try {
            Config config = new ConfigService().getConfig();

            Porta = config.getPortaCom();
            nomeLoja = config.getNomeloja();
            DataLimite = config.getData();
            Impressora = config.getImpressora();
            servico = config.getServico();
            contextoService = config.getContextService().trim();
            urlService = config.getUrlService() != null ? config.getUrlService().trim() : "" ;

        } catch (Exception var1) {
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
