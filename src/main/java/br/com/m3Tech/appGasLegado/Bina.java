package br.com.m3Tech.appGasLegado;

import programagas.CadastrarNovoCliente;
import programagas.Metodos;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

public class Bina extends JFrame implements Runnable, SerialPortEventListener {
    Enumeration listaDePortas = CommPortIdentifier.getPortIdentifiers();
    SerialPort portaSerial;
    static CommPortIdentifier portId;
    InputStream inputStream;
    Thread readThread;
    Metodos m = new Metodos();
    private JButton SalvarErro;
    private static JTextArea atLog;
    private JButton bConectar;
    private JButton bSalvar;
    private JComboBox<String> cbPortas;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JPanel jPanel1;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane3;
    private JLabel msgTxt;
    private JLabel systemError;
    private JTable tBina;

    public Bina() {
        this.initComponents();
        atLog.setLineWrap(true);

        while(this.listaDePortas.hasMoreElements()) {
            CommPortIdentifier ips = (CommPortIdentifier)this.listaDePortas.nextElement();
            this.cbPortas.addItem(ips.getName());
        }

        String sql = "Select * FROM config";

        try {
            programagas.Conectar.pesquisar(sql);
            if (programagas.Conectar.rs.next()) {
                this.cbPortas.setSelectedItem(programagas.Conectar.rs.getString("PORTA"));
            }
        } catch (SQLException var3) {
            salvarErro(var3.getMessage());
            this.systemError.setText(var3.toString());
        }

        ActionEvent evt = null;
        this.bConectarActionPerformed((ActionEvent)evt);
    }

    public void AbrirPorta() {
        try {
            this.portaSerial = (SerialPort)portId.open("SimpleReadApp", 1000);
            this.msgTxt.setText("Conectado");
            atLog.append("Conectado com sucesso\r\n");
            this.msgTxt.setForeground(Color.green);
        } catch (PortInUseException var5) {
            salvarErro(var5.getMessage());
            this.msgTxt.setText("Erro");
            atLog.append("Erro ao conectar, porta em uso.\r\n");
            this.msgTxt.setForeground(Color.red);
            this.systemError.setText(var5.toString());
        }

        try {
            this.inputStream = this.portaSerial.getInputStream();
        } catch (IOException var4) {
            salvarErro(var4.getMessage());
            this.msgTxt.setText("Erro");
            this.msgTxt.setForeground(Color.red);
            this.systemError.setText(var4.toString());
        }

        try {
            this.portaSerial.addEventListener(this);
        } catch (TooManyListenersException var3) {
            salvarErro(var3.getMessage());
            this.msgTxt.setText("Erro");
            this.msgTxt.setForeground(Color.red);
            this.systemError.setText(var3.toString());
        }

        this.portaSerial.notifyOnDataAvailable(true);

        try {
            this.portaSerial.setSerialPortParams(19200, 8, 1, 0);
            this.portaSerial.setDTR(true);
            this.portaSerial.setRTS(false);
        } catch (UnsupportedCommOperationException var2) {
            salvarErro(var2.getMessage());
            this.msgTxt.setText("Erro");
            this.msgTxt.setForeground(Color.red);
            this.systemError.setText(var2.toString());
        }

        this.readThread = new Thread(this);
        this.readThread.start();
    }

    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.cbPortas = new JComboBox();
        this.jLabel1 = new JLabel();
        this.bConectar = new JButton();
        this.jScrollPane1 = new JScrollPane();
        this.tBina = new JTable();
        this.systemError = new JLabel();
        this.msgTxt = new JLabel();
        this.bSalvar = new JButton();
        this.jLabel2 = new JLabel();
        this.jScrollPane3 = new JScrollPane();
        atLog = new JTextArea();
        this.SalvarErro = new JButton();
        this.setDefaultCloseOperation(2);
        this.setTitle("Bina");
        this.jPanel1.setBackground(new Color(255, 255, 255));
        this.cbPortas.setFont(new Font("Tahoma", 1, 12));
        this.cbPortas.setForeground(new Color(0, 0, 204));
        this.jLabel1.setFont(new Font("Tahoma", 1, 12));
        this.jLabel1.setText("Porta Comm");
        this.bConectar.setText("Conectar");
        this.bConectar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                bConectarActionPerformed(evt);
            }
        });
        this.tBina.setFont(new Font("Tahoma", 1, 12));
        this.tBina.setForeground(new Color(0, 0, 204));
        this.tBina.setModel(new DefaultTableModel(new Object[0][], new String[]{"ID", "Tipo", "Numero"}) {
            Class[] types = new Class[]{String.class, String.class, String.class};
            boolean[] canEdit = new boolean[]{false, false, false};

            public Class getColumnClass(int columnIndex) {
                return this.types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.jScrollPane1.setViewportView(this.tBina);
        this.systemError.setForeground(new Color(204, 0, 0));
        this.msgTxt.setFont(new Font("Tahoma", 1, 12));
        this.msgTxt.setForeground(new Color(0, 153, 0));
        this.bSalvar.setText("Salvar");
        this.bSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                bSalvarActionPerformed(evt);
            }
        });
        this.jLabel2.setText("LOG");
        atLog.setColumns(20);
        atLog.setRows(5);
        this.jScrollPane3.setViewportView(atLog);
        this.SalvarErro.setText("Salvar erro");
        this.SalvarErro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                SalvarErroActionPerformed(evt);
            }
        });
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING, false).addComponent(this.jScrollPane1, Alignment.LEADING, -2, 0, 32767).addGroup(Alignment.LEADING, jPanel1Layout.createSequentialGroup().addComponent(this.jLabel1).addGap(29, 29, 29).addComponent(this.cbPortas, -2, 95, -2).addGap(39, 39, 39).addComponent(this.bConectar, -2, 109, -2)).addComponent(this.msgTxt, Alignment.LEADING, -1, -1, 32767).addGroup(jPanel1Layout.createSequentialGroup().addGap(273, 273, 273).addComponent(this.systemError, -1, -1, 32767).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.bSalvar, -2, 73, -2))).addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(182, 182, 182).addComponent(this.jLabel2).addContainerGap(278, 32767)).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(28, 28, 28).addComponent(this.jScrollPane3)).addGroup(Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent(this.SalvarErro))).addContainerGap()))));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.cbPortas, -2, -1, -2).addComponent(this.jLabel1).addComponent(this.bConectar).addComponent(this.SalvarErro)).addPreferredGap(ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING).addComponent(this.msgTxt, -2, 21, -2).addComponent(this.jLabel2)).addPreferredGap(ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addComponent(this.jScrollPane1, -2, 402, -2).addComponent(this.jScrollPane3, -2, 388, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING).addComponent(this.systemError, -2, 24, -2).addComponent(this.bSalvar)).addContainerGap(23, 32767)));
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(this.jPanel1, -1, -1, 32767));
        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jPanel1, -1, -1, 32767).addContainerGap()));
        this.pack();
    }

    private void bConectarActionPerformed(ActionEvent evt) {
        boolean portFound = false;
        String defaultPort = this.cbPortas.getSelectedItem().toString();
        this.listaDePortas = CommPortIdentifier.getPortIdentifiers();

        while(this.listaDePortas.hasMoreElements()) {
            portId = (CommPortIdentifier)this.listaDePortas.nextElement();
            if (portId.getPortType() == 1 && portId.getName().equals(defaultPort)) {
                atLog.append("Porta encontrada: " + defaultPort + "\r\n");
                portFound = true;
                this.AbrirPorta();
            }
        }

        if (!portFound) {
            this.systemError.setText("porta " + defaultPort + " não encontrada.");
        }

    }

    private void bSalvarActionPerformed(ActionEvent evt) {
        String porta = this.cbPortas.getSelectedItem().toString();
        String sql = "Update Config set PORTA = '" + porta + "' where id_config = 1 ";

        try {
            programagas.Conectar.alterar(sql);
        } catch (SQLException var5) {
            Logger.getLogger(programagas.Bina.class.getName()).log(Level.SEVERE, (String)null, var5);
            this.systemError.setText(var5.toString());
            atLog.append(var5.getMessage() + "\r\n");
            salvarErro(var5.getMessage());
        }

    }

    public static void salvarErro(String text) {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        String hoje = dateFormat.format(date);

        try {
            FileWriter fw = new FileWriter("C:/BancoDeDados/ProgramaGas/logErros/erroBina " + hoje + ".txt", true);
            BufferedWriter BW = new BufferedWriter(fw);
            BW.write(text);
            BW.newLine();
            BW.close();
        } catch (IOException var6) {
            Logger.getLogger(programagas.Bina.class.getName()).log(Level.SEVERE, (String)null, var6);
            atLog.append(var6.getMessage());
        }

    }

    private void SalvarErroActionPerformed(ActionEvent evt) {
        salvarErro(atLog.getText());
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
            Logger.getLogger(programagas.Bina.class.getName()).log(Level.SEVERE, (String)null, var5);
        }

        EventQueue.invokeLater(() -> {
            (new programagas.Bina()).setVisible(true);
        });
    }

    public void run() {
        try {
            Thread.sleep(20000L);
        } catch (InterruptedException var2) {
            atLog.append(var2.getMessage() + "\r\n");
            salvarErro(var2.getMessage());
        }

    }

    public void serialEvent(SerialPortEvent event) {
        switch (event.getEventType()) {
            case 1:
                byte[] dadosLidos1 = new byte[40];

                String numero1;
                try {
                    if (this.inputStream.available() > 7) {
                        Thread.sleep(1000L);
                        this.inputStream.read(dadosLidos1);
                        String numero = new String(dadosLidos1);
                        atLog.append("Numero  " + numero + "  " + Integer.toString(numero.length()) + "\r\n");
                        numero1 = this.m.numero(numero);
                        atLog.append("Numero1 " + numero1 + "  " + Integer.toString(numero1.length()) + "\r\n");
                        Object evt;
                        if (numero1.length() != 0 && numero1.length() >= 5) {
                            String numeroCerto;
                            String tipo;
                            if (numero1.length() == 12 & "9".equals(numero1.substring(3, 4))) {
                                tipo = "Celular";
                                numeroCerto = numero1.substring(1, 12);
                                atLog.append("Numero certo: " + numeroCerto + "\r\n");
                            } else if (numero1.length() == 11 & !"9".equals(numero1.substring(3, 4)) & !"1".equals(numero1.substring(3, 4)) & !"8".equals(numero1.substring(3, 4)) & !"7".equals(numero1.substring(3, 4)) & !"6".equals(numero1.substring(3, 4))) {
                                tipo = "Fixo";
                                numeroCerto = numero1.substring(1, 11);
                                atLog.append("Numero certo: " + numeroCerto + "\r\n");
                            } else if ("1".equals(numero1.substring(3, 4))) {
                                tipo = "Erro";
                                numeroCerto = this.encontrarString(numero1);
                                atLog.append("Numero certo: " + numeroCerto + "\r\n");
                            } else {
                                tipo = "Erro";
                                numeroCerto = this.encontrarString(numero1);
                                atLog.append("Numero certo: " + numeroCerto + " Erro." + "\r\n");
                            }

                            this.carregarTela(numeroCerto, tipo);
                            this.portaSerial.close();
                            evt = null;
                            this.bConectarActionPerformed((ActionEvent)evt);
                        } else {
                            this.portaSerial.close();
                            evt = null;
                            this.bConectarActionPerformed((ActionEvent)evt);
                        }
                    }
                } catch (Exception var8) {
                    salvarErro(var8.getMessage());
                    atLog.append(var8.getMessage() + "\r\n");
                    this.portaSerial.close();
                    numero1 = null;
                    //this.bConectarActionPerformed(numero1);
                }
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            default:
        }
    }

    private String encontrarString(String numero) {
        int indice;
        if (numero.contains("211")) {
            indice = numero.indexOf("211");
            numero = numero.substring(indice + 1, numero.length());
        } else if (numero.contains("111")) {
            indice = numero.indexOf("111");
            numero = numero.substring(indice + 1, numero.length());
        }

        return numero;
    }

    private void carregarTela(String telefone, String tipo) {
        int i = this.tBina.getRowCount() + 1;
        String[] conteudo = new String[]{Integer.toString(i), tipo, telefone};
        DefaultTableModel model = (DefaultTableModel)this.tBina.getModel();
        model.addRow(conteudo);
        String sql = "SELECT * FROM CLIENTES where telefone =  '" + telefone + "'";

        try {
            programagas.Conectar.pesquisar(sql);
            if (Conectar.rs.next()) {
                (new TelaPedidos(telefone)).setVisible(true);
            } else {
                CadastrarNovoCliente cnc = new CadastrarNovoCliente();
                cnc.setVisible(true);
                cnc.telefoneTxt.setText(telefone);
            }
        } catch (SQLException var8) {
            salvarErro(var8.getMessage());
            Vendas.systemError.setText(var8.getMessage());
            atLog.append(var8.getMessage() + "\r\n");
        }

    }
}

