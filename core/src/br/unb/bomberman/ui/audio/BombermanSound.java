package br.unb.bomberman.ui.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class BombermanSound {

	
	public static final String AUDIO_BASE = "audio/music/";
	public static final String OPEN_THEME = AUDIO_BASE + "DST-1990.mp3";
	
	public Music openTheme;
	
	public void load(){
		openTheme = Gdx.audio.newMusic(Gdx.files.internal(OPEN_THEME));	
	}
	
}
