package br.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
public class ClienteResourceTest {

    @Test
    @Order(1)
    public void testListarTodosClientes() {
        given()
            .when().get("/clientes")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON);
    }

    @Test
    @Order(2)
    public void testCriarCliente() {
        String clienteJson = """
            {
                "nome": "João Silva",
                "cpf": "12345678901",
                "email": "joao@email.com",
                "telefone": "11999999999",
                "enderecos": [
                    {
                        "logradouro": "Rua Teste",
                        "numero": "123",
                        "complemento": "Apto 1",
                        "bairro": "Centro",
                        "cidade": "São Paulo",
                        "estado": "SP",
                        "cep": "01234567"
                    }
                ]
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(clienteJson)
            .when().post("/clientes")
            .then()
            .statusCode(201)
            .body("nome", equalTo("João Silva"))
            .body("cpf", equalTo("12345678901"))
            .body("email", equalTo("joao@email.com"));
    }

    @Test
    @Order(3)
    public void testBuscarClientePorId() {
        given()
            .when().get("/clientes/1")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("id", notNullValue());
    }

    @Test
    @Order(4)
    public void testBuscarClienteInexistente() {
        given()
            .when().get("/clientes/99999")
            .then()
            .statusCode(404);
    }

    @Test
    @Order(5)
    public void testAtualizarCliente() {
        String clienteJson = """
            {
                "nome": "João Silva Atualizado",
                "cpf": "12345678901",
                "email": "joao.novo@email.com",
                "telefone": "11988888888",
                "enderecos": [
                    {
                        "logradouro": "Rua Nova",
                        "numero": "456",
                        "complemento": "Casa",
                        "bairro": "Jardim",
                        "cidade": "São Paulo",
                        "estado": "SP",
                        "cep": "01234567"
                    }
                ]
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(clienteJson)
            .when().put("/clientes/1")
            .then()
            .statusCode(200)
            .body("nome", equalTo("João Silva Atualizado"))
            .body("email", equalTo("joao.novo@email.com"));
    }

    @Test
    @Order(6)
    public void testBuscarClientePorCpf() {
        given()
            .when().get("/clientes/cpf/12345678901")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("cpf", equalTo("12345678901"));
    }

    @Test
    @Order(7)
    public void testBuscarClientePorEmail() {
        given()
            .when().get("/clientes/email/joao.novo@email.com")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("email", equalTo("joao.novo@email.com"));
    }

    @Test
    @Order(8)
    public void testDeletarCliente() {
        given()
            .when().delete("/clientes/1")
            .then()
            .statusCode(204);
    }

    @Test
    @Order(9)
    public void testCriarClienteSemNome() {
        String clienteJson = """
            {
                "cpf": "98765432100",
                "email": "teste@email.com",
                "telefone": "11999999999",
                "enderecos": []
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(clienteJson)
            .when().post("/clientes")
            .then()
            .statusCode(400);
    }

    @Test
    @Order(10)
    public void testCriarClienteComCpfDuplicado() {
        String clienteJson1 = """
            {
                "nome": "Cliente 1",
                "cpf": "11111111111",
                "email": "cliente1@email.com",
                "telefone": "11999999999",
                "enderecos": []
            }
            """;

        String clienteJson2 = """
            {
                "nome": "Cliente 2",
                "cpf": "11111111111",
                "email": "cliente2@email.com",
                "telefone": "11999999999",
                "enderecos": []
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(clienteJson1)
            .when().post("/clientes")
            .then()
            .statusCode(201);

        given()
            .contentType(ContentType.JSON)
            .body(clienteJson2)
            .when().post("/clientes")
            .then()
            .statusCode(400);
    }
}
