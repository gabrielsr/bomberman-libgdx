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

public class TestListMenuScreen implements Screen {

	final GDXGame game;
	
	private static final int    FRAME_COLS = 4;     // #1
    private static final int    FRAME_ROWS = 1;     // #2
    
    private static final int   TOP_X  = 800;
    private static final int   TOP_Y  = 320;
    private static final int   DOWN_X = -126;
    private static final int   DOWN_Y = 30;
    
    Texture           background;
    OrthographicCamera camera;
    
    private boolean loaded = false;
    
	private Stage stage;

    public TestListMenuScreen(final GDXGame game) {
        this.game = game;
    }
    
    public void load(){
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
                
        // Game Main Menu Building
        stage = new Stage();
        stage.clear();
        Gdx.input.setInputProcessor(stage);
        MenuButtonFactory factory = new MenuButtonFactory();
        stage.addActor(factory.makeMenuButton(game, "Explosion", new GameScreen(game, game.TEST_STAGE_EXPLOSION)));
        stage.addActor(factory.makeMenuButton(game, "Renderization", new GameScreen(game, game.TEST_RENDERIZATION)));
    }
    
    @Override
    public void render(float delta) {
    	if(!loaded){
    		load();
    		loaded = true;
    	}
    	
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        
        game.batch.begin();
       
        // Forces the render
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