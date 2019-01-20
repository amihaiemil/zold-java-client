package com.amihaiemil.zold.request;

import com.amihaiemil.zold.client.ZoldClient;
import com.amihaiemil.zold.exception.ZoldException;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.time.Duration;

/**
 * Wait for job to be finished.
 *
 * @author Oleksandr Sankin (sankin.alex@gmail.com)
 */

public final class ZoldJobStatus extends AbstractZoldRequest<HttpGet> {

    /**
     * ZoldClient client.
     */
    private final ZoldClient client;

    /**
     * Duration duration.
     */
    private final Duration timeout;

    /**
     * Job walletId.
     */
    private final String job;

    /**
     * Constructor.
     *
     * @param client ZoldClient
     * @param job String
     * @param timeout Duration
     */
    public ZoldJobStatus(final ZoldClient client, final String job,
                         final Duration timeout) {
        super(client);

        this.timeout = timeout;
        this.client = client;
        this.job = job;
    }

    /**
     * Wait for job to be finished.
     *
     * @throws ZoldException exception
     */
    public void waitForJob() throws ZoldException {
        if (this.timeout.toMillis() < 500) {
            throw new ZoldException("Timeout should not "
                    + "be less than 500 millis");
        }

        final long start = System.nanoTime();

        do {
            this.checkTimeout(start);

            if (this.status().equals("OK")) {
                return;
            }

            this.waitSomeMillis();
        } while (true);
    }

    @Override
    HttpGet request() {
        HttpGet request = new HttpGet("/job?id=" + this.job);
        prepareHeaders(request);
        prepareRequestConfig(request);

        return request;
    }

    /**
     * Read job status from HTTP response body.
     *
     * @return String status - 'Running' or 'OK'
     * @throws ZoldException exception
     */
    private String status() throws ZoldException {
        HttpResponse response = this.client.execute(this.request());
        try {
            return IOUtils.toString(response.getEntity().getContent());
        } catch (final IOException error) {
            throw new ZoldException(error.getMessage(), error);
        }
    }

    /**
     * To avoid bombard the server with the hundreds of requests.
     *
     * @throws ZoldException exception
     */
    private void waitSomeMillis() throws ZoldException {
        try {
            Thread.sleep(500);
        } catch (final InterruptedException error) {
            throw new ZoldException(error.getMessage(), error);
        }
    }

    /**
     * Check timeout.
     *
     * @param start Start time
     * @throws ZoldException exception
     */
    private void checkTimeout(final long start) throws ZoldException {
        long end = System.nanoTime();
        if (end - start > this.timeout.toNanos()) {
            throw new ZoldException("Job " + this.job
                    + " wasn't complete in a " + this.timeout.getSeconds()
                    + " seconds");
        }
    }
}
