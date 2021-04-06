package dao;

import models.Book;
import models.Restaurant;

import java.util.List;

public class GestorPersistenciaXML implements GestorPersistencia {
    private final DaoRestaurantXML daoRestaurantXML = new DaoRestaurantXML();
    private final DaoBookingXML daoBookingXml = new DaoBookingXML();

    @Override
    public List<Restaurant> getAll_Restaurants() {
        return null;
    }

    @Override
    public void insert_Restaurant(Restaurant restaurant) {
        daoRestaurantXML.insert(restaurant);
    }

    @Override
    public Restaurant findById_Restaurant(int id) {
        return null;
    }

    @Override
    public void deleteOne_Restaurant(int id) {
        daoRestaurantXML.delete(id);
    }

    @Override
    public void update_Restaurant(Restaurant restaurant) {
        daoRestaurantXML.update(restaurant);
    }

    @Override
    public List<Restaurant> searchByCity(String cityName) {
        return null;
    }

    @Override
    public List<Book> getAll_Bookings() {
        return null;
    }

    @Override
    public void insert_Book(Book book) {
        daoBookingXml.insert(book);
    }

    @Override
    public Book findById_Book(int id) {
        return null;
    }

    @Override
    public void deleteOne_Book(int id) {
        daoBookingXml.delete(id);
    }

    @Override
    public void update_Book(Book book) {
        daoBookingXml.update(book);
    }
}