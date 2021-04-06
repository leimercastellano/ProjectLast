package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class principal implements Initializable {
    public static String seletedMethod ="";

    @FXML
    private Button btnJdbc;
    @FXML
    private Button btnXml;
    @FXML
    private Button btnMongo;


    @FXML
    public void clickJdbc(ActionEvent actionEvent) {
        seletedMethod="JDBC";
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("\\..\\views\\home.fxml"));
            Stage stage = (Stage) btnJdbc.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);



        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void clickXml(ActionEvent actionEvent) {
        seletedMethod="XML";
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("\\..\\views\\home.fxml"));
            Stage stage = (Stage) btnXml.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void clickMongo(ActionEvent actionEvent) {
        seletedMethod="MONGODB";
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("\\..\\views\\home.fxml"));
            Stage stage = (Stage) btnMongo.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
