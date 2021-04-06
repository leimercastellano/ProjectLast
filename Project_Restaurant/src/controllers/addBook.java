package controllers;

import Utils.Comprobaciones;
import dao.GestorPersistencia;
import dao.GestorPersistenciaJDBC;
import dao.GestorPersistenciaMongo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.Book;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class addBook implements Initializable {
    //TextField

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtDate;
    @FXML
    private TextField txtNroOfDinners;
    @FXML
    private TextField txtTableNro;
    @FXML
    private TextField txtVgMenu;
    @FXML
    private Button btnGoBack;
    @FXML
    private Button btnAddBook;
    @FXML
    private TableView<Book> tblBooks;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colName;
    @FXML
    private TableColumn colDate;
    @FXML
    private TableColumn colNDiners;
    @FXML
    private TableColumn colTableN;
    @FXML
    private TableColumn colVegetarianMenu;

    private ObservableList<Book> observableBooks;
    public int idBookSelectioned = 0;

    /**
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnImg();
        observableBooks = FXCollections.observableArrayList();
        this.colId.setCellValueFactory(new PropertyValueFactory("idBook"));
        this.colName.setCellValueFactory(new PropertyValueFactory("bookName"));
        this.colDate.setCellValueFactory(new PropertyValueFactory("bookDate"));
        this.colNDiners.setCellValueFactory(new PropertyValueFactory("dinersBook"));
        this.colTableN.setCellValueFactory(new PropertyValueFactory("tableN"));
        this.colVegetarianMenu.setCellValueFactory(new PropertyValueFactory("vegetarianMenu"));


        if (home.selected.equals("JDBC")) {
            jdbcSelection();
        }
        if (home.selected.equals("XML")) {
            xmlSelection();
        }
        if (home.selected.equals("MONGODB")) {
            mongoSelection();
        }
    }

    /**
     * Este metodo recibe la id seleccionada para luego trabajar en la db
     */
    private void jdbcSelection() {
        int idRestaurantSelected = home.restaurantSelected.getIdRestaurant();
        List<Book> booksSelected = new ArrayList<>();

        GestorPersistencia gestorPersistenciaJdbc = new GestorPersistenciaJDBC();
        List<Book> booksList = gestorPersistenciaJdbc.getAll_Bookings();

        for (int i = 0; i < booksList.size(); i++) {
            if (idRestaurantSelected == booksList.get(i).getIdRestaurant()) {
                booksSelected.add(booksList.get(i));
            }
        }
        loadBooks(booksSelected);
    }

    private void mongoSelection() {
        GestorPersistenciaMongo gestorPersistenciaMongo = new GestorPersistenciaMongo();
        List<Book> booksList = gestorPersistenciaMongo.getAll_Bookings();

        int idRestaurantSelected = home.restaurantSelected.getIdRestaurant();
        List<Book> booksSelected = new ArrayList<>();

        for (int i = 0; i < booksList.size(); i++) {
            if (idRestaurantSelected == booksList.get(i).getIdRestaurant()) {
                booksSelected.add(booksList.get(i));
            }
        }

        loadBooks(booksSelected);
    }

    private void xmlSelection() {

    }

    /**
     * @param booksList Carga los books en el observable list
     */
    private void loadBooks(List<Book> booksList) {
        for (int i = 0; i < booksList.size(); i++) {
            this.observableBooks.add(booksList.get(i));
        }
        this.tblBooks.setItems(observableBooks);
    }

    /**
     * Agregamos una imagen a los botones
     */
    public void btnImg() {
        Image imageDecline = new Image(getClass().getResourceAsStream("\\..\\images\\gobackCircle.png"));

        ImageView imageView1 = new ImageView(imageDecline);
        imageView1.setFitHeight(30);
        imageView1.setFitWidth(30);

        btnGoBack.setGraphic(imageView1);
        // btnGoBack.setStyle("-fx-font: 12 arial; -fx-base: #b6e7c9;");
    }


    /**
     * @autor Castellano Leimer
     *
     * Va a la Scena Anterior
     */
    public void clickGoBack() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("\\..\\views\\home.fxml"));
            Stage stage = (Stage) btnGoBack.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void clickAddBook() {
        GestorPersistencia gestorJdbc = new GestorPersistenciaJDBC();
        GestorPersistencia gestorMongo = new GestorPersistenciaMongo();
        LocalDate comprobacionDate;
        boolean comprobationVgMenu;
        int idSelectedRestaurant = home.restaurantSelected.getIdRestaurant();
        int idBook = Integer.parseInt(this.txtId.getText());
        String name = this.txtName.getText();
        String Date = this.txtDate.getText();
        int dinnersBook = Integer.parseInt(this.txtNroOfDinners.getText());
        int tableNro = Integer.parseInt(this.txtTableNro.getText());
        String vgMenu = this.txtVgMenu.getText();

//comprobar Fecha

        comprobationVgMenu = Comprobaciones.comprobacionVgMenu(vgMenu);
        comprobacionDate = Comprobaciones.comprobacionFecha(Date);


            Book b = new Book(idBook, idSelectedRestaurant, name, comprobacionDate, dinnersBook, tableNro, comprobationVgMenu);

            this.observableBooks.add(b);
            this.tblBooks.setItems(observableBooks);

            if (home.selected.equals("JDBC")) {
                System.out.println("JDBC");
                gestorJdbc.insert_Book(b);
            }
            if (home.selected.equals("XML")) {
                // System.out.println("XML");

            }
            if (home.selected.equals("MONGODB")) {
                System.out.println("Mongo");
                gestorMongo.insert_Book(b);
            }

            clear();

    }

    public void clear() {
        txtId.clear();
        txtNroOfDinners.clear();
        txtName.clear();
        txtTableNro.clear();
        txtVgMenu.clear();
        txtDate.clear();
    }

}
