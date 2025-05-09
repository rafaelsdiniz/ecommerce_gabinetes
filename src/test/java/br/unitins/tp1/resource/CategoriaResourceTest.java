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

        idCategoria = 
            ((Number) given()
                .contentType(ContentType.JSON)
                .body(json)
            .when()
                .post("/categorias")
            .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("nome", is("Categoria Gamer"))
                .body("descricao", is("Gabinetes para jogos"))
                .extract().path("id")).longValue(); // Extraindo o ID da categoria criada
    }


    @Test
    @Order(2)
    public void testListarTodosCategorias() {
        // Crie apenas uma categoria, se ainda não houver
        given()
            .when().get("/categorias")
            .then()
                .statusCode(200)
                .body("$.size()", is(11));  
    }
    
    


    @Test
    @Order(3)
    public void testPorIdCategoria() {
        given()
            .when().get("/categorias/" + idCategoria)
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
            .body(json)
        .when().put("/categorias/" + idCategoria)
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
            .when().delete("/categorias/" + idCategoria)
            .then()
                .statusCode(204);  

        try {
            categoriaService.buscarPorId(idCategoria);
        } catch (jakarta.ws.rs.NotFoundException e) {
            assertThat(e.getMessage(), containsString("Categoria não encontrada"));
        }
    }
}
