package com.amihaiemil.zold.client;

import com.amihaiemil.zold.exception.ZoldException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

import java.io.IOException;

/**
 * Zold client.
 *
 * @author Oleksandr Sankin (sankin.alex@gmail.com)
 */

public class NetworkZoldClient implements ZoldClient {

    /**
     * Http client.
     */
    private final HttpClient client;

    /**
     * Http host.
     */
    private final HttpHost host;

    /**
     * API key.
     */
    private final String key;

    /**
     * Constructor.
     *  @param client HttpClient
     * @param host HttpHost
     * @param key API key
     */
    public NetworkZoldClient(final HttpClient client, final HttpHost host,
                             final String key) {
        this.client = client;
        this.host = host;
        this.key = key;
    }

    /**
     * Execute request.
     *
     * @param request HttpRequest.
     * @return HttpResponse.
     * @throws ZoldException exception.
     */
    public final HttpResponse execute(final HttpRequest request)
            throws ZoldException {
        try {
            return this.client.execute(this.host, request);
        } catch (final IOException error) {
            throw new ZoldException(error.getMessage(), error);
        }
    }

    /**
     * Return API key.
     *
     * @return API key
     */
    public final String key() {
        return this.key;
    }
}
