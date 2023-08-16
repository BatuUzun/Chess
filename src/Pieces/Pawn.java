package Pieces;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Pawn extends Pieces {
	private String gamePerspective;
	private int firstPosition, secondPosition;
	private ArrayList<int[]> possibleMoves;
	private int[] coordinates;
	private boolean isWhiteEnPassant, isBlackEnPassant;
	private int enPassantFirstPosition, enPassantSecondPosition;
	private int enPassantMovingPawnFirstPosition, enPassantMovingPawnSecondPosition;

	public void setEnPassantMovingPawnFirstPosition(int enPassantMovingPawnFirstPosition) {
		this.enPassantMovingPawnFirstPosition = enPassantMovingPawnFirstPosition;
	}

	public void setEnPassantMovingPawnSecondPosition(int enPassantMovingPawnSecondPosition) {
		this.enPassantMovingPawnSecondPosition = enPassantMovingPawnSecondPosition;
	}

	public Pawn(String gamePerspective, int firstPosition, int secondPosition) {
		this.gamePerspective = gamePerspective;
		this.firstPosition = firstPosition;
		this.secondPosition = secondPosition;
		possibleMoves = new ArrayList<int[]>();
		isWhiteEnPassant = false;
		isBlackEnPassant = false;
		enPassantFirstPosition = -1;
		enPassantSecondPosition = -1;
	}

	public void setWhiteEnpassant(boolean isEnPassant) {
		this.isWhiteEnPassant = isEnPassant;
	}

	public void setBlackEnpassant(boolean isEnPassant) {
		this.isBlackEnPassant = isEnPassant;
	}

	public void setEnPassantFirstPosition(int enPassantFirstPosition) {
		this.enPassantFirstPosition = enPassantFirstPosition;
	}

	public void setEnPassantSecondPosition(int enPassantSecondPosition) {
		this.enPassantSecondPosition = enPassantSecondPosition;
	}

	@Override
	public ArrayList<int[]> showPossibleMoves(ImageView[][] imageview) {

		// possibleMoves = new ArrayList<int[]>();
		// coordinates = new int[2];
		if (imageview[firstPosition][secondPosition].getImage() != null
				&& imageview[firstPosition][secondPosition].getImage().getUrl().contains("black")) {
			if (firstPosition == 0) {
				if (imageview[firstPosition + 1][secondPosition + 1].getImage() != null
						&& imageview[firstPosition + 1][secondPosition + 1].getImage().getUrl().contains("white"))
					addCoordinates(firstPosition + 1, secondPosition + 1, imageview);
			} else if (firstPosition == 7) {
				if (imageview[firstPosition - 1][secondPosition + 1].getImage() != null
						&& imageview[firstPosition - 1][secondPosition + 1].getImage().getUrl().contains("white"))
					addCoordinates(firstPosition - 1, secondPosition + 1, imageview);
			} else {
				if (imageview[firstPosition - 1][secondPosition + 1].getImage() != null
						&& imageview[firstPosition - 1][secondPosition + 1].getImage().getUrl().contains("white"))
					addCoordinates(firstPosition - 1, secondPosition + 1, imageview);
				if (imageview[firstPosition + 1][secondPosition + 1].getImage() != null
						&& imageview[firstPosition + 1][secondPosition + 1].getImage().getUrl().contains("white"))
					addCoordinates(firstPosition + 1, secondPosition + 1, imageview);
			}
			if (secondPosition == 1) {
				for (int i = 1; i <= 2; i++) {
					coordinates = new int[2];
					coordinates[0] = firstPosition;
					coordinates[1] = secondPosition + i;
					if (isEmpty(imageview, coordinates)) {
						possibleMoves.add(coordinates);
					} else {
						break;
					}
				}
			} else {
				coordinates = new int[2];
				coordinates[0] = firstPosition;
				coordinates[1] = secondPosition + 1;
				if (isEmpty(imageview, coordinates)) {
					possibleMoves.add(coordinates);
				}

			}
			if (isBlackEnPassant && firstPosition == enPassantMovingPawnFirstPosition
					&& secondPosition == enPassantMovingPawnSecondPosition) {
				addCoordinates(enPassantFirstPosition, enPassantSecondPosition + 1, imageview);
			}
		} else {
			if (firstPosition == 0) {
				if (imageview[firstPosition + 1][secondPosition - 1].getImage() != null
						&& imageview[firstPosition + 1][secondPosition - 1].getImage().getUrl().contains("black"))
					addCoordinates(firstPosition + 1, secondPosition - 1, imageview);
			} else if (firstPosition == 7) {
				if(secondPosition-1 >= 0)
				if (imageview[firstPosition - 1][secondPosition - 1].getImage() != null
						&& imageview[firstPosition - 1][secondPosition - 1].getImage().getUrl().contains("black"))
					addCoordinates(firstPosition - 1, secondPosition - 1, imageview);
			} else {
				if(secondPosition-1 >= 0) {
					if (imageview[firstPosition - 1][secondPosition - 1].getImage() != null
							&& imageview[firstPosition - 1][secondPosition - 1].getImage().getUrl().contains("black"))
						addCoordinates(firstPosition - 1, secondPosition - 1, imageview);
					if (imageview[firstPosition + 1][secondPosition - 1].getImage() != null
							&& imageview[firstPosition + 1][secondPosition - 1].getImage().getUrl().contains("black"))
						addCoordinates(firstPosition + 1, secondPosition - 1, imageview);
				}
			
			}
			if (secondPosition == 6) {
				for (int i = 1; i <= 2; i++) {
					coordinates = new int[2];
					coordinates[0] = firstPosition;
					coordinates[1] = secondPosition - i;
					if (isEmpty(imageview, coordinates)) {
						possibleMoves.add(coordinates);
					} else {
						break;
					}
				}
			} else {
				coordinates = new int[2];
				coordinates[0] = firstPosition;
				coordinates[1] = secondPosition - 1;
				if (isEmpty(imageview, coordinates)) {
					possibleMoves.add(coordinates);
				}

			}

			if (isWhiteEnPassant && firstPosition == enPassantMovingPawnFirstPosition
					&& secondPosition == enPassantMovingPawnSecondPosition) {
				addCoordinates(enPassantFirstPosition, enPassantSecondPosition - 1, imageview);
			}

		}

		/*
		 * for (int i = 0; i < possibleMoves.size(); i++) {
		 * System.out.println(possibleMoves.get(i)[0] + "        " +
		 * possibleMoves.get(i)[1] + "         " + possibleMoves.size());
		 * 
		 * }
		 */

		return possibleMoves;

	}

	public ArrayList<int[]> showPossibleMovesForBlackPerspective(ImageView[][] imageview, int firstPosition,
			int secondPosition) {

		// possibleMoves = new ArrayList<int[]>();
		// coordinates = new int[2];
		if (imageview[firstPosition][secondPosition].getImage() != null && imageview[firstPosition][secondPosition].getImage().getUrl().contains("white")) {
			if (firstPosition == 0) {
				if (imageview[firstPosition + 1][secondPosition + 1].getImage() != null
						&& imageview[firstPosition + 1][secondPosition + 1].getImage().getUrl().contains("black"))
					addCoordinates(firstPosition + 1, secondPosition + 1, imageview);
			} else if (firstPosition == 7) {
				if (imageview[firstPosition - 1][secondPosition + 1].getImage() != null
						&& imageview[firstPosition - 1][secondPosition + 1].getImage().getUrl().contains("black"))
					addCoordinates(firstPosition - 1, secondPosition + 1, imageview);
			} else {
				if (imageview[firstPosition - 1][secondPosition + 1].getImage() != null
						&& imageview[firstPosition - 1][secondPosition + 1].getImage().getUrl().contains("black"))
					addCoordinates(firstPosition - 1, secondPosition + 1, imageview);
				if (imageview[firstPosition + 1][secondPosition + 1].getImage() != null
						&& imageview[firstPosition + 1][secondPosition + 1].getImage().getUrl().contains("black"))
					addCoordinates(firstPosition + 1, secondPosition + 1, imageview);
			}
			if (secondPosition == 1) {
				for (int i = 1; i <= 2; i++) {
					coordinates = new int[2];
					coordinates[0] = firstPosition;
					coordinates[1] = secondPosition + i;
					if (isEmpty(imageview, coordinates)) {
						possibleMoves.add(coordinates);
					} else {
						break;
					}
				}
			} else {
				coordinates = new int[2];
				coordinates[0] = firstPosition;
				coordinates[1] = secondPosition + 1;
				if (isEmpty(imageview, coordinates)) {
					possibleMoves.add(coordinates);
				}

			}
			if (isWhiteEnPassant && firstPosition == enPassantMovingPawnFirstPosition
					&& secondPosition == enPassantMovingPawnSecondPosition) {
				addCoordinates(enPassantFirstPosition, enPassantSecondPosition + 1, imageview);
			}
		} else {
			if (firstPosition == 0) {
				if (imageview[firstPosition + 1][secondPosition - 1].getImage() != null
						&& imageview[firstPosition + 1][secondPosition - 1].getImage().getUrl().contains("white"))
					addCoordinates(firstPosition + 1, secondPosition - 1, imageview);
			} else if (firstPosition == 7) {
				if (imageview[firstPosition - 1][secondPosition - 1].getImage() != null
						&& imageview[firstPosition - 1][secondPosition - 1].getImage().getUrl().contains("white"))
					addCoordinates(firstPosition - 1, secondPosition - 1, imageview);
			} else {
				if (imageview[firstPosition - 1][secondPosition - 1].getImage() != null
						&& imageview[firstPosition - 1][secondPosition - 1].getImage().getUrl().contains("white"))
					addCoordinates(firstPosition - 1, secondPosition - 1, imageview);
				if (imageview[firstPosition + 1][secondPosition - 1].getImage() != null
						&& imageview[firstPosition + 1][secondPosition - 1].getImage().getUrl().contains("white"))
					addCoordinates(firstPosition + 1, secondPosition - 1, imageview);
			}
			if (secondPosition == 6) {
				for (int i = 1; i <= 2; i++) {
					coordinates = new int[2];
					coordinates[0] = firstPosition;
					coordinates[1] = secondPosition - i;
					if (isEmpty(imageview, coordinates)) {
						possibleMoves.add(coordinates);
					} else {
						break;
					}
				}
			} else {
				coordinates = new int[2];
				coordinates[0] = firstPosition;
				coordinates[1] = secondPosition - 1;
				if (isEmpty(imageview, coordinates)) {
					possibleMoves.add(coordinates);
				}

			}
			if (isBlackEnPassant && firstPosition == enPassantMovingPawnFirstPosition
					&& secondPosition == enPassantMovingPawnSecondPosition) {
				addCoordinates(enPassantFirstPosition, enPassantSecondPosition - 1, imageview);
			}
		}

		/*
		 * for (int i = 0; i < possibleMoves.size(); i++) {
		 * System.out.println(possibleMoves.get(i)[0] + "        " +
		 * possibleMoves.get(i)[1] + "         " + possibleMoves.size());
		 * 
		 * }
		 */
		return possibleMoves;

	}

	@Override
	public void addCoordinates(int firstIndex, int secondIndex, ImageView[][] imageview) {
		coordinates = new int[2];
		coordinates[0] = firstIndex;
		coordinates[1] = secondIndex;
		possibleMoves.add(coordinates);

	}

	@Override
	public void checkColors(ImageView[][] imageview, int[] coordinates) {}

}
