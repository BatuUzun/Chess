package application;

import java.awt.Color;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Stack;

import Pieces.Bishop;
import Pieces.King;
import Pieces.Knight;
import Pieces.Pawn;
import Pieces.Quenn;
import Pieces.Rook;
import Sounds.SoundsSys;
import Timer.TimerSys;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class BoardController implements Initializable {
	private static final int GAME_DURATION = 10;
	private Pane[][] pane;
	private ImageView[][] imageView;
	private Color brown = new Color(181, 136, 99);
	private Color white = new Color(240, 217, 181);
	private Color green = new Color(130, 151, 105);
	private final int NUMBER_OF_ROWS = 8;
	private final int NUMBER_OF_COLUMS = 8;
	@FXML
	private AnchorPane anchorPane;
	@FXML
	Label nameLbl;
	private final int SIZE = 100;
	private static double IMAGE_HEIGHT = 70;
	private static double IMAGE_WIDTH = 70;
	private Image image;
	private ArrayList<int[]> possibleMoves;
	private boolean isPossibleMoveClicked = false;

	private int moveFirstPosition, moveSecondPosition, selectedFirstPosition, selectedSecondPosition;
	private ArrayList<int[]> specialCoord;
	private String perspective = "white";
	private boolean whiteTurn;
	private King whiteKing;
	private King blackKing;
	private boolean rightCastlingWhite, leftCastlingWhite, rightCastlingBlack, leftCastlingBlack;
	private boolean isWhiteEnPassant, isBlackEnPassant;
	private int enPassantFirstPosition;
	private int enPassantSecondPosition;
	private int enPassantMovingPawnFirstPosition, enPassantMovingPawnSecondPosition;
	@FXML
	private Button nextButton, previousButton, lastButton, firstButton, restartButton,
			restartAndChangePerspectiveButton, changePerspectiveButton;
	@FXML
	private Label timerTopLbl, timerBottomLbl;
	private boolean isWhiteMoved, isBlackMoved, isTimerRunning;

	private int timeWhite, timeBlack;
	private String timerString;
	private Timeline timeline;
	private Stack<ImageView[][]> allMoves, poppedMoves;
	private int currentMoveStackController;
	private boolean isGameFinished;

	private void drawBoardWhitePerspective() {
		int boardColorController = 0;
		char letter = 'a';
		Label label = new Label();
		pane = new Pane[NUMBER_OF_ROWS][NUMBER_OF_COLUMS];
		imageView = new ImageView[NUMBER_OF_ROWS][NUMBER_OF_COLUMS];
		for (int i = 0; i < NUMBER_OF_ROWS; i++) {
			if (i % 2 == 0) {
				boardColorController = 0;
			} else {
				boardColorController = 1;
			}
			for (int k = 0; k < NUMBER_OF_COLUMS; k++) {

				pane[i][k] = new Pane();

				imageView[i][k] = new ImageView();
				imageView[i][k].setFitHeight(IMAGE_HEIGHT);
				imageView[i][k].setFitWidth(IMAGE_WIDTH);
				imageView[i][k].setLayoutX(15);
				imageView[i][k].setLayoutY(15);
				pane[i][k].getChildren().add(imageView[i][k]);

				pane[i][k].setLayoutX(50 + i * SIZE);
				pane[i][k].setLayoutY(100 + k * SIZE);
				pane[i][k].setPrefSize(SIZE, SIZE);

				if (i == 7) {
					label = new Label(String.valueOf(NUMBER_OF_COLUMS - k));
					label.setLayoutX(SIZE - 10); // Adjust the label's position within the pane
					label.setLayoutY(5);
					pane[i][k].getChildren().add(label);
					label.setStyle("-fx-text-fill: rgb(" + brown.getRed() + "," + brown.getGreen() + ", "
							+ brown.getBlue() + ");-fx-font-weight: bold;");
				} else if (k == 7 && i != 7) {
					label = new Label(String.valueOf(letter++));
					label.setLayoutX(5); // Adjust the label's position within the pane
					label.setLayoutY(SIZE - 17);
					pane[i][k].getChildren().add(label);
				}
				if (k == 7 && i == 7) {
					label = new Label(String.valueOf(letter++));
					label.setLayoutX(5); // Adjust the label's position within the pane
					label.setLayoutY(SIZE - 17);
					pane[i][k].getChildren().add(label);

				}

				if (boardColorController % 2 == 0) {
					pane[i][k].setStyle("-fx-background-color: rgb(" + white.getRed() + "," + white.getGreen() + ", "
							+ white.getBlue() + ");");
					label.setStyle("-fx-text-fill: rgb(" + brown.getRed() + "," + brown.getGreen() + ", "
							+ brown.getBlue() + ");-fx-font-weight: bold;");
				} else {
					pane[i][k].setStyle("-fx-background-color: rgb(" + brown.getRed() + "," + brown.getGreen() + ", "
							+ brown.getBlue() + ");");

					label.setStyle("-fx-text-fill: rgb(" + white.getRed() + "," + white.getGreen() + ", "
							+ white.getBlue() + ");-fx-font-weight: bold;");

				}
				// label.setStyle("-fx-font-weight: bold");
				boardColorController++;
				anchorPane.getChildren().add(pane[i][k]);

			}

		}
		setIdWhitePerspective();
	}

	private void drawBoardBlackPerspective() {

		int boardColorController = 0;
		char letter = 'h';
		pane = new Pane[NUMBER_OF_ROWS][NUMBER_OF_COLUMS];
		imageView = new ImageView[NUMBER_OF_ROWS][NUMBER_OF_COLUMS];
		Label label = new Label();
		for (int i = 0; i < NUMBER_OF_ROWS; i++) {
			if (i % 2 == 1) {
				boardColorController = 0;
			} else {
				boardColorController = 1;
			}
			for (int k = 0; k < NUMBER_OF_COLUMS; k++) {

				pane[i][k] = new Pane();
				imageView[i][k] = new ImageView();
				imageView[i][k].setFitHeight(IMAGE_HEIGHT);
				imageView[i][k].setFitWidth(IMAGE_WIDTH);
				imageView[i][k].setLayoutX(15);
				imageView[i][k].setLayoutY(15);
				pane[i][k].getChildren().add(imageView[i][k]);
				pane[i][k].setLayoutX(50 + i * SIZE);
				pane[i][k].setLayoutY(100 + k * SIZE);
				pane[i][k].setPrefSize(SIZE, SIZE);
				if (i == 7) {
					label = new Label(String.valueOf(k + 1));
					label.setLayoutX(SIZE - 10); // Adjust the label's position within the pane
					label.setLayoutY(5);
					pane[i][k].getChildren().add(label);
					label.setStyle("-fx-text-fill: rgb(" + brown.getRed() + "," + brown.getGreen() + ", "
							+ brown.getBlue() + ");-fx-font-weight: bold;");
				} else if (k == 7 && i != 7) {
					label = new Label(String.valueOf(letter--));
					label.setLayoutX(5); // Adjust the label's position within the pane
					label.setLayoutY(SIZE - 17);
					pane[i][k].getChildren().add(label);
				}
				if (k == 7 && i == 7) {
					label = new Label(String.valueOf(letter--));
					label.setLayoutX(5); // Adjust the label's position within the pane
					label.setLayoutY(SIZE - 17);
					pane[i][k].getChildren().add(label);
				}

				if (boardColorController % 2 == 0) {
					pane[i][k].setStyle("-fx-background-color: rgb(" + brown.getRed() + "," + brown.getGreen() + ", "
							+ brown.getBlue() + ");");
					label.setStyle("-fx-text-fill: rgb(" + white.getRed() + "," + white.getGreen() + ", "
							+ white.getBlue() + ");-fx-font-weight: bold;");
				} else {
					pane[i][k].setStyle("-fx-background-color: rgb(" + white.getRed() + "," + white.getGreen() + ", "
							+ white.getBlue() + ");");
					label.setStyle("-fx-text-fill: rgb(" + brown.getRed() + "," + brown.getGreen() + ", "
							+ brown.getBlue() + ");-fx-font-weight: bold;");
				}
				boardColorController++;
				anchorPane.getChildren().add(pane[i][k]);

			}

		}
		setIdBlackPerspective();

	}

	private void setIdWhitePerspective() {
		int row = 8;
		char column = 'a';
		for (int i = 0; i < NUMBER_OF_ROWS; i++) {

			for (int k = 0; k < NUMBER_OF_COLUMS; k++) {
				pane[i][k].setId(String.valueOf(column) + row);

			}

			column = 'a';
			row--;

		}

	}

	private void setIdBlackPerspective() {
		int row = 1;
		char column = 'h';
		for (int i = 0; i < NUMBER_OF_ROWS; i++) {

			for (int k = 0; k < NUMBER_OF_COLUMS; k++) {
				pane[i][k].setId(String.valueOf(column) + row);

			}

			column = 'h';
			row++;

		}

	}

	private void drawPiecesWhitePerspective() {
		perspective = "white";
		for (int i = 0; i < NUMBER_OF_COLUMS; i++) {
			image = new Image("C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_pawn.png");
			imageView[i][1].setImage(image);

			image = new Image("C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_pawn.png");
			imageView[i][6].setImage(image);

		}

		image = new Image("C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_rook.png");
		imageView[0][7].setImage(image);
		imageView[7][7].setImage(image);

		image = new Image("C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_rook.png");

		imageView[0][0].setImage(image);
		imageView[7][0].setImage(image);

		imageView[3][7].setImage(image);
		// imageView[0][4].setImage(image);

		image = new Image("C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_knight.png");
		imageView[1][7].setImage(image);
		imageView[6][7].setImage(image);

		image = new Image("C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_knight.png");
		imageView[1][0].setImage(image);
		imageView[6][0].setImage(image);

		image = new Image("C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_bishop.png");
		imageView[2][7].setImage(image);
		imageView[5][7].setImage(image);

		image = new Image("C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_bishop.png");
		imageView[2][0].setImage(image);
		imageView[5][0].setImage(image);

		image = new Image("C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_quenn.png");
		imageView[3][7].setImage(image);

		image = new Image("C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_quenn.png");
		imageView[3][0].setImage(image);
		// imageView[4][3].setImage(image);
		// imageView[4][4].setImage(image);

		image = new Image("C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_king.png");
		imageView[4][7].setImage(image);

		// imageView[4][4].setImage(image);

		// imageView[4][4].setImage(image);

		image = new Image("C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_king.png");
		imageView[4][0].setImage(image);

	}

	private void drawPiecesBlackPerspective() {
		perspective = "black";
		for (int i = 0; i < NUMBER_OF_COLUMS; i++) {
			image = new Image("C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_pawn.png");
			imageView[i][1].setImage(image);

			image = new Image("C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_pawn.png");
			imageView[i][6].setImage(image);
		}

		image = new Image("C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_rook.png");
		imageView[0][7].setImage(image);
		imageView[7][7].setImage(image);
		image = new Image("C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_rook.png");
		imageView[0][0].setImage(image);
		imageView[7][0].setImage(image);

		image = new Image("C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_knight.png");
		imageView[1][7].setImage(image);
		imageView[6][7].setImage(image);
		image = new Image("C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_knight.png");
		imageView[1][0].setImage(image);
		imageView[6][0].setImage(image);

		image = new Image("C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_bishop.png");
		imageView[2][7].setImage(image);
		imageView[5][7].setImage(image);
		image = new Image("C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_bishop.png");
		imageView[2][0].setImage(image);
		imageView[5][0].setImage(image);

		image = new Image("C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_king.png");
		imageView[3][7].setImage(image);
		image = new Image("C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_king.png");
		imageView[3][0].setImage(image);

		image = new Image("C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_quenn.png");
		imageView[4][7].setImage(image);
		image = new Image("C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_quenn.png");
		imageView[4][0].setImage(image);

	}

	private void setClickEvents() {

		for (int i = 0; i < NUMBER_OF_ROWS; i++) {
			final int firstPosition = i;
			for (int k = 0; k < NUMBER_OF_COLUMS; k++) {
				final int secondPosition = k;
				pane[i][k].setOnMouseClicked(new EventHandler<Event>() {

					@Override
					public void handle(Event arg0) {

						image = imageView[firstPosition][secondPosition].getImage();
						if (possibleMoves != null) {
							resetPossibleMoves();
						}

						if (image != null) {

							switch (image.getUrl()) {
							case "C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_pawn.png":
							case "C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_pawn.png":
								selectedSecondPosition = secondPosition;
								selectedFirstPosition = firstPosition;

								Pawn pawn = new Pawn(perspective, firstPosition, secondPosition);

								possibleMoves = new ArrayList<int[]>();
								if (isPossibleMoveClicked == false) {
									moveFirstPosition = firstPosition;
									moveSecondPosition = secondPosition;
									isPossibleMoveClicked = true;
									if (isWhiteEnPassant) {
										pawn.setWhiteEnpassant(isWhiteEnPassant);
										pawn.setEnPassantFirstPosition(enPassantFirstPosition);
										pawn.setEnPassantSecondPosition(enPassantSecondPosition);
										pawn.setEnPassantMovingPawnSecondPosition(enPassantMovingPawnSecondPosition);
										pawn.setEnPassantMovingPawnFirstPosition(enPassantMovingPawnFirstPosition);
									} else if (isBlackEnPassant) {
										pawn.setBlackEnpassant(isBlackEnPassant);
										pawn.setEnPassantFirstPosition(enPassantFirstPosition);
										pawn.setEnPassantSecondPosition(enPassantSecondPosition);
										pawn.setEnPassantMovingPawnSecondPosition(enPassantMovingPawnSecondPosition);
										pawn.setEnPassantMovingPawnFirstPosition(enPassantMovingPawnFirstPosition);
									}
									if (perspective.equalsIgnoreCase("white")) {

										possibleMoves = pawn.showPossibleMoves(imageView);

									} else {
										possibleMoves = pawn.showPossibleMovesForBlackPerspective(imageView,
												firstPosition, secondPosition);
									}

									if (poppedMoves.isEmpty() && !isGameFinished)
										setGreenDots(firstPosition, secondPosition);
								} else {
									if (imageView[moveFirstPosition][moveSecondPosition].getImage() != null
											&& ((whiteTurn
													&& imageView[moveFirstPosition][moveSecondPosition].getImage()
															.getUrl().contains("white")
													|| (!whiteTurn && imageView[moveFirstPosition][moveSecondPosition]
															.getImage().getUrl().contains("black")))))
										if (specialCoord != null)
											for (int[] a : specialCoord) {
												if (a[0] == selectedFirstPosition && a[1] == selectedSecondPosition) {
													if (isAbleToMove()) {
														moveToPiece(moveFirstPosition, moveSecondPosition);
														SoundsSys.captureSound();
														saveMoves();

														isBlackEnPassant = false;
														isWhiteEnPassant = false;
														checkCastling();
														if (perspective.equalsIgnoreCase("white")) {

															if (imageView[selectedFirstPosition][selectedSecondPosition]
																	.getImage().getUrl().contains("white_pawn")
																	&& selectedSecondPosition == 0) {

																promotionScreen("white", false);
															} else if (imageView[selectedFirstPosition][selectedSecondPosition]
																	.getImage().getUrl().contains("black_pawn")
																	&& selectedSecondPosition == 7) {

																promotionScreen("black", false);
															}

														} else {

															if (imageView[selectedFirstPosition][selectedSecondPosition]
																	.getImage().getUrl().contains("white_pawn")
																	&& selectedSecondPosition == 7) {

																promotionScreen("white", false);
															} else if (imageView[selectedFirstPosition][selectedSecondPosition]
																	.getImage().getUrl().contains("black_pawn")
																	&& selectedSecondPosition == 0) {

																promotionScreen("black", false);
															}

														}
													} else
														SoundsSys.errorSound();

												}
											}
									resetPossibleMoves();
									isPossibleMoveClicked = false;
									possibleMoves = new ArrayList<int[]>();

								}

								break;
							case "C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_rook.png":
							case "C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_rook.png":
								selectedSecondPosition = secondPosition;
								selectedFirstPosition = firstPosition;

								Rook rook = new Rook("yes");
								rook.setFirstPosition(firstPosition);
								rook.setSecondPosition(secondPosition);

								// Rook rook = new Rook(firstPosition, secondPosition, "yes");
								if (isPossibleMoveClicked == false) {
									moveFirstPosition = firstPosition;
									moveSecondPosition = secondPosition;
									isPossibleMoveClicked = true;
									possibleMoves = rook.showPossibleMoves(imageView);
									if (poppedMoves.isEmpty() && !isGameFinished)
										setGreenDots(firstPosition, secondPosition);
								} else {
									// moveFirstPosition = firstPosition;
									// moveSecondPosition = secondPosition;
									if (imageView[moveFirstPosition][moveSecondPosition].getImage() != null
											&& ((whiteTurn
													&& imageView[moveFirstPosition][moveSecondPosition].getImage()
															.getUrl().contains("white")
													|| (!whiteTurn && imageView[moveFirstPosition][moveSecondPosition]
															.getImage().getUrl().contains("black")))))
										if (specialCoord != null)
											for (int[] a : specialCoord) {
												if (a[0] == selectedFirstPosition && a[1] == selectedSecondPosition) {
													if (isAbleToMove()) {
														moveToPiece(moveFirstPosition, moveSecondPosition);
														SoundsSys.captureSound();
														saveMoves();
														if (isThreeMoveEqual()) {
															timeline.stop();
															isGameFinished = true;
															WinScreen.showWinScreen("Draw!");
														}
														isWhiteEnPassant = false;
														isBlackEnPassant = false;
														checkCastling();
														if (perspective.equalsIgnoreCase("white")) {

															if (imageView[selectedFirstPosition][selectedSecondPosition]
																	.getImage().getUrl().contains("white_pawn")
																	&& selectedSecondPosition == 0) {

																promotionScreen("white", false);
															} else if (imageView[selectedFirstPosition][selectedSecondPosition]
																	.getImage().getUrl().contains("black_pawn")
																	&& selectedSecondPosition == 7) {

																promotionScreen("black", false);
															}

														} else {

															if (imageView[selectedFirstPosition][selectedSecondPosition]
																	.getImage().getUrl().contains("white_pawn")
																	&& selectedSecondPosition == 7) {

																promotionScreen("white", false);
															} else if (imageView[selectedFirstPosition][selectedSecondPosition]
																	.getImage().getUrl().contains("black_pawn")
																	&& selectedSecondPosition == 0) {

																promotionScreen("black", false);
															}

														}
													} else
														SoundsSys.errorSound();

												}
											}
									resetPossibleMoves();
									isPossibleMoveClicked = false;
									possibleMoves = new ArrayList<int[]>();

								}

								break;
							case "C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_knight.png":
							case "C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_knight.png":
								selectedSecondPosition = secondPosition;
								selectedFirstPosition = firstPosition;

								Knight knight = new Knight(firstPosition, secondPosition, "yes");
								if (isPossibleMoveClicked == false) {
									moveFirstPosition = firstPosition;
									moveSecondPosition = secondPosition;
									isPossibleMoveClicked = true;
									possibleMoves = knight.showPossibleMoves(imageView);
									if (poppedMoves.isEmpty() && !isGameFinished)
										setGreenDots(firstPosition, secondPosition);
								} else {
									// moveFirstPosition = firstPosition;
									// moveSecondPosition = secondPosition;
									if (imageView[moveFirstPosition][moveSecondPosition].getImage() != null
											&& ((whiteTurn
													&& imageView[moveFirstPosition][moveSecondPosition].getImage()
															.getUrl().contains("white")
													|| (!whiteTurn && imageView[moveFirstPosition][moveSecondPosition]
															.getImage().getUrl().contains("black")))))
										if (specialCoord != null)
											for (int[] a : specialCoord) {
												if (a[0] == selectedFirstPosition && a[1] == selectedSecondPosition) {
													if (isAbleToMove()) {
														moveToPiece(moveFirstPosition, moveSecondPosition);
														SoundsSys.captureSound();
														saveMoves();
														if (isThreeMoveEqual()) {
															timeline.stop();
															isGameFinished = true;
															WinScreen.showWinScreen("Draw!");
														}
														isWhiteEnPassant = false;
														isBlackEnPassant = false;
														checkCastling();
														if (perspective.equalsIgnoreCase("white")) {

															if (imageView[selectedFirstPosition][selectedSecondPosition]
																	.getImage().getUrl().contains("white_pawn")
																	&& selectedSecondPosition == 0) {

																promotionScreen("white", false);
															} else if (imageView[selectedFirstPosition][selectedSecondPosition]
																	.getImage().getUrl().contains("black_pawn")
																	&& selectedSecondPosition == 7) {

																promotionScreen("black", false);
															}

														} else {

															if (imageView[selectedFirstPosition][selectedSecondPosition]
																	.getImage().getUrl().contains("white_pawn")
																	&& selectedSecondPosition == 7) {

																promotionScreen("white", false);
															} else if (imageView[selectedFirstPosition][selectedSecondPosition]
																	.getImage().getUrl().contains("black_pawn")
																	&& selectedSecondPosition == 0) {

																promotionScreen("black", false);
															}

														}
													} else
														SoundsSys.errorSound();

												}
											}
									resetPossibleMoves();
									isPossibleMoveClicked = false;
									possibleMoves = new ArrayList<int[]>();

								}
								break;
							case "C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_bishop.png":
							case "C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_bishop.png":
								selectedSecondPosition = secondPosition;
								selectedFirstPosition = firstPosition;

								Bishop bishop = new Bishop(firstPosition, secondPosition, "yes");
								if (isPossibleMoveClicked == false) {
									moveFirstPosition = firstPosition;
									moveSecondPosition = secondPosition;
									isPossibleMoveClicked = true;
									possibleMoves = bishop.showPossibleMoves(imageView);
									if (poppedMoves.isEmpty() && !isGameFinished)
										setGreenDots(firstPosition, secondPosition);
								} else {
									// moveFirstPosition = firstPosition;
									// moveSecondPosition = secondPosition;
									if (imageView[moveFirstPosition][moveSecondPosition].getImage() != null
											&& ((whiteTurn
													&& imageView[moveFirstPosition][moveSecondPosition].getImage()
															.getUrl().contains("white")
													|| (!whiteTurn && imageView[moveFirstPosition][moveSecondPosition]
															.getImage().getUrl().contains("black")))))
										if (specialCoord != null)
											for (int[] a : specialCoord) {
												if (a[0] == selectedFirstPosition && a[1] == selectedSecondPosition) {
													if (isAbleToMove()) {
														moveToPiece(moveFirstPosition, moveSecondPosition);
														SoundsSys.captureSound();
														saveMoves();
														if (isThreeMoveEqual()) {
															timeline.stop();
															isGameFinished = true;
															WinScreen.showWinScreen("Draw!");
														}
														isWhiteEnPassant = false;
														isBlackEnPassant = false;
														checkCastling();
														if (perspective.equalsIgnoreCase("white")) {

															if (imageView[selectedFirstPosition][selectedSecondPosition]
																	.getImage().getUrl().contains("white_pawn")
																	&& selectedSecondPosition == 0) {

																promotionScreen("white", false);
															} else if (imageView[selectedFirstPosition][selectedSecondPosition]
																	.getImage().getUrl().contains("black_pawn")
																	&& selectedSecondPosition == 7) {

																promotionScreen("black", false);
															}

														} else {

															if (imageView[selectedFirstPosition][selectedSecondPosition]
																	.getImage().getUrl().contains("white_pawn")
																	&& selectedSecondPosition == 7) {

																promotionScreen("white", false);
															} else if (imageView[selectedFirstPosition][selectedSecondPosition]
																	.getImage().getUrl().contains("black_pawn")
																	&& selectedSecondPosition == 0) {

																promotionScreen("black", false);
															}

														}
													} else
														SoundsSys.errorSound();

												}
											}
									resetPossibleMoves();
									isPossibleMoveClicked = false;
									possibleMoves = new ArrayList<int[]>();

								}

								break;
							case "C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_quenn.png":
							case "C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_quenn.png":
								selectedSecondPosition = secondPosition;
								selectedFirstPosition = firstPosition;

								Quenn quenn = new Quenn(firstPosition, secondPosition);
								if (isPossibleMoveClicked == false) {
									moveFirstPosition = firstPosition;
									moveSecondPosition = secondPosition;
									isPossibleMoveClicked = true;
									possibleMoves = quenn.showPossibleMoves(imageView);
									if (poppedMoves.isEmpty() && !isGameFinished)
										setGreenDots(firstPosition, secondPosition);
								} else {
									// moveFirstPosition = firstPosition;
									// moveSecondPosition = secondPosition;
									if (imageView[moveFirstPosition][moveSecondPosition].getImage() != null
											&& ((whiteTurn
													&& imageView[moveFirstPosition][moveSecondPosition].getImage()
															.getUrl().contains("white")
													|| (!whiteTurn && imageView[moveFirstPosition][moveSecondPosition]
															.getImage().getUrl().contains("black")))))
										if (specialCoord != null)
											for (int[] a : specialCoord) {
												if (a[0] == selectedFirstPosition && a[1] == selectedSecondPosition) {
													if (isAbleToMove()) {
														moveToPiece(moveFirstPosition, moveSecondPosition);
														SoundsSys.captureSound();
														saveMoves();
														if (isThreeMoveEqual()) {
															timeline.stop();
															isGameFinished = true;
															WinScreen.showWinScreen("Draw!");
														}
														isWhiteEnPassant = false;
														isBlackEnPassant = false;
														checkCastling();
														if (perspective.equalsIgnoreCase("white")) {

															if (imageView[selectedFirstPosition][selectedSecondPosition]
																	.getImage().getUrl().contains("white_pawn")
																	&& selectedSecondPosition == 0) {

																promotionScreen("white", false);
															} else if (imageView[selectedFirstPosition][selectedSecondPosition]
																	.getImage().getUrl().contains("black_pawn")
																	&& selectedSecondPosition == 7) {

																promotionScreen("black", false);
															}

														} else {

															if (imageView[selectedFirstPosition][selectedSecondPosition]
																	.getImage().getUrl().contains("white_pawn")
																	&& selectedSecondPosition == 7) {

																promotionScreen("white", false);
															} else if (imageView[selectedFirstPosition][selectedSecondPosition]
																	.getImage().getUrl().contains("black_pawn")
																	&& selectedSecondPosition == 0) {

																promotionScreen("black", false);
															}

														}
													} else
														SoundsSys.errorSound();

												}
											}
									resetPossibleMoves();
									isPossibleMoveClicked = false;
									possibleMoves = new ArrayList<int[]>();

								}

								break;
							case "C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_king.png":
							case "C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_king.png":
								selectedSecondPosition = secondPosition;
								selectedFirstPosition = firstPosition;

								if (image.getUrl().contains("white")) {
									whiteKing.setFirstPosition(firstPosition);
									whiteKing.setSecondPosition(secondPosition);
								} else {
									blackKing.setFirstPosition(firstPosition);
									blackKing.setSecondPosition(secondPosition);
								}
								// King king = new King(perspective, firstPosition, secondPosition);
								if (isPossibleMoveClicked == false) {
									moveFirstPosition = firstPosition;
									moveSecondPosition = secondPosition;
									isPossibleMoveClicked = true;
									if (image.getUrl().contains("white"))
										possibleMoves = whiteKing.showPossibleMoves(imageView);
									else
										possibleMoves = blackKing.showPossibleMoves(imageView);
									if (poppedMoves.isEmpty() && !isGameFinished)
										setGreenDots(firstPosition, secondPosition);
								} else {

									// moveFirstPosition = firstPosition;
									// moveSecondPosition = secondPosition;
									if (imageView[moveFirstPosition][moveSecondPosition].getImage() != null
											&& ((whiteTurn
													&& imageView[moveFirstPosition][moveSecondPosition].getImage()
															.getUrl().contains("white")
													|| (!whiteTurn && imageView[moveFirstPosition][moveSecondPosition]
															.getImage().getUrl().contains("black")))))
										if (specialCoord != null)
											for (int[] a : specialCoord) {
												if (a[0] == selectedFirstPosition && a[1] == selectedSecondPosition) {

													moveToPiece(moveFirstPosition, moveSecondPosition);
													SoundsSys.captureSound();
													saveMoves();
													if (isThreeMoveEqual()) {
														timeline.stop();
														isGameFinished = true;
														WinScreen.showWinScreen("Draw!");
													}
													isWhiteEnPassant = false;
													isBlackEnPassant = false;
													if (image.getUrl().contains("white"))
														whiteKing.setIsMoved();
													else
														blackKing.setIsMoved();

													checkCastling();
												}

											}
									resetPossibleMoves();
									isPossibleMoveClicked = false;
									possibleMoves = new ArrayList<int[]>();

								}

								break;
							default:
								moveFirstPosition = firstPosition;
								moveSecondPosition = secondPosition;

								if (image.getUrl().contains("green_dot"))
									if ((whiteTurn
											&& imageView[selectedFirstPosition][selectedSecondPosition]
													.getImage() != null
											&& imageView[selectedFirstPosition][selectedSecondPosition].getImage()
													.getUrl().contains("white"))
											|| (!whiteTurn
													&& imageView[selectedFirstPosition][selectedSecondPosition]
															.getImage() != null
													&& imageView[selectedFirstPosition][selectedSecondPosition]
															.getImage().getUrl().contains("black")))
										if (isAbleToMove() || isAbleToMoveGreenDot()) {
											moveToGreenDot(moveFirstPosition, moveSecondPosition);
											previousButton.setDisable(false);
											firstButton.setDisable(false);
											saveMoves();
											if (isThreeMoveEqual()) {
												timeline.stop();
												isGameFinished = true;
												WinScreen.showWinScreen("Draw!");
											}
											if (imageView[moveFirstPosition][moveSecondPosition].getImage().getUrl()
													.contains("black_king")) {
												leftCastlingBlack = false;
												rightCastlingBlack = false;
												blackKing.setLeftCastlingBlack(leftCastlingBlack);
												blackKing.setRightCastlingBlack(rightCastlingBlack);
											} else if (imageView[moveFirstPosition][moveSecondPosition].getImage()
													.getUrl().contains("white_king")) {
												leftCastlingWhite = false;
												rightCastlingWhite = false;
											}
											checkCastling();
											SoundsSys.moveSound();
											isWhiteEnPassant = false;
											isBlackEnPassant = false;
											if (imageView[moveFirstPosition][moveSecondPosition].getImage() != null
													&& imageView[moveFirstPosition][moveSecondPosition].getImage()
															.getUrl().contains("white_king")) {
												whiteKing.setIsMoved();

											} else if (imageView[moveFirstPosition][moveSecondPosition]
													.getImage() != null
													&& imageView[moveFirstPosition][moveSecondPosition].getImage()
															.getUrl().contains("black_king")) {
												blackKing.setIsMoved();

											}

											// checkCastling();
											if (perspective.equalsIgnoreCase("white")) {

												if (imageView[moveFirstPosition][moveSecondPosition].getImage().getUrl()
														.contains("white_pawn") && moveSecondPosition == 0) {

													promotionScreen("white", true);
												} else if (imageView[moveFirstPosition][moveSecondPosition].getImage()
														.getUrl().contains("black_pawn") && moveSecondPosition == 7) {

													promotionScreen("black", true);
												}

											} else {

												if (imageView[moveFirstPosition][moveSecondPosition].getImage().getUrl()
														.contains("white_pawn") && moveSecondPosition == 7) {

													promotionScreen("white", true);
												} else if (imageView[moveFirstPosition][moveSecondPosition].getImage()
														.getUrl().contains("black_pawn") && moveSecondPosition == 0) {

													promotionScreen("black", true);
												}

											}
											if (moveFirstPosition + 1 <= 7 && moveFirstPosition - 1 >= 0) {
												if (Math.abs(selectedSecondPosition - moveSecondPosition) == 2
														&& whiteTurn
														&& ((imageView[moveFirstPosition + 1][moveSecondPosition]
																.getImage() != null
																&& imageView[moveFirstPosition + 1][moveSecondPosition]
																		.getImage().getUrl().contains("white_pawn"))
																|| (imageView[moveFirstPosition - 1][moveSecondPosition]
																		.getImage() != null
																		&& imageView[moveFirstPosition
																				- 1][moveSecondPosition].getImage()
																				.getUrl().contains("white_pawn")))) {

													isWhiteEnPassant = true;
													enPassantFirstPosition = moveFirstPosition;
													enPassantSecondPosition = moveSecondPosition;
													if (imageView[moveFirstPosition + 1][moveSecondPosition]
															.getImage() != null
															&& imageView[moveFirstPosition + 1][moveSecondPosition]
																	.getImage().getUrl().contains("white_pawn")) {
														enPassantMovingPawnFirstPosition = moveFirstPosition + 1;
														enPassantMovingPawnSecondPosition = moveSecondPosition;
													} else {
														enPassantMovingPawnFirstPosition = moveFirstPosition - 1;
														enPassantMovingPawnSecondPosition = moveSecondPosition;
													}

												}

												else if (Math.abs(selectedSecondPosition - moveSecondPosition) == 2
														&& !whiteTurn
														&& ((imageView[moveFirstPosition + 1][moveSecondPosition]
																.getImage() != null
																&& imageView[moveFirstPosition + 1][moveSecondPosition]
																		.getImage().getUrl().contains("black_pawn"))
																|| (imageView[moveFirstPosition - 1][moveSecondPosition]
																		.getImage() != null
																		&& imageView[moveFirstPosition
																				- 1][moveSecondPosition].getImage()
																				.getUrl().contains("black_pawn")))) {

													isBlackEnPassant = true;
													enPassantFirstPosition = moveFirstPosition;
													enPassantSecondPosition = moveSecondPosition;
													if (imageView[moveFirstPosition + 1][moveSecondPosition]
															.getImage() != null
															&& imageView[moveFirstPosition + 1][moveSecondPosition]
																	.getImage().getUrl().contains("black_pawn")) {
														enPassantMovingPawnFirstPosition = moveFirstPosition + 1;
														enPassantMovingPawnSecondPosition = moveSecondPosition;
													} else {
														enPassantMovingPawnFirstPosition = moveFirstPosition - 1;
														enPassantMovingPawnSecondPosition = moveSecondPosition;
													}
												}
											} else {
												if (moveFirstPosition + 1 > 7) {
													if (Math.abs(selectedSecondPosition - moveSecondPosition) == 2
															&& whiteTurn
															&& ((imageView[moveFirstPosition - 1][moveSecondPosition]
																	.getImage() != null
																	&& imageView[moveFirstPosition
																			- 1][moveSecondPosition].getImage().getUrl()
																			.contains("white_pawn")))) {

														isWhiteEnPassant = true;
														enPassantFirstPosition = moveFirstPosition;
														enPassantSecondPosition = moveSecondPosition;

													} else if (Math
															.abs(selectedSecondPosition - moveSecondPosition) == 2
															&& !whiteTurn
															&& ((imageView[moveFirstPosition - 1][moveSecondPosition]
																	.getImage() != null
																	&& imageView[moveFirstPosition
																			- 1][moveSecondPosition].getImage().getUrl()
																			.contains("black_pawn")))) {

														isBlackEnPassant = true;
														enPassantFirstPosition = moveFirstPosition;
														enPassantSecondPosition = moveSecondPosition;

													}
													enPassantMovingPawnFirstPosition = moveFirstPosition - 1;
													enPassantMovingPawnSecondPosition = moveSecondPosition;
												}

												else {
													if (Math.abs(selectedSecondPosition - moveSecondPosition) == 2
															&& whiteTurn
															&& ((imageView[moveFirstPosition + 1][moveSecondPosition]
																	.getImage() != null
																	&& imageView[moveFirstPosition
																			+ 1][moveSecondPosition].getImage().getUrl()
																			.contains("white_pawn")))) {

														isWhiteEnPassant = true;
														enPassantFirstPosition = moveFirstPosition;
														enPassantSecondPosition = moveSecondPosition;

													} else if (Math
															.abs(selectedSecondPosition - moveSecondPosition) == 2
															&& !whiteTurn
															&& ((imageView[moveFirstPosition + 1][moveSecondPosition]
																	.getImage() != null
																	&& imageView[moveFirstPosition
																			+ 1][moveSecondPosition].getImage().getUrl()
																			.contains("black_pawn")))) {

														isBlackEnPassant = true;
														enPassantFirstPosition = moveFirstPosition;
														enPassantSecondPosition = moveSecondPosition;

													}
													enPassantMovingPawnFirstPosition = moveFirstPosition + 1;
													enPassantMovingPawnSecondPosition = moveSecondPosition;

												}

											}
											// tıkladığı koordinatlar gidiyor

										} else
											SoundsSys.errorSound();
								break;
							}

						}

						if (!isGameFinished) {
							int howManyPieces = 0;
							;
							for (int i = 0; i < NUMBER_OF_COLUMS; i++) {

								for (int k = 0; k < NUMBER_OF_ROWS; k++) {
									if (imageView[i][k].getImage() != null) {
										howManyPieces++;
										if (howManyPieces > 2)
											break;
									}

								}

							}
							if (howManyPieces == 2) {
								timeline.stop();
								isGameFinished = true;
								WinScreen.showWinScreen("Draw!");
							}
						}

					}
				});

			}
		}

	}

	private void resetPossibleMoves() {
		int boardColorController = 0;
		for (int i = 0; i < NUMBER_OF_ROWS; i++) {
			if (i % 2 == 0) {
				boardColorController = 0;
			} else {
				boardColorController = 1;
			}
			for (int k = 0; k < NUMBER_OF_COLUMS; k++) {
				if (boardColorController % 2 == 0) {
					pane[i][k].setStyle("-fx-background-color: rgb(" + white.getRed() + "," + white.getGreen() + ", "
							+ white.getBlue() + ");");

				} else {
					pane[i][k].setStyle("-fx-background-color: rgb(" + brown.getRed() + "," + brown.getGreen() + ", "
							+ brown.getBlue() + ");");

				}
				// label.setStyle("-fx-font-weight: bold");
				boardColorController++;

			}

		}
		for (int[] coord : possibleMoves) {
			if (imageView[coord[0]][coord[1]].getImage() != null
					&& imageView[coord[0]][coord[1]].getImage().getUrl().contains("green_dot")) {
				imageView[coord[0]][coord[1]].setImage(null);
			}

		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		anchorPane.setStyle("-fx-background-color: rgb(64,64,64)");
		nameLbl.setStyle("-fx-text-fill: white");
		whiteTurn = true;
		timerTopLbl.setStyle("-fx-text-fill: white");
		timerBottomLbl.setStyle("-fx-text-fill: white");
		currentMoveStackController = 1;
		previousButton.setDisable(true);
		nextButton.setDisable(true);
		firstButton.setDisable(true);
		lastButton.setDisable(true);
		isGameFinished = false;
		timeWhite = 1000 * 60 * GAME_DURATION;
		timeBlack = 1000 * 60 * GAME_DURATION;
		allMoves = new Stack<ImageView[][]>();
		poppedMoves = new Stack<ImageView[][]>();
		if (perspective.equalsIgnoreCase("white")) {
			drawBoardWhitePerspective();
			drawPiecesWhitePerspective();
		} else {
			drawBoardBlackPerspective();
			drawPiecesBlackPerspective();
		}

		// drawBoardBlackPerspective();
		// drawPiecesBlackPerspective();

		setClickEvents();
		leftCastlingBlack = true;
		leftCastlingWhite = true;
		rightCastlingBlack = true;
		rightCastlingWhite = true;
		isWhiteEnPassant = false;
		isBlackEnPassant = false;
		isPossibleMoveClicked = false;
		isTimerRunning = false;
		isBlackMoved = false;
		isWhiteMoved = false;

		whiteKing = new King(perspective, rightCastlingWhite, leftCastlingWhite, rightCastlingBlack, leftCastlingBlack);
		blackKing = new King(perspective, rightCastlingWhite, leftCastlingWhite, rightCastlingBlack, leftCastlingBlack);

		changePerspectiveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				changePerspective();
			}
		});

		restartButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				restartGame();
			}
		});
		restartAndChangePerspectiveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				restartAndChangePerspective();
			}
		});

		previousButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				previousMove();
			}
		});
		firstButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				firstMove();
			}
		});
		lastButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				lastMove();
			}
		});
		nextButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				nextMove();
			}
		});

		ImageView[][] a = new ImageView[NUMBER_OF_COLUMS][NUMBER_OF_ROWS];
		for (int i = 0; i < NUMBER_OF_COLUMS; i++) {

			for (int k = 0; k < NUMBER_OF_ROWS; k++) {
				a[i][k] = new ImageView();
				// if(imageView[i][k].getImage() != null)
				a[i][k].setImage(imageView[i][k].getImage());
				// else
				// a[i][k].setImage(null);
			}
		}
		allMoves.add(a);

	}

	private void setGreenDots(int firstPosition, int secondPosition) {
		specialCoord = new ArrayList<int[]>();
		pane[firstPosition][secondPosition].setStyle(
				"-fx-background-color: rgb(" + green.getRed() + "," + green.getGreen() + ", " + green.getBlue() + ");");
		for (int[] coord : possibleMoves) {
			image = imageView[coord[0]][coord[1]].getImage();
			if (image == null) {
				image = new Image(
						"C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\green_dot.png");
				imageView[coord[0]][coord[1]].setImage(image);
			} else {
				pane[coord[0]][coord[1]].setStyle("-fx-background-color: rgb(" + green.getRed() + "," + green.getGreen()
						+ ", " + green.getBlue() + ");");
				specialCoord.add(coord);
			}

		}
	}

	private void moveToGreenDot(int moveFirstPosition, int moveSecondPosition) {
		Image im;
		image = imageView[selectedFirstPosition][selectedSecondPosition].getImage();
		if (Math.abs(moveFirstPosition - selectedFirstPosition) == 2 && image.getUrl().contains("king")) {
			if (perspective.equalsIgnoreCase("white")) {
				if (moveFirstPosition == 2 && moveSecondPosition == 0) {
					im = imageView[0][0].getImage();
					imageView[0][0].setImage(null);
					imageView[3][0].setImage(im);
				}
				if (moveFirstPosition == 6 && moveSecondPosition == 0) {
					im = imageView[7][0].getImage();
					imageView[7][0].setImage(null);
					imageView[5][0].setImage(im);
				}
				if (moveFirstPosition == 2 && moveSecondPosition == 7) {
					im = imageView[0][7].getImage();
					imageView[0][7].setImage(null);
					imageView[3][7].setImage(im);
				}
				if (moveFirstPosition == 6 && moveSecondPosition == 7) {
					im = imageView[7][7].getImage();
					imageView[7][7].setImage(null);
					imageView[5][7].setImage(im);
				}
			} else {
				if (moveFirstPosition == 1 && moveSecondPosition == 0) {
					im = imageView[0][0].getImage();
					imageView[0][0].setImage(null);
					imageView[2][0].setImage(im);
				}
				if (moveFirstPosition == 5 && moveSecondPosition == 0) {
					im = imageView[7][0].getImage();
					imageView[7][0].setImage(null);
					imageView[4][0].setImage(im);
				}
				if (moveFirstPosition == 1 && moveSecondPosition == 7) {
					im = imageView[0][7].getImage();
					imageView[0][7].setImage(null);
					imageView[2][7].setImage(im);
				}
				if (moveFirstPosition == 5 && moveSecondPosition == 7) {
					im = imageView[7][7].getImage();
					imageView[7][7].setImage(null);
					imageView[4][7].setImage(im);
				}
			}

		}
		if (perspective.equalsIgnoreCase("white")) {
			if (imageView[selectedFirstPosition][selectedSecondPosition].getImage() != null
					&& imageView[selectedFirstPosition][selectedSecondPosition].getImage().getUrl()
							.contains("white_pawn")
					&& isWhiteEnPassant && imageView[moveFirstPosition][moveSecondPosition].getImage() == null
					&& ((selectedFirstPosition - 1 == moveFirstPosition
							&& selectedSecondPosition - 1 == moveSecondPosition)
							|| (selectedFirstPosition + 1 == moveFirstPosition
									&& selectedSecondPosition - 1 == moveSecondPosition))) {

				imageView[enPassantFirstPosition][enPassantSecondPosition].setImage(null);
			} else if (imageView[selectedFirstPosition][selectedSecondPosition].getImage() != null
					&& imageView[selectedFirstPosition][selectedSecondPosition].getImage().getUrl()
							.contains("black_pawn")
					&& isBlackEnPassant && imageView[moveFirstPosition][moveSecondPosition].getImage() == null
					&& ((selectedFirstPosition - 1 == moveFirstPosition
							&& selectedSecondPosition + 1 == moveSecondPosition)
							|| (selectedFirstPosition + 1 == moveFirstPosition
									&& selectedSecondPosition + 1 == moveSecondPosition))) {

				imageView[enPassantFirstPosition][enPassantSecondPosition].setImage(null);
			}

		} else {
			if (imageView[selectedFirstPosition][selectedSecondPosition].getImage() != null
					&& imageView[selectedFirstPosition][selectedSecondPosition].getImage().getUrl()
							.contains("white_pawn")
					&& isWhiteEnPassant && imageView[moveFirstPosition][moveSecondPosition].getImage() == null
					&& ((selectedFirstPosition + 1 == moveFirstPosition
							&& selectedSecondPosition + 1 == moveSecondPosition)
							|| (selectedFirstPosition - 1 == moveFirstPosition
									&& selectedSecondPosition + 1 == moveSecondPosition))) {

				imageView[enPassantFirstPosition][enPassantSecondPosition].setImage(null);
			} else if (imageView[selectedFirstPosition][selectedSecondPosition].getImage() != null
					&& imageView[selectedFirstPosition][selectedSecondPosition].getImage().getUrl()
							.contains("black_pawn")
					&& isBlackEnPassant && imageView[moveFirstPosition][moveSecondPosition].getImage() == null
					&& ((selectedFirstPosition - 1 == moveFirstPosition
							&& selectedSecondPosition - 1 == moveSecondPosition)
							|| (selectedFirstPosition + 1 == moveFirstPosition
									&& selectedSecondPosition - 1 == moveSecondPosition))) {

				imageView[enPassantFirstPosition][enPassantSecondPosition].setImage(null);
			}
		}

		imageView[moveFirstPosition][moveSecondPosition].setImage(image);
		imageView[selectedFirstPosition][selectedSecondPosition].setImage(null);

		if (isWhiteMoved)
			isBlackMoved = true;
		isWhiteMoved = true;

		if (!isTimerRunning && isBlackMoved && isWhiteMoved) {

			setTimer();
		}

		whiteTurn = !whiteTurn;
		checkMate();
		isPat();
	}

	private void setTimer() {

		timeline = new Timeline(new KeyFrame(Duration.millis(1), ev -> {

			if (whiteTurn) {
				timeWhite--;
				if (perspective.equalsIgnoreCase("white")) {
					timerBottomLbl.setStyle("-fx-background-color: green;-fx-text-fill: white;");
					timerTopLbl.setStyle("-fx-text-fill: white;");
				} else {
					timerTopLbl.setStyle("-fx-background-color: green;-fx-text-fill: white;");
					timerBottomLbl.setStyle("-fx-text-fill: white;");
				}

				if (timeWhite % 1000 == 0) {
					timerString = TimerSys.timerLabelValueFormatted(timeWhite);
					if (perspective.equalsIgnoreCase("white"))
						timerBottomLbl.setText(timerString);
					else
						timerTopLbl.setText(timerString);
				}
				if (timeWhite == 0) {
					timeline.stop();
					isGameFinished = true;
					WinScreen.showWinScreen("Black wins!");
				}
			} else {
				timeBlack--;
				if (perspective.equalsIgnoreCase("white")) {
					timerTopLbl.setStyle("-fx-background-color: green;-fx-text-fill: white;");
					timerBottomLbl.setStyle("-fx-text-fill: white;");
				} else {
					timerBottomLbl.setStyle("-fx-background-color: green;-fx-text-fill: white;");
					timerTopLbl.setStyle("-fx-text-fill: white;");
				}
				if (timeBlack % 1000 == 0) {
					timerString = TimerSys.timerLabelValueFormatted(timeBlack);
					if (perspective.equalsIgnoreCase("white"))
						timerTopLbl.setText(timerString);
					else
						timerBottomLbl.setText(timerString);
				}
				if (timeBlack == 0) {
					timeline.stop();
					isGameFinished = true;
					WinScreen.showWinScreen("White wins!");
				}
			}

		}));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
		isTimerRunning = true;
	}

	private void moveToPiece(int moveFirstPosition, int moveSecondPosition) {
		Image im = imageView[selectedFirstPosition][selectedSecondPosition].getImage();
		King king = new King(perspective, rightCastlingWhite, leftCastlingWhite, rightCastlingBlack, leftCastlingBlack);
		king.setFirstPosition(moveFirstPosition);
		king.setSecondPosition(moveSecondPosition);
		image = imageView[moveFirstPosition][moveSecondPosition].getImage();
		imageView[selectedFirstPosition][selectedSecondPosition].setImage(image);
		imageView[moveFirstPosition][moveSecondPosition].setImage(null);

		ArrayList<int[]> checkedCoordinates = null;
		if (imageView[selectedFirstPosition][selectedSecondPosition].getImage().getUrl().contains("white_king")) {
			king.checkCoordinatesControlledByBlack(imageView);
			checkedCoordinates = king.getControlledByBlackList();

			if (checkedCoordinates != null)
				for (int i = 0; i < checkedCoordinates.size(); i++) {
					if (selectedFirstPosition == checkedCoordinates.get(i)[0]
							&& selectedSecondPosition == checkedCoordinates.get(i)[1]) {

						imageView[moveFirstPosition][moveSecondPosition].setImage(image);
						imageView[selectedFirstPosition][selectedSecondPosition].setImage(im);
						whiteTurn = !whiteTurn;
						break;
					}

				}

		} else if (imageView[selectedFirstPosition][selectedSecondPosition].getImage().getUrl()
				.contains("black_king")) {

			king.checkCoordinatesControlledByWhite(imageView);
			checkedCoordinates = king.getControlledByWhiteList();

			if (checkedCoordinates != null)
				for (int i = 0; i < checkedCoordinates.size(); i++) {
					if (selectedFirstPosition == checkedCoordinates.get(i)[0]
							&& selectedSecondPosition == checkedCoordinates.get(i)[1]) {

						imageView[moveFirstPosition][moveSecondPosition].setImage(image);
						imageView[selectedFirstPosition][selectedSecondPosition].setImage(im);
						whiteTurn = !whiteTurn;
						break;
					}

				}
		}

		whiteTurn = !whiteTurn;
		checkMate();
		isPat();
	}

	private boolean isAbleToMove() {
		// ImageView[][] checkMoveImageView = imageView.clone();
		int[] kingCoordinates = new int[2];
		King king = null;
		ArrayList<int[]> checkedCoordinates = null;
		Image im = imageView[moveFirstPosition][moveSecondPosition].getImage();
		image = imageView[selectedFirstPosition][selectedSecondPosition].getImage();
		imageView[selectedFirstPosition][selectedSecondPosition].setImage(im);

		// Image im = imageView[moveFirstPosition][moveSecondPosition].getImage();
		imageView[moveFirstPosition][moveSecondPosition].setImage(null);

		for (int i = 0; i < NUMBER_OF_ROWS; i++) {

			for (int k = 0; k < NUMBER_OF_COLUMS; k++) {
				if (imageView[i][k].getImage() != null && imageView[i][k].getImage().getUrl().contains("white_king")
						&& whiteTurn) {
					kingCoordinates[0] = i;
					kingCoordinates[1] = k;
					king = new King(perspective, rightCastlingWhite, leftCastlingWhite, rightCastlingBlack,
							leftCastlingBlack);
					king.setFirstPosition(i);
					king.setSecondPosition(k);
					king.checkCoordinatesControlledByBlack(imageView);
					checkedCoordinates = king.getControlledByBlackList();
					break;
				} else if (imageView[i][k].getImage() != null
						&& imageView[i][k].getImage().getUrl().contains("black_king") && !whiteTurn) {
					kingCoordinates[0] = i;
					kingCoordinates[1] = k;
					king = new King(perspective, rightCastlingWhite, leftCastlingWhite, rightCastlingBlack,
							leftCastlingBlack);
					king.setFirstPosition(i);
					king.setSecondPosition(k);
					king.checkCoordinatesControlledByWhite(imageView);
					checkedCoordinates = king.getControlledByWhiteList();
					break;
				}
			}

		}
		if (checkedCoordinates != null)
			for (int i = 0; i < checkedCoordinates.size(); i++) {
				if (kingCoordinates[0] == checkedCoordinates.get(i)[0]
						&& kingCoordinates[1] == checkedCoordinates.get(i)[1]) {
					imageView[selectedFirstPosition][selectedSecondPosition].setImage(image);
					imageView[moveFirstPosition][moveSecondPosition].setImage(im);

					return false;
				}

			}

		imageView[selectedFirstPosition][selectedSecondPosition].setImage(image);
		imageView[moveFirstPosition][moveSecondPosition].setImage(im);
		return true;
	}

	public boolean isAbleToMoveGreenDot() {
		image = imageView[selectedFirstPosition][selectedSecondPosition].getImage();
		imageView[selectedFirstPosition][selectedSecondPosition].setImage(null);
		imageView[moveFirstPosition][moveSecondPosition].setImage(image);
		int[] kingCoordinates = new int[2];
		King king = null;
		ArrayList<int[]> checkedCoordinates = null;
		for (int i = 0; i < NUMBER_OF_ROWS; i++) {

			for (int k = 0; k < NUMBER_OF_COLUMS; k++) {
				if (imageView[i][k].getImage() != null && imageView[i][k].getImage().getUrl().contains("white_king")
						&& whiteTurn) {
					kingCoordinates[0] = i;
					kingCoordinates[1] = k;
					king = new King(perspective, rightCastlingWhite, leftCastlingWhite, rightCastlingBlack,
							leftCastlingBlack);
					king.setFirstPosition(i);
					king.setSecondPosition(k);
					king.checkCoordinatesControlledByBlack(imageView);
					checkedCoordinates = king.getControlledByBlackList();
					break;
				} else if (imageView[i][k].getImage() != null
						&& imageView[i][k].getImage().getUrl().contains("black_king") && !whiteTurn) {
					kingCoordinates[0] = i;
					kingCoordinates[1] = k;
					king = new King(perspective, rightCastlingWhite, leftCastlingWhite, rightCastlingBlack,
							leftCastlingBlack);
					king.setFirstPosition(i);
					king.setSecondPosition(k);
					king.checkCoordinatesControlledByWhite(imageView);
					checkedCoordinates = king.getControlledByWhiteList();
					break;
				}
			}

		}
		if (checkedCoordinates != null)
			for (int i = 0; i < checkedCoordinates.size(); i++) {
				if (kingCoordinates[0] == checkedCoordinates.get(i)[0]
						&& kingCoordinates[1] == checkedCoordinates.get(i)[1]) {
					imageView[selectedFirstPosition][selectedSecondPosition].setImage(image);
					imageView[moveFirstPosition][moveSecondPosition].setImage(null);
					return false;
				}

			}
		imageView[moveFirstPosition][moveSecondPosition].setImage(null);
		imageView[selectedFirstPosition][selectedSecondPosition].setImage(image);
		return true;
	}

	public void checkCastling() {
		if (perspective.equalsIgnoreCase("white")) {
			if (whiteKing.getisMoved()) {
				rightCastlingWhite = false;
				leftCastlingWhite = false;

			}
			if (blackKing.getisMoved()) {
				rightCastlingBlack = false;
				leftCastlingBlack = false;
			}

			if (imageView[0][7].getImage() == null || !imageView[0][7].getImage().getUrl().contains("white_rook")) {
				leftCastlingWhite = false;
			}
			if (imageView[7][7].getImage() == null || !imageView[7][7].getImage().getUrl().contains("white_rook")) {
				rightCastlingWhite = false;
			}
			if (imageView[0][0].getImage() == null || !imageView[0][0].getImage().getUrl().contains("black_rook")) {
				leftCastlingBlack = false;
			}
			if (imageView[7][0].getImage() == null || !imageView[7][0].getImage().getUrl().contains("black_rook")) {
				rightCastlingBlack = false;
			}

		} else {
			if (whiteKing.getisMoved()) {
				rightCastlingBlack = false;
				leftCastlingBlack = false;

			}
			if (blackKing.getisMoved()) {
				rightCastlingWhite = false;
				leftCastlingWhite = false;
			}

			if (imageView[0][7].getImage() == null || !imageView[0][7].getImage().getUrl().contains("black_rook")) {
				leftCastlingBlack = false;
			}
			if (imageView[7][7].getImage() == null || !imageView[7][7].getImage().getUrl().contains("black_rook")) {
				rightCastlingBlack = false;
			}
			if (imageView[0][0].getImage() == null || !imageView[0][0].getImage().getUrl().contains("white_rook")) {
				leftCastlingWhite = false;
			}
			if (imageView[7][0].getImage() == null || !imageView[7][0].getImage().getUrl().contains("white_rook")) {
				rightCastlingWhite = false;
			}

		}
		whiteKing.setLeftCastlingWhite(leftCastlingWhite);
		whiteKing.setRightCastlingWhite(rightCastlingWhite);
		blackKing.setLeftCastlingWhite(leftCastlingBlack);
		blackKing.setRightCastlingWhite(rightCastlingBlack);

	}

	public void promotionScreen(String promotedPawnColor, boolean isDeafult) {

		Promotion promotion = new Promotion();
		promotion.switchToScenePromotion(promotedPawnColor);
		int selectedPiece = promotion.getSelectedPiece();
		Image i = null;
		switch (selectedPiece) {
		case 0:
			if (promotedPawnColor.equalsIgnoreCase("white")) {
				i = new Image("C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_quenn.png");

			} else {
				i = new Image("C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_quenn.png");

			}
			break;
		case 1:
			if (promotedPawnColor.equalsIgnoreCase("white")) {
				i = new Image("C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_rook.png");

			} else {
				i = new Image("C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_rook.png");

			}

			break;
		case 2:
			if (promotedPawnColor.equalsIgnoreCase("white")) {
				i = new Image("C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_knight.png");

			} else {
				i = new Image("C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_knight.png");

			}
			break;
		case 3:
			if (promotedPawnColor.equalsIgnoreCase("white")) {
				i = new Image("C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_bishop.png");

			} else {
				i = new Image("C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_bishop.png");

			}

			break;

		}
		if (!isDeafult)
			imageView[selectedFirstPosition][selectedSecondPosition].setImage(i);
		else
			imageView[moveFirstPosition][moveSecondPosition].setImage(i);
	}

	private void restartGame() {
		stopTimer();
		initialize(null, null);
	}

	private void restartAndChangePerspective() {
		if (perspective.equalsIgnoreCase("white")) {
			perspective = "black";
		} else {
			perspective = "white";
		}
		stopTimer();
		initialize(null, null);
	}

	private void stopTimer() {
		if (timeline != null)
			timeline.stop();
		timerBottomLbl.setText("10:00");
		timerTopLbl.setText("10:00");
	}

	private void saveMoves() {
		ImageView[][] sourceImageView = new ImageView[NUMBER_OF_ROWS][NUMBER_OF_COLUMS];
		Image sourceImage;
		for (int i = 0; i < NUMBER_OF_COLUMS; i++) {

			for (int k = 0; k < NUMBER_OF_ROWS; k++) {
				sourceImageView[i][k] = new ImageView();
				if (imageView[i][k].getImage() != null)
					sourceImage = new Image(imageView[i][k].getImage().getUrl());
				else
					sourceImage = null;
				sourceImageView[i][k].setImage(sourceImage);

			}

		}
		allMoves.add(sourceImageView);
	}

	private void previousMove() {

		ImageView[][] a;
		if (poppedMoves.isEmpty()) {
			a = allMoves.pop();
			poppedMoves.add(a);
			a = allMoves.pop();
		} else if (!poppedMoves.isEmpty() && currentMoveStackController == 2) {
			a = allMoves.pop();
			poppedMoves.add(a);
			a = allMoves.pop();
		}

		else
			a = allMoves.pop();
		poppedMoves.add(a);
		if (allMoves.isEmpty()) {
			previousButton.setDisable(true);
			firstButton.setDisable(true);
		}

		for (int i = 0; i < NUMBER_OF_COLUMS; i++) {

			for (int k = 0; k < NUMBER_OF_ROWS; k++) {
				imageView[i][k].setImage(a[i][k].getImage());

			}
		}
		nextButton.setDisable(false);
		lastButton.setDisable(false);
		currentMoveStackController = 1;
	}

	private void firstMove() {
		ImageView[][] a = null;
		while (!allMoves.isEmpty()) {
			a = allMoves.pop();
			poppedMoves.add(a);
		}
		for (int i = 0; i < NUMBER_OF_COLUMS; i++) {

			for (int k = 0; k < NUMBER_OF_ROWS; k++) {
				imageView[i][k].setImage(a[i][k].getImage());

			}
		}
		previousButton.setDisable(true);
		firstButton.setDisable(true);
		nextButton.setDisable(false);
		lastButton.setDisable(false);
	}

	private void lastMove() {
		ImageView[][] a = null;
		while (!poppedMoves.isEmpty()) {
			a = poppedMoves.pop();
			allMoves.add(a);
		}
		a = allMoves.lastElement();
		for (int i = 0; i < NUMBER_OF_COLUMS; i++) {

			for (int k = 0; k < NUMBER_OF_ROWS; k++) {
				imageView[i][k].setImage(a[i][k].getImage());

			}
		}
		nextButton.setDisable(true);
		lastButton.setDisable(true);
		previousButton.setDisable(false);
		firstButton.setDisable(false);
	}

	private void nextMove() {
		ImageView[][] a = null;
		if (currentMoveStackController == 1) {
			a = poppedMoves.pop();
			allMoves.add(a);
		}
		currentMoveStackController = 2;
		a = poppedMoves.pop();
		allMoves.add(a);

		for (int i = 0; i < NUMBER_OF_COLUMS; i++) {

			for (int k = 0; k < NUMBER_OF_ROWS; k++) {
				imageView[i][k].setImage(a[i][k].getImage());

			}
		}
		previousButton.setDisable(false);
		firstButton.setDisable(false);
		if (poppedMoves.isEmpty()) {
			nextButton.setDisable(true);
			lastButton.setDisable(true);
		}

	}

	private boolean isThreeMoveEqual() {
		ImageView[][] a = null;
		ImageView[][] b = null;
		boolean checkCondition1 = false;
		boolean checkCondition2 = false;
		boolean checkCondition3 = false;
		int howManyPieceEqual;
		if (allMoves.size() >= 7) {
			for (int i = 0; i < 7; i++) {
				a = allMoves.pop();
				poppedMoves.add(a);
			}
			a = poppedMoves.get(6);
			b = poppedMoves.get(2);
			howManyPieceEqual = checkInsideOfImageViews(a, b);
			if (howManyPieceEqual == 64) {
				checkCondition1 = true;
			}
			a = poppedMoves.get(1);
			b = poppedMoves.get(5);
			howManyPieceEqual = checkInsideOfImageViews(a, b);
			if (howManyPieceEqual == 64) {
				checkCondition2 = true;
			}
			a = poppedMoves.get(4);
			b = poppedMoves.get(0);
			howManyPieceEqual = checkInsideOfImageViews(a, b);
			if (howManyPieceEqual == 64) {
				checkCondition3 = true;
			}
			if (checkCondition1 && checkCondition2 && checkCondition3)
				return true;

		}
		while (!poppedMoves.isEmpty()) {
			allMoves.add(poppedMoves.pop());
		}

		return false;
	}

	private int checkInsideOfImageViews(ImageView[][] a, ImageView[][] b) {
		int howManyPieceEqual = 0;
		for (int i = 0; i < NUMBER_OF_ROWS; i++) {
			for (int k = 0; k < NUMBER_OF_COLUMS; k++) {
				if ((a[i][k].getImage() != null && b[i][k].getImage() != null)
						&& a[i][k].getImage().getUrl().equals(b[i][k].getImage().getUrl())) {
					howManyPieceEqual++;
				} else {
					if (a[i][k].getImage() == null && b[i][k].getImage() == null) {
						howManyPieceEqual++;
					}
				}
			}
		}

		return howManyPieceEqual;
	}

	private void checkMate() {
		int[] kingPosition = new int[2];
		ArrayList<int[]> controlledByBlackList = new ArrayList<int[]>();
		ArrayList<int[]> selectedPieceMoves = new ArrayList<int[]>();
		boolean isLost = false;
		Image piece;

		if (whiteTurn) {
			kingPosition = kingCoordinate("white");
			whiteKing.setFirstPosition(kingPosition[0]);
			whiteKing.setSecondPosition(kingPosition[1]);
			whiteKing.checkCoordinatesControlledByBlack(imageView);

			controlledByBlackList = whiteKing.getControlledByBlackList();
			if (isKingControlled(kingPosition, controlledByBlackList)) {

				for (int i = 0; i < NUMBER_OF_ROWS; i++) {
					for (int k = 0; k < NUMBER_OF_COLUMS; k++) {

						piece = imageView[i][k].getImage();
						// if(piece != null && piece.getUrl().contains("white"))
						// imageView[i][k].setImage(null);
						if (piece != null)
							switch (piece.getUrl()) {
							case "C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_bishop.png":
								Bishop bishop = new Bishop(i, k, "yes");
								selectedPieceMoves = bishop.showPossibleMoves(imageView);
								isLost = checkIfThereIsAnyPossibleMove(i, k, selectedPieceMoves, controlledByBlackList,
										kingPosition, piece);
								break;
							case "C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_knight.png":
								Knight knight = new Knight(i, k, "yes");
								selectedPieceMoves = knight.showPossibleMoves(imageView);
								isLost = checkIfThereIsAnyPossibleMove(i, k, selectedPieceMoves, controlledByBlackList,
										kingPosition, piece);
								break;
							case "C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_pawn.png":
								Pawn pawn = new Pawn(perspective, i, k);
								if (perspective.equalsIgnoreCase("white"))
									selectedPieceMoves = pawn.showPossibleMoves(imageView);
								else
									selectedPieceMoves = pawn.showPossibleMovesForBlackPerspective(imageView, i, k);
								isLost = checkIfThereIsAnyPossibleMove(i, k, selectedPieceMoves, controlledByBlackList,
										kingPosition, piece);
								break;
							case "C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_quenn.png":
								Quenn quenn = new Quenn(i, k);
								selectedPieceMoves = quenn.showPossibleMoves(imageView);
								isLost = checkIfThereIsAnyPossibleMove(i, k, selectedPieceMoves, controlledByBlackList,
										kingPosition, piece);
								break;
							case "C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_rook.png":
								Rook rook = new Rook("yes");
								rook.setFirstPosition(i);
								rook.setSecondPosition(k);
								selectedPieceMoves = rook.showPossibleMoves(imageView);
								isLost = checkIfThereIsAnyPossibleMove(i, k, selectedPieceMoves, controlledByBlackList,
										kingPosition, piece);
								break;
							/*
							 * case
							 * "C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_king.png"
							 * : King king = new King(perspective, rightCastlingWhite, leftCastlingWhite,
							 * rightCastlingBlack, leftCastlingBlack); king.setFirstPosition(i);
							 * king.setSecondPosition(k); imageView[i][k].setImage(piece);
							 * selectedPieceMoves = king.showPossibleMoves(imageView); isLost =
							 * checkIfThereIsAnyPossibleMove(i, k, selectedPieceMoves,
							 * controlledByBlackList, kingPosition, piece); break;
							 */

							}
						if (piece != null)
							imageView[i][k].setImage(piece);
						if (isLost) {
							break;
						}
					}

					if (isLost) {
						break;
					}
				}
				if (!isLost) {
					ArrayList<int[]> a = whiteKing.showPossibleMoves(imageView);

					if (a.isEmpty()) {
						isGameFinished = true;
						timeline.stop();
						WinScreen.showWinScreen("Black Wins!");
					}
				}

			}

		} else {

			kingPosition = kingCoordinate("black");
			blackKing.setFirstPosition(kingPosition[0]);
			blackKing.setSecondPosition(kingPosition[1]);
			blackKing.checkCoordinatesControlledByWhite(imageView);
			controlledByBlackList = blackKing.getControlledByWhiteList();
			if (isKingControlled(kingPosition, controlledByBlackList)) {

				for (int i = 0; i < NUMBER_OF_ROWS; i++) {
					for (int k = 0; k < NUMBER_OF_COLUMS; k++) {

						piece = imageView[i][k].getImage();
						// if(piece != null && piece.getUrl().contains("white"))
						// imageView[i][k].setImage(null);
						if (piece != null)
							switch (piece.getUrl()) {
							case "C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_bishop.png":
								Bishop bishop = new Bishop(i, k, "yes");
								selectedPieceMoves = bishop.showPossibleMoves(imageView);
								isLost = checkIfThereIsAnyPossibleMove(i, k, selectedPieceMoves, controlledByBlackList,
										kingPosition, piece);
								break;
							case "C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_knight.png":
								Knight knight = new Knight(i, k, "yes");
								selectedPieceMoves = knight.showPossibleMoves(imageView);
								isLost = checkIfThereIsAnyPossibleMove(i, k, selectedPieceMoves, controlledByBlackList,
										kingPosition, piece);
								break;
							case "C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_pawn.png":
								Pawn pawn = new Pawn(perspective, i, k);
								if (perspective.equalsIgnoreCase("white"))
									selectedPieceMoves = pawn.showPossibleMoves(imageView);
								else
									selectedPieceMoves = pawn.showPossibleMovesForBlackPerspective(imageView, i, k);
								isLost = checkIfThereIsAnyPossibleMove(i, k, selectedPieceMoves, controlledByBlackList,
										kingPosition, piece);
								break;
							case "C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_quenn.png":
								Quenn quenn = new Quenn(i, k);
								selectedPieceMoves = quenn.showPossibleMoves(imageView);
								isLost = checkIfThereIsAnyPossibleMove(i, k, selectedPieceMoves, controlledByBlackList,
										kingPosition, piece);
								break;
							case "C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_rook.png":
								Rook rook = new Rook("yes");
								rook.setFirstPosition(i);
								rook.setSecondPosition(k);
								selectedPieceMoves = rook.showPossibleMoves(imageView);
								isLost = checkIfThereIsAnyPossibleMove(i, k, selectedPieceMoves, controlledByBlackList,
										kingPosition, piece);
								break;
							/*
							 * case
							 * "C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_king.png"
							 * : King king = new King(perspective, rightCastlingWhite, leftCastlingWhite,
							 * rightCastlingBlack, leftCastlingBlack); king.setFirstPosition(i);
							 * king.setSecondPosition(k); imageView[i][k].setImage(piece);
							 * selectedPieceMoves = king.showPossibleMoves(imageView); isLost =
							 * checkIfThereIsAnyPossibleMove(i, k, selectedPieceMoves,
							 * controlledByBlackList, kingPosition, piece); break;
							 */

							}
						if (piece != null)
							imageView[i][k].setImage(piece);
						if (isLost) {
							break;
						}
					}

					if (isLost) {
						break;
					}
				}
				if (!isLost) {
					ArrayList<int[]> a = blackKing.showPossibleMoves(imageView);

					if (a.isEmpty()) {
						isGameFinished = true;
						timeline.stop();
						WinScreen.showWinScreen("White Wins!");
					}

				}

			}

		}

	}

	private int[] kingCoordinate(String color) {
		int[] coordinate = new int[2];
		for (int i = 0; i < NUMBER_OF_COLUMS; i++) {
			for (int k = 0; k < NUMBER_OF_ROWS; k++) {
				if (imageView[i][k].getImage() != null
						&& imageView[i][k].getImage().getUrl().contains(color + "_king")) {
					coordinate[0] = i;
					coordinate[1] = k;
					return coordinate;
				}

			}
		}

		return null;
	}

	private boolean isKingControlled(int[] kingPosition, ArrayList<int[]> controlledByBlackList) {
		for (int i = 0; i < controlledByBlackList.size(); i++) {
			if (controlledByBlackList.get(i)[0] == kingPosition[0]
					&& controlledByBlackList.get(i)[1] == kingPosition[1]) {
				return true;
			}

		}

		return false;
	}

	private boolean checkIfThereIsAnyPossibleMove(int i, int k, ArrayList<int[]> selectedPieceMoves,
			ArrayList<int[]> controlledByBlackList, int[] kingPosition, Image piece) {
		Image im;
		for (int m = 0; m < selectedPieceMoves.size(); m++) {
			if (selectedPieceMoves.get(m)[0] >= 0 && selectedPieceMoves.get(m)[0] <= 7
					&& selectedPieceMoves.get(m)[1] <= 7 && selectedPieceMoves.get(m)[1] >= 0) {
				if (imageView[selectedPieceMoves.get(m)[0]][selectedPieceMoves.get(m)[1]].getImage() == null) {
					imageView[selectedPieceMoves.get(m)[0]][selectedPieceMoves.get(m)[1]].setImage(piece);
					if (whiteTurn) {
						whiteKing.checkCoordinatesControlledByBlack(imageView);
						controlledByBlackList = whiteKing.getControlledByBlackList();
					} else {
						blackKing.checkCoordinatesControlledByWhite(imageView);
						controlledByBlackList = blackKing.getControlledByWhiteList();
					}

					imageView[selectedPieceMoves.get(m)[0]][selectedPieceMoves.get(m)[1]].setImage(null);
				} else {
					im = imageView[selectedPieceMoves.get(m)[0]][selectedPieceMoves.get(m)[1]].getImage();
					imageView[selectedPieceMoves.get(m)[0]][selectedPieceMoves.get(m)[1]].setImage(piece);
					if (whiteTurn) {
						whiteKing.checkCoordinatesControlledByBlack(imageView);
						controlledByBlackList = whiteKing.getControlledByBlackList();
					} else {
						blackKing.checkCoordinatesControlledByWhite(imageView);
						controlledByBlackList = blackKing.getControlledByWhiteList();
					}
					// imageView[i][k].setImage(piece);
					imageView[selectedPieceMoves.get(m)[0]][selectedPieceMoves.get(m)[1]].setImage(im);
				}
			}

			if (!isKingControlled(kingPosition, controlledByBlackList)) {

				return true;

			}

		}
		return false;
	}

	private void isPat() {
		int[] kingPosition = new int[2];
		ArrayList<int[]> controlledByList = new ArrayList<int[]>();
		boolean isThereAnyPossibleMove = false;
		Image image = null;
		ArrayList<int[]> moves = new ArrayList<int[]>();
		int firstMoveFirst = moveFirstPosition;
		int secondMoveSecond = moveSecondPosition;
		int firstSelectedFirst = selectedFirstPosition;
		int secondSelectedSecond = selectedSecondPosition;

		if (whiteTurn) {
			kingPosition = kingCoordinate("white");
			whiteKing.setFirstPosition(kingPosition[0]);
			whiteKing.setSecondPosition(kingPosition[1]);
			whiteKing.checkCoordinatesControlledByBlack(imageView);
			controlledByList = whiteKing.getControlledByBlackList();
			if (!isKingControlled(kingPosition, controlledByList)) {
				for (int i = 0; i < NUMBER_OF_ROWS; i++) {
					for (int k = 0; k < NUMBER_OF_COLUMS; k++) {
						image = imageView[i][k].getImage();
						if (image != null)
							switch (image.getUrl()) {
							case "C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_bishop.png":
								Bishop bishop = new Bishop(i, k, "yes");
								moves = bishop.showPossibleMoves(imageView);
								isThereAnyPossibleMove = checkAnyPossibleMove(i, k, moves);
								break;
							case "C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_knight.png":
								Knight knight = new Knight(i, k, "yes");
								moves = knight.showPossibleMoves(imageView);
								isThereAnyPossibleMove = checkAnyPossibleMove(i, k, moves);
								break;
							case "C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_king.png":
								King king = new King(perspective, rightCastlingWhite, leftCastlingWhite,
										rightCastlingBlack, leftCastlingBlack);
								king.setFirstPosition(i);
								king.setSecondPosition(k);
								moves = king.showPossibleMoves(imageView);
								isThereAnyPossibleMove = checkAnyPossibleMove(i, k, moves);
								break;
							case "C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_quenn.png":
								Quenn quenn = new Quenn(i, k);
								moves = quenn.showPossibleMoves(imageView);
								isThereAnyPossibleMove = checkAnyPossibleMove(i, k, moves);
								break;
							case "C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_rook.png":
								Rook rook = new Rook("yes");
								rook.setFirstPosition(i);
								rook.setSecondPosition(k);
								moves = rook.showPossibleMoves(imageView);
								isThereAnyPossibleMove = checkAnyPossibleMove(i, k, moves);
								break;
							case "C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\white_pawn.png":
								Pawn pawn = new Pawn(perspective, i, k);
								if (perspective.equalsIgnoreCase("white"))
									moves = pawn.showPossibleMoves(imageView);
								else
									moves = pawn.showPossibleMovesForBlackPerspective(imageView, i, k);
								isThereAnyPossibleMove = checkAnyPossibleMove(i, k, moves);
								break;

							}
						if (isThereAnyPossibleMove) {
							break;
						}
					}
					if (isThereAnyPossibleMove) {
						break;
					}

				}
				if (!isThereAnyPossibleMove) {
					isGameFinished = true;
					timeline.stop();
					WinScreen.showWinScreen("Pat!");
				}

			}

		} else {

			kingPosition = kingCoordinate("black");
			whiteKing.setFirstPosition(kingPosition[0]);
			whiteKing.setSecondPosition(kingPosition[1]);
			whiteKing.checkCoordinatesControlledByWhite(imageView);
			controlledByList = whiteKing.getControlledByWhiteList();
			if (!isKingControlled(kingPosition, controlledByList)) {
				for (int i = 0; i < NUMBER_OF_ROWS; i++) {
					for (int k = 0; k < NUMBER_OF_COLUMS; k++) {
						image = imageView[i][k].getImage();
						if (image != null)
							switch (image.getUrl()) {
							case "C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_bishop.png":
								Bishop bishop = new Bishop(i, k, "yes");
								moves = bishop.showPossibleMoves(imageView);
								isThereAnyPossibleMove = checkAnyPossibleMove(i, k, moves);
								break;
							case "C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_knight.png":
								Knight knight = new Knight(i, k, "yes");
								moves = knight.showPossibleMoves(imageView);
								isThereAnyPossibleMove = checkAnyPossibleMove(i, k, moves);
								break;
							case "C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_king.png":
								King king = new King(perspective, rightCastlingWhite, leftCastlingWhite,
										rightCastlingBlack, leftCastlingBlack);
								king.setFirstPosition(i);
								king.setSecondPosition(k);
								moves = king.showPossibleMoves(imageView);
								isThereAnyPossibleMove = checkAnyPossibleMove(i, k, moves);
								break;
							case "C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_quenn.png":
								Quenn quenn = new Quenn(i, k);
								moves = quenn.showPossibleMoves(imageView);
								isThereAnyPossibleMove = checkAnyPossibleMove(i, k, moves);
								break;
							case "C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_rook.png":
								Rook rook = new Rook("yes");
								rook.setFirstPosition(i);
								rook.setSecondPosition(k);
								moves = rook.showPossibleMoves(imageView);
								isThereAnyPossibleMove = checkAnyPossibleMove(i, k, moves);
								break;
							case "C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\images\\black_pawn.png":
								Pawn pawn = new Pawn(perspective, i, k);
								if (perspective.equalsIgnoreCase("white"))
									moves = pawn.showPossibleMoves(imageView);
								else
									moves = pawn.showPossibleMovesForBlackPerspective(imageView, i, k);
								isThereAnyPossibleMove = checkAnyPossibleMove(i, k, moves);
								break;

							}
						if (isThereAnyPossibleMove) {
							break;
						}
					}
					if (isThereAnyPossibleMove) {
						break;
					}

				}
				if (!isThereAnyPossibleMove) {
					isGameFinished = true;
					timeline.stop();
					WinScreen.showWinScreen("Pat!");
				}

			}

		}
		moveFirstPosition = firstMoveFirst;
		moveSecondPosition = secondMoveSecond;
		selectedFirstPosition = firstSelectedFirst;
		selectedSecondPosition = secondSelectedSecond;

	}

	private boolean checkAnyPossibleMove(int i, int k, ArrayList<int[]> moves) {
		for (int m = 0; m < moves.size(); m++) {
			if (imageView[moves.get(m)[0]][moves.get(m)[1]].getImage() == null) {
				selectedFirstPosition = i;
				selectedSecondPosition = k;
				moveFirstPosition = moves.get(m)[0];
				moveSecondPosition = moves.get(m)[1];
				if (isAbleToMoveGreenDot()) {
					return true;

				}
			} else {
				selectedFirstPosition = moves.get(m)[0];
				selectedSecondPosition = moves.get(m)[1];
				moveFirstPosition = i;
				moveSecondPosition = k;
				if (isAbleToMove()) {
					return true;

				}
			}
		}
		return false;
	}

	private void changePerspective() {

		ImageView[][] newImageView = new ImageView[NUMBER_OF_COLUMS][NUMBER_OF_ROWS];
		Image image;
		for (int i = 0; i < NUMBER_OF_ROWS; i++) {
			for (int k = 0; k < NUMBER_OF_COLUMS; k++) {
				image = imageView[i][k].getImage();
				newImageView[NUMBER_OF_ROWS - 1 - i][NUMBER_OF_COLUMS - 1 - k] = new ImageView();
				if (image == null) {
					newImageView[NUMBER_OF_ROWS - 1 - i][NUMBER_OF_COLUMS - 1 - k].setImage(null);
				} else {
					newImageView[NUMBER_OF_ROWS - 1 - i][NUMBER_OF_COLUMS - 1 - k].setImage(image);
				}

			}
		}
		if (perspective.equalsIgnoreCase("white")) {
			drawBoardBlackPerspective();
			perspective = "black";
		} else {
			drawBoardWhitePerspective();
			perspective = "white";
		}

		for (int i = 0; i < NUMBER_OF_ROWS; i++) {
			for (int k = 0; k < NUMBER_OF_COLUMS; k++) {
				image = newImageView[i][k].getImage();
				if (image != null)
					imageView[i][k].setImage(image);
				else
					imageView[i][k].setImage(null);

			}
		}

		setClickEvents();
		String temp;
		temp = timerBottomLbl.getText();
		timerBottomLbl.setText(timerTopLbl.getText());
		timerTopLbl.setText(temp);

	}

}
