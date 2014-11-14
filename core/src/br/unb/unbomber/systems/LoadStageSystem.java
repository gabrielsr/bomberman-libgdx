package br.unb.unbomber.systems;

import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.StageSpec;

public class LoadStageSystem extends BaseSystem {
	
	StageSpec stage;

	
	public LoadStageSystem(EntityManager em) {
		super(em);
	}


	@Override
	public void start(){
		for(Entity entity: stage.getEntities()){
			getEntityManager().addEntity(entity);			
		}
	}


	@Override
	public void update() {
		// nothing
	}
}
