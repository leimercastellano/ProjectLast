package resources;

import connections.ConnectionJDBC;
import connections.ConnectionMongo;
import dao.DaoBookingJDBC;
import dao.DaoRestaurantJDBC;
import dao.GestorPersistencia;
import dao.GestorPersistenciaJDBC;
import models.Book;
import models.Restaurant;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, SQLException {
ReadProperties r = new ReadProperties();



 /*       String date = "15/11/2021";
        String[] date1 = date.split("/");

        for (String date:
             ) {

        }*/




        //GetAllBooking
       // DaoBookingJDBC daoBookingJDBC = new DaoBookingJDBC();
        //DaoRestaurantJDBC daoRestaurantJDBC = new DaoRestaurantJDBC();
    /*   List<Book> listaBooks= daoBookingJDBC.getAll();

        for (Book b:listaBooks
        ) {
            System.out.println(b.toString());
        }*/
//Insert Book;


        //LocalDateTime Now
       /* LocalDate date = LocalDate.now();
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        String text = date.format(formatters);
        LocalDate parsedDate = LocalDate.parse(text, formatters);*/

        //LocalDate
        /*String dataComanda = "13/03/2021";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        LocalDate localDate = LocalDate.parse(dataComanda, formatter);*/


       /* System.out.println(localDate);
        Book book = new Book(007, 2, "pepeto", localDate, 7, 6, true);
        daoBookingJDBC.insert(book);*/


        //FindById
   /* int id =11;
    Book book =daoBookingJDBC.findById(id);

        System.out.println(book.toString());*/


        //DeleteOne
        /*int id=11;
        daoBookingJDBC.deleteOne(id);*/

        //Update
        /*Book book = new Book(1, 1, "juana", localDate, 5, 7, true);
        daoBookingJDBC.update(book);*/



        //Restaurant

//GetAllRestaurant
/*
        List<Restaurant> lista = daoRestaurantJDBC.getAll();

        for (Restaurant r: lista
             ) {
            System.out.println(r.toString());
        }
*/

        //

/*Restaurant r = new Restaurant(9, "Domino's", "Montreal", 95);

daoRestaurantJDBC.insert(r);*/

/*
        Restaurant rr = daoRestaurantJDBC.findById(9);

        System.out.println(rr.toString());*/

        //Delete

        //daoRestaurantJDBC.deleteOne(9);


        //Update
/*
        Restaurant r = new Restaurant(1, "Mcdonal's", "Berlin", 200);

        daoRestaurantJDBC.update(r);*/

    /*    GestorPersistencia gestorPersistencia = new GestorPersistenciaJDBC();
        List<Restaurant> lis = gestorPersistencia.getAll_Restaurants();

        for (Restaurant r:lis
             ) {
            System.out.println(r);
        }
*/


    }
}
