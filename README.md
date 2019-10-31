# User Portal Authentication Service for UM KPMP Apps

This is the repository for the authentication service for UM KPMP apps. It connects to the UWash User Portal to get group/access information for KPMP users. 

## Endpoint /v1/user/info/{shibId} 

Returns the JSON object from UW User Portal with the information for the user with specified shibId. Throws a 404 if the user is not found. This endpoint uses the default client ID (i.e. the client ID for this app). The default clientID is set in the Docker environment variable ENV_DEFAULT_CLIENT_ID (set in the .env file).

## Endpoint /v1/user/info/{clientId}/{shibId}

Returns the JSON object from UW User Portal with the information for the user with specified shibId. Throws a 404 if the user is not found. This endpoint requires a client ID (i.e. the clientID of the application requesting the login information). 


## Return JSON

`{"displayName":auser,"email":"auser@email.com","_id":"12345abcdfefg","active":true,"first_name":"Firstname","groups":["user_group1","user_group2"],"last_name":"Lastname","organization_id":"543421abcdrfg","phone_numbers":["123-456-7891],"shib_id":"auser@email.com"}`

