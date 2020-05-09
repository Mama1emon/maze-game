package maze.Controllers;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.util.Duration;
import maze.Main;

public class startSceneController {
    @FXML
    private Button runButton;

    @FXML
    public void runButtonClicked(){
        Main.isStart = false; //активировать экран Меню
        Main.isMenu = true;
    }

    //анимация при наведении курсором на кнопку
    @FXML
    public void runButtonMouseEntered(){
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0.0);

        runButton.setEffect(colorAdjust);
        //при наведении мыши созвать анимацию
        runButton.setOnMouseEntered(e -> {
            Timeline fadeInTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(0),
                            new KeyValue(colorAdjust.brightnessProperty(), colorAdjust.brightnessProperty().getValue(), Interpolator.LINEAR)),
                    new KeyFrame(Duration.seconds(1), new KeyValue(colorAdjust.brightnessProperty(), -1, Interpolator.LINEAR)
                    ));
            fadeInTimeline.setCycleCount(2);
            fadeInTimeline.setAutoReverse(true);
            fadeInTimeline.play();
        });
    }
}
