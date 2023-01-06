package at.domain314.backend.httpserver.server;

public interface Service {
    Response handleRequest(Request request);
}
