package br.unb.bomberman.ui.screens;

import br.unb.unbomber.GDXGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MainMenuScreen implements Screen {

	final GDXGame game;
	
	private static final int    FRAME_COLS = 4;     // #1
    private static final int    FRAME_ROWS = 1;     // #2
    
    private static final int   TOP_X  = 800;
    private static final int   TOP_Y  = 320;
    private static final int   DOWN_X = -126;
    private static final int   DOWN_Y = 30;
    
    Texture           background;
    OrthographicCamera camera;
    
    Animation           walkAnimationRightMoonWalker;
	Animation           walkAnimationLeftMoonWalker;
	Animation           walkAnimationRightZombie;
	Animation           walkAnimationLeftZombie;
	
	Texture             walkSheetRightMoonWalker;
	Texture             walkSheetLeftMoonWalker;
	Texture             walkSheetRightZombie;
	Texture             walkSheetLeftZombie;
	
	TextureRegion       currentFrameMoonWalker; 
	TextureRegion       currentFrameZombie; 

	int initialPositionMoonWalker_X = DOWN_X;
	int initialPositionMoonWalker_Y = DOWN_Y;
	int initialPositionZombie_X = TOP_X;
	int initialPositionZombie_Y = TOP_Y;
	boolean isMoonWalkerWalkingRight = true;
	int steps = 0;
	float stateTime = 0f;
	
	// Main Menu Components
	final int INIT_POS_X_MENU = 160;
	final int INIT_POS_Y_MENU = 290;
	final int MENU_ITEN_HEIGHT = 50;
	final int MENU_ITEN_WIDTH = 350;
	private Stage stage;
	private Skin skin;
	private Pixmap pixmap;
	private TextButton button;
	private TextButtonStyle textButtonStyle;
	

    public MainMenuScreen(final GDXGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        
        //The background
        background = new Texture(Gdx.files.local("background.png"));       
        // The MoonWalker
        walkSheetRightMoonWalker = new Texture(Gdx.files.local("data/character1/walking-left.png"));
        walkAnimationRightMoonWalker = createWalkingFrame( walkSheetRightMoonWalker);
        walkSheetLeftMoonWalker = new Texture(Gdx.files.local("data/character1/walking-right.png"));
        walkAnimationLeftMoonWalker = createWalkingFrame( walkSheetLeftMoonWalker);
        // The Zoombie
        walkSheetRightZombie = new Texture(Gdx.files.internal("data/character2/walking-right.png"));
        walkAnimationRightZombie = createWalkingFrame( walkSheetRightZombie);
        walkSheetLeftZombie = new Texture(Gdx.files.internal("data/character2/walking-left.png"));
        walkAnimationLeftZombie = createWalkingFrame( walkSheetLeftZombie);
        
        // Game Main Menu
        //TODO: refactoring 
        stage = new Stage();
        stage.clear();
        Gdx.input.setInputProcessor(stage);
        pixmap = new Pixmap(100, 100, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin = new Skin();
		skin.add("white", new Texture(pixmap));
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.checked = skin.newDrawable("white", Color.BLACK);
		textButtonStyle.font = new BitmapFont(Gdx.files.internal("data/font.fnt"), Gdx.files.internal("data/font.png"), false);
		textButtonStyle.font.setScale(1.6f);
        button = new TextButton("New Game", textButtonStyle);
        button.setPosition(INIT_POS_X_MENU, INIT_POS_Y_MENU-MENU_ITEN_HEIGHT*0);
        button.setHeight(MENU_ITEN_HEIGHT);
        button.setWidth(MENU_ITEN_WIDTH);
        button.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Assets.playSound(Assets.clickSound);
				Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				game.getScreen().dispose();
				game.setScreen(new GameScreen(game, game.FIRST_STAGE_LEVEL_ID));
			}
		});
        stage.addActor(button);
        button = new TextButton("High Scores", textButtonStyle);
        button.setPosition(INIT_POS_X_MENU, INIT_POS_Y_MENU-MENU_ITEN_HEIGHT*1);
        button.setHeight(MENU_ITEN_HEIGHT);
        button.setWidth(MENU_ITEN_WIDTH);
        button.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Assets.playSound(Assets.clickSound);
				game.getScreen().dispose();
			}
		});
        stage.addActor(button);
        button = new TextButton("Settings", textButtonStyle);
        button.setPosition(INIT_POS_X_MENU, INIT_POS_Y_MENU-MENU_ITEN_HEIGHT*2);
        button.setHeight(MENU_ITEN_HEIGHT);
        button.setWidth(MENU_ITEN_WIDTH);
        button.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Assets.playSound(Assets.clickSound);
				game.getScreen().dispose();
			}
		});
        stage.addActor(button);
        button = new TextButton("How to Play", textButtonStyle);
        button.setPosition(INIT_POS_X_MENU, INIT_POS_Y_MENU-MENU_ITEN_HEIGHT*3);
        button.setHeight(MENU_ITEN_HEIGHT);
        button.setWidth(MENU_ITEN_WIDTH);
        button.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Assets.playSound(Assets.clickSound);
				game.getScreen().dispose();
			}
		});
        stage.addActor(button);
    }
    
    private Animation createWalkingFrame( Texture walkSheet){
		
		TextureRegion[][] aux = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS);
		TextureRegion[]   walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        
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
        
        //inverte quando chegar no final
        if(isMoonWalkerWalkingRight && initialPositionMoonWalker_X + steps >= TOP_X){
        	initialPositionMoonWalker_X = TOP_X;
        	initialPositionMoonWalker_Y = TOP_Y;
        	initialPositionZombie_X = DOWN_X;
        	initialPositionZombie_Y = DOWN_Y;
        	isMoonWalkerWalkingRight = false;
        	steps = 0;        	
        }else if( !isMoonWalkerWalkingRight && initialPositionMoonWalker_X - steps <= DOWN_X){
        	initialPositionMoonWalker_X = DOWN_X;
        	initialPositionMoonWalker_Y = DOWN_Y;
        	initialPositionZombie_X = TOP_X;
        	initialPositionZombie_Y = TOP_Y;
        	isMoonWalkerWalkingRight = true;
        	steps = 0;
        }
        if(isMoonWalkerWalkingRight){
        	currentFrameMoonWalker = walkAnimationRightMoonWalker.getKeyFrame(stateTime, true);
        	currentFrameZombie = walkAnimationLeftZombie.getKeyFrame(stateTime, true);
        }else{
        	currentFrameMoonWalker = walkAnimationLeftMoonWalker.getKeyFrame(stateTime, true);
        	currentFrameZombie = walkAnimationRightZombie.getKeyFrame(stateTime, true);
        }
        
        game.batch.begin();
        game.batch.draw(background, -28, -122, 864, 720);
        if(isMoonWalkerWalkingRight){
        	game.batch.draw(currentFrameMoonWalker, initialPositionMoonWalker_X + steps, initialPositionMoonWalker_Y, 154, 126); 
        	game.batch.draw(currentFrameZombie, initialPositionZombie_X - steps, initialPositionZombie_Y, 154, 126); 
        }else{
        	game.batch.draw(currentFrameMoonWalker, initialPositionMoonWalker_X - steps, initialPositionMoonWalker_Y, 154, 126); 
        	game.batch.draw(currentFrameZombie, initialPositionZombie_X + steps, initialPositionZombie_Y, 154, 126);
        }
        Assets.font.draw(game.batch, "NEED FIX: Why the zombie disapear if we delete/comment this line?", 0, 0);
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
		stage.dispose();
		// TODO Auto-generated method stub
		
	}
        //...Rest of class omitted for succinctness.
}