import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import com.jhlabs.image.GammaFilter;
import com.jhlabs.image.GlowFilter;
import com.jhlabs.image.RotateFilter;

public class ExtremeAnimation extends JPanel{

	BufferedImage img;
	Graphics img_g,panelG;
	
	double xxmin = -1,xxmax = 1,yymin = -1,yymax = 1;
	double[][] p;
	//int presX = 800,presY=800;
	int presX,presY;
	int iter1 = 1000,iter2 = 100;
	double xinc,yinc;
	//double[][] color = new double[2000][2000];
	int xxx,yyy;
	
	double xold =2*(Math.random()-0.5),yold=2*(Math.random()-0.5),xnew,ynew;
	
	//Random Vars
	Random rand = new Random();
	int randV;
	
	//Linear Functions
	int linNum = 10;
	linearFunction[] linearFx = new linearFunction[linNum],linearFy = new linearFunction[linNum];
	double linearX,linearY;
	double[] ParA = new double[linNum],ParB = new double[linNum],ParC = new double[linNum],ParD = new double[linNum],ParE = new double[linNum],ParF = new double[linNum];
	
	double A = -0.966918,B = 2.879879,C = 0.765145,D = 0.744728;
	int opA=1,opB=1;
	
	GlowFilter filter = new GlowFilter();
	GammaFilter filter2 = new GammaFilter(1.3f);
	
	double time = 0.0;
	boolean timer = true,rendering = true;
	
	public ExtremeAnimation(){
		init();
		//tick();
	}
	
	
	public void tick(){
		SwingWorker worker = new SwingWorker(){

			@Override
			protected Object doInBackground() throws Exception {
				// TODO Auto-generated method stub
				while(timer){
					Thread.sleep(20);
					repaint();
				}
				return null;
			}
			
		};
		worker.execute();
		
	}
	/*public void paintComponent(Graphics g){
		g.drawImage(img,0,0,null);
	}*/
	
	public void drawFormula(){
		
		//double[][] u = new double[iter1][iter2];
		
		
		for(int j=0;j<linNum;j++){
			ParA[j] = 2*(Math.random()-0.5);
			ParB[j] = 2*(Math.random()-0.5);
			ParC[j] = 2*(Math.random()-0.5);
			ParD[j] = 2*(Math.random()-0.5);
			ParE[j] = 2*(Math.random()-0.5);
			ParF[j] = 2*(Math.random()-0.5);
			linearFx[j] = new linearFunction(ParA[j],ParB[j],ParC[j],0);
			linearFy[j] = new linearFunction(ParD[j],ParE[j],ParF[j],1);
			//linearFx[j] = ParA[j]*xold+ParB[j]*yold+ParC[j];
			//linearFy[j] = ParD[j]*xold+ParE[j]*yold+ParF[j];
		}
		
		while(rendering){
		
		for(int i=0;i<getWidth();i++){
			for(int j=0;j<getHeight();j++){
				p[i][j] = 0;
				//u[i][j] = 0;
			}
		}
		
		
		
		//for(int k=0;k<50;k++){
		time += 0.55;
		//xold = xold-0.5*Math.sin(2*Math.PI*5000);
		for(int i=0;i<iter1;i++){
			for(int j=0;j<iter2;j++){
				
				
				randV = (int)(Math.random()*(linNum));
				
				calcLinear(xold+time,yold+time);
				
				calcXY(linearX,linearY,randV);
				
				//calcLinear(xnew,ynew);
				//randV = (int)(Math.random()*(linNum-1));
				//calcXY(linearX,linearY,randV);
				xold = xnew;
				yold = ynew;
				
				
				if(xnew<xxmax && xnew>xxmin && ynew<yymax && ynew>yymin){
					xxx = (int)((xnew-xxmin)*xinc);
					yyy = (int)((ynew-yymin)*yinc);
					
					//System.out.println(xxx+"|"+yyy);
					
					p[xxx][yyy] = p[xxx][yyy]+(double)((1-AnimatorPanel.pow2(p[xxx][yyy],0.1))*0.01);
					p[xxx][yyy] %= 1;
					
					//p[xxx][yyy] += 0.1;
					
					if(p[xxx][yyy]<0.02){
						img_g.setColor(Color.black);
					}
					else{
						//img_g.setColor(Color.getHSBColor(randV/9.0f, randV/11f/*0.70f*/, (float)(p[xxx][yyy]*1.8)));
						img_g.setColor(Color.getHSBColor(randV*0.11f, randV*0.09f/*0.70f*/, (float)(p[xxx][yyy]*1.8)));
					}
					
					img_g.drawOval(xxx, yyy, 1, 1);
					//repaint();
					
				}
			}
		}
		filter.filter(img, img);
		filter2.filter(img, img);
		
		panelG.drawImage(img, 0, 0, null);
		
		img_g.setColor(Color.black);
		img_g.fillRect(0, 0, getWidth(), getHeight());
		
		}
		/*try {
			ImageIO.write(img, "png", new File("frames/Animation09/fractal"+getNumStr3d(""+0)+".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		//img_g.setColor(Color.black);
		//img_g.fillRect(0, 0, 6000, 9000);
		
		
		//}
	}
	
	
	
	public void calcXY(double x,double y,int t){
		
		double r = Math.sqrt(x*x+y*y);
		double a = Math.atan2(y, x);
		
		switch(t){
			case 1:
				xnew = x;
				ynew = y;
				break;
			case 2:
				xnew = Math.sin(x);
				ynew = Math.sin(y);
				break;
			case 3:
				xnew = x/(r*r);
				ynew = y/(r*r);
				break;
			case 4:
				xnew = r*Math.cos(a+r);
				ynew = r*Math.cos(a-r);
				break;
			case 5:
				xnew = r*Math.cos(2*a);
				ynew = r*Math.sin(2*a);
				break;
			case 6:
				xnew = a/Math.PI;
				ynew = r-1;
				break;
			case 7:
				xnew = r*Math.sin(a+r);
				ynew = r*Math.cos(a-r);
				break;
			case 8:
				xnew = r*Math.sin(a*r);
				ynew = -r*Math.cos(a*r);
				break;
			case 9:
				xnew = a/Math.PI*(Math.sin(Math.PI*r));
				ynew = a/Math.PI*(Math.cos(Math.PI*r));
				break;
			case 10:
				xnew = x*Math.sin(r*r)-y*Math.cos(r*r);
				ynew = x*Math.cos(r*r)+y*Math.sin(r*r);
				break;
			case 11:
				xnew = Math.cos(x)/y;
				ynew = Math.sin(x)/y;
				break;
			default:
		}
	}
	
	public void calcLinear(double LinX,double LinY){
		linearX = linearFx[randV].getLinearX(LinX, LinY);
		linearY = linearFy[randV].getLinearY(LinX, LinY);
	}
	
	public void init(){
		SwingWorker worker = new SwingWorker(){

			@Override
			protected Object doInBackground() throws Exception {
				// TODO Auto-generated method stub
				Thread.sleep(500);
				img = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB);
				img_g = img.getGraphics();
				img_g.setColor(Color.black);
				img_g.fillRect(0, 0, getWidth(), getHeight());
				
				panelG = getGraphics();
				panelG.drawImage(img, 0, 0, null);
				
				presX = getWidth();
				presY = getHeight();
				
				xinc = presX/(xxmax-xxmin);
				yinc = presY/(yymax-yymin);
				
				p = new double[presX][presY];
				
				
				drawFormula();
				return null;
			}
			
		};
		worker.execute();
	}
	
	public String getNumStr3d(String num){
		
		if(num.length() ==1){
			num = "00"+num;
		}
		else if(num.length() == 2){
			num = "0"+num;
		}
		
		return num;
	}
	
	class linearFunction{
		double a,b,c,d,e,f;
		
		public linearFunction(double p1,double p2,double p3,int XorY){
			if(XorY == 0){ a=p1;b=p2;c=p3;}
			if(XorY == 1){ d=p1;e=p2;f=p3;}
		}
		public double getLinearX(double x,double y){
			return a*x+b*y+c;
		}
		
		public double getLinearY(double x,double y){
			return d*x+e*y+f;
		}
		
	}
}
