package br.unitins.tp1.resource;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@QuarkusTest
public class ClienteFisicoResourceTest {


    private static final String TOKEN_FIXO = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ1bml0aW5zLWp3dCIsInN1YiI6ImxlYW5kcmEiLCJncm91cHMiOlsiQWRtIl0sImV4cCI6MTc1MTgwMzg3MiwiaWF0IjoxNzQ5MjExODcyLCJqdGkiOiIyZDgwOTQyMy03Y2ZlLTRhOTktYTE4OS00ODk5NWRlYmViNzkifQ.iFMAWEvqQUvOGOu8QiKS4gNpJBMtAaKvzINzOGPmRbLreHKWLjN6SALQ54451CxENgCoh_a0DLa7Y2J2WfcqYifPMVM7Kj4nSSwLAv2OmEO6ZM063hkhZLisjqqkT7o2CXLber_b3TXWPyVytvzcgeyfQI--Gv_3MAhzXTDOr0bVBgNYW3qNU_APyBmhkdC8-7He2n0agenq7brBZxzJDmNsOUbuC1E4Tlt6bQ3rEjQMBcIOB39Jq4Jo2q800vj4VE-gbI9AHEMojXPAaF_2IpMwIc0_U7wa--pHCTmoMy0AiQzvtmNMMgdAncTX0O6acFEHx4Bb9OReohGNtQq5ZA";

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
            .header("Authorization", "Bearer " + TOKEN_FIXO)
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
            .header("Authorization", "Bearer " + TOKEN_FIXO)
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
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(jsonUpdate)
        .when()
            .put("/clientes/fisicos/" + id)
        .then()
            .statusCode(204);

        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .get("/clientes/fisicos/" + id)
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
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(json)
        .when()
            .post("/clientes/fisicos")
        .then()
            .statusCode(201)
            .extract().path("id");

        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
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
            .header("Authorization", "Bearer " + TOKEN_FIXO)
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
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(json)
        .when()
            .post("/clientes/fisicos")
        .then()
            .statusCode(201)
            .extract().path("id");

        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .delete("/clientes/fisicos/" + id)
        .then()
            .statusCode(204);

        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
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
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(json)
        .when()
            .post("/clientes/fisicos")
        .then()
            .statusCode(400);
    }
}
