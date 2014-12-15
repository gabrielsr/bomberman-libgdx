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

import br.unb.unbomber.Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


/**
 * Store the assets used in the Game 
 * 
 * TODO remove unused imports
 *   
 * @author grodrigues
 *
 */
public class Assets {
	
	public static Texture background;
	public static TextureRegion backgroundGround;
	public static TextureRegion backgroundSBlock;
	public static TextureRegion backgroundHBlock;
	
	public static Texture hud;
	public static TextureRegion hudBar;
	
	public static Texture players;
	public static TextureRegion p1;
	public static TextureRegion p2;
	public static TextureRegion p3;
	public static TextureRegion p4;
	
	public static Texture box;
	public static TextureRegion boxScore;

	public static Texture walking;
	public static Texture walkingDown;
	public static Texture walkingZombie;
	public static Texture walkingZombieDown;
	public static TextureRegion walkingFront;
	public static TextureRegion walkingZombieFront;
	public static Animation bomberAnim;
	public static Animation zombieAnim;

	public static Texture bombs;
	public static Animation bomb;
	
	public static Texture explosion;	
	public static TextureRegion explosionCenter;
	public static Animation explosionAnim;
	
	public static Texture items;
	public static TextureRegion mainMenu;
	public static TextureRegion pauseMenu;
	public static TextureRegion ready;
	public static TextureRegion gameOver;
	public static TextureRegion highScoresRegion;
	public static TextureRegion helpRegion;
	public static TextureRegion logo;
	public static TextureRegion soundOn;
	public static TextureRegion soundOff;
	public static TextureRegion arrow;
	public static TextureRegion pause;
	public static TextureRegion spring;
	public static TextureRegion castle;
	public static Animation coinAnim;
	public static Animation bobJump;
	public static Animation bobFall;
	public static Animation bobHit;
	public static Animation squirrelFly;
	public static Animation platform;
	public static Animation breakingPlatform;
	public static BitmapFont font;

	public static Music music;
	public static Sound jumpSound;
	public static Sound highJumpSound;
	public static Sound hitSound;
	public static Sound coinSound;
	public static Sound clickSound;
	

	public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}

	public static void load () {
		background = loadTexture("map_tiles.png");
		backgroundGround = new TextureRegion(background, 0, 0, 32, 32);
		backgroundSBlock = new TextureRegion(background, 64, 0, 32, 32);
		backgroundHBlock = new TextureRegion(background, 96, 0, 32, 32);
		
		hud = loadTexture("HUD.png");
		hudBar = new TextureRegion(hud, 0, 0, 640, 64);
		
		players = loadTexture("pants_icons.png");
		p1 = new TextureRegion(players, 0, 0, 184/4 , 28);
		p2 = new TextureRegion(players, 46, 0, 184/4 , 28);
		p3 = new TextureRegion(players, 92, 0, 184/4 , 28);
		p4 = new TextureRegion(players, 138, 0, 184/4 , 28);
		
		box = loadTexture("score_rectangle.png");
		boxScore = new TextureRegion(box, 0, 0, 32, 32);

		walking = loadTexture("walking.png");

		walkingZombie = loadTexture("walking-zombie.png");
		walkingFront = new TextureRegion(walking, 8*218/12, 0, 218/12, 22);
		walkingZombieFront = new TextureRegion(walkingZombie, 8*218/12, 0, 218/12, 22);
		walkingDown = loadTexture("data/character1/walking-down.png");
		bomberAnim = new Animation(0.3f, new TextureRegion(walkingDown, 0, 0, 18, 22), new TextureRegion(walkingDown, 18, 0, 18, 22), 
				new TextureRegion(walkingDown, 36, 0, 18, 22), new TextureRegion(walkingDown, 54, 0, 18, 22));
		walkingZombieDown = loadTexture("data/character2/walking-down.png");
		zombieAnim = new Animation(0.2f, new TextureRegion(walkingZombieDown, 0, 0, 18, 22), new TextureRegion(walkingZombieDown, 18, 0, 18, 22), 
				new TextureRegion(walkingZombieDown, 36, 0, 18, 22), new TextureRegion(walkingZombieDown, 54, 0, 18, 22));
		
		bombs = loadTexture("bomb_anim.png");
		bomb = new Animation(0.3f, new TextureRegion(bombs, 0, 0, 32, 32), new TextureRegion(bombs, 32, 0, 32, 32),
				new TextureRegion(bombs, 64, 0, 32, 32), new TextureRegion(bombs, 32, 0, 32, 32));

		explosion = loadTexture("explosion.png");
		explosionCenter = new TextureRegion(explosion, 4*32, 0, 32, 32);
		explosionAnim = new Animation(0.15f, new TextureRegion(explosion, 4*32, 32, 32, 32),
				new TextureRegion(explosion, 4*32, 32, 32, 32), new TextureRegion(explosion, 2*32, 2*32, 32, 32),
				new TextureRegion(explosion, 2*32, 32, 32, 32), new TextureRegion(explosion, 0, 0, 32, 32),
				new TextureRegion(explosion, 4*32, 0, 32, 32), new TextureRegion(explosion, 0, 0, 32, 32),
				new TextureRegion(explosion, 2*32, 2*32, 32, 32));
		
		items = loadTexture("data/items.png");
		mainMenu = new TextureRegion(items, 0, 224, 300, 110);
		pauseMenu = new TextureRegion(items, 224, 128, 192, 96);
		ready = new TextureRegion(items, 320, 224, 192, 32);
		gameOver = new TextureRegion(items, 352, 256, 160, 96);
		highScoresRegion = new TextureRegion(Assets.items, 0, 257, 300, 110 / 3);
		helpRegion = new TextureRegion(Assets.items, 0, 300, 300, 110 / 3);
		logo = new TextureRegion(items, 0, 352, 274, 142);
		soundOff = new TextureRegion(items, 0, 0, 64, 64);
		soundOn = new TextureRegion(items, 64, 0, 64, 64);
		arrow = new TextureRegion(items, 0, 64, 64, 64);
		pause = new TextureRegion(items, 64, 64, 64, 64);

		spring = new TextureRegion(items, 128, 0, 32, 32);
		castle = new TextureRegion(items, 128, 64, 64, 64);
		coinAnim = new Animation(0.2f, new TextureRegion(items, 128, 32, 32, 32), new TextureRegion(items, 160, 32, 32, 32),
			new TextureRegion(items, 192, 32, 32, 32), new TextureRegion(items, 160, 32, 32, 32));
		bobJump = new Animation(0.2f, new TextureRegion(items, 0, 128, 32, 32), new TextureRegion(items, 32, 128, 32, 32));
		bobFall = new Animation(0.2f, new TextureRegion(items, 64, 128, 32, 32), new TextureRegion(items, 96, 128, 32, 32));
		bobHit = new Animation(0.2f, new TextureRegion(items, 128, 128, 32, 32));
		squirrelFly = new Animation(0.2f, new TextureRegion(items, 0, 160, 32, 32), new TextureRegion(items, 32, 160, 32, 32));
		platform = new Animation(0.2f, new TextureRegion(items, 64, 160, 64, 16));
		breakingPlatform = new Animation(0.2f, new TextureRegion(items, 64, 160, 64, 16), new TextureRegion(items, 64, 176, 64, 16),
			new TextureRegion(items, 64, 192, 64, 16), new TextureRegion(items, 64, 208, 64, 16));

		font = new BitmapFont(Gdx.files.internal("data/font.fnt"), Gdx.files.internal("data/font.png"), false);

		music = Gdx.audio.newMusic(Gdx.files.internal("data/music.mp3"));
		if (Settings.musicEnabled) Settings.enableMusic();
		jumpSound = Gdx.audio.newSound(Gdx.files.internal("data/jump.wav"));
		highJumpSound = Gdx.audio.newSound(Gdx.files.internal("data/highjump.wav"));
		hitSound = Gdx.audio.newSound(Gdx.files.internal("data/hit.wav"));
		coinSound = Gdx.audio.newSound(Gdx.files.internal("data/coin.wav"));
		clickSound = Gdx.audio.newSound(Gdx.files.internal("data/click.wav"));
		
		coinAnim.setPlayMode(PlayMode.LOOP);
		bobJump.setPlayMode(PlayMode.LOOP);
		bobFall.setPlayMode(PlayMode.LOOP);
		bobHit.setPlayMode(PlayMode.LOOP);
		squirrelFly.setPlayMode(PlayMode.LOOP);
		platform.setPlayMode(PlayMode.LOOP);
	}

	public static void playSound (Sound sound) {
		if (Settings.soundEnabled) sound.play(1);
	}
}
