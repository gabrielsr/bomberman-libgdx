package br.unb.unbomber.systems;

import net.mostlyoriginal.api.event.common.EventManager;
import br.unb.unbomber.component.Control;
import br.unb.unbomber.component.ControlPair;
import br.unb.unbomber.component.Direction;
import br.unb.unbomber.event.ActionCommandEvent;
import br.unb.unbomber.event.ActionCommandEvent.ActionType;
import br.unb.unbomber.event.MovementCommandEvent;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Wire;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;

@Wire
public class PlayerControlSystem extends EntitySystem {

	/** used to dispatch events */
	private EventManager em;
	
	ComponentMapper<Control> cmControl;
	
	public PlayerControlSystem(){
		super(Aspect.getAspectForAll(Control.class));
	}

	@Override
	public void processEntities(ImmutableBag<Entity> entities){
		
		for(Entity entity: entities){
			checkControls(entity);
		}
	}
	
	public void checkControls(Entity entity){

		Control control = cmControl.get(entity);
		
		for(ControlPair actionControl: control.getActions()){
			if(Gdx.input.isKeyPressed(actionControl.getKey())){
				switch (actionControl.getCommand().type) {
				case ACTION:
					em.dispatch(
							new ActionCommandEvent(
									(ActionType) actionControl.getCommand().command, entity ));
					
					break;
				case MOVEMENT:
					em.dispatch(
							new MovementCommandEvent(
									(Direction) actionControl.getCommand().command, entity ));
					break;
				}
			}
		}
	}
}
