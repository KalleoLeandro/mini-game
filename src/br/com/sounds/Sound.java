/**
 * 
 */
package br.com.sounds;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

/**
 * 
 */

public class Sound {
	
	private Clip clip;

	public Sound(String path) {
		try {
			File audioFile = new File(path);						
			
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);

			DataLine.Info info = new DataLine.Info(Clip.class, audioInputStream.getFormat());
			clip = (Clip) AudioSystem.getLine(info);

			clip.open(audioInputStream);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
