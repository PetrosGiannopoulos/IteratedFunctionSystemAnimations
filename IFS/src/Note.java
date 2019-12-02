import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.SwingWorker;


public class Note {

	AudioFormat audioFormat;
	SourceDataLine line;
	int sampleRate = 16000;
	
	static int La = 440;
	static int La_d = 466;
	static int Si = 494;
	static int Do = 523;
	static int Do_d = 554;
	static int Re = 587;
	static int Re_d = 622;
	static int Mi = 659;
	static int Fa = 698;
	static int Fa_d = 740;
	static int Sol = 784;
	static int Sol_d = 830;
	
	public Note(){
		
		try {
			audioFormat = new AudioFormat(sampleRate, 8, 1, true, true);
			line = AudioSystem.getSourceDataLine(audioFormat);
			
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private byte[] generateSineWavefreq(double frequencyOfSignal, double msec) {
        // total samples = (duration in second) * (samples per second)
		
        byte[] sin = new byte[(int)(msec/1000 * sampleRate)];
        double samplingInterval = (double) (sampleRate / frequencyOfSignal);
        //System.out.println("Sampling Frequency  : "+sampleRate);
        //System.out.println("Frequency of Signal : "+frequencyOfSignal);
        //System.out.println("Sampling Interval   : "+samplingInterval);
        for (int i = 0; i < sin.length; i++) {
            double angle = (2.0 * Math.PI * i) / samplingInterval;
            sin[i] = (byte) (Math.sin(angle) * 127);
            //System.out.println("" + sin[i]);
        }
        return sin;
    }
    private void play(SourceDataLine line, byte[] array) {
        int length = sampleRate * array.length / 1000;
        line.write(array, 0, array.length);
        line.drain();
        line.close();
    }
    
    public void play(final double freq,final double duration){
    	try {
			line.open(audioFormat);
			line.start();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		play(line,generateSineWavefreq(freq, duration));
	}
}
