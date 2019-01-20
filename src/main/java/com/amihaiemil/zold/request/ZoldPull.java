package com.amihaiemil.zold.request;

import com.amihaiemil.zold.client.ZoldClient;
import com.amihaiemil.zold.exception.ZoldException;
import org.apache.http.client.methods.HttpGet;

/**
 * Pull request for Zold network.
 *
 * @author Oleksandr Sankin (sankin.alex@gmail.com)
 */

public final class ZoldPull extends AbstractZoldRequest<HttpGet> {

    /**
     * ZoldClient client.
     */
    private final ZoldClient client;

    /**
     * ZoldJobId zoldJobId.
     */
    private final ZoldJobId zoldJobId;

    /**
     * Constructor.
     *
     * @param client Client
     * @param zoldJobId ZoldJobId
     */
    public ZoldPull(final ZoldClient client, final ZoldJobId zoldJobId) {
        super(client);

        this.client = client;
        this.zoldJobId = zoldJobId;
    }

    /**
     * Return job walletId.
     * @return Job walletId
     * @throws ZoldException exception
     */
    public String jobId() throws ZoldException {
        return this.zoldJobId.jobId(this.client.execute(this.request()));
    }

    @Override
    HttpGet request() {
        HttpGet request = new HttpGet("/pull");
        prepareHeaders(request);
        prepareRequestConfig(request);

        return request;
    }
}
