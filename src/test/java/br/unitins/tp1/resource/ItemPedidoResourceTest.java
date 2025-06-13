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
public class ItemPedidoResourceTest {

    static Long idItemPedido;


    private static final String TOKEN_ADMIN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ1bml0aW5zLWp3dCIsInN1YiI6ImxlYW5kcmEiLCJncm91cHMiOlsiQWRtIl0sImV4cCI6MTc1MTgwMzg3MiwiaWF0IjoxNzQ5MjExODcyLCJqdGkiOiIyZDgwOTQyMy03Y2ZlLTRhOTktYTE4OS00ODk5NWRlYmViNzkifQ.iFMAWEvqQUvOGOu8QiKS4gNpJBMtAaKvzINzOGPmRbLreHKWLjN6SALQ54451CxENgCoh_a0DLa7Y2J2WfcqYifPMVM7Kj4nSSwLAv2OmEO6ZM063hkhZLisjqqkT7o2CXLber_b3TXWPyVytvzcgeyfQI--Gv_3MAhzXTDOr0bVBgNYW3qNU_APyBmhkdC8-7He2n0agenq7brBZxzJDmNsOUbuC1E4Tlt6bQ3rEjQMBcIOB39Jq4Jo2q800vj4VE-gbI9AHEMojXPAaF_2IpMwIc0_U7wa--pHCTmoMy0AiQzvtmNMMgdAncTX0O6acFEHx4Bb9OReohGNtQq5ZA";

    private void ensureItemPedidoExists() {

        if (idItemPedido == null) {
            testCriarItemPedidoValido();
        }
    }

    @Test
    @Order(1)
    public void testCriarItemPedidoValido() {
        String json = """
            {
                "idPedido": 1,
                "idGabinete": 1,
                "quantidade": 2,
                "precoUnitario": 350.00,
                "precoTotal": 700.00
            }
        """;

        idItemPedido = ((Number)
            given()
                .header("Authorization", "Bearer " + TOKEN_ADMIN)
                .contentType(ContentType.JSON)
                .body(json)
            .when()
                .post("/itempedido")
            .then()
                .statusCode(201)
                .body("id", notNullValue())
                .extract().path("id")
        ).longValue();
    }

    @Test
    @Order(2)
    public void testListarTodosItensPedido() {
        given()
            .header("Authorization", "Bearer " + TOKEN_ADMIN)
        .when()
            .get("/itempedido")
        .then()
            .statusCode(200);
    }

    @Test
    @Order(3)
    public void testBuscarItemPedidoPorId() {
        ensureItemPedidoExists();

        given()
            .header("Authorization", "Bearer " + TOKEN_ADMIN)
        .when()
            .get("/itempedido/" + idItemPedido)
        .then()
            .statusCode(200)
            .body("id", is(idItemPedido.intValue()));
    }

    @Test
    @Order(4)
    public void testAtualizarItemPedido() {
        ensureItemPedidoExists();

        String json = """
        {
            "idPedido": 1,
            "idGabinete": 1,
            "quantidade": 3,
            "precoUnitario": 400.00,
            "precoTotal": 1200.00
        }
        """;

        given()
            .header("Authorization", "Bearer " + TOKEN_ADMIN)
            .contentType(ContentType.JSON)
            .body(json)
        .when()
            .put("/itempedido/" + idItemPedido)
        .then()
            .statusCode(204);
    }

    @Test
    @Order(5)
    public void testDeletarItemPedido() {
        ensureItemPedidoExists();

        given()
            .header("Authorization", "Bearer " + TOKEN_ADMIN)
        .when()
            .delete("/itempedido/" + idItemPedido)
        .then()
            .statusCode(204);
    }

    @Test
    @Order(6)
    public void testCriarItemPedidoInvalido() {
        String json = """
        {
            "pedido": null,
            "gabinete": null,
            "quantidade": 0,
            "precoUnitario": -10,
            "precoTotal": -10
        }
        """;

        given()
            .header("Authorization", "Bearer " + TOKEN_ADMIN)
            .contentType(ContentType.JSON)
            .body(json)
        .when()
            .post("/itempedido")
        .then()
            .statusCode(400);
    }
}
