package br.unb.bomberman.ui.screens;

import br.unb.unbomber.GDXGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MainMenuScreen implements Screen {

    final GDXGame game;
    
    OrthographicCamera camera;

    public MainMenuScreen(final GDXGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        
        game.batch.begin();
        
        Assets.font.draw(game.batch, "Welcome to Bomberman!!! ", 100, 150);
        Assets.font.draw(game.batch, "Tap anywhere to begin!", 100, 100);
        
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game, game.FIRST_STAGE_LEVEL_ID));
            dispose();
        }
    }

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}


        //...Rest of class omitted for succinctness.

}