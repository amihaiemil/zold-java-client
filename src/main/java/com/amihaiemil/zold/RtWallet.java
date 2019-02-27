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

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.json.Json;
import javax.json.JsonArray;

/**
 * RESTful Zold wallet.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 * @version $Id$
 * @since 0.0.1
 * @todo #29:30min Write unit test for all wallet operations.
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
     * Ctor.
     * @param client API Client.
     * @param baseUri URI.
     */
    RtWallet(final HttpClient client, final URI baseUri) {
        this.client = client;
        this.baseUri = baseUri;
    }

    @Override
    public String getId() throws IOException {
        final HttpGet walletId = new HttpGet(
            URI.create(this.baseUri.toString() + "/id")
        );
        try {
            return this.client.execute(
                walletId,
                new ReadString(
                    new MatchStatus(
                        walletId.getURI(),
                        HttpStatus.SC_OK
                    )
                )
            );
        } finally {
            walletId.releaseConnection();
        }
    }

    @Override
    public double balance() throws IOException{
        final HttpGet walletBalance = new HttpGet(
            URI.create(this.baseUri.toString() + "/balance")
        );
        try {
            return Double.parseDouble(
                this.client.execute(
                    walletBalance,
                    new ReadString(
                        new MatchStatus(
                            walletBalance.getURI(),
                            HttpStatus.SC_OK
                        )
                    )
                )
            );
        } finally {
            walletBalance.releaseConnection();
        }
    }

    @Override
    public void pay(
        final String keygap, final String user,
        final double amount, final String details
    ) throws IOException {
        
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
                        request.getURI(),
                        HttpStatus.SC_MOVED_TEMPORARILY
                    )
                )
            );
        } finally {
            request.releaseConnection();
        }
    }

    @Override
    public JsonArray find(
            final String id, final String details
    ) throws IOException, URISyntaxException {
        URIBuilder builder = new URIBuilder(this.baseUri.toString() + "/find");
        builder.setParameter("bnf", id);
        builder.setParameter("details", details);
        final HttpGet request = new HttpGet(
                builder.build()
        );
        try {
            return this.client.execute(
                    request,
                    new ReadJsonArray(
                            new MatchStatus(
                                    request.getURI(),
                                    HttpStatus.SC_OK
                            )
                    )
            );
        } finally {
            request.releaseConnection();
        }
    }
}
