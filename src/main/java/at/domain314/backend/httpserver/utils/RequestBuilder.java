package at.domain314.backend.httpserver.utils;
import at.domain314.backend.httpserver.server.Request;
import at.domain314.backend.httpserver.http.Method;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Locale;

public class RequestBuilder {
    public Request buildRequest(BufferedReader bufferedReader) throws IOException {
        Request request = new Request();
        String line = bufferedReader.readLine();
        System.out.println(line);
        if (line != null) {
            String[] splitFirstLine = line.split(" ");

            request.setMethod(getMethod(splitFirstLine[0]));
            request.setUrlContent(splitFirstLine[1]);

            line = bufferedReader.readLine();
            System.out.println(line);
            while (!line.isEmpty()) {
                request.getHeaderMap().ingest(line);
                line = bufferedReader.readLine();
                System.out.println(line);
            }

            if (request.getHeaderMap().getContentLength() > 0) {
                char[] charBuffer = new char[request.getHeaderMap().getContentLength()];
                bufferedReader.read(charBuffer, 0, request.getHeaderMap().getContentLength());

                request.setBody(new String(charBuffer));
            }
        }
        return request;
    }

    private Method getMethod(String methodString) {
        return Method.valueOf(methodString.toUpperCase(Locale.ROOT));
    }

}
