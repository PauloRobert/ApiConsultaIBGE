package org.estudos.br;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.RETURNS_MOCKS;
import static org.mockito.Mockito.when;

public class ConsultaIBGETest {
    @Mock
    private HttpURLConnection connectionMock;

    private static final String ESTADOS_API_URL = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/";

    private static final String JSON_MOCK_ESTADOS_PE = "{\"id\":26,\"sigla\":\"PE\",\"nome\":\"Pernambuco\",\"regiao\":{\"id\":2,\"sigla\":\"NE\",\"nome\":\"Nordeste\"}}";

    private static final String DISTRITOS_API_URL = "https://servicodados.ibge.gov.br/api/v1/localidades/distritos/";

    @BeforeEach
    public void setup() throws IOException{
        MockitoAnnotations.openMocks(this);

        InputStream inputStreamStateAC = new ByteArrayInputStream(JSON_MOCK_ESTADOS_PE.getBytes());
        when(connectionMock.getInputStream()).thenReturn(inputStreamStateAC);
    }

    @Test
    @DisplayName("Teste para consulta única de um estado")
    public void testConsultarEstado() throws IOException {
        // Arrange
        String uf = "SP"; // Define o estado a ser consultado

        // Act
        String resposta = ConsultaIBGE.consultarEstado(uf); // Chama o método a ser testado

        // Assert
        // Verifica se a resposta não está vazia
        assert !resposta.isEmpty();

        // Verifica se o status code é 200 (OK)
        HttpURLConnection connection = (HttpURLConnection) new URL(ESTADOS_API_URL + uf).openConnection();
        int statusCode = connection.getResponseCode();
        assertEquals(200, statusCode, "O status code da resposta da API deve ser 200 (OK)");
    }

    @Test
    @DisplayName("Teste para consulta única de um estado")
    public void testConsultarEstadoRJ() throws IOException {
        // Arrange
        String uf = "RJ"; // Define o estado a ser consultado

        // Act
        String resposta = ConsultaIBGE.consultarEstado(uf); // Chama o método a ser testado

        // Assert
        // Verifica se a resposta não está vazia
        assert !resposta.isEmpty();

        // Verifica se o status code é 200 (OK)
        HttpURLConnection connection = (HttpURLConnection) new URL(ESTADOS_API_URL + uf).openConnection();
        int statusCode = connection.getResponseCode();
        assertEquals(200, statusCode, "O status code da resposta da API deve ser 200 (OK)");
    }

    @Test
    @DisplayName("Teste para consulta única de um estado")
    public void testConsultarDistritoPorID() throws IOException {
        // Arrange
        String id = "520005005"; // Define o id para distrito Abadia de Goias

        // Act
        String resposta = ConsultaIBGE.consultarEstado(id); // Chama o método a ser testado

        // Assert
        // Verifica se a resposta não está vazia
        assert !resposta.isEmpty();

        // Verifica se o status code é 200 (OK)
        HttpURLConnection connection = (HttpURLConnection) new URL(DISTRITOS_API_URL + id).openConnection();
        int statusCode = connection.getResponseCode();
        assertEquals(200, statusCode, "O status code da resposta da API deve ser 200 (OK)");
    }

    @Test
    @DisplayName("Teste Mockado de consulta de Estado: ")
    public void testMockConsultarEstadoPernambuco() throws IOException {
        String siglaEstado = "PE";

        String response = ConsultaIBGE.consultarEstado(siglaEstado);

        assertEquals(JSON_MOCK_ESTADOS_PE, response, "Há divergencia no body da response");
    }
}