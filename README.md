# realtime-price-monitor
index price monitoring Rest API

Apache Maven 3.6.2 
Java version: 1.8.0_181

Steps to setup project

1. git clone https://github.com/Gyan0522/realtime-price-monitor.git

   cd realtime-price-monitor/

2. mvn install

3. mvn package && java -jar target/pricemonitor-0.1.0.jar

3. mvn -N io.takari:maven:wrapper

5. ./mvnw clean spring-boot:run


Note :
While setting up the project , ensure that maven is installed
steps to export the maven path from cmd line: 

Maven path setup 
$ export M2_HOME=/path to maven/apache-maven-3.6.2 
$ export M2=$M2_HOME/bin
$ export MAVEN_OPTS=-Xms256m -Xmx512m
$ export PATH=$M2:$PATH 

API Endpoint : 

GET
1. http://localhost:8080/statistics
2. http://localhost:8080/statistics/IBM.N

POST

3. http://localhost:8080/ticks

Body:
      {
      "instrument": "IBM.N",
      "price": 143.82,
      "timestamp": 1478192204000
      }



