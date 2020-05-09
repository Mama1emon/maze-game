package maze.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import maze.Main;

public class finishSceneController {
    @FXML
    private Button GoBackButton;

    @FXML
    public void GoBackButtonClicked(){
        Main.isFinish = false;
        Main.isMenu = true;
    }
}