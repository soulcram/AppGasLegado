package br.com.m3Tech.appGasLegado.utils;

import br.com.m3Tech.appGasLegado.ProgramaGas;
import br.com.m3Tech.appGasLegado.dto.DadosImpressaoDto;
import br.com.m3Tech.appGasLegado.service.PedidoService;
import br.com.m3Tech.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import javax.print.*;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import java.awt.event.ActionEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

@Slf4j
public class ImpressoraUtils {
    public static String gerarNotaTermica(DadosImpressaoDto dadosImpressaoDto) {
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
        nota = nota + dadosImpressaoDto.getTelefone() + System.getProperty("line.separator");
        nota = nota + "                                " + System.getProperty("line.separator");
        nota = nota + dadosImpressaoDto.getNome() + System.getProperty("line.separator");
        nota = nota + "                                " + System.getProperty("line.separator");
        nota = nota + dadosImpressaoDto.getTp_logradouro() + ": " + dadosImpressaoDto.getLogradouro() + ", " + dadosImpressaoDto.getNumCasa() + System.getProperty("line.separator");
        nota = nota + "                                " + System.getProperty("line.separator");
        nota = nota + dadosImpressaoDto.getBairro() + System.getProperty("line.separator");
        nota = nota + "                                " + System.getProperty("line.separator");
        nota = nota + dadosImpressaoDto.getObs() + System.getProperty("line.separator");
        nota = nota + "                                " + System.getProperty("line.separator");
        nota = nota + dadosImpressaoDto.getPedido() + System.getProperty("line.separator");
        nota = nota + "                                " + System.getProperty("line.separator");
        nota = nota + "Total: " + dadosImpressaoDto.getTotal() + System.getProperty("line.separator");
        nota = nota + "                                " + System.getProperty("line.separator");
        nota = nota + dadosImpressaoDto.getFormaPagamento() + System.getProperty("line.separator") + "--------------------------------" + System.getProperty("line.separator");
        nota = nota + "                                " + System.getProperty("line.separator") ;
        nota = nota + "                                " + System.getProperty("line.separator") ;
        nota = nota + "                                " + System.getProperty("line.separator") ;
        nota = nota + "                                " + System.getProperty("line.separator") ;
        nota = nota + "                                " + System.getProperty("line.separator") ;
        nota = nota + "                                " + System.getProperty("line.separator") ;
        return nota;
    }

    public static String SemAcento(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public static void reimprimirPedido(Integer idPedido) {

        DadosImpressaoDto dadosImpressaoDto = new PedidoService().getPedido(idPedido);

        reimprimir(dadosImpressaoDto);

    }

    public static void reimprimir(DadosImpressaoDto dadosImpressaoDto) {
        try {
            DocFlavor df = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
            PrintService[] printServices = PrintServiceLookup.lookupPrintServices(df, (AttributeSet)null);

            if(StringUtils.emptyOrNull(ProgramaGas.Impressora)){
                log.error("nenhuma Impressora Configurada como padrão.");
                return;
            }

            PrintService impressoraSelecionada = null;

            for(PrintService ps : printServices){
                if(ps.getName().equals(ProgramaGas.Impressora)){
                    impressoraSelecionada = ps;
                    break;
                }
            }

            if(impressoraSelecionada == null){
                log.error("nenhuma Impressora Configurada como padrão.");
                return;
            }

            String textoNota = SemAcento(gerarNotaTermica(dadosImpressaoDto));

            byte[] corte = new byte[]{0x1B, 'i'};


            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(textoNota.getBytes("CP437"));
            outputStream.write(corte);

            InputStream stream = new ByteArrayInputStream(outputStream.toByteArray());

            DocPrintJob dpj = impressoraSelecionada.createPrintJob();
            DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
            Doc documentoTexto = new SimpleDoc(stream, flavor, (DocAttributeSet)null);


            dpj.print(documentoTexto, (PrintRequestAttributeSet)null);
        } catch (Exception var18) {
            log.error(var18.getMessage() + "  Local:  " + var18.getLocalizedMessage());
        }

    }
}
