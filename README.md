# Example resource gateway

[![CircleCI](https://circleci.com/gh/Onegini/example-resource-gateway.svg?style=shield&circle-token=6136616c3a42e02dbc404ba9fb568c493f0dc969)](https://circleci.com/gh/Onegini/example-resource-gateway)

This example shows the basic functionality a resource gateway should support. The project is built based on Spring Boot.

The resource gateway supports bearer token usage via the authorization header according to [RFC6750](https://tools.ietf.org/html/rfc6750). This access token is
validated via the Onegini Token Server. If the access token was proven to be valid, the actual resource is returned. In this example resource gateway
the [device api](https://docs.onegini.com/msp/stable/token-server/api-reference/end-user/device-v4.html) of the Onegini Token Server is exposed as resource. In
this example the Onegini Token Server acts both as an authorization server and as a resource server.

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

Refer to the Spring
Boot [Externalized Configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-external-config) for
setting the properties.

The following properties can be set for the Example Resource Gateway:

| Property                                  | Default value | Example value               | Description
|-------------------------------------------|---------------|-----------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
| resource.gateway.tokenserver.baseUri      |               | http://localhost:7878/oauth | The base URI of the [Onegini Token Server](https://docs.onegini.com/msp/stable/token-server).
| resource.gateway.tokenserver.clientId     |               |                             | The client id of the API client acting as resource gateway, see [Resource Gateway config](https://docs.onegini.com/msp/stable/token-server/topics/general-app-config/resource-gateway/resource-gateway.html).
| resource.gateway.tokenserver.clientSecret |               |                             | The client secret of the API client acting as resource gateway.
| device.api.serverRoot                     | The value of resource.gateway.tokenServer.baseUri | http://localhost:7878/oauth | The base uri of the resource server that returns a list of Devices. In this example, it is the base URI of the Onegini Token Server.
| device.api.username                       |               |                             | The basic authentication username used to [access the End User api](https://docs.onegini.com/msp/stable/token-server/topics/technical-app-management/api-configuration/api-configuration.html) in the Onegini Token Server.
| device.api.password                       |               |                             | The basic authentication password used to access the End User api in the Onegini Token Server.
| spring.servlet.multipart.max-file-size    | 1MB           |                             | Sets the maximum file size per uploaded file.
| spring.servlet.multipart.max-request-size | 10MB          |                             | Sets the maximum size of the HTTP request when uploading files.

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

Requires an Access token with scope `application-details`. Access tokens issued via the Implicit Authentication flow are rejected.

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

### File upload

POST: `/resources/file-upload`

This request accepts multipart/form-data with the following parameters:

| Parameter name    | Type            | Required  | Remarks
|-------------------|-----------------|-----------|---------------
| name              | string          | no        |
| email             | string          | no        |
| attachments       | file (multiple) | no        | Multiple files can be sent as form-data with name "attachments"

Requires an Access token with scope `write`. Access tokens issued via the Implicit Authentication flow are rejected.

Example request:

```http request
POST /resources/upload HTTP/1.1
Host: resource.example.com
Authorization: Bearer 5F09FC2DD7DCDB72ABF1A6C026DF8FFB9D7D1F4AD069E34F0A6EC6206A593420
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW

----WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="email"

jane@example.com
----WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="name"

Jane Doe
----WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="attachments"; filename="background.jpg"
Content-Type: image/jpeg

(data)
----WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="attachments"; filename="logo.svg"
Content-Type: image/svg+xml

(data)
----WebKitFormBoundary7MA4YWxkTrZu0gW
```

This endpoint returns all parts in the response. The body is a Base64 encoded byte[] of the original file contents.

Example response:

```json
{
  "name": "Jane Doe",
  "email": "jane@example.com",
  "attachments": [
    {
      "file_name": "background.jpg",
      "file_size": 34626,
      "content_type": "image/jpeg",
      "body": "LzlqLzRBQVFTa1pKUmdBQkF…dk42UC9a"
    },
    {
      "file_name": "logo.svg",
      "file_size": 1428,
      "content_type": "image/svg+xml",
      "body": "UEhOMlp5QjRiV3h1Y3o…1jKw=="
    }
  ]
}
```

## Releasing

- Update the [CHANGELOG.md](CHANGELOG.md). Make sure that the version you are releasing is mentioned
- Create a tag either from the GitHub UI or through git. Make sure that the name of the tag only contains the version number that you want to release,
  e.g. `2.0.0`.
