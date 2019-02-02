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
 
### Step 01: Basic Message

[Code](src/test/java/me/yogendra/samples/springintegrationsamples/Step01BasicTests.java)

* Create Message
* Add header fields
* Examine ID and timsamp in message header


### Step 02: Use Message Builder to create Message

[Code](src/test/java/me/yogendra/samples/springintegrationsamples/Step02MessageBuilderTest.java)

This shows how to use MessageBuilder to create messages. 

### Step 03: Connect Via Channels

[Code](src/test/java/me/yogendra/samples/springintegrationsamples/Step03ConnectViaChannelTest.java)

* Create a Channel
* Send Message to channel
* Check message structure received over channel


### Step 04: Service Activator

[Code](src/test/java/me/yogendra/samples/springintegrationsamples/Step04ServiceActivatorTest.java)

* Create your own service
* Attach service to a channel
* Send message over the channel

### Step 05: Send Response from Service

[Code](src/test/java/me/yogendra/samples/springintegrationsamples/Step05ServiceActivatorWithResponseTest.java)

* Receive a message in your service 
* Create a new message and return
* Receive mesasge via output channel

### Step 06: Create IntegrationFlow

[Code](src/test/java/me/yogendra/samples/springintegrationsamples/Step06IntegrationFlow.java)

* Create Integration flow
* Send message over the input channel
* Verify message received over output channel

### Step 07: Gateway

[Code](src/test/java/me/yogendra/samples/springintegrationsamples/Step07GatewayTest.java)

* Create a Gateway interface
* Create Integration flow
* Attach Integration flow to Gateway via channel


 