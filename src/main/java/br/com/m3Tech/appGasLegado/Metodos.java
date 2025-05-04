package br.com.m3Tech.appGasLegado;

public class Metodos {
    public Metodos() {
    }

    public String numero(String num) {
        String numCerto = "";

        for(int i = 1; i <= num.length(); ++i) {
            if ("0".equals(num.substring(i - 1, i)) || "1".equals(num.substring(i - 1, i)) || "2".equals(num.substring(i - 1, i)) || "3".equals(num.substring(i - 1, i)) || "4".equals(num.substring(i - 1, i)) || "5".equals(num.substring(i - 1, i)) || "6".equals(num.substring(i - 1, i)) || "7".equals(num.substring(i - 1, i)) || "8".equals(num.substring(i - 1, i)) || "9".equals(num.substring(i - 1, i))) {
                numCerto = numCerto + num.substring(i - 1, i);
            }
        }

        return numCerto;
    }
}
