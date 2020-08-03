package org.acme;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class GraphQLResourceTest {

  @Test
  public void test_graphql_request() {
    String requestBody = "{\"query\": \" {dummyQuery} \"}";

    given().body(requestBody).post("/graphql/").then().contentType(ContentType.JSON).log()
        .ifValidationFails(LogDetail.ALL).body("data.dummyQuery.size()", is(3)).statusCode(200);
  }

}