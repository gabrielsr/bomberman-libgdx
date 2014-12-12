package br.unb.unbomber;

import br.unb.bomberman.ui.screens.Assets;
import br.unb.bomberman.ui.screens.DrawScreen;
import br.unb.bomberman.ui.screens.HighScoresScreen;
import br.unb.bomberman.ui.screens.MainMenuScreen;
import br.unb.bomberman.ui.screens.SettingsScreen;
import br.unb.bomberman.ui.screens.TestListMenuScreen;
import br.unb.bomberman.ui.screens.WinAMatchScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GDXGame extends Game {
    
    public final String FIRST_STAGE_LEVEL_ID = "stage";
    public final String TEST_STAGE_EXPLOSION = "test/explosion";
	
	public SpriteBatch batch;
	
    //Game Screens
	//We will keep a single instance of each screen around for the life of the game
	//so that new Screen objects are not being created every screen change
	public MainMenuScreen mainMenuScreen;
	public HighScoresScreen highScoresScreen;
	public SettingsScreen settingsScreen;
	public TestListMenuScreen testListMenuScreen;
	public WinAMatchScreen winAMatchScreen;
	public DrawScreen drawScreen;
	
    /**
     *  Load the assets and 
     */
	@Override public void create () {
        batch = new SpriteBatch();
		
        Settings.load();
		Assets.load();                
		
		mainMenuScreen = new MainMenuScreen(this);
		highScoresScreen = new HighScoresScreen(this);
		settingsScreen = new SettingsScreen(this);
		testListMenuScreen = new TestListMenuScreen(this);
		winAMatchScreen = new WinAMatchScreen(this);
		drawScreen = new DrawScreen(this);
		
		this.setScreen(mainMenuScreen);
	}

    public void render() {
        super.render(); //important!
    }
    
    public void dispose() {
        batch.dispose();
    }
}
