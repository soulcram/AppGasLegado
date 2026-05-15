package br.com.m3Tech.appGasLegado;

import br.com.m3Tech.appGasLegado.dto.ClienteDto;
import br.com.m3Tech.appGasLegado.entity.Config;
import br.com.m3Tech.appGasLegado.service.ClienteService;
import br.com.m3Tech.appGasLegado.service.ConfigService;
import br.com.m3Tech.appGasLegado.ui.AppTheme;
import br.com.m3Tech.appGasLegado.ui.UiComponents;
import br.com.m3Tech.appGasLegado.utils.BinaUtils;
import br.com.m3Tech.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import programagas.Metodos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
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


@Slf4j
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
            Conectar.pesquisar(sql);
            if (Conectar.rs.next()) {
                this.cbPortas.setSelectedItem(Conectar.rs.getString("PORTA"));
            }
            Conectar.rs.close();
        } catch (SQLException var3) {
            log.error("Erros",var3);
            salvarErro(var3.getMessage());
            this.systemError.setText(var3.toString());
        }

        ActionEvent evt = null;
        this.bConectarActionPerformed((ActionEvent)evt);
    }

    private void conectarPorta() {

        Config configuracaoGlobal = new ConfigService().getConfig();

        String portaComm = configuracaoGlobal.getPortaCom();

        if(StringUtils.emptyOrNull(portaComm)) {
            return ;
        }

        boolean portFound = false;

        if (portaSerial != null) {
            portaSerial.close();
        }

        listaDePortas = CommPortIdentifier.getPortIdentifiers();

        while (listaDePortas.hasMoreElements()) {
            portId = (CommPortIdentifier) listaDePortas.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                if (portId.getName().equals(portaComm)) {
                    System.out.println("Porta encontrada: " + portaComm + "\r\n");

                    portFound = true;
                    AbrirPorta();
                    break;
                }
            }
        }
        if (!portFound) {
            System.err.println("porta " + portaComm + " nao encontrada.");

        }

    }

    public void AbrirPorta() {
        try {
            this.portaSerial = (SerialPort)portId.open("SimpleReadApp", 1000);
            this.msgTxt.setText("Conectado");
            atLog.append("Conectado com sucesso\r\n");
            this.msgTxt.setForeground(AppTheme.SUCCESS);
        } catch (PortInUseException var5) {
            salvarErro(var5.getMessage());
            log.error("Erros",var5);
            this.msgTxt.setText("Erro");
            atLog.append("Erro ao conectar, porta em uso.\r\n");
            this.msgTxt.setForeground(AppTheme.DANGER);
            this.systemError.setText(var5.toString());
        }

        try {
            this.inputStream = this.portaSerial.getInputStream();
        } catch (IOException var4) {
            log.error("Erros",var4);
            salvarErro(var4.getMessage());
            this.msgTxt.setText("Erro");
            this.msgTxt.setForeground(AppTheme.DANGER);
            this.systemError.setText(var4.toString());
        }

        try {
            this.portaSerial.addEventListener(this);
        } catch (TooManyListenersException var3) {
            log.error("Erros",var3);
            salvarErro(var3.getMessage());
            this.msgTxt.setText("Erro");
            this.msgTxt.setForeground(AppTheme.DANGER);
            this.systemError.setText(var3.toString());
        }

        this.portaSerial.notifyOnDataAvailable(true);

        try {
            this.portaSerial.setSerialPortParams(19200, 8, 1, 0);
            this.portaSerial.setDTR(true);
            this.portaSerial.setRTS(false);
        } catch (UnsupportedCommOperationException var2) {
            log.error("Erros",var2);
            salvarErro(var2.getMessage());
            this.msgTxt.setText("Erro");
            this.msgTxt.setForeground(AppTheme.DANGER);
            this.systemError.setText(var2.toString());
        }

        this.readThread = new Thread(this);
        this.readThread.start();
    }

    private void initComponents() {
        this.cbPortas = new JComboBox();
        this.jLabel1 = new JLabel("Porta COM");
        this.bConectar = UiComponents.primaryButton("Conectar");
        this.jScrollPane1 = new JScrollPane();
        this.tBina = new JTable();
        this.systemError = new JLabel();
        this.msgTxt = new JLabel("Aguardando conexão...");
        this.bSalvar = UiComponents.secondaryButton("Salvar porta");
        this.jLabel2 = new JLabel("Log");
        this.jScrollPane3 = new JScrollPane();
        atLog = new JTextArea();
        this.SalvarErro = UiComponents.secondaryButton("Salvar erro");

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Bina");
        UiComponents.applyFrameDefaults(this);

        jLabel1.setFont(AppTheme.fontLabelBold());
        jLabel2.setFont(AppTheme.fontLabelBold());
        msgTxt.setFont(AppTheme.fontLabelBold());
        msgTxt.setForeground(AppTheme.SUCCESS);
        UiComponents.styleErrorLabel(systemError);

        this.bConectar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                bConectarActionPerformed(evt);
            }
        });
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
        UiComponents.styleTable(this.tBina);
        this.jScrollPane1.setViewportView(this.tBina);
        this.bSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                bSalvarActionPerformed(evt);
            }
        });
        atLog.setColumns(20);
        atLog.setRows(5);
        this.jScrollPane3.setViewportView(atLog);
        this.SalvarErro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                SalvarErroActionPerformed(evt);
            }
        });

        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, AppTheme.PAD_SM, 0));
        toolbar.setOpaque(false);
        toolbar.add(jLabel1);
        toolbar.add(cbPortas);
        toolbar.add(bConectar);
        toolbar.add(SalvarErro);

        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setOpaque(false);
        statusBar.add(msgTxt, BorderLayout.WEST);
        statusBar.add(systemError, BorderLayout.CENTER);
        statusBar.add(bSalvar, BorderLayout.EAST);

        JPanel callsCard = UiComponents.cardPanel("Chamadas");
        callsCard.setLayout(new BorderLayout());
        callsCard.add(jScrollPane1, BorderLayout.CENTER);

        JPanel logCard = UiComponents.cardPanel("Log");
        logCard.setLayout(new BorderLayout());
        logCard.add(jScrollPane3, BorderLayout.CENTER);

        JPanel center = new JPanel(new BorderLayout(AppTheme.PAD, 0));
        center.setOpaque(false);
        center.add(callsCard, BorderLayout.CENTER);
        center.add(logCard, BorderLayout.EAST);
        logCard.setPreferredSize(new java.awt.Dimension(280, 0));

        JPanel root = new JPanel(new BorderLayout(0, AppTheme.PAD));
        root.setBackground(AppTheme.BACKGROUND);
        root.setBorder(javax.swing.BorderFactory.createEmptyBorder(AppTheme.PAD, AppTheme.PAD, AppTheme.PAD, AppTheme.PAD));
        root.add(UiComponents.headerBar("Bina"), BorderLayout.NORTH);
        JPanel top = new JPanel(new BorderLayout(0, AppTheme.PAD_SM));
        top.setOpaque(false);
        top.add(toolbar, BorderLayout.NORTH);
        top.add(statusBar, BorderLayout.SOUTH);
        root.add(top, BorderLayout.NORTH);
        root.add(center, BorderLayout.CENTER);
        getContentPane().add(root);
        pack();
        setSize(Math.max(getWidth(), 720), Math.max(getHeight(), 520));
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
            Conectar.alterar(sql);
        } catch (SQLException var5) {
            log.error("Erros",var5);
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
            log.error("Erros",var6);
            Logger.getLogger(programagas.Bina.class.getName()).log(Level.SEVERE, (String)null, var6);
            atLog.append(var6.getMessage());
        }

    }

    private void SalvarErroActionPerformed(ActionEvent evt) {
        salvarErro(atLog.getText());
    }

    public static void main(String[] args) {
        AppTheme.install();
        EventQueue.invokeLater(() -> {
            (new programagas.Bina()).setVisible(true);
        });
    }

    public void run() {
        try {
            Thread.sleep(20000L);
        } catch (InterruptedException var2) {
            log.error("Erros",var2);
            atLog.append(var2.getMessage() + "\r\n");
            salvarErro(var2.getMessage());
        }

    }

    public void serialEvent(SerialPortEvent event) {
        switch (event.getEventType()) {

            case SerialPortEvent.BI:

            case SerialPortEvent.OE:

            case SerialPortEvent.FE:

            case SerialPortEvent.PE:

            case SerialPortEvent.CD:

            case SerialPortEvent.CTS:

            case SerialPortEvent.DSR:

            case SerialPortEvent.RI:

            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                break;

            case SerialPortEvent.DATA_AVAILABLE:

                byte[] dadosLidos1 = new byte[40];



                try {

                    if (inputStream.available() > 7) {

                        Thread.sleep(1000);

                        inputStream.read(dadosLidos1);

                        String numero = new String(dadosLidos1);

                        System.out.println("Numero  " + numero + "  " + numero.length() + "\r\n");

                        String numero1 = m.numero(numero);

                        System.out.println("Numero1 " + numero1 + "  " + numero1.length() + "\r\n");

                        if(numero1.length() > 9){
                            String numeroCerto = BinaUtils.encontrarTelefone(numero1);

                            this.carregarTela(numeroCerto, numeroCerto.length() == 11 ? "Celular" : "Fixo");
                            portaSerial.close();
                            conectarPorta();
                        }else{
                            portaSerial.close();
                            conectarPorta();
                        }

                    }

                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                    log.error("Erros",ex);
                    portaSerial.close();
                    conectarPorta();

                }

                break;
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


        try {

            Service service = new Service();
            ClienteDto cliente = null;

            if (ProgramaGas.servico) {
                cliente = service.getCliente(telefone);
            }

            if(cliente != null && cliente.getClienteEnderecos() != null && !cliente.getClienteEnderecos().isEmpty()){
                cliente.setViaApi(true);
                (new TelaPedidos(cliente)).setVisible(true);
                new ClienteService().salvarCliente(cliente);
            }else {

                ClienteDto novoCliente = new ClienteDto();
                novoCliente.setTelefone(telefone);
                String sql = "SELECT * FROM CLIENTES where telefone =  '" + telefone + "'";
                Conectar.pesquisar(sql);
                if (Conectar.rs.next()) {
                    (new TelaPedidos(novoCliente)).setVisible(true);
                } else {
                    CadastrarNovoCliente cnc = new CadastrarNovoCliente();
                    cnc.setVisible(true);
                    cnc.telefoneTxt.setText(telefone);
                }
                Conectar.rs.close();
            }
        } catch (SQLException var8) {
            log.error("Erros",var8);
            salvarErro(var8.getMessage());
            Vendas.systemError.setText(var8.getMessage());
            atLog.append(var8.getMessage() + "\r\n");
        }

    }
}

