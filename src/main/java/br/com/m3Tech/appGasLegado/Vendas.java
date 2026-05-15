package br.com.m3Tech.appGasLegado;

import br.com.m3Tech.appGasLegado.dto.ClienteDto;
import br.com.m3Tech.appGasLegado.dto.OpcoesDto;
import br.com.m3Tech.appGasLegado.dto.PedidoServicoDto;
import br.com.m3Tech.appGasLegado.service.ClienteService;
import br.com.m3Tech.appGasLegado.ui.AppTheme;
import br.com.m3Tech.appGasLegado.ui.UiComponents;
import br.com.m3Tech.appGasLegado.utils.Mascaras;
import br.com.m3Tech.utils.BooleanUtils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Vendas extends JFrame {
    public static String status = "";
    Metodos m = new Metodos();
    public static JFormattedTextField entradaTelTxt;
    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JLabel lValidade;
    private static JLabel pedidosAbertosTxt;
    private static JLabel pedidosAbertosTxt1;
    public static JLabel systemError;

    public Vendas() {
        this.initComponents();
        this.lValidade.setText("Licença valida até " + ProgramaGas.DataLimite);
        Mascaras.mascaraTelefone(this.entradaTelTxt);

        try {
            (new Bina()).setVisible(true);
        } catch (Exception var3) {
            ProgramaGas.salvarErro(var3.getMessage() + "  Local:  " + var3.getLocalizedMessage());
        }
    }

    public static void pedidosAberto() {
        int linhas = ProgramaGas.tabela1.getRowCount();
        int pedidoAbertos = 0;

        for (int i = 0; i < linhas; ++i) {
            Object valueAt = ProgramaGas.tabela1.getValueAt(i, 6);
            if (valueAt == null) {
                ++pedidoAbertos;
            } else {
                String valor = valueAt.toString();
                if (!"Finalizado".equals(valor)) {
                    ++pedidoAbertos;
                }
            }
        }

        pedidosAbertosTxt1.setText(Integer.toString(pedidoAbertos));
        pedidosAbertosTxt.setText(Integer.toString(linhas));
    }

    public static void AddLinhaTabela(String pedido, String nome, String endereco, String fDp, String telefone, String id) {
        DefaultTableModel model = (DefaultTableModel) ProgramaGas.tabela1.getModel();
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String hora = dateFormat.format(date);
        String[] conteudo = new String[]{pedido, nome, telefone, endereco, fDp, "Funcionário", "Aberto", hora, id};
        model.addRow(conteudo);
    }

    private void initComponents() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Consigaz");
        UiComponents.applyFrameDefaults(this);

        entradaTelTxt = new JFormattedTextField();
        entradaTelTxt.setPreferredSize(new Dimension(160, 32));
        entradaTelTxt.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                entradaTelTxtFocusGained(evt);
            }
        });
        entradaTelTxt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                VK_enter(evt);
            }
        });

        jButton1 = UiComponents.secondaryButton("Menu");
        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2 = UiComponents.primaryButton("Pedidos de hoje");
        jButton2.setToolTipText("Caso falte energia e seja necessario Recarregar os pedidos feito hoje");
        jButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3 = UiComponents.primaryButton("Salvar alterações");
        jButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        pedidosAbertosTxt = new JLabel("0", SwingConstants.CENTER);
        pedidosAbertosTxt.setFont(AppTheme.fontKpi());
        pedidosAbertosTxt.setForeground(AppTheme.SUCCESS);

        pedidosAbertosTxt1 = new JLabel("0", SwingConstants.CENTER);
        pedidosAbertosTxt1.setFont(AppTheme.fontKpi());
        pedidosAbertosTxt1.setForeground(AppTheme.DANGER);

        systemError = new JLabel();
        UiComponents.styleErrorLabel(systemError);

        lValidade = new JLabel();
        lValidade.setFont(AppTheme.font(Font.PLAIN, 11));
        lValidade.setForeground(AppTheme.TEXT_MUTED);

        ProgramaGas.tabela1 = new JTable();
        ProgramaGas.tabela1.setModel(new DefaultTableModel(new Object[0][], new String[]{
                "Pedido", "Nome ", "Telefone", "Endereço", "Forma de Pagamento", "Entregador", "Status", "Hora", "ID"
        }) {
            Class[] types = new Class[]{String.class, String.class, String.class, String.class, String.class,
                    String.class, String.class, String.class, String.class};
            boolean[] canEdit = new boolean[]{false, false, false, false, true, true, true, false, false};

            public Class getColumnClass(int columnIndex) {
                return this.types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        ProgramaGas.tabela1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                tabela1MouseClicked(evt);
            }
        });
        UiComponents.styleTable(ProgramaGas.tabela1);
        if (ProgramaGas.tabela1.getColumnModel().getColumnCount() > 0) {
            ProgramaGas.tabela1.getColumnModel().getColumn(0).setMaxWidth(300);
            ProgramaGas.tabela1.getColumnModel().getColumn(1).setMaxWidth(150);
            ProgramaGas.tabela1.getColumnModel().getColumn(2).setMaxWidth(100);
            ProgramaGas.tabela1.getColumnModel().getColumn(3).setMaxWidth(1000);
            ProgramaGas.tabela1.getColumnModel().getColumn(4).setMaxWidth(150);
            ProgramaGas.tabela1.getColumnModel().getColumn(5).setMaxWidth(150);
            ProgramaGas.tabela1.getColumnModel().getColumn(6).setMaxWidth(100);
            ProgramaGas.tabela1.getColumnModel().getColumn(7).setMaxWidth(100);
            ProgramaGas.tabela1.getColumnModel().getColumn(8).setMaxWidth(10);
        }

        JScrollPane scrollPane = new JScrollPane(ProgramaGas.tabela1);
        JPanel tableCard = UiComponents.cardPanel("Pedidos");
        tableCard.setLayout(new BorderLayout());
        tableCard.add(scrollPane, BorderLayout.CENTER);

        JPanel header = UiComponents.headerBar("Consigaz");
        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, AppTheme.PAD_SM, 0));
        headerRight.setOpaque(false);
        JLabel telLabel = new JLabel("Telefone");
        telLabel.setFont(AppTheme.fontLabelBold());
        headerRight.add(telLabel);
        headerRight.add(entradaTelTxt);
        headerRight.add(jButton1);
        header.add(headerRight, BorderLayout.EAST);

        JPanel kpiPanel = new JPanel(new GridLayout(1, 2, AppTheme.PAD, 0));
        kpiPanel.setOpaque(false);
        kpiPanel.setBorder(new EmptyBorder(0, AppTheme.PAD, 0, AppTheme.PAD));
        kpiPanel.setPreferredSize(new Dimension(280, 100));

        JPanel kpiHoje = UiComponents.cardPanel("Pedidos de hoje");
        kpiHoje.setLayout(new BorderLayout());
        kpiHoje.add(pedidosAbertosTxt, BorderLayout.CENTER);

        JPanel kpiAbertos = UiComponents.cardPanel("Pedidos em aberto");
        kpiAbertos.setLayout(new BorderLayout());
        kpiAbertos.add(pedidosAbertosTxt1, BorderLayout.CENTER);

        kpiPanel.add(kpiHoje);
        kpiPanel.add(kpiAbertos);

        JPanel northWrapper = new JPanel(new BorderLayout());
        northWrapper.setOpaque(false);
        northWrapper.add(header, BorderLayout.NORTH);
        JPanel northBody = new JPanel(new BorderLayout(AppTheme.PAD, 0));
        northBody.setOpaque(false);
        northBody.setBorder(new EmptyBorder(AppTheme.PAD_SM, AppTheme.PAD, AppTheme.PAD_SM, AppTheme.PAD));
        northBody.add(kpiPanel, BorderLayout.EAST);
        northWrapper.add(northBody, BorderLayout.CENTER);

        JPanel footer = new JPanel(new BorderLayout(AppTheme.PAD, AppTheme.PAD_SM));
        footer.setOpaque(false);
        footer.setBorder(new EmptyBorder(AppTheme.PAD_SM, AppTheme.PAD, AppTheme.PAD, AppTheme.PAD));
        footer.add(lValidade, BorderLayout.WEST);
        footer.add(systemError, BorderLayout.CENTER);
        JPanel footerButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, AppTheme.PAD_SM, 0));
        footerButtons.setOpaque(false);
        footerButtons.add(jButton2);
        footerButtons.add(jButton3);
        footer.add(footerButtons, BorderLayout.EAST);

        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setOpaque(false);
        centerWrapper.setBorder(new EmptyBorder(0, AppTheme.PAD, 0, AppTheme.PAD));
        centerWrapper.add(tableCard, BorderLayout.CENTER);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(AppTheme.BACKGROUND);
        root.add(northWrapper, BorderLayout.NORTH);
        root.add(centerWrapper, BorderLayout.CENTER);
        root.add(footer, BorderLayout.SOUTH);
        getContentPane().add(root);
    }

    private void VK_enter(ActionEvent evt) {
        String numeroTelefoneVerificado = this.m.numero(this.entradaTelTxt.getText());

        ClienteDto cliente = null;

        if (ProgramaGas.servico) {
            cliente = new Service().getCliente(numeroTelefoneVerificado);
        }

        if (cliente != null && cliente.getClienteEnderecos() != null && !cliente.getClienteEnderecos().isEmpty()) {
            cliente.setViaApi(true);
            (new TelaPedidos(cliente)).setVisible(true);
            new ClienteService().salvarCliente(cliente);
        } else {
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
        }
        this.entradaTelTxt.setText("");
    }

    private void entradaTelTxtFocusGained(FocusEvent evt) {
        this.entradaTelTxt.setCaretPosition(4);
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        (new TelaMenu()).setVisible(true);
    }

    private void tabela1MouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            OpcoesDto opcoesDto = new OpcoesDto();
            opcoesDto.setIdPedido(Integer.valueOf(ProgramaGas.tabela1.getValueAt(ProgramaGas.tabela1.getSelectedRow(), 8).toString()));
            opcoesDto.setTelefone(ProgramaGas.tabela1.getValueAt(ProgramaGas.tabela1.getSelectedRow(), 2).toString());

            (new TelaOpcoes(opcoesDto)).setVisible(true);
        }
    }

    private void jButton2ActionPerformed(ActionEvent evt) {
        DefaultTableModel model = (DefaultTableModel) ProgramaGas.tabela1.getModel();
        model.setNumRows(0);

        if (BooleanUtils.defaultFalseIfNull(ProgramaGas.servico)) {
            List<PedidoServicoDto> allPedidos = new Service().getAllPedidos();

            for (PedidoServicoDto pedidoServicoDto : allPedidos) {
                String endereco = pedidoServicoDto.getLogradouro() + ", " + pedidoServicoDto.getNumeroResidencia()
                        + " - " + pedidoServicoDto.getBairro() + "    Prox: " + pedidoServicoDto.getComplemento();

                String[] conteudo = new String[]{pedidoServicoDto.getPedido(), pedidoServicoDto.getNomeCliente(),
                        pedidoServicoDto.getTelefoneCliente(), endereco, pedidoServicoDto.getFormaPagamento(),
                        pedidoServicoDto.getEntregador(), pedidoServicoDto.getStatus(), pedidoServicoDto.getHora(),
                        pedidoServicoDto.getIdPedido().toString()};
                model.addRow(conteudo);
            }
        } else {
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String hoje = dateFormat.format(date);
            String sql = "select * from pedidos INNER JOIN CLIENTES ON ID_CLIENTE = ID_CLIENTEp INNER JOIN ENDERECO ON ID_CEP = ID_ENDERECO where DIA = '" + hoje + "'";

            try {
                Conectar.pesquisar(sql);

                while (Conectar.rs.next()) {
                    String endereco = Conectar.rs.getString("tp_logradouro") + ": " + Conectar.rs.getString("logradouro")
                            + ", " + Conectar.rs.getString("numero") + " - " + Conectar.rs.getString("bairro")
                            + "    Prox: " + Conectar.rs.getString("referencia");
                    String entregador;
                    if ("".equals(Conectar.rs.getString("entregador"))) {
                        entregador = "Funcionário";
                    } else {
                        entregador = Conectar.rs.getString("entregador");
                    }

                    String[] conteudo = new String[]{Conectar.rs.getString("pedido"), Conectar.rs.getString("nome"),
                            Conectar.rs.getString("telefone"), endereco, Conectar.rs.getString("formadepagamento"),
                            entregador, Conectar.rs.getString("Status"), "", Conectar.rs.getString("id_pedido")};
                    model.addRow(conteudo);
                }
                Conectar.rs.close();
            } catch (SQLException var10) {
                ProgramaGas.salvarErro(var10.getMessage() + "  Local:  " + var10.getLocalizedMessage());
                systemError.setText(var10.toString());
            }
        }
        pedidosAberto();
    }

    private void jButton3ActionPerformed(ActionEvent evt) {
        if (ProgramaGas.tabela1.getRowCount() > 0) {
            pedidosAberto();
        }
    }
}
