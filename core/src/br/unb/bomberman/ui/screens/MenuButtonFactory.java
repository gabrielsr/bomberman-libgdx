package br.unb.bomberman.ui.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * A TextButton builder for menu buttons
 * 
 * @author zidenis
 */
public class MenuButtonFactory {

	// values for default positions
	private int initPosXMenu = 160;
	private int initPosYMenu = 290;
	private int menuItemHeight = 50;
	private int menuItemWidth = 350;
	// number of menuItem that is increased after each makeMenuButton. Used to
	// position the button correctly
	private int menuItemNumber = 0;

	private Skin skin;
	private Pixmap pixmap;
	private TextButton button;
	private TextButtonStyle textButtonStyle;

	/**
	 * Constructor apply default button style
	 */
	public MenuButtonFactory() {
		pixmap = new Pixmap(100, 100, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin = new Skin();
		skin.add("white", new Texture(pixmap));
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.checked = skin.newDrawable("white", Color.BLACK);
		textButtonStyle.font = new BitmapFont(
				Gdx.files.internal("data/font.fnt"),
				Gdx.files.internal("data/font.png"), false);
		textButtonStyle.font.setScale(1.6f);
	}

	/**
	 * Build a new TextButton with factory default button style
	 * 
	 * @param context
	 *            a Game context where
	 * @param label
	 *            string button's label
	 * @param nextScreenAfterClick
	 *            the next screen that will be showed after button click
	 * @return a new TextButton ready to be added to a stage
	 */
	public TextButton makeMenuButton(final Game game, final String label,
			final Screen nextScreenAfterClick) {
		button = new TextButton(label, textButtonStyle);
		button.setHeight(menuItemHeight);
		button.setWidth(menuItemWidth);
		button.setPosition(initPosXMenu, initPosYMenu - menuItemHeight
				* menuItemNumber);
		menuItemNumber++;
		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assets.playSound(Assets.clickSound);
				game.getScreen().dispose();
				game.setScreen(nextScreenAfterClick);
			}
		});
		return button;
	}
	
	public TextButton makeMenuButton(final Game game, final String label, final ClickListener clickListener) {
		button = new TextButton(label, textButtonStyle);
		button.setHeight(menuItemHeight);
		button.setWidth(menuItemWidth);
		button.setPosition(initPosXMenu, initPosYMenu - menuItemHeight * menuItemNumber);
		menuItemNumber++;
		button.addListener(clickListener);
		return button;
	}

	/**
	 * Define default corner X position of the menu
	 * 
	 * @param initPosXMenu
	 *            value of corner's coordinate x
	 */
	public void setInitPosXMenu(int initPosXMenu) {
		this.initPosXMenu = initPosXMenu;
	}

	/**
	 * Define default corner Y position of the menu
	 * 
	 * @param initPosYMenu
	 *            value of corner's coordinate x
	 */
	public void setInitPosYMenu(int initPosYMenu) {
		this.initPosYMenu = initPosYMenu;
	}

	/**
	 * Define default menu item's height
	 * 
	 * @param menuItemHeight
	 *            height value
	 */
	public void setMenuItemHeight(int menuItemHeight) {
		this.menuItemHeight = menuItemHeight;
	}

	/**
	 * Define default menu item's width
	 * 
	 * @param menuItemWidth
	 *            width value
	 */
	public void setMenuItemWidth(int menuItemWidth) {
		this.menuItemWidth = menuItemWidth;
	}
}
