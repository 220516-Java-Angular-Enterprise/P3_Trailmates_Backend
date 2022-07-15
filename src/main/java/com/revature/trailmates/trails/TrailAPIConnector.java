package com.revature.trailmates.trails;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.trailmates.util.annotations.Inject;
import com.revature.trailmates.util.custom_exception.InvalidRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class TrailAPIConnector {

    @Inject
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Inject
    @Autowired
    public TrailAPIConnector(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public JsonNode getAllPlainJSON(int page) {
        String hikingId = "BFF8C027-7C8F-480B-A5F8-CD8CE490BFBA";
        String authKey = "Pf4dbh8cMwO2nYtThNGIAkt29icTBDUaldu9eBgm";
        String url = "https://developer.nps.gov/api/v1/thingstodo?q=" + hikingId + "&limit=10&start=" + page*10 + "&api_key=" + authKey;
        String jsonStr = restTemplate.getForObject(url, String.class);
        try {
            JsonNode test = objectMapper.readTree(jsonStr);
            return test;
        } catch (Exception e) {
            throw new InvalidRequestException("Could Not Find Trail.");
        }
    }

    public JsonNode getPlainJSON(String id){
        String hikingId = "BFF8C027-7C8F-480B-A5F8-CD8CE490BFBA";
        String authKey = "RdglHzFV5Hs3eeu5UrJUBwCj0KscErdZAE9xqCK4";
        String url = "https://developer.nps.gov/api/v1/thingstodo?id=" + id + "&q=" + hikingId + "&api_key=" + authKey;
        String jsonStr = restTemplate.getForObject(url, String.class);
        try {
            JsonNode test = objectMapper.readTree(jsonStr);
            JsonNode content = test.at("/data").get(0);
            return content;
        } catch (Exception e) {
            throw new InvalidRequestException("Could Not Find Trail.");
        }
    }

}

