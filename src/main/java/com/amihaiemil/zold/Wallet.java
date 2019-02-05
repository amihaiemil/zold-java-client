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

import java.io.IOException;

/**
 * Zold Wallet.
 * @author Ammar Atef (ammar.atef45@gmail.com)
 * @version $Id$
 * @since 0.0.1
 */
public interface Wallet {

    /**
     * Get the wallet's ID.
     * @return String.
     * @throws IOException If there's a networking problem.
     */
    String getId() throws IOException;

    /**
    * Get the balance of the wallet.
    * @return Balance
    * @throws IOException If there's a networking problem.
    */
    double balance() throws IOException;

    /**
    * Pay to another wallet.
    * @param keygap Sender keygap
    * @param user Recipient user id
    * @param amount Amount to be sent
    * @param details The details of transfer
    * @throws IOException If there's a networking problem.
    * @checkstyle ParameterNumber (5 lines)
    */
    void pay(
        final String keygap, final String user,
        final double amount, final String details
    )throws IOException;

    /**
    * Finds all payments that match this query and returns.
    * @param id Wallet id
    * @param details Regex of payment details
    * @throws IOException If there's a networking problem.
    */
    void find(String id, String details)throws IOException;
}
