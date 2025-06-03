package br.com.m3Tech.appGasLegado.utils;


import programagas.ProgramaGas;

import java.awt.Component;
import java.text.ParseException;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;

public class Mascaras {
    public Mascaras() {
    }

    public static void mascaraCep(JFormattedTextField jtf) {
        try {
            MaskFormatter formatoDois = new MaskFormatter("*****-***");
            formatoDois.setValidCharacters("0123456789");
            formatoDois.install(jtf);
        } catch (ParseException var3) {
            JOptionPane.showMessageDialog((Component)null, (Object)null, "Não foi possivel inserir mask nos campos cep: " + var3.getMessage(), 0);
            ProgramaGas.salvarErro(var3.getMessage() + "  Local:  " + var3.getLocalizedMessage());
        }

    }

    public static void mascaraTelefone(JFormattedTextField jtf) {
        try {
            MaskFormatter formatoDois = new MaskFormatter("(11)*********");
            formatoDois.setValidCharacters("0123456789");
            formatoDois.install(jtf);
        } catch (ParseException var3) {
            JOptionPane.showMessageDialog((Component)null, (Object)null, "Não foi possivel inserir mask nos campos cep: " + var3.getMessage(), 0);
            ProgramaGas.salvarErro(var3.getMessage() + "  Local:  " + var3.getLocalizedMessage());
        }

    }

    public static void mascaraNumero(JFormattedTextField jtf) {
        try {
            MaskFormatter formatoDois = new MaskFormatter("***********");
            formatoDois.setValidCharacters("0123456789");
            formatoDois.install(jtf);
        } catch (ParseException var3) {
            JOptionPane.showMessageDialog((Component)null, (Object)null, "Não foi possivel inserir mask nos campos cep: " + var3.getMessage(), 0);
        }

    }

    public static void mascaraData(JFormattedTextField jtf) {
        try {
            MaskFormatter formatoDois = new MaskFormatter("**/**/****");
            formatoDois.setValidCharacters("0123456789");
            formatoDois.install(jtf);
        } catch (ParseException var3) {
            JOptionPane.showMessageDialog((Component)null, (Object)null, "Não foi possivel inserir mask nos campos cep: " + var3.getMessage(), 0);
        }

    }
}

