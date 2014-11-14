package br.unb.unbomber.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.StageSpec;

public class AudioSystem extends BaseSystem {

	public static final String AUDIO_BASE = "audio/music/";
	public static final String OPEN_THEME = AUDIO_BASE + "DST-1990.mp3";
	
	public Music openTheme;
	
	public AudioSystem(EntityManager entityManager, StageSpec stage) {
		super(entityManager);
	}

	@Override
	public void start() {
		openTheme = Gdx.audio.newMusic(Gdx.files.internal(OPEN_THEME));
	}

	@Override
	public void stop() {
		openTheme.pause();
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

}
