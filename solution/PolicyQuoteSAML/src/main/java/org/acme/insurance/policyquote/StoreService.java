package org.acme.insurance.policyquote;

import org.acme.insurance.policyquote.domain.PolicyQuote;

public interface StoreService {

    /**
     * Stores given policy quote in database.
     * 
     * @param quote Policy quote to store.
     */
    void storePolicyQuote(PolicyQuote quote);
}
