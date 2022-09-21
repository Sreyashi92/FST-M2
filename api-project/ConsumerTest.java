package liveProject;


import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;


import static io.restassured.RestAssured.given;

@ExtendWith(PactConsumerTestExt.class)
public class ConsumerTest {

    //Headers
    Map<String,String> reqHeaders= new HashMap<>();

    //Resource Path

    String resourcePath ="/api/users";

    //Create the contract

    @Pact(consumer = "UserConsumer", provider = "UserProvider")
    public RequestResponsePact createPact(PactDslWithProvider builder){

        //Set the Headers
        reqHeaders.put("Content-Type","application/json");

        //Create the JSON body
        DslPart reqResBody = new PactDslJsonBody()
                .numberType("id")
                .stringType("firstName")
                .stringType("lastName")
                .stringType("email");


        return builder.given("Request to create a user")
                .uponReceiving("Request to create a user")
                    .method("POST")
                    .headers(reqHeaders)
                    .path(resourcePath)
                    .body(reqResBody)
                .willRespondWith()
                    .status(201)
                    .body(reqResBody)
                .toPact();


    }

    @Test
    @PactTestFor(providerName = "UserProvider",port="8282")
    public void consumerTest(){

        //Set BaseURI

        String baseURI = "http://localhost:8282";

        //Request body
        Map<String,Object>reqBody= new HashMap<>();
        reqBody.put("id",123456);
        reqBody.put("firstName", "Sreya");
        reqBody.put("lastName","Hazra");
        reqBody.put("email","shazra@gmail.com");

        //Generate Response

        given().headers(reqHeaders).body(reqBody).         //Request Specification
                when().post(baseURI + resourcePath).    //Post Response
                then().log().all().statusCode(201);     //Assertion



    }

}
