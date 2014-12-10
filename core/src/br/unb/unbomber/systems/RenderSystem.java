package br.unb.unbomber.systems;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import br.un.unbomber.components.Transform;
import br.un.unbomber.components.Visual;
import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.Component;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
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
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, 640, 480);
	}

	
	@Override
	public void update() {
		LoadTexture.load(getEntityManager());

		List<Component> renderQueue = new ArrayList<Component>();
		renderQueue.addAll(getEntityManager().getComponents(Visual.class));
		
		//renderQueue.sort(comparator);
		
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		
		for (Component visComponent : renderQueue) {
			Visual vis = (Visual) visComponent;
			if (vis.getRegion() == null) {
				continue;
			}
			
			CellPlacement cellPlacement = (CellPlacement) getEntityManager().getComponent(CellPlacement.class, vis.getEntityId());
			Transform t = updatePos(vis, cellPlacement);
		
			float width = vis.getRegion().getRegionWidth();
			float height = vis.getRegion().getRegionHeight();
			float originX = width * 0.5f;
			float originY = height * 0.5f;
			
			batch.draw(vis.getRegion(),
					   t.posx - originX, t.posy - originY,
					   originX, originY,
					   width, height,
					   t.scalex, t.scaley,
					   MathUtils.radiansToDegrees * t.rotation);
		}
		
		batch.end();
		renderQueue.clear();
	}
	
	private Transform updatePos(Visual vis, CellPlacement cellPlacement) {
		
		// TODO make it right (and prettier)
		
		vis.getTransform().posx = cellPlacement.getCellX()*32;
		vis.getTransform().posy = cellPlacement.getCellY()*-32 + 468;
		return vis.getTransform();
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
