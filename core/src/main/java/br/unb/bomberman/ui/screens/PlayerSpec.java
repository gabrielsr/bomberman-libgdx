package br.unb.bomberman.ui.screens;

import java.util.ArrayList;
import java.util.List;

public class PlayerSpec {

	private List<Player> players;

	public static class Player implements Comparable<Player>{

		public String playerName;

		public int score;

		public String getPlayerName() {
			return playerName;
		}

		public int getScore() {
			return score;
		}

		public void setPlayerName(String playerName) {
			this.playerName = playerName;
		}

		public void setScore(int score) {
			this.score = score;
		}

		@Override
		public int compareTo(Player otherPlayer) {
			int otherScore = otherPlayer.getScore();
			if (this.score > otherScore) {
				return -1;
			}
			else if (this.score == otherScore) {
				return 0;
			}
			return 1;
		}

	}

	public List<Player> getPlayers() {
		if (players == null) {
			players = new ArrayList<Player>();
		}
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

}
