package br.unb.unbomber.systems;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import br.unb.bomberman.ui.screens.ScreenDimensions;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.EntityManagerImpl;
import br.unb.unbomber.gridphysics.Vector2D;

public class RenderSystemTestCase {

	/** Gerenciador das entidades. */
	EntityManager entityManager;

	RenderSystem renderSystem;
	
	ScreenDimensions screenDimensions;

	@Before
	public void setUp() throws Exception {

		/** Inicia um sistema para cada caso de teste. */
		EntityManagerImpl.init();
		entityManager = EntityManagerImpl.getInstance();
		renderSystem = new RenderSystem(entityManager);
		
		screenDimensions = new ScreenDimensions();
	}

	@Test
	public void testGridPositionToScreenPosition() {
		
		Vector2D<Float> screenPosition = renderSystem.gridPositionToScreenPosition(new Vector2D<Float>(0.0f, 0.0f));
		
		assertEquals("x position", 0, screenPosition.getX().intValue());
	
		assertEquals("y position", screenDimensions.getScreenHeight() 
				- screenDimensions.getHudHeight() - screenDimensions.getCellSize(),
				 screenPosition.getY().intValue());
		
	}

}
