package br.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HashServiceImpl implements HashService {

    private static final String SALT = "@#1237Zt";
    private static final int ITERATION_COUNT = 403;
    private static final int KEY_LENGTH = 512;

    @Override
    public String getHashSenha(String senha) throws Exception {
        try {
            byte[] hash = SecretKeyFactory
                .getInstance("PBKDF2WithHmacSHA512")
                .generateSecret(
                    new PBEKeySpec(senha.toCharArray(), SALT.getBytes(), ITERATION_COUNT, KEY_LENGTH)
                )
                .getEncoded();

            return Base64.getEncoder().encodeToString(hash);

        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new Exception("Erro ao gerar hash da senha", e);
        }
    }

    @Override
    public boolean validarSenha(String senha, String hash) throws Exception {
        try {
            String hashDaSenhaFornecida = getHashSenha(senha);
            return hashDaSenhaFornecida.equals(hash);
        } catch (Exception e) {
            throw new Exception("Erro ao validar senha", e);
        }
    }
}