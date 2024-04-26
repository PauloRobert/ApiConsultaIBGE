package org.estudos.br;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class ConsultaIBGETest {
    private static final String ESTADOS_API_URL = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/";
    private static final String DISTRITOS_API_URL = "https://servicodados.ibge.gov.br/api/v1/localidades/distritos/"; // Seguindo o padrão de nome

    private HttpURLConnection connectionMock;

    private static final String JSON_RESPONSE =
            "{\"id\":31,\"sigla\":\"MG\",\"nome\":\"Minas Gerais\",\"regiao\":{\"id\":3,\"sigla\":\"SE\",\"nome\":\"Sudeste\"}}";



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
    
    @ParameterizedTest
    @ValueSource(strings = {"SP", "RJ", "MG"})  // Fornecimento direto de valores para o teste
    @DisplayName("Teste de consulta de estado")
    public void testConsultarEstado(String estado) throws IOException {
        // Chamada para a API que consulta informações do estado
        String response = ConsultaIBGE.consultarEstado(estado);
        // Converte a resposta de String para JSONObject
        JSONObject jsonResponse = new JSONObject(response);
        // Verifica se a sigla do estado na resposta é igual ao esperado
        assertEquals(estado, jsonResponse.getString("sigla"));
    }

    // Teste para verificar a consulta de distritos
    @ParameterizedTest
    @ValueSource(ints = {520005005, 310010405, 520010005})  // Identificadores de distritos
    @DisplayName("Teste de consulta de distrito")
    public void testConsultarDistrito(int identificador) throws IOException {
        // Consulta informações do distrito com o identificador fornecido
        String resposta = ConsultaIBGE.consultarDistrito(identificador);
        // Verifica se a resposta não está vazia
        assert !resposta.isEmpty();

        // Realiza uma conexão HTTP para verificar o status code da resposta
        int statusCode = obterStatusCode(identificador);
        // Verifica se o status code é 200, o que indica sucesso
        assertEquals(200, statusCode, "O status code da resposta da API deve ser 200 (OK)");
    }

    // Método auxiliar para obter o status code de uma URL
    private int obterStatusCode(int id) throws IOException {
        URL url = new URL(DISTRITOS_API_URL + id);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        return connection.getResponseCode();
    }

    @BeforeEach
    public void setup() throws IOException {
        MockitoAnnotations.openMocks(this); // Inicializando o Mock

        // Configurando o Mock
        InputStream inputStream = new ByteArrayInputStream(JSON_RESPONSE.getBytes());
        when(connectionMock.getInputStream()).thenReturn(inputStream);
    }

    @Test
    @DisplayName("Teste estado usando o Mock")
    public void testConsultarEstadoComMock() throws IOException {
        String uf = "MG"; // Estado que será consultado
        String resposta = ConsultaIBGE.consultarEstado(uf); // Chama o método utilizado para teste
        assertEquals(JSON_RESPONSE, resposta, "O JSON retornado não corresponde ao esperado."); // Verifica a resposta e o JSON esperado
    }
}