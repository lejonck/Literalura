package br.com.alura.literalura.services;

import br.com.alura.literalura.model.ResponseData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataConverter implements IConvertsData{
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T fetchData(String json, Class <T> classe){
        try {
            System.out.println(mapper.readValue(json, classe));
            return mapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseData fetchResponseData(String json) {
        try {
            return mapper.readValue(json, ResponseData.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
