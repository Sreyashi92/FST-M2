package liveProject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GitHubTest {

    RequestSpecification requestSpec;

    int SSHid;


    @BeforeClass

    public void setup() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://api.github.com/user/keys")
                .setContentType(ContentType.JSON)
                .addHeader("Authorization","token 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")
                .build();

    }

    @Test(priority = 1)

    public void postRequest(){


        String reqBody= "{\"title\": \"TestAPIKey\", \"key\": \"ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCrKZszx9Ve0eVxKB1uv0VSniCi5iUOKuyenRtFCMBTFeuG3NIyZFTYOoGkeoK04akXMjPOEqkMgi69qXps/vt9ct8Au9hhcdyQkiDLWc0rdwJ/qaCra0QrelcNsolCzBqBrjgZyBnV7N4IJXx5CxbV53jOeOTzsgRS052yf/d5z6iRwB3hQgkeK3UZqN5343OT+fH8RboVE7cc1MIjuJlcolCnyJp+7azKZ87PuukT8fZAxbmT8DBRDTijTniYs1UbZARMKn3i33e7HUsWlgy//Cpo6XN6bKckQHjydeCpv51GMdSorIEGuAqhhOwq9LHyEC4oq2+G+x1Y0DYPDg7X\"}";
        Response response= given().spec(requestSpec)
                .body(reqBody)
                .when().post();

        System.out.println(response.getBody().asString());
        SSHid=response.then().extract().path("id");

        response.then().statusCode(201);
    }

    @Test(priority = 2)


    public void getRequest(){

        Response response = given().spec(requestSpec)
                .pathParam("keyId",SSHid)
                .when().get("/{keyId}");

        System.out.println(response.getBody().asString());
        response.then().statusCode(200);
    }
    @Test(priority = 3)

    public void deleteRequest(){


        Response response = given().spec(requestSpec)
                .pathParam("keyId",SSHid)
                .when().delete("/{keyId}");

        System.out.println(response.getBody().asString());
        response.then().statusCode(204);


    }

}
