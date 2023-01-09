package at.domain314.backend.httpserver.server;

import at.domain314.backend.httpserver.http.ContentType;
import at.domain314.backend.httpserver.http.HttpStatus;
import at.domain314.utils.Constants;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Response {
    private int status;
    private String message;
    private String contentType;
    private String content;

    public Response(HttpStatus httpStatus, ContentType contentType, String content) {
        this.status = httpStatus.code;
        this.message = httpStatus.message;
        this.contentType = contentType.type;
        this.content = content;
    }

    public Response(String content, boolean isOk) {
        this.status = HttpStatus.OK.code;
        this.message = HttpStatus.OK.message;
        this.contentType = ContentType.JSON.type;
        this.content = content;
    }

    public Response(String content) {
        this.status = HttpStatus.BAD_REQUEST.code;
        this.message = HttpStatus.BAD_REQUEST.message;
        this.contentType = ContentType.JSON.type;
        this.content = content;
    }

    public Response() {
        this.status = HttpStatus.BAD_REQUEST.code;
        this.message = HttpStatus.BAD_REQUEST.message;
        this.contentType = ContentType.JSON.type;
        this.content = Constants.RESPONSE_BAD_ERROR;
    }

    public Response(boolean isBadRequest) {
        this.status = HttpStatus.BAD_REQUEST.code;
        this.message = HttpStatus.BAD_REQUEST.message;
        this.contentType = ContentType.JSON.type;
        this.content = Constants.RESPONSE_BAD_REQUEST;
    }

    public String get() {

        String localDatetime = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneId.of("UTC")));
        return "HTTP/1.1 " + this.status + " " + this.message + "\r\n" +
                "Cache-Control: max-age=0\r\n" +
                "Connection: close\r\n" +
                "Date: " + localDatetime + "\r\n" +
                "Expires: " + localDatetime + "\r\n" +
                "Content-Type: " + this.contentType + "\r\n" +
                "Content-Length: " + this.content.length() + "\r\n" +
                "\r\n" +
                this.content;
    }
}