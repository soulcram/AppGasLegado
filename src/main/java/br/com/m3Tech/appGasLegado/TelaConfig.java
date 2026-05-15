package br.com.m3Tech.appGasLegado;

import br.com.m3Tech.appGasLegado.entity.Config;
import br.com.m3Tech.appGasLegado.service.ConfigService;
import br.com.m3Tech.appGasLegado.ui.AppTheme;
import br.com.m3Tech.appGasLegado.ui.UiComponents;
import br.com.m3Tech.appGasLegado.ui.UiLayout;
import br.com.m3Tech.utils.StringUtils;
import programagas.Mascaras;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Enumeration;
import javax.comm.CommPortIdentifier;
import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.DocFlavor.SERVICE_FORMATTED;
import javax.print.attribute.AttributeSet;
import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

public class TelaConfig extends JFrame {

    private JButton bOK;
    private JComboBox<String> boxImpressoras;
    private JComboBox<String> comboBoxPortas;
    private JLabel systemError;
    private JFormattedTextField txtNovaData;
    private JTextField txtIniTel;
    private JTextField txtFimTel;
    private JTextField txtNomeLoja;
    private JTextField txtUrlService;
    private JTextField txtContextService;
    private JCheckBox servicoCheckBox;

    public TelaConfig() {
        this.initComponents();
        Mascaras.mascaraData(this.txtNovaData);
        Enumeration listaDePortas = CommPortIdentifier.getPortIdentifiers();

        while (listaDePortas.hasMoreElements()) {
            CommPortIdentifier ips = (CommPortIdentifier) listaDePortas.nextElement();
            this.comboBoxPortas.addItem(ips.getName());
        }

        try {
            this.boxImpressoras.removeAllItems();
            DocFlavor df = SERVICE_FORMATTED.PRINTABLE;
            PrintService[] ps = PrintServiceLookup.lookupPrintServices(df, (AttributeSet) null);

            for (PrintService p : ps) {
                this.boxImpressoras.addItem(p.getName());
            }
        } catch (Exception var9) {
            ProgramaGas.salvarErro(var9.getMessage() + "  Local:  " + var9.getLocalizedMessage());
        }
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Configurações");
        UiComponents.applyFrameDefaults(this);

        comboBoxPortas = new JComboBox<>();
        boxImpressoras = new JComboBox<>();
        txtNovaData = new JFormattedTextField();
        txtNovaData.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter()));
        txtNovaData.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                txtNovaDataFocusGained(evt);
            }
        });
        txtNomeLoja = new JTextField();
        txtUrlService = new JTextField();
        txtContextService = new JTextField();
        txtIniTel = new JTextField();
        txtFimTel = new JTextField();
        servicoCheckBox = new JCheckBox("Ativar integração com serviço");
        systemError = new JLabel();
        UiComponents.styleErrorLabel(systemError);

        bOK = UiComponents.primaryButton("Salvar");
        bOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                bOKActionPerformed(evt);
            }
        });

        JPanel form = UiLayout.formPanel("Parâmetros do sistema");
        int row = 0;
        UiLayout.addFormRow(form, row++, UiLayout.formRow("Porta COM", comboBoxPortas));
        UiLayout.addFormRow(form, row++, UiLayout.formRow("Impressora", boxImpressoras));
        UiLayout.addFormRow(form, row++, UiLayout.formRow("Data limite", txtNovaData));
        UiLayout.addFormRow(form, row++, UiLayout.formRow("Nome da loja", txtNomeLoja));
        UiLayout.addFormRow(form, row++, UiLayout.formRow("URL do serviço", txtUrlService));
        UiLayout.addFormRow(form, row++, UiLayout.formRow("Contexto", txtContextService));
        UiLayout.addFormRow(form, row++, UiLayout.formRow("Telefone inicial", txtIniTel));
        UiLayout.addFormRow(form, row++, UiLayout.formRow("Telefone final", txtFimTel));
        UiLayout.addFormRow(form, row, UiLayout.formRow("Serviço", servicoCheckBox));

        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        footer.add(systemError, BorderLayout.CENTER);
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.setOpaque(false);
        buttons.add(bOK);
        footer.add(buttons, BorderLayout.EAST);

        JPanel root = new JPanel(new BorderLayout(0, AppTheme.PAD));
        root.setBackground(AppTheme.BACKGROUND);
        root.setBorder(BorderFactory.createEmptyBorder(AppTheme.PAD_LG, AppTheme.PAD_LG, AppTheme.PAD_LG, AppTheme.PAD_LG));
        root.add(UiComponents.headerBar("Configurações"), BorderLayout.NORTH);
        root.add(form, BorderLayout.CENTER);
        root.add(footer, BorderLayout.SOUTH);
        getContentPane().add(root);
        pack();
        setSize(Math.max(getWidth(), 520), Math.max(getHeight(), 480));
    }

    private void bOKActionPerformed(ActionEvent evt) {
        Config config = new Config();

        String novaData = this.txtNovaData.getText();
        String impressora = this.boxImpressoras.getSelectedItem() != null ? this.boxImpressoras.getSelectedItem().toString() : "Generic";
        String porta = this.comboBoxPortas.getSelectedItem() != null ? this.comboBoxPortas.getSelectedItem().toString() : "Com1";
        String nomeLoja = this.txtNomeLoja.getText();
        String urlService = this.txtUrlService.getText();
        String contextService = !StringUtils.emptyOrNull(this.txtContextService.getText()) ? this.txtContextService.getText() : "appConsigaz";
        String telIni = this.txtIniTel.getText();
        String telFim = this.txtFimTel.getText();
        Boolean servico = this.servicoCheckBox.isSelected();

        config.setData(novaData);
        config.setImpressora(impressora);
        config.setPortaCom(porta);
        config.setNomeloja(nomeLoja);
        config.setUrlService(urlService);
        config.setContextService(contextService);
        config.setIniTel(telIni);
        config.setFimTel(telFim);
        config.setServico(servico);

        new ConfigService().salvarConfig(config);
        this.dispose();
    }

    private void txtNovaDataFocusGained(FocusEvent evt) {
        this.txtNovaData.setCaretPosition(0);
    }
}
