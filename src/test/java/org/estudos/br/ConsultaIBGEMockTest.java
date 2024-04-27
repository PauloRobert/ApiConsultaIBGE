package org.estudos.br;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ConsultaIBGEMockTest {

    // Mock para simular a conexão HTTP
    @Mock
    private HttpURLConnection connectionMock;

    // JSON de resposta simulada
    private static final String JSON_RESPONSE = "{\"id\":35,\"sigla\":\"SP\",\"nome\":\"São Paulo\",\"regiao\":{\"id\":3,\"sigla\":\"SE\",\"nome\":\"Sudeste\"}}";  // Método executado antes de cada teste

    // Método executado antes de cada teste
    @BeforeEach
    public void configurar() throws IOException {
        // Inicializa os mocks
        MockitoAnnotations.openMocks(this);

        // Configura o comportamento do mock
        InputStream inputStream = new ByteArrayInputStream(JSON_RESPONSE.getBytes());
        when(connectionMock.getInputStream()).thenReturn(inputStream);
    }

    //Teste para verificar se o método consultarDistritoIdentificador retorna o JSON esperado para o distrido Fazendinha
    @Test
    @DisplayName("Teste de consulta usando Mock")
    public void testarConsultarEstadoId() throws IOException {


        // Identificador do distrito a ser consultado
        int id = 35;

        // Execução do método a ser testado
        String resposta = ConsultaIBGE.consultarEstadoId(id);

        // Verificar se o JSON retornado corresponde ao esperado
        assertEquals(JSON_RESPONSE, resposta, "O JSON retornado não corresponde ao esperado.");
    }
}
