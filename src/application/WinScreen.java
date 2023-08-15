package application;

import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

public class WinScreen {
	public static void showWinScreen(String winner) {
		Dialog dialog = new Dialog();
		dialog.setTitle("Game Result");
		dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
		dialog.getDialogPane().setContent(createContent(winner));
		dialog.show();

	}

	private static Node createContent(String winner) {
		AnchorPane gridPane = new AnchorPane();
		gridPane.setPrefWidth(400);
		gridPane.setPrefHeight(100);
		gridPane.setStyle("-fx-background-color: rgb(64,64,64)");

		Label lbl = new Label(winner);
		lbl.setStyle("-fx-text-fill: white");
		lbl.setFont(new Font(25));
		lbl.setLayoutX(140);
		lbl.setLayoutY(25);
		gridPane.getChildren().add(lbl);

		return gridPane;
	}

}
