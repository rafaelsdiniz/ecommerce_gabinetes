package br.unitins.tp1.resource;

import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@QuarkusTest
public class MarcaResourceTest {


    @Test
    public void testGetAllMarcas() {
        given()
            .when().get("/marcas")
            .then()
            .statusCode(200);  
    }

  
    @Test
    public void testCreateMarca() {
        String json = """
            {
                "nome": "Logitech"
            }
        """; 

        given()
            .contentType(ContentType.JSON)
            .body(json)
        .when()
            .post("/marcas")
        .then()
            .statusCode(201) 
            .body("nome", is("Logitech"));
    }


    @Test
    public void testGetMarcaById() {
        String jsonCreate = """
            {
                "nome": "Razer"
            }
        """; 

        int id = given()
            .contentType(ContentType.JSON)
            .body(jsonCreate)
        .when()
            .post("/marcas")
        .then()
            .statusCode(201)
            .extract().path("id");

        given()
            .when().get("/marcas/" + id)
            .then()
            .statusCode(200)
            .body("id", is(id))
            .body("nome", is("Razer"));
    }

   
    @Test
    public void testUpdateMarca() {
        String jsonCreate = """
            {
                "nome": "HyperX"
            }
        """; 

        // Cria uma nova marca
        int id = given()
            .contentType(ContentType.JSON)
            .body(jsonCreate)
        .when()
            .post("/marcas")
        .then()
            .statusCode(201)
            .extract().path("id");

        
        String jsonUpdate = """
            {
                "nome": "Corsair"
            }
        """; 

    
        given()
            .contentType(ContentType.JSON)
            .body(jsonUpdate)
        .when()
            .put("/marcas/" + id)
        .then()
            .statusCode(204); 

        
        given()
            .when().get("/marcas/" + id)
            .then()
            .statusCode(200)
            .body("nome", is("Corsair"));  
    }

    
    @Test
    public void testDeleteMarca() {
        String jsonCreate = """
            {
                "nome": "HyperX"
            }
        """; 

        // Cria uma nova marca
        int id = given()
            .contentType(ContentType.JSON)
            .body(jsonCreate)
        .when()
            .post("/marcas")
        .then()
            .statusCode(201)
            .extract().path("id");

        // Deleta a marca criada
        given()
            .when().delete("/marcas/" + id)
            .then()
            .statusCode(204);  
    }

    
    @Test
    public void testCreateMarcaInvalid() {
        String json = """
            {
                "nome": ""
            }
        """; 

        
        given()
            .contentType(ContentType.JSON)
            .body(json)
        .when()
            .post("/marcas")
        .then()
            .statusCode(400);  
    }
}
