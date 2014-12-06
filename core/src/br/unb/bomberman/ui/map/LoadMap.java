package br.unb.bomberman.ui.map;

import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.ExplosionBarrier;
import br.unb.unbomber.component.ExplosionBarrier.ExplosionBarrierType;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;

import com.badlogic.gdx.utils.Json;

public class LoadMap extends LoadedStage{
	private static final Object stage = "stage.json";

	private static final ExplosionBarrierType BLOCKER = null;
	private static final ExplosionBarrierType STOPPER = null;
	
	int x = 0;
	int y = 0;
	
	Json json = new Json();
	String text = json.toJson(stage);
	LoadedStage loadedStage = json.fromJson(LoadedStage.class, text);
	
	String map = LoadedStage.getStage();

	private EntityManager entityManager; {
	
	for (int i = 0; i < map.length(); i++) {
		if(map.charAt(i) == '#') {
			Entity hardBlock = entityManager.createEntity();
			hardBlock.addComponent(new CellPlacement());
			((CellPlacement) hardBlock.getComponents()).setCellX(x);
			((CellPlacement) hardBlock.getComponents()).setCellY(y);
			x += 1;
			hardBlock.addComponent(new ExplosionBarrier());
			((ExplosionBarrier) hardBlock.getComponents()).setType(BLOCKER);
			entityManager.update(hardBlock);
		}
		else if (map.charAt(i) == '@') {
			Entity softBlock = entityManager.createEntity();
			softBlock.addComponent(new CellPlacement());
			((CellPlacement) softBlock.getComponents()).setCellX(x);
			((CellPlacement) softBlock.getComponents()).setCellY(y);
			x += 1;
			softBlock.addComponent(new ExplosionBarrier());
			((ExplosionBarrier) softBlock.getComponents()).setType(STOPPER);
			entityManager.update(softBlock);
		}
		else if (map.charAt(i) == '\n') {
			y += 1;
			x = 0;
		}
		else {
			x += 1;
		}
	}
	/*Cade o Hardblocks e Softblocks para eu instanciar??
	*Aqui você vai pecorrer a string stage que pode ser interpretada como um vetor, conforme você lê, 
	 * cria objetos soft e hard blocks e adicionar no arraylist com 
	 *ArrayList Entities = new ArrayList(); (já foi criado na outra classe)
	 *Entities.add(new Entity(alguma coisa));
	 *Entities.add(new HardBlock(alguma coisa));
	 */
	}
}
