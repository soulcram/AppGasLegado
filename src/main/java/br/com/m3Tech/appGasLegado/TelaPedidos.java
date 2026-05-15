package br.com.m3Tech.appGasLegado;

import br.com.m3Tech.appGasLegado.dto.*;
import br.com.m3Tech.appGasLegado.ui.AppTheme;
import br.com.m3Tech.appGasLegado.ui.UiComponents;
import br.com.m3Tech.appGasLegado.utils.ImpressoraUtils;
import br.com.m3Tech.appGasLegado.utils.PedidosUtils;
import br.com.m3Tech.utils.StringUtils;
import org.apache.commons.lang3.BooleanUtils;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.DocFlavor.SERVICE_FORMATTED;
import javax.print.attribute.AttributeSet;
import javax.swing.*;
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

    private JCheckBox imprimirCheckBox;

    public TelaPedidos(ClienteDto cliente) {
        this.initComponents();
        imprimirCheckBox.setSelected(true);
        this.telefone = cliente.getTelefone();
        String loadProdutos = "Select * from PRODUTOS";

        try {
            Conectar.pesquisar(loadProdutos);

            while(Conectar.rs.next()) {
                this.produtosCol.addItem(Conectar.rs.getString("NOME"));
            }
            Conectar.rs.close();
        } catch (SQLException var10) {
            ProgramaGas.salvarErro(var10.getMessage() + "  Local:  " + var10.getLocalizedMessage());
            this.systemError.setText(var10.toString());
        }

        this.jTable2.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(this.produtosCol));
        this.jTable2.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(this.quantCol));
        ActionEvent evt = null;
        this.jButton1ActionPerformed((ActionEvent)evt);
        this.preencher(cliente);

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
            ProgramaGas.salvarErro(var9.getMessage() + "  Local:  " + var9.getLocalizedMessage());
        }

        this.boxImpressoras.setSelectedItem(ProgramaGas.Impressora);
    }

    private void preencher(ClienteDto cliente) {

        if(BooleanUtils.toBooleanDefaultIfNull(cliente.getViaApi(),false)){

            ClienteEndereco clienteEndereco = cliente.getClienteEnderecos().get(0);

            this.telefoneTxt.setText(cliente.getTelefone());
            if (cliente.getIdCliente() != null) {
                this.id_cliente = String.valueOf(cliente.getIdCliente());
            }
            if (!Boolean.TRUE.equals(ProgramaGas.servico)) {
                resolverIdClienteLocal(cliente.getTelefone());
            }

            if(cliente.getUltimosPedidos() != null) {
                try {
                    DefaultTableModel model = (DefaultTableModel) this.jTable1.getModel();
                    List<PedidoLegadoSimplesDto> pedidosOrdenados = new ArrayList<>(cliente.getUltimosPedidos());
                    pedidosOrdenados.sort(Comparator.comparing(
                            PedidoLegadoSimplesDto::getData,
                            Comparator.nullsLast(Comparator.reverseOrder())));

                    for (PedidoLegadoSimplesDto pedidoDto : pedidosOrdenados) {
                        //ordem das colunas "Data", "Pedido", "Valor", "Loja", "Forma de Pagamento", "Entregador", "Status"
                        String[] conteudo = new String[]{
                                pedidoDto.getData() == null ? "" : pedidoDto.getData().toString(),
                                pedidoDto.getPedido(),
                                pedidoDto.getValorTotal() == null ? "" : pedidoDto.getValorTotal().toString(),
                                pedidoDto.getLojaOriginal() != null ? pedidoDto.getLoja() + " - " + pedidoDto.getLojaOriginal() : pedidoDto.getLoja(),
                                pedidoDto.getFormaPagamento(),
                                pedidoDto.getEntregador(),
                                pedidoDto.getStatus()
                        };

                        model.addRow(conteudo);
                    }

                } catch (Exception var7) {
                    ProgramaGas.salvarErro(var7.getMessage() + "  Local:  " + var7.getLocalizedMessage());
                    this.systemError.setText(var7.toString());
                }
            }
            this.idTxt.setText(this.id_cliente);
            this.nomeTxt.setText(cliente.getNome());
            this.endTxt.setText( clienteEndereco.getEndereco().getLogradouro() + ", " + clienteEndereco.getNumero() + " - " + clienteEndereco.getEndereco().getBairro());
            this.obsTxt.setText(cliente.getObservacao());
            this.pedidoObs.setText(cliente.getObservacao());
            this.nome = cliente.getNome();
            this.numCasa = clienteEndereco.getNumero();
            this.obs = cliente.getObservacao();
            this.cidade = clienteEndereco.getEndereco().getCidade();
            this.logradouro = clienteEndereco.getEndereco().getLogradouro();
            this.bairro = clienteEndereco.getEndereco().getBairro();
            this.cep = clienteEndereco.getEndereco().getCep();
            this.tp_logradouro = "Rua";
            this.referencia = clienteEndereco.getComplemento();

        }else {


            String sql = "SELECT * FROM CLIENTES where telefone =  '" + cliente.getTelefone() + "'";

            try {
                Conectar.pesquisar(sql);

                while (Conectar.rs.next()) {
                    this.id_cliente = Conectar.rs.getString("ID_CLIENTE");
                    this.nome = Conectar.rs.getString("NOME");
                    this.id_endereco = Conectar.rs.getString("ID_ENDERECO");
                    this.numCasa = Conectar.rs.getString("NUMERO");
                    this.obs = Conectar.rs.getString("OBSERVACAO");
                }
                Conectar.rs.close();
            } catch (SQLException var9) {
                ProgramaGas.salvarErro(var9.getMessage() + "  Local:  " + var9.getLocalizedMessage());
                this.systemError.setText(var9.toString());
            }

            String sql1 = "SELECT * FROM ENDERECO where ID_CEP =  " + this.id_endereco + "";

            try {
                Conectar.pesquisar(sql1);

                while (Conectar.rs.next()) {
                    this.cidade = Conectar.rs.getString("CIDADE");
                    this.logradouro = Conectar.rs.getString("LOGRADOURO");
                    this.bairro = Conectar.rs.getString("BAIRRO");
                    this.cep = Conectar.rs.getString("CEP");
                    this.tp_logradouro = Conectar.rs.getString("TP_LOGRADOURO");
                    this.referencia = Conectar.rs.getString("REFERENCIA");
                }
                Conectar.rs.close();
            } catch (SQLException var8) {
                ProgramaGas.salvarErro(var8.getMessage() + "  Local:  " + var8.getLocalizedMessage());
                this.systemError.setText(var8.toString());
            }

            String sql2 = "SELECT * FROM PEDIDOS where ID_CLIENTEp =  " + this.id_cliente + "";

            try {
                Conectar.pesquisar(sql2);
                DefaultTableModel model = (DefaultTableModel) this.jTable1.getModel();
                List<String[]> linhas = new ArrayList<>();
                DateFormat formatoDia = new SimpleDateFormat("dd/MM/yyyy");

                while (Conectar.rs.next()) {
                    linhas.add(new String[]{Conectar.rs.getString("DIA"), Conectar.rs.getString("PEDIDO"), Conectar.rs.getString("VALOR") ,"", Conectar.rs.getString("formadepagamento"), Conectar.rs.getString("ENTREGADOR"), Conectar.rs.getString("STATUS") });
                }
                Conectar.rs.close();

                linhas.sort((a, b) -> {
                    if (a[0] == null && b[0] == null) {
                        return 0;
                    }
                    if (a[0] == null) {
                        return 1;
                    }
                    if (b[0] == null) {
                        return -1;
                    }
                    try {
                        Date dataA = formatoDia.parse(a[0]);
                        Date dataB = formatoDia.parse(b[0]);
                        return dataB.compareTo(dataA);
                    } catch (Exception e) {
                        return b[0].compareTo(a[0]);
                    }
                });

                for (String[] conteudo : linhas) {
                    model.addRow(conteudo);
                }
            } catch (SQLException var7) {
                ProgramaGas.salvarErro(var7.getMessage() + "  Local:  " + var7.getLocalizedMessage());
                this.systemError.setText(var7.toString());
            }

            this.telefoneTxt.setText(cliente.getTelefone());
            this.idTxt.setText(this.id_cliente);
            this.nomeTxt.setText(this.nome);
            this.endTxt.setText(this.tp_logradouro + ": " + this.logradouro + ", " + this.numCasa + " - " + this.bairro);
            this.obsTxt.setText(this.obs);
            this.pedidoObs.setText(this.obs);
        }
    }

    private void initComponents() {
        this.produtosCol = new JComboBox();
        this.quantCol = new JComboBox();
        this.idTxt = new JLabel("ID");
        this.nomeTxt = new JLabel("Nome");
        this.endTxt = new JLabel("Endereço");
        this.obsTxt = new JLabel("Observação");
        this.telefoneTxt = new JLabel("Telefone");
        this.jScrollPane1 = new JScrollPane();
        this.jTable1 = new JTable();
        this.jScrollPane2 = new JScrollPane();
        this.jTable2 = new JTable();
        this.jLabel1 = new JLabel("Forma de pagamento", SwingConstants.CENTER);
        this.formaPagamento = new JComboBox();
        this.txtTotal = new JTextField();
        this.pedidoObs = new JTextField();
        this.msgErro = new JLabel();
        this.systemError = new JLabel();
        this.boxImpressoras = new JComboBox();
        this.imprimirCheckBox = new JCheckBox("Imprimir pedido");

        this.quantCol.setModel(new DefaultComboBoxModel(new String[]{
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"
        }));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Pedidos");
        UiComponents.applyFrameDefaults(this);

        for (JLabel label : new JLabel[]{idTxt, nomeTxt, endTxt, obsTxt, telefoneTxt}) {
            label.setFont(AppTheme.fontSubtitle());
            label.setForeground(AppTheme.TEXT);
        }
        UiComponents.styleErrorLabel(msgErro);
        UiComponents.styleErrorLabel(systemError);

        this.jTable1.setModel(new DefaultTableModel(new Object[0][], new String[]{
                "Data", "Pedido", "Valor", "Loja", "Forma de Pagamento", "Entregador", "Status"
        }) {
            Class[] types = new Class[]{String.class, String.class, String.class, String.class,
                    String.class, String.class, String.class};
            boolean[] canEdit = new boolean[]{false, false, false, false, false, false, false};

            public Class getColumnClass(int columnIndex) {
                return this.types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        UiComponents.styleTable(this.jTable1);
        this.jScrollPane1.setViewportView(this.jTable1);
        if (this.jTable1.getColumnModel().getColumnCount() > 0) {
            this.jTable1.getColumnModel().getColumn(0).setPreferredWidth(90);
            this.jTable1.getColumnModel().getColumn(1).setPreferredWidth(230);
            this.jTable1.getColumnModel().getColumn(2).setPreferredWidth(90);
            this.jTable1.getColumnModel().getColumn(3).setPreferredWidth(230);
            this.jTable1.getColumnModel().getColumn(4).setPreferredWidth(125);
            this.jTable1.getColumnModel().getColumn(5).setPreferredWidth(90);
            this.jTable1.getColumnModel().getColumn(6).setPreferredWidth(90);
            this.jTable1.getColumnModel().getColumn(6).setMaxWidth(150);
        }

        this.formaPagamento.setEditable(true);
        this.formaPagamento.setModel(new DefaultComboBoxModel(new String[]{
                "Troco não", "QR CODE", "Visa", "Mastercard", "Troco 100,00", "Troco 200,00",
                "Troco 50,00", "Troco 60,00", "Troco100,00", "Troco 70,00", "Troco 10,00",
                "Troco 20,00", "Elo", "Mais", "Hipercard", "Outros", "Troco 9,00", "Troco 15,00"
        }));
        this.jTable2.setModel(new DefaultTableModel(new Object[0][], new String[]{"Produto", "Quantidade", "Valor Unidade"}) {
            Class[] types = new Class[]{String.class, String.class, String.class};

            public Class getColumnClass(int columnIndex) {
                return this.types[columnIndex];
            }
        });
        UiComponents.styleTable(this.jTable2);
        this.jScrollPane2.setViewportView(this.jTable2);

        this.jButton1 = UiComponents.primaryButton("+");
        this.jButton1.setPreferredSize(new Dimension(48, 40));
        this.jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        this.jButton2 = UiComponents.secondaryButton("Total");
        this.jButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        this.botaoOk = UiComponents.primaryButton("Salvar pedido");
        this.botaoOk.setFont(AppTheme.font(java.awt.Font.BOLD, 16));
        this.botaoOk.setPreferredSize(new Dimension(220, 52));
        this.botaoOk.setMinimumSize(new Dimension(180, 52));
        this.botaoOk.setMaximumSize(new Dimension(320, 52));
        this.botaoOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                botaoOkActionPerformed(evt);
            }
        });
        this.alterarEndereco = UiComponents.secondaryButton("Alterar endereço");
        this.alterarEndereco.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                alterarEnderecoActionPerformed(evt);
            }
        });
        this.pedidoObs.setBorder(BorderFactory.createTitledBorder("Observações para o pedido"));

        JPanel clientCard = UiComponents.cardPanel("Cliente");
        clientCard.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = new Insets(0, 0, 6, 0);
        clientCard.add(idTxt, gc);
        gc.gridy++;
        clientCard.add(telefoneTxt, gc);
        gc.gridy++;
        clientCard.add(nomeTxt, gc);
        gc.gridy++;
        clientCard.add(endTxt, gc);
        gc.gridy++;
        clientCard.add(obsTxt, gc);
        gc.gridy++;
        clientCard.add(alterarEndereco, gc);

        JPanel historyCard = UiComponents.cardPanel("Últimos pedidos");
        historyCard.setLayout(new BorderLayout());
        historyCard.add(jScrollPane1, BorderLayout.CENTER);
        historyCard.setPreferredSize(new Dimension(400, 160));

        JPanel leftCol = new JPanel();
        leftCol.setLayout(new BoxLayout(leftCol, BoxLayout.Y_AXIS));
        leftCol.setOpaque(false);
        leftCol.add(clientCard);
        leftCol.add(Box.createVerticalStrut(AppTheme.PAD_SM));
        leftCol.add(historyCard);
        leftCol.add(Box.createVerticalStrut(AppTheme.PAD_SM));
        msgErro.setAlignmentX(0f);
        leftCol.add(msgErro);

        this.jPanel2 = UiComponents.cardPanel("Pedido de hoje");
        jPanel2.setLayout(new BorderLayout(AppTheme.PAD, AppTheme.PAD));
        JPanel orderTop = new JPanel(new BorderLayout(AppTheme.PAD_SM, AppTheme.PAD_SM));
        orderTop.setOpaque(false);
        orderTop.add(jScrollPane2, BorderLayout.CENTER);
        JPanel orderSide = new JPanel();
        orderSide.setLayout(new BoxLayout(orderSide, BoxLayout.Y_AXIS));
        orderSide.setOpaque(false);
        orderSide.add(jButton1);
        orderSide.add(Box.createVerticalStrut(8));
        orderSide.add(jLabel1);
        orderSide.add(Box.createVerticalStrut(4));
        orderSide.add(formaPagamento);
        orderSide.add(Box.createVerticalStrut(8));
        orderSide.add(jButton2);
        orderSide.add(Box.createVerticalStrut(4));
        orderSide.add(txtTotal);
        orderTop.add(orderSide, BorderLayout.EAST);
        jPanel2.add(orderTop, BorderLayout.CENTER);

        JPanel actionsCard = UiComponents.cardPanel("Finalizar");
        actionsCard.setLayout(new GridBagLayout());
        GridBagConstraints ac = new GridBagConstraints();
        ac.gridx = 0;
        ac.gridy = 0;
        ac.weightx = 1;
        ac.fill = GridBagConstraints.HORIZONTAL;
        ac.insets = new Insets(0, 0, 8, 0);
        actionsCard.add(pedidoObs, ac);
        ac.gridy++;
        actionsCard.add(boxImpressoras, ac);
        ac.gridy++;
        actionsCard.add(imprimirCheckBox, ac);

        JPanel rightCol = new JPanel();
        rightCol.setLayout(new BoxLayout(rightCol, BoxLayout.Y_AXIS));
        rightCol.setOpaque(false);
        rightCol.add(jPanel2);
        rightCol.add(Box.createVerticalStrut(AppTheme.PAD));
        rightCol.add(actionsCard);

        JPanel columns = new JPanel(new GridBagLayout());
        columns.setOpaque(false);
        GridBagConstraints cc = new GridBagConstraints();
        cc.gridx = 0;
        cc.gridy = 0;
        cc.weightx = 0.45;
        cc.weighty = 1;
        cc.fill = GridBagConstraints.BOTH;
        cc.insets = new Insets(0, 0, 0, AppTheme.PAD);
        columns.add(leftCol, cc);
        cc.gridx = 1;
        cc.weightx = 0.55;
        columns.add(rightCol, cc);

        JPanel footer = new JPanel(new BorderLayout(AppTheme.PAD, 0));
        footer.setOpaque(false);
        footer.setBorder(BorderFactory.createEmptyBorder(AppTheme.PAD, 0, 0, 0));
        footer.add(systemError, BorderLayout.CENTER);
        footer.add(botaoOk, BorderLayout.EAST);

        JPanel root = new JPanel(new BorderLayout(0, AppTheme.PAD));
        root.setBackground(AppTheme.BACKGROUND);
        root.setBorder(BorderFactory.createEmptyBorder(AppTheme.PAD, AppTheme.PAD, AppTheme.PAD, AppTheme.PAD));
        root.add(UiComponents.headerBar("Novo pedido"), BorderLayout.NORTH);
        root.add(columns, BorderLayout.CENTER);
        root.add(footer, BorderLayout.SOUTH);
        getContentPane().add(root);
        pack();
        setSize(Math.max(getWidth(), 980), Math.max(getHeight(), 640));
    }

    private void resolverIdClienteLocal(String telefoneCliente) {
        if (telefoneCliente == null || telefoneCliente.isEmpty()) {
            return;
        }
        if (idClienteValido()) {
            return;
        }
        try {
            Conectar.pesquisar("SELECT ID_CLIENTE FROM CLIENTES WHERE TELEFONE = '" + telefoneCliente + "'");
            if (Conectar.rs != null && Conectar.rs.next()) {
                this.id_cliente = Conectar.rs.getString("ID_CLIENTE");
            }
            if (Conectar.rs != null) {
                Conectar.rs.close();
            }
        } catch (SQLException e) {
            ProgramaGas.salvarErro(e.getMessage() + "  Local:  " + e.getLocalizedMessage());
            this.systemError.setText(e.toString());
        }
    }

    private boolean idClienteValido() {
        return this.id_cliente != null
                && !this.id_cliente.isEmpty()
                && !"null".equals(this.id_cliente);
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

            boolean pedidoSalvo = false;

            if(!ProgramaGas.servico) {
                if (!idClienteValido()) {
                    resolverIdClienteLocal(this.telefone);
                }
                if (!idClienteValido()) {
                    this.msgErro.setText("Cliente não identificado no banco. Cadastre ou recarregue o cliente.");
                    return;
                }

                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String hoje = dateFormat.format(date);
                String valorPedido = getValorFormatado(this.txtTotal.getText()).toPlainString();
                String sql = "INSERT INTO PEDIDOS (DIA,ID_CLIENTEp,STATUS,  PEDIDO, OBSERVACAO,entregador,formadepagamento,VALOR) VALUES ('" + hoje + "'," + this.id_cliente + ", 'Aberto', '" + pedido + "', '" + this.obsPedido + "','Funcionário','" + fDp + "', " + valorPedido + ")";

                try {
                    Conectar.alterar(sql);

                    String sql1 = "UPDATE CLIENTES set OBSERVACAO = '" + this.obsPedido + "' where id_cliente = " + this.id_cliente + "";
                    Conectar.alterar(sql1);

                    String sql2 = "Select * from PEDIDOS where id_clientep = " + this.id_cliente + " and dia = '" + hoje + "' and pedido = '" + pedido + "'";
                    Conectar.pesquisar(sql2);
                    if (Conectar.rs != null && Conectar.rs.next()) {
                        this.id_pedido = Conectar.rs.getString("id_pedido");
                        pedidoSalvo = this.id_pedido != null && !this.id_pedido.isEmpty();
                    }
                    if (Conectar.rs != null) {
                        Conectar.rs.close();
                    }
                    if (!pedidoSalvo) {
                        this.msgErro.setText("Pedido não foi gravado no banco. Verifique os dados e tente novamente.");
                    }
                } catch (SQLException e) {
                    ProgramaGas.salvarErro(e.getMessage() + "  Local:  " + e.getLocalizedMessage());
                    this.systemError.setText(e.toString());
                    this.msgErro.setText("Erro ao gravar pedido. Verifique o log.");
                }

            } else {

                PedidoDto pedidoDto = new PedidoDto();
                pedidoDto.setTelefoneCliente(this.telefone);
                pedidoDto.setNomeCliente(this.nome);
                pedidoDto.setCep(this.cep);
                pedidoDto.setLogradouro(this.logradouro);
                pedidoDto.setNumeroResidencia(this.numCasa);
                pedidoDto.setComplemento("Prox. " + this.referencia);
                pedidoDto.setBairro(this.bairro);
                pedidoDto.setObservacao(this.obsPedido);
                pedidoDto.setFormaPagamento(fDp);
                pedidoDto.setValorTotal( getValorFormatado(this.txtTotal.getText()));
                pedidoDto.setLoja(ProgramaGas.nomeLoja != null ? ProgramaGas.nomeLoja.trim() : "");
                pedidoDto.setProdutos(this.montarPedidoParaEnviar());

                Service service = new Service();
                PedidoServicoDto pedidoEnviado = service.enviarPedido(pedidoDto);

                if (pedidoEnviado != null && pedidoEnviado.getIdPedido() != null) {
                    this.id_pedido = pedidoEnviado.getIdPedido().toString();
                    pedidoSalvo = true;
                } else {
                    this.msgErro.setText("Erro ao enviar pedido para o serviço.");
                }

            }

            if (!pedidoSalvo) {
                return;
            }

            if(this.imprimirCheckBox.isSelected()) {
                DadosImpressaoDto dadosImpressaoDto = new DadosImpressaoDto();
                dadosImpressaoDto.setTelefone(telefone);
                dadosImpressaoDto.setNome(nome);
                dadosImpressaoDto.setLogradouro(logradouro);
                dadosImpressaoDto.setBairro(bairro);
                dadosImpressaoDto.setPedido(montarPedido());
                dadosImpressaoDto.setObs(obsPedido);
                dadosImpressaoDto.setFormaPagamento(this.formaPagamento.getSelectedItem().toString());
                dadosImpressaoDto.setTotal(this.txtTotal.getText());
                dadosImpressaoDto.setNumCasa(numCasa);
                dadosImpressaoDto.setTp_logradouro(tp_logradouro);

                ImpressoraUtils.reimprimir(dadosImpressaoDto);
            }
            Vendas.AddLinhaTabela(pedido, this.nome, end, fDp, this.telefone, this.id_pedido);
            this.dispose();
            Vendas.pedidosAberto();
        }

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
                Conectar.pesquisar(sql);
                if (Conectar.rs.next()) {
                    this.jTable2.setValueAt(Conectar.rs.getString("Valor"), i, 2);
                }
                Conectar.rs.close();
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

            this.txtTotal.setText(formatarTotalParaExibicao(total));
        }

    }

    private String formatarTotalParaExibicao(double total) {
        return String.format(new Locale("pt", "BR"), "%.2f", total);
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

    private List<PedidoProdutoDto> montarPedidoParaEnviar() {

        List<PedidoProdutoDto> retorno = new ArrayList<>();

        PedidoProdutoDto primeiroProduto = new PedidoProdutoDto();
        primeiroProduto.setNome((String) this.jTable2.getValueAt(0, 0));
        primeiroProduto.setQuantidade(Integer.valueOf((String) this.jTable2.getValueAt(0, 1)));

        primeiroProduto.setValor(getValorFormatado((String) this.jTable2.getValueAt(0, 2)));

        retorno.add(primeiroProduto);

        int linhas = this.jTable2.getRowCount();
        if (linhas > 1) {
            for(int i = 2; i <= linhas; ++i) {
                PedidoProdutoDto demaisProduto = new PedidoProdutoDto();
                demaisProduto.setNome((String) this.jTable2.getValueAt(i - 1, 0));
                demaisProduto.setQuantidade(Integer.valueOf((String) this.jTable2.getValueAt(0, 1)));
                demaisProduto.setValor(getValorFormatado((String) this.jTable2.getValueAt(i - 1, 2)));

                retorno.add(demaisProduto);
            }
        }

        return retorno;
    }

    private BigDecimal getValorFormatado(String valor){

        if(StringUtils.emptyOrNull(valor)){
            return BigDecimal.ZERO;
        }

        String valorFormatado = valor.replaceAll(",", ".");

        return new BigDecimal(valorFormatado.trim());

    }
}

