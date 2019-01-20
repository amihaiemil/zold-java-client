package com.amihaiemil.zold.request;

import com.amihaiemil.zold.client.ZoldClient;
import com.amihaiemil.zold.exception.ZoldException;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;

/**
 * Return Zold wallet walletId.
 *
 * @author Oleksandr Sankin (sankin.alex@gmail.com)
 */

public class ZoldWalletId extends AbstractZoldRequest<HttpGet> {

    /**
     * Zold client.
     */
    private final ZoldClient client;

    /**
     * Constructor.
     *
     * @param client ZoldClient
     */
    public ZoldWalletId(final ZoldClient client) {
        super(client);

        this.client = client;
    }

    /**
     * Return wallet walletId.
     *
     * @return Wallet walletId.
     * @throws ZoldException exception.
     */
    public final String walletId() throws ZoldException {
        try {
            return IOUtils.toString(this.client.execute(
                    this.request()).getEntity().getContent());
        } catch (final IOException error) {
            throw new ZoldException(error.getMessage(), error);
        }
    }

    /**
     * Build HttpGet request.
     *
     * @return HttpGet.
     */
    @Override
    final HttpGet request() {
        HttpGet request = new HttpGet("/id");
        prepareHeaders(request);
        prepareRequestConfig(request);

        return request;
    }
}
