package br.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
public class CategoriaResourceTest {

    @Test
    @Order(1)
    public void testListarTodasCategorias() {
        given()
            .when().get("/categorias")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON);
    }

    @Test
    @Order(2)
    public void testCriarCategoria() {
        String categoriaJson = """
            {
                "nome": "Gaming",
                "descricao": "Gabinetes para jogos"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(categoriaJson)
            .when().post("/categorias")
            .then()
            .statusCode(201)
            .body("nome", equalTo("Gaming"))
            .body("descricao", equalTo("Gabinetes para jogos"));
    }

    @Test
    @Order(3)
    public void testBuscarCategoriaPorId() {
        given()
            .when().get("/categorias/1")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("id", notNullValue());
    }

    @Test
    @Order(4)
    public void testBuscarCategoriaInexistente() {
        given()
            .when().get("/categorias/99999")
            .then()
            .statusCode(404);
    }

    @Test
    @Order(5)
    public void testAtualizarCategoria() {
        String categoriaJson = """
            {
                "nome": "Gaming Pro",
                "descricao": "Gabinetes profissionais para jogos"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(categoriaJson)
            .when().put("/categorias/1")
            .then()
            .statusCode(200)
            .body("nome", equalTo("Gaming Pro"))
            .body("descricao", equalTo("Gabinetes profissionais para jogos"));
    }

    @Test
    @Order(6)
    public void testBuscarCategoriaPorNome() {
        given()
            .when().get("/categorias/nome/Gaming Pro")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("nome", equalTo("Gaming Pro"));
    }

    @Test
    @Order(7)
    public void testBuscarGabinetesPorCategoria() {
        given()
            .when().get("/categorias/1/gabinetes")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON);
    }

    @Test
    @Order(8)
    public void testDeletarCategoria() {
        given()
            .when().delete("/categorias/1")
            .then()
            .statusCode(204);
    }

    @Test
    @Order(9)
    public void testCriarCategoriaSemNome() {
        String categoriaJson = """
            {
                "descricao": "Categoria sem nome"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(categoriaJson)
            .when().post("/categorias")
            .then()
            .statusCode(400);
    }

    @Test
    @Order(10)
    public void testCriarCategoriaComNomeDuplicado() {
        String categoriaJson1 = """
            {
                "nome": "Profissional",
                "descricao": "Categoria 1"
            }
            """;

        String categoriaJson2 = """
            {
                "nome": "Profissional",
                "descricao": "Categoria 2"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(categoriaJson1)
            .when().post("/categorias")
            .then()
            .statusCode(201);

        given()
            .contentType(ContentType.JSON)
            .body(categoriaJson2)
            .when().post("/categorias")
            .then()
            .statusCode(400);
    }
}
