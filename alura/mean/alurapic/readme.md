# AluraPic in MEAN

AluraPic application made using stack MEAN.

## Node Modules
* express
* consign
* body-parser
* mongoose
* jsonwebtoken
* cookie-parser


## Angular & Express
A token is generated when the user sign in.
The API sets the generated token at:
* Custom header x-access-token
* Header Set-Header, using httpOnly (the Angular won't access it)

The Angular also store the token in the SessionStorage and LocationStorage.
On every request, a interceptor insert the header x-access-token.
