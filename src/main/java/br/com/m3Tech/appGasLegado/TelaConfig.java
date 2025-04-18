package br.com.m3Tech.appGasLegado;

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
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.Border;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

public class TelaConfig extends JFrame {

    private JButton bOK;
    private JButton bSalvarData;
    private JButton bSalvarImpressora;
    private JButton bSalvarPorta;
    private JComboBox<String> boxImpressoras;
    private JComboBox<String> comboBoxPortas;
    private JLabel jLabel1;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JPanel jPanel1;
    private JLabel systemError;
    private JFormattedTextField txtNovaData;

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

    }

    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.comboBoxPortas = new JComboBox();
        this.jLabel1 = new JLabel();
        this.bSalvarPorta = new JButton();
        this.bSalvarData = new JButton();
        this.txtNovaData = new JFormattedTextField();
        this.jLabel3 = new JLabel();
        this.boxImpressoras = new JComboBox();
        this.bSalvarImpressora = new JButton();
        this.jLabel4 = new JLabel();
        this.bOK = new JButton();
        this.systemError = new JLabel();
        this.setDefaultCloseOperation(2);
        this.setTitle("Configurações");
        this.comboBoxPortas.setBorder((Border)null);
        this.jLabel1.setFont(new Font("Tahoma", 1, 12));
        this.jLabel1.setHorizontalAlignment(0);
        this.jLabel1.setText("Portas Disponiveis");
        this.bSalvarPorta.setText("Salvar porta principal");
        this.bSalvarPorta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                bSalvarPortaActionPerformed(evt);
            }
        });
        this.bSalvarData.setText("Salvar Data");
        this.bSalvarData.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                bSalvarDataActionPerformed(evt);
            }
        });
        this.txtNovaData.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter()));
        this.txtNovaData.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                txtNovaDataFocusGained(evt);
            }
        });
        this.jLabel3.setFont(new Font("Tahoma", 1, 12));
        this.jLabel3.setHorizontalAlignment(0);
        this.jLabel3.setText("Impressoras Disponiveis");
        this.boxImpressoras.setBorder((Border)null);
        this.bSalvarImpressora.setText("Salvar impressora  principal");
        this.bSalvarImpressora.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                bSalvarImpressoraActionPerformed(evt);
            }
        });
        this.jLabel4.setFont(new Font("Tahoma", 1, 12));
        this.jLabel4.setHorizontalAlignment(0);
        this.jLabel4.setText("Nova Data Limite");
        this.bOK.setText("OK");
        this.bOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                bOKActionPerformed(evt);
            }
        });

        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING, false).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING, false).addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel1, -2, 183, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.comboBoxPortas, -2, 183, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel3, -2, 183, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.boxImpressoras, -2, 183, -2))).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel4, -2, 183, -2).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent(this.txtNovaData, -2, 183, -2))).addGap(34, 34, 34).addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING, false).addComponent(this.bSalvarImpressora, -1, -1, 32767).addComponent(this.bSalvarPorta, -1, -1, 32767).addComponent(this.bSalvarData, -1, -1, 32767))).addGroup(jPanel1Layout.createSequentialGroup().addGap(31, 31, 31).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent(this.bOK))).addContainerGap(41, 32767)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.comboBoxPortas, -2, 23, -2).addComponent(this.bSalvarPorta)).addGap(28, 28, 28).addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.boxImpressoras, -2, 23, -2).addComponent(this.bSalvarImpressora)).addGap(18, 18, 18).addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.txtNovaData, -2, -1, -2).addComponent(this.bSalvarData).addComponent(this.jLabel4)).addPreferredGap(ComponentPlacement.RELATED, 217, 32767).addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addComponent(this.bOK, -2, 41, -2)).addContainerGap()));
        this.systemError.setForeground(new Color(204, 0, 0));
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(this.jPanel1, -1, -1, 32767).addGroup(layout.createSequentialGroup().addComponent(this.systemError, -2, 605, -2).addGap(0, 13, 32767))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jPanel1, -2, -1, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.systemError, -1, -1, 32767).addContainerGap()));
        this.pack();
    }

    private void bSalvarDataActionPerformed(ActionEvent evt) {
        String novaData = this.txtNovaData.getText();
        String sql = "UPDATE CONFIG set DATA = '" + novaData + "' where id_config = 1";

        try {
            programagas.Conectar.alterar(sql);
            this.systemError.setText("Nova Data salva.");
        } catch (SQLException var5) {
            programagas.ProgramaGas.salvarErro(var5.getMessage() + "  Local:  " + var5.getLocalizedMessage());
            this.systemError.setText(var5.getMessage());
        }

    }

    private void bOKActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void bSalvarImpressoraActionPerformed(ActionEvent evt) {
        String impressora = this.boxImpressoras.getSelectedItem().toString();
        String sql = "UPDATE CONFIG set IMPRESSORA = '" + impressora + "' where id_config = 1";

        try {
            programagas.Conectar.alterar(sql);
            this.systemError.setText("Impressora salva.");
        } catch (SQLException var5) {
            programagas.ProgramaGas.salvarErro(var5.getMessage() + "  Local:  " + var5.getLocalizedMessage());
            this.systemError.setText(var5.getMessage());
        }

    }

    private void bSalvarPortaActionPerformed(ActionEvent evt) {
        String porta = this.comboBoxPortas.getSelectedItem().toString();
        String sql = "UPDATE CONFIG set PORTA = '" + porta + "' where id_config = 1";

        try {
            Conectar.alterar(sql);
            this.systemError.setText("Porta preferencial salva.");
        } catch (SQLException var5) {
            programagas.ProgramaGas.salvarErro(var5.getMessage() + "  Local:  " + var5.getLocalizedMessage());
            this.systemError.setText(var5.getMessage());
        }

    }



    private void txtNovaDataFocusGained(FocusEvent evt) {
        this.txtNovaData.setCaretPosition(0);
    }

    public static void main(String[] args) {
        try {
            UIManager.LookAndFeelInfo[] var1 = UIManager.getInstalledLookAndFeels();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                UIManager.LookAndFeelInfo info = var1[var3];
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException | ClassNotFoundException var5) {
            Logger.getLogger(programagas.TelaConfig.class.getName()).log(Level.SEVERE, (String)null, var5);
            ProgramaGas.salvarErro(var5.getMessage() + "  Local:  " + var5.getLocalizedMessage());
        }

        EventQueue.invokeLater(() -> {
            (new programagas.TelaConfig()).setVisible(true);
        });
    }
}

