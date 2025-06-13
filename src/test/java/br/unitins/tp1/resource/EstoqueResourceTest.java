package br.unitins.tp1.resource;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@QuarkusTest
@TestMethodOrder(OrderAnnotation.class)
public class EstoqueResourceTest {

    private static final String ADMIN_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ1bml0aW5zLWp3dCIsInN1YiI6ImxlYW5kcmEiLCJncm91cHMiOlsiQWRtIl0sImV4cCI6MTc1MTgwMzg3MiwiaWF0IjoxNzQ5MjExODcyLCJqdGkiOiIyZDgwOTQyMy03Y2ZlLTRhOTktYTE4OS00ODk5NWRlYmViNzkifQ.iFMAWEvqQUvOGOu8QiKS4gNpJBMtAaKvzINzOGPmRbLreHKWLjN6SALQ54451CxENgCoh_a0DLa7Y2J2WfcqYifPMVM7Kj4nSSwLAv2OmEO6ZM063hkhZLisjqqkT7o2CXLber_b3TXWPyVytvzcgeyfQI--Gv_3MAhzXTDOr0bVBgNYW3qNU_APyBmhkdC8-7He2n0agenq7brBZxzJDmNsOUbuC1E4Tlt6bQ3rEjQMBcIOB39Jq4Jo2q800vj4VE-gbI9AHEMojXPAaF_2IpMwIc0_U7wa--pHCTmoMy0AiQzvtmNMMgdAncTX0O6acFEHx4Bb9OReohGNtQq5ZA";

    private static Long idEstoqueCriado;

    @Test
    @Order(1)
    public void testCriarEstoqueValido() {
        String json = """
            {
                "gabineteId": 11,
                "quantidadeDisponivel": 100
            }
        """;

        idEstoqueCriado =
        given()
            .header("Authorization", "Bearer " + ADMIN_TOKEN)
            .contentType(ContentType.JSON)
            .body(json)
        .when()
            .post("/estoques")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("gabineteId", is(11))
            .body("quantidadeDisponivel", is(100))
            .extract()
            .jsonPath()
            .getLong("id");
    }

    @Test
    @Order(2)
    public void testListarTodosEstoques() {
        given()
            .header("Authorization", "Bearer " + ADMIN_TOKEN)
        .when()
            .get("/estoques")
        .then()
            .statusCode(200);
    }

    @Test
    @Order(3)
    public void testBuscarEstoquePorId() {
        given()
            .header("Authorization", "Bearer " + ADMIN_TOKEN)
        .when()
            .get("/estoques/" + idEstoqueCriado)
        .then()
            .statusCode(200)
            .body("id", is(idEstoqueCriado.intValue()))
            .body("gabineteId", is(11));
    }

    @Test
    @Order(4)
    public void testAtualizarEstoque() {
        String jsonUpdate = """
            {
                "gabineteId": 11,
                "quantidadeDisponivel": 150
            }
        """;

        given()
            .header("Authorization", "Bearer " + ADMIN_TOKEN)
            .contentType(ContentType.JSON)
            .body(jsonUpdate)
        .when()
            .put("/estoques/" + idEstoqueCriado)
        .then()
            .statusCode(200)
            .body("quantidadeDisponivel", is(150));
    }

    @Test
    @Order(5)
    public void testDeletarEstoque() {
        given()
            .header("Authorization", "Bearer " + ADMIN_TOKEN)
        .when()
            .delete("/estoques/" + idEstoqueCriado)
        .then()
            .statusCode(204);
    }
    
    @Test
    @Order(6)
    public void testBuscarEstoqueInexistente() {
        long idInexistente = 99999L;

        given()
            .header("Authorization", "Bearer " + ADMIN_TOKEN)
        .when()
            .get("/estoques/" + idInexistente)
        .then()
            .statusCode(404);
    }
}
