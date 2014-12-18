package br.unb.bomberman.ui.screens;

import br.unb.unbomber.GDXGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * A simple help screen to give instructions to player
 * 
 * @author zidenis
 */
public class HowToPlayScreen implements Screen {

	final GDXGame game;
	
	private Texture background;
	private OrthographicCamera camera;
	private Stage stage;

	public HowToPlayScreen(final GDXGame game) {
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
		game.batch.draw(Assets.helpRegion, 400 - (Assets.helpRegion.getRegionWidth() / 2), 420);
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
		stage.addActor(help());
	}

	private Table help() {
		Table table = new Table();
		table.setFillParent(true);
		LabelStyle style = new LabelStyle(Assets.font, Color.WHITE);
		Skin skin = new Skin();
		skin.add("default", style, LabelStyle.class);
		table.add(new Label("You're the amazing bomberman ", skin)).colspan(7);
		table.add(new AnimatedImage(Assets.bomberAnim)).width(64).height(64);
		table.row();
        table.add(new Label("Complete the levels by strategically", skin)).colspan(9);
        table.row();
        table.add(new Label("placing bombs", skin)).colspan(3);
        table.add(new AnimatedImage(Assets.bomb)).width(64).height(64);
        table.add(new Label("to kill enemies", skin)).colspan(4);
        table.row();
        table.add(new AnimatedImage(Assets.zombieAnim)).width(64).height(64);
        table.add(new Label("and destroy softblocks.", skin)).colspan(6);
        table.add(new Image(Assets.backgroundSBlock)).width(64).height(64);
        table.row();
        table.add(new Label("Hardblocks", skin)).colspan(3);
        table.add(new Image(Assets.backgroundHBlock)).width(64).height(64);
        table.add(new Label("are unbreakable.", skin)).colspan(4);
        table.row();
        table.add(new Label("Beware of explosions", skin)).colspan(6);
        table.add(new AnimatedImage(Assets.explosionAnim)).width(64).height(64);
        table.add(new Label("There", skin)).colspan(1);
        table.row();
        table.add(new Label("is no safe place in planet bomber.", skin)).colspan(9);
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
	
	public static class AnimatedImage extends Image {
		protected Animation animation = null;
		private float stateTime = 0;
		public AnimatedImage(Animation animation) {
			super(animation.getKeyFrame(0));
			this.animation = animation;
		}
		@Override
		public void act(float delta) {
			((TextureRegionDrawable)getDrawable()).setRegion(animation.getKeyFrame(stateTime+=delta, true));
			super.act(delta);
		}
	}
}
