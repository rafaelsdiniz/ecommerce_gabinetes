package br.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
public class GabineteResourceTest {

    @Test
    @Order(1)
    public void testListarTodosGabinetes() {
        given()
            .when().get("/gabinetes")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON);
    }

    @Test
    @Order(2)
    public void testCriarGabinete() {
        String gabineteJson = """
            {
                "nome": "Gabinete Gamer RGB",
                "descricao": "Gabinete gamer com iluminação RGB",
                "marca": "Cooler Master",
                "preco": 599.90,
                "cor": "Preto",
                "formato": "Mid Tower",
                "categoriasIds": []
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(gabineteJson)
            .when().post("/gabinetes")
            .then()
            .statusCode(201)
            .body("nome", equalTo("Gabinete Gamer RGB"))
            .body("marca", equalTo("Cooler Master"))
            .body("preco", equalTo(599.90f));
    }

    @Test
    @Order(3)
    public void testBuscarGabinetePorId() {
        given()
            .when().get("/gabinetes/1")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("id", notNullValue());
    }

    @Test
    @Order(4)
    public void testBuscarGabineteInexistente() {
        given()
            .when().get("/gabinetes/99999")
            .then()
            .statusCode(404);
    }

    @Test
    @Order(5)
    public void testAtualizarGabinete() {
        String gabineteJson = """
            {
                "nome": "Gabinete Gamer RGB Atualizado",
                "descricao": "Gabinete gamer com iluminação RGB atualizado",
                "marca": "Cooler Master",
                "preco": 649.90,
                "cor": "Preto",
                "formato": "Mid Tower",
                "categoriasIds": []
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(gabineteJson)
            .when().put("/gabinetes/1")
            .then()
            .statusCode(200)
            .body("nome", equalTo("Gabinete Gamer RGB Atualizado"))
            .body("preco", equalTo(649.90f));
    }

    @Test
    @Order(6)
    public void testBuscarPorMarca() {
        given()
            .queryParam("marca", "Cooler Master")
            .when().get("/gabinetes/buscar")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON);
    }

    @Test
    @Order(7)
    public void testBuscarPorFaixaPreco() {
        given()
            .queryParam("precoMin", 500)
            .queryParam("precoMax", 700)
            .when().get("/gabinetes/buscar")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON);
    }

    @Test
    @Order(8)
    public void testBuscarPorCor() {
        given()
            .queryParam("cor", "Preto")
            .when().get("/gabinetes/buscar")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON);
    }

    @Test
    @Order(9)
    public void testBuscarPorFormato() {
        given()
            .queryParam("formato", "Mid Tower")
            .when().get("/gabinetes/buscar")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON);
    }

    @Test
    @Order(10)
    public void testBuscarPorNome() {
        given()
            .queryParam("nome", "Gamer")
            .when().get("/gabinetes/buscar")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON);
    }

    @Test
    @Order(11)
    public void testOrdenarPorPrecoAsc() {
        given()
            .queryParam("ordenarPor", "preco")
            .queryParam("ordem", "asc")
            .when().get("/gabinetes/buscar")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON);
    }

    @Test
    @Order(12)
    public void testOrdenarPorPrecoDesc() {
        given()
            .queryParam("ordenarPor", "preco")
            .queryParam("ordem", "desc")
            .when().get("/gabinetes/buscar")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON);
    }

    @Test
    @Order(13)
    public void testDeletarGabinete() {
        given()
            .when().delete("/gabinetes/1")
            .then()
            .statusCode(204);
    }

    @Test
    @Order(14)
    public void testCriarGabineteSemNome() {
        String gabineteJson = """
            {
                "descricao": "Gabinete sem nome",
                "marca": "Teste",
                "preco": 100.00,
                "cor": "Branco",
                "formato": "Mini Tower",
                "categoriasIds": []
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(gabineteJson)
            .when().post("/gabinetes")
            .then()
            .statusCode(400);
    }

    @Test
    @Order(15)
    public void testCriarGabineteComPrecoNegativo() {
        String gabineteJson = """
            {
                "nome": "Gabinete Teste",
                "descricao": "Teste",
                "marca": "Teste",
                "preco": -100.00,
                "cor": "Branco",
                "formato": "Mini Tower",
                "categoriasIds": []
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(gabineteJson)
            .when().post("/gabinetes")
            .then()
            .statusCode(400);
    }
}
