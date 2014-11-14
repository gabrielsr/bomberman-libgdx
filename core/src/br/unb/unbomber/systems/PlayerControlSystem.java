package br.unb.unbomber.systems;

import br.unb.unbomber.core.BaseSystem;
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
			direction = MovementType.MOVE_UP;
		}
		if (Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.DOWN)) {
			direction = MovementType.MOVE_DOWN;
		}
		
		if(direction!=null){
			getEntityManager().addEvent(
				(new MovementCommandEvent(direction,1)));
		}
		
		ActionType action = null;
		
		if (Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isKeyPressed(Keys.J)) {
			action = ActionType.DROP_BOMB;
		}
		if (Gdx.input.isKeyPressed(Keys.ENTER) || Gdx.input.isKeyPressed(Keys.K)) {
			action = ActionType.EXPLODE_REMOTE_BOMB;
		}
		
		if(direction!=null){
			getEntityManager().addEvent(
				(new ActionCommandEvent(action,1)));
		}
	}
}
