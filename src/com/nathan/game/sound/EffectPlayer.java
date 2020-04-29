package com.nathan.game.sound;

import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;

public class EffectPlayer {

	private ArrayList<String> musicFiles;
	private int currentSongIndex;
	private int loudnessVal;
	
	public EffectPlayer(double distance, int radius, String... files) {
		loudnessVal = setLoudness(distance, radius);
		musicFiles = new ArrayList<String>();
		for(String file : files )
			musicFiles.add("./res/sfx/" + file + ".wav");
		run();
	}

	public EffectPlayer(String... files) {
		loudnessVal = -30;
		musicFiles = new ArrayList<String>();
		for(String file : files )
			musicFiles.add("./res/sfx/" + file + ".wav");
		run();
	}
	
	private void playSound(String fileName) {
		try {
			File soundFile = new File(fileName);
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
			AudioFormat format = ais.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip clip = (Clip) AudioSystem.getLine(info);
			clip.open(ais);
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(loudnessVal);
			clip.start();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		playSound(musicFiles.get(currentSongIndex));
	}
	
	public void play(Clip clip) {
		clip.start();
	}

	public int setLoudness(double distance, int radius) {
		//highest val = 6, lowest val = -80
		int val = -20;

		if(distance >= radius){
			val = -80;
			return val;
		}

		for(int i = 0; i < distance / 16 || (val - i) < -80; i++) {
			val -= i;
		}
		return val;
	}
}
