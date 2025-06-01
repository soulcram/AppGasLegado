package br.com.m3Tech.appGasLegado;

import br.com.m3Tech.appGasLegado.dto.AlterarEntregadorDto;
import br.com.m3Tech.appGasLegado.dto.AlterarLojaDto;
import br.com.m3Tech.appGasLegado.dto.EntregadorDisponivelDto;
import br.com.m3Tech.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

@Slf4j
public class TelaEnviarParaEntregador extends JFrame {

    private JButton bEnviar;
    private JComboBox<String> boxEntregadores;
    private JLabel jLabelEntregadores;
    private JPanel jPanel1;
    private Integer idPedido;

    public TelaEnviarParaEntregador(Integer idPedido) {
        try {
            this.initComponents();
            this.idPedido = idPedido;
            carregarBoxEntregadores();
        } catch (Exception var9) {
            log.error(var9.getMessage() + "  Local:  " + var9.getLocalizedMessage());
        }


    }

    private void carregarBoxEntregadores() {
        this.boxEntregadores.removeAllItems();
        List<EntregadorDisponivelDto> entregadores = new Service().getEntregadores(idPedido);

        for(EntregadorDisponivelDto item : entregadores){
            this.boxEntregadores.addItem(item.toString());
        }

    }

    private void initComponents() {
        this.jPanel1 = new JPanel();

        this.bEnviar = new JButton();

        this.setDefaultCloseOperation(2);
        this.setTitle("Configurações");

        this.jLabelEntregadores = new JLabel();
        this.jLabelEntregadores.setBounds(10,10,150,30);
        this.jLabelEntregadores.setFont(new Font("Tahoma", 1, 12));
        this.jLabelEntregadores.setText("Entregadores Disponíveis");

        this.boxEntregadores = new JComboBox();
        this.boxEntregadores.setBorder((Border)null);
        this.boxEntregadores.setBounds(180,10,350,30);


        this.bEnviar.setText("Enviar");
        this.bEnviar.setBounds(180,110,100,30);
        this.bEnviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                bEnviarActionPerformed(evt);
            }
        });

        jPanel1.setBounds(10,10,620,400);
        jPanel1.setLayout(null);
        jPanel1.setBackground(Color.WHITE);

        jPanel1.add(jLabelEntregadores);
        jPanel1.add(boxEntregadores);

        jPanel1.add(bEnviar);

        this.setBounds(10, 10, 630, 400);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.add(jPanel1);
        this.repaint();

    }


    private void bEnviarActionPerformed(ActionEvent evt) {

        String entregadorSelecionado = boxEntregadores.getSelectedItem().toString();

        if(StringUtils.emptyOrNull(entregadorSelecionado)) {
            log.error("Nenhuma entregador Selecionada");
            return;
        }

        String[] split = entregadorSelecionado.split(" \\| ");

        AlterarEntregadorDto alterarEntregadorDto = new AlterarEntregadorDto();
        alterarEntregadorDto.setIdPedido(this.idPedido);
        alterarEntregadorDto.setIdEntregador(Integer.valueOf(split[0]));
        alterarEntregadorDto.setNomeEntregador(split[1]);

        new Service().alterarEntregador(alterarEntregadorDto);
        ProgramaGas.atualizarTabelaPedidos = true;
        this.dispose();
    }


}

