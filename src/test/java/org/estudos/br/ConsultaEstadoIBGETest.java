package org.estudos.br;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ConsultaEstadoIBGETest {
    private static final String ESTADOS_API_URL = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/";

    @Mock
    private HttpURLConnection connectionMock;

    private static final String JSON_RESPONSE =
            "{\"id\":11,\"sigla\":\"RO\",\"nome\":\"Rondônia\",\"regiao\":{\"id\":1,\"sigla\":\"N\",\"nome\":\"Norte\"}}";

    @BeforeEach
    public void createMock() throws IOException {
        MockitoAnnotations.openMocks(this); // Inicializando o Mock

        // Configurando o Mock
        InputStream inputStream = new ByteArrayInputStream(JSON_RESPONSE.getBytes());
        when(connectionMock.getInputStream()).thenReturn(inputStream);
    }

    @Test
    @DisplayName("Teste de busca de estado")
    public void testeConsultaEstado() throws IOException {
        String uf = "RO"; // Estado que será consultado
        String expected = "{\"id\":11,\"sigla\":\"RO\",\"nome\":\"Rondônia\",\"regiao\":{\"id\":1,\"sigla\":\"N\",\"nome\":\"Norte\"}}"; // Resposta esperada
        String answer = ConsultaIBGE.consultarEstado(uf); // Chama o método utilizado para teste

        // Assert
        assertEquals(expected, answer);
    }

    @ParameterizedTest
    @CsvSource({"RO, RO", "AC, AC", "AM, AM"})
    @DisplayName("Teste de busca de distrito parametrizado")
    public void testeConsultaUfParametrizado(String uf) throws IOException {
        String id = uf; // Define o UF a ser consultado
        String answer = ConsultaIBGE.consultarEstado(id); // Chama o método utilizado para teste

        assert !answer.isEmpty(); // Verifica se a resposta não está vazia

        // Verifica se o status code é 200 (OK)
        HttpURLConnection connection = (HttpURLConnection) new URL(ESTADOS_API_URL + id).openConnection();
        int statusCode = connection.getResponseCode();
        assertEquals(200, statusCode, "O status code da resposta da API deve ser 200 (OK)");
    }

    @Test
    @DisplayName("Teste estado usando o Mock")
    public void testeConsultaEstadoMock() throws IOException {
        String uf = "RO"; // Estado que será consultado
        String answer = ConsultaIBGE.consultarEstado(uf); // Chama o método utilizado para teste
        assertEquals(JSON_RESPONSE, answer, "O JSON retornado não corresponde ao esperado."); // Verifica a resposta e o JSON esperado
    }
}
