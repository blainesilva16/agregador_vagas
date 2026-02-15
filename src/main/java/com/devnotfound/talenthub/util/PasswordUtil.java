package com.devnotfound.talenthub.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Gera o hash da senha
    public static String hashSenha(String senha) {
        return encoder.encode(senha);
    }

    // Verifica se a senha informada corresponde ao hash armazenado
    public static boolean verificarSenha(String senha, String hash) {
        return encoder.matches(senha, hash);
    }

    // Exemplo de uso
    public static void main(String[] args) {
        String senhaOriginal = "minhaSenhaSuperSecreta";
        String hash = hashSenha(senhaOriginal);

        System.out.println("Senha original: " + senhaOriginal);
        System.out.println("Hash gerado: " + hash);

        boolean confere = verificarSenha("minhaSenhaSuperSecreta", hash);
        System.out.println("Senha confere? " + confere);
    }
}