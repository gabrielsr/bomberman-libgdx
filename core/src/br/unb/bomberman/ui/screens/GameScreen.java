package br.unb.bomberman.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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

	
	private static final int    FRAME_COLS =3;     // #1
    private static final int    FRAME_ROWS = 1;     // #2
	
	Animation           walkAnimationLeft;
	Animation           walkAnimationRight;
	Texture             walkSheetLeft;
	Texture             walkSheetRight;// #4
    TextureRegion[]         walkFrames;     // #5
    SpriteBatch         spriteBatch;        // #6
    TextureRegion           currentFrame;  
    int direction;
    
    float stateTime; 
    
    int position = 0;
    
	public GameScreen(GDXGame game) {
        this.game = game;
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        
        walkSheetLeft = new Texture(Gdx.files.internal("right.jpg")); // #9
        walkSheetRight = new Texture(Gdx.files.internal("left.jpg"));
        TextureRegion[][] tmp = TextureRegion.split(walkSheetLeft, walkSheetLeft.getWidth()/FRAME_COLS, walkSheetLeft.getHeight()/FRAME_ROWS);              // #10
        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        walkAnimationLeft = new Animation(0.25f, walkFrames);      // #11
        tmp = TextureRegion.split(walkSheetRight, walkSheetRight.getWidth()/FRAME_COLS, walkSheetRight.getHeight()/FRAME_ROWS);              // #10
        
        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        walkAnimationRight = new Animation(0.25f, walkFrames);
        spriteBatch = new SpriteBatch();                // #12
        stateTime = 0f;
        
        direction = Keys.A;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1f, 1f, 1f, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		stateTime += Gdx.graphics.getDeltaTime();           // #15
		if (Gdx.input.isKeyPressed(Keys.A)) {
			direction = Keys.A;
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			direction = Keys.D;
		}
		if (Gdx.input.isKeyPressed(Keys.W)) {
			direction = Keys.W;
		}
		if (Gdx.input.isKeyPressed(Keys.S)) {
			direction = Keys.S;
		}
//		if (direction == Keys.A) {
//			currentFrame = walkAnimationLeft.getKeyFrame(stateTime, true);
//			positionX--;
//		}
//		else if (direction == Keys.D) {
//			currentFrame = walkAnimationRight.getKeyFrame(stateTime, true);
//			positionX++;
//		}else if (direction == Keys.W) {
//			currentFrame = walkAnimationLeft.getKeyFrame(stateTime, true);
//			positionY--;
//		}
//		else if (direction == Keys.S) {
//			currentFrame = walkAnimationRight.getKeyFrame(stateTime, true);
//			positionY++;
//		}
//		
//        
//        if(positionX >=800){
//        	positionX = 0;
//        }else if(positionX <=0){
//        	positionX = 800;
//        }
        
		game.batch.begin();

		
		game.batch.draw(currentFrame, position, 50, 256, 128);  
		
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
