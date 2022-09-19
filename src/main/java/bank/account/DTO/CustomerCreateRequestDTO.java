package bank.account.DTO;

public class CustomerCreateRequestDTO {
    private String name;
    private String email;
    private String password;

    public CustomerCreateRequestDTO(
            String name,
            String email,
            String password
    ) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }
}
