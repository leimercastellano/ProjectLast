package controllers;

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
import models.Restaurant;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class editRestaurant implements Initializable {

    @FXML
    private Button btnGoBack;
    @FXML
    private TableView<Book> tblBook;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtCity;
    @FXML
    private TextField txtCapacity;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colNB;
    @FXML
    private TableColumn colDate;
    @FXML
    private TableColumn colNDin;
    @FXML
    private TableColumn colTableN;
    @FXML
    private TableColumn colVMenu;

    private ObservableList<Book> observableBooks;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnImg();
        observableBooks = FXCollections.observableArrayList();
        this.colId.setCellValueFactory(new PropertyValueFactory("idBook"));
        this.colNB.setCellValueFactory(new PropertyValueFactory("bookName"));
        this.colDate.setCellValueFactory(new PropertyValueFactory("bookDate"));
        this.colNDin.setCellValueFactory(new PropertyValueFactory("dinersBook"));
        this.colTableN.setCellValueFactory(new PropertyValueFactory("tableN"));
        this.colVMenu.setCellValueFactory(new PropertyValueFactory("vegetarianMenu"));

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

    public void btnImg() {
        Image imageDecline = new Image(getClass().getResourceAsStream("\\..\\images\\gobackCircle.png"));

        ImageView imageView1 = new ImageView(imageDecline);
        imageView1.setFitHeight(30);
        imageView1.setFitWidth(30);
        //btnBack.setGraphic(new ImageView(imageDecline));
        btnGoBack.setGraphic(imageView1);
        // btnBack.setStyle("-fx-font: 12 arial; -fx-base: #b6e7c9;");
    }

    private void jdbcSelection() {

        int idRestaurantSelected = home.restaurantSelected.getIdRestaurant();
        List<Book> booksSelected = new ArrayList<>();

        GestorPersistencia gestorPersistenciaJdbc = new GestorPersistenciaJDBC();
        List<Book> booksList = gestorPersistenciaJdbc.getAll_Bookings();

        for(int i = 0; i < booksList.size(); i++){
            if(idRestaurantSelected == booksList.get(i).getIdRestaurant()){
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

        for(int i = 0; i < booksList.size(); i++){
            if(idRestaurantSelected == booksList.get(i).getIdRestaurant()){
                booksSelected.add(booksList.get(i));
            }
        }

        loadBooks(booksSelected);
    }

    private void xmlSelection() {

    }

    private void loadBooks(List<Book> booksList) {
        for (int i = 0; i < booksList.size(); i++) {
            this.observableBooks.add(booksList.get(i));
        }
        this.tblBook.setItems(observableBooks);
    }

    public void editMyRestaurant() {
        GestorPersistencia gestorPersistenciaJdbc = new GestorPersistenciaJDBC();
        GestorPersistencia gestorPersistenciaMongo = new GestorPersistenciaMongo();

        String name = this.txtName.getText();
        String city = this.txtCity.getText();
        int capacity = Integer.parseInt(this.txtCapacity.getText());

        Restaurant r = new Restaurant(home.restaurantSelected.getIdRestaurant(), name, city, capacity);

        if (home.selected.equals("JDBC")) {
            gestorPersistenciaJdbc.update_Restaurant(r);

        } else if (home.selected.equals("MONGODB")) {
            gestorPersistenciaMongo.update_Restaurant(r);
        }

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("S'han actualitzat les dades del restaurant");
        alert.showAndWait();

        clear();
    }

    public void deleteMyRestaurant() {
        GestorPersistencia gestorPersistenciaJdbc = new GestorPersistenciaJDBC();
        GestorPersistencia gestorPersistenciaMongo = new GestorPersistenciaMongo();

        if (home.selected.equals("JDBC")) {
            gestorPersistenciaJdbc.deleteOne_Restaurant(home.restaurantSelected.getIdRestaurant());

        } else if (home.selected.equals("MONGODB")) {
            gestorPersistenciaMongo.deleteOne_Restaurant(home.restaurantSelected.getIdRestaurant());
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("S'ha eliminat el restaurant");
        alert.showAndWait();

        clickGoBack();
    }

    public void clear() {
        txtName.clear();
        txtCity.clear();
        txtCapacity.clear();
    }

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
}
