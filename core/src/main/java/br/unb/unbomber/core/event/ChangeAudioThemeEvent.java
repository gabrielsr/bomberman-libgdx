package br.unb.unbomber.core.event;

import br.unb.unbomber.core.Event;


/**
 * Notify the AudioSystem to play a new theme
 * 
 * @author grodrigues
 *
 */
public class ChangeAudioThemeEvent extends Event{

	private String newThemeName;

	public String getNewThemeName() {
		return newThemeName;
	}

	public void setNewThemeName(String newThemeName) {
		this.newThemeName = newThemeName;
	}
	
}
