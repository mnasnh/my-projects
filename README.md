# my-projects
## myretail-product-service

### Objective
This project is a demo for writing REST APIs for a popular retail company. The goal is to create an end-to-end Proof-of-Concept for a products API, 
which will aggregate product data from multiple sources and return it as JSON to the caller. 
1. We create a RESTful service that can retrieve product and price details by ID. 
2. We create a mock API service using Json-Server that will return price and name details for a product.

### How to run the application

#### Set up mock service for price and name
1. download npm package if not installed already.
2. Run command **npm install -g json-server**. This will install json-server and add it to the path variable.
3. Navigate to the json-server installation directory and create a db.json file. Use the sample json file available in this project in the directory **json-server**.
3. Start the json-server using command **json-server --watch db.json**.
4. json-server will start at port 3000. Browse available resources: http://localhost:3000/currentPrice ,   http://localhost:3000/names

#### Run application
1. Download the project source code and create a run configuration for MyRetailProductServiceApplication.
2. Start the application. REST APIs for retrieving product by id and updating price of a product are now available at localhost:8080.

#### Test application using POSTMAN
1. Download POSTMAN if not installed already.
2. Import REST endpoint request collection **myretail.postman_collection.json** available in the project directory **postman-request**
3. Test the GET and PUT endpoints.




