package br.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
public class PedidoResourceTest {

    @Test
    @Order(1)
    public void testListarTodosPedidos() {
        given()
            .when().get("/pedidos")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON);
    }

    @Test
    @Order(2)
    public void testCriarPedido() {
        String pedidoJson = """
            {
                "clienteId": 1,
                "enderecoEntrega": {
                    "logradouro": "Rua Teste",
                    "numero": "123",
                    "complemento": "Apto 1",
                    "bairro": "Centro",
                    "cidade": "São Paulo",
                    "estado": "SP",
                    "cep": "01234567"
                },
                "itens": [
                    {
                        "gabineteId": 1,
                        "quantidade": 2,
                        "precoUnitario": 599.90
                    }
                ]
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(pedidoJson)
            .when().post("/pedidos")
            .then()
            .statusCode(201)
            .body("clienteId", equalTo(1))
            .body("status", equalTo("PENDENTE"));
    }

    @Test
    @Order(3)
    public void testBuscarPedidoPorId() {
        given()
            .when().get("/pedidos/1")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("id", notNullValue());
    }

    @Test
    @Order(4)
    public void testBuscarPedidoInexistente() {
        given()
            .when().get("/pedidos/99999")
            .then()
            .statusCode(404);
    }

    @Test
    @Order(5)
    public void testFinalizarPedido() {
        given()
            .when().put("/pedidos/1/finalizar")
            .then()
            .statusCode(200)
            .body("status", equalTo("FINALIZADO"));
    }

    @Test
    @Order(6)
    public void testCancelarPedido() {
        given()
            .when().put("/pedidos/1/cancelar")
            .then()
            .statusCode(200)
            .body("status", equalTo("CANCELADO"));
    }

    @Test
    @Order(7)
    public void testBuscarPedidosPorStatus() {
        given()
            .queryParam("status", "PENDENTE")
            .when().get("/pedidos/buscar")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON);
    }

    @Test
    @Order(8)
    public void testBuscarHistoricoCliente() {
        given()
            .when().get("/pedidos/cliente/1/historico")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON);
    }

    @Test
    @Order(9)
    public void testDeletarPedido() {
        given()
            .when().delete("/pedidos/1")
            .then()
            .statusCode(204);
    }

    @Test
    @Order(10)
    public void testCriarPedidoSemCliente() {
        String pedidoJson = """
            {
                "enderecoEntrega": {
                    "logradouro": "Rua Teste",
                    "numero": "123",
                    "bairro": "Centro",
                    "cidade": "São Paulo",
                    "estado": "SP",
                    "cep": "01234567"
                },
                "itens": []
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(pedidoJson)
            .when().post("/pedidos")
            .then()
            .statusCode(400);
    }

    @Test
    @Order(11)
    public void testCriarPedidoSemItens() {
        String pedidoJson = """
            {
                "clienteId": 1,
                "enderecoEntrega": {
                    "logradouro": "Rua Teste",
                    "numero": "123",
                    "bairro": "Centro",
                    "cidade": "São Paulo",
                    "estado": "SP",
                    "cep": "01234567"
                },
                "itens": []
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(pedidoJson)
            .when().post("/pedidos")
            .then()
            .statusCode(400);
    }

    @Test
    @Order(12)
    public void testFinalizarPedidoInexistente() {
        given()
            .when().put("/pedidos/99999/finalizar")
            .then()
            .statusCode(404);
    }

    @Test
    @Order(13)
    public void testCancelarPedidoInexistente() {
        given()
            .when().put("/pedidos/99999/cancelar")
            .then()
            .statusCode(404);
    }
}
