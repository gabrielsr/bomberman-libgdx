package br.unb.bomberman.ui.screens;

import br.unb.unbomber.GDXGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class WinScreen implements Screen {


		final GDXGame game;
		
		Texture           background;
	    OrthographicCamera camera;
	    
	    private Stage stage;
	    
	    public WinScreen (final GDXGame game){
	    	this.game = game;
	    }
	    
		@Override
		public void render(float delta) {

			Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
	        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	        camera.update();
	        game.batch.setProjectionMatrix(camera.combined);
	        
	        game.batch.begin();
	        game.batch.draw(background, -28, -122, 864, 720);
	        
	        // Forces the render
	        game.batch.flush();
	        
	        stage.act();
	        stage.draw();
	        game.batch.end();
			
			
		}

		@Override
		public void resize(int width, int height) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void show() {
	        camera = new OrthographicCamera();
	        camera.setToOrtho(false, 800, 480);
	        //The background
	        background = new Texture(Gdx.files.local("win.png"));
	        // Game Main Menu Building
	        stage = new Stage();
	        stage.clear();
	        Gdx.input.setInputProcessor(stage);
	        MenuButtonFactory factory = new MenuButtonFactory();
	        stage.addActor(factory.makeMenuButton(game, "New Game", new GameScreen(game, game.FIRST_STAGE_LEVEL_ID)));
	        stage.addActor(factory.makeMenuButton(game, "Quit", game.mainMenuScreen));
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
			stage.dispose();
			
		}

}
