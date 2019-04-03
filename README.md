Before running make sure you have docker installed and your JAVA_HOME path variable set(also make sure ports 8080 and 27017 are free)
Also make sure you are logged into the default docker hub to pull the mongoDB image. Do 'docker login' in ther terminal to login

Navigate to the directory in the terminal and run the script runIt.bat (may need to alter it if not running windows)

runIt.bat performs a Maven clean/install on the code, spins up a docker container for the Mongo DB data store and 
    runs the Spring boot application/dependencies 
    
To access the API, go to http://localhost:8080/v2/products to view all the ProductIds that have prices associated with them
To view an API response enter http://localhost:8080/v2/products{productID}

To update a price, use Postman or a similar application to make a PUT request to http://localhost:8080/v2/products{productID}
    with a JSON string similar to this:
    {
        "id": 13860428,
        "price": 12.45,
        "currencyType": "USD"
    }
    
Responds with an error based on if GET returns nothing or you try and use PUT to update a record that doesnt exist in the data store

Unit tests exist in test/java/com.my.retail.myretailapi and can be ran from there
