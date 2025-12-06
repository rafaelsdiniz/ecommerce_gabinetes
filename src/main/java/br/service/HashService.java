package br.service;

public interface HashService {
    String getHashSenha(String senha) throws Exception;
    boolean validarSenha(String senha, String hash) throws Exception;
}