package br.com.m3Tech.appGasLegado.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Config {
    String impressora;
    String portaCom;
    String iniTel;
    String fimTel;
    String contextService;
    String urlService;
    String nomeloja;
    String data;
}
