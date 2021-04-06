package dao;

import connections.ConnectionXML;
import models.Restaurant;
import org.w3c.dom.*;

import java.io.IOException;
import java.util.List;

public class DaoRestaurantXML implements DaoRestaurant {
    @Override
    public List<Restaurant> searchByCity(String cityName) throws IOException {
        return null;
    }

    /**
     * @author Wilter Torres
     * <p>
     * El metode crea un element amb els seus atributs corresponents desde un objecte
     */
    @Override
    public void insert(Restaurant object) {
        ConnectionXML connection = new ConnectionXML();

        try {
            connection.connect();
            Document doc = connection.getDoc();
            Node root = doc.getFirstChild();

            Element restaurant = doc.createElement("Restaurant");
            root.appendChild(restaurant);

            restaurant.setAttribute("id", String.valueOf(object.getIdRestaurant()));
            restaurant.setAttribute("name", object.getName());
            restaurant.setAttribute("city", object.getCity());
            restaurant.setAttribute("capacity", String.valueOf(object.getCapacity()));

            connection.close();
        } catch (DOMException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Restaurant getById(Integer id) {
        return null;
    }

    @Override
    public List<Restaurant> getAll() {
        return null;
    }

    /**
     * @author Wilter Torres
     * <p>
     * El metode mitjançant id busca i elimina el restaurant sencer
     */
    @Override
    public void delete(Integer id) {
        ConnectionXML connection = new ConnectionXML();
        try {
            connection.connect();
            Document doc = connection.getDoc();
            NodeList list = doc.getElementsByTagName("Restaurant");
            for (int i = 0; i < list.getLength(); i++) {
                Element restaurant = (Element) list.item(i);
                if (restaurant.getAttributeNode("idRestaurant").getTextContent().equalsIgnoreCase(String.valueOf(id))) {
                    Element delete = (Element) restaurant.getElementsByTagName("Restaurant").item(0);
                    restaurant.removeChild(delete);
                }

            }
            connection.close();
        } catch (DOMException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author Wilter Torres
     * <p>
     * El metode fa un update eliminant i inserint
     */
    @Override
    public void update(Restaurant object) {
        delete(object.getIdRestaurant());
        insert(object);
    }
}
