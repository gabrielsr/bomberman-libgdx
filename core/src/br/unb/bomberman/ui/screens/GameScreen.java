/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package br.unb.bomberman.ui.screens;

import br.unb.unbomber.BomberMatchWithUi;
import br.unb.unbomber.BomberMatchWithUi.State;
import br.unb.unbomber.GDXGame;
import br.unb.unbomber.Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class GameScreen extends ScreenAdapter {
	static final int GAME_NOT_SELECTED = -1;
	static final int GAME_READY = 0;
	static final int GAME_RUNNING = 1;
	static final int GAME_PAUSED = 2;
	static final int GAME_LEVEL_END = 3;
	static final int GAME_OVER = 4;
	
	GDXGame game;

	OrthographicCamera guiCam;
	Vector3 touchPoint;

	Rectangle pauseBounds;
	Rectangle resumeBounds;
	Rectangle quitBounds;
	
	int lastScore;
	String scoreString;
	
	BomberMatchWithUi match;
	
	private int state;
	private final String stageId;

	public GameScreen (GDXGame game, String stageId) {
		this.game = game;
		this.stageId = stageId;
		state = GAME_NOT_SELECTED;
	}
	
	@Override
	public void show(){
		loadGame();
		match.start();
	}

	private void loadGame() {
		
		guiCam = new OrthographicCamera(320, 480);
		guiCam.position.set(320 / 2, 480 / 2, 0);
		touchPoint = new Vector3();
		
		match = new BomberMatchWithUi(game.batch, stageId);

		pauseBounds = new Rectangle(320 - 64, 480 - 64, 64, 64);
		resumeBounds = new Rectangle(160 - 96, 240, 192, 36);
		quitBounds = new Rectangle(160 - 96, 240 - 36, 192, 36);

		lastScore = 0;
		scoreString = "SCORE: 0";
		state = GAME_READY;
		
		pauseSystems();

		
	}

	public void update (float deltaTime) {
		if (deltaTime > 0.1f) deltaTime = 0.1f;

		match.update();
		
		switch (state) {
		case GAME_READY:
			updateReady();
			break;
		case GAME_RUNNING:
			updateRunning(deltaTime);
			break;
		case GAME_PAUSED:
			updatePaused();
			break;
		case GAME_LEVEL_END:
			updateLevelEnd();
			break;
		case GAME_OVER:
			updateGameOver();
			break;
		}
	}

	private void updateReady () {
		if (Gdx.input.justTouched()) {
			state = GAME_RUNNING;
			resumeSystems();
		}
	}

	private void updateRunning (float deltaTime) {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (pauseBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				Assets.music.setVolume(0f);
				state = GAME_PAUSED;
				pauseSystems();
				return;
			}
		}
		
		
		if (match.score != lastScore) {
			lastScore = match.score;
			scoreString = "SCORE: " + lastScore;
		}
		if (match.state == State.WORLD_STATE_NEXT_LEVEL) {
			game.setScreen(game.winAMatchScreen);
		}
		if (match.state == State.WORLD_STATE_GAME_OVER) {
			state = GAME_OVER;
			if (lastScore >= Settings.highscores[4])
				scoreString = "NEW HIGHSCORE: " + lastScore;
			else
				scoreString = "SCORE: " + lastScore;
			pauseSystems();
			Settings.addScore(lastScore);
			Settings.save();
		}
	}

	private void updatePaused () {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (resumeBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				Assets.music.setVolume(Settings.soundVolume);
				state = GAME_RUNNING;
				resumeSystems();
				return;
			}

			if (quitBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				Assets.music.setVolume(Settings.soundVolume);
				state = GAME_READY;
				game.getScreen().dispose();
				game.setScreen(game.mainMenuScreen);
				return;
			}
		}
	}

	private void updateLevelEnd () {
		if (Gdx.input.justTouched()) {
			match.removeAllEntities();
			
			//TODO get the next level
			String nextLevelStageId = "stage";
			
			match = new BomberMatchWithUi(game.batch, nextLevelStageId);
			match.score = lastScore;
			state = GAME_READY;
		}
	}

	private void updateGameOver () {
		if (Gdx.input.justTouched()) {
			game.setScreen(game.mainMenuScreen);
		}
	}

	public void drawUI () {
		guiCam.update();
		game.batch.setProjectionMatrix(guiCam.combined);
		game.batch.begin();
		switch (state) {
		case GAME_READY:
			presentReady();
			break;
		case GAME_RUNNING:
			presentRunning();
			break;
		case GAME_PAUSED:
			presentPaused();
			break;
		case GAME_LEVEL_END:
			presentLevelEnd();
			break;
		case GAME_OVER:
			presentGameOver();
			break;
		}
		game.batch.end();
	}

	private void presentReady () {
		game.batch.draw(Assets.ready, 160 - 192 / 2, 240 - 32 / 2, 192, 32);
	}

	private void presentRunning () {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.draw(Assets.pause, 320 - 64, 480 - 64, 64, 64);
		Assets.font.draw(game.batch, scoreString, 16, 480 - 20);
	}

	private void presentPaused () {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.draw(Assets.pauseMenu, 160 - 192 / 2, 240 - 96 / 2, 192, 96);
		Assets.font.draw(game.batch, scoreString, 16, 480 - 20);
	}

	private void presentLevelEnd () {
		String topText = "the princess is ...";
		String bottomText = "in another castle!";
		float topWidth = Assets.font.getBounds(topText).width;
		float bottomWidth = Assets.font.getBounds(bottomText).width;
		Assets.font.draw(game.batch, topText, 160 - topWidth / 2, 480 - 40);
		Assets.font.draw(game.batch, bottomText, 160 - bottomWidth / 2, 40);
	}

	private void presentGameOver () {
		game.batch.draw(Assets.gameOver, 160 - 160 / 2, 240 - 96 / 2, 160, 96);
		float scoreWidth = Assets.font.getBounds(scoreString).width;
		Assets.font.draw(game.batch, scoreString, 160 - scoreWidth / 2, 480 - 20);
	}
	
	private void pauseSystems() {
		//TODO
	}
	
	private void resumeSystems() {
		//TODO
	}

	@Override
	public void render (float delta) {
		update(delta);
		drawUI();
	}

	@Override
	public void pause () {
		if (state == GAME_RUNNING) {
			state = GAME_PAUSED;
			pauseSystems();
		}
	}
}