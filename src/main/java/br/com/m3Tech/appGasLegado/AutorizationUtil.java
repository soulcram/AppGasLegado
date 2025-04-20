package br.com.m3Tech.appGasLegado;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class AutorizationUtil {
    public AutorizationUtil(){};

    @Value("${user.service:soulcram}")
    private String user;

    @Value("${password.service:p4r4tud0}")
    private String password;

    public String getAutorization(){

        String userPass = "soulcram" + ":" + "p4r4tud0";

        byte[] encode = Base64.getEncoder().encode(userPass.getBytes(StandardCharsets.UTF_8));

        return "Basic " +  new String(encode, StandardCharsets.UTF_8);
    }
}
