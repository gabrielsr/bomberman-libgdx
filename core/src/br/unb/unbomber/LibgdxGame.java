package br.unb.unbomber;

import br.unb.unbomber.TargetFrameRateMatch;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.StageSpec;
import br.unb.unbomber.systems.AudioSystem;
import br.unb.unbomber.systems.PlayerControlSystem;
import br.unb.unbomber.systems.LoadStageSystem;
import br.unb.unbomber.systems.RenderSystem;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LibgdxGame extends TargetFrameRateMatch{
	final static int DEFAUT_FRAME_RATE = 30; 
	

	
	public LibgdxGame( SpriteBatch batch, StageSpec stage) {
		super(DEFAUT_FRAME_RATE);
		EntityManager em = this.getEntityManager();
		
		super.addSystem(new LoadStageSystem(em));
		super.addSystem(new PlayerControlSystem(em));
		super.addSystem(new AudioSystem(em, stage));
		super.addSystem(new RenderSystem(em, stage, batch));
		
	}

	public void tick(){
		super.tick();
		
	}

}
