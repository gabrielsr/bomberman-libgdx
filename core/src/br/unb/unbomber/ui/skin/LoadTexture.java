package br.unb.unbomber.ui.skin;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.un.unbomber.components.Transform;
import br.un.unbomber.components.Visual;
import br.unb.bomberman.ui.screens.Assets;
import br.unb.unbomber.GameMatch;
import br.unb.unbomber.component.Draw;
import br.unb.unbomber.core.Component;
import br.unb.unbomber.core.EntityManager;

public class LoadTexture {

	private static String TEXTURE_EXTENSION = ".png";
	
	private final static Logger LOGGER = Logger.getLogger(GameMatch.class.getName()); 
	
	public static void load(EntityManager em){
		
		List<Component> draws = (List<Component>) em.getComponents(Draw.class);
		
		for(Component component: draws){
			Draw drawable = (Draw) component;
			
			Component componentVis = em.getComponent(Visual.class, drawable.getEntityId());
			
			if(componentVis == null){
				LOGGER.log(Level.INFO, "loading draw for " + drawable.getType());
				Visual vis = createVisual(drawable.getType());
				vis.setEntityId(drawable.getEntityId());
				em.addComponent(vis);
			}
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
		}else{
			vis = new Visual(Assets.backgroundGround);
		}
		
		vis.setTransform(trans);
		return vis;
	}
}
