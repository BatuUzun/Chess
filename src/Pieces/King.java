package Pieces;

import java.util.ArrayList;


import javafx.scene.image.ImageView;

public class King extends Pieces {
	ArrayList<int[]> controlledByWhiteList, controlledByBlackList, possibleMoves;
	ArrayList<int[]> temp;
	private int firstPosition;
	private int secondPosition;
	private int[] coordinates;
	private String perspective;
	private boolean isMoved;
	private boolean rightCastlingWhite, leftCastlingWhite, rightCastlingBlack, leftCastlingBlack;

	public King(String perspective, boolean rightCastlingWhite, boolean leftCastlingWhite, boolean rightCastlingBlack,
			boolean leftCastlingBlack) {
		controlledByWhiteList = null;
		controlledByBlackList = null;

		coordinates = new int[2];
		this.perspective = perspective;
		this.rightCastlingBlack = rightCastlingBlack;
		this.rightCastlingWhite = rightCastlingWhite;
		this.leftCastlingBlack = leftCastlingBlack;
		this.leftCastlingWhite = leftCastlingWhite;
		this.isMoved = false;
	}

	public boolean getisMoved() {
		return isMoved;
	}

	public void setRightCastlingWhite(boolean rightCastlingWhite) {
		this.rightCastlingWhite = rightCastlingWhite;
	}

	public void setLeftCastlingWhite(boolean leftCastlingWhite) {
		this.leftCastlingWhite = leftCastlingWhite;
	}

	public void setRightCastlingBlack(boolean rightCastlingBlack) {
		this.rightCastlingBlack = rightCastlingBlack;
	}

	public void setLeftCastlingBlack(boolean leftCastlingBlack) {
		this.leftCastlingBlack = leftCastlingBlack;
	}

	public void setFirstPosition(int firstPosition) {
		this.firstPosition = firstPosition;
	}

	public void setSecondPosition(int secondPosition) {
		this.secondPosition = secondPosition;
	}

	public ArrayList<int[]> getControlledByWhiteList() {
		return controlledByWhiteList;
	}

	public ArrayList<int[]> getControlledByBlackList() {
		return controlledByBlackList;
	}

	public void setIsMoved() {
		isMoved = true;
	}

	@Override
	public ArrayList<int[]> showPossibleMoves(ImageView[][] imageview) {
		possibleMoves = new ArrayList<int[]>();
		coordinates = new int[2];
		if (imageview[firstPosition][secondPosition].getImage() != null
				&& imageview[firstPosition][secondPosition].getImage().getUrl().contains("white_king")) {
			checkCoordinatesControlledByBlack(imageview);
		} else {
			checkCoordinatesControlledByWhite(imageview);
		}
		// right

		if (firstPosition != 7) {
			addCoordinates(firstPosition + 1, secondPosition, imageview);
			possibleMoves.add(coordinates);
			if (!isEmpty(imageview, coordinates)) {
				checkColors(imageview, coordinates);
			}
		}
		// left
		if (firstPosition != 0) {
			addCoordinates(firstPosition - 1, secondPosition, imageview);
			possibleMoves.add(coordinates);
			if (!isEmpty(imageview, coordinates)) {
				checkColors(imageview, coordinates);
			}
		}
		// bottom
		if (secondPosition != 7) {
			addCoordinates(firstPosition, secondPosition + 1, imageview);
			possibleMoves.add(coordinates);
			if (!isEmpty(imageview, coordinates)) {
				checkColors(imageview, coordinates);
			}
		}
		// top
		if (secondPosition != 0) {
			addCoordinates(firstPosition, secondPosition - 1, imageview);
			possibleMoves.add(coordinates);
			if (!isEmpty(imageview, coordinates)) {
				checkColors(imageview, coordinates);
			}
		}
		// right top
		if (firstPosition != 7 && secondPosition != 0) {
			addCoordinates(firstPosition + 1, secondPosition - 1, imageview);
			possibleMoves.add(coordinates);
			if (!isEmpty(imageview, coordinates)) {
				checkColors(imageview, coordinates);
			}
		}
		// right bottom
		if (firstPosition != 7 && secondPosition != 7) {
			addCoordinates(firstPosition + 1, secondPosition + 1, imageview);
			possibleMoves.add(coordinates);
			if (!isEmpty(imageview, coordinates)) {
				checkColors(imageview, coordinates);
			}
		}
		// left top
		if (firstPosition != 0 && secondPosition != 0) {
			addCoordinates(firstPosition - 1, secondPosition - 1, imageview);
			possibleMoves.add(coordinates);
			if (!isEmpty(imageview, coordinates)) {
				checkColors(imageview, coordinates);
			}
		}
		// left bottom
		if (firstPosition != 0 && secondPosition != 7) {
			addCoordinates(firstPosition - 1, secondPosition + 1, imageview);
			possibleMoves.add(coordinates);
			if (!isEmpty(imageview, coordinates)) {
				checkColors(imageview, coordinates);
			}
		}
		boolean isValidCastling = true;
		if (controlledByBlackList != null) {

			for (int i = 0; i < possibleMoves.size(); i++) {

				for (int[] b : controlledByBlackList) {

					if (possibleMoves.get(i)[0] == b[0] && possibleMoves.get(i)[1] == b[1]) {
						possibleMoves.remove(i);
						i--;
						break;
					}

				}
			}

			if (perspective.equalsIgnoreCase("white")) {
				if (leftCastlingWhite) {
					
					if (imageview[3][7].getImage() == null && imageview[2][7].getImage() == null
							&& imageview[1][7].getImage() == null) {
						for (int[] b : controlledByBlackList) {
							if ((b[0] == 2 && b[1] == 7) || (b[0] == 3 && b[1] == 7)
									|| (b[0] == firstPosition && b[1] == secondPosition)) {
								
								isValidCastling = false;
								break;
							}

						}

						if (isValidCastling) {
							
							addCoordinates(2, 7, imageview);
							possibleMoves.add(coordinates);
						}
					}

				}
				isValidCastling = true;
				if (rightCastlingWhite) {
					
					if (imageview[5][7].getImage() == null && imageview[6][7].getImage() == null) {
						for (int[] b : controlledByBlackList) {
							if ((b[0] == 5 && b[1] == 7) || (b[0] == 6 && b[1] == 7)
									|| (b[0] == firstPosition && b[1] == secondPosition)) {
								
								isValidCastling = false;
								break;
							}

						}

						if (isValidCastling) {
							
							addCoordinates(6, 7, imageview);
							possibleMoves.add(coordinates);
						}
					}

				}

			} else {
				if (leftCastlingWhite) {
					
					if (imageview[2][0].getImage() == null && imageview[1][0].getImage() == null) {
						for (int[] b : controlledByBlackList) {
							if ((b[0] == 1 && b[1] == 0) || (b[0] == 2 && b[1] == 0)
									|| (b[0] == firstPosition && b[1] == secondPosition)) {
								
								isValidCastling = false;
								break;
							}

						}

						if (isValidCastling) {
							
							addCoordinates(1, 0, imageview);
							possibleMoves.add(coordinates);
						}
					}

				}
				if (rightCastlingWhite) {
					
					if (imageview[5][0].getImage() == null && imageview[6][0].getImage() == null) {
						for (int[] b : controlledByBlackList) {
							if ((b[0] == 5 && b[1] == 0) || (b[0] == 6 && b[1] == 0)
									|| (b[0] == firstPosition && b[1] == secondPosition)) {
								
								isValidCastling = false;
								break;
							}

						}

						if (isValidCastling) {
							
							addCoordinates(5, 0, imageview);
							possibleMoves.add(coordinates);
						}
					}

				}

			}

		}

		else {
			for (int i = 0; i < possibleMoves.size(); i++) {

				for (int[] b : controlledByWhiteList) {
					if (possibleMoves.get(i)[0] == b[0] && possibleMoves.get(i)[1] == b[1]) {
						possibleMoves.remove(i);
						i--;
						break;
					}

				}
			}
			if (perspective.equalsIgnoreCase("white")) {
				if (leftCastlingBlack) {
					
					if (imageview[3][0].getImage() == null && imageview[2][0].getImage() == null
							&& imageview[1][0].getImage() == null) {
						for (int[] b : controlledByWhiteList) {
							if ((b[0] == 2 && b[1] == 0) || (b[0] == 3 && b[1] == 0)
									|| (b[0] == firstPosition && b[1] == secondPosition)) {
								
								isValidCastling = false;
								break;
							}

						}

						if (isValidCastling) {
							
							addCoordinates(2, 0, imageview);
							possibleMoves.add(coordinates);
						}
					}

				}
				isValidCastling = true;
				if (rightCastlingBlack) {

					
					if (imageview[5][0].getImage() == null && imageview[6][0].getImage() == null) {
						for (int[] b : controlledByWhiteList) {
							if ((b[0] == 5 && b[1] == 0) || (b[0] == 6 && b[1] == 0)
									|| (b[0] == firstPosition && b[1] == secondPosition)) {
								
								isValidCastling = false;
								break;
							}

						}

						if (isValidCastling) {
							
							addCoordinates(6, 0, imageview);
							possibleMoves.add(coordinates);
						}
					}

				}

			} else {
				if (leftCastlingBlack) {
					
					if (imageview[2][7].getImage() == null && imageview[1][7].getImage() == null) {
						for (int[] b : controlledByWhiteList) {
							if ((b[0] == 2 && b[1] == 7) || (b[0] == 1 && b[1] == 7)
									|| (b[0] == firstPosition && b[1] == secondPosition)) {
								
								isValidCastling = false;
								break;
							}

						}

						if (isValidCastling) {
							
							addCoordinates(1, 7, imageview);
							possibleMoves.add(coordinates);
						}
					}

				}
				if (rightCastlingBlack) {
					
					if (imageview[4][7].getImage() == null && imageview[5][7].getImage() == null
							&& imageview[6][7].getImage() == null) {
						for (int[] b : controlledByWhiteList) {
							if ((b[0] == 4 && b[1] == 7) || (b[0] == 5 && b[1] == 7)
									|| (b[0] == firstPosition && b[1] == secondPosition)) {
								
								isValidCastling = false;
								break;
							}

						}

						if (isValidCastling) {
							
							addCoordinates(5, 7, imageview);
							possibleMoves.add(coordinates);
						}
					}

				}

			}

		}

		return possibleMoves;
	}

	@Override
	public void addCoordinates(int firstIndex, int secondIndex, ImageView[][] imageview) {
		coordinates = new int[2];
		coordinates[0] = firstIndex;
		coordinates[1] = secondIndex;

	}

	public void checkCoordinatesControlledByWhite(ImageView[][] imageview) {
		controlledByWhiteList = new ArrayList<int[]>();

		for (int i = 0; i < 8; i++) {

			for (int k = 0; k < 8; k++) {

				if (imageview[i][k].getImage() != null
						&& imageview[i][k].getImage().getUrl().contains("white_knight")) {
					Knight knight = new Knight(i, k, "no");
					temp = knight.showPossibleMoves(imageview);
					controlledByWhiteList.addAll(temp);
				} else if (imageview[i][k].getImage() != null
						&& imageview[i][k].getImage().getUrl().contains("white_bishop")) {
					Bishop bishop = new Bishop(i, k, "no");
					temp = bishop.checkedCoordinates(imageview);
					controlledByWhiteList.addAll(temp);
				} else if (imageview[i][k].getImage() != null
						&& imageview[i][k].getImage().getUrl().contains("white_quenn")) {
					Quenn quenn = new Quenn(i, k);
					temp = quenn.checkedCoordinates(imageview);
					controlledByWhiteList.addAll(temp);
				} else if (imageview[i][k].getImage() != null
						&& imageview[i][k].getImage().getUrl().contains("white_rook")) {
					Rook rook = new Rook("no");
					rook.setFirstPosition(i);
					rook.setSecondPosition(k);

					temp = rook.checkedCoordinates(imageview);
					controlledByWhiteList.addAll(temp);
				} else if (imageview[i][k].getImage() != null
						&& imageview[i][k].getImage().getUrl().contains("white_pawn")) {
					if (perspective.equalsIgnoreCase("white")) {
						if (i == 0 && k != 0) {
							addCoordinates(i + 1, k - 1, imageview);
							controlledByWhiteList.add(coordinates);
						} else if (i == 7 && k != 0) {
							addCoordinates(i - 1, k - 1, imageview);
							controlledByWhiteList.add(coordinates);
						} else if (k != 0) {
							addCoordinates(i + 1, k - 1, imageview);
							controlledByWhiteList.add(coordinates);
							addCoordinates(i - 1, k - 1, imageview);
							controlledByWhiteList.add(coordinates);
						}
					} else {
						if (i == 0 && k != 7) {
							addCoordinates(i + 1, k + 1, imageview);
							controlledByWhiteList.add(coordinates);
						} else if (i == 7 && k != 7) {
							addCoordinates(i - 1, k + 1, imageview);
							controlledByWhiteList.add(coordinates);
						} else if (k != 7) {
							addCoordinates(i + 1, k + 1, imageview);
							controlledByWhiteList.add(coordinates);
							addCoordinates(i - 1, k + 1, imageview);
							controlledByWhiteList.add(coordinates);
						}
					}
				} else if (imageview[i][k].getImage() != null
						&& imageview[i][k].getImage().getUrl().contains("white_king")) {
					/*
					 * King king = new King(perspective, i, k, "no"); temp =
					 * king.showPossibleMoves(imageview); controlledByWhiteList.addAll(temp);
					 */

					// right
					if (i != 7) {
						addCoordinates(i + 1, k, imageview);
						controlledByWhiteList.add(coordinates);
					}
					// left
					if (i != 0) {
						addCoordinates(i - 1, k, imageview);
						controlledByWhiteList.add(coordinates);
					}
					// bottom
					if (k != 7) {
						addCoordinates(i, k + 1, imageview);
						controlledByWhiteList.add(coordinates);
					}
					// top
					if (k != 0) {
						addCoordinates(i, k - 1, imageview);
						controlledByWhiteList.add(coordinates);
					}
					// right top
					if (i != 7 && k != 0) {
						addCoordinates(i + 1, k - 1, imageview);
						controlledByWhiteList.add(coordinates);
					}
					// right bottom
					if (i != 7 && k != 7) {
						addCoordinates(i + 1, k + 1, imageview);
						controlledByWhiteList.add(coordinates);
					}
					// left top
					if (i != 0 && k != 0) {
						addCoordinates(i - 1, k - 1, imageview);
						controlledByWhiteList.add(coordinates);
					}
					// left bottom
					if (i != 0 && k != 7) {
						addCoordinates(i - 1, k + 1, imageview);
						controlledByWhiteList.add(coordinates);
					}

				}

			}

		}

	}

	public void checkCoordinatesControlledByBlack(ImageView[][] imageview) {
		controlledByBlackList = new ArrayList<int[]>();
		for (int i = 0; i < 8; i++) {

			for (int k = 0; k < 8; k++) {

				if (imageview[i][k].getImage() != null
						&& imageview[i][k].getImage().getUrl().contains("black_knight")) {
					Knight knight = new Knight(i, k, "no");
					temp = knight.showPossibleMoves(imageview);
					controlledByBlackList.addAll(temp);
				} else if (imageview[i][k].getImage() != null
						&& imageview[i][k].getImage().getUrl().contains("black_bishop")) {
					Bishop bishop = new Bishop(i, k, "no");
					temp = bishop.checkedCoordinates(imageview);
					controlledByBlackList.addAll(temp);
				} else if (imageview[i][k].getImage() != null
						&& imageview[i][k].getImage().getUrl().contains("black_quenn")) {
					Quenn quenn = new Quenn(i, k);
					temp = quenn.checkedCoordinates(imageview);
					controlledByBlackList.addAll(temp);
				} else if (imageview[i][k].getImage() != null
						&& imageview[i][k].getImage().getUrl().contains("black_rook")) {
					Rook rook = new Rook("no");
					rook.setFirstPosition(i);
					rook.setSecondPosition(k);
					temp = rook.checkedCoordinates(imageview);
					controlledByBlackList.addAll(temp);
				} else if (imageview[i][k].getImage() != null
						&& imageview[i][k].getImage().getUrl().contains("black_pawn")) {
					if (perspective.equalsIgnoreCase("black")) {
						if (i == 0 && k != 0) {
							addCoordinates(i + 1, k - 1, imageview);
							controlledByBlackList.add(coordinates);
						} else if (i == 7 && k != 0) {
							addCoordinates(i - 1, k - 1, imageview);
							controlledByBlackList.add(coordinates);
						} else if (k != 0) {
							addCoordinates(i + 1, k - 1, imageview);
							controlledByBlackList.add(coordinates);
							addCoordinates(i - 1, k - 1, imageview);
							controlledByBlackList.add(coordinates);
						}
					} else {
						if (i == 0 && k != 7) {
							addCoordinates(i + 1, k + 1, imageview);
							controlledByBlackList.add(coordinates);
						} else if (i == 7 && k != 7) {
							addCoordinates(i - 1, k + 1, imageview);
							controlledByBlackList.add(coordinates);
						} else if (k != 7) {
							addCoordinates(i + 1, k + 1, imageview);
							controlledByBlackList.add(coordinates);
							addCoordinates(i - 1, k + 1, imageview);
							controlledByBlackList.add(coordinates);
						}
					}
				} else if (imageview[i][k].getImage() != null
						&& imageview[i][k].getImage().getUrl().contains("black_king")) {

					// right

					if (i != 7) {
						addCoordinates(i + 1, k, imageview);
						controlledByBlackList.add(coordinates);
					}
					// left
					if (i != 0) {
						addCoordinates(i - 1, k, imageview);
						controlledByBlackList.add(coordinates);
					}
					// bottom
					if (k != 7) {
						addCoordinates(i, k + 1, imageview);
						controlledByBlackList.add(coordinates);
					}
					// top
					if (k != 0) {
						addCoordinates(i, k - 1, imageview);
						controlledByBlackList.add(coordinates);
					}
					// right top
					if (i != 7 && k != 0) {
						addCoordinates(i + 1, k - 1, imageview);
						controlledByBlackList.add(coordinates);
					}
					// right bottom
					if (i != 7 && k != 7) {
						addCoordinates(i + 1, k + 1, imageview);
						controlledByBlackList.add(coordinates);
					}
					// left top
					if (i != 0 && k != 0) {
						addCoordinates(i - 1, k - 1, imageview);
						controlledByBlackList.add(coordinates);
					}
					// left bottom
					if (i != 0 && k != 7) {
						addCoordinates(i - 1, k + 1, imageview);
						controlledByBlackList.add(coordinates);
					}

				}

			}

		}

	}
	@Override
	public void checkColors(ImageView[][] imageview, int[] coordinates) {
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
