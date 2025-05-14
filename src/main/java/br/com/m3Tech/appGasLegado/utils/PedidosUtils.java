package br.com.m3Tech.appGasLegado.utils;

import br.com.m3Tech.appGasLegado.dto.PedidoServicoDto;
import com.google.common.collect.Lists;

import java.util.List;

public class PedidosUtils {

    private static List<PedidoServicoDto> pedidos = Lists.newArrayList();

    public static List<PedidoServicoDto> getAllPedidos(){
        return pedidos;
    }

    public static void addPedido(PedidoServicoDto pedidoServicoDto){
        pedidos.add(pedidoServicoDto);
    }
}
