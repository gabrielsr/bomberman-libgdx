package br.unb.bomberman.ui.screens;

import br.unb.unbomber.GDXGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MainMenuScreen implements Screen {

	final GDXGame game;

	private static final int FRAME_COLS = 4; // #1
	private static final int FRAME_ROWS = 1; // #2

	private static final int TOP_X = 800;
	private static final int TOP_Y = 320;
	private static final int DOWN_X = -126;
	private static final int DOWN_Y = 30;

	Texture background;
	OrthographicCamera camera;

	Animation walkAnimationRightMoonWalker;
	Animation walkAnimationLeftMoonWalker;
	Animation walkAnimationRightZombie;
	Animation walkAnimationLeftZombie;

	Texture walkSheetRightMoonWalker;
	Texture walkSheetLeftMoonWalker;
	Texture walkSheetRightZombie;
	Texture walkSheetLeftZombie;

	TextureRegion currentFrameMoonWalker;
	TextureRegion currentFrameZombie;

	int initialPositionMoonWalker_X = DOWN_X;
	int initialPositionMoonWalker_Y = DOWN_Y;
	int initialPositionZombie_X = TOP_X;
	int initialPositionZombie_Y = TOP_Y;
	boolean isMoonWalkerWalkingRight = true;
	int steps = 0;
	float stateTime = 0f;

	private Stage stage;

	public MainMenuScreen(final GDXGame gdxGame) {
		this.game = gdxGame;
	}

	private Animation createWalkingFrame(Texture walkSheet) {

		TextureRegion[][] aux = TextureRegion.split(walkSheet,
				walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight()
						/ FRAME_ROWS);
		TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];

		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkFrames[index++] = aux[i][j];
			}
		}
		return new Animation(0.25f, walkFrames);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		stateTime += Gdx.graphics.getDeltaTime();
		steps += 2;

		// inverte quando chegar no final
		if (isMoonWalkerWalkingRight
				&& initialPositionMoonWalker_X + steps >= TOP_X) {
			initialPositionMoonWalker_X = TOP_X;
			initialPositionMoonWalker_Y = TOP_Y;
			initialPositionZombie_X = DOWN_X;
			initialPositionZombie_Y = DOWN_Y;
			isMoonWalkerWalkingRight = false;
			steps = 0;
		} else if (!isMoonWalkerWalkingRight
				&& initialPositionMoonWalker_X - steps <= DOWN_X) {
			initialPositionMoonWalker_X = DOWN_X;
			initialPositionMoonWalker_Y = DOWN_Y;
			initialPositionZombie_X = TOP_X;
			initialPositionZombie_Y = TOP_Y;
			isMoonWalkerWalkingRight = true;
			steps = 0;
		}
		if (isMoonWalkerWalkingRight) {
			currentFrameMoonWalker = walkAnimationRightMoonWalker.getKeyFrame(
					stateTime, true);
			currentFrameZombie = walkAnimationLeftZombie.getKeyFrame(stateTime,
					true);
		} else {
			currentFrameMoonWalker = walkAnimationLeftMoonWalker.getKeyFrame(
					stateTime, true);
			currentFrameZombie = walkAnimationRightZombie.getKeyFrame(
					stateTime, true);
		}

		game.batch.begin();
		game.batch.draw(background, -28, -122, 864, 720);
		if (isMoonWalkerWalkingRight) {
			game.batch.draw(currentFrameMoonWalker, initialPositionMoonWalker_X
					+ steps, initialPositionMoonWalker_Y, 154, 126);
			game.batch.draw(currentFrameZombie,
					initialPositionZombie_X - steps, initialPositionZombie_Y,
					154, 126);
		} else {
			game.batch.draw(currentFrameMoonWalker, initialPositionMoonWalker_X
					- steps, initialPositionMoonWalker_Y, 154, 126);
			game.batch.draw(currentFrameZombie,
					initialPositionZombie_X + steps, initialPositionZombie_Y,
					154, 126);
		}

		// Forces the render
		game.batch.flush();
		Gdx.input.setInputProcessor(stage);
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
		// The background
		background = new Texture(Gdx.files.local("background.png"));
		// The MoonWalker
		walkSheetRightMoonWalker = new Texture(
				Gdx.files.local("data/character1/walking-left.png"));
		walkAnimationRightMoonWalker = createWalkingFrame(walkSheetRightMoonWalker);
		walkSheetLeftMoonWalker = new Texture(
				Gdx.files.local("data/character1/walking-right.png"));
		walkAnimationLeftMoonWalker = createWalkingFrame(walkSheetLeftMoonWalker);
		// The Zoombie
		walkSheetRightZombie = new Texture(
				Gdx.files.internal("data/character2/walking-right.png"));
		walkAnimationRightZombie = createWalkingFrame(walkSheetRightZombie);
		walkSheetLeftZombie = new Texture(
				Gdx.files.internal("data/character2/walking-left.png"));
		walkAnimationLeftZombie = createWalkingFrame(walkSheetLeftZombie);
		// Game Main Menu Building
		stage = new Stage();
		stage.clear();
		MenuButtonFactory factory = new MenuButtonFactory();
		stage.addActor(factory.makeMenuButton(game, "New Game", new GameScreen(game, game.FIRST_STAGE_LEVEL_ID)));
		stage.addActor(factory.makeMenuButton(game, "Highscores", new HighScoresScreen(game)));
		stage.addActor(factory.makeMenuButton(game, "Settings", new SettingsScreen(game)));
		stage.addActor(factory.makeMenuButton(game, "How to Play", new HowToPlayScreen(game)));
		stage.addActor(factory.makeMenuButton(game, "Tests", new TestListMenuScreen(game)));
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
		walkSheetRightMoonWalker.dispose();
		walkSheetLeftMoonWalker.dispose();
		walkSheetRightZombie.dispose();
		walkSheetLeftZombie.dispose();
		background.dispose();
		stage.dispose();
	}
}