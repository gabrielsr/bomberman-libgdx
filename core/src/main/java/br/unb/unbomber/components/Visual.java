package br.unb.unbomber.components;

import br.unb.unbomber.core.Component;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


/**
 * The visual component of an entity
 * Use delegate pattern for hide the differences between a static image 
 * or an animation
 * @author grodrigues
 *
 */
public class Visual extends Component {
	
	private TextureRegionSource source;
	
	private Transform transform;
	
	
	public Visual() {
	}

	public Visual(TextureRegion region) {
		this.source = new StaticVisual(region);
	}
	
	public Visual(Animation animation) {
		this.source = new SequenceAnimation(animation);
	}

	public TextureRegion getRegion() {
		return this.source.getRegion();
	}

	public Transform getTransform() {
		return transform;
	}

	public void setTransform(Transform transform) {
		this.transform = transform;
	}

}