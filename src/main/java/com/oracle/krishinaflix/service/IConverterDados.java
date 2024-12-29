package com.oracle.krishinaflix.service;

public interface IConverterDados {
    <T> T obterDados(String json, Class<T> classe);
}
