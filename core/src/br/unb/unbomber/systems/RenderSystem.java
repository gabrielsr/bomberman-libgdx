package br.unb.unbomber.systems;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import br.un.unbomber.components.Transform;
import br.un.unbomber.components.Visual;
import br.unb.bomberman.ui.screens.ScreenDimensions;
import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Draw;
import br.unb.unbomber.component.Movable;
import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.Component;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.gridphysics.Vector2D;
import br.unb.unbomber.ui.skin.LoadTexture;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class RenderSystem extends BaseSystem {

	
	private OrthographicCamera cam;
	private Array<Entity> renderQueue;
	private Comparator<Entity> comparator;

	SpriteBatch batch;
	
	private ScreenDimensions screenDimensions;
	
	
	
	public RenderSystem(EntityManager entityManager) {
		super(entityManager);
	}

	public RenderSystem(EntityManager entityManager,
			SpriteBatch batch) {
		super(entityManager);
		this.batch = batch;
		
		comparator = new Comparator<Entity>() {
			@Override
			public int compare(Entity entityA, Entity entityB) {
				return (int)Math.signum(getY(entityB) - getY(entityA));
			}

			private int getY(Entity entityA) {
				CellPlacement placement = (CellPlacement)getEntityManager().getComponent(CellPlacement.class, entityA.getEntityId());
				return placement.getCellY();
			}
		};
		
		this.batch = batch;

	}

	@Override
	public void start() {
		screenDimensions = new ScreenDimensions();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, screenDimensions.getScreenWidth(), screenDimensions.getScreenHeight());
	}

	
	@Override
	public void update() {
		LoadTexture.load(getEntityManager());

		List<Component> renderQueue = new ArrayList<Component>();
		renderQueue.addAll(getEntityManager().getComponents(Visual.class));
		
		//renderQueue.sort(comparator);
		
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		
		for (Component visComponent : renderQueue) {
			Visual vis = (Visual) visComponent;
			if (vis.getRegion() == null) {
				continue;
			}

			
			CellPlacement cellPlacement = (CellPlacement) getEntityManager().getComponent(CellPlacement.class, vis.getEntityId());
			if(cellPlacement==null){
				Draw draw = (Draw) getEntityManager().getComponent(Draw.class,  vis.getEntityId());
				//LOGGER.log(Level.SEVERE, "trying to draw a "+ draw.getType() +"\n But it has not a placement");
				continue;
			}
			
			Vector2D<Float> screenPosition;

			float width = vis.getRegion().getRegionWidth();
			float height = vis.getRegion().getRegionHeight();
			
			Movable movable = (Movable) getEntityManager().getComponent(Movable.class, vis.getEntityId());
			
			if(movable==null){
				
				screenPosition = gridPositionToScreenPosition(cellPlacement.centerPosition(), 
						vis.getTransform(), width,height );
			}else{
				screenPosition = gridPositionToScreenPosition(cellPlacement.centerPosition(), 
						vis.getTransform(), width,height, movable);
			}
			
			Transform t = vis.getTransform();
			

			float originX = width * 0.5f;
			float originY = height * 0.5f;			
			
//			batch.draw(vis.getRegion(),
//					screenPosition.getX(), 
//					screenPosition.getY());
			
			batch.draw(vis.getRegion(),
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
	
	public Visual getVisual(Entity entity){
		Visual visual = (Visual)getEntityManager().getComponent(Visual.class, entity.getEntityId());
		return visual;
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
	 * @param cellPlacement
	 * @return
	 */
	public Vector2D<Float>  gridPositionToScreenPosition(Vector2D<Float> gridRef, 
			Transform gameTranformation, float width, float height ){		
		//(0,-1) - invert y axe
		Vector2D<Float> orient = new Vector2D<Float>(1.0f, -1.0f);

		Vector2D<Float> bringToTheBorder = new Vector2D<Float>(0.0f, -0.5f*screenDimensions.getCellSize());
		
		Vector2D<Float> moveUp = new Vector2D<Float>(0.0f, (float)(screenDimensions.getScreenHeight() - screenDimensions.getHudHeight()) );
		
		float scale = screenDimensions.getCellSize();
		
		
		Vector2D<Float> repositeTheCenter = new Vector2D<Float>(-0.5f * width, -0.5f *  height);
		
		/** Transformations for the game */
		Vector2D<Float> gameTransSum = new Vector2D<Float>(gameTranformation.dx, gameTranformation.dy);

		return gridRef.mult(orient)//.add(bringToTheBorder)
				.mult(scale)
				.add(moveUp)//.add(transformC)
				.add(gameTransSum)
				.add(repositeTheCenter);

	}

	public Vector2D<Float>  gridPositionToScreenPosition(Vector2D<Float> gridRef, 
			Transform gameTranformation, float width, float height, Movable movable){

		/** Transformations for the movement inside a cell */
		gridRef = gridRef.add(movable.getCellDisplacement());
				
		return gridPositionToScreenPosition(gridRef, 
				gameTranformation, width,height );
	}
}
