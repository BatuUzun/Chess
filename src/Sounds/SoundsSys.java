package Sounds;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundsSys {

	private static Media media;
	private static MediaPlayer mediaPlayer;
	private static File file;

	public static void moveSound() {
		file = new File("C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\Sounds\\Move.mp3");
		media = new Media(file.toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
	}

	public static void captureSound() {
		file = new File("C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\Sounds\\Capture.mp3");
		media = new Media(file.toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
	}

	public static void errorSound() {
		file = new File("C:\\Users\\Batu\\Desktop\\nuclear launch code\\JavaFX\\Chess\\Sounds\\Error.mp3");
		media = new Media(file.toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
	}

}
