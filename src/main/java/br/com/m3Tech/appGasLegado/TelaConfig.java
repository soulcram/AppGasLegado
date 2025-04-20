package br.com.m3Tech.appGasLegado;

import br.com.m3Tech.appGasLegado.entity.Config;
import br.com.m3Tech.appGasLegado.service.ConfigService;
import br.com.m3Tech.utils.StringUtils;
import programagas.Mascaras;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.comm.CommPortIdentifier;
import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.DocFlavor.SERVICE_FORMATTED;
import javax.print.attribute.AttributeSet;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.Border;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

public class TelaConfig extends JFrame {

    private JButton bOK;
    private JButton bSalvar;
    private JComboBox<String> boxImpressoras;
    private JComboBox<String> comboBoxPortas;
    private JLabel jLabel1;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabelNomeLoja;
    private JLabel jLabelUrlService;
    private JLabel jLabelContextService;
    private JLabel jLabelTelIni;
    private JLabel jLabelTelFim;
    private JPanel jPanel1;
    private JLabel systemError;
    private JFormattedTextField txtNovaData;
    private JTextField txtIniTel;
    private JTextField txtFimTel;
    private JTextField txtNomeLoja;
    private JTextField txtUrlService;
    private JTextField txtContextService;

    public TelaConfig() {
        this.initComponents();
        Mascaras.mascaraData(this.txtNovaData);
        Enumeration listaDePortas = CommPortIdentifier.getPortIdentifiers();
        int i = 0;

        while(listaDePortas.hasMoreElements()) {
            CommPortIdentifier ips = (CommPortIdentifier)listaDePortas.nextElement();
            ++i;
            this.comboBoxPortas.addItem(ips.getName());
        }

        try {
            this.boxImpressoras.removeAllItems();
            DocFlavor df = SERVICE_FORMATTED.PRINTABLE;
            PrintService[] ps = PrintServiceLookup.lookupPrintServices(df, (AttributeSet)null);
            PrintService[] var5 = ps;
            int var6 = ps.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                PrintService p = var5[var7];
                this.boxImpressoras.addItem(p.getName());
            }
        } catch (Exception var9) {
            programagas.ProgramaGas.salvarErro(var9.getMessage() + "  Local:  " + var9.getLocalizedMessage());
        }

        //Carregando Configurações do banco
        ConfigService configService = new ConfigService();

        Config config = configService.getConfig();
        if(config != null) {
            if (!StringUtils.emptyOrNull(config.getPortaCom())) {
                this.comboBoxPortas.setSelectedItem(config.getPortaCom());
            }
            if (!StringUtils.emptyOrNull(config.getImpressora())) {
                this.boxImpressoras.setSelectedItem(config.getImpressora());
            }
            if (!StringUtils.emptyOrNull(config.getData())) {
                this.txtNovaData.setText(config.getData());
            }
            if (!StringUtils.emptyOrNull(config.getNomeloja())) {
                this.txtNomeLoja.setText(config.getNomeloja());
            }
            if (!StringUtils.emptyOrNull(config.getUrlService())) {
                this.txtUrlService.setText(config.getUrlService());
            }
            if (!StringUtils.emptyOrNull(config.getContextService())) {
                this.txtContextService.setText(config.getContextService());
            }
            if (!StringUtils.emptyOrNull(config.getIniTel())) {
                this.txtIniTel.setText(config.getIniTel());
            }
            if (!StringUtils.emptyOrNull(config.getFimTel())) {
                this.txtFimTel.setText(config.getFimTel());
            }
        }

    }

    private void initComponents() {
        this.jPanel1 = new JPanel();

        this.bOK = new JButton();
        this.systemError = new JLabel();
        this.setDefaultCloseOperation(2);
        this.setTitle("Configurações");

        this.jLabel1 = new JLabel();
        this.jLabel1.setBounds(10,10,150,30);
        this.jLabel1.setFont(new Font("Tahoma", 1, 12));
        this.jLabel1.setText("Portas Disponiveis");

        this.comboBoxPortas = new JComboBox();
        this.comboBoxPortas.setBorder((Border)null);
        this.comboBoxPortas.setBounds(180,10,250,30);

        this.jLabel3 = new JLabel();
        this.jLabel3.setBounds(10,50,150,30);
        this.jLabel3.setFont(new Font("Tahoma", 1, 12));
        this.jLabel3.setText("Impressoras Disponiveis");

        this.boxImpressoras = new JComboBox();
        this.boxImpressoras.setBorder((Border)null);
        this.boxImpressoras.setBounds(180,50,250,30);

        this.jLabel4 = new JLabel();
        this.jLabel4.setBounds(10,90,150,30);
        this.jLabel4.setFont(new Font("Tahoma", 1, 12));
        this.jLabel4.setText("Nova Data Limite");

        this.txtNovaData = new JFormattedTextField();
        this.txtNovaData.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter()));
        this.txtNovaData.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                txtNovaDataFocusGained(evt);
            }
        });
        this.txtNovaData.setBounds(180,90,250,30);

        this.jLabelNomeLoja = new JLabel();
        this.jLabelNomeLoja.setBounds(10,130,150,30);
        this.jLabelNomeLoja.setFont(new Font("Tahoma", 1, 12));
        this.jLabelNomeLoja.setText("Nome Loja");

        this.txtNomeLoja = new JTextField();
        this.txtNomeLoja.setBounds(180,130,250,30);

        this.jLabelUrlService = new JLabel();
        this.jLabelUrlService.setBounds(10,170,150,30);
        this.jLabelUrlService.setFont(new Font("Tahoma", 1, 12));
        this.jLabelUrlService.setText("Url Service");

        this.txtUrlService = new JTextField();
        this.txtUrlService.setBounds(180,170,250,30);

        this.jLabelContextService = new JLabel();
        this.jLabelContextService.setBounds(10,210,150,30);
        this.jLabelContextService.setFont(new Font("Tahoma", 1, 12));
        this.jLabelContextService.setText("Context Service");

        this.txtContextService = new JTextField();
        this.txtContextService.setBounds(180,210,250,30);

        this.jLabelTelIni = new JLabel();
        this.jLabelTelIni.setBounds(10,250,150,30);
        this.jLabelTelIni.setFont(new Font("Tahoma", 1, 12));
        this.jLabelTelIni.setText("Tel Ini");

        this.txtIniTel = new JTextField();
        this.txtIniTel.setBounds(180,250,250,30);

        this.jLabelTelFim = new JLabel();
        this.jLabelTelFim.setBounds(10,290,150,30);
        this.jLabelTelFim.setFont(new Font("Tahoma", 1, 12));
        this.jLabelTelFim.setText("Tel Fim");

        this.txtFimTel = new JTextField();
        this.txtFimTel.setBounds(180,290,250,30);


        this.bOK.setText("OK");
        this.bOK.setBounds(300,460,60,30);
        this.bOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                bOKActionPerformed(evt);
            }
        });

        jPanel1.setBounds(10,10,600,600);
        jPanel1.setLayout(null);
        jPanel1.setBackground(Color.WHITE);

        jPanel1.add(jLabel1);
        jPanel1.add(comboBoxPortas);

        jPanel1.add(jLabel3);
        jPanel1.add(boxImpressoras);

        jPanel1.add(jLabel4);
        jPanel1.add(txtNovaData);

        jPanel1.add(jLabelNomeLoja);
        jPanel1.add(txtNomeLoja);

        jPanel1.add(jLabelUrlService);
        jPanel1.add(txtUrlService);

        jPanel1.add(jLabelContextService);
        jPanel1.add(txtContextService);

        jPanel1.add(jLabelTelIni);
        jPanel1.add(txtIniTel);

        jPanel1.add(jLabelTelFim);
        jPanel1.add(txtFimTel);


        jPanel1.add(bOK);

        this.setBounds(10, 10, 600, 600);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.add(jPanel1);
        this.repaint();

    }


    private void bOKActionPerformed(ActionEvent evt) {

        Config config = new Config();

        String novaData = this.txtNovaData.getText();
        String impressora = this.boxImpressoras.getSelectedItem() != null ? this.boxImpressoras.getSelectedItem().toString() : null;
        String porta = this.comboBoxPortas.getSelectedItem() != null ? this.comboBoxPortas.getSelectedItem().toString() : null;
        String nomeLoja = this.txtNomeLoja.getText();
        String urlService = this.txtUrlService.getText();
        String contextService = !StringUtils.emptyOrNull(this.txtContextService.getText()) ? this.txtContextService.getText() : "appConsigaz";
        String telIni = this.txtIniTel.getText();
        String telFim = this.txtFimTel.getText();

        config.setData(novaData);
        config.setImpressora(impressora);
        config.setPortaCom(porta);
        config.setNomeloja(nomeLoja);
        config.setUrlService(urlService);
        config.setContextService(contextService);
        config.setIniTel(telIni);
        config.setFimTel(telFim);

       new ConfigService().salvarConfig(config);

        this.dispose();
    }


    private void txtNovaDataFocusGained(FocusEvent evt) {
        this.txtNovaData.setCaretPosition(0);
    }

}

