package br.unb.unbomber.systems;

import net.mostlyoriginal.api.event.common.Subscribe;
import br.unb.unbomber.core.event.ChangeAudioThemeEvent;

import com.artemis.systems.VoidEntitySystem;
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
public class AudioSystem extends VoidEntitySystem {

	public static final String AUDIO_BASE = "audio/music/";
	public static final String OPEN_THEME = AUDIO_BASE + "DST-1990.mp3";
	
	public Music openTheme;

	
	
	@Override
	public void begin() {
		load();
	}

	@Override
	public void end() {
		openTheme.pause();
		// TODO Auto-generated method stub
	}

	@Override
	public void processSystem() {
	

	}

	@Subscribe
	public void handle(ChangeAudioThemeEvent event){
		
	}
	private void load(){
		openTheme = Gdx.audio.newMusic(Gdx.files.internal(OPEN_THEME));
	}
	
	

}
