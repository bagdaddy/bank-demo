package bank.account.services;

import bank.account.DTO.CustomerLoginRequestDTO;
import bank.account.models.Customer;
import bank.account.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Value("${jwt.secret}")
    String secretKey;

    public String authenticateUser(CustomerLoginRequestDTO customerLoginDTO) throws AccessDeniedException {
        Customer customer = this.customerRepository.findByEmail(customerLoginDTO.getEmail());

        if(customer == null) {
            throw new AccessDeniedException("Customer with this email was not found");
        }

        if (!passwordEncoder.matches(customerLoginDTO.getPassword(), customer.getPassword())) {
            throw new AccessDeniedException("Credentials don't match");
        }

        return this.getJWTToken(customerLoginDTO.getEmail());
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private String getJWTToken(String email) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(email)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }

    public Customer getAuthenticatedCustomer() {
        Authentication authentication = authenticationFacade.getAuthentication();
        String email = authentication.getName();

        return this.customerRepository.findByEmail(email);
    }
}
