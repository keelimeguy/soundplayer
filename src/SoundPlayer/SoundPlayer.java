package SoundPlayer;

/**
 *
 * @author Mangusbrother 
 * with edits by keelimeguy
 */

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * This is an example program that demonstrates how to play back an audio file
 * using the Clip in Java Sound API.
 * @author www.codejava.net
 *
 */
public class SoundPlayer extends Thread implements LineListener {

	/**
	 * this flag indicates whether the playback completes or not.
	 */
	private boolean playCompleted, flag;
	private int start = 0, end = 0;
	private String path = null;
	private boolean playing = false, looping = false, sound = false;
	private Clip audioClip;

	public void run() {
		while (true)
			if (playing) play(path);
	}

	/**
	 * Play a given audio file.
	 * @param audioFilePath Path of the audio file.
	 */
	private void play(String audioFilePath) {
		//File audioFile = new File(audioFilePath);

		try {
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(getClass().getResource(audioFilePath));//audioFile);

			AudioFormat format = audioStream.getFormat();

			DataLine.Info info = new DataLine.Info(Clip.class, format);

			audioClip = (Clip) AudioSystem.getLine(info);

			audioClip.addLineListener(this);

			audioClip.open(audioStream);

			if (looping) {
				//FloatControl gainControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
				//gainControl.setValue(-20.0f);
				if (start != end) audioClip.setLoopPoints(start, end);
				audioClip.loop(Clip.LOOP_CONTINUOUSLY);
			} else {
				audioClip.start();
			}
			while (!playCompleted) {
				if (sound) {
					sound = false;
					play(path);
				}
				// wait for the playback completes
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}

			}
			if (playCompleted) {
				playing = false;
				audioClip.removeLineListener(this);
			}
			audioClip.close();

		} catch (UnsupportedAudioFileException ex) {
			System.out.println("The specified audio file is not supported.");
			ex.printStackTrace();
		} catch (LineUnavailableException ex) {
			System.out.println("Audio line for playing back is unavailable.");
			ex.printStackTrace();
		} catch (IOException ex) {
			System.out.println("Error playing the audio file.");
			ex.printStackTrace();
		}

	}

	public void playMusic(String audioFilePath) {
		path = audioFilePath;
		playing = true;
		playCompleted = false;
		looping = true;
		start = end = 0;
	}

	public void playMusic(String audioFilePath, int start, int end) {
		path = audioFilePath;
		playing = true;
		playCompleted = false;
		looping = true;
		this.start = start;
		this.end = end;
	}

	public void playSound(String audioFilePath) {
		sound = true;
		path = audioFilePath;
		playCompleted = false;
		playing = true;
		looping = false;
	}

	public void stopSound() {
		if (playing) {
			audioClip.stop();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Listens to the START and STOP events of the audio line.
	 */
	@Override
	public void update(LineEvent event) {
		LineEvent.Type type = event.getType();

		if (type == LineEvent.Type.START) {
			flag = false;
			//System.out.println("Playback started.");

		} else if (type == LineEvent.Type.STOP) {
			if (!flag) {
				flag = true;
				playCompleted = true;
			}
			//System.out.println("Playback completed.");
		}

	}
}
