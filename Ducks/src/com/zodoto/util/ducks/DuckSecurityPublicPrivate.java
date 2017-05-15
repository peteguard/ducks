package com.zodoto.util.ducks;

/**
 * Encrypt / Decrypt
 * 
 * @author Pete Guard
 *
 */
public class DuckSecurityPublicPrivate implements DuckSecurity {

	@Override
	public String encryptRequest(String request, String identity) throws Exception {
		
		//	Encrypt the payload with the requesters private key
		String step1 = encryptWithOriginatorPrivateKey(request);
		
		//	Append the identity, the key source will need it to know which public key to use to decrypt
		String step2 = step1 + DuckConstants.DIVIDER + identity;
		
		//	Encrypt with the key source's public key
		String step3 = encryptWithKeySourcePublicKey(step2);
		
		return step3;
	}
	
	@Override
	public String decryptRequest(String request) throws Exception {
		
		//	Decrypt with the key source's public key
		String step1 = decryptWithKeySourcePublicKey(request);
		
		//	Split the payload and the identity
		int index = step1.lastIndexOf(DuckConstants.DIVIDER);
		String step2 = step1.substring(0, index);
		String identity = step1.substring(index + 1);
		
		//	Decrypt with the sender's public key
		String step3 = decryptWithOriginatorPublicKey(step2, identity);
		
		return step3;
	}
	
	@Override
	public String encryptResponse(String response, String identity) throws Exception {
		
		//	Encrypt with the key source's private key
		String step1 = encryptWithKeySourcePrivateKey(response);
		
		//	Encrypt with the original requestor's public key
		String step2 = encryptWithOriginatorPublicKey(step1, identity);

		return step2;
	}
	
	@Override
	public String decryptResponse(String response) throws Exception {
		
		//	Decrypt with the original requestor's private key
		String step1 = decryptWithOriginatorPrivateKey(response);
		
		//	Decrypt with the key source's public key
		String step2 = decryptWithKeySourcePublicKey(step1);
		
		return step2;
	}

	
	public String encryptWithOriginatorPrivateKey(String request)	{
		//TODO - encrypt request
		return request;
	}
	
	public String decryptWithOriginatorPublicKey(String request, String identity)	{
		//TODO - encrypt request
		return request;
	}
		
	
	public String encryptWithOriginatorPublicKey(String request, String identity)	{
		//TODO - encrypt request
		return request;
	}
	
	public String decryptWithOriginatorPrivateKey(String request)	{
		//TODO - encrypt request
		return request;
	}
	
	
	public String encryptWithKeySourcePublicKey(String request)	{
		//TODO - encrypt request
		return request;
	}
	
	public String decryptWithKeySourcePrivateKey(String request)	{
		//TODO - decrypt request
		return request;
	}
	
	
	public String encryptWithKeySourcePrivateKey(String request)	{
		//TODO - decrypt request
		return request;
	}
	
	public String decryptWithKeySourcePublicKey(String request)	{
		//TODO - decrypt request
		return request;
	}

	
}
