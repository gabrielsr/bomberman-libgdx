package br.unb.unbomber.systems;

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
