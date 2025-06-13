package br.unitins.tp1.resource;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import br.dto.CategoriaResponseDTO;
import br.service.CategoriaService;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
@TestMethodOrder(OrderAnnotation.class)
public class CategoriaResourceTest {

    @Inject
    CategoriaService categoriaService;

    private static final String TOKEN_FIXO = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ1bml0aW5zLWp3dCIsInN1YiI6ImxlYW5kcmEiLCJncm91cHMiOlsiQWRtIl0sImV4cCI6MTc1MjM0OTk5OSwiaWF0IjoxNzQ5NzU3OTk5LCJqdGkiOiIwY2ZlZDNlMy03MWIzLTRiMmMtOTQxNy1iMmMwZDk5MGEyZDcifQ.in6cV-yOrfsUAVFCC5EsQnoU2NLV9_pSUAZ1SbpyxTbWQtrfYB9UlzopcXKdzONNfxUy3pmSKiMRaSRvUV_ZAG--TayzgUfHzkEf2ceAMHrTait7-RmcA1I_cgBPAPijODtpB-839na44M8TG18ZXArxNDGvIBqKx1AZGUt-E3fu5hHaqc-04e4PMjlwv0Fm1TGLFvArrT3mqeDun68YCfic-laF-6jUhl2wMTovi3dH4yVAGxX39P3iR8eQl0rQbeqDc56rw3Uxd_Yd9rzobJ4XUN4dF4de-_3niegbSo2YziM8_bvdY0nmUBYpmLuTkQMUTGa5vrnN_6cAGIOzPA";

    static Long idCategoria;

    @Test
    @Order(1)
    public void testCriarCategoria() {
        String json = """
            {
                "nome": "Categoria Gamer",
                "descricao": "Gabinetes para jogos"
            }
        """;

        idCategoria = ((Number) given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + TOKEN_FIXO)
                .body(json)
            .when()
                .post("/categorias")
            .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("nome", is("Categoria Gamer"))
                .body("descricao", is("Gabinetes para jogos"))
                .extract()
                .path("id")).longValue();
    }

    @Test
    @Order(2)
    public void testListarTodosCategorias() {
        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .get("/categorias")
        .then()
            .statusCode(200)
            .body("size()", is(11)); 
    }

    @Test
    @Order(3)
    public void testPorIdCategoria() {
        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .get("/categorias/" + idCategoria)
        .then()
            .statusCode(200)
            .body("id", is(idCategoria.intValue()))
            .body("nome", is("Categoria Gamer"))
            .body("descricao", is("Gabinetes para jogos"));
    }

    @Test
    @Order(4)
    public void testAtualizarCategoria() {
        String json = """
            {
                "nome": "Categoria Atualizada",
                "descricao": "Descrição atualizada"
            }
        """;

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(json)
        .when()
            .put("/categorias/" + idCategoria)
        .then()
            .statusCode(204);

        CategoriaResponseDTO categoriaAtualizada = categoriaService.buscarPorId(idCategoria);
        assertThat(categoriaAtualizada.getNome(), is("Categoria Atualizada"));
        assertThat(categoriaAtualizada.getDescricao(), is("Descrição atualizada"));
    }

    @Test
    @Order(5)
    public void testDeletarCategoria() {
        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .delete("/categorias/" + idCategoria)
        .then()
            .statusCode(204);

        try {
            categoriaService.buscarPorId(idCategoria);
        } catch (jakarta.ws.rs.NotFoundException e) {
            assertThat(e.getMessage(), containsString("Categoria não encontrada"));
        }
    }
}
