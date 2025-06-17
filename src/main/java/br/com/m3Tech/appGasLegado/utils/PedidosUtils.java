package br.com.m3Tech.appGasLegado.utils;

import br.com.m3Tech.appGasLegado.dto.PedidoDto;
import br.com.m3Tech.appGasLegado.dto.PedidoProdutoDto;
import br.com.m3Tech.appGasLegado.dto.PedidoServicoDto;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

public class PedidosUtils {

    private static List<PedidoServicoDto> pedidos = Lists.newArrayList();

    public static List<PedidoServicoDto> getAllPedidos(){
        return pedidos;
    }

    public static void addPedido(PedidoServicoDto pedidoServicoDto){
        pedidos.add(pedidoServicoDto);
    }

    public static String getPedidosString(List<PedidoProdutoDto> lista){
        return lista.stream()
                .map(p -> p.getQuantidade() + " - " + p.getNome())
                .collect(Collectors.joining("; "));
    }
}
