package com.ceviant.coding.challenge.Exercise1;

import com.ceviant.coding.challenge.exercise1.service.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class TransactionControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private int port;
    @Autowired
    private TransactionService transactionService;

    @Test
    public void getTransactionStatistics()  throws JsonProcessingException, JSONException{
        transactionService.deleteTransactions();
        Map<String, Object> json = new HashMap<>();
        json.put("amount", "100");
        json.put("timestamp", Instant.now().toString());
        sendPost(json);
        String response = restTemplate.getForEntity("http://localhost:"+port+"/statistics", String.class).getBody();
        JSONObject res = new JSONObject(response);
        Assertions.assertEquals("100", res.getString("max"));
        Assertions.assertEquals("100", res.getString("min"));
        Assertions.assertEquals(1L, res.getLong("count"));
        Assertions.assertEquals("100", res.getString("sum"));
        Assertions.assertEquals("100", res.getString("avg"));
    }

    @Test
    public void createTransactionJSONInvalid() throws JsonProcessingException {
        Map<String, Object> json = new HashMap<>();
        json.put("amount", "1");

        Assertions.assertEquals(400, sendPost(json).getStatusCodeValue());
    }

    @Test
    public void createTransactionJSONParse() throws JsonProcessingException {
        Map<String, Object> json = new HashMap<>();
        json.put("amount", "1");
        json.put("timestamp", "2018-");

        Assertions.assertEquals(422, sendPost(json).getStatusCodeValue());
    }

    @Test
    public void createTransaction() throws JsonProcessingException {
        Map<String, Object> json = new HashMap<>();
        json.put("amount", "1");
        json.put("timestamp", Instant.now().toString());

        Assertions.assertEquals(201, sendPost(json).getStatusCodeValue());
    }

    @Test
    public void createTransactionOld() throws JsonProcessingException {
        Map<String, Object> json = new HashMap<>();
        json.put("amount", "1");
        json.put("timestamp", Instant.now().minusSeconds(60 + 1).toString());

        Assertions.assertEquals(204, sendPost(json).getStatusCodeValue());
    }

    @Test
    public void createTransactionFuture() throws JsonProcessingException {
        Map<String, Object> json = new HashMap<>();
        json.put("amount", "1");
        json.put("timestamp", Instant.now().plusSeconds(30).toString());

        Assertions.assertEquals(422, sendPost(json).getStatusCodeValue());
    }

    @Test
    public void deleteTransactions () throws JSONException{
        restTemplate.delete("http://localhost:"+port+"/transactions");
        String response = restTemplate.getForEntity("http://localhost:"+port+"/statistics", String.class).getBody();
        JSONObject res = new JSONObject(response);
        Assertions.assertEquals("0", res.getString("count"));
        Assertions.assertEquals("0", res.getString("max"));
        Assertions.assertEquals("0", res.getString("min"));
        Assertions.assertEquals("0", res.getString("sum"));

    }
    private ResponseEntity<String> sendPost(Map<String, Object> json) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonString = new ObjectMapper().writeValueAsString(json);
        HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);

        return restTemplate.postForEntity(    "http://localhost:"+port+"/transactions", entity, String.class);
    }
}
