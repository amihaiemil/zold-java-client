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

import org.apache.hc.core5.net.URIBuilder;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import javax.json.Json;
import javax.json.JsonArray;

/**
 * RESTful Zold wallet.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 * @version $Id$
 * @since 0.0.1
 * @todo #34:30min Write unit tests for uncovered wallet operations.
 * @checkstyle ParameterNumber (200 lines)
 */
final class RtWallet implements Wallet {

    /**
     * API client.
     */
    private final HttpClient client;

    /**
     * Base uri.
     */
    private final URI baseUri;

    /**
    * Valid params in find.
    */
    private static final Set<String> VALID_FIND_PARAMS =
            Collections.unmodifiableSet(
                new HashSet<>(
                        Arrays.asList(
                            "id", "date", "amount", "prefix", "bnf", "details"
                        )
                )
            );

    /**
     * Ctor.
     * @param client API Client.
     * @param baseUri URI.
     */
    RtWallet(final HttpClient client, final URI baseUri) {
        this.client = client;
        this.baseUri = baseUri;
    }

    @Override
    public String getId() throws IOException, URISyntaxException {
        final HttpGet walletId = new HttpGet(
            URI.create(this.baseUri.toString() + "/id")
        );
        try {
            return this.client.execute(
                walletId,
                new ReadString(
                    new MatchStatus(
                        walletId.getUri(),
                        HttpStatus.SC_OK
                    )
                )
            );
        } finally {
            walletId.reset();
        }
    }

    @Override
    public String balance() throws IOException, URISyntaxException {
        final HttpGet walletBalance = new HttpGet(
            URI.create(this.baseUri.toString() + "/balance")
        );
        try {
            return this.client.execute(
                walletBalance,
                new ReadString(
                    new MatchStatus(
                        walletBalance.getUri(),
                        HttpStatus.SC_OK
                    )
                )
            );
        } finally {
            walletBalance.reset();
        }
    }

    @Override
    public void pay(
        final String keygap, final String user,
        final String amount, final String details
    ) throws IOException, URISyntaxException {
        
        final HttpPost request = new HttpPost(
            URI.create(this.baseUri.toString() + "/do-pay")
        );
        String payload = Json.createObjectBuilder()
            .add("keygap", keygap)
            .add("bnf", user)
            .add("amount", amount)
            .add("details", details)
            .build().toString();
        request.setEntity(new StringEntity(payload.toString(),
             ContentType.APPLICATION_JSON));
        try {
            this.client.execute(
                request,
                new ReadString(
                    new MatchStatus(
                        request.getUri(),
                        HttpStatus.SC_MOVED_TEMPORARILY
                    )
                )
            );
        } finally {
            request.reset();
        }
    }

    @Override
    public JsonArray find(final Map<String, String> params)
            throws IOException, URISyntaxException {
        URIBuilder builder = new URIBuilder(this.baseUri.toString() + "/find");

        for (final String key: params.keySet()) {
            if(VALID_FIND_PARAMS.contains(key)) {
                builder.setParameter(key, params.get(key));
            }
        }

        final HttpGet request = new HttpGet(
                builder.build()
        );
        try {
            return this.client.execute(
                    request,
                    new ReadJsonArray(
                            new MatchStatus(
                                    request.getUri(),
                                    HttpStatus.SC_OK
                            )
                    )
            );
        } finally {
            request.reset();
        }
    }
}
