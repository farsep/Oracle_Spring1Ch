package com.frx.literalura.service;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

@Service
public class Http {
    private HttpClient client = HttpClient.newHttpClient();

    public CompletableFuture<String> getData(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body);
    }
}