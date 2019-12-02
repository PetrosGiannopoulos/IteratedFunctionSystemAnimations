import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;


public class FastAnimFrame extends JFrame{

	static FastAnimFrame frame;
	static final int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
	static final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	public static void main(String[] args){
		frame = new FastAnimFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH,HEIGHT);
		frame.setLocation(0,0);
		frame.setUndecorated(true);
		frame.setVisible(true);
	}
	
	public FastAnimFrame(){
		
		add(new ExtremeAnimation());
		//add(new FormulaAnimation());
		//add(new AnimatorPanel());
		
		addKeyListener(new KeyIt());
	}
	
	private class KeyIt implements KeyListener{

		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			if(arg0.getKeyText(arg0.getKeyCode()) == "Escape"){
				System.exit(0);
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
