package sample;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicReference;


public class Main extends Application {

    private final Image IMG = new Image(getClass().getResourceAsStream("sprite_man.png"));
    private  int COUNT = 10; // сколько спрайтов возьмём
    private  int COLUMN = 10; // сколько колонок
    private  int OFSETX = 0;
    private  int OFSETY = 90; // откуда начинать
    private  int WIDTH = 90; // размеры
    private  int HEIGHT = 90;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Спрайт"); // заголовок формы

        // передаём нашу картинку
        ImageView imageView = new ImageView(IMG);
        imageView.setViewport(new Rectangle2D(OFSETX, OFSETY, WIDTH, HEIGHT)); // прорисовываем, но не всю

        // передаём наши данные в анимацию
        SpriteAnimated myAnimated = new SpriteAnimated(
                imageView,
                Duration.millis(500), // анимация
                COUNT, COLUMN,
                OFSETX, OFSETY,
                WIDTH, HEIGHT
        );

        myAnimated.setCycleCount(Animation.INDEFINITE); // повторять до бесконечности (полность от 0 до значения COUNT), менять его значение
        myAnimated.play(); // запустить

        // добавление на сцены | на форму
        Scene scene = new Scene(new Group(imageView), 500, 300);
        primaryStage.setScene(scene);  // размер формы и сцена
        primaryStage.show(); // отобразить
    }

    public static void main(String[] args) {
        launch(args);
    }

    // ------------------------------------------- наш класс с наследием от анимации
    private class SpriteAnimated extends Transition{
        private  ImageView imageView; // картинка
        private  int count; // сколько спрайтов возьмём
        private  int column;// сколько колонок
        private  int ofsetX;// откуда начинать
        private  int ofsetY;
        private  int width;// размеры
        private  int height;

        public SpriteAnimated( // конструктор
            ImageView imageVie,
            Duration duration, // анимация
            int count, int column,
            int ofsetX, int ofsetY,
            int width, int height
            )
        {
            this.imageView = imageVie;
            this.count=count;
            this.column=column;
            this.ofsetX=ofsetX;
            this.ofsetY=ofsetY;
            this.width=width;
            this.height=height;
            setCycleDuration(duration); // анимация, перелистывания спрайта
        }

        // наша анимация
        @Override
        protected void interpolate(double frac) {
             int index = Math.min((int)Math.floor(frac*count), count-1); // позиция, в зависимости от COUNT
             int x = (index % column) * width + ofsetX; // по Х
             int y = (index / column) * height + ofsetY; // по Y
            imageView.setViewport(new Rectangle2D(x,y,width,height)); // отрисовываем новый эл.
        }
    }
}

