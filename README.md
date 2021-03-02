# Example resource gateway

[![CircleCI](https://circleci.com/gh/Onegini/example-resource-gateway.svg?style=shield&circle-token=6136616c3a42e02dbc404ba9fb568c493f0dc969)](https://circleci.com/gh/Onegini/example-resource-gateway)

This example shows the basic functionality a resource gateway should support. The project is build based on Spring Boot.

The resource gateway supports bearer token usage via the authorization header according to [RFC6750](https://tools.ietf.org/html/rfc6750). This access token is
validated via the Onegini Token Server. If the access token was proven to be valid the actual resource is returned. In this example the device api of the Token
Server is exposed as resource. So in this example the Token Server acts as a resource server as well.

```
  +-------------+                           +------------------+                                          +-----------------+
  | Onegini SDK | ---- (1) get devices ---> | Resource Gateway |  ---- (2) validate access token    --->  |  Token Server   |
  |             |                           |                  |                                          |                 |
  |             | <--- (6) user devices --- |                  |  <---- (3) introspection response -----  |                 |
  +-------------+                           +------------------+                                          +-----------------+
                                               ^     |
                                               |     |                                                    +-----------------+
                                               |     +------------------- (4) get user device --------->  | Resource Server |
                                               |                                                          | (Token Server)  |
                                               +--------------------------- (5) user devices -----------  |                 |
                                                                                                          +-----------------+
```

## Configuration

| Property                                  | Description                                                                                                                                                                                                    |
|-------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| resource.gateway.tokenserver.clientId     | The client id of the oauth client acting as resource gateway, see [Resource Gateway config](https://docs.onegini.com/public/token-server/topics/general-app-config/resource-gateway/resource-gateway.html)     |
| resource.gateway.tokenserver.clientSecret | The client secret of the oauth client acting as resource gateway, see [Resource Gateway config](https://docs.onegini.com/public/token-server/topics/general-app-config/resource-gateway/resource-gateway.html) |
| resource.gateway.tokenserver.baseUri      | The token server base uri.                                                                                                                                                                                     |
| device.api.serverRoot                     | The base uri of the resource server (Token Server in this example).                                                                                                                                            |
| device.api.username                       | The basic authentication username used to access the End User api in the Token Server.                                                                                                                                                      |
| device.api.password                       | The basic authentication password used to access the End User api in the Token Server.                                                                                                                                                      |

## Building the sourcecode

`mvn clean install`

## Run the application

You can either run the application via the Spring Boot Maven plugin or by using the jar file created while building the application.

`mvn spring-boot:run`

or

`java -jar <location of the jar file>`

## Endpoints

All endpoints require a valid Bearer Access Token passed via the `Authorization` header.

Example request:

```http request
GET /resources/devices HTTP/1.1
Authorization: Bearer 5F09FC2DD7DCDB72ABF1A6C026DF8FFB9D7D1F4AD069E34F0A6EC6206A593420
```

### List devices

GET: `/resources/devices`

Requires an Access token with scope `read`. Access tokens issued via
the [Implicit Authentication flow](https://docs.onegini.com/msp/stable/token-server/topics/mobile-apps/implicit-authentication/implicit-authentication.html) are
rejected.

Returns a list of devices for the user for which the Access Token was issued. Refer
to [Device API v4](https://docs.onegini.com/msp/stable/token-server/api-reference/end-user/device-v4.html) for its response format.

### Details of the application

GET: `/resources/application-details`

Requires an Access token with scope `application-details`.  Access tokens issued via the Implicit Authentication flow are rejected.

Returns an object with details of the (mobile) application for which the Access token was issued.

Example response:

```json
{
  "application_identifier": "exampleApp",
  "application_platform": "android",
  "application_version": "1.0.0"
}
```

### Decorated user id

GET: `/resources/user-id-decorated`

Requires an Access token that was issued via the Implicit Authentication flow.

Returns an object with a "decorated" user identifier.

Example response:

```json
{
  "decorated_user_id": "✨ myDummyUserId ✨"
}
```

## Releasing

- Update the [CHANGELOG.md](CHANGELOG.md). Make sure that the version you are releasing is mentioned
- Create a tag either from the GitHub UI or through git. Make sure that the name of the tag only contains the version number that you want to release,
  e.g. `2.0.0`.
