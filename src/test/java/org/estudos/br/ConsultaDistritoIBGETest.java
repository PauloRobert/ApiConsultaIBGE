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

public class ConsultaDistritoIBGETest {
    private static final String DISTRITOS_API_URL = "https://servicodados.ibge.gov.br/api/v1/localidades/distritos/";

    @Mock
    private HttpURLConnection connectionMock;

    private static final String JSON_RESPONSE =
            "[{\"id\":520005005,\"nome\":\"Abadia de Goiás\",\"municipio\":{\"id\":5200050,\"nome\":\"Abadia de Goiás\",\"microrregiao\":{\"id\":52010,\"nome\":\"Goiânia\",\"mesorregiao\":{\"id\":5203,\"nome\":\"Centro Goiano\",\"UF\":{\"id\":52,\"sigla\":\"GO\",\"nome\":\"Goiás\",\"regiao\":{\"id\":5,\"sigla\":\"CO\",\"nome\":\"Centro-Oeste\"}}}},\"regiao-imediata\":{\"id\":520001,\"nome\":\"Goiânia\",\"regiao-intermediaria\":{\"id\":5201,\"nome\":\"Goiânia\",\"UF\":{\"id\":52,\"sigla\":\"GO\",\"nome\":\"Goiás\",\"regiao\":{\"id\":5,\"sigla\":\"CO\",\"nome\":\"Centro-Oeste\"}}}}}}]";

    @BeforeEach
    public void createMock() throws IOException {
        MockitoAnnotations.openMocks(this); // Inicializando o Mock

        // Configurando o Mock
        InputStream inputStream = new ByteArrayInputStream(JSON_RESPONSE.getBytes());
        when(connectionMock.getInputStream()).thenReturn(inputStream);
    }

    @Test
    @DisplayName("Teste de busca de distrito")
    public void testeConsultaDistrito() throws IOException {
        int id = 520005005; // Distrito que será consultado
        String expected = "[{\"id\":520005005,\"nome\":\"Abadia de Goiás\",\"municipio\":{\"id\":5200050,\"nome\":\"Abadia de Goiás\",\"microrregiao\":{\"id\":52010,\"nome\":\"Goiânia\",\"mesorregiao\":{\"id\":5203,\"nome\":\"Centro Goiano\",\"UF\":{\"id\":52,\"sigla\":\"GO\",\"nome\":\"Goiás\",\"regiao\":{\"id\":5,\"sigla\":\"CO\",\"nome\":\"Centro-Oeste\"}}}},\"regiao-imediata\":{\"id\":520001,\"nome\":\"Goiânia\",\"regiao-intermediaria\":{\"id\":5201,\"nome\":\"Goiânia\",\"UF\":{\"id\":52,\"sigla\":\"GO\",\"nome\":\"Goiás\",\"regiao\":{\"id\":5,\"sigla\":\"CO\",\"nome\":\"Centro-Oeste\"}}}}}}]"; // Resposta esperada
        String answer = ConsultaIBGE.consultarDistrito(id); // Chama o método utilizado para teste

        // Assert
        assertEquals(expected, answer);
    }

    @ParameterizedTest
    @CsvSource({"520005005", "310010405", "520010005"})
    @DisplayName("Teste de busca de distrito parametrizado")
    public void testeConsultaDistritoParametrizado(int district) throws IOException {
        int id = district; // Define o distrito a ser consultado
        String answer = ConsultaIBGE.consultarDistrito(id); // Chama o método utilizado para teste

        assert !answer.isEmpty(); // Verifica se a resposta não está vazia

        // Verifica se o status code é 200 (OK)
        HttpURLConnection connection = (HttpURLConnection) new URL(DISTRITOS_API_URL + id).openConnection();
        int statusCode = connection.getResponseCode();
        assertEquals(200, statusCode, "O status code da resposta da API deve ser 200 (OK)");
    }

    @Test
    @DisplayName("Teste distrito usando o Mock")
    public void testeConsultaDistritoMock() throws IOException {
        int id = 520005005; // Estado que será consultado
        String answer = ConsultaIBGE.consultarDistrito(id); // Chama o método utilizado para teste
        assertEquals(JSON_RESPONSE, answer, "O JSON retornado não corresponde ao esperado."); // Verifica a resposta e o JSON esperado
    }
}
