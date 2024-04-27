package org.estudos.br;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

public class ConsultaIBGETest {

    private static final String DISTRITOS_API_URL = "https://servicodados.ibge.gov.br/api/v1/localidades/distritos/";

    @Test
    @DisplayName("Teste para conferir todos os distritos")
    public void testConsultarTodosDistritos() throws IOException {


        String resposta = ConsultaIBGE.consultarTodosDistritos();


        assertNotNull(resposta, "A resposta não pode ser nula");

        HttpURLConnection connection = (HttpURLConnection) new URL(DISTRITOS_API_URL).openConnection();
        int statusCode = connection.getResponseCode();
        assertEquals(200, statusCode, "O status code da resposta da API deve ser 200 (OK)");
    }

    private static final String REGIOES_API_URL = "https://servicodados.ibge.gov.br/api/v1/localidades/regioes/";

    @Test
    @DisplayName("Teste para conferir todos as regiões")
    public void testconsultaTodasRegioes() throws IOException {


        String resposta = ConsultaIBGE.consultaTodasRegioes();


        assertNotNull(resposta, "A resposta não pode ser nula");

        HttpURLConnection connection = (HttpURLConnection) new URL(REGIOES_API_URL).openConnection();
        int statusCode = connection.getResponseCode();
        assertEquals(200, statusCode, "O status code da resposta da API deve ser 200 (OK)");
    }

}


