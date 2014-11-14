package br.un.unbomber.components;

import br.unb.unbomber.core.Component;

import com.badlogic.gdx.graphics.Texture;

public class Looks extends Component {
	private Texture texture;

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}
}
