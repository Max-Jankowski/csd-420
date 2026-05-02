// Max Jankowski
// CSD 420
// Module 7 assignment class code


import javafx.application.Application; //used for base class extended
import javafx.scene.Scene;  // wrapping layouts
import javafx.scene.layout.HBox;    //horizontal container
import javafx.scene.shape.Circle; // for the circle nodes
import javafx.stage.Stage;  //used for the window


// class to display 4 circles and have them styled based on css from textbook
public class CircleStyles extends Application {

    private static final double RADIUS  = 50;  // circle size in pixels
    private static final double SPACING = 10;  // gap between circles

    @Override
    public void start(Stage primaryStage) {

        // Circle1
        Circle circle1 = new Circle(RADIUS);
        circle1.getStyleClass().add("plaincircle");

        // Circle2 is used to show that the CSS class can be reused on more nodes
        Circle circle2 = new Circle(RADIUS);
        circle2.getStyleClass().add("plaincircle");

        //Circle3: red filled
        Circle circle3 = new Circle(RADIUS);
        circle3.setId("redcircle");

        //Circle4 overriding the class fill
        Circle circle4 = new Circle(RADIUS);
        circle4.getStyleClass().add("plaincircle");
        circle4.setId("greencircle");

        // layout of the circles, layout in a row
        HBox hBox = new HBox(SPACING);
        hBox.setStyle("-fx-padding: 20;");
        hBox.getChildren().addAll(circle1, circle2, circle3, circle4);

        // attaching HBox and loading external stylesheet
        Scene scene = new Scene(hBox, 500, 150);
        scene.getStylesheets().add(
                getClass().getResource("mystyle.css").toExternalForm()
        );

        // staging
        primaryStage.setTitle("CSD 420 Module 7 Circle Styles");
        primaryStage.setScene(scene);
        primaryStage.show();

        //running the inline test after scene display, assuming that test are expected inline rather then a
        // separate file, based on grading from last assignment turned in.
        runTests(circle1, circle3);
    }


    // build in inline tests. results will not show in window onl the terminal

    private void runTests(Circle c1, Circle c3) {

        System.out.println("\n   CircleStyles Testing   ");

        // checking if the CSS class was applied to circle1
        boolean t1 = c1.getStyleClass().contains("plaincircle");
        System.out.println((t1 ? "PASS" : "FAIL")
                + " ==> circle1 has CSS class 'plaincircle'");

        // in like manner checking to see CSS ID was applied to circle3
        boolean t2 = "redcircle".equals(c3.getId());
        System.out.println((t2 ? "PASS" : "FAIL")
                + " ==> circle3 has CSS ID 'redcircle'");

        System.out.println("************************************n");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
