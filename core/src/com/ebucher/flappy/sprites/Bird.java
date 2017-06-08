package com.ebucher.flappy.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by buche on 6/8/2017.
 */

public class Bird {
    private static final int GRAVITY = -700;
    private Vector2 position;
    private Vector2 velocity;
    private Rectangle bounds;
    private Animation birdAnimation;
    private Texture birdAnimationTexture;
    private Sound flapSound;

    public Bird(int x, int y) {
        birdAnimationTexture = new Texture("birdanimation.png");
        birdAnimation = new Animation(birdAnimationTexture, 3, 0.5F);
        position = new Vector2(x, y);
        velocity = new Vector2(100, 0);
        bounds = new Rectangle(position.x, position.y, birdAnimation.getWidth(), birdAnimation.getHeight());
        flapSound = Gdx.audio.newSound(Gdx.files.internal("flap_sfx.mp3"));
    }

    public void update(float dt) {
        birdAnimation.update(dt);

        velocity.add(0, GRAVITY * dt);
        position.add(velocity.x * dt, velocity.y * dt);
        bounds.setPosition(position.x, position.y);
    }

    public int getHeight() {
        return (int) bounds.getHeight();
    }

    public void jump() {
        velocity.y = 250;
        flapSound.play();
    }

    public Vector2 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return birdAnimation.getFrame();
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose() {
        birdAnimationTexture.dispose();
        flapSound.dispose();
    }
}
