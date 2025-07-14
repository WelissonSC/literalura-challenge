package com.alura.literalura.service;

public interface DataConvertInterface {
    <T> T GetData(String json, Class<T> classe);
}
