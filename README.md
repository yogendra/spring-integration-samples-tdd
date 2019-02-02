# Spring Integration Samples

This project have sample code for working with spring intergration. I am using TDD approach to run 
spring integration flows. This way I can keep a single repository for almost all samples. (I think 
so)


## How to use?

Plain on clone-cd-mvn.

```bash

git clone https://github.com/yogendra/spring-integration-samples-tdd.git
cd spring-integration-samples-tdd
./mvnw clean install

```
## Samples
 
### Step 01: Basic Message Related Operations

[Code](src/test/java/me/yogendra/samples/springintegrationsamples/Step01BasicTests.java)

This shows how to create messages and add headers.


### Step 02: Build Pattern for Message

[Code](src/test/java/me/yogendra/samples/springintegrationsamples/Step02MessageBuilderTest.java)

This shows how to use MessageBuilder to create messages. 


### Step 03: Connect Via Channels

[Code](src/test/java/me/yogendra/samples/springintegrationsamples/Step03ConnectViaChannelTest.java)

* Create a Channel
* Send Message to channel
* Check message structure received over channel


### Step 03: Create IntegrationFlow

[Code](src/test/java/me/yogendra/samples/springintegrationsamples/Step03IntegrationFlow.java)

* Create a new channel
* Create a new Integration Flow using IntegrationFlows class
* Send message to input channel
* Verify that message has reached print service


### Step 04: Subflow



 