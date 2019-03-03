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

import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ClassicHttpRequest;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.IOException;

/**
 * JSON payload of an HttpRequest.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @version $Id$
 * @since 0.0.1
 * @todo #25:30min Add tests for PayloadOf.
 */
final class PayloadOf extends JsonResource {
    /**
     * Ctor.
     *
     * @param request The http request
     * @throws IllegalStateException if the request's payload cannot be read
     */
    PayloadOf(final ClassicHttpRequest request) {
        super(() -> {
            try {
                final HttpEntity entity = request.getEntity();
                final JsonObject body;
                if (entity != null) {
                    body = Json.createReader(entity.getContent()).readObject();
                } else {
                    body =  Json.createObjectBuilder().build();
                }
                return body;
            } catch (final IOException ex) {
                throw new IllegalStateException(
                        "Cannot read request payload", ex
                );
            }
        });
    }

    /**
     * Ctor.
     * @param response The http response.
     * @throws IllegalStateException if the response's payload cannot be read
     */
    PayloadOf(final ClassicHttpResponse response) {
        super(() -> {
            try {
                return Json.createReader(
                    response.getEntity().getContent()
                ).readObject();
            } catch (final IOException ex) {
                throw new IllegalStateException(
                    "Cannot read response payload", ex
                );
            }
        });
    }
}
