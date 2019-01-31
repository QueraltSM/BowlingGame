package View;

import Model.Game;
import Model.Serialization;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DisplayGUI extends Application {

    private static final TableColumn name = new TableColumn("");
    private static final TableColumn frame1 = new TableColumn("1");
    private static final TableColumn frame2 = new TableColumn("2");
    private static final TableColumn frame3 = new TableColumn("3");
    private static final TableColumn frame4 = new TableColumn("4");
    private static final TableColumn frame5 = new TableColumn("5");
    private static final TableColumn frame6 = new TableColumn("6");
    private static final TableColumn frame7 = new TableColumn("7");
    private static final TableColumn frame8 = new TableColumn("8");
    private static final TableColumn frame9 = new TableColumn("9");
    private static final TableColumn frame10 = new TableColumn("10");
    private static final TableColumn totalScore = new TableColumn("Total");
    private static int lane;
    private static Text text;
    private static TableView<Map> table;
    private static VBox vbox;
    private Game game;

    private final String tableStyle = "-fx-background-color: black;-fx-font-weight: bold;" +
            "-fx-font-size: 15px; -fx-font-family: \"Times New Roman\";";
    private static final String textStyle = "-fx-font-weight: bold;-fx-stroke: black;-fx-stroke-width: 2;" +
            "-fx-font-size: 50px;-fx-font-family: \"Times New Roman\"";
    private static final String imagePath = "src\\main\\java\\Images\\Bowling.jpg";


    @Override
    public void start(Stage stage) throws IOException {
        game = Serialization.getDisplay(lane);
        saveDataLogfile(new StringBuilder("Showing display of lane " + lane));
        stage.setTitle("Planet Bowling | Lane " + game.getLane());
        stage.setMinWidth(900);
        stage.setMinHeight(500);
        stage.setMaxHeight(500);
        stage.setMaxWidth(900);

        Image image = new Image(new FileInputStream(imagePath), 900, 900,
                true, true);
        ImageView imageView = new ImageView(image);
        Group root = new Group(imageView);
        Scene scene = new Scene(root, 1050, 500);

        text = new Text(game.getTeamName());
        text.setStyle(textStyle);
        text.setFill(Color.web("00AEFF"));

        setCellValueFactoryAll();
        setCellFactory();
        setTable();
        setVBox();

        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        stage.setScene(scene);
        stage.show();
    }


    private void setCellValueFactory (TableColumn column, String key) {
        column.setCellValueFactory(new MapValueFactory(key));
        column.setMinWidth(60);
        column.setStyle(!key.equals("Total") ? "-fx-alignment: CENTER;-fx-border-color: black;" +
                "-fx-background-color:#CBFFB3;" : "-fx-alignment: CENTER;-fx-border-color: black;" +
                "-fx-background-color:#FCFF96;");
    }

    private void setCellValueFactoryAll(){
        setCellValueFactory(name, "");
        setCellValueFactory(frame1, "1");
        setCellValueFactory(frame2, "2");
        setCellValueFactory(frame3, "3");
        setCellValueFactory(frame4, "4");
        setCellValueFactory(frame5, "5");
        setCellValueFactory(frame6, "6");
        setCellValueFactory(frame7, "7");
        setCellValueFactory(frame8, "8");
        setCellValueFactory(frame9, "9");
        setCellValueFactory(frame10, "10");
        setCellValueFactory(totalScore, "Total");
    }

    private void setCellFactory() {
        Callback<TableColumn, TableCell>
                cellFactoryForMap = p -> new TextFieldTableCell(new StringConverter() {
            @Override
            public String toString(Object t) {
                return t.toString();
            }

            @Override
            public Object fromString(String string) {
                return string;
            }
        });
        name.setCellFactory(cellFactoryForMap);
        frame1.setCellFactory(cellFactoryForMap);
        frame2.setCellFactory(cellFactoryForMap);
        frame3.setCellFactory(cellFactoryForMap);
        frame4.setCellFactory(cellFactoryForMap);
        frame5.setCellFactory(cellFactoryForMap);
        frame6.setCellFactory(cellFactoryForMap);
        frame7.setCellFactory(cellFactoryForMap);
        frame8.setCellFactory(cellFactoryForMap);
        frame9.setCellFactory(cellFactoryForMap);
        frame10.setCellFactory(cellFactoryForMap);
        totalScore.setCellFactory(cellFactoryForMap);
    }


    private ObservableList<Map> generateDataInMap(Game game) {
        ObservableList<Map> allData = FXCollections.observableArrayList();
        game.getPlayers().forEach(player -> {
            Map<String, String> dataRow = new HashMap<>();
            dataRow.put("", player.getName());
            dataRow.put("1", player.getDisplay().get(0));
            dataRow.put("2", player.getDisplay().get(1));
            dataRow.put("3", player.getDisplay().get(2));
            dataRow.put("4", player.getDisplay().get(3));
            dataRow.put("5", player.getDisplay().get(4));
            dataRow.put("6", player.getDisplay().get(5));
            dataRow.put("7", player.getDisplay().get(6));
            dataRow.put("8", player.getDisplay().get(7));
            dataRow.put("9", player.getDisplay().get(8));
            dataRow.put("10", player.getDisplay().get(9));
            dataRow.put("Total", player.getTotalScore() + "");
            allData.add(dataRow);
        });
        return allData;
    }

    private void setTable(){
        table = new TableView<>(generateDataInMap(game));
        table.setEditable(false);
        table.setMaxWidth(722);
        table.setMaxHeight(188);
        table.setMinWidth(722);
        table.setMinHeight(188);
        table.getSelectionModel().setCellSelectionEnabled(true);
        table.getColumns().setAll(name, frame1, frame2, frame3, frame4,
                frame5,frame6,frame7,frame8,frame9,frame10,totalScore);
        table.setStyle(tableStyle);
    }

    private void setVBox(){
        vbox = new VBox();
        vbox.setPadding(new Insets(0, 50, 80, 50));
        vbox.getChildren().addAll(text, table);
        vbox.setAlignment(Pos.CENTER);
        vbox.setMinSize(900,500);
        vbox.setMaxSize(900,500);
        vbox.setSpacing(30);
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
        lane = Integer.parseInt(args[0]);
        launch(args);
    }
}