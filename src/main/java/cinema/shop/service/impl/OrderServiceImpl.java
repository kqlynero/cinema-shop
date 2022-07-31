package cinema.shop.service.impl;

import cinema.shop.dao.OrderDao;
import cinema.shop.lib.Inject;
import cinema.shop.lib.Service;
import cinema.shop.model.Order;
import cinema.shop.model.ShoppingCart;
import cinema.shop.model.User;
import cinema.shop.service.OrderService;
import cinema.shop.service.ShoppingCartService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Inject
    private OrderDao orderDao;
    @Inject
    private ShoppingCartService shoppingCartService;

    @Override
    public Order completeOrder(ShoppingCart shoppingCart) {
        Order order = new Order();
        order.setTickets(new ArrayList<>(shoppingCart.getTickets()));
        order.setUser(shoppingCart.getUser());
        order.setOrderDate(LocalDateTime.now());
        shoppingCartService.clearShoppingCart(shoppingCart);
        return orderDao.add(order);
    }

    @Override
    public List<Order> getOrdersHistory(User user) {
        return orderDao.getByUser(user);
    }
}
