package com.nathan.game.sound;

import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;

public class AudioPlayer {

	private ArrayList<String> musicFiles;
	private int currentSongIndex;

	private Clip clip = null;
	private FloatControl gainControl;
	public AudioPlayer(String... files) {
		musicFiles = new ArrayList<String>();
		for(String file : files )
			musicFiles.add("./res/music/" + file + ".wav");
		run();
	}
	
	private void playSound(String fileName) {
		try {
			File soundFile = new File(fileName);
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
			AudioFormat format = ais.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(ais);
			gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(-30);
			clip.loop(clip.LOOP_CONTINUOUSLY);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		playSound(musicFiles.get(currentSongIndex));
	}

	public void muteMusic() {
		gainControl.setValue(-80);
	}

	public void unmuteMusic() {
		gainControl.setValue(-30);
	}


}
