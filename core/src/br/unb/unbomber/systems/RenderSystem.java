package br.unb.unbomber.systems;

import java.util.List;

import br.un.unbomber.components.Looks;
import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.Component;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.StageSpec;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RenderSystem extends BaseSystem {

	SpriteBatch batch;
	
	StageSpec stage;

	public RenderSystem(EntityManager entityManager) {
		super(entityManager);
	}

	public RenderSystem(EntityManager entityManager, StageSpec stage,
			SpriteBatch batch) {
		super(entityManager);
		this.batch = batch;
		this.stage = stage;
	}

	@Override
	public void start(){
		loadSprites(null);
	}
	
	@Override
	public void update() {

		List<Component> components = getEntityManager().getComponents(
				Looks.class);
		for (Component component : components) {
			Looks looks = (Looks) component;

			batch.draw(looks.getTexture(), 0.1f, 0.1f);
		}

	}

	private void loadSprites(StageSpec stage) {
		// TODO Auto-generated method stub
		
	}
	
}
