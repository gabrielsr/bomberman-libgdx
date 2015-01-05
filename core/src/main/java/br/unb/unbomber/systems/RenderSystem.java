package br.unb.unbomber.systems;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import br.unb.bomberman.ui.screens.ScreenDimensions;
import br.unb.gridphysics.Vector2D;
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

	SpriteBatch batch;
	
	private ScreenDimensions screenDimensions = new ScreenDimensions();

	
	
	
	public RenderSystem(SpriteBatch batch) {
		super(Aspect.getAspectForAll(Position.class, Visual.class));
		
		comparator = new Comparator<Entity>() {
			//TODO improve with x and type comparison
			@Override
			public int compare(Entity entityA, Entity entityB) {
				return (int)Math.signum( getY(entityA) - getY(entityB));
			}

			private int getY(Entity entityA) {
				Position placement = cmPosition.get(entityA);
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
			
			
			Position position = cmPosition.get(entity);
			Visual visual = cmVisual.get(entity);
			
			Vector2D<Float> screenPosition;

			float width = visual.getRegion().getRegionWidth();
			float height = visual.getRegion().getRegionHeight();

			Vector2D<Float> gridPosition = position.centerPosition();
			
			/** screen position */
			screenPosition = gridPositionToScreenPosition(gridPosition);
			
			Vector2D<Float> repositeTheCenter = new Vector2D<Float>(-0.5f * width, -0.5f *  height);	
			screenPosition = screenPosition.add(repositeTheCenter);
			
			/** Transformations for the game */
			Vector2D<Float> gameTransSum = new Vector2D<Float>(visual.getTransform().dx, visual.getTransform().dy);
			screenPosition = screenPosition.add(gameTransSum);

	
			
			Transform t = visual.getTransform();
			

			float originX = width * 0.5f;
			float originY = height * 0.5f;			
			
//			batch.draw(vis.getRegion(),
//					screenPosition.getX(), 
//					screenPosition.getY());
			batch.draw(visual.getRegion(),
					screenPosition.getX(), 
					screenPosition.getY(),
					   originX, originY,
					   width, height,
					   t.scalex, t.scaley,
					   MathUtils.radiansToDegrees * t.rotation);
		}
		renderQueue.clear();
	}
	
	public void processEntity(Entity entity) {
		renderQueue.add(entity);
	}
	
	public OrthographicCamera getCamera() {
		return cam;
	}
	
	
	/**
	 * Method that convert a cell placement from grid reference to screen position.
	 * 
	 * GridRef -> Up-Down, Left-Right, 1 cell mesure 1.0f u.m.
	 * ScreenRef -> Down-Up, Left-Right, 1 cell 32 pix
	 * 
	 * Do linear transformation:
	 * 
	 *  - get the center of the cell
	 *  - rebase to the screen origin (left, down) 
	 *  - scale to the configured num pix / cell 
	 * @param Position
	 * @return
	 */
	public Vector2D<Float>  gridPositionToScreenPosition(Vector2D<Float> gridRef ){		
		//(0,-1) - invert y axe
		Vector2D<Float> orient = new Vector2D<Float>(1.0f, -1.0f);
		
		Vector2D<Float> moveUp = new Vector2D<Float>(0.0f, (float)(screenDimensions.getScreenHeight() - screenDimensions.getHudHeight()) );
		
		float scale = screenDimensions.getCellSize();
		
		return gridRef.mult(orient)//.add(bringToTheBorder)
				.mult(scale)
				.add(moveUp);

	}
}
