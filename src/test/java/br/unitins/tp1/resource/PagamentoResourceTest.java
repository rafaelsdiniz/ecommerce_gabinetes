package br.unitins.tp1.resource;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@QuarkusTest
public class PagamentoResourceTest {

    private static final String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ1bml0aW5zLWp3dCIsInN1YiI6ImxlYW5kcmEiLCJncm91cHMiOlsiQWRtIl0sImV4cCI6MTc1MTgwMzg3MiwiaWF0IjoxNzQ5MjExODcyLCJqdGkiOiIyZDgwOTQyMy03Y2ZlLTRhOTktYTE4OS00ODk5NWRlYmViNzkifQ.iFMAWEvqQUvOGOu8QiKS4gNpJBMtAaKvzINzOGPmRbLreHKWLjN6SALQ54451CxENgCoh_a0DLa7Y2J2WfcqYifPMVM7Kj4nSSwLAv2OmEO6ZM063hkhZLisjqqkT7o2CXLber_b3TXWPyVytvzcgeyfQI--Gv_3MAhzXTDOr0bVBgNYW3qNU_APyBmhkdC8-7He2n0agenq7brBZxzJDmNsOUbuC1E4Tlt6bQ3rEjQMBcIOB39Jq4Jo2q800vj4VE-gbI9AHEMojXPAaF_2IpMwIc0_U7wa--pHCTmoMy0AiQzvtmNMMgdAncTX0O6acFEHx4Bb9OReohGNtQq5ZA";

    @Test
    public void testCriarPagamento() {

        String pagamentoJson = """
        {
            "pedidoId": 19,
            "formaPagamento": "CARTAO_CREDITO",
            "statusPagamento": "PENDENTE",
            "valor": 250.00,
            "data": "2023-11-20T15:30:00"
        }
        """;

        given()
            .header("Authorization", "Bearer " + TOKEN)
            .contentType(ContentType.JSON)
            .body(pagamentoJson)
        .when()
            .post("/pagamentos")
        .then()
            .statusCode(201);
    }

    @Test
    public void testListarTodosPagamentos() {
        given()
            .header("Authorization", "Bearer " + TOKEN)
        .when()
            .get("/pagamentos")
        .then()
            .statusCode(200);
    }

    @Test
    public void testGetPagamentoById() {

        String pagamentoJson = """
        {
            "pedidoId": 12,
            "formaPagamento": "BOLETO",
            "statusPagamento": "PENDENTE",
            "valor": 180.50,
            "data": "2023-11-21T10:00:00"
        }
        """;

        int idPagamento = given()
            .header("Authorization", "Bearer " + TOKEN)
            .contentType(ContentType.JSON)
            .body(pagamentoJson)
        .when()
            .post("/pagamentos")
        .then()
            .statusCode(201)
            .extract().path("id");

        given()
            .header("Authorization", "Bearer " + TOKEN)
        .when()
            .get("/pagamentos/" + idPagamento)
        .then()
            .statusCode(200);
    }

    @Test
    public void testAtualizarPagamento() {

        String pagamentoCreate = """
        {
            "pedidoId": 13,
            "formaPagamento": "CARTAO_DEBITO",
            "statusPagamento": "PENDENTE",
            "valor": 150.00,
            "data": "2023-11-22T14:00:00"
        }
        """;

        int idPagamento = given()
            .header("Authorization", "Bearer " + TOKEN)
            .contentType(ContentType.JSON)
            .body(pagamentoCreate)
        .when()
            .post("/pagamentos")
        .then()
            .statusCode(201)
            .extract().path("id");

        String pagamentoUpdate = """
        {
            "pedidoId": 13,
            "formaPagamento": "CARTAO_DEBITO",
            "statusPagamento": "APROVADO",
            "valor": 150.00,
            "data": "2023-11-22T14:30:00"
        }
        """;

        given()
            .header("Authorization", "Bearer " + TOKEN)
            .contentType(ContentType.JSON)
            .body(pagamentoUpdate)
        .when()
            .put("/pagamentos/" + idPagamento)
        .then()
            .statusCode(204);
    }

    @Test
    public void testDeletarPagamento() {

        String pagamentoJson = """
        {
            "pedidoId": 14,
            "formaPagamento": "PIX",
            "statusPagamento": "PENDENTE",
            "valor": 300.00,
            "data": "2023-11-23T16:00:00"
        }
        """;

        int idPagamento = given()
            .header("Authorization", "Bearer " + TOKEN)
            .contentType(ContentType.JSON)
            .body(pagamentoJson)
        .when()
            .post("/pagamentos")
        .then()
            .statusCode(201)
            .extract().path("id");

        given()
            .header("Authorization", "Bearer " + TOKEN)
        .when()
            .delete("/pagamentos/" + idPagamento)
        .then()
            .statusCode(204);
    }

    @Test
    public void testCriarPagamentoInvalido() {
        String pagamentoJson = """
        {
            "pedidoId": 9999,
            "formaPagamento": "CARTAO_CREDITO",
            "statusPagamento": "PENDENTE",
            "valor": 250.00,
            "data": "2023-11-20T15:30:00"
        }
        """;

        given()
            .header("Authorization", "Bearer " + TOKEN)
            .contentType(ContentType.JSON)
            .body(pagamentoJson)
        .when()
            .post("/pagamentos")
        .then()
            .statusCode(404);
    }
}
