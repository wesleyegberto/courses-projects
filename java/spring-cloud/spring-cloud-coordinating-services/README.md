# Course Spring Cloud - Coordinating Services from Pluralsight

# Module 2
Showing Eureka

### m2-eureka-server
Eureka Server running on port 8761.
URL: http://localhost:8761/

### m2-tollrate-service
Service running on random port, thus must be accessed using Eureka.
Paths:
* /tollrate/1
* /tollrate/2
* /tollrate/3

### m2-tollrate-service
Service running on random port.
Paths:
* /fastpass?fastpassid=100
* /fastpass?fastpassphone=555-123-4567

### m2-tollrate-billboard
* Dashboard: http://localhost:8081/dashboard?stationId=3

### m2-fastpass-console
* Dashboard: http://localhost:8082/customerdetails?fastpassid=100