package com.patrickcaminhas.main;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {

	private AudioClip clip;

	public static final Sound hurtEffect = new Sound("/hurt.wav");
	public static final Sound music = new Sound("/music.wav");
	public static final Sound bowEffect = new Sound("/bow.wav");
	public static final Sound arrowPickUpEffect = new Sound("/arrow.wav");
	public static final Sound shootEffect = new Sound("/shoot.wav");
	public static final Sound explosionEffect = new Sound("/explosion.wav");

	private Sound(String name) {
		try {

			clip = Applet.newAudioClip(Sound.class.getResource(name));
		} catch (Throwable e) {
		}
	}

	public void play() {
		try {
			new Thread() {
				public void run() {
					clip.play();
				}
			}.start();

		} catch (Throwable e) {
		}
	}

	public void loop() {
		try {
			new Thread() {
				public void run() {
					clip.loop();
				}
			}.start();

		} catch (Throwable e) {
		}
	}
}