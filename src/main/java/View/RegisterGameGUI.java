package View;

import Model.Game;
import Model.Player;
import Model.Serialization;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegisterGameGUI extends Application {
    private final List<Player> data;
    private final Game game = new Game();
    private final TextField nameTxf = new TextField();
    private final TextField laneTxf = new TextField();
    private static VBox vbox;
    private static Text text;
    private static HBox hbButtons;
    private static HBox hbTextfields;
    private static Button registerGame;
    private static Button cancelGame;
    private final String txfStyle = "-fx-background-color: transparent;" +
            "-fx-border-color: transparent transparent black transparent;" +
            "-fx-border-width: 3 3 3 3;-fx-font-size: 20px;-fx-font-weight: bold;" +
            "-fx-font-family: \"Times New Roman\";-fx-prompt-text-fill: black;" +
            "-fx-text-fill: black;";
    private static final String createGameStyle = "-fx-border-color:black;-fx-border-width: 2 2 2 2;" +
            "-fx-background-color: #8DD2FF;-fx-pref-height: 20px;-fx-pref-width: 140px;" +
            "-fx-font-size: 18px;-fx-font-weight: bold;-fx-font-family: \"Times New Roman\";" +
            "-fx-text-fill: black;";
    private static final String cancelGameStyle = "-fx-border-color:black;-fx-border-width: 2 2 2 2;" +
            "-fx-background-color: #8DD2FF;-fx-pref-height: 20px;-fx-pref-width: 140px;" +
            "-fx-font-size: 18px;-fx-font-weight: bold;-fx-font-family: \"Times New Roman\";" +
            "-fx-text-fill: black;";
    private static final String textStyle = "-fx-font-weight: bold;-fx-stroke: black;-fx-stroke-width: 2;" +
            "-fx-font-size: 50px;-fx-font-family: \"Times New Roman\"";
    private static final String imagePath = "src\\main\\java\\Images\\Bowling.jpg";
    private static Stage stage;

    public RegisterGameGUI(ObservableList<Player> data) {
        this.data = data;
    }

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        RegisterGameGUI.stage = stage;
        stage.setTitle("Planet Bowling");
        stage.setMinWidth(600);
        stage.setMinHeight(650);
        stage.setMaxHeight(650);
        stage.setMaxWidth(600);
        Image image = new Image(new FileInputStream(imagePath), 900, 900,
                true, true);
        ImageView imageView = new ImageView(image);
        Group root = new Group(imageView);
        Scene scene = new Scene(root, 600, 600);
        text = new Text("Game Registration");
        text.setStyle(textStyle);
        text.setFill(Color.web("00AEFF"));

        setTxts();
        setCreateGame();
        setCancelGame();
        setHb();
        setVBox();
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        stage.setScene(scene);
        stage.show();
    }

    private void setHb(){
        hbButtons = new HBox();
        hbButtons.getChildren().addAll(registerGame, cancelGame);
        hbButtons.setSpacing(30);
        hbButtons.setAlignment(Pos.CENTER);

        hbTextfields = new HBox();
        hbTextfields.getChildren().addAll(  nameTxf,laneTxf);
        hbTextfields.setSpacing(30);
        hbTextfields.setAlignment(Pos.CENTER);
    }

    private static void alert(String title, String message, String contentText){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.setContentText(contentText);
        alert.show();
    }

    private void setCreateGame(){
        registerGame = new Button("Register game");
        registerGame.setStyle(createGameStyle);
        registerGame.setOnAction(e -> {
            if (!nameTxf.getText().equals("") && !laneTxf.getText().equals("") &&
                    Integer.parseInt(laneTxf.getText())<=10 && Integer.parseInt(laneTxf.getText())>=1) {
                try {
                    setNewGame();
                    StringBuilder message = new StringBuilder("Lane " + game.getLane() + " has been activated\n" +
                            "Team name: " + game.getTeamName());
                    int count = 1;
                    for (Player player : game.getPlayers()) {
                        message.append("\nPlayer ").append(count).append(": ").append(player.getName());
                        count++;
                    }
                    saveDataLogfile(message);
                } catch (TransformerException | ParserConfigurationException | IOException e1) {
                    e1.printStackTrace();
                }
                stage.close();
                View.RegisterUsersGUI.getTable().getItems().clear();
            } else alert("Team not well formed", "Error in the game registration",
                    "Write a team name and a valid lane number (1-10)");
            nameTxf.clear();
            laneTxf.clear();
        });
    }

    private void saveDataLogfile(StringBuilder message){
        try(FileWriter fw = new FileWriter("src\\main\\java\\Data\\Log", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)) {
            out.println(message);
            out.println("- - - - - - - - - - - - - - - - - - - -");
        } catch (IOException ignored) {}
    }

    private void setCancelGame(){
        cancelGame = new Button("Cancel");
        cancelGame.setStyle(cancelGameStyle);
        cancelGame.setOnAction(e -> {
            saveDataLogfile(new StringBuilder("Game has been canceled"));
            stage.close();
        });
    }

    private void setNewGame() throws TransformerException, ParserConfigurationException, IOException {
        game.setLane(Integer.parseInt(laneTxf.getText()));
        game.setTeamName(nameTxf.getText());
        game.setDatetime(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        game.setPlayers( new ArrayList<>(data));
        Serialization.saveGame(game);
        alert("Game was saved", "Lane " + game.getLane() + " was activated",
                "There were saved " + game.getPlayers().size() + " players.");
    }

    private void setTxts(){
        nameTxf.setPromptText("Team name");
        nameTxf.setMinWidth(220);
        nameTxf.setMaxWidth(100);
        nameTxf.setStyle(txfStyle);
        nameTxf.setAlignment(Pos.CENTER);
        laneTxf.setPromptText("Lane number");
        laneTxf.setMinWidth(220);
        laneTxf.setMaxWidth(100);
        laneTxf.setStyle(txfStyle);
        laneTxf.setAlignment(Pos.CENTER);
    }

    private void setVBox(){
        vbox = new VBox();
        vbox.setPadding(new Insets(20, 50, 90, 30));
        vbox.getChildren().addAll(text,hbTextfields,hbButtons);
        vbox.setAlignment(Pos.CENTER);
        vbox.setMinSize(600,600);
        vbox.setMaxSize(600,600);
        vbox.setSpacing(60);
    }
}