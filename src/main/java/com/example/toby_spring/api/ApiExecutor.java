package com.example.toby_spring.api;

import java.io.IOException;
import java.net.URI;

public interface ApiExecutor {
    String execute(final URI uri) throws IOException;
}
