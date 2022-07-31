package cinema.shop.service.impl;

import cinema.shop.dao.UserDao;
import cinema.shop.lib.Inject;
import cinema.shop.lib.Service;
import cinema.shop.model.User;
import cinema.shop.service.UserService;
import cinema.shop.util.HashUtil;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;

    @Override
    public User add(User user) {
        user.setSalt(HashUtil.getSalt());
        user.setPassword(HashUtil.hashPassword(user.getPassword(), user.getSalt()));
        return userDao.add(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
