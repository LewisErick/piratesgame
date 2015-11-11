package com.Uriel.Ejemplo.Game;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Monillo extends GameObject implements Personaje{
	
	public enum State{
		WALKRIGHT,
		WALKLEFT,
		STANDRIGHT,
		STANDLEFT,
		DIE
	}
	private int vida = 100;
	public State currentState = State.WALKLEFT;
	
	private float jumpVelocity = 2f;
	
	public Monillo(Image image, float positionX, float positionY) throws SlickException{
		super(image, positionX, positionY, 108, 140, 8, 1, 8, 50);
		super.maxVelocity = 0.2f;
	}
	
	public void moverse(Control controlador, GameContainer gc, float delta) throws SlickException{
		Input input = gc.getInput();
		
		if(input.isKeyDown(Input.KEY_A)){
			GameObject collision = controlador.checkCollision(this, -delta,0, false);
			if(collision == null){
				moveLeft(delta);
			}else{
				if(collision instanceof Triangulo){
					Triangulo col = (Triangulo)collision;
					moveLeft(delta * (float)Math.sin(col.getAngle()));
					moveUp(delta * (float)Math.cos(col.getAngle()));
				}
			}
			currentState = State.WALKLEFT;
		}
		if(input.isKeyDown(Input.KEY_D)){
			GameObject collision = controlador.checkCollision(this, delta,0,false);
			if(collision == null){
				moveRight(delta);
			}else{
				if(collision instanceof Triangulo){
					Triangulo col = (Triangulo)collision;
					moveRight(delta * (float)Math.sin(col.angle));
					moveUp(delta * (float)Math.cos(col.angle));
				}
			}
			currentState = State.WALKRIGHT;
		}
		if(input.isKeyPressed(Input.KEY_SPACE)){
			jump();
		}
		gravity(delta,controlador);
	}
	
	public void jump(){
		super.inicialVelocityY = jumpVelocity;
	}
	
	@Override
	public void init() throws SlickException {
			Image derecha = new Image("res/nuevomonoderecha.png");
			Image izquierda = new Image("res/nuevomonoizquierda.png");
			super.animation[0] = super.getAnimation(derecha, 8, 1, 108, 140, 8, 50);
			super.animation[1] = super.getAnimation(izquierda, 8, 1, 108, 140, 8, 50);
			super.animation[2] = super.getAnimation(derecha, 8, 1, 108, 140, 8, 50);
			super.animation[3] = super.getAnimation(izquierda, 8, 1, 108, 140, 8, 50);
			super.animation[4] = super.getAnimation(derecha, 8, 1, 108, 140, 8, 50);
	}

	@Override
	public void Render(Graphics g) throws SlickException {
		super.animation[currentState.ordinal()].draw(super.positionX, super.positionY,27,35);
		g.setColor(Color.red);
		g.drawRect(super.positionX+5, super.positionY-11, 21, 6);
		g.setColor(Color.green);
		g.fillRect(super.positionX+5, super.positionY-10, (float) (vida*0.2), 5);
		g.setColor(Color.white);
	}
	
	@Override
	public void Update(int delta) throws SlickException{
		super.animation[currentState.ordinal()].update(delta);
		super.boundingBox = new Rectangle(positionX, positionY, 27, 35);
	}

	
	@Override
	public void implementarVida() {
		
	}
	
}
