package com.example.toby_spring.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientExecutor implements ApiExecutor{
    @Override
    public String execute(final URI uri) throws IOException {
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        // String 타입의 HTTP BODY를 return
        try {
            return HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
