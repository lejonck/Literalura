package br.com.alura.literalura.services;

public interface IConvertsData {
    public <T> T fetchData(String json, Class <T> classe);
}
