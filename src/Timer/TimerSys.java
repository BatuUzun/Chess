package Timer;

public class TimerSys {

	public static String timerLabelValueFormatted(int seconds) {

		String min = String.valueOf(seconds / (60 * 1000));
		String secondsRemaining = String.valueOf(seconds % (60 * 1000));
		secondsRemaining = String.valueOf((int) (Double.parseDouble(secondsRemaining) / 1000));

		if (seconds / (60 * 1000) < 10)
			min = "0" + min;
		if (Integer.valueOf(secondsRemaining) % (60) < 10)
			secondsRemaining = "0" + secondsRemaining;

		return min + ":" + secondsRemaining;
	}

}
