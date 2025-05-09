package br.unitins.tp1.resource;

import static org.hamcrest.CoreMatchers.is;
import static io.restassured.RestAssured.given;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class PedidoResourceTest {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Test
    public void testCreatePedido() {
    
        String json = """
            {
              "clienteId": 1,
              "dataPedido": "2023-11-20T10:00:00",
              "itens": [
                {
                  "idGabinete": 1,
                  "quantidade": 2,
                  "precoUnitario": 150.00
                },
                {
                  "idGabinete": 2,
                  "quantidade": 1,
                  "precoUnitario": 250.00
                }
              ],
              "status": "PROCESSANDO",
              "endereco": {
                "estado": "SP",
                "cidade": "São Paulo",
                "bairro": "Centro",
                "cep": "01001000",
                "numero": "100",
                "logradouro": "Rua Exemplo",
                "complemento": "Sala 10"
              }
            }
            """;
    
        given()
            .contentType(ContentType.JSON)
            .body(json)
        .when()
            .post("/pedidos")
        .then()
            .statusCode(201)
            .body("cliente.id", is(1))  // ID fixo de cliente
            .body("itens.size()", is(2))
            .body("status", is("PROCESSANDO"))
            .body("valorTotal", is(550.00F));
    }
    
    @Test
    public void testGetPedidoById() {

        String dataPedido = LocalDateTime.now().format(formatter);

        String json = """
            {
              "clienteId": 1,
              "dataPedido": "%s",
              "itens": [
                {
                  "idGabinete": 1,
                  "quantidade": 2,
                  "precoUnitario": 300.00
                }
              ],
              "status": "PROCESSANDO",
              "endereco": {
                "estado": "TO",
                "cidade": "Palmas",
                "bairro": "Centro",
                "cep": "77000000",
                "numero": "123",
                "logradouro": "Av. JK"
              }
            }
        """.formatted(dataPedido);

        int id = given()
            .contentType(ContentType.JSON)
            .body(json)
        .when()
            .post("/pedidos")
        .then()
            .statusCode(201)
            .extract().path("id");

        given()
            .when().get("/pedidos/" + id)
            .then()
            .statusCode(200)
            .body("id", is(id))
            .body("cliente.id", is(1)) // ID fixo de cliente
            .body("status", is("PROCESSANDO"));
    }

    @Test
    public void testUpdatePedido() {

        String dataPedido = LocalDateTime.now().format(formatter);

        String jsonCreate = """
            {
              "clienteId": 1,
              "dataPedido": "%s",
              "itens": [
                {
                  "idGabinete": 1,
                  "quantidade": 2,
                  "precoUnitario": 150.00
                },
                {
                  "idGabinete": 2,
                  "quantidade": 1,
                  "precoUnitario": 250.00
                }
              ],
              "status": "PROCESSANDO",
              "endereco": {
                "estado": "SP",
                "cidade": "São Paulo",
                "bairro": "Centro",
                "cep": "01001000",
                "numero": "100",
                "logradouro": "Rua Exemplo",
                "complemento": "Sala 10"
              }
            }
        """.formatted(dataPedido);

        int id = given()
            .contentType(ContentType.JSON)
            .body(jsonCreate)
        .when()
            .post("/pedidos")
        .then()
            .statusCode(201)
            .extract().path("id");

        String jsonUpdate = jsonCreate.replace("\"status\": \"PROCESSANDO\"", "\"status\": \"ENVIADO\"");

        given()
            .contentType(ContentType.JSON)
            .body(jsonUpdate)
        .when()
            .put("/pedidos/" + id)
        .then()
            .statusCode(204);

        given()
            .when().get("/pedidos/" + id)
            .then()
            .statusCode(200)
            .body("status", is("ENVIADO"))
            .body("valorTotal", is(550.00F));
    }

    @Test
    public void testDeletePedido() {

        String dataPedido = LocalDateTime.now().format(formatter);

        String json = """
            {
              "clienteId": 1,
              "dataPedido": "%s",
              "itens": [
                {
                  "idGabinete": 1,
                  "quantidade": 2,
                  "precoUnitario": 300.00
                }
              ],
              "status": "PROCESSANDO",
              "endereco": {
                "estado": "SP",
                "cidade": "São Paulo",
                "bairro": "Centro",
                "cep": "01001000",
                "numero": "100",
                "logradouro": "Rua Exemplo",
                "complemento": "Sala 10"
              }
            }
        """.formatted(dataPedido);

        int id = given()
            .contentType(ContentType.JSON)
            .body(json)
        .when()
            .post("/pedidos")
        .then()
            .statusCode(201)
            .extract().path("id");

        given()
            .when().delete("/pedidos/" + id)
            .then()
            .statusCode(204);
    }

    @Test
    public void testCreatePedidoInvalid() {
        String json = """
            {
              "clienteId": 0,
              "dataPedido": "",
              "itens": [],
              "status": "",
              "endereco": {
                "estado": "",
                "cidade": "",
                "bairro": "",
                "cep": "",
                "numero": "",
                "logradouro": ""
              }
            }
        """; 

        given()
            .contentType(ContentType.JSON)
            .body(json)
        .when()
            .post("/pedidos")
        .then()
            .statusCode(400);
    }
}
