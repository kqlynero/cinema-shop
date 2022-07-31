package cinema.shop.security;

import cinema.shop.exception.AuthenticationException;
import cinema.shop.exception.RegistrationException;
import cinema.shop.lib.Inject;
import cinema.shop.lib.Service;
import cinema.shop.model.User;
import cinema.shop.service.ShoppingCartService;
import cinema.shop.service.UserService;
import cinema.shop.util.HashUtil;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;
    @Inject
    private ShoppingCartService shoppingCartService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isPresent() && matchPasswords(password, userFromDb.get())) {
            return userFromDb.get();
        }
        throw new AuthenticationException("Incorrect email or password!");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with this email is already registered!");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userService.add(user);
        shoppingCartService.registerNewShoppingCart(user);
        return user;
    }

    private boolean matchPasswords(String rawPassword, User userFromDb) {
        String hashedPassword = HashUtil.hashPassword(rawPassword, userFromDb.getSalt());
        return hashedPassword.equals(userFromDb.getPassword());
    }
}
