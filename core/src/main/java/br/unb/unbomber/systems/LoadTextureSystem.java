package br.unb.unbomber.systems;

import java.util.logging.Level;
import java.util.logging.Logger;

import br.unb.bomberman.ui.screens.Assets;
import br.unb.unbomber.component.Draw;
import br.unb.unbomber.component.Position;
import br.unb.unbomber.components.Transform;
import br.unb.unbomber.components.Visual;
import br.unb.unbomber.match.GameMatch;
import br.unb.unbomber.ui.skin.LoadTexture;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Wire;
import com.artemis.utils.ImmutableBag;

@Wire
public class LoadTextureSystem extends EntitySystem {

	ComponentMapper<Draw> cmDraw;

	ComponentMapper<Visual> cmVisual;
	
	private String visualTheme;

	private final static Logger LOGGER = Logger.getLogger(GameMatch.class.getName());
	
	public LoadTextureSystem(String visualTheme) {
		super(Aspect.getAspectForAll(Draw.class, Position.class));
		
		this.visualTheme = visualTheme;
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		for (Entity entity : entities) {
			load(entity, visualTheme);
		}
	}
	


	public void load(Entity entity, String visualTheme){
			Draw drawable = cmDraw.get(entity);
			Visual visual = cmVisual.get(entity);
			
			if(visual == null){
				LOGGER.log(Level.INFO, "loading draw for " + drawable.getType());
				Visual vis = createVisual(drawable.getType());
				entity.edit().add(vis);
			}
	}

	private static Visual createVisual(String type) {
		Transform trans = new Transform();
		//TODO god have sake
		Visual vis = null;
		if("hardBlock".equals(type)){
			vis = new Visual(Assets.backgroundHBlock); 
		}else if("softBlock".equals(type)){
			vis = new Visual(Assets.backgroundSBlock); 
		}else if("character".equals(type)){
			vis = new Visual(Assets.walkingFront); 
			trans.scalex = 1.6f;
			trans.scaley = 1.6f;
			trans.dy=  12;
		}else if("bomb".equals(type)){
			vis = new Visual(Assets.bomb);
			trans.scalex = 0.8f;
			trans.scaley = 0.8f;
		}else if("explosion".equals(type)){
			vis = new Visual(Assets.explosionCenter); 
		}else{
			vis = new Visual(Assets.backgroundGround);
		}
		
		vis.setTransform(trans);
		return vis;
	}
}
