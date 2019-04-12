package app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.BitSet;
import java.util.Set;
import java.util.TreeSet;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import javafx.stage.FileChooser;
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
        menuitem1.setOnAction(e -> {
            primaryStage.setTitle(mainTitle + " - Rail fence");
            if (!main.getChildren().isEmpty()) {
                main.getChildren().clear();
            }
            // tworzenie layoutu do zadania
            LayoutRailFence();
        });
        MenuItem menuitem2 = new MenuItem("Przestawianie macierzowe");
        // akcja po wybraniu elementu 2 menu
        menuitem2.setOnAction(e -> {
            primaryStage.setTitle(mainTitle + " - Przestawianie macierzowe");
            // czyszczenie okna
            if (!main.getChildren().isEmpty()) {
                main.getChildren().clear();
            }
            // tworzenie layoutu do zadania
            LayoutPrzestawianieMacierzowe();
        });
        MenuItem menuitem3 = new MenuItem("Szyfrowanie Cezara");
        // akcja po wybraniu elementu 3 menu
        menuitem3.setOnAction(e -> {
            primaryStage.setTitle(mainTitle + " - Szyfrowanie Cezara");
            if (!main.getChildren().isEmpty()) {
                main.getChildren().clear();
            }
            // tworzenie layoutu do zadania
            LayoutSzyfrCezara();
        });
        MenuItem menuitem4 = new MenuItem("Szyfrowanie Vigenere'a");
        // akcja po wybraniu elementu 4 menu
        menuitem4.setOnAction(e -> {
            primaryStage.setTitle(mainTitle + " - Szyfrowanie Vigenere'a");
            if (!main.getChildren().isEmpty()) {
                main.getChildren().clear();
            }
            // tworzenie layoutu do zadania
            LayoutSzyfrowanieVigenere();
        });
        MenuItem menuitem5 = new MenuItem("Szyfrowanie strumieniowe");
        // akcja po wybraniu elementu 5 menu
        menuitem5.setOnAction(e -> {
            primaryStage.setTitle(mainTitle + " - Szyfrowanie strumieniowe");
            if (!main.getChildren().isEmpty()) {
                main.getChildren().clear();
            }
            // tworzenie layoutu do zadania
            LayoutSzyfrowanieStrumieniowe();
        });

        menu1.getItems().add(menuitem1);
        menu1.getItems().add(menuitem2);
        menu1.getItems().add(menuitem3);
        menu1.getItems().add(menuitem4);
        menu2.getItems().add(menuitem5);
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

    public void LayoutRailFence() {
        Label plainTextLabel = new Label("Wprowadź tekst do zaszyfrowania/odszyfrowania:");
        Label keyLabel = new Label("Podaj liczbę poziomów n:");
        Label cipherTextLabel = new Label("Tekst w postaci zaszyfrowanej/odszyfrowanej:");
        Label cipherText = new Label(" - pusty - ");
        TextField plainTextField = new TextField();
        plainTextField.setPadding(new Insets(10, 10, 10, 10));
        TextField keyTextField = new TextField("3");
        keyTextField.setPadding(new Insets(10, 10, 10, 10));
        keyTextField.prefWidth(50);
        HBox keyBox = new HBox();
        keyBox.setSpacing(10);
        keyBox.setAlignment(Pos.CENTER_LEFT);
        keyBox.getChildren().add(keyLabel);
        keyBox.getChildren().add(keyTextField);
        Button doCipher = new Button("Zaszyfruj");
        doCipher.setOnAction(c -> {
            String plainText = plainTextField.getText();
            plainText = plainText.replaceAll("\\s", "");
            int key = Integer.parseInt(keyTextField.getText());
            String cipher = RailFence(plainText, key);
            cipherText.setText(cipher);
        });
        Button undoCipher = new Button("Odszyfruj");
        undoCipher.setOnAction(c -> {
            String plainText = plainTextField.getText();
            plainText = plainText.replaceAll("\\s", "");
            int key = Integer.parseInt(keyTextField.getText());
            String cipher = unRailFence(plainText, key);
            cipherText.setText(cipher);
        });
        Button copy = new Button("Skopiuj wynik");
        copy.setOnAction(k -> {
            plainTextField.setText(cipherText.getText());
        });
        keyBox.getChildren().add(doCipher);
        keyBox.getChildren().add(undoCipher);

        main.setOrientation(Orientation.VERTICAL);
        main.setVgap(10);
        main.getChildren().add(plainTextLabel);
        main.getChildren().add(plainTextField);
        main.getChildren().add(keyBox);
        main.getChildren().add(cipherTextLabel);
        main.getChildren().add(cipherText);
        main.getChildren().add(copy);
    }

    public String RailFence(String plainTextToCipher, int key) {
        @SuppressWarnings("UnusedAssignment")
        char[] plainText = new char[plainTextToCipher.length()];
        plainText = plainTextToCipher.toCharArray();
        char[][] plainTable = new char[key][plainText.length];
        boolean goDown = false;
        for (int i = 0, j = 0; j < plainText.length; j++) {
            if (i == 0 || i == key - 1) {
                goDown = !goDown;
            }
            plainTable[i][j] = plainText[j];
            if (goDown) {
                i++;
            } else {
                i--;
            }
        }

        StringBuilder cipher = new StringBuilder();
        for (int i = 0; i < key; i++) {
            for (int j = 0; j < plainText.length; j++) {
                if (plainTable[i][j] != 0) {
                    cipher.append(plainTable[i][j]);
                }
            }
        }
        return cipher.toString();
    }

    public String unRailFence(String plainTextToCipher, int key) {
        char[] plainText = new char[plainTextToCipher.length()];
        plainText = plainTextToCipher.toCharArray();
        char[][] plainTable = new char[key][plainText.length];
        boolean goDown = false;
        for (int i = 0, j = 0; j < plainText.length; j++) {
            if (i == 0 || i == key - 1) {
                goDown = !goDown;
            }
            plainTable[i][j] = 32;
            if (goDown) {
                i++;
            } else {
                i--;
            }
        }

        for (int i = 0, k = 0; i < key; i++) {
            for (int j = 0; j < plainText.length; j++) {
                if (plainTable[i][j] == 32) {
                    plainTable[i][j] = plainText[k++];
                }
            }
        }

        StringBuilder cipher = new StringBuilder();
        goDown = false;
        for (int i = 0, j = 0; j < plainText.length; j++) {
            if (i == 0 || i == key - 1) {
                goDown = !goDown;
            }
            cipher.append(plainTable[i][j]);
            if (goDown) {
                i++;
            } else {
                i--;
            }
        }
        return cipher.toString();
    }

    public void LayoutPrzestawianieMacierzowe() {
        int[] key = new int[5];
        Label plainTextLabel = new Label("Wprowadź tekst do zaszyfrowania / rozszyfrowania:");
        Label keyLabel = new Label("Podaj klucz do zaszyfrowania / rozszyfrowania (dla d = 5)");
        Label cipherLabel = new Label("Tekst zaszyfrowany / odszyfrowany:");
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
        Button deCipher = new Button("Rozszyfruj");
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
        deCipher.setOnAction(c -> {
            key[0] = Integer.parseInt(key1.getText());
            key[1] = Integer.parseInt(key2.getText());
            key[2] = Integer.parseInt(key3.getText());
            key[3] = Integer.parseInt(key4.getText());
            key[4] = Integer.parseInt(key5.getText());
            String deCipherText = plainTextField.getText();
            deCipherText = deCipherText.replaceAll("\\s", "");
            String plain = unPrzestawianieMacierzowe(deCipherText, key);
            cipherText.setText(plain);
        });
        Button copy = new Button("Skopiuj wynik");
        copy.setOnAction(k -> {
            plainTextField.setText(cipherText.getText());
        });
        main.setOrientation(Orientation.VERTICAL);
        main.setVgap(10);
        main.getChildren().add(plainTextLabel);
        main.getChildren().add(plainTextField);
        main.getChildren().add(keyLabel);
        main.getChildren().add(keyBox);
        main.getChildren().add(doCipher);
        main.getChildren().add(deCipher);
        main.getChildren().add(cipherLabel);
        main.getChildren().add(cipherText);
        main.getChildren().add(copy);
    }

    public String PrzestawianieMacierzowe(String plainTextToCipher, int[] key) {
        char[] plainText = new char[plainTextToCipher.length()];
        plainText = plainTextToCipher.toCharArray();
        double range = Math.floor(plainText.length / 5) + 1;
        char[][] plainTable = new char[5][(int) range];
        for (int y = 0; y < range; y++) {
            for (int x = 0; x < 5; x++) {
                if (!((y * 5) + (x) >= plainText.length)) {
                    plainTable[x][y] = plainText[(y * 5) + (x)];
                }
            }
        }
        StringBuilder cipher = new StringBuilder();
        for (int y = 0; y < range; y++) {
            for (int x = 0; x < 5; x++) {
                cipher.append(plainTable[key[x] - 1][y]);

            }
        }
        return cipher.toString();
    }

    public String unPrzestawianieMacierzowe(String cipherTextToDecipher, int[] key) {
        char[] cipherText = new char[cipherTextToDecipher.length()];
        cipherText = cipherTextToDecipher.toCharArray();
        double range = Math.floor(cipherText.length / 5) + 1;
        char[][] cipherTable = new char[5][(int) range];
        for (int y = 0; y < range; y++) {
            for (int x = 0; x < 5; x++) {
                if (!((y * 5) + (x) >= cipherText.length)) {
                    cipherTable[(key[x] - 1)][y] = cipherText[(y * 5) + x];
                }
            }
        }
        StringBuilder plainText = new StringBuilder();
        for (int y = 0; y < range; y++) {
            for (int x = 0; x < 5; x++) {
                plainText.append(cipherTable[x][y]);
            }
        }
        return plainText.toString();
    }

    public void LayoutSzyfrCezara() {
        Label plainTextLabel = new Label("Wprowadź tekst w do zaszyfrowania / odszyfrowania:");
        Label keyLabel = new Label("Podaj klucz do szyfrowania / odszyfrowania (0-26):");
        Label cipherTextLabel = new Label("Tekst zaszyfrowany / odszyfrowany:");
        Label cipherText = new Label(" - pusty - ");
        TextField plainTextField = new TextField();
        plainTextField.setPadding(new Insets(10, 10, 10, 10));
        TextField keyTextField = new TextField("10");
        keyTextField.setPadding(new Insets(10, 10, 10, 10));
        keyTextField.prefWidth(50);
        HBox keyBox = new HBox();
        keyBox.setSpacing(10);
        keyBox.setAlignment(Pos.CENTER_LEFT);
        keyBox.getChildren().add(keyLabel);
        keyBox.getChildren().add(keyTextField);
        Button doCipher = new Button("Zaszyfruj");
        Button deCipher = new Button("Odszyfruj");
        doCipher.setOnAction(c -> {
            String plainText = plainTextField.getText();
            plainText = plainText.replaceAll("\\s", "");
            int key = Integer.parseInt(keyTextField.getText());
            String cipher = SzyfrCezara(plainText, key);
            cipherText.setText(cipher);
        });
        deCipher.setOnAction(c -> {
            String deCipherText = plainTextField.getText();
            deCipherText = deCipherText.replaceAll("\\s", "");
            int key = Integer.parseInt(keyTextField.getText());
            String cipher = unSzyfrCezara(deCipherText, key);
            cipherText.setText(cipher);
        });
        Button copy = new Button("Skopiuj wynik");
        copy.setOnAction(k -> {
            plainTextField.setText(cipherText.getText());
        });
        main.setOrientation(Orientation.VERTICAL);
        main.setVgap(10);
        main.getChildren().add(plainTextLabel);
        main.getChildren().add(plainTextField);
        main.getChildren().add(keyBox);
        main.getChildren().add(doCipher);
        main.getChildren().add(deCipher);
        main.getChildren().add(cipherTextLabel);
        main.getChildren().add(cipherText);
        main.getChildren().add(copy);
    }

    public String SzyfrCezara(String plainTextToCipher, int key) {
        char[] plainText = new char[plainTextToCipher.length()];
        plainText = plainTextToCipher.toCharArray();
        StringBuilder cipher = new StringBuilder();
        for (int i = 0; i < plainText.length; i++) {
            if (Character.isUpperCase(plainText[i])) {
                char znak = (char) (((int) plainText[i] + key - 65) % 26 + 65);
                cipher.append(znak);
            } else {
                char znak = (char) (((int) plainText[i] + key - 97) % 26 + 97);
                cipher.append(znak);
            }
        }
        return cipher.toString();
    }

    public String unSzyfrCezara(String cipherTextToDeCipher, int key) {
        char[] cipherText = new char[cipherTextToDeCipher.length()];
        cipherText = cipherTextToDeCipher.toCharArray();
        StringBuilder plain = new StringBuilder();
        key = 26 - key;
        for (int i = 0; i < cipherText.length; i++) {
            if (Character.isUpperCase(cipherText[i])) {
                char znak = (char) (((int) cipherText[i] + key - 65) % 26 + 65);
                plain.append(znak);
            } else {
                char znak = (char) (((int) cipherText[i] + key - 97) % 26 + 97);
                plain.append(znak);
            }
        }
        return plain.toString();
    }

    public void LayoutSzyfrowanieVigenere() {
        Label plainTextLabel = new Label("Wprowadź tekst do zaszyfrowania/odszyfrowania:");
        Label keyLabel = new Label("Podaj klucz do szyfrowania/odszyfrowania:");
        Label cipherTextLabel = new Label("Tekst w postaci zaszyfrowanej/odszyfrowanej:");
        Label cipherText = new Label(" - pusty - ");
        TextField plainTextField = new TextField();
        plainTextField.setPadding(new Insets(10, 10, 10, 10));
        TextField keyTextField = new TextField();
        keyTextField.setPadding(new Insets(10, 10, 10, 10));
        keyTextField.prefWidth(50);
        Button doCipher = new Button("Zaszyfruj");
        doCipher.setOnAction(c -> {
            String plainText = plainTextField.getText();
            plainText = plainText.replaceAll("\\s", "");
            String key = keyTextField.getText();
            String cipher = SzyfrowanieVigenere(plainText, key);
            cipherText.setText(cipher);
        });
        Button undoCipher = new Button("Odszyfruj");
        undoCipher.setOnAction(c -> {
            String plainText = plainTextField.getText();
            plainText = plainText.replaceAll("\\s", "");
            String key = keyTextField.getText();
            String cipher = OdszyfrowanieVigenere(plainText, key);
            cipherText.setText(cipher);
        });
        Button copy = new Button("Skopiuj wynik");
        copy.setOnAction(k -> {
            plainTextField.setText(cipherText.getText());
        });
        HBox keyBox = new HBox();
        keyBox.setSpacing(10);
        keyBox.setAlignment(Pos.CENTER_LEFT);
        keyBox.getChildren().add(doCipher);
        keyBox.getChildren().add(undoCipher);

        main.setOrientation(Orientation.VERTICAL);
        main.setVgap(10);
        main.getChildren().add(plainTextLabel);
        main.getChildren().add(plainTextField);
        main.getChildren().add(keyLabel);
        main.getChildren().add(keyTextField);
        main.getChildren().add(keyBox);
        main.getChildren().add(cipherTextLabel);
        main.getChildren().add(cipherText);
        main.getChildren().add(copy);
    }

    public String SzyfrowanieVigenere(String plainTextToCipher, String key) {
        char[] plainText = new char[plainTextToCipher.length()];
        plainText = plainTextToCipher.toCharArray();
        StringBuilder cipher = new StringBuilder();
        for (int i = 0, j = 0; i < plainText.length; i++) {
            if (Character.isUpperCase(plainText[i])) {
                char znak = (char) (((int) plainText[i] + Character.toUpperCase(key.charAt(j)) - 2 * 'A') % 26 + 'A');
                cipher.append(znak);
            } else {
                char znak = (char) (((int) plainText[i] + Character.toLowerCase(key.charAt(j)) - 2 * 'a') % 26 + 'a');
                cipher.append(znak);
            }
            j = ++j % key.length();
        }
        return cipher.toString();

    }

    public String OdszyfrowanieVigenere(String plainTextToCipher, String key) {
        char[] plainText = new char[plainTextToCipher.length()];
        plainText = plainTextToCipher.toCharArray();
        StringBuilder cipher = new StringBuilder();
        for (int i = 0, j = 0; i < plainText.length; i++) {
            if (Character.isUpperCase(plainText[i])) {
                char znak = (char) (((int) plainText[i] - Character.toUpperCase(key.charAt(j)) + 26) % 26 + 'A');
                cipher.append(znak);
            } else {
                char znak = (char) (((int) plainText[i] - Character.toLowerCase(key.charAt(j)) + 26) % 26 + 'a');
                cipher.append(znak);
            }
            j = ++j % key.length();
        }
        return cipher.toString();

    }

    public void LayoutSzyfrowanieStrumieniowe() {
        Label plainTextLabel = new Label("Podaj potęgi wielomianu 1+x+x^2+...+x^n");
        Label keyLabel = new Label("Podaj ziarno (binarnie):");
        Label cipherText = new Label("Plik do zaszyfrowania:");
        Label pathText = new Label("");
        Label polynomialText = new Label("");
        Label seedText = new Label("");
        Label resultText = new Label("");
        Label fileNameText = new Label("");
        TextField plainTextField = new TextField();
        plainTextField.setPadding(new Insets(10, 10, 10, 10));
        TextField keyTextField = new TextField();
        keyTextField.setPadding(new Insets(10, 10, 10, 10));
        keyTextField.prefWidth(50);

        plainTextField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            plainTextField.setText(newValue.replaceAll("[^\\d^\\s]", ""));      //usuwanie znaków z textFielda nie będących cyfrą lub spacją
        });
        keyTextField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            keyTextField.setText(newValue.replaceAll("[^0-1]", ""));            //usuwanie znaków z textFielda nie będących 0 lub 1
        });

        Button openButton = new Button("Wybierz plik");
        final FileChooser fileChooser = new FileChooser();
        openButton.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                Node node = (Node) e.getSource();
                final Stage stage = (Stage) node.getScene().getWindow();
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {                                             //jeżeli wybrano jakiś plik usuń teksty z labelów
                    pathText.setText(file.getPath());
                    resultText.setText("");
                    seedText.setText("");
                    fileNameText.setText("");
                    polynomialText.setText("");
                }
            }
        });
        Button doCipher = new Button("Szyfruj");

        doCipher.setOnAction(c -> {
            if (keyTextField.getText().equals("") || plainTextField.getText().equals("") || pathText.getText().equals("")) {    //jeżeli nie podano wszystkich danych wypisz komunikat
                resultText.setText("Uzupełnij dane!");
            } else {
                String str = plainTextField.getText().replaceFirst("^ *", "");          //usuń pierwsze wystąpienia spacji
                String[] stringOfNumbers = str.split("\\s{1,}");                        //utwórz tablicę numerów podzielonych conajmniej jedną spacją
                StringBuilder sb = new StringBuilder();                                 //potrzebny do wypisania wielomianu
                TreeSet<Integer> polynomials = new TreeSet<Integer>();                  //TreeSet do pozbycia się powtórzeń i posortowania potęg wielomianu
                try {
                    for (String s : stringOfNumbers) {                                  
                        polynomials.add(Integer.parseInt(s));                           //zamień stringi na inty
                    }
                } catch (NumberFormatException e) {
                    resultText.setText("Za duża potęga wielomianu!");                   //jeżeli przekroczono zakres inta wyświetl komunikat
                    return;
                }
                polynomials.remove(0);                                                  //usuń ewentualną 0 potęgę
                sb.append("Wielomian: 1");                                              //budowa stringa do wyświetlenia wielomianu
                for (int i : polynomials) {
                    sb.append("+x^" + i);
                }
                if (SzyfrowanieStrumieniowe(pathText.getText(), polynomials, Integer.parseInt(keyTextField.getText())) == 0) { //jeżeli zwrócono 0 operacja szyfrowania powiodła się
                    resultText.setText("Szyfrowanie zakończone");                       //wypisanie komunikatów
                    polynomialText.setText(sb.toString());
                    seedText.setText("Ziarno: " + keyTextField.getText());
                    Path path = Paths.get(pathText.getText());
                    fileNameText.setText("Plik wynikowy: " + path.getParent() + "/tajnyplik" + getFileExtension(path));
                } else {
                    resultText.setText("Szyfrowanie zakończone niepowodzeniem");        //operacja szyfrowania nie powiodła się
                }
            }
        });
        HBox keyBox = new HBox();
        keyBox.setSpacing(10);
        keyBox.setAlignment(Pos.CENTER_LEFT);
        keyBox.getChildren().add(openButton);
        keyBox.getChildren().add(doCipher);

        main.setOrientation(Orientation.VERTICAL);
        main.setVgap(10);
        main.getChildren().add(plainTextLabel);
        main.getChildren().add(plainTextField);
        main.getChildren().add(keyLabel);
        main.getChildren().add(keyTextField);
        main.getChildren().add(keyBox);
        main.getChildren().add(resultText);
        main.getChildren().add(cipherText);
        main.getChildren().add(pathText);
        main.getChildren().add(polynomialText);
        main.getChildren().add(seedText);
        main.getChildren().add(fileNameText);

    }

    public int SzyfrowanieStrumieniowe(String stringPath, TreeSet<Integer> polynomial, int seed) {
        Path path = Paths.get(stringPath);                              //budowa ścieżki do pliku
        try {
            byte byteArray[] = Files.readAllBytes(path);                //wczytanie pliku jako tablicy bajtów
            BitSet polynomialBitSet = new BitSet();
            BitSet seedBitSet = convert(seed);                          //konwersja inta na BitSet

            for (int i : polynomial) {                                  //konwersja wielomianu na BitSet
                polynomialBitSet.set(polynomial.last() - i);            //odjęcie od wartości maksymalnej wartości liczby tak aby najwyższa potęga była bitem na pozycji 0
            }
            LFSR lfsr = new LFSR(seedBitSet, polynomialBitSet);         //inicjalizacja algorytmu LFSR

            for (int i = 0; i < byteArray.length; i++) {                //dla każdego bajtu z wczytanego pliku
                byteArray[i] ^= lfsr.getNextByte();                     //XORuj z kolejnym wygenerowanym bajtem przez algorytm
            }
            FileOutputStream fos = new FileOutputStream(path.getParent() + "/tajnyplik" + getFileExtension(path));  //ścieżka do pliku oryginalnego i zmiana nazwy pliku na tajnyplik + rozszerzenie oryginału
            fos.write(byteArray);                                       //zapisanie strumienia bajtów

        } catch (IOException ex) {
            return -1;
        }
        return 0;
    }

    public static BitSet convert(int value) {                           //konwersja inta złożonego z 0 i 1 do BitSetu
        BitSet bits = new BitSet();
        int index = 0;
        while (value != 0) {
            if (value % 10 != 0) {                                      
                bits.set(index);
            }
            ++index;
            value /= 10;
        }
        return bits;
    }

    private String getFileExtension(Path path) {                        //zwrócenie rosrzerzenia pliku
        String name = path.getFileName().toString();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return name.substring(lastIndexOf);
    }

}
