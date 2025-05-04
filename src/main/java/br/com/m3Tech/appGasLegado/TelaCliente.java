package br.com.m3Tech.appGasLegado;


import br.com.m3Tech.appGasLegado.dto.ClienteDto;
import br.com.m3Tech.appGasLegado.dto.ClienteEndereco;
import br.com.m3Tech.appGasLegado.service.ClienteService;

import java.awt.AWTKeyStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.HashSet;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import static br.com.m3Tech.appGasLegado.ProgramaGas.salvarErro;

public class TelaCliente extends JFrame {
    private JButton botaoAlterar;
    private JButton botaoOK;
    private JTextField endTxt;
    private JLabel idTxt;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JPanel jPanel1;
    private JScrollPane jScrollPane1;
    private JTable jTable1;
    private JLabel msgTabela;
    private JTextField nomeTxt;
    private JTextField obsTxt;
    private JLabel systemError;
    private JTextField telefoneTxt;

    public TelaCliente(String telefone) {
        this.initComponents();
        HashSet conj = new HashSet(this.getFocusTraversalKeys(0));
        conj.add(AWTKeyStroke.getAWTKeyStroke(10, 0));
        this.setFocusTraversalKeys(0, conj);
        this.preencher(telefone);
    }

    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.idTxt = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.msgTabela = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.jTable1 = new JTable();
        this.nomeTxt = new JTextField();
        this.endTxt = new JTextField();
        this.obsTxt = new JTextField();
        this.botaoAlterar = new JButton();
        this.jLabel5 = new JLabel();
        this.telefoneTxt = new JTextField();
        this.botaoOK = new JButton();
        this.systemError = new JLabel();
        this.setDefaultCloseOperation(2);
        this.setTitle("Clientes");
        this.jPanel1.setBackground(new Color(255, 255, 153));
        this.idTxt.setFont(new Font("Tahoma", 1, 14));
        this.idTxt.setText("ID");
        this.jLabel2.setFont(new Font("Tahoma", 1, 14));
        this.jLabel2.setText("Nome");
        this.jLabel3.setFont(new Font("Tahoma", 1, 14));
        this.jLabel3.setText("Endereço");
        this.jLabel4.setFont(new Font("Tahoma", 1, 14));
        this.jLabel4.setText("Observação");
        this.msgTabela.setFont(new Font("Tahoma", 1, 12));
        this.msgTabela.setText("Pedidos realizados");
        this.jTable1.setModel(new DefaultTableModel(new Object[0][], new String[]{"", "", "", "", ""}) {
            Class[] types = new Class[]{String.class, String.class, String.class, String.class, String.class};
            boolean[] canEdit = new boolean[]{false, false, false, false, false};

            public Class getColumnClass(int columnIndex) {
                return this.types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.jTable1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.jTable1);
        if (this.jTable1.getColumnModel().getColumnCount() > 0) {
            this.jTable1.getColumnModel().getColumn(0).setMaxWidth(100);
        }

        this.nomeTxt.setFont(new Font("Tahoma", 1, 14));
        this.nomeTxt.setToolTipText("Digite um nome para procurar por um cliente");
        this.nomeTxt.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                nomeTxtFocusGained(evt);
            }
        });
        this.nomeTxt.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                nomeTxtKeyPressed(evt);
            }
        });
        this.endTxt.setFont(new Font("Tahoma", 1, 14));
        this.endTxt.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                endTxtFocusGained(evt);
            }
        });
        this.endTxt.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                endTxtKeyPressed(evt);
            }
        });
        this.obsTxt.setFont(new Font("Tahoma", 1, 14));
        this.botaoAlterar.setFont(new Font("Tahoma", 1, 12));
        this.botaoAlterar.setText("Alterar Dados ");
        this.botaoAlterar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                botaoAlterarActionPerformed(evt);
            }
        });
        this.jLabel5.setFont(new Font("Tahoma", 1, 14));
        this.jLabel5.setText("Telefone");
        this.telefoneTxt.setFont(new Font("Tahoma", 1, 14));
        this.telefoneTxt.setToolTipText("EX: 11954112227");
        this.telefoneTxt.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                telefoneTxtFocusGained(evt);
            }

            public void focusLost(FocusEvent evt) {
                telefoneTxtFocusLost(evt);
            }
        });
        this.telefoneTxt.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                telefoneTxtKeyPressed(evt);
            }
        });
        this.botaoOK.setFont(new Font("Tahoma", 1, 12));
        this.botaoOK.setText("OK");
        this.botaoOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                botaoOKActionPerformed(evt);
            }
        });
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addComponent(this.jScrollPane1).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addComponent(this.msgTabela).addComponent(this.idTxt, -2, 84, -2).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel2, -2, 84, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.nomeTxt, -2, 236, -2))).addGap(0, 0, 32767)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.botaoAlterar).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent(this.botaoOK, -2, 100, -2))).addContainerGap()).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel4, -2, 84, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.obsTxt, -2, 658, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel3, -2, 84, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.endTxt, -2, 658, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel5, -2, 84, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.telefoneTxt, -2, 236, -2))).addGap(0, 24, 32767)))));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(this.idTxt, -2, 22, -2).addPreferredGap(ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.jLabel2, -2, 22, -2).addComponent(this.nomeTxt, -2, -1, -2)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.jLabel5, -2, 22, -2).addComponent(this.telefoneTxt, -2, -1, -2)).addGap(9, 9, 9).addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.jLabel3, -2, 22, -2).addComponent(this.endTxt, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.jLabel4, -2, 22, -2).addComponent(this.obsTxt, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED, 22, 32767).addComponent(this.msgTabela).addGap(18, 18, 18).addComponent(this.jScrollPane1, -2, 208, -2).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.botaoAlterar, -2, 38, -2).addComponent(this.botaoOK, -2, 38, -2)).addContainerGap()));
        this.systemError.setForeground(new Color(204, 0, 0));
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(this.jPanel1, -1, -1, 32767).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.systemError, -2, 751, -2).addContainerGap(-1, 32767)));
        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jPanel1, -2, -1, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.systemError, -2, 19, -2).addGap(0, 11, 32767)));
        this.pack();
    }

    private void nomeTxtKeyPressed(KeyEvent evt) {
//        String nome = this.nomeTxt.getText();
//        String sql = "select * from Clientes  INNER JOIN ENDERECO ON ID_CEP = ID_ENDERECO where UPPER(NOME) LIKE '%" + nome.toUpperCase() + "%'";
//        DefaultTableModel model = (DefaultTableModel)this.jTable1.getModel();
//        model.setNumRows(0);
//        if (!"".equals(nome)) {
//            try {
//                Conectar.pesquisar(sql);
//
//                while(Conectar.rs.next()) {
//                    String[] conteudo = new String[]{Conectar.rs.getString("ID_CLIENTE"), Conectar.rs.getString("TELEFONE"), Conectar.rs.getString("NOME"), Conectar.rs.getString("LOGradouro"), Conectar.rs.getString("NUMERO")};
//                    model.addRow(conteudo);
//                }
//            } catch (SQLException var6) {
//                salvarErro(var6.getMessage() + "  Local:  " + var6.getLocalizedMessage());
//                this.systemError.setText(var6.toString());
//            }
//        }
//
//        this.msgTabela.setText("Resultado da pesquisa pelo nome do cliente.");
    }

    private void endTxtKeyPressed(KeyEvent evt) {
        String nome = this.endTxt.getText();
        String sql = "select * from ENDERECO   where UPPER(LOGRADOURO) LIKE '%" + nome.toUpperCase() + "%'";
        DefaultTableModel model = (DefaultTableModel)this.jTable1.getModel();
        model.setNumRows(0);
        if (!"".equals(nome)) {
            try {
                Conectar.pesquisar(sql);

                while(Conectar.rs.next()) {
                    String[] conteudo = new String[]{Conectar.rs.getString("ID_CEP"), Conectar.rs.getString("CEP"), Conectar.rs.getString("LOGRADOURO"), Conectar.rs.getString("BAIRRO"), Conectar.rs.getString("REFERENCIA")};
                    model.addRow(conteudo);
                }
                Conectar.rs.close();
            } catch (SQLException var6) {
                salvarErro(var6.getMessage() + "  Local:  " + var6.getLocalizedMessage());
                this.systemError.setText(var6.toString());
            }
        }

        this.msgTabela.setText("Click duas vezes na rua escolhida, para exibir os clientes que moram nessa rua.");
    }

    private void jTable1MouseClicked(MouseEvent evt) {
        DefaultTableModel model = (DefaultTableModel)this.jTable1.getModel();
        if (evt.getClickCount() == 2) {
            String consulta;
            if ("Click duas vezes na rua escolhida, para exibir os clientes que moram nessa rua.".equals(this.msgTabela.getText())) {
                consulta = this.jTable1.getModel().getValueAt(this.jTable1.getSelectedRow(), 0).toString();
                String sql = "select * from Clientes  INNER JOIN ENDERECO ON ID_CEP = ID_ENDERECO where ID_ENDERECO = " + consulta + "";
                System.out.println(sql);

                try {
                    Conectar.pesquisar(sql);
                    model.setNumRows(0);

                    while(Conectar.rs.next()) {
                        String[] conteudo = new String[]{Conectar.rs.getString("ID_CLIENTE"), Conectar.rs.getString("TELEFONE"), Conectar.rs.getString("NOME"), Conectar.rs.getString("LOGradouro"), Conectar.rs.getString("NUMERO")};
                        model.addRow(conteudo);
                        this.msgTabela.setText("Resultado da pesquisa pelo nome do cliente.");
                    }
                    Conectar.rs.close();
                } catch (SQLException var6) {
                    salvarErro(var6.getMessage() + "  Local:  " + var6.getLocalizedMessage());
                    this.systemError.setText(var6.toString());
                }
            } else if ("Resultado da pesquisa pelo nome do cliente.".equals(this.msgTabela.getText())) {
                consulta = this.jTable1.getModel().getValueAt(this.jTable1.getSelectedRow(), 1).toString();
                this.preencher(consulta);
            }
        }

    }

    private void endTxtFocusGained(FocusEvent evt) {
        this.endTxt.setCaretPosition(0);
    }

    private void telefoneTxtKeyPressed(KeyEvent evt) {
    }

    private void nomeTxtFocusGained(FocusEvent evt) {
    }

    private void telefoneTxtFocusGained(FocusEvent evt) {
        this.telefoneTxt.setCaretPosition(0);
    }

    private void botaoOKActionPerformed(ActionEvent evt) {
        String lista = this.obsTxt.getText();
        String id_cliente = this.idTxt.getText();
        String sql = "update clientes set observacao  = '" + lista + "' where id_cliente= " + id_cliente + "";

        try {
            Conectar.alterar(sql);
        } catch (SQLException var6) {
            salvarErro(var6.getMessage() + "  Local:  " + var6.getLocalizedMessage());
        }

        this.dispose();
    }

    private void botaoAlterarActionPerformed(ActionEvent evt) {
        Metodos metodos = new Metodos();
        String lista = metodos.numero(this.telefoneTxt.getText());
        CadastrarNovoCliente cnc = new CadastrarNovoCliente();
        cnc.setVisible(true);
        cnc.preencher(lista);
    }

    private void telefoneTxtFocusLost(FocusEvent evt) {
        String numero = this.telefoneTxt.getText();
        if (numero != null & !"".equals(numero)) {
            this.preencher(numero);
        } else {
            this.msgTabela.setText("Campo Telefone vazio.Digite um numero valido. Ex: 11954112227");
        }

    }

    private void preencher(String telefone) {

        DefaultTableModel model = (DefaultTableModel)this.jTable1.getModel();
        model.setNumRows(0);
        String id_cliente = null;
        String nome = null;
        String id_endereco = null;
        String numCasa = null;
        String obs = null;
        String logradouro = null;
        String bairro = null;
        String tp_logradouro = null;
        String referencia = null;

        String sql = "SELECT * FROM CLIENTES where telefone =  '" + telefone + "'";
        try {
            Conectar.pesquisar(sql);

            while(Conectar.rs.next()) {
                id_cliente = Conectar.rs.getString("ID_CLIENTE");
                nome = Conectar.rs.getString("NOME");
                id_endereco = Conectar.rs.getString("ID_ENDERECO");
                numCasa = Conectar.rs.getString("NUMERO");
                obs = Conectar.rs.getString("OBSERVACAO");
            }
            Conectar.rs.close();
        } catch (SQLException var19) {
            salvarErro(var19.getMessage() + "  Local:  " + var19.getLocalizedMessage());
            this.systemError.setText(var19.toString());
        }

        String sql1 = "SELECT * FROM ENDERECO where ID_CEP =  " + id_endereco + "";

        try {
            Conectar.pesquisar(sql1);

            while(Conectar.rs.next()) {
                logradouro = Conectar.rs.getString("LOGRADOURO");
                bairro = Conectar.rs.getString("BAIRRO");
                tp_logradouro = Conectar.rs.getString("TP_LOGRADOURO");
                referencia = Conectar.rs.getString("REFERENCIA");
            }
            Conectar.rs.close();
        } catch (SQLException var18) {
            salvarErro(var18.getMessage() + "  Local:  " + var18.getLocalizedMessage());
            this.systemError.setText(var18.toString());
        }

        String sql2 = "SELECT * FROM PEDIDOS where ID_CLIENTEp =  " + id_cliente + "";

        try {
            Conectar.pesquisar(sql2);

            for(int i = 1; Conectar.rs.next(); ++i) {
                String[] conteudo = new String[]{Integer.toString(i), Conectar.rs.getString("Pedido"), Conectar.rs.getString("ENTREGADOR"), Conectar.rs.getString("FORMADEPAGAMENTO"), Conectar.rs.getString("STATUS"), Conectar.rs.getString("DIA")};
                model.addRow(conteudo);
            }
            Conectar.rs.close();
        } catch (SQLException var17) {
            salvarErro(var17.getMessage() + "  Local:  " + var17.getLocalizedMessage());
            this.systemError.setText(var17.toString());
        }
        ClienteDto cliente = new Service().getCliente(telefone);

        if(cliente != null && cliente.getClienteEnderecos() != null && !cliente.getClienteEnderecos().isEmpty()){

            ClienteEndereco clienteEndereco = cliente.getClienteEnderecos().get(0);

            nome = cliente.getNome();
            numCasa = clienteEndereco.getNumero();
            obs = cliente.getObservacao();
            logradouro = clienteEndereco.getEndereco().getLogradouro();
            bairro = clienteEndereco.getEndereco().getBairro();
            tp_logradouro = "Rua";
        }

        this.telefoneTxt.setText(telefone);
        this.idTxt.setText(id_cliente);
        this.nomeTxt.setText(nome);
        this.endTxt.setText(tp_logradouro + ": " + logradouro + ", " + numCasa + " - " + bairro + "     Prox. " + referencia);
        this.obsTxt.setText(obs);
        this.msgTabela.setText("Pedidos realizados por esse cliente.");
    }
}

