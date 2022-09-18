package bank.account.DTO;

public class CustomerAuthTokenDTO {
    private String jwtToken;

    public CustomerAuthTokenDTO () {}

    public String getJwtToken() {
        return this.jwtToken;
    }

    public CustomerAuthTokenDTO setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;

        return this;
    }
}
