package com.ebucher.flappy.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * Created by buche on 6/8/2017.
 */

public class GameStateManager {

    private Stack<State> states;

    public GameStateManager() {
        states = new Stack<State>();
    }

    public void push(State state) {
        states.push(state);
    }

    public void pop(State state) {
        states.pop().dispose();
    }

    public void set(State state) {
        System.out.print("DISPOSING... ");
        states.pop().dispose();
        System.out.println("PUSHING NEW STATE");
        states.push(state);
    }

    public void update(float dt) {
        states.peek().update(dt);
    }

    public void render(SpriteBatch sb) {
        states.peek().render(sb);
    }

}
