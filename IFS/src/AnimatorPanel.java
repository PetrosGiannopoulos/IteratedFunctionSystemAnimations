import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

//import org.uncommons.maths.random.MersenneTwisterRNG;


public class AnimatorPanel extends JPanel{

	
	FastAnimFrame frame;
	BufferedImage[] imgs;
	
	BufferedImage[] imgs1;
	BufferedImage[] imgs2;
	Image[] imgs3;
	BufferedImage[] imgs4;
	BufferedImage[] imgs5;
	BufferedImage[] imgs6;
	BufferedImage[] imgs7;
	BufferedImage[] imgs8;
	
	Image img;
	int cntr = 0,cntF = 0;
	int cntI = 0,cntJ = 0;
	boolean load = false,stop = false;
	int sampleRate = 16000;
	//AudioFormat audioFormat;
	//SourceDataLine line;
	//int[] Notes = new int[12];
	int[] lengths;
	
	public AnimatorPanel(){
		init();
		
	}
	
	public void paintComponent(Graphics g){
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		if(load){
			/*switch(cntF){
				case 0: g.drawImage(imgs1[cntr],0,0,null);break;
				case 1: g.drawImage(imgs2[cntr],0,0,null);break;
				case 2: g.drawImage(imgs3[cntr],0,0,null);break;
				case 3: g.drawImage(imgs4[cntr],0,0,null);break;
				case 4: g.drawImage(imgs5[cntr],0,0,null);break;
				case 5: g.drawImage(imgs6[cntr],0,0,null);break;
				case 6: g.drawImage(imgs7[cntr],0,0,null);break;
				case 7: g.drawImage(imgs8[cntr],0,0,null);break;
				default:
			};*/
			
			g.drawImage(imgs[cntr],0,0,null);
			
			g.setColor(Color.white);
			g.setFont(new Font("Times New Roman",Font.PLAIN,18));
			g.drawString(""+cntr, getWidth()-50, 30);
		}
	}
	
	public void init(){
		SwingWorker worker = new SwingWorker(){

			@Override
			protected Object doInBackground() throws Exception {
				// TODO Auto-generated method stub
				//calcNotes();
				//Thread.sleep(500);
				
				/*audioFormat = new AudioFormat(sampleRate, 8, 1, true, true);
				line = AudioSystem.getSourceDataLine(audioFormat);
				line.open(audioFormat);
				line.start();*/
				//play(line, generateSineWavefreq(400,50));
				//for(int k=1;k<9;k++){
				
				File[] dir = new File("frames/Animation09/").listFiles();
				imgs = new BufferedImage[dir.length];
				//long start = System.currentTimeMillis();
				for(int i=0;i<dir.length;i++){
					//imgs[i] = ImageIO.read(new File("frames/3dattractors/3dattractor"+getNumStr(""+i)+".png"));
					imgs[i] = ImageIO.read(dir[i]);
				}
				//System.out.println((System.currentTimeMillis()-start)/1000.0);
				
				load = true;
				while(true){
					
					
					for(int i=0;i<dir.length;i++){
						//playSound();
						repaint();
						Thread.sleep(30);
						if(cntr<dir.length-1){
							cntr++;
						}
					
					}
					//cntr = 0;
					for(int i=0;i<dir.length;i++){
						//playSound2();
						repaint();
						Thread.sleep(10);
						if(cntr>0){
							cntr--;
						}
					}
					
					
					
					
					if(stop){
						break;
					}
				//}
				}
				//System.out.println(cntr);
				//line.drain();
	            //line.close();
				return "done";
			}
			
		};
		worker.execute();
	}
	
	public void playSound(){
		SwingWorker<?, ?> worker = new SwingWorker(){

			@Override
			protected Object doInBackground() throws Exception {
				// TODO Auto-generated method stub
				//play(line, generateSineWavefreq(440,50));
				return "done";
			}
			
		};
		worker.execute();
	}
	
	public void playSound2(){
		SwingWorker worker = new SwingWorker(){

			@Override
			protected Object doInBackground() throws Exception {
				// TODO Auto-generated method stub
				//play(line, generateSineWavefreq(440,60));
				return "done";
			}
			
		};
		worker.execute();
	}
	
	public void calcNotes(){
		for(int i=0;i<12;i++){
			//Notes[i] = (int)(440*Math.pow(2, i/12));
		}
	}
	
	public String getNumStr(String num){
		
		if(num.length() ==1){
			num = "0"+num;
		}
		
		return num;
	}
	
	private byte[] generateSineWavefreq(int frequencyOfSignal, double msec) {
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
    }
    
    public static double pow(final double a, final double b) {
	    final int x = (int) (Double.doubleToLongBits(a) >> 32);
	    final int y = (int) (b * (x - 1072632447) + 1072632447);
	    return Double.longBitsToDouble(((long) y) << 32);
	}
	public static double pow2(double a, double b) {
	    final long tmp = (long) (9076650 * (a - 1) / (a + 1 + 4 * (Math.sqrt(a))) * b + 1072632447);
	    return Double.longBitsToDouble(tmp << 32);
	}
	
	public static double exp(double val) {
	    final long tmp = (long) (1512775 * val + 1072632447);
	    return Double.longBitsToDouble(tmp << 32);
	}
	public static double log(double x) {
	    return 6 * (x - 1) / (x + 1 + 4 * (Math.sqrt(x)));
	}
}
