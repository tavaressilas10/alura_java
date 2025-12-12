package br.com.alura.screenmatch.service;

public interface InterfaceConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
