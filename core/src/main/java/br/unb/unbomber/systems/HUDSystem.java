package br.unb.unbomber.systems;

import net.mostlyoriginal.api.event.common.EventManager;
import net.mostlyoriginal.api.event.common.Subscribe;
import br.unb.bomberman.ui.screens.Assets;
import br.unb.unbomber.component.Timer;
import br.unb.unbomber.event.GameOverEvent;
import br.unb.unbomber.event.TimeOverEvent;
import br.unb.unbomber.misc.EntityBuilder2;

import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.VoidEntitySystem;
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
@Wire
public class HUDSystem extends VoidEntitySystem {
	
	TimeSystem timerSystem;
	TimeOverEvent timeOver;
	
	private Entity HUD;
	private Timer timerHUD;
	
	private float lastTime;
	private String timeString;
	private float stateTime = 0f;
	
	SpriteBatch batch;

	EventManager em;
	
	public HUDSystem (SpriteBatch batch) {
		super();
		this.batch = batch;
	}
	
	public void processSystem(){
		
	}
	
	
	@Override
	public void begin() {
		// Inits the time system
		this.timeString = "";
		
		timeOver = new TimeOverEvent();
		
		timerHUD = new Timer(120000, timeOver);
		
		// Creation of a HUD entity
		HUD = EntityBuilder2.create(world)
				.with(timerHUD)
				.build();		
	}
	
	@Subscribe
	public void processTimeOver(TimeOverEvent timeOver) {
		// Total time
		this.stateTime = Gdx.graphics.getFramesPerSecond();
		
		if(timeOver != null)
		{
			end();
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
	public void end() {
		// If the time over create a game over event
		createGameOverEvent();
	}
	
	private void createGameOverEvent() {
		// Create a game over event
		GameOverEvent gameOverEvent = new GameOverEvent(this.HUD);
		em.dispatch(gameOverEvent);
	}
}
