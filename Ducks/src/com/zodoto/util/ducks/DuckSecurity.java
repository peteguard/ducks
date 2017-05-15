package com.zodoto.util.ducks;

/**
 * 
 * Encrypts and decrypts request and response messages
 * 
 * @author Pete Guard
 *
 */
public interface DuckSecurity {

	/**
	 * Encrypt the request - Done by the requester
	 * @param request Plain test request
	 * @param identity Identity of the requester
	 * @return encrypted request
	 * @throws Exception Error
	 */
	public String encryptRequest(String request, String identity) throws Exception;

	/**
	 * Decrypt the request - Done by the key source
	 * @param request Encrypted request
	 * @return plain text request
	 * @throws Exception Error
	 */
	public String decryptRequest(String request) throws Exception;
	
	/**
	 * Encrypt the response - Done by the key source
	 * @param response Plain text response
	 * @param identity Identity of the requester
	 * @return encrypted response
	 * @throws Exception Error
	 */
	public String encryptResponse(String response, String identity) throws Exception;
	
	/**
	 * Decrypt the response - Done by the requester
	 * @param response Encrypted response
	 * @return Plain text response
	 * @throws Exception Error
	 */
	public String decryptResponse(String response) throws Exception;
}
