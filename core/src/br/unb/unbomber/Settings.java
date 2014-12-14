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

package br.unb.unbomber;

import java.util.ArrayList;
import java.util.List;

import br.unb.bomberman.ui.screens.Assets;
import br.unb.bomberman.ui.screens.PlayerSpec;
import br.unb.bomberman.ui.screens.PlayerSpec.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;

public class Settings {
	public static boolean musicEnabled = true;
	public static boolean soundEnabled = true;
	public static float soundVolume = 0.5f;
	public final static int[] highscores = new int[] {100, 80, 50, 30, 10};
	public final static String file = ".superjumper";
	
	public static boolean scoresReseted = false;
	
	private final static String SCORES_FILE = "../core/assets/scores.json";

	public static void load () {
		try {
			FileHandle filehandle = Gdx.files.external(file);
			
			String[] strings = filehandle.readString().split("\n");
			
			musicEnabled = Boolean.parseBoolean(strings[0]);
			for (int i = 0; i < 5; i++) {
				highscores[i] = Integer.parseInt(strings[i+1]);
			}
		} catch (Throwable e) {
			// :( It's ok we have defaults
		}
	}

	public static void save () {
		try {
			FileHandle filehandle = Gdx.files.external(file);
			
			filehandle.writeString(Boolean.toString(musicEnabled)+"\n", false);
			for (int i = 0; i < 5; i++) {
				filehandle.writeString(Integer.toString(highscores[i])+"\n", true);
			}
		} catch (Throwable e) {
		}
	}

//	public static void addScore (int score) {
//		for (int i = 0; i < 5; i++) {
//			if (highscores[i] < score) {
//				for (int j = 4; j > i; j--)
//					highscores[j] = highscores[j - 1];
//				highscores[i] = score;
//				break;
//			}
//		}
//	}
	
	public static void addScore (String name, int score) {
		Json json = new Json();
		FileHandle scoresFile = Gdx.files.local(SCORES_FILE);
		List<Player> players = getScores();
		PlayerSpec playersSpec = new PlayerSpec();
		Player newPlayer = new Player();
		newPlayer.setPlayerName(name);
		newPlayer.setScore(score);
		players.add(newPlayer);
		playersSpec.setPlayers(players);
		json.setOutputType(OutputType.json);
		scoresFile.writeString(json.prettyPrint(json.toJson(playersSpec)), false, null);
		scoresReseted = false;
	}
	
	public static List<Player> getScores() {
		Json json = new Json();
		List<Player> players;
		FileHandle scoresFile = Gdx.files.local(SCORES_FILE);
		PlayerSpec playersSpec = json.fromJson(PlayerSpec.class, scoresFile.reader());
		if (playersSpec != null) {
			players = playersSpec.getPlayers();
		} else {
			players = new ArrayList<PlayerSpec.Player>();
		}
		return players;
	}

	public static void resetScores() {
		FileHandle scoresFile = Gdx.files.local(SCORES_FILE);
		FileHandle scoresBkp = Gdx.files.local(SCORES_FILE + ".bkp");
		scoresFile.moveTo(scoresBkp);
		scoresFile.write(false);
		scoresReseted = true;
	}
	
	public static void enableMusic() {
		musicEnabled = true;
		Assets.music.setLooping(true);
		Assets.music.setVolume(soundVolume);
		Assets.music.play();
	}
	
	public static void disableMusic() {
		musicEnabled = false;
		Assets.music.stop();
	}
}
