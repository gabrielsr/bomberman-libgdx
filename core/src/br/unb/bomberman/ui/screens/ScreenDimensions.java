package br.unb.bomberman.ui.screens;
public class ScreenDimensions{
		
		private int	DEFAULT_SCREEN_WIDTH = 640;
		private int	DEFAULT_SCREEN_HEIGHT = 480;
		private int	DEFAULT_CELL_SIZE = 32;
		private int DEFAULT_HUD_HEIGHT = 64;
		
		private int screenWidth = DEFAULT_SCREEN_WIDTH;
		private int screenHeight = DEFAULT_SCREEN_HEIGHT;
		private int cellSize = DEFAULT_CELL_SIZE;
		
		private int hudHeight = DEFAULT_HUD_HEIGHT;
		
		
		public ScreenDimensions(){
			
		}

		public int getScreenWidth() {
			return screenWidth;
		}

		public void setScreenWidth(int screenWidth) {
			this.screenWidth = screenWidth;
		}

		public int getScreenHeight() {
			return screenHeight;
		}

		public void setScreenHeight(int screenHeight) {
			this.screenHeight = screenHeight;
		}

		public int getCellSize() {
			return cellSize;
		}

		public void setCellSize(int cellSize) {
			this.cellSize = cellSize;
		}

		public int getHudHeight() {
			return hudHeight;
		}

		public void setHudHeight(int hudHeight) {
			this.hudHeight = hudHeight;
		}
		
	}