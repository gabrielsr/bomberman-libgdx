package br.unb.bomberman.ui.screens;

import br.unb.unbomber.LibgdxGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameScreen implements Screen {
	final GDXGame game;

	OrthographicCamera camera;

	private static final int FRAME_COLS = 4; // #1
	private static final int FRAME_ROWS = 1; // #2

	Animation walkAnimationLeft;
	Animation walkAnimationRight;
	Animation walkAnimationUp;
	Animation walkAnimationDown;

	Texture walkSheetLeft;
	Texture walkSheetRight;
	Texture walkSheetUp;
	Texture walkSheetDown;

	SpriteBatch spriteBatch;
	TextureRegion currentFrame;
	int direction;

	float stateTime;

	int positionX = 50;
	int positionY = 50;

	LibgdxGame simulation;

	public GameScreen(GDXGame game, Object batch) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		// Os arquivos estão invertidos para permitirem o efeito de moonwalk
		// Because the zoeira never ends

		walkSheetLeft = new Texture(Gdx.files.local("right.png"));
		walkAnimationLeft = createWalkingFrame(walkSheetLeft);

		walkSheetRight = new Texture(Gdx.files.internal("left.png"));
		walkAnimationRight = createWalkingFrame(walkSheetRight);

		walkSheetUp = new Texture(Gdx.files.internal("front.png"));
		walkAnimationUp = createWalkingFrame(walkSheetUp);

		walkSheetDown = new Texture(Gdx.files.internal("behind.png"));
		walkAnimationDown = createWalkingFrame(walkSheetDown);

		spriteBatch = new SpriteBatch(); // #12
		stateTime = 0f;

		simulation = new LibgdxGame(game.batch, GDXGame.stage);
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
		Gdx.gl.glClearColor(1f, 1f, 1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		stateTime += Gdx.graphics.getDeltaTime(); // #15

		game.batch.begin();

		simulation.tick();

		game.batch.draw(currentFrame, positionX, positionY, 154, 126);

		game.batch.end();
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

}
