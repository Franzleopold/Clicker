package game;

import engine.GameContainer;
import engine.Renderer;

public abstract class Clicker {
	
	public abstract void update(GameContainer gc, float dt);
	public abstract void render(GameContainer gc, Renderer r);

}
