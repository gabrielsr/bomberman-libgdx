package br.unb.unbomber.systems;

import java.util.List;

import br.unb.unbomber.component.BombDropper;
import br.unb.unbomber.component.Movable;
import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.Component;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.event.ActionCommandEvent;
import br.unb.unbomber.event.ActionCommandEvent.ActionType;
import br.unb.unbomber.event.MovementCommandEvent;
import br.unb.unbomber.event.MovementCommandEvent.MovementType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class PlayerControlSystem extends BaseSystem {


	public PlayerControlSystem(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public void update() {
		MovementType direction = null;

		if (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)) {
			direction = MovementType.MOVE_LEFT;
		}
		if (Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)) {
			direction = MovementType.MOVE_RIGHT;
		}
		if (Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP)) {
			direction = MovementType.MOVE_DOWN;
		}
		if (Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.DOWN)) {
			direction = MovementType.MOVE_UP;
		}
		
		if(direction!=null){
			List<Component> movables = getEntityManager().getComponents(Movable.class);
			for(Component movable: movables){
				getEntityManager().addEvent(
						(new MovementCommandEvent(direction,movable.getEntityId())));
			}

		}
		
		ActionType action = null;
		
		if (Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isKeyPressed(Keys.J)) {
			action = ActionType.DROP_BOMB;
		}
		if (Gdx.input.isKeyPressed(Keys.ENTER) || Gdx.input.isKeyPressed(Keys.K)) {
			action = ActionType.EXPLODE_REMOTE_BOMB;
		}
		
		if(action!=null){
			List<Component> droppers = getEntityManager().getComponents(BombDropper.class);
			for(Component dropper: droppers){
				getEntityManager().addEvent(
						(new ActionCommandEvent(action,dropper.getEntityId())));				
			}
		}
	}
}
