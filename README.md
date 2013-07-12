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
            {:status 200,
             :body
             {:user
              {:id "M091355",
               :role "admin",
               :enabled true,
               :refs
               [{:href "http://host/user", :rel "self"},
                {:href "http://host/user", :rel "update"},
                {:href "http://host/user", :rel "delete"}]}}
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
    		    {:status 200,
                 :body
                  :users
                  [:user
                   {:id "M091355",
                    :role "admin",
                    :enabled true,
                    :refs
                    [{:href "http://host/user", :rel "self"},
                     {:href "http://host/user", :rel "update"},
                     {:href "http://host/user", :rel "delete"}]},
                   :user
                   {:id "M094545",
                    :role "readonly",
                    :enabled true,
                    :refs
                    [{:href "http://host/user", :rel "self"},
                     {:href "http://host/user", :rel "update"},
                     {:href "http://host/user", :rel "delete"}]}]}
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
            Body: {:status 200,
                   :body
                   {:keyword
                    {:id "123456-1234-1234-1234-12345678",
                     :keyword "Foobar",
                     :refs
                     [{:href "http://host/keyword/{keyword-id}", :rel "self"},
                      {:href "http://host/keyword/{keyword-id}", :rel "update"},
                      {:href "http://host/keyword/{keyword-id}", :rel "delete"}]}}}
		404 : Not Found
		500 : Server Error
		
### Get All ###
	GET		/keywords
#### Request ####
    Header: Basic Authentication (optional - if present additional ref links will be returned)
            Response Content-Type: application/json
#### Responses ####
		200 : OK
            Body: {:status 200,
                   :body
                   {:keywords
                    [:property
                     {:id "123456-1234-1234-1234-12345678",
                      :keyword "Foobar",
                      :refs
                      [{:href "http://host/keyword/{keyword-id}", :rel "self"},
                       {:href "http://host/keyword/{keyword-id}", :rel "update"},
                       {:href "http://host/keyword/{keyword-id}", :rel "delete"}]},
                     :property
                     {:id "987654-9876-9876-9876-98765432",
                      :keyword "BazBang",
                      :refs
                      [{:href "http://host/keyword/{keyword-id}", :rel "self"},
                       {:href "http://host/keyword/{keyword-id}", :rel "update"},
                       {:href "http://host/keyword/{keyword-id}", :rel "delete"}]}]}}
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
            Body: {:status 200,
                   :body
                   {:property
                    {:id "123456-1234-1234-1234-12345678",
                     :property "System",
                     :userdefined false,
                     :values
                     [{:id "123", :value "DMS"},
                      {:id "234", :value "Problem List"},
                      {:id "345", :value "SIRS"}],
                     :refs
                     [{:href "http://host/property/{property-id}", :rel "self"},
                      {:href "http://host/property/{property-id}", :rel "update"},
                      {:href "http://host/property/{property-id}", :rel "delete"}]}}}
		404 : Not Found
		500 : Server Error
		
### Get All ###
	GET		/properties
#### Request ####
    Header: Basic Authentication (optional - if present additional ref links will be returned)
            Response Content-Type: application/json
#### Responses ####
		200 : OK
            Body: {:status 200,
                   :body
                   {:properties
                    [:property
                     {:id "123456-1234-1234-1234-12345678",
                      :property "System",
                      :userdefined false,
                      :values
                      [{:id "123", :value "DMS"},
                       {:id "234", :value "Problem List"},
                       {:id "345", :value "SIRS"}],
                      :refs
                      [{:href "http://host/property/{property-id}", :rel "self"},
                       {:href "http://host/property/{property-id}", :rel "update"},
                       {:href "http://host/property/{property-id}", :rel "delete"}]},
                     :property
                     {:id "987654-9876-9876-9876-98765432",
                      :property "Status",
                      :userdefined false,
                      :values
                      [{:id "456", :value "Final"},
                       {:id "567", :value "Under Development"},
                       {:id "678", :value "Testing"}],
                      :refs
                      [{:href "http://host/property/{property-id}", :rel "self"},
                       {:href "http://host/property/{property-id}", :rel "update"},
                       {:href "http://host/property/{property-id}", :rel "delete"}]}]}}
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
            Body: {:status 200,
                   :body
                   {:definition
                    {:id "123456-1234-1234-1234-12345678",
                     :title "Diabetes: 101",
                     :author "Dale Suesse",
                     :version "1.1",
                     :created 2002-11-02,
                     :uploaded 2012-07-08,
                     :description "This is a test definition.",
                     :comments
                     [{:comment "Comment number one.", :user "M091355", date: 2012-07-08},
                      {:comment "Comment numero dos.", :user "M091355", date: 2013-05-01}],
                     :properties
                     [{:prop "project", :val "Cohort Standardization"},
                      {:prop "system", :val "DMS"},
                      {:prop "usetype", :val "Sending Survey"},
                      {:prop "applicable years", :val "1998-2008"}],
                     :valuesets
                     [{:valueset
                      {:name "Name",
                       :uri "http://id.mayo.edu/valueset/name",
                       :href "http://host/cts2/valueset/name",
                       :definition 
                       {:name "1",
                        :uri "http://id.mayo.edu/valueset/name/version/1",
                        :resolution
                        {:codeSystems
                         [{:codeSystem "SNOMED", :version "2013"},
                          {:codeSystem "RxNORM", :version "2012"}],
                           :codes
                           [{:codeSystem "SNOMED", :code "123", :description "Code 123 Description"},
                            {:codeSystem "SNOMED", :code "456", :description "Code 456 Description"},
                            {:codeSystem "SNOMED", :code "789", :description "Code 789 Description"},
                            {:codeSystem "SNOMED", :code "012", :description "Code 012 Description"},
                            {:codeSystem "RxNorm", :code "ABC", :description "Code ABC Description"},
                            {:codeSystem "RxNorm", :code "DEF", :description "Code DEF Description"},
                            {:codeSystem "RxNorm", :code "GHI", :description "Code GHI Description"}]}}}}],
                     :refs
                     [{:href "http://host//definition/{definition-id}", :rel "self"},
                      {:href "http://host//definition/{definition-id}", :rel "update"},
                      {:href "http://host//definition/{definition-id}", :rel "delete"}]}}}
		404 : Not Found
		500 : Server Error
		
### Get All ###
	GET		/definition
#### Request ####
    Header: Basic Authentication (optional - if present additional ref links will be returned)
            Response Content-Type : application/json
                                  : application/msexcel
#### Responses ####
		200 : OK
			Body: {:status 200,
			       :body
        		   {:definitions
        		    [definition,
        		     definition,…]}
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