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

import javax.json.Json;
import javax.json.JsonArray;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Provides a test case for the {@link RtWallet} operations.
 */
public final class RtWalletTestCase {

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

    /**
     * Checks whether the {@link RtWallet#balance()} invocation succeeds.
     * @throws Exception If an exception is thrown
     */
    @Test
    public void getsBalance() throws Exception {
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
                            return (BASE_URI + "/balance")
                                .equals(r.getUri().toString());
                        } catch (final URISyntaxException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                )
            )
        );
        MatcherAssert.assertThat(
            "Wallet balance is invalid",
            new RtWallet(
                httpClient,
                URI.create(BASE_URI)
            ).balance(),
            Matchers.equalTo("12345")
        );
    }

    /**
     * Checks whether the {@link RtWallet#find(Map)} invocation
     * succeeds.
     * @throws Exception If an exception is thrown
     */
    @Test
    public void findsWalletTransactions() throws Exception {
        final JsonArray payload = Json.createArrayBuilder()
            .add(
                Json.createObjectBuilder()
                    .add("id", "1")
                    .add("date", "2019-01-26T11:05:17Z")
                    .add("amount", "17")
                    .add("bnf", "72e0b23d95a983d6")
                    .add("details", "Hosting bonus")
            )
            .build();
        final ClassicHttpResponse response = new Response(
            HttpStatus.SC_OK,
            ContentType.APPLICATION_JSON,
            payload.toString()
        );
        final HttpClient httpClient = new MockHttpClient(
            new AssertRequest(
                response,
                new Condition(
                    "Request path is invalid",
                    r -> {
                        try {
                            System.out.println(r.getUri().toString());
                            return (BASE_URI + "/find?amount=17&"
                                + "details=Hosting+bonus&bnf=72e0b23d95a983d6")
                                .equals(r.getUri().toString());
                        } catch (final URISyntaxException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                )
            )
        );
        MatcherAssert.assertThat(
            "Wallet find operation failed",
            new RtWallet(
                httpClient,
                URI.create(BASE_URI)
            ).find(
                Stream.of(
                    new String[][] {
                        {"amount", "17"},
                        {"details", "Hosting bonus"},
                        {"bnf", "72e0b23d95a983d6"},
                    }
                ).collect(
                    Collectors.toMap(e -> e[0], e -> e[1])
                )
            ),
            Matchers.equalTo(payload)
        );
    }

    /**
     * Checks whether the {@link RtWallet#pay(String, String, String, String)} invocation
     * succeeds.
     * @throws Exception If an exception is thrown
     */
    @Test
    public void pays() throws Exception  {
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
                                        return (BASE_URI + "/do-pay")
                                                .equals(r.getUri().toString());
                                    } catch (final URISyntaxException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }
                        )
                )
        );
        new RtWallet(
                httpClient,
                URI.create(BASE_URI)
        ).pay("a", "b", "1", "d");
    }
}
