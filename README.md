# Example resource gateway

This example shows the basic functionality a resource gateway should support. The project is build based on Spring Boot.

The resource gateway supports bearer token usage via the authorization header according to [RFC6750](https://tools.ietf.org/html/rfc6750). This access token
is validated via the Onegini Token Server. If the access token was proven to be valid the actual resource is returned. In this example the device api of the
Token Server is exposed as resource. So in this example the Token Server acts as a resource server as well.

```
  +-------------+                           +------------------+                                       +-----------------+                               
  | Onegini SDK | ---- (1) get devices ---> | Resource Gateway |  ---- (2) validate access token --->  |  Token Server   |                               
  |             |                           |                  |                                       |                 |                               
  |             | <--- (6) user devices --- |                  |  <---- (3) validation response -----  |                 |                               
  +-------------+                           +------------------+                                       +-----------------+    
                                               ^     |
                                               |     |                                                 +-----------------+ 
                                               |     +------------------- (4) get user device ------>  | Resource Server | 
                                               |                                                       | (Token Server)  | 
                                               +--------------------------- (5) user devices --------  |                 |
                                                                                                       +-----------------+ 
```

## Configuration

| Property                                  | Description                                                                                                                                                                                                    |
|-------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| resource.gateway.tokenServer.clientId     | The client id of the oauth client acting as resource gateway, see [Resource Gateway config](https://docs.onegini.com/public/token-server/topics/general-app-config/resource-gateway/resource-gateway.html)     |
| resource.gateway.tokenServer.clientSecret | The client secret of the oauth client acting as resource gateway, see [Resource Gateway config](https://docs.onegini.com/public/token-server/topics/general-app-config/resource-gateway/resource-gateway.html) |
| resource.gateway.tokenServer.baseUri      | The token server base uri.                                                                                                                                                                                     | 
| device.api.serverRoot                     | The base uri of the resource server (Token Server in this example).                                                                                                                                            |
| device.api.username                       | The basic authentication username used to access the api.                                                                                                                                                      |
| device.api.password                       | The basic authentication password used to access the api.                                                                                                                                                      | 

## Building the sourcecode

`mvn clean install`

## Run the application

You can either run the application via the Spring Boot Maven plugin or by using the jar file created while building the application.

`mvn spring-boot:run`

or 

`java -jar <location of the jar file>`