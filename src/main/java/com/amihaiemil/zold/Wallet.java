package com.amihaiemil.zold;

/**
 * Zold network entry point.
 * @author Ammar Atef (ammar.atef45@gmail.com)
 * @version $Id$
 * @since 0.0.1
 */
public interface Wallet {
    /**
    * Get the balance of the wallet.
    * @return Balance
    */
    double balance();

    /**
    * Pay to another wallet.
    * @param keygap Sender keygap
    * @param user Recipient user id
    * @param amount Amount to be sent
    * @param details The details of transfer
    * @todo #4 solve checkstyle paramternumber error.
    */
    void pay(String keygap, String user, double amount, String details);

    /**
    * Finds all payments that match this query and returns.
    * @param id Wallet id
    * @param details Regex of payment details
    */
    void find(String id, String details);
}