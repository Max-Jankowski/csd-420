// Max Jankowski
//Bellevue University
//CSD 420 Module 1 assignment

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;

public class JankowskiCards extends Application {

    // keeping the image views as fields so the refresh can update them when pressed
    private ImageView[] cardViews = new ImageView[4];

    @Override
    public void start(Stage primaryStage) {


        //Building the 4 view slots that will hold card image, the ImageView is a FX node that will display the provided
        //images. They are stored as an array so they can be swapped out later
        for (int i = 0; i < 4; i++) {
            cardViews[i] = new ImageView();
            cardViews[i].setFitWidth(120);   // width in pixels
            cardViews[i].setFitHeight(160);  // likewise for the height
            cardViews[i].setPreserveRatio(true); // preventing the deformations of card by compression or stretching
        }

        // Load the first random hand into the 'ImageViews'
        dealCards();

        // the HBOX presents 4 cards
        HBox cardRow = new HBox(15); // small gap between cards, forgot this one
        cardRow.setAlignment(Pos.CENTER);
        cardRow.getChildren().addAll(cardViews); // adding all 4 images

        // the refresh button
        Button refreshButton = new Button("Refresh");

        // This is the Lambda expression requirement, instead of writing a separate event handler class,
        // I write the action inline as a lambda. I added a resource down at the bottom of the code that helped
        // understand that the "e" is the ActionEvent object. looks like its not needed here the interface requires it.
        refreshButton.setOnAction(e -> dealCards());

        // The VBox allows me to stack the cards on top and the button in the lower section
        // without this FX would have no reference of where to set the refresh
       VBox root = new VBox(20); // 20 pixels between the cards and refresh
       root.setAlignment(Pos.CENTER);
       root.setPadding(new Insets(20)); // space around the edge
       root.getChildren().addAll(cardRow, refreshButton);

        // scene and stage the GUI box
        Scene scene = new Scene(root, 620, 260);
        primaryStage.setTitle("Random Card Dealer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Writing the code that picks out the four random cards and loads the images ino the four ImageViews created
    private void dealCards() {

        // building a list of 52 cards
        ArrayList<Integer> deck = new ArrayList<>();
        for (int i = 1; i <= 52; i++) {
            deck.add(i);
        }

        // Shuffling the deck so the pull is always random
        Collections.shuffle(deck);

        // the first four from the shuffled deck
        for (int i = 0; i < 4; i++) {
            int cardNumber = deck.get(i);

            //The file path to the card folder
            String path = "cards/" + cardNumber + ".png";

            // the URI loading from disk the 'file:' informs JavaFX that this is a local file
            Image img = new Image("file:" + path);

            // swapping the image in this slot
            cardViews[i].setImage(img);
        }
    }

    public static void main(String[] args) {
        launch(args); // gives JavaFX control
    }
}



// RESOURCES:
//Liang, Y. D. (2024). Introduction to java programming and Data Structures. Pearson.
//https://www.educative.io/blog/java-lambda-expression-tutorial?utm_campaign=Pmax_feb25&utm_source=google&utm_medium=ppc&utm_content=&utm_term=&eid=5082902844932096&utm_term=&utm_campaign=%5BMar+25%5D+Pmax.+-+Coding+Interview+Prep&utm_source=adwords&utm_medium=ppc&hsa_acc=5451446008&hsa_cam=22344713166&hsa_grp=&hsa_ad=&hsa_src=x&hsa_tgt=&hsa_kw=&hsa_mt=&hsa_net=adwords&hsa_ver=3&gad_source=1&gad_campaignid=22354833079&gbraid=0AAAAADfWLuSd8FxhBb-igtGiEehF-bSsF&gclid=Cj0KCQjwm6POBhCrARIsAIG58CLARdPovOuz1eIR7MDnzukc5GFz_uczPe5_f4B34uEHsxO-RofMcbcaAnvoEALw_wcB
// Didn't copy this, however did get some ideas, https://coderanch.com/t/749849/java/Draw-cards-deck-card-images
