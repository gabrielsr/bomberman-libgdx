package br.unb.unbomber.systems;

import net.mostlyoriginal.api.event.common.Event;
import net.mostlyoriginal.api.event.common.EventManager;
import br.unb.unbomber.component.ExplosionBarrier.ExplosionBarrierType;
import br.unb.unbomber.component.MovementBarrier.MovementBarrierType;
import br.unb.unbomber.component.PowerUpFont;
import br.unb.unbomber.match.EntitySpec;
import br.unb.unbomber.match.StageSpec;
import br.unb.unbomber.misc.EntityBuilder2;

import com.artemis.annotations.Wire;
import com.artemis.systems.VoidEntitySystem;
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
@Wire
public class LoadStageSystem extends VoidEntitySystem {

	StageSpec stage;

	EventManager em;
	
	private String stageId;
	
	public LoadStageSystem(String stageId) {
		this.stageId = stageId;
	}
	
	public void buildStageBlocks(String map){

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
	
	public void addBlock(int x, int y,
			ExplosionBarrierType Type, String drawType) {
		EntityBuilder2.create(world)
				.withMovementBarrier(MovementBarrierType.BLOCKER)
				.withExplosionBarrier(Type).withPosition(x, y)
				.withDraw(drawType)
				.with(new PowerUpFont())
				.build();
	}

	
	@Override
	public void begin() {
		
		if (this.stage == null) {
			/* Load the stage spec */
			Json json = new Json();
			FileHandle stageFile = Gdx.files.local(stageId + ".json");
			this.stage = json.fromJson(StageSpec.class, stageFile.reader());
			buildStageBlocks(this.stage.getMapRepresentation());

			 for (EntitySpec entity : stage.getEntities()) {
				EntityBuilder2.create(world)
				.with(entity.components)
				.build();
			 }
		}

	}

	
	@Override
	public void processSystem() {
		for (Event event : stage.getEvents()) {
			em.dispatch(event);
		}
		stage.getEvents().clear();
	}
}
