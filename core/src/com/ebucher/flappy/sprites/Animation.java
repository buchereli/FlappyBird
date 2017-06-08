package com.ebucher.flappy.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by buche on 6/8/2017.
 */

public class Animation {

    private Array<TextureRegion> frames;
    private float maxFrameTime;
    private float currentFrameTime;
    private int frameCount;
    private int frame;
    private int frameWidth, frameHeight;

    public Animation(Texture texture, int frameCount, float cycleTime) {
        TextureRegion region = new TextureRegion(texture);
        frames = new Array<TextureRegion>();
        frameWidth = region.getRegionWidth() / frameCount;
        frameHeight = region.getRegionHeight();
        for (int i = 0; i < frameCount; i++)
            frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, frameHeight));
        this.frameCount = frameCount;
        maxFrameTime = cycleTime / frameCount;
        frame = 0;
    }

    public void update(float dt) {
        currentFrameTime += dt;
        if (currentFrameTime > maxFrameTime) {
            frame = (frame+1) % frameCount;
            currentFrameTime = currentFrameTime - maxFrameTime;
        }
    }

    public TextureRegion getFrame() {
        return frames.get(frame);
    }

    public int getWidth() {
        return frameWidth;
    }

    public int getHeight() {
        return frameHeight;
    }
}
