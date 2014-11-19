package br.unb.bomberman.ui.map;

import java.util.ArrayList;

import br.unb.unbomber.core.Entity;

public class LoadedStage /*extends Entity*/ {
	private static String stage;
	ArrayList<Entity> Entities = new ArrayList<Entity>();
	
	public static String getStage() {
		return stage;
	}

	public static void setStage(String stage) {
		LoadedStage.stage = stage;
	}
}
