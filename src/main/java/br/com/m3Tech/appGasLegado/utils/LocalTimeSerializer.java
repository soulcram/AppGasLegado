package br.com.m3Tech.appGasLegado.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeSerializer implements JsonSerializer<LocalTime> {

    @Override
    public JsonElement serialize(LocalTime src, Type typeOfSrc, JsonSerializationContext context) {
        // Define o formato da data como "yyyy-MM-dd"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return new JsonPrimitive(src.format(formatter));
    }
}
