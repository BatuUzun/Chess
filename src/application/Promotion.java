package application;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class Promotion {

	private int selectedPiece;

	public int getSelectedPiece() {
		return selectedPiece;
	}

	public void switchToScenePromotion(String pawnColor) {
		selectedPiece = -1;
		Dialog dialog = new Dialog();
		dialog.setTitle("Promote Pawn");
		dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
		dialog.getDialogPane().setContent(createContent(pawnColor));
		while (selectedPiece == -1)
			dialog.showAndWait();

	}

	private Node createContent(String pawnColor) {
		GridPane gridPane = new GridPane();
		gridPane.setPrefWidth(400);
		gridPane.setPrefHeight(100);
		gridPane.setStyle("-fx-background-color: rgb(64,64,64)");
		gridPane.setHgap(50);

		Pane[] pane = new Pane[4];
		Image[] image = new Image[4];
		ImageView imageView[] = new ImageView[4];

		if (pawnColor.equalsIgnoreCase("white")) {
			image[0] = new Image(
					"C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_quenn.png");
			image[1] = new Image(
					"C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_rook.png");
			image[2] = new Image(
					"C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_knight.png");
			image[3] = new Image(
					"C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_bishop.png");

		} else {
			image[0] = new Image(
					"C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_quenn.png");
			image[1] = new Image(
					"C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_rook.png");
			image[2] = new Image(
					"C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_knight.png");
			image[3] = new Image(
					"C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_bishop.png");

		}

		for (int i = 0; i < 4; i++) {
			imageView[i] = new ImageView(image[i]);
			pane[i] = new Pane();
			final int index = i;
			pane[i].setStyle("-fx-background-color: red;");

			pane[i].setOnMouseClicked(new EventHandler<Event>() {

				@Override
				public void handle(Event arg0) {
					for (int k = 0; k < 4; k++) {
						pane[k].setStyle("-fx-background-color: red;");
					}
					pane[index].setStyle("-fx-background-color: blue;");
					selectedPiece = index;
				}

			});

			pane[i].getChildren().add(imageView[i]);
			gridPane.add(pane[i], i, 0);
		}

		return gridPane;
	}

}
