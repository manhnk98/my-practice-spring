package com.nkm.mypracticespring.exceptions.handler;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is5xxServerError() ||
                response.getStatusCode().is4xxClientError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is5xxServerError()) {
            throw new HttpClientErrorException(response.getStatusCode());
        } else if (response.getStatusCode().is4xxClientError()) {
            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                try {
                    throw new ChangeSetPersister.NotFoundException();
                } catch (ChangeSetPersister.NotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
