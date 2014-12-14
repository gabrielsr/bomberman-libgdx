package br.unb.bomberman.ui.screens;

import br.unb.unbomber.GDXGame;
import br.unb.unbomber.Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Screen for game configurations
 * @author zidenis
 */
public class SettingsScreen implements Screen {

	final GDXGame game;
	private Texture background;
	private OrthographicCamera camera;
	private Stage stage;
	private MenuButtonFactory buttonFactory;
	
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
		buttonFactory = new MenuButtonFactory();
		stage = new Stage();
		stage.clear();
		if (Settings.musicEnabled) {
			stage.addActor(buttonFactory.makeMenuButton(game, "Disable Music", new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					Assets.playSound(Assets.clickSound);
					Settings.disableMusic();
					game.getScreen().show();
				}	
			}));
		} else {
			stage.addActor(buttonFactory.makeMenuButton(game, "Enable Music", new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					Assets.playSound(Assets.clickSound);
					Settings.enableMusic();
					game.getScreen().show();
				}	
			}));
		}
		if (Settings.soundEnabled) {
			stage.addActor(buttonFactory.makeMenuButton(game, "Disable Sound", new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					Settings.soundEnabled = false;
					game.getScreen().show();
				}	
			}));			
		} else {
			stage.addActor(buttonFactory.makeMenuButton(game, "Enable Sound", new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					Settings.soundEnabled = true;
					Assets.playSound(Assets.clickSound);
					game.getScreen().show();
				}	
			}));	
		}
		if (!Settings.scoresReseted) {
			stage.addActor(buttonFactory.makeMenuButton(game, "Reset Scores", new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					Assets.playSound(Assets.clickSound);
					Settings.resetScores();
					game.getScreen().show();
				}	
			}));
		}
		stage.addActor(buttonFactory.makeMenuButton(game, "Back", game.mainMenuScreen));
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
