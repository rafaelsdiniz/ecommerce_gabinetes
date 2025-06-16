package br.unitins.tp1.resource;

import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@QuarkusTest
public class MarcaResourceTest {


    private static final String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ1bml0aW5zLWp3dCIsInN1YiI6ImxlYW5kcmEiLCJncm91cHMiOlsiQWRtIl0sImV4cCI6MTc1MTgwMzg3MiwiaWF0IjoxNzQ5MjExODcyLCJqdGkiOiIyZDgwOTQyMy03Y2ZlLTRhOTktYTE4OS00ODk5NWRlYmViNzkifQ.iFMAWEvqQUvOGOu8QiKS4gNpJBMtAaKvzINzOGPmRbLreHKWLjN6SALQ54451CxENgCoh_a0DLa7Y2J2WfcqYifPMVM7Kj4nSSwLAv2OmEO6ZM063hkhZLisjqqkT7o2CXLber_b3TXWPyVytvzcgeyfQI--Gv_3MAhzXTDOr0bVBgNYW3qNU_APyBmhkdC8-7He2n0agenq7brBZxzJDmNsOUbuC1E4Tlt6bQ3rEjQMBcIOB39Jq4Jo2q800vj4VE-gbI9AHEMojXPAaF_2IpMwIc0_U7wa--pHCTmoMy0AiQzvtmNMMgdAncTX0O6acFEHx4Bb9OReohGNtQq5ZA";

    @Test
    public void testGetAllMarcas() {
        given()
            .header("Authorization", "Bearer " + TOKEN)
            .when().get("/marcas")
            .then()
            .statusCode(200);
    }

    @Test
    public void testCreateMarca() {
        String json = """
            {
                "nome": "Logitech"
            }
        """;

        given()
            .header("Authorization", "Bearer " + TOKEN)
            .contentType(ContentType.JSON)
            .body(json)
            .when()
            .post("/marcas")
            .then()
            .statusCode(201)
            .body("nome", is("Logitech"));
    }

    @Test
    public void testGetMarcaById() {
        String jsonCreate = """
            {
                "nome": "Razer"
            }
        """;

        int id = given()
            .header("Authorization", "Bearer " + TOKEN)
            .contentType(ContentType.JSON)
            .body(jsonCreate)
            .when()
            .post("/marcas")
            .then()
            .statusCode(201)
            .extract().path("id");

        given()
            .header("Authorization", "Bearer " + TOKEN)
            .when()
            .get("/marcas/" + id)
            .then()
            .statusCode(200)
            .body("id", is(id))
            .body("nome", is("Razer"));
    }

    @Test
    public void testUpdateMarca() {
        String jsonCreate = """
            {
                "nome": "HyperX"
            }
        """;

        int id = given()
            .header("Authorization", "Bearer " + TOKEN)
            .contentType(ContentType.JSON)
            .body(jsonCreate)
            .when()
            .post("/marcas")
            .then()
            .statusCode(201)
            .extract().path("id");

        String jsonUpdate = """
            {
                "nome": "Corsair"
            }
        """;

        given()
            .header("Authorization", "Bearer " + TOKEN)
            .contentType(ContentType.JSON)
            .body(jsonUpdate)
            .when()
            .put("/marcas/" + id)
            .then()
            .statusCode(204);

        given()
            .header("Authorization", "Bearer " + TOKEN)
            .when()
            .get("/marcas/" + id)
            .then()
            .statusCode(200)
            .body("nome", is("Corsair"));
    }

    @Test
    public void testDeleteMarca() {
        String jsonCreate = """
            {
                "nome": "HyperX"
            }
        """;

        int id = given()
            .header("Authorization", "Bearer " + TOKEN)
            .contentType(ContentType.JSON)
            .body(jsonCreate)
            .when()
            .post("/marcas")
            .then()
            .statusCode(201)
            .extract().path("id");

        given()
            .header("Authorization", "Bearer " + TOKEN)
            .when()
            .delete("/marcas/" + id)
            .then()
            .statusCode(204);
    }

    @Test
    public void testCreateMarcaInvalid() {
        String json = """
            {
                "nome": ""
            }
        """;

        given()
            .header("Authorization", "Bearer " + TOKEN)
            .contentType(ContentType.JSON)
            .body(json)
            .when()
            .post("/marcas")
            .then()
            .statusCode(400);
    }
}
