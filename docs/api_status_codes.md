# APIs status codes

## 2xx status codes

- **200 OK**: Request succeeded
- **201 Created**: Resource created
- **204 No Content**: The server doesn't return a response body

## 3xx status codes

- **302 Found**: Redirect to URL in Location field

## 4xx status codes

- **400 Bad Request**: Wrong API format
- **401 Unauthorized**: Missing authentication (redirect to login?)
- **403 Forbidden**: Unauthorized request
- **404 Not found**: Requested resource not found

## 5xx status codes

- **500 Internal Server Error**: Unexpected condition
- **501 Not Implemented**: HTTP method is not supported
- **503 Service Unavailable**: Server not ready
