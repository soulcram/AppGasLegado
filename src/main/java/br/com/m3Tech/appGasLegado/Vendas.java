package br.com.m3Tech.appGasLegado;

import br.com.m3Tech.appGasLegado.dto.ClienteDto;
import br.com.m3Tech.appGasLegado.dto.OpcoesDto;
import br.com.m3Tech.appGasLegado.service.ClienteService;
import programagas.Mascaras;
import programagas.PintarTabela;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.DefaultCellEditor;
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
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class Vendas extends JFrame {
    public static String status = "";
    Metodos m = new Metodos();
    private JFormattedTextField entradaTelTxt;
    private JComboBox<String> entregadorCol;
    private JComboBox<String> formPag;
    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JPanel jPanel1;
    private JScrollPane jScrollPane1;
    private JLabel lValidade;
    private static JLabel pedidosAbertosTxt;
    private static JLabel pedidosAbertosTxt1;
    private JComboBox<String> statusCol;
    public static JLabel systemError;
    public static JTable tabela1;

    public Vendas() {
        this.initComponents();
        this.lValidade.setText("Licença valida até " + ProgramaGas.DataLimite);
        tabela1.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(this.entregadorCol));
        tabela1.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(this.statusCol));
        tabela1.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(this.formPag));
        Mascaras.mascaraTelefone(this.entradaTelTxt);
        String sql = "Select * from Funcionarios";

        try {
            Conectar.pesquisar(sql);

            while(Conectar.rs.next()) {
                this.entregadorCol.addItem(Conectar.rs.getString("NOME"));
            }
            Conectar.rs.close();
        } catch (SQLException var4) {
            ProgramaGas.salvarErro(var4.getMessage() + "  Local:  " + var4.getLocalizedMessage());
            systemError.setText(var4.toString());
        }

        try {
            (new Bina()).setVisible(true);
        } catch (Exception var3) {
            ProgramaGas.salvarErro(var3.getMessage() + "  Local:  " + var3.getLocalizedMessage());
        }

    }

    public static void pedidosAberto() {
        int linhas = tabela1.getRowCount();
        int pedidoAbertos = 0;

        for(int i = 0; i < linhas; ++i) {
            String valor = tabela1.getValueAt(i, 6).toString();
            if ("Aberto".equals(valor)) {
                ++pedidoAbertos;
            }
        }

        pedidosAbertosTxt1.setText(Integer.toString(pedidoAbertos));
        pedidosAbertosTxt.setText(Integer.toString(linhas));
    }

    public static void AddLinhaTabela(String pedido, String nome, String endereco, String fDp, String telefone, String id) {
        DefaultTableModel model = (DefaultTableModel)tabela1.getModel();
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String hora = dateFormat.format(date);
        String[] conteudo = new String[]{pedido, nome, telefone, endereco, fDp, "Funcionário", "Aberto", hora, id};
        model.addRow(conteudo);
    }

    private void initComponents() {
        this.statusCol = new JComboBox();
        this.entregadorCol = new JComboBox();
        this.formPag = new JComboBox();
        this.jPanel1 = new JPanel();
        this.jScrollPane1 = new JScrollPane();
        tabela1 = new JTable();
        this.entradaTelTxt = new JFormattedTextField();
        this.jLabel1 = new JLabel();
        this.jButton1 = new JButton();
        this.jButton2 = new JButton();
        this.jButton3 = new JButton();
        this.jLabel2 = new JLabel();
        pedidosAbertosTxt = new JLabel();
        this.jLabel3 = new JLabel();
        pedidosAbertosTxt1 = new JLabel();
        systemError = new JLabel();
        this.lValidade = new JLabel();
        this.statusCol.setEditable(true);
        this.statusCol.setFont(new Font("Tahoma", 1, 12));
        this.statusCol.setModel(new DefaultComboBoxModel(new String[]{"Aberto", "Finalizado", "Cancelado"}));
        this.statusCol.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                statusColItemStateChanged(evt);
            }
        });
        this.statusCol.getAccessibleContext().setAccessibleParent(tabela1);
        this.entregadorCol.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                entregadorColItemStateChanged(evt);
            }
        });
        this.formPag.setModel(new DefaultComboBoxModel(new String[]{"Troco não","QR CODE", "Visa", "Mastercard", "Troco 60,00","Troco 200,00", "Troco100,00", "Troco 70,00", "Troco 50,00", "Troco 10,00", "Troco 20,00", "Elo", "Mais", "Hipercard", "Outros", "Troco 9,00", "Troco 15,00"}));
        this.setDefaultCloseOperation(3);
        this.setTitle("Consigaz");
        this.setSize(new Dimension(1000, 1000));
        this.jPanel1.setBackground(new Color(255, 255, 204));
        tabela1.setModel(new DefaultTableModel(new Object[0][], new String[]{"Pedido", "Nome ", "Telefone", "Endereço", "Forma de Pagamento", "Entregador", "Status", "Hora", "ID"}) {
            Class[] types = new Class[]{String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class};
            boolean[] canEdit = new boolean[]{false, false, false, false, true, true, true, false, false};

            public Class getColumnClass(int columnIndex) {
                return this.types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        tabela1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                tabela1MouseClicked(evt);
            }
        });
        this.jScrollPane1.setViewportView(tabela1);
        if (tabela1.getColumnModel().getColumnCount() > 0) {
            tabela1.getColumnModel().getColumn(0).setMaxWidth(300);
            tabela1.getColumnModel().getColumn(1).setMaxWidth(150);
            tabela1.getColumnModel().getColumn(2).setMaxWidth(100);
            tabela1.getColumnModel().getColumn(3).setMaxWidth(1000);
            tabela1.getColumnModel().getColumn(4).setMaxWidth(150);
            tabela1.getColumnModel().getColumn(5).setMaxWidth(150);
            tabela1.getColumnModel().getColumn(6).setMaxWidth(100);
            tabela1.getColumnModel().getColumn(7).setMaxWidth(100);
            tabela1.getColumnModel().getColumn(8).setMaxWidth(10);
        }

        this.entradaTelTxt.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                entradaTelTxtFocusGained(evt);
            }
        });
        this.entradaTelTxt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                VK_enter(evt);
            }
        });
        this.jLabel1.setText("Telefone");
        this.jButton1.setText("menu");
        this.jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        this.jButton2.setText("Pedidos de hoje");
        this.jButton2.setToolTipText("Caso falte energia e seja necessario Recarregar os pedidos feito hoje");
        this.jButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        this.jButton3.setText("Salvar Alterações");
        this.jButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        this.jLabel2.setFont(new Font("Tahoma", 1, 11));
        this.jLabel2.setText("Pedidos em Aberto");
        pedidosAbertosTxt.setFont(new Font("Tahoma", 1, 48));
        pedidosAbertosTxt.setForeground(new Color(0, 153, 0));
        pedidosAbertosTxt.setHorizontalAlignment(0);
        pedidosAbertosTxt.setText("0");
        this.jLabel3.setFont(new Font("Tahoma", 1, 11));
        this.jLabel3.setText("Pedidos de Hoje");
        pedidosAbertosTxt1.setFont(new Font("Tahoma", 1, 48));
        pedidosAbertosTxt1.setForeground(new Color(204, 0, 0));
        pedidosAbertosTxt1.setHorizontalAlignment(0);
        pedidosAbertosTxt1.setText("0");
        systemError.setForeground(new Color(153, 0, 0));
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(19, 19, 19).addComponent(this.jLabel1, -2, 133, -2).addGap(0, 0, 32767)).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addComponent(this.jScrollPane1, Alignment.TRAILING, -1, 1040, 32767).addGroup(Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addComponent(this.entradaTelTxt, -2, 122, -2).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING, false).addComponent(this.jLabel3, -1, 122, 32767).addComponent(pedidosAbertosTxt, -1, -1, 32767)).addGap(18, 18, 18).addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING, false).addComponent(pedidosAbertosTxt1, -1, -1, 32767).addComponent(this.jLabel2, -1, -1, 32767)).addGap(18, 18, 18).addComponent(this.jButton1, -2, 86, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jButton2, -2, 194, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent(systemError, -2, 639, -2).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent(this.jButton3))))).addContainerGap()).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(this.lValidade, -2, 300, -2).addContainerGap(-1, 32767)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING).addComponent(this.jLabel1, -2, 14, -2).addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.jLabel3))).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addComponent(this.entradaTelTxt, -2, -1, -2).addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(pedidosAbertosTxt, -2, 53, -2).addComponent(pedidosAbertosTxt1, -2, 53, -2)))).addComponent(this.jButton1, -2, 41, -2)).addGap(18, 18, 18).addComponent(this.jScrollPane1, -2, 501, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.lValidade, -1, 19, 32767).addPreferredGap(ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.jButton2).addComponent(this.jButton3)).addComponent(systemError, Alignment.TRAILING, -2, 23, -2)).addContainerGap()));
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(this.jPanel1, -1, -1, 32767));
        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jPanel1, -1, -1, 32767)));
        this.setBounds(0, 0, 1076, 723);
    }

    private void VK_enter(ActionEvent evt) {
        String numeroTelefoneVerificado = this.m.numero(this.entradaTelTxt.getText());

        ClienteDto cliente = new Service().getCliente(numeroTelefoneVerificado);

        if(cliente != null && cliente.getClienteEnderecos() != null && !cliente.getClienteEnderecos().isEmpty()){
            cliente.setViaApi(true);
            (new TelaPedidos(cliente)).setVisible(true);
            new ClienteService().salvarCliente(cliente);
        }else {

            String sql = "SELECT * FROM CLIENTES where telefone =  '" + numeroTelefoneVerificado + "'";

            try {
                Conectar.pesquisar(sql);
                if (Conectar.rs.next()) {
                    ClienteDto novoCliente = new ClienteDto();
                    novoCliente.setTelefone(numeroTelefoneVerificado);
                    (new TelaPedidos(novoCliente)).setVisible(true);
                } else {
                    CadastrarNovoCliente cnc = new CadastrarNovoCliente();
                    cnc.setVisible(true);
                    cnc.telefoneTxt.setText(numeroTelefoneVerificado);
                }
                Conectar.rs.close();
            } catch (SQLException var5) {
                ProgramaGas.salvarErro(var5.getMessage() + "  Local:  " + var5.getLocalizedMessage());
                systemError.setText(var5.toString());
            }

            salvar();
        }
        this.entradaTelTxt.setText("");
    }

    private void statusColItemStateChanged(ItemEvent evt) {
        TableCellRenderer renderer = new PintarTabela();

        for(int c = 0; c < tabela1.getColumnCount(); ++c) {
            tabela1.setDefaultRenderer(tabela1.getColumnClass(c), renderer);
        }

    }

    private void entradaTelTxtFocusGained(FocusEvent evt) {
        this.entradaTelTxt.setCaretPosition(4);
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        (new TelaMenu()).setVisible(true);
    }

    private void tabela1MouseClicked(MouseEvent evt) {
        salvar();
        if (evt.getClickCount() == 2) {
            OpcoesDto opcoesDto = new OpcoesDto();
            opcoesDto.setIdPedido(Integer.valueOf(tabela1.getValueAt(tabela1.getSelectedRow(), 8).toString()));
            opcoesDto.setTelefone(tabela1.getValueAt(tabela1.getSelectedRow(), 2).toString());

            (new TelaOpcoes(opcoesDto)).setVisible(true);
        }

    }

    private void jButton2ActionPerformed(ActionEvent evt) {
        DefaultTableModel model = (DefaultTableModel)tabela1.getModel();
        model.setNumRows(0);
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String hoje = dateFormat.format(date);
        String sql = "select * from pedidos INNER JOIN CLIENTES ON ID_CLIENTE = ID_CLIENTEp INNER JOIN ENDERECO ON ID_CEP = ID_ENDERECO where DIA = '" + hoje + "'";

        try {
            Conectar.pesquisar(sql);

            while(Conectar.rs.next()) {
                String endereco = Conectar.rs.getString("tp_logradouro") + ": " + Conectar.rs.getString("logradouro") + ", " + Conectar.rs.getString("numero") + " - " + Conectar.rs.getString("bairro") + "    Prox: " + Conectar.rs.getString("referencia");
                String entregador;
                if ("".equals(Conectar.rs.getString("entregador"))) {
                    entregador = "Funcionário";
                } else {
                    entregador = Conectar.rs.getString("entregador");
                }

                String[] conteudo = new String[]{Conectar.rs.getString("pedido"), Conectar.rs.getString("nome"), Conectar.rs.getString("telefone"), endereco, Conectar.rs.getString("formadepagamento"), entregador, Conectar.rs.getString("Status"), "", Conectar.rs.getString("id_pedido")};
                model.addRow(conteudo);
            }
            Conectar.rs.close();
        } catch (SQLException var10) {
            ProgramaGas.salvarErro(var10.getMessage() + "  Local:  " + var10.getLocalizedMessage());
            systemError.setText(var10.toString());
        }

    }

    private void entregadorColItemStateChanged(ItemEvent evt) {
    }

    private void jButton3ActionPerformed(ActionEvent evt) {
        if (tabela1.getRowCount() > 0) {
            salvar();
            pedidosAberto();
        }

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Toolkit tk = Toolkit.getDefaultToolkit();
            Dimension d = tk.getScreenSize();
            Conectar.startBd();
            programagas.Vendas tela = new programagas.Vendas();
            tela.setSize(d.width, d.height);
            tela.setVisible(true);
        });
    }

    public static void salvar() {
        int linhas = tabela1.getRowCount();
        if (linhas > 0) {
            for(int i = 0; i < linhas; ++i) {
                String id = tabela1.getValueAt(i, 8).toString();
                String entregador = tabela1.getValueAt(i, 5).toString();
                String Status = tabela1.getValueAt(i, 6).toString();
                String fdp = tabela1.getValueAt(i, 4).toString();
                String sql = "update PEDIDOS set status = '" + Status + "', entregador = '" + entregador + "',formadepagamento = '" + fdp + "' where id_pedido = " + id + " ";

                try {
                    Conectar.alterar(sql);
                } catch (SQLException var8) {
                    ProgramaGas.salvarErro(var8.getMessage() + "  Local:  " + var8.getLocalizedMessage());
                    systemError.setText(var8.toString());
                }
            }
        }

    }
}