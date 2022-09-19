package bank.account.DTO;

public class CustomerLoginRequestDTO {
    private String email;
    private String password;

    public CustomerLoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }
}
