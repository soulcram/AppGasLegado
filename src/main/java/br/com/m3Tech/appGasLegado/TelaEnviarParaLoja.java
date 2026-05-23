package br.com.m3Tech.appGasLegado;

import br.com.m3Tech.appGasLegado.dto.AlterarLojaDto;
import br.com.m3Tech.appGasLegado.entity.Config;
import br.com.m3Tech.appGasLegado.service.ConfigService;
import br.com.m3Tech.utils.BooleanUtils;
import br.com.m3Tech.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import programagas.Mascaras;

import javax.comm.CommPortIdentifier;
import javax.print.DocFlavor;
import javax.print.DocFlavor.SERVICE_FORMATTED;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.AttributeSet;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Enumeration;

@Slf4j
public class TelaEnviarParaLoja extends JFrame {

    private JButton bEnviar;
    private JComboBox<String> boxLojas;
    private JLabel jLabelLojas;
    private JPanel jPanel1;
    private Integer idPedido;

    public TelaEnviarParaLoja(Integer idPedido) {
        try {
            this.initComponents();
            this.idPedido = idPedido;


            this.boxLojas.removeAllItems();
            this.boxLojas.addItem("Consigaz Piraju");
            this.boxLojas.addItem("Consigaz 3 Marias");
            this.boxLojas.addItem("Gasbom Cocaia");
            this.boxLojas.addItem("Consigaz 3 Coracoes");

        } catch (Exception var9) {
            log.error(var9.getMessage() + "  Local:  " + var9.getLocalizedMessage());
        }


    }

    private void initComponents() {
        this.jPanel1 = new JPanel();

        this.bEnviar = new JButton();

        this.setDefaultCloseOperation(2);
        this.setTitle("Configurações");

        this.jLabelLojas = new JLabel();
        this.jLabelLojas.setBounds(10,10,150,30);
        this.jLabelLojas.setFont(new Font("Tahoma", 1, 12));
        this.jLabelLojas.setText("Lojas Disponíveis");

        this.boxLojas = new JComboBox();
        this.boxLojas.setBorder((Border)null);
        this.boxLojas.setBounds(180,10,150,30);


        this.bEnviar.setText("Enviar");
        this.bEnviar.setBounds(180,110,100,30);
        this.bEnviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                bEnviarActionPerformed(evt);
            }
        });

        jPanel1.setBounds(10,10,420,400);
        jPanel1.setLayout(null);
        jPanel1.setBackground(Color.WHITE);

        jPanel1.add(jLabelLojas);
        jPanel1.add(boxLojas);

        jPanel1.add(bEnviar);

        this.setBounds(10, 10, 430, 400);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.add(jPanel1);
        this.repaint();

    }


    private void bEnviarActionPerformed(ActionEvent evt) {

        String lojaSelecionada = boxLojas.getSelectedItem().toString();

        if(StringUtils.emptyOrNull(lojaSelecionada)) {
            log.error("Nenhuma loja Selecionada");
            return;
        }

        if(lojaSelecionada.equals(ProgramaGas.nomeLoja)) {
            log.error("Loja Selecionada já é a loja atual");
            return;
        }

        AlterarLojaDto alterarLojaDto = new AlterarLojaDto();
        alterarLojaDto.setLoja(lojaSelecionada);
        alterarLojaDto.setIdPedido(this.idPedido);

        new Service().alterarLoja(alterarLojaDto);

        this.dispose();
    }


}

