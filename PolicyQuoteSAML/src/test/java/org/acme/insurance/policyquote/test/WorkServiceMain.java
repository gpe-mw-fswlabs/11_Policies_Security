/*
 * Copyright 2013 Red Hat Inc. and/or its affiliates and other contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.acme.insurance.policyquote.test;

import java.util.HashSet;
import java.util.Set;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.log4j.Logger;
import org.picketlink.identity.federation.api.wstrust.WSTrustClient;
import org.picketlink.identity.federation.api.wstrust.WSTrustClient.SecurityInfo;
import org.picketlink.identity.federation.core.wstrust.plugins.saml.SAMLUtil;
import org.switchyard.common.io.pull.StringPuller;
import org.switchyard.common.lang.Strings;
import org.switchyard.common.xml.XMLHelper;
import org.switchyard.component.test.mixins.http.HTTPMixIn;
import org.switchyard.policy.SecurityPolicy;
import org.w3c.dom.Element;

/**
 * WorkServiceMain.
 *
 * @author David Ward &lt;<a href="mailto:dward@jboss.org">dward@jboss.org</a>&gt; (C) 2012 Red Hat Inc.
 */
public final class WorkServiceMain {

    private static final Logger LOGGER = Logger.getLogger(WorkServiceMain.class);

    private static final String CONFIDENTIALITY = SecurityPolicy.CONFIDENTIALITY.getName();
    private static final String CLIENT_AUTHENTICATION = SecurityPolicy.CLIENT_AUTHENTICATION.getName();
    //private static final String CONFIDENTIALITY = "sca:confidentiality";
    //private static final String CLIENT_AUTHENTICATION = "sca:clientAuthentication";
    private static final String HELP = "help";

    private static final String MAVEN_USAGE = String.format("Maven Usage: mvn exec:java -Dexec.args=\"%s %s %s\"", CONFIDENTIALITY, CLIENT_AUTHENTICATION, HELP);

    private static String userId = "admin";
    private static String password = "admin";
    private static String serverHostname = "localhost:8080";

    private static void invokeWorkService(String scheme, int port, Element assertion) throws Exception {

        String soapRequest = new StringPuller().pull("/xml/soap-request.xml");
        LOGGER.info("Assertion: " + XMLHelper.toString(assertion));
        if (assertion != null) {
            soapRequest = soapRequest.replaceFirst("<!-- Assertion -->", XMLHelper.toString(assertion));
        }
        HTTPMixIn http = new HTTPMixIn();
        http.initialize();
        try {
            String endpoint = String.format("%s://"+serverHostname+":%s/policy-quote-saml/PolicyQuoteEntityWSService", scheme, port);
            //LOGGER.info(String.format("Invoking work service at endpoint: %s with request: %s", endpoint, soapRequest));
            LOGGER.info(String.format("Invoking work service at endpoint: %s", endpoint));
            String soapResponse = http.postString(endpoint, soapRequest);
            //LOGGER.info(String.format("Received work service response: %s", soapResponse));
            if (soapResponse.toLowerCase().contains("fault")) {
                throw new Exception("Error invoking work service (check server log)");
            }
        } finally {
            http.uninitialize();
        }
    }

    private static Element getAssertion() throws Exception {
    	LOGGER.info("Getting SAML assertion:  userId = "+ userId+" : password = "+password+" : serverHostname = "+serverHostname);
        WSTrustClient client = new WSTrustClient("PicketLinkSTS", "PicketLinkSTSPort",
                "http://"+serverHostname+"/picketlink-sts/PicketLinkSTS", new SecurityInfo(userId, password));
        //Element assertion = client.issueTokenForEndpoint("urn:switchyard-quickstart-demo:policy-security-saml:0.1.0");
        Element assertion = client.issueToken(SAMLUtil.SAML2_TOKEN_TYPE);
        
        boolean valid = client.validateToken(assertion);
        if (valid) {
            LOGGER.info("Token is valid.");
        } else {
            throw new Exception("Token is invalid.");
        }
        
        return assertion;
    }

    public static void main(String... args) throws Exception {
        serverHostname = System.getProperty("serverHostname", serverHostname);

        Set<String> policies = new HashSet<String>();
        for (String arg : args) {
            arg = Strings.trimToNull(arg);
            if (arg != null) {
                if (arg.equals(CONFIDENTIALITY) || arg.equals(CLIENT_AUTHENTICATION) || arg.equals(HELP)) {
                    policies.add(arg);
                } else {
                    LOGGER.error(MAVEN_USAGE);
                    throw new Exception(MAVEN_USAGE);
                }
            }
        }
        if (policies.contains(HELP)) {
            LOGGER.info(MAVEN_USAGE);
        } else {
            final String scheme;
            final int port;
            if (policies.contains(CONFIDENTIALITY)) {
            	LOGGER.info("Invoking service via SSL...");
                scheme = "https";
                port = 443;
                SSLContext sslcontext = SSLContext.getInstance("TLS");
                sslcontext.init(null, null, null);
                SSLSocketFactory sf = new SSLSocketFactory(sslcontext, SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
                Scheme https = new Scheme(scheme, port, sf);
                SchemeRegistry sr = new SchemeRegistry();
                sr.register(https);
            } else {
                scheme = "http";
                port = 8080;
            }
            Element assertion = policies.contains(CLIENT_AUTHENTICATION) ? getAssertion() : null;
            invokeWorkService(scheme, port, assertion);
        }
    }

}
