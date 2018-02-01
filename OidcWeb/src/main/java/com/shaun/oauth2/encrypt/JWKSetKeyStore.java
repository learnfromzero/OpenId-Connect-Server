package com.shaun.oauth2.encrypt;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.List;

/**
 * @author jricher
 *
 */
public class JWKSetKeyStore {

	private JWKSet jwkSet;

	private Resource location;

	public JWKSetKeyStore() {

	}

	public JWKSetKeyStore(JWKSet jwkSet) {
		this.jwkSet = jwkSet;
		initializeJwkSet();
	}

	private void initializeJwkSet() {

		if (jwkSet == null) {
			if (location != null) {

				if (location.exists() && location.isReadable()) {

					try {
						// read in the file from disk
						String s = CharStreams.toString(new InputStreamReader(location.getInputStream(), Charsets.UTF_8));

						// parse it into a jwkSet object
						jwkSet = JWKSet.parse(s);
					} catch (IOException e) {
						throw new IllegalArgumentException("Key Set resource could not be read: " + location);
					} catch (ParseException e) {
						throw new IllegalArgumentException("Key Set resource could not be parsed: " + location);                    }

				} else {
					throw new IllegalArgumentException("Key Set resource could not be read: " + location);
				}

			} else {
				throw new IllegalArgumentException("Key store must be initialized with at least one of a jwkSet or a location.");
			}
		}
	}

	/**
	 * @return the jwkSet
	 */
	public JWKSet getJwkSet() {
		return jwkSet;
	}

	/**
	 * @param jwkSet the jwkSet to set
	 */
	public void setJwkSet(JWKSet jwkSet) {
		this.jwkSet = jwkSet;
		initializeJwkSet();
	}

	/**
	 * @return the location
	 */
	public Resource getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Resource location) {
		this.location = location;
		initializeJwkSet();
	}

	/**
	 * Get the list of keys in this keystore. This is a passthrough to the underlying JWK Set
	 */
	public List<JWK> getKeys() {
		if (jwkSet == null) {
			initializeJwkSet();
		}
		return jwkSet.getKeys();
	}



}
