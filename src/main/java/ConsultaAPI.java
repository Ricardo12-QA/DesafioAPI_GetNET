import io.restassured.http.ContentType;
import org.junit.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasSize;

public class ConsultaAPI extends BaseClass {
    @Test
    public void registerUserSuccess() {
        HashMap<String, String> register = new HashMap<>();
        register.put("email", "michael.lawson@reqres.in");
        register.put("password", "teste0");

        String token = given()
                .body(register)
                .when()
                .post("register")
                .then()
                .statusCode(200)
                .body("id", is(7))
                .extract().path("token");
    }

    @Test
    public void registerUserFail() {
        HashMap<String, String> register = new HashMap<>();
        register.put("email", "michael.lawson@reqres.in");

        given()
                .body(register)
                .when()
                .post("register")
                .then()
                .statusCode(400)
                .body("error", is("Missing password"))
        ;
    }

    @Test
    public void loginUser() {
        String token = given()
                .body("{\"email\": \"eve.holt@reqres.in\",\"password\": \"cityslicka\"}")
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(200)
                .body(is(notNullValue()))
                .extract().path("token");
    }

    @Test
    public void loginUserFail() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"email\": \"eve.holt@reqres.in\"}")
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(400)
                .body("error", is("Missing password"))
        ;
    }

    @Test
    public void createUser() {
        given()
                .body("{\"name\": \"morpheus\",\"job\": \"leader\"}")
                .when()
                .post("users")
                .then()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"))
        ;
    }

    @Test
    public void buscaUser() {
        given()
                .pathParam("id", "7")
                .when()
                .get("users/{id}")
                .then()
                .statusCode(200)
                .body("data.first_name", is("Michael"))
                .body("data.last_name", is("Lawson"))
        ;
    }

    @Test
    public void editUser() {
        given()
                .body("{\"name\": \"morpheus\",\"job\": \"tester\"}")
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body("job", is("tester"))
                .body("name", is("morpheus"))
        ;
    }

    @Test
    public void listUsers() {
        given()
                .pathParam("page", "per_page=")
                .pathParam("num", 20)
                .when()
                .get("https://reqres.in/api/users?{page}={num}")
                .then()
                .statusCode(200)
                .body("data.email.findAll{it.startsWith('emma')}", hasItem("emma.wong@reqres.in"))
                .body("data.id", hasSize(12))
        ;
    }

    @Test
    public void deleteUser() {
        given()
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .statusCode(204)
        ;
    }
}
