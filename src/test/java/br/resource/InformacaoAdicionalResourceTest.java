package br.resource;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import br.dto.request.GabineteRequestDTO;
import br.dto.request.InformacaoAdicionalRequestDTO;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InformacaoAdicionalResourceTest {

    @Test
    @Order(1)
    public void testCreateGabineteForInfo() {
        GabineteRequestDTO gabineteDto = new GabineteRequestDTO(
            "Gabinete Teste",
            "Marca Teste",
            299.99,
            "Preto",
            "Mid Tower",
            450,
            210,
            5.5,
            350,
            165,
            3,
            2,
            1,
            "Gabinete para teste",
            "snksdjfnsdfnsdf", // imagemUrl
            new ArrayList<>()
        );

        given()
            .contentType(ContentType.JSON)
            .body(gabineteDto)
        .when()
            .post("/gabinetes")
        .then()
            .statusCode(201);
    }

    @Test
    @Order(2)
    public void testCreateInformacaoAdicional() {
        InformacaoAdicionalRequestDTO dto = new InformacaoAdicionalRequestDTO(
            "Compatibilidade",
            "Suporta placas ATX, Micro-ATX e Mini-ITX",
            1L
        );

        given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/informacoes-adicionais")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("titulo", is("Compatibilidade"))
            .body("gabineteId", is(1));
    }

    @Test
    @Order(3)
    public void testFindAllInformacoesAdicionais() {
        given()
        .when()
            .get("/informacoes-adicionais")
        .then()
            .statusCode(200)
            .body("size()", is(1));
    }

    @Test
    @Order(4)
    public void testFindInformacoesByGabineteId() {
        given()
            .queryParam("gabineteId", 1)
        .when()
            .get("/informacoes-adicionais")
        .then()
            .statusCode(200)
            .body("size()", is(1));
    }

    @Test
    @Order(5)
    public void testUpdateInformacaoAdicional() {
        InformacaoAdicionalRequestDTO dto = new InformacaoAdicionalRequestDTO(
            "Compatibilidade Atualizada",
            "Suporta todas as placas padrão ATX",
            1L
        );

        given()
            .contentType(ContentType.JSON)
            .pathParam("id", 1)
            .body(dto)
        .when()
            .put("/informacoes-adicionais/{id}")
        .then()
            .statusCode(200)
            .body("titulo", is("Compatibilidade Atualizada"));
    }

    @Test
    @Order(6)
    public void testDeleteInformacaoAdicional() {
        given()
            .pathParam("id", 1)
        .when()
            .delete("/informacoes-adicionais/{id}")
        .then()
            .statusCode(204);
    }
}
