package br.unb.unbomber.systems;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import br.unb.bomberman.ui.screens.ScreenDimensions;
import br.unb.gridphysics.Vector2D;
import br.unb.unbomber.component.Ballistic;
import br.unb.unbomber.component.Draw;
import br.unb.unbomber.component.Movable;
import br.unb.unbomber.component.Position;
import br.unb.unbomber.components.Transform;
import br.unb.unbomber.components.Visual;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Wire;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

@Wire
public class RenderSystem extends EntitySystem {

	
	private OrthographicCamera cam;
	private Array<Entity> renderQueue;
	private Comparator<Entity> comparator;

	ComponentMapper<Draw> cmDraw;
	
	ComponentMapper<Position> cmPosition;

	ComponentMapper<Visual> cmVisual;
	
	ComponentMapper<Movable>  cmMovable;

	protected Logger LOGGER = Logger.getLogger("br.unb.unbomber.systems");

	private ScreenDimensions screenDimensions = new ScreenDimensions();
	
	SpriteBatch batch;
	
	public RenderSystem(SpriteBatch batch) {
		super(Aspect.getAspectForAll(Visual.class));
		
		comparator = new Comparator<Entity>() {
			//TODO improve with x and type comparison
			@Override
			public int compare(Entity entityA, Entity entityB) {
				return (int)Math.signum( getY(entityA) - getY(entityB));
			}

			private int getY(Entity entityA) {
				Position placement = cmPosition.getSafe(entityA);
				if(placement==null){
					return Integer.MAX_VALUE;
				}
				return placement.getCellY();
			}
		};
		
		this.batch = batch;
	}

	@Override
	public void begin() {
		cam = new OrthographicCamera();
		cam.setToOrtho(false, screenDimensions.getScreenWidth(), screenDimensions.getScreenHeight());
	}

	
	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		List<Entity> renderQueue = new ArrayList<Entity>();

		for(Entity entity:entities){
			renderQueue.add(entity);
		}
		
		renderQueue.sort(comparator);
		
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		
		for (Entity entity : renderQueue) {
			
			Visual visual = cmVisual.get(entity);
					
			
			batch.draw(visual.getRegion(),
					visual.getX(), 
					visual.getY(),
					visual.getOriginX(), visual.getOriginY(),
					visual.getWidth(), visual.getHeight(),
					visual.getScaleX(),visual.getScaleY(),
					visual.getRotation());
		}
		renderQueue.clear();
	}

	public void processEntity(Entity entity) {
		renderQueue.add(entity);
	}
	
	public OrthographicCamera getCamera() {
		return cam;
	}
	
}
