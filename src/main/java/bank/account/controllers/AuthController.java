package bank.account.controllers;

import bank.account.DTO.CustomerAuthTokenDTO;
import bank.account.DTO.CustomerLoginRequestDTO;
import bank.account.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public CustomerAuthTokenDTO login(@RequestBody CustomerLoginRequestDTO customerLoginDTO) {
        String jwtToken = this.authService.authenticateUser(customerLoginDTO);

        return (new CustomerAuthTokenDTO())
                .setJwtToken(jwtToken);
    }
}
