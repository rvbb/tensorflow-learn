package vn.rvbb.tf.practice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
public class InOutLogInterceptorConfig implements ClientHttpRequestInterceptor {

    public static final String API_3RD="API_3RD";

    public static final String API_INTERNAL="API_INTERNAL";

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        logResponse(response);
        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) throws IOException {
        if (log.isDebugEnabled()) {
            log.debug("Intercept Request URI: {}, Method: {}, Headers: {}, Request_Body: {}",
                    request.getURI(), request.getMethod(), request.getHeaders(), new String(body, StandardCharsets.UTF_8));
        }
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
        if (log.isDebugEnabled()) {
            log.debug("Intercept Response Status_Code: {}, Status_Text: {}, Headers: {}, Response_Body: {}",
                    response.getStatusCode(), response.getStatusText(), response.getHeaders(),
                    StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
        }
    }
}