package org.acme.insurance.policyquote.domain;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.switchyard.annotations.Transformer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public final class PolicyQuoteTransformer {

	@Transformer(to = "{http://insurance.acme.org/}createPolicyQuoteResponse")
	public Element transformStringToCreatePolicyQuoteResponse(String from) {
		// TODO Auto-generated method stub
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			Element root = (Element) document
					.createElement("createPolicyQuoteResponse");
			// document.appendChild(root);
			return root;
		} catch (ParserConfigurationException pce) {
			// Parser with specified options can't be built
			pce.printStackTrace();
		}
		return null;
	}

	@Transformer(to = "{http://insurance.acme.org/}getPolicyQuoteResponse")
	public Element transformStringToGetPolicyQuoteResponse(String from) {
		// TODO Auto-generated method stub
		return null;
	}

}
