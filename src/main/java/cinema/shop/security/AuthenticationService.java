package cinema.shop.security;

import cinema.shop.exception.AuthenticationException;
import cinema.shop.exception.RegistrationException;
import cinema.shop.model.User;

public interface AuthenticationService {
    User login(String email, String password) throws AuthenticationException;

    User register(String email, String password) throws RegistrationException;
}
