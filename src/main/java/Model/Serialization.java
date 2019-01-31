package Model;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class Serialization {
    private static Document document;
    private static Element root;
    private static Document doc;

    private static void createDocument() throws ParserConfigurationException {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        document = documentBuilder.newDocument();
        root = document.createElement("Game");
        document.appendChild(root);
    }

    public static void saveGame(Game game) throws ParserConfigurationException, TransformerException, IOException {
        createDocument();
        saveDataPlayers(game.getPlayers());
        saveDataGame(game);
        setTransformer(game);
    }

    private static void setTransformer(Game game) throws TransformerException, IOException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource domSource = new DOMSource(document);
        BufferedWriter bw = new BufferedWriter(new FileWriter("src\\main\\java\\Data\\Display"+
                game.getLane() + ".xml"));
        StreamResult streamResult = new StreamResult(bw);
        transformer.transform(domSource, streamResult);
    }

    private static void saveDataPlayers(List<Player> players){
        players.forEach(player -> {
            Element playerNode = document.createElement("player");
            root.appendChild(playerNode);
            Attr nameAttr = document.createAttribute("player_name");
            nameAttr.setValue(player.getName());
            playerNode.setAttributeNode(nameAttr);
            Element frame1 = document.createElement("match");
            frame1.appendChild(document.createTextNode(player.getMatch()));
            playerNode.appendChild(frame1);
            Element total = document.createElement("totalscore");
            total.appendChild(document.createTextNode(player.getTotalScore() + ""));
            playerNode.appendChild(total);
        });
    }

    private static void saveDataGame(Game game) {
        Element gameNode = document.createElement("game");
        root.appendChild(gameNode);
        Attr laneAttr = document.createAttribute("lane");
        laneAttr.setValue(game.getLane()+"");
        gameNode.setAttributeNode(laneAttr);
        Element teamName = document.createElement("teamname");
        teamName.appendChild(document.createTextNode(game.getTeamName()));
        gameNode.appendChild(teamName);
        Element datetime = document.createElement("datetime");
        datetime.appendChild(document.createTextNode(game.getDatetime()));
        gameNode.appendChild(datetime);
    }

    public static Game getDisplay(int lane) {
        Game game = new Game();
        try {
            File inputFile = new File("src\\main\\java\\Data\\Display"+lane+ ".xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("game");
            saveGameInfo(nList, game);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return game;
    }

    private static void saveGameInfo(NodeList nList, Game game){
        IntStream.range(0, nList.getLength()).mapToObj(nList::item).filter(nNode ->
                nNode.getNodeType() == Node.ELEMENT_NODE).map(nNode -> (Element) nNode).forEach(eElement -> {
            game.setLane(Integer.parseInt(eElement.getAttribute("lane")));
            game.setDatetime(eElement.getElementsByTagName("datetime").item(0).getTextContent());
            game.setTeamName(eElement.getElementsByTagName("teamname").item(0).getTextContent());
            game.setPlayers(getPlayersList(doc));
        });
    }

    private static List<Player> getPlayersList(Document doc){
        List<Player> players = new ArrayList<>();
        NodeList playersList = doc.getElementsByTagName("player");
        IntStream.range(0, playersList.getLength()).forEach(temp -> {
            Node nNode = playersList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                Player player = new Player();
                player.setName(eElement.getAttribute("player_name"));
                player.setMatch(eElement.getElementsByTagName("match").item(0).getTextContent());
                players.add(player);
            }
        });
        return players;
    }

    public static void saveRoll(int lane, String playerName, int roll) throws TransformerException,
            ParserConfigurationException, IOException {
        Game game = getDisplay(lane);
        game.getPlayers().stream().filter(player -> playerName.equals(player.getName()))
                .forEach(player -> player.roll(roll));
        saveGame(game);
    }
}