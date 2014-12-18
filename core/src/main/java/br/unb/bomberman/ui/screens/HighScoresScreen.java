package br.unb.bomberman.ui.screens;

import java.util.Collections;
import java.util.List;

import br.unb.bomberman.ui.screens.PlayerSpec.Player;
import br.unb.unbomber.GDXGame;
import br.unb.unbomber.Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * Screen to show TOP 10 scores Scores are stored in a json file
 * 
 * @author zidenis
 */
public class HighScoresScreen implements Screen {

	final GDXGame game;
	
	private Texture background;
	private OrthographicCamera camera;
	private Stage stage;

	public HighScoresScreen(final GDXGame game) {
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
		game.batch.draw(Assets.highScoresRegion,
				400 - (Assets.highScoresRegion.getRegionWidth() / 2), 420);
		game.batch.flush();
		Gdx.input.setInputProcessor(stage);
		stage.act();
		stage.draw();
		game.batch.end();
		if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)
				|| Gdx.input.isTouched()) {
			dispose();
			game.setScreen(game.mainMenuScreen);
		}
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
		stage.addActor(loadScores());
	}

	private Table loadScores() {
		Table table = new Table();
		LabelStyle style = new LabelStyle(Assets.font, Color.WHITE);
		Skin skin = new Skin();
		skin.add("default", style, LabelStyle.class);
		List<Player> players = Settings.getScores();
		Collections.sort(players);
		for (int pos = 0; pos < 10 && pos < players.size(); pos++) {
			Label name = new Label(String.valueOf(pos+1) + ". " + players.get(pos).getPlayerName(), skin);
			Label score = new Label(String.valueOf(players.get(pos).getScore()), skin);
			table.add(name).pad(2).left();
			table.add(score).pad(2).right();
			table.row();
		}
		table.setFillParent(true);
		return table;
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
