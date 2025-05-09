package br.unitins.tp1.resource;

import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@QuarkusTest
public class PagamentoResourceTest {

    @Test
    public void testCreatePagamento() {

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
            .contentType(ContentType.JSON)
            .body(pagamentoJson)
        .when()
            .post("/pagamentos")
        .then()
            .statusCode(201);
    }

    @Test
    public void testGetAllPagamentos() {
        given()
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
            .contentType(ContentType.JSON)
            .body(pagamentoJson)
        .when()
            .post("/pagamentos")
        .then()
            .statusCode(201)
            .extract().path("id");

        given()
        .when()
            .get("/pagamentos/" + idPagamento)
        .then()
            .statusCode(200);
    }

    @Test
    public void testUpdatePagamento() {

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
            .contentType(ContentType.JSON)
            .body(pagamentoUpdate)
        .when()
            .put("/pagamentos/" + idPagamento)
        .then()
            .statusCode(204);
    }

    @Test
    public void testDeletePagamento() {

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
            .contentType(ContentType.JSON)
            .body(pagamentoJson)
        .when()
            .post("/pagamentos")
        .then()
            .statusCode(201)
            .extract().path("id");

        given()
        .when()
            .delete("/pagamentos/" + idPagamento)
        .then()
            .statusCode(204);
    }

    @Test
    public void testCreatePagamentoInvalid() {
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
            .contentType(ContentType.JSON)
            .body(pagamentoJson)
        .when()
            .post("/pagamentos")
        .then()
            .statusCode(404);
    }
}
