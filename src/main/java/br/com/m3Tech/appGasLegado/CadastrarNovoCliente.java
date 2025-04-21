package br.com.m3Tech.appGasLegado;



import br.com.m3Tech.appGasLegado.dto.ClienteDto;
import br.com.m3Tech.appGasLegado.dto.ClienteEndereco;
import br.com.m3Tech.appGasLegado.dto.Endereco;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import programagas.Mascaras;
import programagas.Metodos;

import java.awt.AWTKeyStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

public class CadastrarNovoCliente extends JFrame {
    Metodos m = new Metodos();
    public static JButton BotaoCadNovoEnd;
    private JTextField bairroTxt;
    private JFormattedTextField cepTxt;
    private JTextField cidadeTxt;
    private JLabel idCepTxt;
    private JLabel idClienteTxt;
    private JButton jButton1;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JScrollPane jScrollPane1;
    private JTable jTable1;
    private JLabel labelTxt;
    private JLabel labelTxt1;
    private JLabel labelTxt2;
    private JLabel labelTxt3;
    private JLabel labelTxt4;
    private JLabel labelTxt5;
    private JLabel labelTxt7;
    private JTextField logradouroTxt;
    private JLabel msgErro;
    private JLabel msgTxt;
    private JTextField nomeTxt;
    private JTextField numeroTxt;
    private JTextField obsTxt;
    private JLabel systemError;
    public JFormattedTextField telefoneTxt;
    private JComboBox<String> tp_LogradouroTxt;

    public CadastrarNovoCliente() {
        this.initComponents();
        HashSet conj = new HashSet(this.getFocusTraversalKeys(0));
        conj.add(AWTKeyStroke.getAWTKeyStroke(10, 0));
        this.setFocusTraversalKeys(0, conj);
        Mascaras.mascaraCep(this.cepTxt);
        BotaoCadNovoEnd.setVisible(false);
    }

    private String arrumarTelefone(String numero) {
        String telefone = numero;
        int tamNum = numero.length();
        if (tamNum > 10) {
            if (!"9".equals(numero.substring(tamNum - 9, tamNum - 8))) {
                telefone = numero.substring(tamNum - 10, tamNum);
            } else {
                telefone = numero.substring(tamNum - 11, tamNum);
            }
        }

        return telefone;
    }

    private boolean verificarTelefone() {
        String telefone = this.telefoneTxt.getText();
        boolean status = false;
        int tamanho = telefone.length();
        Color corErro = new Color(255, 100, 100);
        if (tamanho < 10) {
            this.telefoneTxt.setBackground(corErro);
            this.msgTxt.setText("Numero de telefone errado.");
            this.msgErro.setText("Numero de telefone errado.");
        } else if ("9".equals(telefone.substring(2, 3)) & tamanho != 11) {
            this.telefoneTxt.setBackground(corErro);
            this.msgTxt.setText("Numero de telefone errado.");
            this.msgErro.setText("Numero de telefone errado.");
        } else if ("1".equals(telefone.substring(2, 3))) {
            this.telefoneTxt.setBackground(corErro);
            this.msgTxt.setText("Numero de telefone errado.");
            this.msgErro.setText("Numero de telefone errado.");
        } else if (tamanho > 10 & !"9".equals(telefone.substring(2, 3))) {
            this.telefoneTxt.setBackground(corErro);
            this.msgTxt.setText("Numero de telefone errado.");
            this.msgErro.setText("Numero de telefone errado.");
        } else {
            this.telefoneTxt.setBackground(new Color(255, 255, 255));
            this.msgTxt.setText("");
            this.msgErro.setText("");
            status = true;
        }

        return status;
    }

    public void preencher(String telefone) {
        String sql = "select * from Clientes  INNER JOIN ENDERECO ON ID_CEP = ID_ENDERECO where TELEFONE = '" + telefone + "'";

        try {
            programagas.Conectar.pesquisar(sql);
            if (programagas.Conectar.rs.next()) {
                this.idClienteTxt.setText(programagas.Conectar.rs.getString("ID_CLIENTE"));
                this.idCepTxt.setText(programagas.Conectar.rs.getString("ID_CEP"));
                this.telefoneTxt.setText(telefone);
                this.nomeTxt.setText(programagas.Conectar.rs.getString("NOME"));
                this.obsTxt.setText(programagas.Conectar.rs.getString("OBSERVACAO"));
                this.cepTxt.setText(programagas.Conectar.rs.getString("CEP"));
                this.cidadeTxt.setText(programagas.Conectar.rs.getString("CIDADE"));
                this.tp_LogradouroTxt.setSelectedItem(programagas.Conectar.rs.getString("TP_LOGRADOURO"));
                this.logradouroTxt.setText(programagas.Conectar.rs.getString("LOGRADOURO"));
                this.numeroTxt.setText(programagas.Conectar.rs.getString("NUMERO"));
                this.bairroTxt.setText(programagas.Conectar.rs.getString("BAIRRO"));
            } else {
                this.idClienteTxt.setText("");
                this.idCepTxt.setText("");
                this.telefoneTxt.setText(telefone);
                this.nomeTxt.setText("");
                this.obsTxt.setText("");
                this.cepTxt.setText("");
                this.cidadeTxt.setText("");
                this.tp_LogradouroTxt.setSelectedItem("");
                this.logradouroTxt.setText("");
                this.numeroTxt.setText("");
                this.bairroTxt.setText("");
            }
        } catch (SQLException var4) {
            programagas.ProgramaGas.salvarErro(var4.getMessage() + "  Local:  " + var4.getLocalizedMessage());
            this.systemError.setText(var4.toString());
        }

    }

    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.idClienteTxt = new JLabel();
        this.labelTxt = new JLabel();
        this.labelTxt1 = new JLabel();
        this.jPanel2 = new JPanel();
        this.labelTxt2 = new JLabel();
        this.tp_LogradouroTxt = new JComboBox();
        this.idCepTxt = new JLabel();
        this.labelTxt4 = new JLabel();
        this.cepTxt = new JFormattedTextField();
        this.labelTxt3 = new JLabel();
        this.cidadeTxt = new JTextField();
        this.logradouroTxt = new JTextField();
        this.numeroTxt = new JTextField();
        this.labelTxt5 = new JLabel();
        this.bairroTxt = new JTextField();
        this.jScrollPane1 = new JScrollPane();
        this.jTable1 = new JTable();
        BotaoCadNovoEnd = new JButton();
        this.msgTxt = new JLabel();
        this.msgErro = new JLabel();
        this.labelTxt7 = new JLabel();
        this.nomeTxt = new JTextField();
        this.telefoneTxt = new JFormattedTextField();
        this.obsTxt = new JTextField();
        this.jButton1 = new JButton();
        this.systemError = new JLabel();
        this.setDefaultCloseOperation(2);
        this.setTitle("Cadastrar Novo Cliente");
        this.jPanel1.setBackground(new Color(255, 255, 153));
        this.jPanel1.setBorder(BorderFactory.createTitledBorder((Border)null, "Novo Cliente", 2, 1, new Font("Tahoma", 1, 14)));
        this.idClienteTxt.setFont(new Font("Tahoma", 1, 11));
        this.labelTxt.setFont(new Font("Tahoma", 1, 11));
        this.labelTxt.setText("telefone");
        this.labelTxt1.setFont(new Font("Tahoma", 1, 11));
        this.labelTxt1.setText("Nome");
        this.jPanel2.setBackground(new Color(255, 153, 51));
        this.jPanel2.setBorder(BorderFactory.createTitledBorder((Border)null, "Endereço", 2, 2, new Font("Tahoma", 1, 12)));
        this.labelTxt2.setFont(new Font("Tahoma", 1, 11));
        this.labelTxt2.setText("Numero");
        this.tp_LogradouroTxt.setFont(new Font("Tahoma", 1, 11));
        this.tp_LogradouroTxt.setModel(new DefaultComboBoxModel(new String[]{"Rua", "Avenida", "Viela", "Estrada", "Passagem", "Praça"}));
        this.idCepTxt.setFont(new Font("Tahoma", 1, 11));
        this.idCepTxt.addInputMethodListener(new InputMethodListener() {
            public void caretPositionChanged(InputMethodEvent evt) {
            }

            public void inputMethodTextChanged(InputMethodEvent evt) {
                idCepTxtInputMethodTextChanged(evt);
            }
        });
        this.labelTxt4.setFont(new Font("Tahoma", 1, 11));
        this.labelTxt4.setText("CEP");
        this.cepTxt.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                cepTxtFocusGained(evt);
            }

            public void focusLost(FocusEvent evt) {
                cepTxtFocusLost(evt);
            }
        });
        this.labelTxt3.setFont(new Font("Tahoma", 1, 11));
        this.labelTxt3.setText("Cidade");
        this.cidadeTxt.setFont(new Font("Tahoma", 1, 11));
        this.cidadeTxt.setText("São Paulo");
        this.cidadeTxt.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                cidadeTxtKeyTyped(evt);
            }
        });
        this.logradouroTxt.setFont(new Font("Tahoma", 1, 11));
        this.logradouroTxt.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                logradouroTxtFocusGained(evt);
            }
        });
        this.logradouroTxt.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                logradouroTxtKeyPressed(evt);
            }

            public void keyTyped(KeyEvent evt) {
                logradouroTxtKeyTyped(evt);
            }
        });
        this.numeroTxt.setFont(new Font("Tahoma", 1, 11));
        this.numeroTxt.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                numeroTxtKeyTyped(evt);
            }
        });
        this.labelTxt5.setFont(new Font("Tahoma", 1, 11));
        this.labelTxt5.setText("Bairro");
        this.bairroTxt.setFont(new Font("Tahoma", 1, 11));
        this.bairroTxt.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                bairroTxtKeyPressed(evt);
            }

            public void keyTyped(KeyEvent evt) {
                bairroTxtKeyTyped(evt);
            }
        });
        this.jTable1.setModel(new DefaultTableModel(new Object[0][], new String[]{"ID", "CEP", "LOGRADOURO", "BAIRRO"}) {
            Class[] types = new Class[]{String.class, String.class, String.class, String.class};
            boolean[] canEdit = new boolean[]{false, false, false, false};

            public Class getColumnClass(int columnIndex) {
                return this.types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.jTable1.setAutoResizeMode(0);
        this.jTable1.getTableHeader().setReorderingAllowed(false);
        this.jTable1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.jTable1);
        if (this.jTable1.getColumnModel().getColumnCount() > 0) {
            this.jTable1.getColumnModel().getColumn(0).setResizable(false);
            this.jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);
            this.jTable1.getColumnModel().getColumn(1).setResizable(false);
            this.jTable1.getColumnModel().getColumn(1).setPreferredWidth(100);
            this.jTable1.getColumnModel().getColumn(2).setResizable(false);
            this.jTable1.getColumnModel().getColumn(2).setPreferredWidth(325);
            this.jTable1.getColumnModel().getColumn(3).setResizable(false);
            this.jTable1.getColumnModel().getColumn(3).setPreferredWidth(175);
        }

        BotaoCadNovoEnd.setText("Cadastrar Novo Endereço");
        BotaoCadNovoEnd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                BotaoCadNovoEndActionPerformed(evt);
            }
        });
        this.msgTxt.setFont(new Font("Tahoma", 1, 14));
        this.msgTxt.setForeground(new Color(0, 0, 255));
        this.msgErro.setFont(new Font("Tahoma", 1, 11));
        GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
        this.jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING).addComponent(this.jScrollPane1).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addComponent(this.tp_LogradouroTxt, -2, -1, -2).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(this.logradouroTxt, -2, 385, -2).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(this.labelTxt2).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.numeroTxt, -1, 117, 32767)).addGroup(Alignment.TRAILING, jPanel2Layout.createSequentialGroup().addComponent(BotaoCadNovoEnd).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.msgTxt, -1, -1, 32767).addGap(167, 167, 167)).addGroup(jPanel2Layout.createSequentialGroup().addGap(96, 96, 96).addComponent(this.labelTxt4).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(this.cepTxt, -2, 117, -2).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent(this.labelTxt3).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(this.cidadeTxt, -2, 117, -2)).addGroup(jPanel2Layout.createSequentialGroup().addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addGap(41, 41, 41).addComponent(this.labelTxt5).addGap(18, 18, 18).addComponent(this.bairroTxt, -2, 191, -2)).addGroup(jPanel2Layout.createSequentialGroup().addGap(10, 10, 10).addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING).addComponent(this.idCepTxt, -2, 56, -2).addComponent(this.msgErro, -2, 455, -2)))).addGap(0, 0, 32767))))).addContainerGap()));
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING).addComponent(this.idCepTxt, -2, 16, -2).addGroup(jPanel2Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.labelTxt4).addComponent(this.cepTxt, -2, -1, -2).addComponent(this.labelTxt3).addComponent(this.cidadeTxt, -2, -1, -2))).addGap(2, 2, 2).addComponent(this.msgErro, -2, 14, -2).addPreferredGap(ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.tp_LogradouroTxt, -2, -1, -2).addComponent(this.logradouroTxt, -2, -1, -2).addComponent(this.labelTxt2).addComponent(this.numeroTxt, -2, -1, -2)).addGap(18, 18, 18).addGroup(jPanel2Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.labelTxt5).addComponent(this.bairroTxt, -2, -1, -2)).addGap(18, 18, 18).addComponent(this.jScrollPane1, -2, 139, -2).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addComponent(BotaoCadNovoEnd).addGap(0, 5, 32767)).addComponent(this.msgTxt, -1, -1, 32767)).addContainerGap()));
        this.labelTxt7.setFont(new Font("Tahoma", 1, 11));
        this.labelTxt7.setText("Observação");
        this.nomeTxt.setFont(new Font("Tahoma", 1, 11));
        this.nomeTxt.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                nomeTxtKeyTyped(evt);
            }
        });
        this.telefoneTxt.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                telefoneTxtFocusLost(evt);
            }
        });
        this.telefoneTxt.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                telefoneTxtKeyTyped(evt);
            }
        });
        this.obsTxt.setFont(new Font("Tahoma", 1, 11));
        this.obsTxt.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                obsTxtKeyTyped(evt);
            }
        });
        this.jButton1.setText("Cadastrar Cliente");
        this.jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addComponent(this.jPanel2, -1, -1, 32767).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING, false).addComponent(this.idClienteTxt, -2, 28, -2).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.labelTxt7).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.obsTxt)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.labelTxt).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.telefoneTxt, -2, 129, -2).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(this.labelTxt1).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.nomeTxt, -2, 446, -2))).addGap(0, 13, 32767))).addContainerGap()).addGroup(Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addContainerGap(-1, 32767).addComponent(this.jButton1, -2, 160, -2).addGap(25, 25, 25)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(this.idClienteTxt, -2, 14, -2).addPreferredGap(ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.labelTxt).addComponent(this.labelTxt1).addComponent(this.nomeTxt, -2, -1, -2).addComponent(this.telefoneTxt, -2, -1, -2)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.labelTxt7).addComponent(this.obsTxt, -2, -1, -2)).addGap(7, 7, 7).addComponent(this.jPanel2, -2, -1, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.jButton1).addContainerGap(-1, 32767)));
        this.systemError.setForeground(new Color(204, 0, 0));
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(this.jPanel1, -1, -1, 32767).addComponent(this.systemError, -1, -1, 32767)).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jPanel1, -1, -1, 32767).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.systemError, -2, 23, -2)));
        this.pack();
    }

    private void cepTxtFocusGained(FocusEvent evt) {
        this.cepTxt.setText("");
        this.idCepTxt.setText("");
        this.cepTxt.setCaretPosition(0);
    }

    private void cepTxtFocusLost(FocusEvent evt) {
        String lista = this.cepTxt.getText();
        String sql = "select * from endereco where cep = '" + lista + "'";
        System.out.println(sql);
        if ("     -   ".equals(lista)) {
            this.systemError.setText("Nenhum CEP digitado");
        } else {
            try {
                programagas.Conectar.pesquisar(sql);

                while(programagas.Conectar.rs.next()) {
                    this.bairroTxt.setText(programagas.Conectar.rs.getString("bairro"));
                    this.logradouroTxt.setText(programagas.Conectar.rs.getString("logradouro"));
                    this.tp_LogradouroTxt.setSelectedItem(programagas.Conectar.rs.getString("tp_logradouro"));
                    this.idCepTxt.setText(programagas.Conectar.rs.getString("id_cep"));
                    this.cidadeTxt.setText(programagas.Conectar.rs.getString("cidade"));
                }

                this.numeroTxt.requestFocus();
            } catch (SQLException var5) {
                programagas.ProgramaGas.salvarErro(var5.getMessage() + "  Local:  " + var5.getLocalizedMessage());
                this.systemError.setText(var5.toString());
            }
        }

    }

    private void BotaoCadNovoEndActionPerformed(ActionEvent evt) {
        String id = this.idCepTxt.getText();
        String cidade = this.cidadeTxt.getText();
        String logradouro = this.logradouroTxt.getText();
        String bairro = this.bairroTxt.getText();
        String cep = this.cepTxt.getText();
        String tp_logradouro = this.tp_LogradouroTxt.getSelectedItem().toString();
        String sql;
        if ("".equals(this.idCepTxt.getText())) {
            sql = "INSERT INTO endereco (cidade,logradouro,bairro,cep,tp_logradouro) VALUES('" + cidade + "','" + logradouro + "','" + bairro + "','" + cep + "','" + tp_logradouro + "')";

            try {
                programagas.Conectar.alterar(sql);
                this.msgTxt.setText("Cadastrado com sucesso");
            } catch (SQLException var11) {
                programagas.ProgramaGas.salvarErro(var11.getMessage() + "  Local:  " + var11.getLocalizedMessage());
            }
        } else {
            sql = "UPDATE ENDERECO SET CIDADE = '" + cidade + "' ,LOGRADOURO = '" + logradouro + "',BAIRRO = '" + bairro + "',CEP = '" + cep + "',TP_LOGRADOURO = '" + tp_logradouro + "'  where ID_CEP = " + id + "";

            try {
                programagas.Conectar.alterar(sql);
                this.msgTxt.setText("Alterado com sucesso");
            } catch (SQLException var10) {
                programagas.ProgramaGas.salvarErro(var10.getMessage() + "  Local:  " + var10.getLocalizedMessage());
                this.systemError.setText(var10.toString());
            }
        }

        BotaoCadNovoEnd.setVisible(false);
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        String numero1 = this.m.numero(this.telefoneTxt.getText());
        int tamNum1 = numero1.length();
        if (!this.verificarTelefone()) {
            this.msgTxt.setText("Numero de telefone errado. Ex: 11954112227");
        } else if ("".equals(this.idCepTxt.getText())) {
            this.msgTxt.setText("Nenhum endereço foi atribuido ao cliente.");
            this.msgErro.setText("Digite um cep valido. ou escolha um na tabela de pesquisa");
        } else if ("".equals(this.nomeTxt.getText())) {
            this.msgTxt.setText("Digite o nome do cliente.");
            this.msgErro.setText("Digite o nome do cliente.");
        } else if ("".equals(this.numeroTxt.getText())) {
            this.msgTxt.setText("Digite o numero da casa.");
            this.msgErro.setText("Digite o numero da casa.");
        } else {
            this.msgTxt.setText("");
            this.msgErro.setText("");
            String idCliente = this.idClienteTxt.getText();
            String telefone = this.m.numero(this.telefoneTxt.getText());
            String nome = this.nomeTxt.getText();
            String id_endereco = this.idCepTxt.getText();
            String numero = this.numeroTxt.getText();
            String observacao = this.obsTxt.getText();
            String sql;
            if ("".equals(this.idClienteTxt.getText())) {
                sql = "INSERT INTO clientes (TELEFONE,NOME,ID_ENDERECO,NUMERO,OBSERVACAO) VALUES('" + telefone + "','" + nome + "'," + id_endereco + ",'" + numero + "','" + observacao + "')";

                try {
                    programagas.Conectar.alterar(sql);
                    this.msgTxt.setText("Cliente cadastrado com sucesso");
                } catch (SQLException var13) {
                    programagas.ProgramaGas.salvarErro(var13.getMessage() + "  Local:  " + var13.getLocalizedMessage());
                    this.systemError.setText(var13.toString());
                }
            } else {
                sql = "UPDATE CLIENTES SET TELEFONE = '" + telefone + "' ,NOME = '" + nome + "',ID_ENDERECO = " + id_endereco + ",NUMERO = '" + numero + "',OBSERVACAO = '" + observacao + "'  where ID_CLIENTE = " + idCliente + "";

                try {
                    programagas.Conectar.alterar(sql);
                    this.msgTxt.setText("Dados do Cliente alterado com sucesso");
                } catch (SQLException var12) {
                    programagas.ProgramaGas.salvarErro(var12.getMessage() + "  Local:  " + var12.getLocalizedMessage());
                    this.systemError.setText(var12.toString());
                }
            }

            Integer novoIdCliente = null;

            if(!StringUtils.isBlank(idCliente)){
                novoIdCliente = Integer.valueOf(idCliente);
            }

            ClienteDto cliente = new ClienteDto();
            cliente.setIdCliente(novoIdCliente);
            cliente.setTelefone(telefone);
            cliente.setViaApi(true);
            cliente.setNome(this.nomeTxt.getText());
            cliente.setObservacao(observacao);

            List<ClienteEndereco> listaEndereco = Lists.newArrayList();

            Endereco endereco = new Endereco();
            endereco.setBairro(this.bairroTxt.getText());
            endereco.setCep(this.cepTxt.getText());
            endereco.setCidade(this.cidadeTxt.getText());
            endereco.setLogradouro(this.logradouroTxt.getText());


            ClienteEndereco end = new ClienteEndereco();
            end.setApelido("Principal");
            end.setNumero(numero);
            end.setEndereco(endereco);

            listaEndereco.add(end);
            cliente.setClienteEnderecos(listaEndereco);

            (new TelaPedidos(cliente)).setVisible(true);
            this.dispose();
        }

    }

    private void logradouroTxtKeyPressed(KeyEvent evt) {
        this.msgTxt.setText("");
        String lista = this.logradouroTxt.getText();
        String sql = "select * from endereco where UPPER(logradouro) LIKE '%" + lista.toUpperCase() + "%'";
        DefaultTableModel model = (DefaultTableModel)this.jTable1.getModel();
        model.setNumRows(0);
        if (!"".equals(lista)) {
            try {
                programagas.Conectar.pesquisar(sql);

                while(programagas.Conectar.rs.next()) {
                    String[] conteudo = new String[]{programagas.Conectar.rs.getString("ID_CEP"), programagas.Conectar.rs.getString("CEP"), programagas.Conectar.rs.getString("LOGRADOURO"), programagas.Conectar.rs.getString("BAIRRO")};
                    model.addRow(conteudo);
                }
            } catch (SQLException var6) {
                programagas.ProgramaGas.salvarErro(var6.getMessage() + "  Local:  " + var6.getLocalizedMessage());
                this.systemError.setText(var6.toString());
            }
        }

    }

    private void jTable1MouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            String id = this.jTable1.getModel().getValueAt(this.jTable1.getSelectedRow(), 0).toString();
            String sql = "select * from endereco where ID_CEP = " + id + "";

            try {
                programagas.Conectar.pesquisar(sql);

                while(programagas.Conectar.rs.next()) {
                    this.bairroTxt.setText(programagas.Conectar.rs.getString("bairro"));
                    this.logradouroTxt.setText(programagas.Conectar.rs.getString("logradouro"));
                    this.tp_LogradouroTxt.setSelectedItem(programagas.Conectar.rs.getString("tp_logradouro"));
                    this.idCepTxt.setText(programagas.Conectar.rs.getString("id_cep"));
                    this.cidadeTxt.setText(programagas.Conectar.rs.getString("cidade"));
                    this.cepTxt.setText(programagas.Conectar.rs.getString("CEP"));
                }
            } catch (SQLException var5) {
                programagas.ProgramaGas.salvarErro(var5.getMessage() + "  Local:  " + var5.getLocalizedMessage());
                this.systemError.setText(var5.toString());
            }

            this.numeroTxt.requestFocus();
            BotaoCadNovoEnd.setVisible(false);
        }

    }

    private void telefoneTxtFocusLost(FocusEvent evt) {
        String numero = this.m.numero(this.telefoneTxt.getText());
        this.preencher(this.arrumarTelefone(numero));
    }

    private void bairroTxtKeyPressed(KeyEvent evt) {
        this.msgTxt.setText("");
        String lista = this.bairroTxt.getText();
        String sql = "select * from endereco where UPPER(BAIRRO) LIKE '%" + lista.toUpperCase() + "%'";
        DefaultTableModel model = (DefaultTableModel)this.jTable1.getModel();
        model.setNumRows(0);
        if (!"".equals(lista)) {
            try {
                programagas.Conectar.pesquisar(sql);

                while(programagas.Conectar.rs.next()) {
                    String[] conteudo = new String[]{programagas.Conectar.rs.getString("ID_CEP"), programagas.Conectar.rs.getString("CEP"), programagas.Conectar.rs.getString("LOGRADOURO"), Conectar.rs.getString("BAIRRO")};
                    model.addRow(conteudo);
                }
            } catch (SQLException var6) {
                ProgramaGas.salvarErro(var6.getMessage() + "  Local:  " + var6.getLocalizedMessage());
                this.systemError.setText(var6.toString());
            }
        }

    }

    private void telefoneTxtKeyTyped(KeyEvent evt) {
        String caracteres = "0123456789";
        if (!caracteres.contains(evt.getKeyChar() + "") || this.telefoneTxt.getText().length() > 10) {
            evt.consume();
        }

    }

    private void logradouroTxtFocusGained(FocusEvent evt) {
        this.idCepTxt.setText("");
        this.cepTxt.setText("");
        BotaoCadNovoEnd.setVisible(true);
    }

    private void idCepTxtInputMethodTextChanged(InputMethodEvent evt) {
    }

    private void nomeTxtKeyTyped(KeyEvent evt) {
        String caracteres = "0123456789abcdefghijklmnopqrstuvxzwyçABCDEFGHIJKLMNOPQRSTUVXZWYÇ ";
        if (!caracteres.contains(evt.getKeyChar() + "") || this.nomeTxt.getText().length() > 74) {
            evt.consume();
        }

    }

    private void obsTxtKeyTyped(KeyEvent evt) {
        String caracteres = "0123456789abcdefghijklmnopqrstuvxzwyçABCDEFGHIJKLMNOPQRSTUVXZWYÇ ";
        if (!caracteres.contains(evt.getKeyChar() + "") || this.obsTxt.getText().length() > 74) {
            evt.consume();
        }

    }

    private void numeroTxtKeyTyped(KeyEvent evt) {
        String caracteres = "0123456789abcdefghijklmnopqrstuvxzwyçABCDEFGHIJKLMNOPQRSTUVXZWYÇ ";
        if (!caracteres.contains(evt.getKeyChar() + "") || this.numeroTxt.getText().length() > 74) {
            evt.consume();
        }

    }

    private void cidadeTxtKeyTyped(KeyEvent evt) {
        String caracteres = "0123456789abcdefghijklmnopqrstuvxzwyçABCDEFGHIJKLMNOPQRSTUVXZWYÇ ";
        if (!caracteres.contains(evt.getKeyChar() + "") || this.cidadeTxt.getText().length() > 49) {
            evt.consume();
        }

    }

    private void logradouroTxtKeyTyped(KeyEvent evt) {
        String caracteres = "0123456789abcdefghijklmnopqrstuvxzwyçABCDEFGHIJKLMNOPQRSTUVXZWYÇ ";
        if (!caracteres.contains(evt.getKeyChar() + "") || this.logradouroTxt.getText().length() > 69) {
            evt.consume();
        }

    }

    private void bairroTxtKeyTyped(KeyEvent evt) {
        String caracteres = "0123456789abcdefghijklmnopqrstuvxzwyçABCDEFGHIJKLMNOPQRSTUVXZWYÇ ";
        if (!caracteres.contains(evt.getKeyChar() + "") || this.bairroTxt.getText().length() > 70) {
            evt.consume();
        }

    }
}

