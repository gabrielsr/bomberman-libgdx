package br.unb.unbomber.components;

import com.artemis.Component;
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

	float x;
	float y;
	float originX;
	float originY;
	float width;
	float height;
	float scaleX;
	float scaleY;
	float rotation;
	
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

	public void updatToDraw(float x, float y, float originX, float originY,
			float width, float height, float scaleX, float scaleY,
			float rotation) {
		this.x = x;
		this.y = y;
		this.originX = originX;
		this.originY = originY;
		this.width = width;
		this.height = height;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.rotation = rotation;
	}

	public float getX() {
		return this.x ;
	}

	public float getY() {
		return this.y;
	}

	public float getOriginX() {
		return this.originX;
	}

	public float getOriginY() {
		return this.originY;
	}

	public float getWidth() {
		return this.width;
	}

	public float getHeight() {
		return this.height;
	}

	public float getScaleX() {
		return this.scaleX;
	}

	public float getScaleY() {
		return this.scaleY;
	}

	public float getRotation() {
		return this.rotation;
	}
}