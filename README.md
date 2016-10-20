# hw6-microservices
the microservices assignment



There are 2 services- Items (containing items and their description) and Prices (containing items and their prices)

To run, follow the sequence:




java -jar commerce-eureka/target/commerce-eureka-0.0.1-SNAPSHOT.jar


java -jar commerce-config-server/target/commerce-config-server-0.0.1-SNAPSHOT.jar


java -jar commerce-hystrix-dashboard/target/commerce-hystrix-dashboard-0.0.1-SNAPSHOT.jar


java -jar commerce-item-service/target/commerce-item-service-0.0.1-SNAPSHOT.jar


java -jar commerce-price-service/target/commerce-price-service-0.0.1-SNAPSHOT.jar


java -jar commerce-ui/target/commerce-ui-0.0.1-SNAPSHOT.jar


