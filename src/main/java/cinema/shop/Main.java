package cinema.shop;

import cinema.shop.exception.RegistrationException;
import cinema.shop.lib.Injector;
import cinema.shop.model.CinemaHall;
import cinema.shop.model.Movie;
import cinema.shop.model.MovieSession;
import cinema.shop.model.ShoppingCart;
import cinema.shop.model.User;
import cinema.shop.security.AuthenticationService;
import cinema.shop.service.CinemaHallService;
import cinema.shop.service.MovieService;
import cinema.shop.service.MovieSessionService;
import cinema.shop.service.OrderService;
import cinema.shop.service.ShoppingCartService;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    private static final Injector injector = Injector.getInstance("cinema.shop");

    public static void main(String[] args) throws RegistrationException {
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall firstCinemaHall = new CinemaHall();
        firstCinemaHall.setCapacity(100);
        firstCinemaHall.setDescription("first hall with capacity 100");

        CinemaHall secondCinemaHall = new CinemaHall();
        secondCinemaHall.setCapacity(200);
        secondCinemaHall.setDescription("second hall with capacity 200");

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(firstCinemaHall);
        cinemaHallService.add(secondCinemaHall);

        System.out.println(cinemaHallService.getAll());
        System.out.println(cinemaHallService.get(firstCinemaHall.getId()));

        MovieSession tomorrowMovieSession = new MovieSession();
        tomorrowMovieSession.setCinemaHall(firstCinemaHall);
        tomorrowMovieSession.setMovie(fastAndFurious);
        tomorrowMovieSession.setShowTime(LocalDateTime.now().plusDays(1L));

        MovieSession yesterdayMovieSession = new MovieSession();
        yesterdayMovieSession.setCinemaHall(firstCinemaHall);
        yesterdayMovieSession.setMovie(fastAndFurious);
        yesterdayMovieSession.setShowTime(LocalDateTime.now().minusDays(1L));

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(tomorrowMovieSession);
        movieSessionService.add(yesterdayMovieSession);

        System.out.println(movieSessionService.get(yesterdayMovieSession.getId()));
        System.out.println(movieSessionService.findAvailableSessions(
                        fastAndFurious.getId(), LocalDate.now()));

        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        User user = authenticationService.register("qwerty@gmail.com", "qwerty");

        ShoppingCartService shoppingCartService =
                (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
        ShoppingCart shoppingCart = shoppingCartService.getByUser(user);

        OrderService orderService =
                (OrderService) injector.getInstance(OrderService.class);
        orderService.completeOrder(shoppingCart);
        orderService.getOrdersHistory(user);
    }
}
