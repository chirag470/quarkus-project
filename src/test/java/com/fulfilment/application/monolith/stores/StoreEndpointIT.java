package com.fulfilment.application.monolith.stores;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@QuarkusTest
public class StoreEndpointIT {

  private static final String PATH = "store";

  @InjectMock
  LegacyStoreManagerGateway legacyStoreManagerGateway;

  @Test
  void crudOperations() {
    Integer id = given()
        .contentType(ContentType.JSON)
        .body("""
            {
              "name": "COPILOT_STORE",
              "quantityProductsInStock": 12
            }
            """)
        .when()
        .post(PATH)
        .then()
        .statusCode(201)
        .body("name", equalTo("COPILOT_STORE"))
        .body("quantityProductsInStock", equalTo(12))
        .extract()
        .path("id");

    given()
        .when()
        .get(PATH + "/" + id)
        .then()
        .statusCode(200)
        .body("name", equalTo("COPILOT_STORE"))
        .body("quantityProductsInStock", equalTo(12));

    given()
        .contentType(ContentType.JSON)
        .body("""
            {
              "name": "UPDATED_STORE",
              "quantityProductsInStock": 20
            }
            """)
        .when()
        .put(PATH + "/" + id)
        .then()
        .statusCode(200)
        .body("name", equalTo("UPDATED_STORE"))
        .body("quantityProductsInStock", equalTo(20));

    given()
        .contentType(ContentType.JSON)
        .body("""
            {
              "name": "PATCHED_STORE",
              "quantityProductsInStock": 25
            }
            """)
        .when()
        .patch(PATH + "/" + id)
        .then()
        .statusCode(200)
        .body("name", equalTo("PATCHED_STORE"))
        .body("quantityProductsInStock", equalTo(25));

    given()
        .when()
        .delete(PATH + "/" + id)
        .then()
        .statusCode(204);

    given()
        .when()
        .get(PATH + "/" + id)
        .then()
        .statusCode(404);
  }

  @Test
  void listsSeedStores() {
    given()
        .when()
        .get(PATH)
        .then()
        .statusCode(200)
        .body(containsString("TONSTAD"))
        .body(containsString("KALLAX"))
        .body(containsString("BESTÅ"));
  }

  @Test
  void rejectsCreatingStoreWithId() {
    clearInvocations(legacyStoreManagerGateway);

    given()
        .contentType(ContentType.JSON)
        .body("""
            {
              "id": 999,
              "name": "INVALID_STORE",
              "quantityProductsInStock": 10
            }
            """)
        .when()
        .post(PATH)
        .then()
        .statusCode(422);

    verify(legacyStoreManagerGateway, never()).createStoreOnLegacySystem(any(Store.class));
    verify(legacyStoreManagerGateway, never()).updateStoreOnLegacySystem(any(Store.class));
  }

  @Test
  void rejectsMissingStoreForUpdate() {
    given()
        .contentType(ContentType.JSON)
        .body("""
            {
              "name": "MISSING_STORE",
              "quantityProductsInStock": 10
            }
            """)
        .when()
        .put(PATH + "/999999")
        .then()
        .statusCode(404);

    verify(legacyStoreManagerGateway, never()).createStoreOnLegacySystem(any(Store.class));
    verify(legacyStoreManagerGateway, never()).updateStoreOnLegacySystem(any(Store.class));
  }
}
