package br.unitins.tp1.resource;

import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@QuarkusTest
public class GabineteResourceTest {

    private Long idGabinete;

    @BeforeEach
    public void setup() {
        String json = """
            {
                "nomeExibicao": "Gabinete Gamer",
                "marca": "Corsair",
                "preco": 499.90,
                "cor": "Preto",
                "formato": "ATX",
                "altura": 450,
                "largura": 220,
                "peso": 6.5,
                "tamanhoMaxGpu": 350,
                "alturaMaxCooler": 165,
                "qtdRgb": 3,
                "usb": 2,
                "usbc": 1
            }
        """;

        idGabinete =
            ((Number) given()
                .contentType(ContentType.JSON)
                .body(json)
            .when()
                .post("/gabinetes")
            .then()
                .statusCode(201)
                .extract().path("id")).longValue();
    }

    @Test
    public void testCriarGabineteValido() {
        // Esse já foi testado em setup(), mas aqui é um teste separado se desejar manter.
        given()
            .when()
            .get("/gabinetes/" + idGabinete)
        .then()
            .statusCode(200)
            .body("nomeExibicao", is("Gabinete Gamer"))
            .body("marca", is("Corsair"));
    }

    @Test
    public void testAtualizarGabinete() {
        String jsonUpdate = """
            {
                "nomeExibicao": "Gabinete Atualizado",
                "marca": "NZXT",
                "preco": 579.90,
                "cor": "Branco",
                "formato": "ATX",
                "altura": 460,
                "largura": 230,
                "peso": 6.8,
                "tamanhoMaxGpu": 370,
                "alturaMaxCooler": 170,
                "qtdRgb": 2,
                "usb": 3,
                "usbc": 1
            }
        """;

        given()
            .contentType(ContentType.JSON)
            .body(jsonUpdate)
        .when()
            .put("/gabinetes/" + idGabinete)
        .then()
            .statusCode(204);

        given()
            .when()
            .get("/gabinetes/" + idGabinete)
        .then()
            .statusCode(200)
            .body("nomeExibicao", is("Gabinete Atualizado"))
            .body("marca", is("NZXT"))
            .body("usb", is(3));
    }

    @Test
    public void testBuscarGabinetePorId() {
        given()
            .when()
            .get("/gabinetes/" + idGabinete)
        .then()
            .statusCode(200)
            .body("id", is(idGabinete.intValue()))
            .body("nomeExibicao", is("Gabinete Gamer"));
    }

    @Test
    public void testListarTodosGabinetes() {
        given()
            .when()
            .get("/gabinetes")
        .then()
            .statusCode(200);
    }

    @Test
    public void testCriarGabineteInvalido() {
        String json = """
            {
                "nomeExibicao": "",
                "marca": "",
                "preco": 0,
                "cor": "Preto",
                "formato": "ATX",
                "altura": 0,
                "largura": 0,
                "peso": 0,
                "tamanhoMaxGpu": 0,
                "alturaMaxCooler": 0,
                "qtdRgb": 0,
                "usb": 0,
                "usbc": 0
            }
        """;

        given()
            .contentType(ContentType.JSON)
            .body(json)
        .when()
            .post("/gabinetes")
        .then()
            .statusCode(400);
    }

    @Test
    public void testDeletarGabinete() {
        given()
            .when()
            .delete("/gabinetes/" + idGabinete)
        .then()
            .statusCode(204);

        given()
            .when()
            .get("/gabinetes/" + idGabinete)
        .then()
            .statusCode(404);
    }
}
