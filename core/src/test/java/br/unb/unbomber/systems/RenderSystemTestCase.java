package br.unb.unbomber.systems;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.EntityManagerImpl;
import br.unb.unbomber.gridphysics.Vector2D;

public class RenderSystemTestCase {

	/** Gerenciador das entidades. */
	EntityManager entityManager;

	
	RenderSystem renderSystem;


	@Before
	public void setUp() throws Exception {

		/** Inicia um sistema para cada caso de teste. */
		EntityManagerImpl.init();
		entityManager = EntityManagerImpl.getInstance();
		renderSystem = new RenderSystem(entityManager);
	}

	@Test
	public void testGridPositionToScreenPosition() {
		
		Vector2D<Float> resultPosition = renderSystem.gridPositionToScreenPosition(new Vector2D<Float>(0.0f, 0.0f));
		
		assertEquals("x position", resultPosition.getX().intValue(),16);
	
		assertEquals("y position", resultPosition.getY().intValue(),480 - 40 - 32 + 16);
		
	}

}
