package game;

import java.awt.event.MouseEvent;

import engine.GameContainer;
import engine.Renderer;
import engine.gfx.Image;

public class GameManager extends Clicker {

	private Image pic;
	private int offX, offY;
	
	private String label = "Clicker";
	private int counter = 0;
	private int savetmp = 0;
	private double save = 0.0;
	private float bps = 0f;
	
	private int black = 0xff000000;
	
	// 0 - backups
	// 1 - Amount clicks
	// 2 - Price clicks
	// 3 - Amount database
	// 4 - Price database
	private int[] aup = new int[9];
	
	double firstTime = 0;
	double lastTime = System.nanoTime() / 1000000000.0;
	double passedTime = 0;
	double unprocessedTime = 0;
	
	public GameManager() {
		
		pic = new Image("/clicker.png");
		for(int i=1; i<aup.length; i+=2) {
			aup[i] = 0;
		}
		aup[0] = 0;
		aup[1] = 1;
		aup[2] = 100;
		aup[4] = 50;
		aup[6] = 200;
		aup[8] = 1000;
	}
	
	@Override
	public void update(GameContainer gc, float dt) {
		
		offX = gc.getWidth() / 2 - pic.getW() / 2;
		offY = 32;
		
		if(gc.getInput().isButtonDown(MouseEvent.BUTTON1) && (gc.getInput().getMouseX() > offX && gc.getInput().getMouseY() > offY && gc.getInput().getMouseX() < offX + pic.getW() && gc.getInput().getMouseY() < offY + pic.getH())) {
			aup[0] += aup[1];
		}
		
		if(gc.getInput().isButtonDown(MouseEvent.BUTTON1) && (gc.getInput().getMouseX() > gc.getWidth() - 75 && gc.getInput().getMouseY() > 8 && gc.getInput().getMouseX() < gc.getWidth() - 11 && gc.getInput().getMouseY() < 40) && aup[0] >= aup[2]) {
			aup[1]++;
			aup[0] -= aup[2];
			aup[2] *= 1.2;
		}
		
		if(gc.getInput().isButtonDown(MouseEvent.BUTTON1) && (gc.getInput().getMouseX() > gc.getWidth() - 75 && gc.getInput().getMouseY() > 48 && gc.getInput().getMouseX() < gc.getWidth() - 11 && gc.getInput().getMouseY() < 80) && aup[0] >= aup[4]) {
			aup[3]++;
			aup[0] -= aup[4];
			aup[4] *= 1.2;
		}
		
		if(gc.getInput().isButtonDown(MouseEvent.BUTTON1) && (gc.getInput().getMouseX() > gc.getWidth() - 75 && gc.getInput().getMouseY() > 88 && gc.getInput().getMouseX() < gc.getWidth() - 11 && gc.getInput().getMouseY() < 120) && aup[0] >= aup[6]) {
			aup[5]++;
			aup[0] -= aup[6];
			aup[6] *= 1.2;
		}
		
		if(gc.getInput().isButtonDown(MouseEvent.BUTTON1) && (gc.getInput().getMouseX() > gc.getWidth() - 75 && gc.getInput().getMouseY() > 128 && gc.getInput().getMouseX() < gc.getWidth() - 11 && gc.getInput().getMouseY() < 160) && aup[0] >= aup[8]) {
			aup[7]++;
			aup[0] -= aup[8];
			aup[8] *= 1.2;
		}
		
		bps = (float) (aup[3] * 0.1 + aup[5] * 0.5 + aup[7] * 1);
		
		if(counter >= 1800) {
			
			savetmp += (aup[3] * 1 + aup[5] * 5 + aup[7] * 10) / 10;
			save += bps - savetmp;
			while(save >= 1) {
				aup[0]++;
				save--;
			}
			aup[0] += savetmp;
			counter -= 1800;
			savetmp -= (aup[3] * 1 + aup[5] * 5 + aup[7] * 10) / 10;
		}
	}

	@Override
	public void render(GameContainer gc, Renderer r) {
		
		r.drawRect(0, 0, gc.getWidth() - 1, gc.getHeight() - 1, black);
		r.drawRect(1, 1, gc.getWidth() - 3, gc.getHeight() - 3, black);
		r.drawRect(0, 0, 81, gc.getHeight() - 1, black);
		r.drawRect(gc.getWidth() - 82, 0, 81, gc.getHeight() - 1, black);
		r.drawImage(pic, gc.getWidth() / 2 - pic.getW() / 2, 32);
		r.drawText("Money: " + aup[0], 85, 16, black);
		r.drawText(label, 85, 4, black);
		
		r.drawRect(gc.getWidth() - 75, 8, 64, 32, black);
		r.drawText("Clicks", gc.getWidth() - 74, 9, black);
		r.drawText("A: " + aup[1], gc.getWidth() - 74, 18, black);
		r.drawText("P: " + aup[2], gc.getWidth() - 74, 27, black);
		
		r.drawRect(gc.getWidth() - 75, 48, 64, 32, black);
		r.drawText("DB (0.1)", gc.getWidth() - 74, 49, black);
		r.drawText("A: " + aup[3], gc.getWidth() - 74, 58, black);
		r.drawText("P: " + aup[4], gc.getWidth() - 74, 67, black);
		
		r.drawRect(gc.getWidth() - 75, 88, 64, 32, black);
		r.drawText("WP (0.5)", gc.getWidth() - 74, 89, black);
		r.drawText("A: " + aup[5], gc.getWidth() - 74, 98, black);
		r.drawText("P: " + aup[6], gc.getWidth() - 74, 107, black);
		
		r.drawRect(gc.getWidth() - 75, 128, 64, 32, black);
		r.drawText("OD (1)", gc.getWidth() - 74, 129, black);
		r.drawText("A: " + aup[7], gc.getWidth() - 74, 138, black);
		r.drawText("P: " + aup[8], gc.getWidth() - 74, 147, black);
		
		r.drawText("MPS: " + bps, 4, 4, black);
		
		counter++;
	}
	
	public static void main(String args[]) {
		
		GameContainer gc = new GameContainer(new GameManager());
		gc.setWidth(320);
		gc.setHeight(240);
		gc.setScale(3f);
		gc.start();
	}

}
