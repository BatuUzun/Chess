package Pieces;

import java.util.ArrayList;

import javafx.scene.image.ImageView;

public class Bishop extends Pieces {
	private ArrayList<int[]> possibleMoves;
	private int firstPosition;
	private int secondPosition;
	private int[] coordinates;
	private String check;

	public Bishop(int firstPosition, int secondPosition, String check) {
		this.firstPosition = firstPosition;
		this.secondPosition = secondPosition;
		this.check = check;
	}

	@Override
	public ArrayList<int[]> showPossibleMoves(ImageView[][] imageview) {
		possibleMoves = new ArrayList<int[]>();
		coordinates = null;

		// right bottom
		if (firstPosition != 7 && secondPosition != 7) {
			for (int i = 1; i < 8; i++) {
				if (firstPosition + i <= 7 && secondPosition + i <= 7) {
					addCoordinates(firstPosition + i, secondPosition + i, imageview);
					if (!isEmpty(imageview, coordinates)) {
						if (check.equalsIgnoreCase("yes"))
							checkColors(imageview, coordinates);

						break;
					}
				}

			}
		}

		// right top
		if (firstPosition != 7 && secondPosition != 0) {
			for (int i = 1; i < 8; i++) {
				if (firstPosition + i <= 7 && secondPosition - i >= 0) {
					addCoordinates(firstPosition + i, secondPosition - i, imageview);
					if (!isEmpty(imageview, coordinates)) {
						if (check.equalsIgnoreCase("yes"))
							checkColors(imageview, coordinates);

						break;
					}
				}

			}
		}

		// left bottom
		if (firstPosition != 0 && secondPosition != 7) {
			for (int i = 1; i < 8; i++) {
				if (firstPosition - i >= 0 && secondPosition + i <= 7) {
					addCoordinates(firstPosition - i, secondPosition + i, imageview);
					if (!isEmpty(imageview, coordinates)) {
						if (check.equalsIgnoreCase("yes"))
							checkColors(imageview, coordinates);

						break;
					}
				}

			}
		}

		// left top
		if (firstPosition != 0 && secondPosition != 0) {
			for (int i = 1; i < 8; i++) {
				if (firstPosition - i >= 0 && secondPosition - i >= 0) {
					addCoordinates(firstPosition - i, secondPosition - i, imageview);
					if (!isEmpty(imageview, coordinates)) {
						if (check.equalsIgnoreCase("yes"))
							checkColors(imageview, coordinates);

						break;
					}
				}

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

	private void checkColors(ImageView[][] imageview, int[] coordinates) {
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

	public ArrayList<int[]> checkedCoordinates(ImageView[][] imageview) {

		possibleMoves = new ArrayList<int[]>();
		coordinates = null;

		// right bottom
		if (firstPosition != 7 && secondPosition != 7) {
			for (int i = 1; i < 8; i++) {
				if (firstPosition + i <= 7 && secondPosition + i <= 7) {
					addCoordinates(firstPosition + i, secondPosition + i, imageview);
					if (!isEmpty(imageview, coordinates)) {

						if (imageview[firstPosition][secondPosition].getImage().getUrl().contains("white")) {
							if (imageview[coordinates[0]][coordinates[1]].getImage() != null
									&& !imageview[coordinates[0]][coordinates[1]].getImage().getUrl()
											.contains("black_king")) {
								// checkColors(imageview, coordinates);
								break;
							}

						} else {
							if (imageview[coordinates[0]][coordinates[1]].getImage() != null
									&& !imageview[coordinates[0]][coordinates[1]].getImage().getUrl()
											.contains("white_king")) {
								// checkColors(imageview, coordinates);
								break;
							}

						}
					}
				}

			}
		}

		// right top
		if (firstPosition != 7 && secondPosition != 0) {
			for (int i = 1; i < 8; i++) {
				if (firstPosition + i <= 7 && secondPosition - i >= 0) {
					addCoordinates(firstPosition + i, secondPosition - i, imageview);
					if (!isEmpty(imageview, coordinates)) {

						if (imageview[firstPosition][secondPosition].getImage().getUrl().contains("white")) {
							if (imageview[coordinates[0]][coordinates[1]].getImage() != null
									&& !imageview[coordinates[0]][coordinates[1]].getImage().getUrl()
											.contains("black_king")) {
								// checkColors(imageview, coordinates);
								break;
							}

						} else {
							if (imageview[coordinates[0]][coordinates[1]].getImage() != null
									&& !imageview[coordinates[0]][coordinates[1]].getImage().getUrl()
											.contains("white_king")) {
								// checkColors(imageview, coordinates);
								break;
							}

						}
					}
				}

			}
		}

		// left bottom
		if (firstPosition != 0 && secondPosition != 7) {
			for (int i = 1; i < 8; i++) {
				if (firstPosition - i >= 0 && secondPosition + i <= 7) {
					addCoordinates(firstPosition - i, secondPosition + i, imageview);
					if (!isEmpty(imageview, coordinates)) {

						if (imageview[firstPosition][secondPosition].getImage().getUrl().contains("white")) {
							if (imageview[coordinates[0]][coordinates[1]].getImage() != null
									&& !imageview[coordinates[0]][coordinates[1]].getImage().getUrl()
											.contains("black_king")) {
								// checkColors(imageview, coordinates);
								break;
							}

						} else {
							if (imageview[coordinates[0]][coordinates[1]].getImage() != null
									&& !imageview[coordinates[0]][coordinates[1]].getImage().getUrl()
											.contains("white_king")) {
								// checkColors(imageview, coordinates);
								break;
							}

						}
					}
				}

			}
		}

		// left top
		if (firstPosition != 0 && secondPosition != 0) {
			for (int i = 1; i < 8; i++) {
				if (firstPosition - i >= 0 && secondPosition - i >= 0) {
					addCoordinates(firstPosition - i, secondPosition - i, imageview);
					if (!isEmpty(imageview, coordinates)) {

						if (imageview[firstPosition][secondPosition].getImage().getUrl().contains("white")) {
							if (imageview[coordinates[0]][coordinates[1]].getImage() != null
									&& !imageview[coordinates[0]][coordinates[1]].getImage().getUrl()
											.contains("black_king")) {
								// checkColors(imageview, coordinates);
								break;
							}

						} else {
							if (imageview[coordinates[0]][coordinates[1]].getImage() != null
									&& !imageview[coordinates[0]][coordinates[1]].getImage().getUrl()
											.contains("white_king")) {
								// checkColors(imageview, coordinates);
								break;
							}

						}
					}
				}

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

}
