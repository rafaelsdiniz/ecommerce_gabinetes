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

    private final String TOKEN_FIXO = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ1bml0aW5zLWp3dCIsInN1YiI6ImxlYW5kcmEiLCJncm91cHMiOlsiQWRtIl0sImV4cCI6MTc1MTgwMzg3MiwiaWF0IjoxNzQ5MjExODcyLCJqdGkiOiIyZDgwOTQyMy03Y2ZlLTRhOTktYTE4OS00ODk5NWRlYmViNzkifQ.iFMAWEvqQUvOGOu8QiKS4gNpJBMtAaKvzINzOGPmRbLreHKWLjN6SALQ54451CxENgCoh_a0DLa7Y2J2WfcqYifPMVM7Kj4nSSwLAv2OmEO6ZM063hkhZLisjqqkT7o2CXLber_b3TXWPyVytvzcgeyfQI--Gv_3MAhzXTDOr0bVBgNYW3qNU_APyBmhkdC8-7He2n0agenq7brBZxzJDmNsOUbuC1E4Tlt6bQ3rEjQMBcIOB39Jq4Jo2q800vj4VE-gbI9AHEMojXPAaF_2IpMwIc0_U7wa--pHCTmoMy0AiQzvtmNMMgdAncTX0O6acFEHx4Bb9OReohGNtQq5ZA";

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
                .header("Authorization", "Bearer " + TOKEN_FIXO)
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
        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
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
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .contentType(ContentType.JSON)
            .body(jsonUpdate)
        .when()
            .put("/gabinetes/" + idGabinete)
        .then()
            .statusCode(204);

        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
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
            .header("Authorization", "Bearer " + TOKEN_FIXO)
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
            .header("Authorization", "Bearer " + TOKEN_FIXO)
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
            .header("Authorization", "Bearer " + TOKEN_FIXO)
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
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .when()
            .delete("/gabinetes/" + idGabinete)
        .then()
            .statusCode(204);

        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .when()
            .get("/gabinetes/" + idGabinete)
        .then()
            .statusCode(404);
    }
}
