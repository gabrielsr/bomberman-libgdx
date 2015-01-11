package br.unb.unbomber.systems;

import static org.junit.Assert.assertEquals;
import net.mostlyoriginal.api.event.common.EventManager;

import org.junit.Before;
import org.junit.Test;

import br.unb.bomberman.ui.screens.ScreenDimensions;
import br.unb.gridphysics.Vector2D;

import com.artemis.EntityManager;
import com.artemis.World;

public class RenderSystemTestCase {

	/** Gerenciador das entidades. */
	EntityManager entityManager;

	ScreenPositionSystem screenPositionSystem;
	
	ScreenDimensions screenDimensions;
	
	World world;
	
	@Before
	public void setUp() throws Exception {

		screenPositionSystem = new ScreenPositionSystem();
		
		world = new World();
		world.setSystem(screenPositionSystem);
		
		world.setManager(new EventManager());
		
		screenDimensions = new ScreenDimensions();
		
		world.initialize();
	}


	@Test
	public void testGridPositionToScreenPosition() {
		
		Vector2D<Float> screenPosition = screenPositionSystem.gridPositionToScreenPosition(new Vector2D<Float>(0.0f, 0.0f));
		
		assertEquals("x position", 0, screenPosition.getX().intValue());
	
		assertEquals("y position", screenDimensions.getScreenHeight() 
				- screenDimensions.getHudHeight() - screenDimensions.getCellSize(),
				 screenPosition.getY().intValue());
		
	}

}
