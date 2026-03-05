package com.devnotfound.talenthub.constants;

public class SystemConstants {

    private SystemConstants() {
        throw new IllegalStateException("Utility class");
    }

    // Mensagens de erro
    public static final String USER_NOT_FOUND_ID = "Usuário não encontrado com o id: ";
    public static final String USER_NOT_FOUND_EMAIL = "Usuário não encontrado com o email: ";
    public static final String USER_NOT_FOUND_NAME = "Nenhum usuário encontrado com o nome: ";
    public static final String CUSTOMER_NOT_FOUND_ID = "Customer not found with id: ";
    public static final String CUSTOMER_NOT_FOUND_EMAIL = "Customer not found with email: ";
    public static final String EMAIL_ALREADY_EXISTS = "Email já cadastrado: ";
    public static final String CLIENT_NOT_FOUND_EMAIL = "Cliente não encontrado com o email: ";
    public static final String CLIENT_NOT_FOUND_CREDENTALS = "login ou senha inválidos: ";

    // Configurações
    public static final String DEFAULT_BACKOFFICE_PASSWORD = "Senha!23";
    public static final String DEFAULT_SITE_PASSWORD = "Cli!23";
}
