package br.unitins.tp1.resource;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@QuarkusTest
public class ClienteJuridicoResourceTest {

    private static final String TOKEN_FIXO = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ1bml0aW5zLWp3dCIsInN1YiI6ImxlYW5kcmEiLCJncm91cHMiOlsiQWRtIl0sImV4cCI6MTc1MTgwMzg3MiwiaWF0IjoxNzQ5MjExODcyLCJqdGkiOiIyZDgwOTQyMy03Y2ZlLTRhOTktYTE4OS00ODk5NWRlYmViNzkifQ.iFMAWEvqQUvOGOu8QiKS4gNpJBMtAaKvzINzOGPmRbLreHKWLjN6SALQ54451CxENgCoh_a0DLa7Y2J2WfcqYifPMVM7Kj4nSSwLAv2OmEO6ZM063hkhZLisjqqkT7o2CXLber_b3TXWPyVytvzcgeyfQI--Gv_3MAhzXTDOr0bVBgNYW3qNU_APyBmhkdC8-7He2n0agenq7brBZxzJDmNsOUbuC1E4Tlt6bQ3rEjQMBcIOB39Jq4Jo2q800vj4VE-gbI9AHEMojXPAaF_2IpMwIc0_U7wa--pHCTmoMy0AiQzvtmNMMgdAncTX0O6acFEHx4Bb9OReohGNtQq5ZA";

    @Test
    public void testSalvarClienteJuridico() {
        String json = """
            {
                "nomeFantasia": "Empresa Teste",
                "razaoSocial": "LTDA",
                "email": "contato@empresa.com",
                "telefone": "63984592037",
                "cnpj": "12345678000190"
            }
        """;

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(json)
        .when()
            .post("/clientes/juridicos")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("nomeFantasia", is("Empresa Teste"))
            .body("razaoSocial", is("LTDA"))
            .body("email", is("contato@empresa.com"))
            .body("telefone", is("63984592037"))
            .body("cnpj", is("12345678000190"));
    }

    @Test
    public void testAtualizarClienteJuridico() {
        String jsonCreate = """
            {
                "nomeFantasia": "Empresa Original",
                "razaoSocial": "ME",
                "email": "original@empresa.com",
                "telefone": "63980001111",
                "cnpj": "00111222000133"
            }
        """;

        int id = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(jsonCreate)
        .when()
            .post("/clientes/juridicos")
        .then()
            .statusCode(201)
            .extract().path("id");

        String jsonUpdate = """
            {
                "nomeFantasia": "Empresa Atualizada",
                "razaoSocial": "LTDA",
                "email": "atualizada@empresa.com",
                "telefone": "63980002222",
                "cnpj": "00111222000133"
            }
        """;

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(jsonUpdate)
        .when()
            .put("/clientes/juridicos/" + id)
        .then()
            .statusCode(204);

        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .get("/clientes/juridicos/" + id)
        .then()
            .statusCode(200)
            .body("nomeFantasia", is("Empresa Atualizada"))
            .body("razaoSocial", is("LTDA"))
            .body("email", is("atualizada@empresa.com"))
            .body("telefone", is("63980002222"))
            .body("cnpj", is("00111222000133"));
    }

    @Test
    public void testBuscarPorIdClienteJuridico() {
        String json = """
            {
                "nomeFantasia": "Empresa Alpha",
                "razaoSocial": "EIRELI",
                "email": "alpha@empresa.com",
                "telefone": "63980003333",
                "cnpj": "33333333000133"
            }
        """;

        int id = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(json)
        .when()
            .post("/clientes/juridicos")
        .then()
            .statusCode(201)
            .extract().path("id");

        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .get("/clientes/juridicos/" + id)
        .then()
            .statusCode(200)
            .body("nomeFantasia", is("Empresa Alpha"))
            .body("razaoSocial", is("EIRELI"))
            .body("email", is("alpha@empresa.com"))
            .body("telefone", is("63980003333"))
            .body("cnpj", is("33333333000133"));
    }

    @Test
    public void testListarTodos() {
        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .get("/clientes/juridicos")
        .then()
            .statusCode(200);
    }

    @Test
    public void testDeletarClienteJuridico() {
        String json = """
            {
                "nomeFantasia": "Empresa Delete",
                "razaoSocial": "LTDA",
                "email": "delete@empresa.com",
                "telefone": "63980004444",
                "cnpj": "44444444000144"
            }
        """;

        int id = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(json)
        .when()
            .post("/clientes/juridicos")
        .then()
            .statusCode(201)
            .extract().path("id");

        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .delete("/clientes/juridicos/" + id)
        .then()
            .statusCode(204);

        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .get("/clientes/juridicos/" + id)
        .then()
            .statusCode(404);
    }

    @Test
    public void testCriarClienteJuridicoInvalido() {
        String json = """
            {
                "nomeFantasia": "",
                "razaoSocial": "LTDA",
                "email": "invalido@empresa.com",
                "telefone": "63980005555",
                "cnpj": ""
            }
        """;

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(json)
        .when()
            .post("/clientes/juridicos")
        .then()
            .statusCode(400);
    }
}
