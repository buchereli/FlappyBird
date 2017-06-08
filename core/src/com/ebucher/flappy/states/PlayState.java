package com.ebucher.flappy.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.ebucher.flappy.FlappyDemo;
import com.ebucher.flappy.sprites.Bird;
import com.ebucher.flappy.sprites.Tube;

/**
 * Created by buche on 6/8/2017.
 */

public class PlayState extends State {
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private static final int GROUND_OFFSET = -50;

    private Bird bird;
    private Texture bg, ground;
    private Vector2 groundPos1, groundPos2;
    private Array<Tube> tubes;

    protected PlayState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, FlappyDemo.WIDTH / 2, FlappyDemo.HEIGHT / 2);

        bg = new Texture("bg.png");
        ground = new Texture("ground.png");
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_OFFSET);
        groundPos2 = new Vector2(groundPos1.x + ground.getWidth(), GROUND_OFFSET);

        bird = new Bird(50, (int) (cam.position.y));

        tubes = new Array<Tube>(TUBE_COUNT);
        for (int i = 0; i < TUBE_COUNT; i++)
            tubes.add(new Tube((TUBE_SPACING + Tube.TUBE_WIDTH) * i + cam.position.x + cam.viewportWidth / 2));
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            bird.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        bird.update(dt);

        // Set the cam to the birds position
        cam.position.x = bird.getPosition().x + 80;

        for (Tube tube : tubes) {
            // If tube off screen, reposition to the right of the right most tube
            if (tube.getPosBotTube().x + Tube.TUBE_WIDTH < cam.position.x - cam.viewportWidth / 2)
                tube.reposition(tube.getPosBotTube().x + (TUBE_SPACING + Tube.TUBE_WIDTH) * TUBE_COUNT);
            // Check for tube collision
            if (tube.collides(bird.getBounds())) {
                gsm.set(new PlayState(gsm));
                return;
            }
        }

        // Check if bird flew off screen
        if (bird.getPosition().y < cam.position.y - cam.viewportHeight / 2 + groundPos1.y + ground.getHeight()
                || bird.getPosition().y + bird.getHeight() > cam.position.y + cam.viewportHeight / 2) {
            gsm.set(new PlayState(gsm));
            return;
        }

        updateGround();

        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - cam.viewportWidth / 2, cam.position.y - cam.viewportHeight / 2);
        sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);
        for (Tube tube : tubes) {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }
        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        ground.dispose();
        bird.dispose();
        for (Tube tube : tubes)
            tube.dispose();
        System.out.println("PLAY STATE DISPOSED");
    }

    public void updateGround() {
        if (groundPos1.x + ground.getWidth() < cam.position.x - (cam.viewportWidth / 2))
            groundPos1.x = groundPos2.x + ground.getWidth();
        if (groundPos2.x + ground.getWidth() < cam.position.x - (cam.viewportWidth / 2))
            groundPos2.x = groundPos1.x + ground.getWidth();
    }
}
