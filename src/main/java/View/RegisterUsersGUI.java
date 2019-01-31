package View;

import Model.Player;
import Model.PlayerScore;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

public class RegisterUsersGUI extends Application {
    private static final ObservableList<Player> data = FXCollections.observableArrayList();
    private static TableView<Player> table;
    private static TableColumn name;
    private static VBox vbox;
    private static HBox hb;
    private static Button addPlayer;
    private static Button registerUsers;
    private static TextField nameTxf;
    private static Text text;
    private static HBox hbButtons;
    private static Button cancelPlayers;
    private static final String addPlayerStyle = "-fx-border-color:black;-fx-border-width: 2 2 2 2;" +
            "-fx-background-color: #8DD2FF;-fx-pref-height: 20px;-fx-font-family: \"Times New Roman\";" +
            "-fx-pref-width: 40px;-fx-font-size: 19px;-fx-font-weight: bold;-fx-text-fill: black;";
    private static final String registerUsersStyle = "-fx-border-color:black;-fx-border-width: 2 2 2 2;" +
            "-fx-background-color: #8DD2FF;-fx-pref-height: 20px;-fx-pref-width: 180px;" +
            "-fx-font-size: 18px;-fx-font-weight: bold;-fx-font-family: \"Times New Roman\";" +
            "-fx-text-fill: black;";
    private static final String cancelPlayersStyle = "-fx-border-color:black;-fx-border-width: 2 2 2 2;" +
            "-fx-background-color: #8DD2FF;-fx-pref-height: 20px;-fx-pref-width: 100px;" +
            "-fx-font-size: 18px;-fx-font-weight: bold;-fx-font-family: \"Times New Roman\";" +
            "-fx-text-fill: black;";
    private static final String nameTxfStyle = "-fx-background-color: transparent;" +
            "-fx-border-color: transparent transparent black transparent;" +
            "-fx-border-width: 3 3 3 3;-fx-font-size: 20px;-fx-font-weight: bold;" +
            "-fx-font-family: \"Times New Roman\";-fx-prompt-text-fill: black;" +
            "-fx-text-fill: black;";
    private static final String tableStyle = "-fx-background-color: black;-fx-font-weight: bold;" +
            "-fx-font-size: 17px; -fx-font-family: \"Times New Roman\";";
    private static final String textStyle = "-fx-font-weight: bold;-fx-stroke: black;-fx-stroke-width: 2;" +
            "-fx-font-size: 50px;-fx-font-family: \"Times New Roman\"";
    private static final String nameStyle = "-fx-alignment: CENTER;-fx-border-color: black;" +
            "-fx-background-color:#8DD2FF;-fx-border-width: 1 1 1 1;-fx-text-fill: black;";
    private static final String imagePath = "src\\main\\java\\Images\\Bowling.jpg";

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        table = new TableView<>();
        hb = new HBox();
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
        text = new Text("User Registration");
        text.setStyle(textStyle);
        text.setFill(Color.web("00AEFF"));
        setTable();
        setNameTxf();
        setAddPlayerButton();
        setRegisterPlayers();
        setCancelPlayers();
        setHb();
        setVBox();
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        stage.setScene(scene);
        stage.show();
    }

    private static void setHb(){
        hb.getChildren().addAll(nameTxf);
        hb.setSpacing(3);
        hb.setAlignment(Pos.CENTER);
        hbButtons = new HBox();
        hbButtons.getChildren().addAll(addPlayer, registerUsers, cancelPlayers);
        hbButtons.setSpacing(20);
        hbButtons.setAlignment(Pos.CENTER);
    }

    static TableView<Player> getTable(){
        return table;
    }

    private static void alert(String title, String message, String contentText){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.setContentText(contentText);
        alert.show();
    }

    private void setTable(){
        table.setEditable(true);
        table.setMaxWidth(302);
        table.setPlaceholder(new Label(""));
        table.setMaxHeight(205);
        table.setStyle(tableStyle);
        name = new TableColumn("Team players");
        name.setMinWidth(300);
        name.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
        name.setStyle(nameStyle);
        table.setItems(data);
        table.getColumns().addAll(name);
    }

    private void setNameTxf(){
        nameTxf = new TextField();
        nameTxf.setPromptText("Player name");
        nameTxf.setMinWidth(300);
        nameTxf.setMaxWidth(name.getPrefWidth());
        nameTxf.setStyle(nameTxfStyle);
        nameTxf.setAlignment(Pos.CENTER);
    }

    private void setAddPlayerButton(){
        addPlayer = new Button("+");
        addPlayer.setStyle(addPlayerStyle);
        addPlayer.setOnAction(e -> {
            if (nameTxf.getText().equals("")) alert("Player not saved", "Write a player name",
                    "Player name's textfield can not be empty");
            else if (data.size() <= 5) {
                setNewPlayer();
                nameTxf.clear();
            } else alert("Team formed", "No more players are allowed",
                    "Only 6 players per lane are allowed");
        });
    }

    private void setNewPlayer(){
        Player player = new Player();
        player.setName(nameTxf.getText());
        player.setMatch("");
        player.setPlayerScore(new PlayerScore());
        data.add(player);
        saveDataLogfile(new StringBuilder("Player " + data.size() + " has been added: " + player.getName()));
    }

    private void setRegisterPlayers(){
        registerUsers = new Button("Register players");
        registerUsers.setStyle(registerUsersStyle);
        registerUsers.setOnAction(e -> {
            if (data.size() >= 1) try {
                new RegisterGameGUI(data).start(new Stage());
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            else alert("Team not formed", "There are no players",
                    "Must be a minimum of 1 player per lane");
        });
    }

    private void setCancelPlayers(){
        cancelPlayers = new Button("Cancel");
        cancelPlayers.setStyle(cancelPlayersStyle);
        cancelPlayers.setOnAction(e -> {
            StringBuilder message = new StringBuilder("Players that have been removed from the system: \n");
            AtomicInteger count = new AtomicInteger(1);
            data.forEach(player -> message.append("Player ").append(count.getAndIncrement())
                    .append(": ").append(player.getName()).append("\n"));
            saveDataLogfile(message);
            data.removeAll();
            table.getItems().clear();
        });
    }

    private void setVBox(){
        vbox = new VBox();
        vbox.setPadding(new Insets(0, 50, 30, 30));
        vbox.getChildren().addAll(text, table, hb, hbButtons);
        vbox.setAlignment(Pos.CENTER);
        vbox.setMinSize(600,600);
        vbox.setMaxSize(600,600);
        vbox.setSpacing(40);
    }

    private void saveDataLogfile(StringBuilder message){
        try(FileWriter fw = new FileWriter("src\\main\\java\\Data\\Log", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)) {
            out.println(message);
            out.println("- - - - - - - - - - - - - - - - - - - -");
        } catch (IOException ignored) {}
    }

    public static void main(String[] args) {
        launch(args);
    }
}