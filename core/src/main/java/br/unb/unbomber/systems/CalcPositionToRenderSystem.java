package br.unb.unbomber.systems;

import org.apache.log4j.Logger;

import br.unb.bomberman.ui.screens.ScreenDimensions;
import br.unb.gridphysics.Vector2D;
import br.unb.unbomber.component.Ballistic;
import br.unb.unbomber.component.Draw;
import br.unb.unbomber.component.Movable;
import br.unb.unbomber.component.Position;
import br.unb.unbomber.component.Timer;
import br.unb.unbomber.components.Transform;
import br.unb.unbomber.components.Visual;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Wire;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

@Wire
public class CalcPositionToRenderSystem extends EntitySystem {

	
	ComponentMapper<Draw> cmDraw;
	
	ComponentMapper<Position> cmPosition;

	ComponentMapper<Visual> cmVisual;
	
	ComponentMapper<Movable>  cmMovable;

	private ComponentMapper<Ballistic> cmBallistic;

	private ComponentMapper<Timer> cmTimer;

	protected Logger LOGGER = Logger.getLogger("br.unb.unbomber.systems");

	private ScreenDimensions screenDimensions = new ScreenDimensions();

	public CalcPositionToRenderSystem() {
		super(Aspect.getAspectForAll(Visual.class));
	}

	
	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		
		for (Entity entity : entities) {
			
			Visual visual = cmVisual.get(entity);
			
			Vector2D<Float> screenPosition;

			float width = visual.getRegion().getRegionWidth();
			float height = visual.getRegion().getRegionHeight();

			Vector2D<Float> gridPosition;
			Position position = cmPosition.getSafe(entity);
			if(position != null){
				gridPosition = position.centerPosition();
			}else {
				Ballistic ballistic = cmBallistic.getSafe(entity);
				Timer ballisticTimer = cmTimer.getSafe(entity);
				gridPosition = calcBallisticPosition(ballistic, ballisticTimer);
			}
			
			
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
			
			visual.updatToDraw( screenPosition.getX(), 
					screenPosition.getY(),
					   originX, originY,
					   width, height,
					   t.scalex, t.scaley,
					   MathUtils.radiansToDegrees * t.rotation);
		}
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
		
	private Vector2D<Float> calcBallisticPosition(Ballistic ballistic, Timer timer) {
		Vector2D<Float>  actualDispl = ballistic.getDispl().toFloatVector().mult(timer.getProgress());
		
		Vector2D<Float> result = ballistic.getOrig().toFloatVector()
				.add(actualDispl)
				.add(new Vector2D<Float>(0.5f, 0.5f));
		return result;
	}

}
