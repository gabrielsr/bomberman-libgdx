package br.unb.unbomber.core.event;

import net.mostlyoriginal.api.event.common.Event;


/**
 * Notify the AudioSystem to play a new theme
 * 
 * @author grodrigues
 *
 */
public class ChangeAudioThemeEvent implements Event{

	private String newThemeName;

	public String getNewThemeName() {
		return newThemeName;
	}

	public void setNewThemeName(String newThemeName) {
		this.newThemeName = newThemeName;
	}
	
}
