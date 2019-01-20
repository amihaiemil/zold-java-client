package com.amihaiemil.zold.client;

import com.amihaiemil.zold.exception.ZoldException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

/**
 * Interface for Zold client.
 *
 * @author Oleksandr Sankin (sankin.alex@gmail.com)
 */

public interface ZoldClient {

    /**
     * Execute request.
     *
     * @param request HttpRequest.
     * @return HttpResponse.
     * @throws ZoldException exception.
     */
    HttpResponse execute(HttpRequest request) throws ZoldException;

    /**
     * Return API key.
     *
     * @return API key
     */
    String key();
}
