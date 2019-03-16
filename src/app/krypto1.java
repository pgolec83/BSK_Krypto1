package app;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Paweł
 */
public class krypto1 extends Application {
    private String mainTitle = "Wspaniały świat kryptografii";
    
    @Override
    public void start(Stage primaryStage) {
        
        // inicjalizacja menu aplikacji
        MenuBar menubar = new MenuBar();
        Menu menu1 = new Menu("PS1");
        Menu menu2 = new Menu("PS2");
        MenuItem menuitem1 = new MenuItem("Rail fence");
        // akcja po wybraniu elementu 1 menu
        menuitem1.setOnAction( e -> {
           primaryStage.setTitle(mainTitle + " - Rail fence");
           RailFence();
        });
        MenuItem menuitem2 = new MenuItem("Przestawienie macierzowe");
        // akcja po wybraniu elementu 2 menu
        menuitem2.setOnAction( e -> {
            primaryStage.setTitle(mainTitle + " - Przestawienie macierzowe");
            PrzestawianieMacierzowe();
        });
        MenuItem menuitem3 = new MenuItem("Szyfrowanie Cezara");
        // akcja po wybraniu elementu 3 menu
        menuitem3.setOnAction( e -> {
            primaryStage.setTitle(mainTitle + " - Szyfrowanie Cezara");
            SzyfrCezara();
        });
        MenuItem menuitem4 = new MenuItem("Szyfrowanie Vigenere'a");
        // akcja po wybraniu elementu 4 menu
        menuitem4.setOnAction( e -> {
            primaryStage.setTitle(mainTitle + " - Szyfrowanie Vigenere'a");
            SzyfrowanieVigenere();
        });
        menu1.getItems().add(menuitem1);
        menu1.getItems().add(menuitem2);
        menu1.getItems().add(menuitem3);
        menu1.getItems().add(menuitem4);
        menubar.getMenus().add(menu1);
        menubar.getMenus().add(menu2);
        
        // główny layout aplikacji z implementacją menu
        VBox root = new VBox(menubar);
        
        // main - główne okno do wyświetlania zawartości
        StackPane main = new StackPane();
        root.getChildren().add(main);
        
        // tworzenie okna aplikacji
        Scene scene = new Scene(root, 600, 400);   
        primaryStage.setTitle("Wspaniały świat kryptografii");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public void RailFence(){
        
    }
    
    public void PrzestawianieMacierzowe(){
        
    }
    
    public void SzyfrCezara(){
        
    }
    
    public void SzyfrowanieVigenere(){
        
    }
}
