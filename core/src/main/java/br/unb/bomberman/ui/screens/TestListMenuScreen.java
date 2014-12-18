package br.unb.bomberman.ui.screens;

import br.unb.unbomber.GDXGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class TestListMenuScreen implements Screen {

	final GDXGame game;
    
    OrthographicCamera camera;
    
	private Stage stage;

    public TestListMenuScreen(final GDXGame game) {
        this.game = game;
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
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
		stage = new Stage();
        stage.clear();
        Gdx.input.setInputProcessor(stage);
        MenuButtonFactory factory = new MenuButtonFactory();
        stage.addActor(factory.makeMenuButton(game, "Explosion", new GameScreen(game, game.TEST_STAGE_EXPLOSION)));
        stage.addActor(factory.makeMenuButton(game, "Renderization", new GameScreen(game, game.TEST_RENDERIZATION)));
        stage.addActor(factory.makeMenuButton(game, "Draw", new DrawScreen(game)));
        stage.addActor(factory.makeMenuButton(game, "Win", new WinScreen(game)));
        stage.addActor(factory.makeMenuButton(game, "Win a Match", new WinAMatchScreen(game)));
        stage.addActor(factory.makeMenuButton(game, "Back", game.mainMenuScreen));
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