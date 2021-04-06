package dao;

import connections.ConnectionMongo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import models.Book;
import org.bson.Document;
import resources.ReadProperties;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class DaoBookingMongo implements DaoBooking{
    /**
     * @author Roger Vinyals
     *
     * Insereix l'objecte que li passen per parametre
     * a la base de dades utilitzant mongoDB
     *
     * @param book l'objecte tipo Book que vols inserir
     *
     */
    @Override
    public void insert(Book book) {
        ConnectionMongo mongodb = new ConnectionMongo();
        ReadProperties readProperties = new ReadProperties();
        String table = readProperties.getDatabaseTableR();

        int idRestaruant = book.getIdRestaurant();
        boolean find = false;

        try {
            mongodb.connect();
            MongoDatabase dataBase = mongodb.getDatabase();
            MongoCollection<Document> collection = dataBase.getCollection(table);

            MongoCursor<Document> cursor = collection.find().iterator();
            while (cursor.hasNext() && !find) {
                Document d = cursor.next();
                if (d.getInteger("idRestaurant") == idRestaruant) {
                    List<Document> bookingList = d.getList("bookingList", Document.class);

                    bookingList.add((new Document("idBook", book.getIdBook())
                            .append("idRestaurant", book.getIdRestaurant())
                            .append("bookName", book.getBookName())
                            .append("bookDate", book.getBookDate())
                            .append("dinersBook", book.getDinersBook())
                            .append("tableN", book.getTableN())
                            .append("vegetarianMenu", book.isVegetarianMenu())));

                    d.replace("bookingList", bookingList);

                    collection.replaceOne(eq("idRestaurant", idRestaruant), d);

                    find = true;
                }
            }
        } finally {
            mongodb.close();
        }
    }

    /**
     * @author Roger Vinyals
     *
     * Passant-li un id de un Book, busca el book dins de la base de dades
     *
     * @param id id del book que es vol buscar
     *
     * @return book que es volia trobar
     */
    @Override
    public Book getById(Integer id) {

        ConnectionMongo mongodb = new ConnectionMongo();
        ReadProperties readProperties = new ReadProperties();
        String table = readProperties.getDatabaseTableR();

        Book book;
        int idRestaurant = 0;
        String bookName = null;
        Date bookDateBeafore = null;
        int dinersBook = 0;
        int tableN= 0;
        boolean vegetarianMenu = false;
        boolean find = false;

        try {

            mongodb.connect();
            MongoDatabase dataBase = mongodb.getDatabase();
            MongoCollection<Document> collection = dataBase.getCollection(table);

            MongoCursor<Document> cursor = collection.find().iterator();
            while (cursor.hasNext() && !find) {
                Document d = cursor.next();
                List<Document> bookingList = d.getList("bookingList", Document.class);

                for (Document subdoc : bookingList) {
                    if (subdoc.getInteger("idBook") == id) {

                        idRestaurant = subdoc.getInteger("idRestaurant");
                        bookName = subdoc.getString("bookName");
                        bookDateBeafore = subdoc.getDate("bookDate");
                        dinersBook = subdoc.getInteger("dinersBook");
                        tableN = subdoc.getInteger("tableN");
                        vegetarianMenu = subdoc.getBoolean("vegetarianMenu");

                        find = true;
                    }
                }
            }
        } finally {
            mongodb.close();
        }
        LocalDate bookDate = bookDateBeafore.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        book = new Book(id, idRestaurant, bookName, bookDate, dinersBook, tableN, vegetarianMenu);
        if (find) {
            return book;
        } else {
            System.out.println("No s'ha trobat ningún");
            return null;
        }
    }

    /**
     * @author Roger Vinyals
     *
     * Recupera totes les Books de tots els restaurants
     *
     * @return Llistat de tots els books
     */
    @Override
    public List<Book> getAll() {
        ConnectionMongo mongodb = new ConnectionMongo();
        ReadProperties readProperties = new ReadProperties();
        String table = readProperties.getDatabaseTableR();

        List<Book> listBooks = new ArrayList<>();

        try {
            mongodb.connect();
            MongoDatabase dataBase = mongodb.getDatabase();
            MongoCollection<Document> collection = dataBase.getCollection(table);

            MongoCursor<Document> cursor = collection.find().iterator();
            while (cursor.hasNext()) {
                Document d = cursor.next();

                List<Document> bookingList = d.getList("bookingList", Document.class);
                for (Document subdoc : bookingList) {

                    int idBook = subdoc.getInteger("idBook");
                    int idRestaurant = subdoc.getInteger("idRestaurant");
                    String bookName = subdoc.getString("bookName");
                    Date bookDateBeafore = subdoc.getDate("bookDate");
                    int dinersBook = subdoc.getInteger("dinersBook");
                    int tableN = subdoc.getInteger("tableN");
                    boolean vegetarianMenu = subdoc.getBoolean("vegetarianMenu");

                    LocalDate bookDate = bookDateBeafore.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    Book book = new Book(idBook, idRestaurant, bookName, bookDate, dinersBook, tableN, vegetarianMenu);

                    listBooks.add(book);
                }
            }
        } finally {
            mongodb.close();
        }
        return listBooks;
    }

    /**
     * @author Roger Vinyals
     *
     * Elimina de la base de dades el Book que vols per id
     *
     * @param id idBook del Book que vols eliminar
     */
    @Override
    public void delete(Integer id) {
        ConnectionMongo mongodb = new ConnectionMongo();
        ReadProperties readProperties = new ReadProperties();
        String table = readProperties.getDatabaseTableR();

        boolean find = false;
        int idRestaruant = -1;
        Document docEliminar = null;

        try {
            mongodb.connect();
            MongoDatabase dataBase = mongodb.getDatabase();
            MongoCollection<Document> collection = dataBase.getCollection(table);

            MongoCursor<Document> cursor = collection.find().iterator();
            while (cursor.hasNext() && !find) {
                Document d = cursor.next();

                List<Document> bookingList = d.getList("bookingList", Document.class);
                for (Document subdoc : bookingList) {
                    if (subdoc.getInteger("idBook") == id) {
                        idRestaruant = subdoc.getInteger("idRestaurant");
                        docEliminar = subdoc;
                        find = true;
                    }
                }
                if (find && idRestaruant != -1) {
                    bookingList.remove(docEliminar);
                    d.replace("bookingList", bookingList);
                    collection.replaceOne(eq("idRestaurant", idRestaruant), d);
                }
            }
        } finally {
            mongodb.close();
            System.out.println("Close");
        }
    }

    /**
     * @author Roger Vinyals
     *
     * Métode que actualitza l'objecte tipo Book de la base de dades
     *
     * @param book Object Book
     */
    @Override
    public void update(Book book) {
        delete(book.getIdBook());
        insert(book);
    }

}
