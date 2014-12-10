package br.unb.unbomber.systems;

import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Draw;
import br.unb.unbomber.component.ExplosionBarrier;
import br.unb.unbomber.component.ExplosionBarrier.ExplosionBarrierType;
import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.StageSpec;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

/**
 * Instantiate the Entities of a match.
 * 
 * Create the entities form a JSON with the Spec of the Stage and put them into
 * the EntityManager
 * 
 * @author grodrigues
 *
 */
public class LoadStageSystem extends BaseSystem {

	StageSpec stage;

	
	public LoadStageSystem(EntityManager em, String stageId) {
		super(em);

		/* Load the stage spec */
		Json json = new Json();
		FileHandle stageFile = Gdx.files.local(stageId + ".json");
		this.stage = json.fromJson(StageSpec.class, stageFile.reader());
		addBlocks(em, this.stage.getMapRepresentation());
	}
	
	public void addBlocks(EntityManager entityManager, String map){

		int x = 0;
		int y = 0;
		
		
		for (int i = 0; i < map.length(); i++) {
			if(map.charAt(i) == '#') {
				addBlock(x,y,ExplosionBarrierType.BLOCKER, "hardBlock");
			}
			else if (map.charAt(i) == '@') {
				addBlock(x,y,ExplosionBarrierType.STOPPER, "softBlock");
			}
			
			if (map.charAt(i) == '\n') {
				y += 1;
				x = 0;
			}
			else {
				x += 1;
			}
		}
	}
	
	public void addBlock(int x, int y, ExplosionBarrierType explosionBarrierType, String drawType){
		Entity block = getEntityManager().createEntity();
		
		CellPlacement cell = new CellPlacement();
		
		cell.setCellX(x);
		cell.setCellY(y);
		
		ExplosionBarrier explosionBarrier = new ExplosionBarrier();
		explosionBarrier.setType(explosionBarrierType);
	
		block.addComponent(new Draw("hardBlock"));
		block.addComponent(cell);
		getEntityManager().update(block);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void start() {

		for (Entity entity : stage.getEntities()) {
			getEntityManager().addEntity(entity);
		}
	}

	@Override
	public void update() {
		// nothing
	}
}
