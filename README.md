# spo-coding-challenge

* The app was built with Spring boot and Kotlin. The feature of the app is exposed through a JSON REST API

#Build
 * Run `mvn package` from the root directory
 * Run `java -jar target/cleaning-optimization-0.0.1-SNAPSHOT.jar`

#Usage

## Base url
http://locahost:8080

### Optimiser endpoint (POST)
/api/optimiser

####Sample payload and response
##### A response has an error(boolean) and message(string) attributes. A results attribute is returned only when optimisation succeeds. All responses will return the appropriate HTTP status code.

* Request payload<br/>
	```json 
	{ 
		"rooms": [35, 21, 17, 28],
		"senior": 10, 
		"junior": 6 
	 }
	 ```
	
* Response payload (success)
	```json 
		{
        		"error": false,
        		"message": "Successfully optimised resources",
            	"results": 
            		[
					{
						"senior": 3,
						"junior": 1
					},
					{
						"senior": 1,
						"junior": 2
					},
					{
						"senior": 2,
						"junior": 0
					},
					{
						"senior": 1,
						"junior": 3
					}
				]
        }
     ```
* Response payload (validation error)
	```json 
		{
            "error": true,
            "message": "err.building.room.cleaner.minCleaningCapacitySeniorIs1, err.building.room.cleaner.seniorCapacityShouldBeMoreThanJuniorCapacity"
        	}
     ```
 