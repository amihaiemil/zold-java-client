/*
  Copyright (c) 2019, Mihai Emil Andronache
  All rights reserved.
  Redistribution and use in source and binary forms, with or without
  modification, are permitted provided that the following conditions are met:
  1)Redistributions of source code must retain the above copyright notice,
  this list of conditions and the following disclaimer.
  2)Redistributions in binary form must reproduce the above copyright notice,
  this list of conditions and the following disclaimer in the documentation
  and/or other materials provided with the distribution.
  3)Neither the name of zold-java-client nor the names of its
  contributors may be used to endorse or promote products derived from
  this software without specific prior written permission.
  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
  POSSIBILITY OF SUCH DAMAGE.
 */
package com.amihaiemil.zold;

import com.amihaiemil.zold.mock.AssertRequest;
import com.amihaiemil.zold.mock.Condition;
import com.amihaiemil.zold.mock.MockHttpClient;
import com.amihaiemil.zold.mock.Response;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpStatus;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Provides a test case for the {@link RtWallet} operations.
 */
public class RtWalletTestCase {

    /**
     * A base URI for testing.
     */
    private static final String BASE_URI = "http://wts.test.com";

    /**
     * Checks whether the wallet can be instantiated.
     */
    @Test
    public void instantiates() {
        new RtWallet(
            new MockHttpClient(),
            URI.create(BASE_URI)
        );
    }

    /**
     * Checks whether the {@link RtWallet#getId()} invocation succeeds.
     * @throws Exception If an exception is thrown
     */
    @Test
    public void getsId() throws Exception {
        final ClassicHttpResponse response = new Response(
            HttpStatus.SC_OK,
            ContentType.TEXT_PLAIN,
            "12345"
        );
        final HttpClient httpClient = new MockHttpClient(
            new AssertRequest(
                response,
                new Condition(
                    "Request path is invalid",
                    r -> {
                        try {
                            return (BASE_URI + "/id")
                                .equals(r.getUri().toString());
                        } catch (final URISyntaxException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                )
            )
        );
        MatcherAssert.assertThat(
            "Wallet ID is invalid",
            new RtWallet(
                httpClient,
                URI.create(BASE_URI)
            ).getId(),
            Matchers.equalTo("12345")
        );
    }
}
