package br.unb.unbomber.systems;

import java.util.List;

import br.unb.bomberman.ui.screens.Assets;
import br.unb.unbomber.component.Timer;
import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.Event;
import br.unb.unbomber.event.GameOverEvent;
import br.unb.unbomber.event.TimeOverEvent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Control the system of the status bar.
 * 
 * Create the status bar and update the values of the time e and score.
 * 
 * @version 0.1 13 Dez 2014
 * @author Grupo 5 - Dayanne <dayannefernandesc@gmail.com>
 * 
 */
public class HUDSystem extends BaseSystem {
	
	TimeSystem system;
	TimeOverEvent timeOver;
	
	private Entity HUD;
	private Timer timerHUD;
	
	private float lastTime;
	private String timeString;
	private float stateTime = 0f;
	
	SpriteBatch batch;

	
	public HUDSystem (EntityManager entityManager,
			SpriteBatch batch) {
		super(entityManager);
		this.batch = batch;
	}
	
	@Override
	public void start() {
		// Inits the time system
		this.system = new TimeSystem(this.getEntityManager());
		
		this.timeString = "";
		
		// Creation of a HUD entity
		HUD =  this.getEntityManager().createEntity();
		// Creation of a component time for HUD entity
		timerHUD = new Timer(120000, timeOver);
		HUD.addComponent(timerHUD);
		this.getEntityManager().update(HUD);
		
		
		
	}
	
	@Override
	public void update() {
		// Total time
		this.stateTime = Gdx.graphics.getFramesPerSecond();
		List<Event> timeOver = this.getEntityManager().getEvents(TimeOverEvent.class);
		
		if(timeOver != null)
		{
			stop();
		}
		
		// Increase the lastime counted with the time elapsed times the framerate
		float timeElpsed = this.timerHUD.getElapsedTime() * 31;
		float min = (long)((timeElpsed / 60000)  % 60) ;
		float seg = (long)((timeElpsed / 1000) % 60);
		
		// Modify the string to show on HUD with the new time
		String minString = String.format("%.0f", min);
		String segString = String.format("%.0f", seg);
		
		this.timeString = minString + ":" + segString;
		
		// Draw on HUD the time regressively
		Assets.font.setScale(0.6f, 1);
		Assets.font.draw(batch, this.timeString , 85, 480 - 28);
	}
	
	@Override
	public void stop() {
		// If the time over create a game over event
		createGameOverEvent();
	}
	
	private void createGameOverEvent() {
		// Create a game over event
		GameOverEvent gameOverEvent = new GameOverEvent(this.HUD.getEntityId());
		getEntityManager().addEvent(gameOverEvent);
	}
}
