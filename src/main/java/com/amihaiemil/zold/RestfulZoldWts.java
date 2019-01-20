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

import java.net.URI;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * RESTful Zold network entry point.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 * @version $Id$
 * @since 0.0.1
 * @todo #11:30min implement body of interface methods.
 */
public final class RestfulZoldWts implements ZoldWts {
    
    /**
     * Apache HttpClient which sends the requests.
     */
    private final HttpClient client;

    /**
     * Base URI.
     */
    private final URI baseUri;
    
    /**
     * Constructor.
     * @param baseUri Base URI.
     */
    public RestfulZoldWts(final URI baseUri) {
        this(
            HttpClients.custom()
                .setMaxConnPerRoute(10)
                .setMaxConnTotal(10)
                .build(),
            baseUri
        );
    }
    
    /**
     * Constructor. We recommend you to use the simple constructor
     * and let us configure the HttpClient for you. <br><br>
     * Use this constructor only if you know what you're doing.
     * 
     * @param client Given HTTP Client.
     * @param baseUri Base URI.
     */
    public RestfulZoldWts(final HttpClient client, final URI baseUri) {
        this.client = client;
        this.baseUri = baseUri;
    }

    /**
    * Pull the wallet from the network.
    * @return Wallet object.
    */
    public Wallet pull() {
        throw new UnsupportedOperationException(
            "Not yet implemented. If you can contribute please, do it at "
            + "https://github.com/amihaiemil/zold-java-client"
        );
    }
}
