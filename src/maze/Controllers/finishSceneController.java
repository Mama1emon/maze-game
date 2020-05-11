/*
 * Контроллер, управляющий финальной сценой
 */
package maze.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import maze.Main;

public class finishSceneController {
    @FXML
    private Button GoBackButton;

    //обработка при нажатии на кнопку
    @FXML
    public void GoBackButtonClicked(){
        //переход с финального экрана на экран меню
        Main.isFinish = false;
        Main.isMenu = true;
    }
}