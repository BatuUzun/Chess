package Pieces;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Pieces implements Interface_Move {

	public boolean isEmpty(ImageView[][] imageview, int[] coordinates) {
		Image image = null;
		if(coordinates[0] >= 0 && coordinates[0]<= 7 && coordinates[1] >= 0 && coordinates[1]<= 7) {
			image = imageview[coordinates[0]][coordinates[1]].getImage();
		}
		
		if (image == null) {

			return true;

		}

		return false;
	}

	public abstract void addCoordinates(int firstIndex, int secondIndex, ImageView[][] imageview);
	public abstract void checkColors(ImageView[][] imageview, int[] coordinates);

}
