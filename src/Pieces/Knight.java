package Pieces;

import java.util.ArrayList;

import javafx.scene.image.ImageView;

public class Knight extends Pieces {
	private ArrayList<int[]> possibleMoves;
	private int firstPosition;
	private int secondPosition;
	private int[] coordinates;
	private String check;

	public Knight(int firstPosition, int secondPosition, String check) {
		this.firstPosition = firstPosition;
		this.secondPosition = secondPosition;
		this.check = check;
	}

	@Override
	public ArrayList<int[]> showPossibleMoves(ImageView[][] imageview) {
		possibleMoves = new ArrayList<int[]>();
		coordinates = null;

		// 1 left 2 up
		if (firstPosition != 0 && secondPosition >= 2) {
			addCoordinates(firstPosition - 1, secondPosition - 2, imageview);
		}

		// 1 right 2 up
		if (firstPosition != 7 && secondPosition >= 2) {

			addCoordinates(firstPosition + 1, secondPosition - 2, imageview);
		}

		// 1 left 2 down
		if (secondPosition <= 5 && firstPosition != 0)
			addCoordinates(firstPosition - 1, secondPosition + 2, imageview);

		// 1 right 2 down
		if (secondPosition <= 5 && firstPosition != 7)
			addCoordinates(firstPosition + 1, secondPosition + 2, imageview);

		// 2 right 1 down
		if (secondPosition != 7 && firstPosition <= 5)
			addCoordinates(firstPosition + 2, secondPosition + 1, imageview);

		// 2 left 1 down
		if (secondPosition != 7 && firstPosition >= 2)
			addCoordinates(firstPosition - 2, secondPosition + 1, imageview);

		// 2 right 1 up
		if (secondPosition != 0 && firstPosition <= 5)
			addCoordinates(firstPosition + 2, secondPosition - 1, imageview);

		// 2 left 1 up
		if (secondPosition != 0 && firstPosition >= 2)
			addCoordinates(firstPosition - 2, secondPosition - 1, imageview);
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
	public void checkColors(ImageView[][] imageview, int[] coordinates) {
		if (!isEmpty(imageview, coordinates)) {
			if (imageview[firstPosition][secondPosition].getImage() != null
					&& imageview[firstPosition][secondPosition].getImage().getUrl().contains("white")) {
				if (!imageview[coordinates[0]][coordinates[1]].getImage().getUrl().contains("black")) {
					possibleMoves.remove(possibleMoves.size() - 1);
				}

			} else {
				if (!imageview[coordinates[0]][coordinates[1]].getImage().getUrl().contains("white")) {
					possibleMoves.remove(possibleMoves.size() - 1);
				}
			}

		}

	}

	@Override
	public void addCoordinates(int firstIndex, int secondIndex, ImageView[][] imageview) {
		coordinates = new int[2];
		coordinates[0] = firstIndex;
		coordinates[1] = secondIndex;
		possibleMoves.add(coordinates);
		if (check.equalsIgnoreCase("yes"))
			checkColors(imageview, coordinates);

	}

}
