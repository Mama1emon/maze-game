package maze.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import maze.Main;

public class finishSceneController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button GoBackButton;

    @FXML
    public void GoBackButtonClicked(){
        Main.screenController.activate("menu");
    }
}