package br.unb.bomberman.ui.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GDXGame extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
	
	@Override
	public void create () {
        batch = new SpriteBatch();
        //Use LibGDX's default Arial font.
        font = new BitmapFont();
		
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
