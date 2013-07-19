# Library of Cohort Definitions REST API #

## Build & Run ##

```sh
$ cd LCD_REST_API
$ ./sbt
> container:start
> browse
```

If `browse` doesn't launch your browser, manually open [http://localhost:8080/](http://localhost:8080/) in your browser.

# Resources #

## Users ##
-----------
### Create ###
    POST /user
#### Request ####
    Header: Basic Authentication
            Response Content-Type : application/json
#### Responses ####
        201 : Created
            Header: Location: /user/{user-id}
		400 : Bad Request
		401 : Unauthorized
		500 : Server Error

### Update ###
    PUT /user/{user-id}
#### Request ####
    Header: Basic Authentication
            Response Content-Type : application/json
#### Responses ####
        200 : OK
		401 : Unauthorized
		404 : Not Found
		409 : Conflict
		500 : Server Error
		
### Delete ###
    DELETE /user/{user-id}
#### Request ####
    Header: Basic Authentication
            Response Content-Type : application/json
#### Responses ####
        200 : OK
		401 : Unauthorized
		404 : Not Found
		405 : Method Not Allowed
		500 : Server Error
		
### Get ###
    GET /user/{user-id}
#### Request ####
    Header: Basic Authentication
            Response Content-Type: application/json
#### Responses ####
        200 : OK
        Body:
            {"user" : {
        	 "id" : "M091355",
             "roles" : ["ADMIN", "SUPER_USER"],
          	 "enabled" : true,
        	 "refs" : [{"href" : "http://host/user/M091355", "rel" : "self"},
                       {"href" : "http://host/user/M091355", "rel" : "update"},
                       {"href" : "http://host/user/M091355", "rel" : "delete"}]}}
        401 : Unauthorized
		404 : Not Found
		500 : Server Error
		
### Get All ###
    GET /users
#### Request ####
    Header: Basic Authentication
            Response Content-Type: application/json
#### Responses ####
        200 : OK
		    Body:
                {"users" :
                 [{"user" : {
                   "id" : "M091355",
                   "roles" : ["ADMIN", "SUPER_USER"],
                   "enabled" : true,
                   "refs" : [{"href" : "http://host/user/M091355", "rel" : "self"},
                             {"href" : "http://host/user/M091355", "rel" : "update"},
                        	{"href" : "http://host/user/M091355", "rel" : "delete"}]}}},
			      {"user" : {…}}]}
   		401 : Unauthorized
		500 : Server Error

## Keywords ##
--------
### Create ###
	POST 	/keyword
#### Request ####
    Header: Basic Authentication
            Response Content-Type : application/json
#### Responses ####
    	201 : Created
			Header: Location: /keyword/{keyword-id}
		400 : Bad Request
		401 : Unauthorized
		500 : Server Error
		
### Update ###
	PUT		/keyword/{keyword-id}
#### Request ####
    Header: Basic Authentication
            Response Content-Type : application/json
#### Responses ####
		200 : OK
		401 : Unauthorized
		409 : Conflict
		500 : Server Error
		
### Delete ###
	DELETE	/keyword/{keyword-id}
#### Request ####
    Header: Basic Authentication
            Response Content-Type : application/json
#### Responses ####
		200 : OK
		401 : Unauthorized
		404 : Not Found
		405 : Method Not Allowed
		500 : Server Error
		
### Get ###
	GET		/keyword/{keyword-id}
#### Request ####
    Header: Basic Authentication (optional - if present additional ref links will be returned)
            Response Content-Type: application/json
#### Responses ####
		200 : OK
            Body: 
                {"keyword" : {
             	 "id" : "123456",
            	 "keyword" : "Foobar",
            	 "refs" : [{"href" : "http://host/keyword/123456", "rel" : "self"},
                           {"href" : "http://host/keyword/123456", "rel" : "update"},
                           {"href" : "http://host/keyword/123456", "rel" : "delete"}]}}
		404 : Not Found
		500 : Server Error
		
### Get All ###
	GET		/keywords
#### Request ####
    Header: Basic Authentication (optional - if present additional ref links will be returned)
            Response Content-Type: application/json
#### Responses ####
		200 : OK
            Body: 
                {"keywords" :
                 [{"keyword" : {
	               "id" : "123456",
                   "keyword" : "Foobar",
                   "refs" : [{"href" : "http://host/keyword/123456", "rel" : "self"},
                             {"href" : "http://host/keyword/123456", "rel" : "update"},
                             {"href" : "http://host/keyword/123456", "rel" : "delete"}]}},
                  {"keyword" : {…}}]}
		500 : Server Error

## Properties ##
--------
### Create ###
	POST 	/property
#### Request ####
    Header: Basic Authentication
            Response Content-Type : application/json
#### Responses ####
    	201 : Created
			Header: Location: /property/{property-id}
		400 : Bad Request
		401 : Unauthorized
		500 : Server Error
		
### Update ###
	PUT		/property/{property-id}
#### Request ####
    Header: Basic Authentication
            Response Content-Type : application/json
#### Responses ####
		200 : OK
		401 : Unauthorized
		409 : Conflict
		500 : Server Error
		
### Delete ###
	DELETE	/property/{property-id}
#### Request ####
    Header: Basic Authentication
            Response Content-Type : application/json
#### Responses ####
		200 : OK
		401 : Unauthorized
		404 : Not Found
		405 : Method Not Allowed
		500 : Server Error
		
### Get ###
	GET		/property/{property-id}
#### Request ####
    Header: Basic Authentication (optional - if present additional ref links will be returned)
            Response Content-Type: application/json
#### Responses ####
		200 : OK
            Body:
                {"property" : {
	             "id" : "1234",
    	         "property" : "System",
             	 "userdefined" : false,
            	 "values" : [{"id" : "123", "value" : "DMS"},
				             {"id" : "234", "value" : "Problem List"},
				             {"id" : "345", "value" : "SIRS"}],
            	 "refs" : [{"href" : "http://host/property/1234", "rel" : "self"},
			               {"href" : "http://host/property/1234", "rel" : "update"},
             			   {"href" : "http://host/property/1234", "rel" : "delete"}]}}
		404 : Not Found
		500 : Server Error
		
### Get All ###
	GET		/properties
#### Request ####
    Header: Basic Authentication (optional - if present additional ref links will be returned)
            Response Content-Type: application/json
#### Responses ####
		200 : OK
            Body:
                {"properties" :
                 [{"property" : {
	               "id" : "1234",
    	           "property" : "System",
             	   "userdefined" : false,
            	   "values" : [{"id" : "123", "value" : "DMS"},
				               {"id" : "234", "value" : "Problem List"},
				               {"id" : "345", "value" : "SIRS"}],
            	   "refs" : [{"href" : "http://host/property/1234", "rel" : "self"},
			                 {"href" : "http://host/property/1234", "rel" : "update"},
             		  	     {"href" : "http://host/property/1234", "rel" : "delete"}]}},
             	  {"property" : {…}}]}
        500 : Server Error

## Logs ##
----
### Get Users Log ###
	GET		/auditlog/users
#### Request ####
    Header: Basic Authentication
            Response Content-Type: application/json
#### Responses ####
		200 : OK
			Body: auditlog-users-json (TODO: define)
		401 : Unauthorized
		500 : Server Error
		
### Get Keywords Log ###
	GET		/auditlog/keywords
#### Request ####
    Header: Basic Authentication
            Response Content-Type: application/json
#### Responses ####
		200 : OK
		    Body: audit-log-keywords-json (TODO: define)
		401 : Unauthorized
		500 : Server Error
		
### Get Definitions Log ###
	GET		/auditlog/definitions
#### Request ####
    Header: Basic Authentication
#### Responses ####
    Response Content-Type: application/json
		200 : OK
		    Body: auditlog-definitions-json (TODO: define)
		401 : Unauthorized
		500 : Server Error

## Definitions ##
-----------
### Create ###
	POST	/definition
#### Request ####
    Header: Basic Authentication
            Response Content-Type : application/json
#### Responses ####
		201 : Created
			Header: Location: /definition/{definition-id}
		400 : Bad Request
		401 : Unauthorized
		500 : Server Error
		
### Update ###
	PUT		/definition/{definition-id}
#### Request ####
    Header: Basic Authentication
            Response Content-Type : application/json
#### Responses ####
		200 : OK
		401 : Unauthorized
		404 : Not Found
		409 : Conflict
		500 : Server Error
		
### Delete ###
	DELETE 	/definition/{definition-id}
#### Request ####
    Header: Basic Authentication
            Response Content-Type : application/json
#### Responses ####
		200 : OK
		401 : Unauthorized
		404 : Not Found
		405 : Method Not Allowed
		500 : Server Error
		
### Get###
	GET		/definition/{definition-id}
#### Request ####
    Header: Basic Authentication (optional - if present additional ref links will be returned)
            Response Content-Type : application/json
                                  : application/msexcel
#### Responses ####
		200 : OK
            Body:
                {"definition" : {
             	 "id" : "123456",
	             "uploader" : "M091355",
	             "uploaded" : "2013-07-10",
	             "properties" : [{"property" : "title", "value" : "Diabetes: 101"},
                                 {"property" : "author", "value" : "Dale Suesse"},
					             {"property" : "version", "value" : "1.1"},
                                 {"property" : "created", "value" : "2009-01-01"},
                                 {"property" : "description", "value" : "This is a test definition."},
                                 {"property" : "project", "value" : "CLIP"},
                                 {"property" : "system", "value" : "DMS"},
                                 {"property" : "usetype", "value" : "Sending Survey"},
                                 {"property" : "applicableYear", "value" : "2009-present"}],
	             "comments" : [{"comment" : "Comment number one", "user" : "M091355", "date" : "2013-07-10"},
				               {"comment" : "Comment numero dos", "user" : "M091355", "date" : "2013-07-12"}],
	             "valuesets" : [
		            {"name" : "123.123.12.33.1445",
		             "uri" : "",
		             "href" : "",
            		 "definition" : {
		 	            "name" : "1",
            		 	"uri" : "",
		 	            "resolution" : {
            		 		"codesystems" : [{"codesystem" : "SNOMED", "version" : "2013"},
		 					                 {"codesystem" : "RxNORM", "version" : "2012"}],
				            "codes" : [{"codesystem" : "SNOMED", "code" : "123", "description" : "Code 123 Description"},
						               {"codesystem" : "SNOMED", "code" : "456", "description" : "Code 456 Description"},
						               {"codesystem" : "SNOMED", "code" : "789", "description" : "Code 789 Description"},
						               {"codesystem" : "RxNORM", "code" : "abc", "description" : "Code abc Description"},
						               {"codesystem" : "RxNORM", "code" : "def", "description" : "Code def Description"}]}}}],
    	          "refs" : [{"href" : "http://host/definition/123456", "rel" : "self"},
			                {"href" : "http://host/definition/123456", "rel" : "update"},
			                {"href" : "http://host/definition/123456", "rel" : "delete"}]}}
		404 : Not Found
		500 : Server Error
		
### Get All ###
	GET		/definitions
#### Request ####
    Header: Basic Authentication (optional - if present additional ref links will be returned)
            Response Content-Type : application/json
                                  : application/msexcel
    Parameters:
                q (optional)		: text to perform freetext search on
                keywords (optional)	: array of keywords to filter
                property (optional)	: properties to filter
#### Responses ####
		200 : OK
			Body:
			    {"definitions" : [{"definition" : {…}},
			                      {"definition" : {…}}]}
		500 : Server Error
		
## Response Codes ##
    200 : OK
    201 : Created             - Object was created
    400 : Bad Request         - Malformed object
    401 : Unauthorized        - Authenticated user doesn't have proper permissions 
    404 : Not Found           - Object doesn't exist
	405 : Method Not Allowed  - Method can not be performed on this object
    409 : Conflict            - this object is in a conflicted state.
	                            Another user may be manipulating.
	                            Client to re-get the object and attempt update again if desired
	500 : Server Error        - Internal Server error