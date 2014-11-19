package br.unb.unbomber.systems;

import java.util.Comparator;

import br.un.unbomber.components.Transform;
import br.un.unbomber.components.Visual;
import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.StageSpec;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class RenderSystem extends BaseSystem {

	static final float FRUSTUM_WIDTH = 10;
	static final float FRUSTUM_HEIGHT = 15;
	static final float PIXELS_TO_METRES = 1.0f / 32.0f;
	
	private OrthographicCamera cam;
	private Array<Entity> renderQueue;
	private Comparator<Entity> comparator;

	SpriteBatch batch;
	
	public RenderSystem(EntityManager entityManager) {
		super(entityManager);
	}

	public RenderSystem(EntityManager entityManager,
			SpriteBatch batch) {
		super(entityManager);
		this.batch = batch;
				
		renderQueue = new Array<Entity>();
		
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
		
		cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		cam.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
		
		
	}

	@Override
	public void start() {
		loadSprites(null);
	}

	
	@Override
	public void update() {
		//super.update();
		
		renderQueue.sort(comparator);
		
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		
		for (Entity entity : renderQueue) {
			Visual vis =  getVisual(entity);
			
			if (vis.getRegion() == null) {
				continue;
			}
			
			Transform t = vis.getTransform();
		
			float width = vis.getRegion().getRegionWidth();
			float height = vis.getRegion().getRegionHeight();
			float originX = width * 0.5f;
			float originY = height * 0.5f;
			
			batch.draw(vis.getRegion(),
					   t.posx - originX, t.posy - originY,
					   originX, originY,
					   width, height,
					   t.scalex * PIXELS_TO_METRES, t.scaley * PIXELS_TO_METRES,
					   MathUtils.radiansToDegrees * t.rotation);
		}
		
		batch.end();
		renderQueue.clear();
	}
	


	private void loadSprites(StageSpec stage) {
		// TODO Auto-generated method stub

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

}
