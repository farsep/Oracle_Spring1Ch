package com.frx.literalura.service;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;

@Service
public class GsonService {
    private Gson gson = new Gson();

    public String toJson(Object object) {
        return gson.toJson(object);
    }

    public <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }
}