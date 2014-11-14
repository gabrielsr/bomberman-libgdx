package br.unb.bomberman.ui.screens;

import br.unb.unbomber.core.StageSpec;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;

public class GDXGame extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    
    public static StageSpec stage = null;
	
    
    
	@Override
	public void create () {
        batch = new SpriteBatch();
        //Use LibGDX's default Arial font.
        font = new BitmapFont();
        
        
    	Json json = new Json();
		FileHandle stageFile = Gdx.files.local("stage.json");
		GDXGame.stage = json.fromJson(StageSpec.class, stageFile.reader());
        
		this.setScreen(new MainMenuScreen(this));
		
		
		
	}

    public void render() {
        super.render(); //important!
    }
    
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
