package br.unb.unbomber;

import br.unb.bomberman.ui.screens.Assets;
import br.unb.bomberman.ui.screens.MainMenuScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GDXGame extends Game {
    
    public final String FIRST_STAGE_LEVEL_ID = "stage";
    public final String TEST_STAGE_EXPLOSION = "test/explosion";
    public final String TEST_RENDERIZATION = "test/renderization";
	public SpriteBatch batch;
	
	public MainMenuScreen mainMenuScreen; 
	
    /**
     *  Load the assets and 
     */
	@Override 
	public void create () {
        batch = new SpriteBatch();
		
        Settings.load();
		Assets.load();                
		
		mainMenuScreen = new MainMenuScreen(this);
		this.setScreen(mainMenuScreen);
	}

    public void render() {
        super.render(); //important!
    }
    
    public void dispose() {
        batch.dispose();
    }
}
