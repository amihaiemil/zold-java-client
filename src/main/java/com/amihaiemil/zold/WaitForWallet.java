/**
 * Copyright (c) 2019, Mihai Emil Andronache
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1)Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * 2)Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 3)Neither the name of zold-java-client nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.amihaiemil.zold;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.Instant;

/**
 * Fetch a wallet, after checking the job's status.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 * @version $Id$
 * @since 0.0.1
 */
final class WaitForWallet implements ResponseHandler<Wallet> {

    /**
     * Handlers to be executed before actually reading the array.
     */
    private final ResponseHandler<HttpResponse> other;

    /**
     * API client.
     */
    private final HttpClient client;

    /**
     * Base uri.
     */
    private final URI baseUri;

    /**
     * Time to wait for job to be finished.
     */
    private static final Duration TIME_TO_WAIT = Duration.ofMinutes(5);

    /**
     * Ctor.
     *
     * @param other Handlers to be executed before actually reading the array.
     * @param client API Http client.
     * @param baseUri Base URI.
     */
    WaitForWallet(
        final ResponseHandler<HttpResponse> other,
        final HttpClient client,
        final URI baseUri
    ) {
        this.other = other;
        this.client = client;
        this.baseUri = baseUri;
    }

    @Override
    public Wallet handleResponse(final HttpResponse httpResponse)
        throws IOException {
        final HttpResponse resp = this.other.handleResponse(httpResponse);
        final String jobId = resp.getFirstHeader("X-Zold-Job").getValue();
        if(jobId == null || jobId.isEmpty()) {
            throw new IllegalStateException(
                "X-Zold-Job header expected on response " + httpResponse
            );
        } else {
            try {
                final Instant start = Instant.now();

                do {
                    Thread.sleep(1000);

                    if (this.jobFinished(jobId)) {
                        break;
                    }

                    if (this.hasNoTimeToWait(start)) {
                        throw new IllegalStateException(
                                "Can't wait for job " + jobId + " during "
                                + TIME_TO_WAIT.toMinutes() + " minutes");
                    }
                } while (true);

                return new RtWallet(this.client, this.baseUri);
            } catch (final InterruptedException | URISyntaxException ex) {
                throw new IllegalStateException(
                        "Exception while waiting for Wallet!", ex
                );
            }
        }
    }

    /**
     * Calculates if the job finished.
     *
     * @param jobId Job id
     * @return False if the job was not finished. True if the job was finished.
     * @throws IOException in case of some network issue
     * @throws URISyntaxException in case of wrong url formatting
     */
    private boolean jobFinished(final String jobId)
            throws IOException, URISyntaxException {
        final String status = this.jobStatus(jobId);
        final boolean result;

        if ("Running".equals(status)) {
            result = false;
        } else if ("OK".equals(status)) {
            result = true;
        } else {
            throw new IllegalStateException(
                    "Got unknown job status from Zold network: "
                            + status + ", jobId = " + jobId);
        }

        return result;
    }

    /**
     * Returns job status.
     *
     * @param jobId Job id.
     *
     * @return Http response with job status
     * @throws URISyntaxException in case of wrong url formatting
     * @throws IOException in case of some network issue
     */
    private String jobStatus(final String jobId)
            throws URISyntaxException, IOException {
        final URIBuilder uri = new URIBuilder(
                this.baseUri.toString() + "/job"
        ).addParameter("id", jobId);
        final HttpGet status = new HttpGet(uri.build());
        return this.client.execute(
                status,
                new ReadString(
                        new MatchStatus(
                                status.getURI(),
                                HttpStatus.SC_OK
                        )
                )
        );
    }

    /**
     * Calculates if there is no time to wait.
     *
     * @param start Initial start time
     * @return False if there is time to wait. True - if not
     */
    private boolean hasNoTimeToWait(final Instant start) {
        final Instant finish = start.plus(TIME_TO_WAIT);
        return Instant.now().isAfter(finish);
    }
}