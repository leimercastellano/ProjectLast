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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.Restaurant;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class addRestaurant implements Initializable {

    @FXML
    private Button btnBack;
    @FXML
    private TextField txtIdRestaurant;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtCity;
    @FXML
    private TextField txtCapacity;
    @FXML
    private Button btnAddRestaurant;
    @FXML
    private Button btnDeleteR;
    @FXML
    private Button btnEditR;
    @FXML
    private TableView<Restaurant> tblRestaurant;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colName;
    @FXML
    private TableColumn colCity;
    @FXML
    private TableColumn colCapacity;

    private ObservableList<Restaurant> restaurantObservableList;

    public void btnImg() {
        Image imageDecline = new Image(getClass().getResourceAsStream("\\..\\images\\gobackCircle.png"));

        ImageView imageView1 = new ImageView(imageDecline);
        imageView1.setFitHeight(30);
        imageView1.setFitWidth(30);


        //btnBack.setGraphic(new ImageView(imageDecline));
        btnBack.setGraphic(imageView1);
       // btnBack.setStyle("-fx-font: 12 arial; -fx-base: #b6e7c9;");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            btnImg();
        restaurantObservableList = FXCollections.observableArrayList();
        this.colId.setCellValueFactory(new PropertyValueFactory("IdRestaurant"));
        this.colName.setCellValueFactory(new PropertyValueFactory("Name"));
        this.colCity.setCellValueFactory(new PropertyValueFactory("City"));
        this.colCapacity.setCellValueFactory(new PropertyValueFactory("Capacity"));

        if (home.selected.equals("JDBC")) {
            System.out.println("JDBC");
            selectedJDBC();
        }
        if (home.selected.equals("XML")) {
            System.out.println("XML");

        }
        if (home.selected.equals("MONGODB")) {
            System.out.println("Mongo");
            selectedMONGO();
        }

    }

    private void selectedMONGO() {
        GestorPersistenciaMongo gestorPersistenciaMongo = new GestorPersistenciaMongo();
        List<Restaurant> restaurantList = gestorPersistenciaMongo.getAll_Restaurants();
        loadingRestaurants(restaurantList);
    }

    private void selectedJDBC() {
        GestorPersistencia gestorPersistenciaJdbc = new GestorPersistenciaJDBC();
        List<Restaurant> restaurantsList = gestorPersistenciaJdbc.getAll_Restaurants();
        loadingRestaurants(restaurantsList);

    }

    public void loadingRestaurants(List<Restaurant> restList) {
        for (int i = 0; i < restList.size(); i++) {
            this.restaurantObservableList.add(restList.get(i));
        }
        this.tblRestaurant.setItems(restaurantObservableList);

    }


    public void addMyRestaurant() {
        GestorPersistencia gestorJdbc = new GestorPersistenciaJDBC();
        GestorPersistencia gestorMongo = new GestorPersistenciaMongo();
        int id = Integer.parseInt(this.txtIdRestaurant.getText());
        String name = this.txtName.getText();
        String city = this.txtCity.getText();
        int capacity = Integer.parseInt(this.txtCapacity.getText());

      //Controlar el error
        Restaurant r = new Restaurant(id, name, city, capacity);
        this.restaurantObservableList.add(r);
        this.tblRestaurant.setItems(restaurantObservableList);

        if (home.selected.equals("JDBC")) {
            System.out.println("JDBC");

            gestorJdbc.insert_Restaurant(r);

        }
        if (home.selected.equals("XML")) {
            // System.out.println("XML");

        }
        if (home.selected.equals("MONGODB")) {
            System.out.println("Mongo");
            gestorMongo.insert_Restaurant(r);
        }

        clear();
    }

    public void clear() {
        txtIdRestaurant.clear();
        txtName.clear();
        txtCity.clear();
        txtCapacity.clear();
    }

    public void goBack(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("\\..\\views\\home.fxml"));
            Stage stage = (Stage) btnBack.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    }

