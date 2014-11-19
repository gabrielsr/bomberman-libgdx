package br.un.unbomber.components;

import br.unb.unbomber.core.Component;

import com.badlogic.gdx.graphics.g2d.TextureRegion;


/**
 * The visual component of an entity
 * @author grodrigues
 *
 */
public class Visual extends Component {
	
	private TextureRegion region;
	
	private Transform transform;

	public Visual(TextureRegion region) {
		this.setRegion(region);
	}

	public TextureRegion getRegion() {
		return region;
	}

	public void setRegion(TextureRegion region) {
		this.region = region;
	}

	public Transform getTransform() {
		return transform;
	}

	public void setTransform(Transform transform) {
		this.transform = transform;
	}
}