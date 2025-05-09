package br.unitins.tp1.resource;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@QuarkusTest
public class ClienteFisicoResourceTest {

    @Test
    public void testSalvarClienteFisico() {
        String json = """
            {
                "nome": "João",
                "sobreNome": "Silva",
                "idade": 30,
                "email": "joao.silva@example.com",
                "cpf": "12345678901",
                "telefone": "63984592035"
            }
        """;

        given()
            .contentType(ContentType.JSON)
            .body(json)
        .when()
            .post("/clientes/fisicos")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("nome", is("João"))
            .body("cpf", is("12345678901"));
    }

    @Test
    public void testAtualizarClienteFisico() {
        String jsonCreate = """
            {
                "nome": "Carlos",
                "sobreNome": "Pereira",
                "idade": 40,
                "email": "carlos.pereira@example.com",
                "cpf": "98765432100",
                "telefone": "63984596984"
            }
        """;

        int id = given()
            .contentType(ContentType.JSON)
            .body(jsonCreate)
        .when()
            .post("/clientes/fisicos")
        .then()
            .statusCode(201)
            .extract().path("id");

        String jsonUpdate = """
            {
                "nome": "Carlos Alberto",
                "sobreNome": "Pereira",
                "idade": 42,
                "email": "carlos.alberto.pereira@example.com",
                "cpf": "98765432100",
                "telefone": "63985592035"
            }
        """;

        given()
            .contentType(ContentType.JSON)
            .body(jsonUpdate)
        .when()
            .put("/clientes/fisicos/" + id)
        .then()
            .statusCode(204);

        given()
            .when().get("/clientes/fisicos/" + id)
        .then()
            .statusCode(200)
            .body("nome", is("Carlos Alberto"))
            .body("idade", is(42));
    }

    @Test
    public void testBuscarPorIdClienteFisico() {
        String json = """
            {
                "nome": "Ana",
                "sobreNome": "Souza",
                "idade": 25,
                "email": "ana.souza@example.com",
                "cpf": "33344455566",
                "telefone": "63984592035"
            }
        """;

        int id = given()
            .contentType(ContentType.JSON)
            .body(json)
        .when()
            .post("/clientes/fisicos")
        .then()
            .statusCode(201)
            .extract().path("id");

        given()
            .when()
            .get("/clientes/fisicos/" + id)
        .then()
            .statusCode(200)
            .body("nome", is("Ana"))
            .body("cpf", is("33344455566"));
    }

    @Test
    public void testListarTodos4() {
        given()
            .when()
            .get("/clientes/fisicos")
        .then()
            .statusCode(200);
    }

    @Test
    public void testDeletarClienteFisico() {
        String json = """
            {
                "nome": "Pedro",
                "sobreNome": "Oliveira",
                "idade": 35,
                "email": "pedro.oliveira@example.com",
                "cpf": "55667788990",
                "telefone": "63984592035"
            }
        """;

        int id = given()
            .contentType(ContentType.JSON)
            .body(json)
        .when()
            .post("/clientes/fisicos")
        .then()
            .statusCode(201)
            .extract().path("id");

        given()
            .when()
            .delete("/clientes/fisicos/" + id)
        .then()
            .statusCode(204);

        given()
            .when()
            .get("/clientes/fisicos/" + id)
        .then()
            .statusCode(404);
    }

    @Test
    public void testCriarClienteFisicoInvalido() {
        String json = """
            {
                "nome": "",
                "sobreNome": "Souza",
                "idade": 28,
                "email": "invalido@example.com",
                "cpf": "12345678900",
                "telefone": "63984592035"
            }
        """;

        given()
            .contentType(ContentType.JSON)
            .body(json)
        .when()
            .post("/clientes/fisicos")
        .then()
            .statusCode(400);
    }
}
