package br.unitins.tp1.resource;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import br.dto.ItemPedidoResponseDTO;
import br.service.ItemPedidoService;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
@TestMethodOrder(OrderAnnotation.class)
public class ItemPedidoResourceTest {

    @Inject
    ItemPedidoService itemPedidoService;

    static Long idItemPedido;

    @Test
    @Order(1)
    public void testCriarItemPedidoValido() {
        String json = """
            {
            "idPedido": 1,
            "idGabinete": 1,
            "quantidade": 2,
            "precoUnitario": 350.00,
            "precoTotal": 700.00
            }
        """; 

        given()
            .contentType(ContentType.JSON)
            .body(json)
        .when()
            .post("/itempedido")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .log().ifValidationFails(); // Adicione essa linha para obter mais informações sobre o erro
    }

    @Test
    @Order(2)
    public void testListarTodosItensPedido() {
        given()
            .when().get("/itempedido")
            .then()
            .statusCode(200);
    }

    @Test
    @Order(3)
    public void testBuscarItemPedidoPorId() {
        List<ItemPedidoResponseDTO> itens = itemPedidoService.listarTodos();
        if (itens.isEmpty()) {
            testCriarItemPedidoValido();
            itens = itemPedidoService.listarTodos();
        }
        if (!itens.isEmpty()) {
            idItemPedido = itens.get(0).getId();

            given()
                .when().get("/itempedido/" + idItemPedido)
                .then()
                .statusCode(200)
                .body("id", is(idItemPedido.intValue()));  
        } else {
            // Lidar com a lista vazia
        }
    }

    @Test
    @Order(4)
    public void testAtualizarItemPedido() {
        String json = """
        {
        "idPedido": 1,
        "idGabinete": 1,
        "quantidade": 2,
        "precoUnitario": 350.00,
        "precoTotal": 700.00
        }
        """; 

        given()
            .contentType(ContentType.JSON)
            .body(json)
        .when()
            .put("/itempedido/" + idItemPedido)
        .then()
            .statusCode(204);  
    }

    @Test
    @Order(5)
    public void testDeletarItemPedido() {
        given()
            .when().delete("/itempedido/" + idItemPedido)
            .then()
            .statusCode(204);  
    }

    @Test
    @Order(6)
    public void testCriarItemPedidoInvalido() {
        String json = """
        {
            "pedido": null,
            "gabinete": null,
            "quantidade": 0,
            "precoUnitario": -10,
            "precoTotal": -10
        }
        """; 

        given()
            .contentType(ContentType.JSON)
            .body(json)
        .when()
            .post("/itempedido")
        .then()
            .statusCode(400);  
    }
}