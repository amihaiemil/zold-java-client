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

import com.amihaiemil.zold.client.ZoldClient;
import com.amihaiemil.zold.exception.ZoldException;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.time.Duration;

import static org.junit.Assert.*;

/**
 * Unit tests for {@link RestfulZoldWts}.
 *
 * @author Mihai Andronache (amihaiemil@gmail.com)
 * @version $Id$
 * @since 0.0.1
 */
public final class RestfulWtsTestCase {

    /**
     * Job Id.
     */
    private static final String JOB_ID = "123-456-789";

    /**
     * {@link RestfulZoldWts} can be instantiated.
     */
    @Test
    public void isInstantiated() {
        MatcherAssert.assertThat(
                new RestfulZoldWts("key"),
                Matchers.instanceOf(ZoldWts.class)
        );
    }

    /**
     * Check that job id is received.
     *
     * @throws ZoldException exception.
     */
    @Test
    public void jobIdIsReceived() throws ZoldException {
        ZoldClient mockClient = new ZoldClient() {

            @Override
            public HttpResponse execute(final HttpRequest request) {
                RestfulWtsTestCase.this.assertHeaders(request);

                BasicHttpResponse response = new BasicHttpResponse(
                        new BasicStatusLine(HttpVersion.HTTP_1_1, 302, ""));
                response.setHeader("X-Zold-Job", JOB_ID);

                return response;
            }

            @Override
            public String key() {
                return "key";
            }
        };

        String jobId = new RestfulZoldWts(mockClient).pull();
        assertEquals("123-456-789", jobId);
    }

    /**
     * Check the case when header is absent.
     */
    @Test
    public void jobIdHeaderIsAbsent() {
        ZoldClient mockClient = new ZoldClient() {

            @Override
            public HttpResponse execute(final HttpRequest request) {
                RestfulWtsTestCase.this.assertHeaders(request);

                return new BasicHttpResponse(
                        new BasicStatusLine(HttpVersion.HTTP_1_1, 302, ""));
            }

            @Override
            public String key() {
                return "key";
            }
        };

        try {
            new RestfulZoldWts(mockClient).pull();
            fail("Exception expected");
        } catch (final ZoldException error) {
            assertTrue(error.toString().contains(
                    "No X-Zold-Job header found among"));
        }
    }

    /**
     * Check the case when job is timeout.
     */
    @Test
    public void waitForJobBadTimeout() {
        ZoldClient mockClient = new ZoldClient() {
            @Override
            public HttpResponse execute(final HttpRequest request) {
                RestfulWtsTestCase.this.assertHeaders(request);

                return RestfulWtsTestCase.this.createHttpResponse("");
            }

            @Override
            public String key() {
                return "key";
            }
        };

        try {
            new RestfulZoldWts(mockClient)
                    .waitForJob(JOB_ID, Duration.ofMillis(200));
            fail("Exception expected");
        } catch (final ZoldException error) {
            assertTrue(error.toString().contains(
                    "Timeout should not be less than 500 millis"));
        }
    }

    /**
     * Check the case when job finish successfully.
     *
     * @throws ZoldException exception.
     */
    @Test
    public void waitForJobSuccess() throws ZoldException {
        ZoldClient mockClient = new ZoldClient() {
            private int count;
            @Override
            public HttpResponse execute(final HttpRequest request) {
                HttpResponse response;
                RestfulWtsTestCase.this.assertHeaders(request);
                if (++count < 2) {
                    response = RestfulWtsTestCase.this
                            .createHttpResponse("Running");
                } else {
                    response = RestfulWtsTestCase.this
                            .createHttpResponse("OK");
                }
                return response;
            }
            @Override
            public String key() {
                return "key";
            }
        };

        new RestfulZoldWts(mockClient).waitForJob(JOB_ID,
                Duration.ofSeconds(5));
    }

    /**
     * Check the case when the job is not finish.
     */
    @Test
    public void waitForJobTooLong() {

        ZoldClient mockClient = new ZoldClient() {
            @Override
            public HttpResponse execute(final HttpRequest request) {
                RestfulWtsTestCase.this.assertHeaders(request);
                return RestfulWtsTestCase.this.createHttpResponse("Running");
            }

            @Override
            public String key() {
                return "key";
            }
        };

        try {

            new RestfulZoldWts(mockClient).waitForJob(JOB_ID,
                    Duration.ofSeconds(2));
            fail("Exception expected");

        } catch (final ZoldException error) {
            assertTrue(error.toString().contains(
                    "Job 123-456-789 wasn't complete in a 2 seconds"));
        }
    }

    /**
     * Get wallet Id.
     *
     * @throws ZoldException exception.
     */
    @Test
    public void walletId() throws ZoldException {
        ZoldClient mockClient = new ZoldClient() {
            @Override
            public HttpResponse execute(final HttpRequest request) {
                RestfulWtsTestCase.this.assertHeaders(request);
                return RestfulWtsTestCase.this
                        .createHttpResponse("walletId_555");
            }

            @Override
            public String key() {
                return "key";
            }
        };

        String walletId = new RestfulZoldWts(mockClient).walletId();
        assertEquals("walletId_555", walletId);
    }

    /**
     * Create HTTP response.
     *
     * @param body String body
     * @return HttpResponse httpResponse
     */
    private HttpResponse createHttpResponse(final String body) {
        BasicHttpResponse response = new BasicHttpResponse(
                new BasicStatusLine(HttpVersion.HTTP_1_1, 200, ""));
        BasicHttpEntity entity = new BasicHttpEntity();
        entity.setContent(new ByteArrayInputStream(body.getBytes()));
        response.setEntity(entity);
        return response;
    }

    /**
     * Assert headers.
     *
     * @param request HttpRequest
     */
    private void assertHeaders(final HttpRequest request) {
        assertNotNull(request.getFirstHeader(HttpHeaders.CONTENT_TYPE));
        assertNotNull(request.getFirstHeader(HttpHeaders.USER_AGENT));
        assertNotNull(request.getFirstHeader("X-Zold-Wts"));
    }
}
