package dao;

import connections.ConnectionXML;
import models.Book;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.List;

public class DaoBookingXML implements DaoBooking {


    /**
     * @author Wilter Torres
     * <p>
     * Busqueda de un restaurant per id i creacio d'un arbre "booking" al fitxer xml
     */
    @Override
    public void insert(Book obj) {
        ConnectionXML connection = new ConnectionXML();

        try {
            connection.connect();
            Document doc = connection.getDoc();


            NodeList restaurants = doc.getElementsByTagName("Restaurant");
            for (int i = 0; i < restaurants.getLength(); i++) {

                Element restaurant = (Element) restaurants.item(i);
                if (restaurant.getAttributeNode("id").getTextContent().equalsIgnoreCase(String.valueOf(obj.getIdRestaurant()))) {
                    Element book = doc.createElement("Book");
                    restaurant.appendChild(book);

                    Element idBook = doc.createElement("idBook");
                    idBook.appendChild(doc.createTextNode(String.valueOf(obj.getIdBook())));
                    book.appendChild(idBook);

                    Element idRestaurant = doc.createElement("idRestaurant");
                    idRestaurant.appendChild(doc.createTextNode(String.valueOf(obj.getIdRestaurant())));
                    book.appendChild(idRestaurant);

                    Element dinersBook = doc.createElement("dinersBook");
                    dinersBook.appendChild(doc.createTextNode(String.valueOf(obj.getDinersBook())));
                    book.appendChild(dinersBook);

                    Element tableN = doc.createElement("tableN");
                    tableN.appendChild(doc.createTextNode(String.valueOf(obj.getTableN())));
                    book.appendChild(tableN);

                    Element bookName = doc.createElement("bookName");
                    bookName.appendChild(doc.createTextNode(String.valueOf(obj.getBookName())));
                    book.appendChild(bookName);

                    Element bookDate = doc.createElement("bookDate");
                    bookDate.appendChild(doc.createTextNode(String.valueOf(obj.getBookDate())));
                    book.appendChild(bookDate);

                    Element vegetarianMenu = doc.createElement("vegetarianMenu");
                    vegetarianMenu.appendChild(doc.createTextNode(String.valueOf(obj.isVegetarianMenu())));
                    book.appendChild(vegetarianMenu);
                }
            }
            connection.close();
        } catch (DOMException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Book getById(Integer id) {
        return null;
    }

    @Override
    public List<Book> getAll() {
        return null;
    }



    /**
     * @author Wilter Torres
     * <p>
     * El metode mitjançant id busca i elimina el booking sencer
     */
    @Override
    public void delete(Integer id) {

        ConnectionXML connection = new ConnectionXML();
        try {
            connection.connect();
            Document doc = connection.getDoc();
            NodeList list = doc.getElementsByTagName("Restaurant");
            for (int i = 0; i < list.getLength(); i++) {
                Element booking = (Element) list.item(i);
                if (booking.getAttributeNode("idBook").getTextContent().equalsIgnoreCase(String.valueOf(id))) {
                    Element delete = (Element) booking.getElementsByTagName("Restaurant").item(0);
                    booking.removeChild(delete);
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
     * Update eliminant i inserint
     */
    @Override
    public void update(Book object) {
        delete(object.getIdRestaurant());
        insert(object);
    }



    /**
     * @author Wilter Torres
     * <p>
     * Metode alternatiu de update especific
     */
    public void updateXML(String toBeUpdated, String search, String value) {
        ConnectionXML connection = new ConnectionXML();
        try {
            connection.connect();
            Document doc = connection.getDoc();

            NodeList restaurant = doc.getElementsByTagName("Book");
            for (int i = 0; i < restaurant.getLength(); i++) {
                Element book = (Element) restaurant.item(i);
                if (book.getElementsByTagName(toBeUpdated).item(0).getTextContent().equalsIgnoreCase(search)) {
                    Element update = (Element) book.getElementsByTagName("bookName").item(0);
                    update.setTextContent(value);
                    break;
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
     * Metode alternatiu de eliminat especific
     */
    public void deleteXML(String id, String idValue, String value) {
        ConnectionXML connection = new ConnectionXML();
        try {
            connection.connect();
            Document doc = connection.getDoc();

            NodeList restaurant = doc.getElementsByTagName("Book");
            for (int i = 0; i < restaurant.getLength(); i++) {
                Element book = (Element) restaurant.item(i);
                if (book.getElementsByTagName(id).item(0).getTextContent().equalsIgnoreCase(idValue)) {
                    Element delete = (Element) book.getElementsByTagName(value).item(0);
                    book.removeChild(delete);
                }

            }

            connection.close();
        } catch (DOMException e) {
            e.printStackTrace();
        }
    }
}