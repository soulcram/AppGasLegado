package br.com.m3Tech.appGasLegado;

import programagas.CadastrarNovoCliente;
import programagas.Conectar;
import programagas.ProgramaGas;
import programagas.Vendas;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.text.Normalizer.Form;
import java.util.Date;
import java.util.regex.Pattern;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.DocFlavor.INPUT_STREAM;
import javax.print.DocFlavor.SERVICE_FORMATTED;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

public class TelaPedidos extends JFrame {
    String id_cliente;
    String id_endereco;
    String nome;
    String obs;
    String numCasa;
    String cidade;
    String logradouro;
    String bairro;
    String cep;
    String tp_logradouro;
    String referencia;
    String obsPedido;
    String telefone;
    String id_pedido;
    PrintService[] ps;
    private JButton alterarEndereco;
    private JButton botaoOk;
    private JComboBox<String> boxImpressoras;
    private JLabel endTxt;
    private JComboBox<String> formaPagamento;
    private JLabel idTxt;
    private JButton jButton1;
    private JButton jButton2;
    private JLabel jLabel1;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JTable jTable1;
    private JTable jTable2;
    private JLabel msgErro;
    private JLabel nomeTxt;
    private JLabel obsTxt;
    private JTextField pedidoObs;
    private JComboBox<String> produtosCol;
    private JComboBox<String> quantCol;
    private JLabel systemError;
    private JLabel telefoneTxt;
    private JTextField txtTotal;

    public TelaPedidos(String numero) {
        this.initComponents();
        this.telefone = numero;
        String loadProdutos = "Select * from PRODUTOS";

        try {
            programagas.Conectar.pesquisar(loadProdutos);

            while(programagas.Conectar.rs.next()) {
                this.produtosCol.addItem(programagas.Conectar.rs.getString("NOME"));
            }
        } catch (SQLException var10) {
            programagas.ProgramaGas.salvarErro(var10.getMessage() + "  Local:  " + var10.getLocalizedMessage());
            this.systemError.setText(var10.toString());
        }

        this.jTable2.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(this.produtosCol));
        this.jTable2.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(this.quantCol));
        ActionEvent evt = null;
        this.jButton1ActionPerformed((ActionEvent)evt);
        this.preencher(numero);

        try {
            this.boxImpressoras.removeAllItems();
            DocFlavor df = SERVICE_FORMATTED.PRINTABLE;
            this.ps = PrintServiceLookup.lookupPrintServices(df, (AttributeSet)null);
            PrintService[] var5 = this.ps;
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                PrintService p = var5[var7];
                this.boxImpressoras.addItem(p.getName());
            }
        } catch (Exception var9) {
            programagas.ProgramaGas.salvarErro(var9.getMessage() + "  Local:  " + var9.getLocalizedMessage());
        }

        this.boxImpressoras.setSelectedItem(programagas.ProgramaGas.Impressora);
    }

    private String gerarNotaTermica() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat dateFormatHora = new SimpleDateFormat("HH:mm");
        String hoje = dateFormat.format(date);
        String hora = dateFormatHora.format(date);
        String nota = "";
        nota = nota + "\u001bE       CONSIGAZ\u001bF" + System.getProperty("line.separator");
        nota = nota + "--------------------------------" + System.getProperty("line.separator");
        nota = nota + hoje + "    " + hora + System.getProperty("line.separator");
        nota = nota + "                                " + System.getProperty("line.separator");
        nota = nota + this.telefoneTxt.getText() + System.getProperty("line.separator");
        nota = nota + "                                " + System.getProperty("line.separator");
        nota = nota + this.nome + System.getProperty("line.separator");
        nota = nota + "                                " + System.getProperty("line.separator");
        nota = nota + this.tp_logradouro + ": " + this.logradouro + ", " + this.numCasa + System.getProperty("line.separator");
        nota = nota + "                                " + System.getProperty("line.separator");
        nota = nota + this.bairro + System.getProperty("line.separator");
        nota = nota + "                                " + System.getProperty("line.separator");
        nota = nota + this.obs + System.getProperty("line.separator");
        nota = nota + "                                " + System.getProperty("line.separator");
        nota = nota + this.montarPedido() + System.getProperty("line.separator");
        nota = nota + "                                " + System.getProperty("line.separator");
        nota = nota + "Total: " + this.txtTotal.getText() + System.getProperty("line.separator");
        nota = nota + "                                " + System.getProperty("line.separator");
        nota = nota + this.formaPagamento.getSelectedItem().toString() + System.getProperty("line.separator") + "--------------------------------" + System.getProperty("line.separator");
        return nota;
    }

    private void preencher(String numero) {
        String sql = "SELECT * FROM CLIENTES where telefone =  '" + numero + "'";

        try {
            programagas.Conectar.pesquisar(sql);

            while(programagas.Conectar.rs.next()) {
                this.id_cliente = programagas.Conectar.rs.getString("ID_CLIENTE");
                this.nome = programagas.Conectar.rs.getString("NOME");
                this.id_endereco = programagas.Conectar.rs.getString("ID_ENDERECO");
                this.numCasa = programagas.Conectar.rs.getString("NUMERO");
                this.obs = programagas.Conectar.rs.getString("OBSERVACAO");
            }
        } catch (SQLException var9) {
            programagas.ProgramaGas.salvarErro(var9.getMessage() + "  Local:  " + var9.getLocalizedMessage());
            this.systemError.setText(var9.toString());
        }

        String sql1 = "SELECT * FROM ENDERECO where ID_CEP =  " + this.id_endereco + "";

        try {
            programagas.Conectar.pesquisar(sql1);

            while(programagas.Conectar.rs.next()) {
                this.cidade = programagas.Conectar.rs.getString("CIDADE");
                this.logradouro = programagas.Conectar.rs.getString("LOGRADOURO");
                this.bairro = programagas.Conectar.rs.getString("BAIRRO");
                this.cep = programagas.Conectar.rs.getString("CEP");
                this.tp_logradouro = programagas.Conectar.rs.getString("TP_LOGRADOURO");
                this.referencia = programagas.Conectar.rs.getString("REFERENCIA");
            }
        } catch (SQLException var8) {
            programagas.ProgramaGas.salvarErro(var8.getMessage() + "  Local:  " + var8.getLocalizedMessage());
            this.systemError.setText(var8.toString());
        }

        String sql2 = "SELECT * FROM PEDIDOS where ID_CLIENTEp =  " + this.id_cliente + "";

        try {
            programagas.Conectar.pesquisar(sql2);
            DefaultTableModel model = (DefaultTableModel)this.jTable1.getModel();

            while(programagas.Conectar.rs.next()) {
                String[] conteudo = new String[]{programagas.Conectar.rs.getString("PEDIDO"), programagas.Conectar.rs.getString("formadepagamento"), programagas.Conectar.rs.getString("ENTREGADOR"), programagas.Conectar.rs.getString("STATUS"), programagas.Conectar.rs.getString("DIA")};
                model.addRow(conteudo);
            }
        } catch (SQLException var7) {
            programagas.ProgramaGas.salvarErro(var7.getMessage() + "  Local:  " + var7.getLocalizedMessage());
            this.systemError.setText(var7.toString());
        }

        this.telefoneTxt.setText(numero);
        this.idTxt.setText(this.id_cliente);
        this.nomeTxt.setText(this.nome);
        this.endTxt.setText(this.tp_logradouro + ": " + this.logradouro + ", " + this.numCasa + " - " + this.bairro);
        this.obsTxt.setText(this.obs);
        this.pedidoObs.setText(this.obs);
    }

    private void initComponents() {
        this.produtosCol = new JComboBox();
        this.quantCol = new JComboBox();
        this.jPanel1 = new JPanel();
        this.idTxt = new JLabel();
        this.nomeTxt = new JLabel();
        this.endTxt = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.jTable1 = new JTable();
        this.obsTxt = new JLabel();
        this.jPanel2 = new JPanel();
        this.jLabel1 = new JLabel();
        this.formaPagamento = new JComboBox();
        this.jScrollPane2 = new JScrollPane();
        this.jTable2 = new JTable();
        this.jButton1 = new JButton();
        this.txtTotal = new JTextField();
        this.jButton2 = new JButton();
        this.botaoOk = new JButton();
        this.pedidoObs = new JTextField();
        this.alterarEndereco = new JButton();
        this.msgErro = new JLabel();
        this.telefoneTxt = new JLabel();
        this.systemError = new JLabel();
        this.boxImpressoras = new JComboBox();
        this.quantCol.setModel(new DefaultComboBoxModel(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"}));
        this.setDefaultCloseOperation(2);
        this.setTitle("Pedidos");
        this.jPanel1.setBackground(new Color(255, 255, 255));
        this.jPanel1.setBorder(BorderFactory.createTitledBorder((Border)null, "Pedido", 2, 1, new Font("Tahoma", 0, 18)));
        this.idTxt.setFont(new Font("Tahoma", 1, 14));
        this.idTxt.setText("ID");
        this.nomeTxt.setFont(new Font("Tahoma", 1, 14));
        this.nomeTxt.setText("Nome");
        this.endTxt.setFont(new Font("Tahoma", 1, 14));
        this.endTxt.setText("Endereço");
        this.jScrollPane1.setBorder(BorderFactory.createTitledBorder("Ultimos Pedidos"));
        this.jTable1.setModel(new DefaultTableModel(new Object[0][], new String[]{"Pedido", "Forma de Pagamento", "Entregador", "Status", "Data"}) {
            Class[] types = new Class[]{String.class, String.class, String.class, String.class, String.class};
            boolean[] canEdit = new boolean[]{false, false, false, false, false};

            public Class getColumnClass(int columnIndex) {
                return this.types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.jTable1.setAutoResizeMode(0);
        this.jScrollPane1.setViewportView(this.jTable1);
        if (this.jTable1.getColumnModel().getColumnCount() > 0) {
            this.jTable1.getColumnModel().getColumn(0).setPreferredWidth(300);
            this.jTable1.getColumnModel().getColumn(1).setPreferredWidth(200);
            this.jTable1.getColumnModel().getColumn(2).setPreferredWidth(150);
            this.jTable1.getColumnModel().getColumn(3).setPreferredWidth(150);
            this.jTable1.getColumnModel().getColumn(4).setPreferredWidth(125);
            this.jTable1.getColumnModel().getColumn(4).setMaxWidth(150);
        }

        this.obsTxt.setFont(new Font("Tahoma", 1, 14));
        this.obsTxt.setText("Observação");
        this.jPanel2.setBorder(BorderFactory.createTitledBorder("Pedido de Hoje"));
        this.jLabel1.setFont(new Font("Tahoma", 1, 12));
        this.jLabel1.setHorizontalAlignment(0);
        this.jLabel1.setText("Forma de Pagamento");
        this.formaPagamento.setEditable(true);
        this.formaPagamento.setFont(new Font("Tahoma", 1, 12));
        this.formaPagamento.setModel(new DefaultComboBoxModel(new String[]{"Troco não", "Visa", "Mastercard", "Troco 50,00", "Troco 60,00", "Troco100,00", "Troco 70,00", "Troco 10,00", "Troco 20,00", "Elo", "Mais", "Hipercard", "Outros", "Troco 9,00", "Troco 15,00"}));
        this.jTable2.setModel(new DefaultTableModel(new Object[0][], new String[]{"Produto", "Quantidade", "Valor Unidade"}) {
            Class[] types = new Class[]{String.class, String.class, String.class};

            public Class getColumnClass(int columnIndex) {
                return this.types[columnIndex];
            }
        });
        this.jScrollPane2.setViewportView(this.jTable2);
        this.jButton1.setBackground(new Color(0, 102, 255));
        this.jButton1.setText("+");
        this.jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        this.jButton2.setText("Total");
        this.jButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               jButton2ActionPerformed(evt);
            }
        });
        GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
        this.jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane2, -2, 293, -2).addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED, 101, 32767).addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING, false).addComponent(this.txtTotal).addComponent(this.jButton2, -1, 91, 32767)).addGap(35, 35, 35).addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING, false).addComponent(this.formaPagamento, 0, -1, 32767).addComponent(this.jLabel1, -1, 173, 32767)).addContainerGap()).addGroup(jPanel2Layout.createSequentialGroup().addGap(11, 11, 11).addComponent(this.jButton1).addContainerGap(-1, 32767)))));
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addGroup(jPanel2Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.jLabel1, -2, 23, -2).addComponent(this.jButton2)).addPreferredGap(ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.formaPagamento, -2, -1, -2).addComponent(this.txtTotal, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent(this.jButton1)).addGroup(jPanel2Layout.createSequentialGroup().addGap(0, 0, 32767).addComponent(this.jScrollPane2, -2, 97, -2))).addContainerGap()));
        this.botaoOk.setBackground(new Color(0, 102, 255));
        this.botaoOk.setFont(new Font("Tahoma", 1, 18));
        this.botaoOk.setText("OK");
        this.botaoOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                botaoOkActionPerformed(evt);
            }
        });
        this.pedidoObs.setBorder(BorderFactory.createTitledBorder("Observações para o pedido"));
        this.alterarEndereco.setBackground(new Color(0, 102, 255));
        this.alterarEndereco.setFont(new Font("Tahoma", 1, 11));
        this.alterarEndereco.setText("Alterar endereço");
        this.alterarEndereco.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                alterarEnderecoActionPerformed(evt);
            }
        });
        this.msgErro.setFont(new Font("Tahoma", 1, 12));
        this.msgErro.setForeground(new Color(204, 0, 0));
        this.telefoneTxt.setFont(new Font("Tahoma", 1, 14));
        this.telefoneTxt.setText("Telefone");
        this.systemError.setForeground(new Color(204, 0, 0));
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addComponent(this.obsTxt, -1, -1, 32767).addComponent(this.nomeTxt, -1, -1, 32767).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.endTxt, -1, -1, 32767).addGap(60, 60, 60)).addGroup(Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING).addComponent(this.pedidoObs, Alignment.LEADING).addComponent(this.jScrollPane1).addGroup(Alignment.LEADING, jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING, false).addComponent(this.jPanel2, -1, -1, 32767).addComponent(this.msgErro, -1, -1, 32767)).addGap(18, 18, 18).addComponent(this.botaoOk, -1, 217, 32767))).addGap(4, 4, 4)).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addComponent(this.telefoneTxt, -2, 897, -2).addComponent(this.systemError, -2, 952, -2)).addGap(0, 0, 32767)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.idTxt, -2, 162, -2).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent(this.boxImpressoras, -2, 187, -2)).addGroup(Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addGap(0, 0, 32767).addComponent(this.alterarEndereco))).addContainerGap()));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.idTxt).addComponent(this.boxImpressoras, -2, -1, -2)).addGap(13, 13, 13).addComponent(this.telefoneTxt).addGap(18, 18, 18).addComponent(this.nomeTxt).addGap(18, 18, 18).addComponent(this.endTxt).addGap(18, 18, 18).addComponent(this.obsTxt).addGap(35, 35, 35).addComponent(this.alterarEndereco).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(this.jScrollPane1, -2, 130, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.msgErro, -2, 24, -2).addGap(21, 21, 21).addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING, false).addComponent(this.botaoOk, -1, -1, 32767).addComponent(this.jPanel2, -1, -1, 32767)).addGap(18, 18, 18).addComponent(this.pedidoObs, -2, -1, -2).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent(this.systemError, -2, 25, -2)));
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(this.jPanel1, Alignment.TRAILING, -1, -1, 32767));
        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(this.jPanel1, Alignment.TRAILING, -1, -1, 32767));
        this.pack();
    }

    private void alterarEnderecoActionPerformed(ActionEvent evt) {
        CadastrarNovoCliente CNC = new CadastrarNovoCliente();
        CNC.setVisible(true);
        CNC.preencher(this.telefoneTxt.getText());
        this.dispose();
    }

    private void botaoOkActionPerformed(ActionEvent evt) {
        if ("" == this.jTable2.getValueAt(0, 0)) {
            this.msgErro.setText("Marque um pedido.");
        } else {
            String pedido = this.montarPedido();
            String end = this.tp_logradouro + ": " + this.logradouro + ", " + this.numCasa + " - " + this.bairro + "     Prox. " + this.referencia;
            String fDp = this.formaPagamento.getSelectedItem().toString();
            this.obsPedido = this.pedidoObs.getText();
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String hoje = dateFormat.format(date);
            String sql = "INSERT INTO PEDIDOS (DIA,ID_CLIENTEp,STATUS,  PEDIDO, OBSERVACAO,entregador,formadepagamento) VALUES ('" + hoje + "'," + this.id_cliente + ", 'Aberto', '" + pedido + "', '" + this.obsPedido + "','Funcionário','" + fDp + "')";

            try {
                programagas.Conectar.alterar(sql);
            } catch (SQLException var21) {
                programagas.ProgramaGas.salvarErro(var21.getMessage() + "  Local:  " + var21.getLocalizedMessage());
                this.systemError.setText(var21.toString());
            }

            String sql1 = "UPDATE CLIENTES set OBSERVACAO = '" + this.obsPedido + "' where id_cliente = " + this.id_cliente + "";

            try {
                programagas.Conectar.alterar(sql1);
            } catch (SQLException var20) {
                programagas.ProgramaGas.salvarErro(var20.getMessage() + "  Local:  " + var20.getLocalizedMessage());
                this.systemError.setText(var20.toString());
            }

            String sql2 = "Select * from PEDIDOS where id_clientep = " + this.id_cliente + " and dia = '" + hoje + "' and pedido = '" + pedido + "'";

            try {
                programagas.Conectar.pesquisar(sql2);
                if (programagas.Conectar.rs.next()) {
                    this.id_pedido = programagas.Conectar.rs.getString("id_pedido");
                }
            } catch (SQLException var19) {
                programagas.ProgramaGas.salvarErro(var19.getMessage() + "  Local:  " + var19.getLocalizedMessage());
                this.systemError.setText(var19.toString());
            }

            programagas.Vendas.AddLinhaTabela(pedido, this.nome, end, fDp, this.telefone, this.id_pedido);
            this.dispose();
            Vendas.pedidosAberto();

            for(int i = 0; i < 1; ++i) {
                InputStream stream = new ByteArrayInputStream((SemAcento(this.gerarNotaTermica()) + '\u001b' + 'w').getBytes());
                PrintService impressora = this.ps[this.boxImpressoras.getSelectedIndex()];
                DocPrintJob dpj = impressora.createPrintJob();
                DocFlavor flavor = INPUT_STREAM.AUTOSENSE;
                Doc documentoTexto = new SimpleDoc(stream, flavor, (DocAttributeSet)null);

                try {
                    dpj.print(documentoTexto, (PrintRequestAttributeSet)null);
                } catch (PrintException var18) {
                    programagas.ProgramaGas.salvarErro(var18.getMessage() + "  Local:  " + var18.getLocalizedMessage());
                }
            }
        }

    }

    public static String SemAcento(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        DefaultTableModel model = (DefaultTableModel)this.jTable2.getModel();
        String[] linha = new String[]{"", "1"};
        model.addRow(linha);
    }

    private void jButton2ActionPerformed(ActionEvent evt) {
        int linhas = this.jTable2.getRowCount();
        double total = 0.0;
        System.out.println(linhas);

        int i;
        for(i = 0; i < linhas; ++i) {
            String nomeValor = this.jTable2.getValueAt(i, 0).toString();
            String sql = "Select * from PRODUTOS where nome = '" + nomeValor + "'";

            try {
                programagas.Conectar.pesquisar(sql);
                if (programagas.Conectar.rs.next()) {
                    this.jTable2.setValueAt(Conectar.rs.getString("Valor"), i, 2);
                }
            } catch (SQLException var9) {
                ProgramaGas.salvarErro(var9.getMessage() + "  Local:  " + var9.getLocalizedMessage());
            }
        }

        if (linhas > 0) {
            for(i = 0; i < linhas; ++i) {
                double valor = Double.parseDouble(this.jTable2.getValueAt(i, 2).toString().replace(",", "."));
                int multiplicador = Integer.parseInt(this.jTable2.getValueAt(i, 1).toString());
                total += valor * (double)multiplicador;
            }

            this.txtTotal.setText(Double.toString(total).replace(",", ".") + "0");
        }

    }

    private String montarPedido() {
        String pedido = this.jTable2.getValueAt(0, 1) + " " + this.jTable2.getValueAt(0, 0);
        int linhas = this.jTable2.getRowCount();
        if (linhas > 1) {
            for(int i = 2; i <= linhas; ++i) {
                pedido = pedido + ", " + this.jTable2.getValueAt(i - 1, 1) + " " + this.jTable2.getValueAt(i - 1, 0);
            }
        }

        return pedido;
    }
}

