package org.greading.api.request;

public class JoinRequest {

    private String username;

    private String password;

    private String email;

    private String confirmUrl;

    public JoinRequest() {}

    public JoinRequest(String username, String password, String email, String confirmUrl) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.confirmUrl = confirmUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getConfirmUrl() {
        return confirmUrl;
    }
}
