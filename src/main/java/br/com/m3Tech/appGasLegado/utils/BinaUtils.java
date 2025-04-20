package br.com.m3Tech.appGasLegado.utils;

import br.com.m3Tech.appGasLegado.entity.Config;
import br.com.m3Tech.appGasLegado.service.ConfigService;

public class BinaUtils {
    public static String encontrarTelefone(String numeroEncontrado){
        Config config = new ConfigService().getConfig();

        Integer telIni = Integer.valueOf(config.getIniTel());
        Integer telFim =  Integer.valueOf(config.getFimTel());

        String numeroCerto = numeroEncontrado.substring(telIni, numeroEncontrado.length() < telFim ? numeroEncontrado.length() : telFim);

        if ("9".equals(numeroCerto.substring(2,3))) {
            System.out.println("Numero certo: " + numeroCerto + "\r\n");
        } else {
            numeroCerto = numeroCerto.substring(0, 10);
            System.out.println("Numero certo: " + numeroCerto + "\r\n");
        }
        return numeroCerto;
    }
}
