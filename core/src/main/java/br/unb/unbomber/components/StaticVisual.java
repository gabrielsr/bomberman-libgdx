package br.unb.unbomber.components;

import br.unb.unbomber.core.Component;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @author grodrigues
 *
 */
public class StaticVisual extends Component implements TextureRegionSource {
	
	private TextureRegion region;
	
	public StaticVisual() {
	}

	public StaticVisual(TextureRegion region) {
		this.setRegion(region);
	}

	public TextureRegion getRegion() {
		return region;
	}

	public void setRegion(TextureRegion region) {
		this.region = region;
	}


}
