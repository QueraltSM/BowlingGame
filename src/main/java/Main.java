import Model.Serialization;
import View.DisplayGUI;
import View.RegisterUsersGUI;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

class Main {
    public static void main(String[] args) throws TransformerException, ParserConfigurationException, IOException {
    if (args.length == 0) RegisterUsersGUI.main(args);
        else // To see a specific lane's display
            if (args.length == 1) DisplayGUI.main(args);
            else if (args.length == 3) { // To set a roll, arguments: { lane number, player name, roll }
            int lane = Integer.parseInt(args[0]);
            int roll = Integer.parseInt(args[2]);
            if (lane >= 11 || lane <1 || roll > 10) System.out.println((char)27 + "[31;40m\n------------------\n" +
                    "Error in arguments\n------------------\n");
            else Serialization.saveRoll(lane, args[1], roll);
        }
    }
}