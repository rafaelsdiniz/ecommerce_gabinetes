package br.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import br.dto.request.MarcaRequestDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MarcaResourceTest {

    @Test
    @Order(1)
    public void testCreateMarca() {
        MarcaRequestDTO dto = new MarcaRequestDTO(
            "Marca Teste",
            "Descrição da marca teste"
        );

        given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/marcas")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("nome", is("Marca Teste"));
    }

    @Test
    @Order(2)
    public void testFindAllMarcas() {
        given()
        .when()
            .get("/marcas")
        .then()
            .statusCode(200)
            .body("size()", is(1));
    }

    @Test
    @Order(3)
    public void testFindMarcaById() {
        given()
            .pathParam("id", 1)
        .when()
            .get("/marcas/{id}")
        .then()
            .statusCode(200)
            .body("nome", is("Marca Teste"));
    }

    @Test
    @Order(4)
    public void testUpdateMarca() {
        MarcaRequestDTO dto = new MarcaRequestDTO(
            "Marca Atualizada",
            "Nova descrição"
        );

        given()
            .contentType(ContentType.JSON)
            .pathParam("id", 1)
            .body(dto)
        .when()
            .put("/marcas/{id}")
        .then()
            .statusCode(200)
            .body("nome", is("Marca Atualizada"));
    }

    @Test
    @Order(5)
    public void testDeleteMarca() {
        given()
            .pathParam("id", 1)
        .when()
            .delete("/marcas/{id}")
        .then()
            .statusCode(204);
    }

    @Test
    @Order(6)
    public void testCreateMarcaWithInvalidData() {
        MarcaRequestDTO dto = new MarcaRequestDTO("", null);

        given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/marcas")
        .then()
            .statusCode(400);
    }
}
