package br.un.unbomber.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SimpleAnimation implements TextureRegionSource {

	float stateTime = 0f;
	
	private final Animation animation;
	
	public SimpleAnimation(Animation animation) {
		this.animation = animation;
	}

	public TextureRegion getRegion() {
		stateTime += Gdx.graphics.getDeltaTime(); 
		return animation.getKeyFrame(stateTime, true);
	}
}
