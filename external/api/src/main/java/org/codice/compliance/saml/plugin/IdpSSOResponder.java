/**
 * Copyright (c) Codice Foundation
 *
 * <p>This is free software: you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or any later version.
 *
 * <p>This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details. A copy of the GNU Lesser General Public
 * License is distributed along with this program and can be found at
 * <http://www.gnu.org/licenses/lgpl.html>.
 */
package org.codice.compliance.saml.plugin;

import com.jayway.restassured.response.Response;

/**
 * This interface provides a mechanism for implementers to handle a portion of the SAML IdP
 * interactions that are not constrained by the SAML specification (and are therefore
 * implementation-dependent).
 */
public interface IdpSSOResponder {

  /**
   * After the tests send an AuthnRequest to the IdP using the Redirect binding, they will hand the
   * HTTP response they get to this method.
   *
   * <p>This method is responsible for handling the implementation-specific interactions that need
   * to occur before successfully authenticating a user and getting the SAML response. Once the SAML
   * response is received, this method should build and return the appropriate object.
   *
   * @param originalResponse - the original {@code RestAssured} response from the initial REDIRECT
   *     authn request
   * @return The response as an {@code IdpPostResponse}. The internal builder should be called to
   *     build the response object:
   *     <pre>{@code
   * return new IdpPostResponse.Builder()
   * .httpStatusCode(exampleStatusCode)
   * .url(exampleUrl)
   * .build();
   * }
   * where {@code exampleStatusCode} is the http status code returned by the IdP
   * where {@code exampleUrl} is the url in the "Location" header returned by the IdP
   * </pre>
   */
  IdpResponse getRedirectResponse(Response originalResponse);

  /**
   * After the tests send an AuthnRequest to the IdP using the POST binding, they will hand the HTTP
   * response they get to this method.
   *
   * <p>This method is responsible for handling the implementation-specific interactions that need
   * to occur before successfully authenticating a user and getting the SAML response. Once the SAML
   * response is received, this method should build and return the appropriate object.
   *
   * @param originalResponse - the original {@code RestAssured} response from the initial POST authn
   *     request
   * @return The response as an {@code IdpPostResponse}. The internal builder should be called to
   *     build the response object:
   *     <pre>{@code
   * return new IdpPostResponse.Builder()
   * .httpStatusCode(exampleStatusCode)
   * .samlForm(exampleSamlForm)
   * .build();
   * }
   * where {@code exampleStatusCode} is the http status code returned by the IdP
   * where {@code exampleSamlForm} is the wrapping form containing the samlResponse form control
   * returned by the IdP
   * </pre>
   */
  IdpResponse getPostResponse(Response originalResponse);
}
