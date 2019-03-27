package app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class krypto1 extends Application {
    private String mainTitle = "BSK - Kryptografia";
    // main - główne okno do wyświetlania zawartości
    FlowPane main;
    
    @Override
    public void start(Stage primaryStage) {
        
        // inicjalizacja menu aplikacji
        MenuBar menubar = new MenuBar();
        Menu menu1 = new Menu("PS2");
        Menu menu2 = new Menu("PS3");
        MenuItem menuitem1 = new MenuItem("Rail fence");
        // akcja po wybraniu elementu 1 menu
        menuitem1.setOnAction( e -> {
           primaryStage.setTitle(mainTitle + " - Rail fence");
           if(!main.getChildren().isEmpty()){
                main.getChildren().clear();
           }
           // tworzenie layoutu do zadania
           LayoutRailFence();
        });
        MenuItem menuitem2 = new MenuItem("Przestawianie macierzowe");
        // akcja po wybraniu elementu 2 menu
        menuitem2.setOnAction( e -> {
            primaryStage.setTitle(mainTitle + " - Przestawianie macierzowe");
            // czyszczenie okna
            if(!main.getChildren().isEmpty()){
                main.getChildren().clear();
            }
            // tworzenie layoutu do zadania
            LayoutPrzestawianieMacierzowe();
        });
        MenuItem menuitem3 = new MenuItem("Szyfrowanie Cezara");
        // akcja po wybraniu elementu 3 menu
        menuitem3.setOnAction( e -> {
            primaryStage.setTitle(mainTitle + " - Szyfrowanie Cezara");
            if(!main.getChildren().isEmpty()){
                main.getChildren().clear();
            }      
            // tworzenie layoutu do zadania
            LayoutSzyfrCezara();
        });
        MenuItem menuitem4 = new MenuItem("Szyfrowanie Vigenere'a");
        // akcja po wybraniu elementu 4 menu
        menuitem4.setOnAction( e -> {
            primaryStage.setTitle(mainTitle + " - Szyfrowanie Vigenere'a");
            if(!main.getChildren().isEmpty()){
                main.getChildren().clear();
            }
            // tworzenie layoutu do zadania
            LayoutSzyfrowanieVigenere();
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
        main = new FlowPane();
        main.setPadding(new Insets(20, 20, 20, 20));
        root.getChildren().add(main);
        
        // tworzenie okna aplikacji
        Scene scene = new Scene(root, 600, 400);   
        primaryStage.setTitle(mainTitle);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public void LayoutRailFence(){
        Label plainTextLabel = new Label("Wprowadź tekst w postaci jawnej do zaszyfrowania:");
        Label keyLabel = new Label("Podaj liczbę poziomów n:");
        Label cipherTextLabel = new Label("Tekst w postaci zaszyfrowanej:");
        Label cipherText = new Label(" - pusty - ");
        TextField plainTextField = new TextField();
        plainTextField.setPadding(new Insets(10,10,10,10));
        TextField keyTextField = new TextField("3");
        keyTextField.setPadding(new Insets(10,10,10,10));
        keyTextField.prefWidth(50);
        HBox keyBox = new HBox();
        keyBox.setSpacing(10);
        keyBox.setAlignment(Pos.CENTER_LEFT);
        keyBox.getChildren().add(keyLabel);
        keyBox.getChildren().add(keyTextField);
        Button doCipher = new Button("Zaszyfruj");
        doCipher.setOnAction( c -> {
            String plainText = plainTextField.getText();
            plainText = plainText.replaceAll("\\s", "");
            int key = Integer.parseInt(keyTextField.getText());
            String cipher = RailFence(plainText, key);
            cipherText.setText(cipher);
        });
        main.setOrientation(Orientation.VERTICAL);
        main.setVgap(10);
        main.getChildren().add(plainTextLabel);
        main.getChildren().add(plainTextField);
        main.getChildren().add(keyBox);
        main.getChildren().add(doCipher);
        main.getChildren().add(cipherTextLabel);
        main.getChildren().add(cipherText);
        
    }
    
    public String RailFence(String plainTextToCipher, int key){
        char[] plainText = new char[plainTextToCipher.length()];
        plainText = plainTextToCipher.toCharArray();
        char[][] plainTable = new char[key][plainText.length];
        boolean goDown = false;
        for(int i=0, j=0; j<plainText.length; j++) {
            if(i==0 || i==key-1)
                goDown = !goDown;
            plainTable[i][j]=plainText[j];
            if(goDown)
                i++;
            else
                i--;
        }
        
        StringBuilder cipher = new StringBuilder();
        for(int i=0; i<key; i++)
            for(int j=0; j<plainText.length; j++)
                if(plainTable[i][j]!=0)
                    cipher.append(plainTable[i][j]);
        return cipher.toString();
    }
    
    public void LayoutPrzestawianieMacierzowe() {
        int[] key = new int[5];
        Label plainTextLabel = new Label("Wprowadź tekst w postaci jawnej do zaszyfrowania:");
        Label keyLabel = new Label("Podaj klucz do zaszyfrowania dla d = 5");
        Label cipherLabel = new Label("Tekst w postacji zaszyfrowanej:");
        Label cipherText = new Label(" - pusty -");
        TextField plainTextField = new TextField();
        plainTextField.setPadding(new Insets(10, 10, 10, 10));
        HBox keyBox = new HBox();
        keyBox.setSpacing(10);
        TextField key1 = new TextField("3");
        TextField key2 = new TextField("4");
        TextField key3 = new TextField("1");
        TextField key4 = new TextField("5");
        TextField key5 = new TextField("2");
        key1.setPadding(new Insets(10, 10, 10, 10));
        key2.setPadding(new Insets(10, 10, 10, 10));
        key3.setPadding(new Insets(10, 10, 10, 10));
        key4.setPadding(new Insets(10, 10, 10, 10));
        key5.setPadding(new Insets(10, 10, 10, 10));
        key1.setPrefWidth(30);
        key2.setPrefWidth(30);
        key3.setPrefWidth(30);
        key4.setPrefWidth(30);
        key5.setPrefWidth(30);
        keyBox.getChildren().add(key1);
        keyBox.getChildren().add(key2);
        keyBox.getChildren().add(key3);
        keyBox.getChildren().add(key4);
        keyBox.getChildren().add(key5);
        Button doCipher = new Button("Zaszyfruj");
        doCipher.setOnAction(c -> {
            key[0] = Integer.parseInt(key1.getText());
            key[1] = Integer.parseInt(key2.getText());
            key[2] = Integer.parseInt(key3.getText());
            key[3] = Integer.parseInt(key4.getText());
            key[4] = Integer.parseInt(key5.getText());
            String plainText = plainTextField.getText();
            plainText = plainText.replaceAll("\\s", "");
            String cipher = PrzestawianieMacierzowe(plainText, key);
            cipherText.setText(cipher);
        });
        main.setOrientation(Orientation.VERTICAL);
        main.setVgap(10);
        main.getChildren().add(plainTextLabel);
        main.getChildren().add(plainTextField);
        main.getChildren().add(keyLabel);
        main.getChildren().add(keyBox);
        main.getChildren().add(doCipher);
        main.getChildren().add(cipherLabel);
        main.getChildren().add(cipherText);
    }
    
    public String PrzestawianieMacierzowe(String plainTextToCipher, int[] key){
        char[] plainText = new char[plainTextToCipher.length()];
        plainText = plainTextToCipher.toCharArray();
        double floor = Math.floor(plainText.length / 5);
        char[][] plainTable = new char[5][(int)floor+1];     
        for(int y=0;y<=floor;y++){
            for(int x=0;x<5;x++){
                if((y*5)+(x) == plainText.length) break;
                plainTable[x][y] = plainText[(y*5)+(x)];
            }
        }
        StringBuilder cipher = new StringBuilder();
        for(int y=0;y<=floor;y++){
            for(int x=0;x<5;x++){
                if((y*5)+(x) == plainText.length) break;
                cipher.append(plainTable[key[x]-1][y]);
            }
        }
        return cipher.toString();
    }
    
    public void LayoutSzyfrCezara(){
        Label plainTextLabel = new Label("Wprowadź tekst w postaci jawnej do zaszyfrowania:");
        Label keyLabel = new Label("Podaj klucz do szyfrowania (0-26):");
        Label cipherTextLabel = new Label("Tekst w postaci zaszyfrowanej:");
        Label cipherText = new Label(" - pusty - ");
        TextField plainTextField = new TextField();
        plainTextField.setPadding(new Insets(10,10,10,10));
        TextField keyTextField = new TextField("10");
        keyTextField.setPadding(new Insets(10,10,10,10));
        keyTextField.prefWidth(50);
        HBox keyBox = new HBox();
        keyBox.setSpacing(10);
        keyBox.setAlignment(Pos.CENTER_LEFT);
        keyBox.getChildren().add(keyLabel);
        keyBox.getChildren().add(keyTextField);
        Button doCipher = new Button("Zaszyfruj");
        doCipher.setOnAction( c -> {
            String plainText = plainTextField.getText();
            plainText = plainText.replaceAll("\\s", "");
            int key = Integer.parseInt(keyTextField.getText());
            String cipher = SzyfrCezara(plainText, key);
            cipherText.setText(cipher);
        });
        main.setOrientation(Orientation.VERTICAL);
        main.setVgap(10);
        main.getChildren().add(plainTextLabel);
        main.getChildren().add(plainTextField);
        main.getChildren().add(keyBox);
        main.getChildren().add(doCipher);
        main.getChildren().add(cipherTextLabel);
        main.getChildren().add(cipherText);
       
    }
    
    public String SzyfrCezara(String plainTextToCipher, int key){
        char[] plainText = new char[plainTextToCipher.length()];
        plainText = plainTextToCipher.toCharArray();
        StringBuilder cipher = new StringBuilder();
        for(int i=0;i<plainText.length;i++){
            if(Character.isUpperCase(plainText[i])){
                char znak = (char)(((int)plainText[i] + key - 65) % 26 + 65);
                cipher.append(znak);
            } else {
                char znak = (char)(((int)plainText[i] + key - 97) % 26 + 97);
                cipher.append(znak);
            }
        }
        return cipher.toString();
    }
    
    public void LayoutSzyfrowanieVigenere(){
        
    }
    
    public void SzyfrowanieVigenere(){
        
    }
}
