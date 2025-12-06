package br.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
public class EstoqueResourceTest {

    @Test
    @Order(1)
    public void testListarTodosEstoques() {
        given()
            .when().get("/estoques")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON);
    }

    @Test
    @Order(2)
    public void testBuscarEstoquePorId() {
        given()
            .when().get("/estoques/1")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("id", notNullValue());
    }

    @Test
    @Order(3)
    public void testBuscarEstoqueInexistente() {
        given()
            .when().get("/estoques/99999")
            .then()
            .statusCode(404);
    }

    @Test
    @Order(4)
    public void testBuscarEstoquePorGabinete() {
        given()
            .when().get("/estoques/gabinete/1")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON);
    }

    @Test
    @Order(5)
    public void testAdicionarEstoque() {
        given()
            .queryParam("quantidade", 10)
            .when().put("/estoques/1/adicionar")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON);
    }

    @Test
    @Order(6)
    public void testAdicionarEstoqueQuantidadeInvalida() {
        given()
            .queryParam("quantidade", -5)
            .when().put("/estoques/1/adicionar")
            .then()
            .statusCode(400);
    }

    @Test
    @Order(7)
    public void testRemoverEstoque() {
        given()
            .queryParam("quantidade", 5)
            .when().put("/estoques/1/remover")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON);
    }

    @Test
    @Order(8)
    public void testRemoverEstoqueQuantidadeInvalida() {
        given()
            .queryParam("quantidade", -5)
            .when().put("/estoques/1/remover")
            .then()
            .statusCode(400);
    }

    @Test
    @Order(9)
    public void testRemoverEstoqueInsuficiente() {
        given()
            .queryParam("quantidade", 99999)
            .when().put("/estoques/1/remover")
            .then()
            .statusCode(400);
    }

    @Test
    @Order(10)
    public void testVerificarDisponibilidade() {
        given()
            .queryParam("quantidade", 5)
            .when().get("/estoques/1/disponibilidade")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("disponivel", notNullValue());
    }

    @Test
    @Order(11)
    public void testListarEstoqueBaixo() {
        given()
            .queryParam("limite", 10)
            .when().get("/estoques/baixo")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON);
    }

    @Test
    @Order(12)
    public void testDeletarEstoque() {
        given()
            .when().delete("/estoques/1")
            .then()
            .statusCode(204);
    }
}
