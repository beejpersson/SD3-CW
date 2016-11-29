import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio {
	
	//public static final Audio audio = new Audio("audio.mp3");
	
	//private AudioClip clip;
	
	public static synchronized void playSound(final String filename) {
		  new Thread(new Runnable() {
		    public void run() {
		      try {
		        Clip clip = AudioSystem.getClip();
		        AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(filename).getAbsoluteFile());
		        clip.open(inputStream);
		        clip.start(); 
		      } catch (Exception e) {
		        e.printStackTrace();
		      }
		    }
		  }).start();
		}
}
