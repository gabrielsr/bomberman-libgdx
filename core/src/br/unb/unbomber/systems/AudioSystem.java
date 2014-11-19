package br.unb.unbomber.systems;

import java.util.List;

import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.Event;
import br.unb.unbomber.core.event.ChangeAudioThemeEvent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Controls the audio of a Match.
 * 
 * This system should start the music when the game begin and 
 *  play sounds when choose events occur.
 * 
 * @author grodrigues
 *
 */
public class AudioSystem extends BaseSystem {

	public static final String AUDIO_BASE = "audio/music/";
	public static final String OPEN_THEME = AUDIO_BASE + "DST-1990.mp3";
	
	public Music openTheme;
	
	public AudioSystem(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public void start() {
		load();
	}

	@Override
	public void stop() {
		openTheme.pause();
		// TODO Auto-generated method stub
	}

	@Override
	public void update() {
		List<Event> destroyedEvents = getEntityManager().getEvents(
				ChangeAudioThemeEvent.class);

		if (destroyedEvents != null) {
			for (Event event : destroyedEvents) {
				ChangeAudioThemeEvent changeAudio = (ChangeAudioThemeEvent) event;
				//TODO change the audio loaded like the music.
			}
		}

	}
	
	private void load(){
		openTheme = Gdx.audio.newMusic(Gdx.files.internal(OPEN_THEME));
	}
	
	

}
