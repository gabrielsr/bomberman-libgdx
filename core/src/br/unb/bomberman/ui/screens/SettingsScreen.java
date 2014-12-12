package br.unb.bomberman.ui.screens;

import br.unb.unbomber.GDXGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Screen for game configurations
 * @author zidenis
 */
public class SettingsScreen implements Screen {

	final GDXGame game;
	private Texture background;
	private OrthographicCamera camera;
	private Stage stage;

	public SettingsScreen(final GDXGame game) {
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
		game.batch.flush();
		Gdx.input.setInputProcessor(stage);
		stage.act();
		stage.draw();
		game.batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		background = new Texture(Gdx.files.local("background.png"));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		stage = new Stage();
		stage.clear();
		MenuButtonFactory factory = new MenuButtonFactory();
		stage.addActor(factory
				.makeMenuButton(game, "Back", game.mainMenuScreen));
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		background.dispose();
		stage.dispose();
	}
}
