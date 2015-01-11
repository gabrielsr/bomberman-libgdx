package br.unb.unbomber;

import br.unb.unbomber.match.TargetFrameRateMatch;
import br.unb.unbomber.systems.AudioSystem;
import br.unb.unbomber.systems.ScreenPositionSystem;
import br.unb.unbomber.systems.GridSystem;
import br.unb.unbomber.systems.HUDSystem;
import br.unb.unbomber.systems.LoadStageSystem;
import br.unb.unbomber.systems.LoadTextureSystem;
import br.unb.unbomber.systems.PlayerControlSystem;
import br.unb.unbomber.systems.RenderSystem;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Bomber match with Libgdx User Interface
 * 
 * @author grodrigues
 *
 */
public class BomberMatchWithUi extends TargetFrameRateMatch{
	final static int DEFAUT_FRAME_RATE = 30;
	
	public enum State {
		WORLD_STATE_NEXT_LEVEL,
		WORLD_STATE_GAME_OVER
	}
	
	
	public int score;

	public State state;
	
	final private String stageId;
	
	final private SpriteBatch batch;
	
	public BomberMatchWithUi( SpriteBatch batch, String stageId) {
		super(DEFAUT_FRAME_RATE);
		this.stageId = stageId;
		this.batch = batch;
	}

	public void start(){
		super.addSystem(new GridSystem());
		super.addSystem(new LoadStageSystem(stageId));
		super.addSystem(new PlayerControlSystem());
		super.addSystem(new AudioSystem());
		super.addSystem(new LoadTextureSystem(stageId));
		super.addSystem(new ScreenPositionSystem());
		super.addSystem(new RenderSystem(batch));
		super.addSystem(new HUDSystem(batch));
		
		super.start();
	}

	public void removeAllEntities() {
		//TODO
	}

}
