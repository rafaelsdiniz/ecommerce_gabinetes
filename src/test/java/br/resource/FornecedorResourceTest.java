package br.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import br.dto.request.FornecedorRequestDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FornecedorResourceTest {

    @Test
    @Order(1)
    public void testCreateFornecedor() {
        FornecedorRequestDTO dto = new FornecedorRequestDTO(
            "Fornecedor Teste",
            "fornecedor@teste.com",
            "11999999999",
            "12345678000190"
        );

        given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/fornecedores")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("nome", is("Fornecedor Teste"))
            .body("cnpj", is("12345678000190"));
    }

    @Test
    @Order(2)
    public void testFindAllFornecedores() {
        given()
        .when()
            .get("/fornecedores")
        .then()
            .statusCode(200)
            .body("size()", is(1));
    }

    @Test
    @Order(3)
    public void testFindFornecedorById() {
        given()
            .pathParam("id", 1)
        .when()
            .get("/fornecedores/{id}")
        .then()
            .statusCode(200)
            .body("nome", is("Fornecedor Teste"));
    }

    @Test
    @Order(4)
    public void testFindFornecedorByCnpj() {
        given()
            .pathParam("cnpj", "12345678000190")
        .when()
            .get("/fornecedores/cnpj/{cnpj}")
        .then()
            .statusCode(200)
            .body("nome", is("Fornecedor Teste"));
    }

    @Test
    @Order(5)
    public void testUpdateFornecedor() {
        FornecedorRequestDTO dto = new FornecedorRequestDTO(
            "Fornecedor Atualizado",
            "fornecedor.novo@teste.com",
            "11988888888",
            "12345678000190"
        );

        given()
            .contentType(ContentType.JSON)
            .pathParam("id", 1)
            .body(dto)
        .when()
            .put("/fornecedores/{id}")
        .then()
            .statusCode(200)
            .body("nome", is("Fornecedor Atualizado"));
    }

    @Test
    @Order(6)
    public void testDeleteFornecedor() {
        given()
            .pathParam("id", 1)
        .when()
            .delete("/fornecedores/{id}")
        .then()
            .statusCode(204);
    }

    @Test
    @Order(7)
    public void testCreateFornecedorWithInvalidData() {
        FornecedorRequestDTO dto = new FornecedorRequestDTO(
            "",
            "email-invalido",
            "",
            ""
        );

        given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/fornecedores")
        .then()
            .statusCode(400);
    }
}
