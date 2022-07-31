package cinema.shop.service.impl;

import cinema.shop.dao.MovieDao;
import cinema.shop.lib.Inject;
import cinema.shop.lib.Service;
import cinema.shop.model.Movie;
import cinema.shop.service.MovieService;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    @Inject
    private MovieDao movieDao;

    @Override
    public Movie add(Movie movie) {
        return movieDao.add(movie);
    }

    @Override
    public Movie get(Long id) {
        return movieDao.get(id).get();
    }

    @Override
    public List<Movie> getAll() {
        return movieDao.getAll();
    }
}
