package br.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import br.dto.request.MarcaRequestDTO;
import br.dto.request.ModeloRequestDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ModeloResourceTest {

    @Test
    @Order(1)
    public void testCreateMarcaForModelo() {
        MarcaRequestDTO marcaDto = new MarcaRequestDTO("Marca para Modelo", "Descrição");

        given()
            .contentType(ContentType.JSON)
            .body(marcaDto)
        .when()
            .post("/marcas")
        .then()
            .statusCode(201);
    }

    @Test
    @Order(2)
    public void testCreateModelo() {
        ModeloRequestDTO dto = new ModeloRequestDTO("Modelo Teste", 1L);

        given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/modelos")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("nomeModelo", is("Modelo Teste"))
            .body("marcaId", is(1));
    }

    @Test
    @Order(3)
    public void testFindAllModelos() {
        given()
        .when()
            .get("/modelos")
        .then()
            .statusCode(200)
            .body("size()", is(1));
    }

    @Test
    @Order(4)
    public void testFindModelosByMarcaId() {
        given()
            .queryParam("marcaId", 1)
        .when()
            .get("/modelos")
        .then()
            .statusCode(200)
            .body("size()", is(1));
    }

    @Test
    @Order(5)
    public void testSearchModelosByNome() {
        given()
            .queryParam("nome", "Teste")
        .when()
            .get("/modelos")
        .then()
            .statusCode(200)
            .body("size()", is(1));
    }

    @Test
    @Order(6)
    public void testUpdateModelo() {
        ModeloRequestDTO dto = new ModeloRequestDTO("Modelo Atualizado", 1L);

        given()
            .contentType(ContentType.JSON)
            .pathParam("id", 1)
            .body(dto)
        .when()
            .put("/modelos/{id}")
        .then()
            .statusCode(200)
            .body("nomeModelo", is("Modelo Atualizado"));
    }

    @Test
    @Order(7)
    public void testDeleteModelo() {
        given()
            .pathParam("id", 1)
        .when()
            .delete("/modelos/{id}")
        .then()
            .statusCode(204);
    }
}
