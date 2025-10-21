package io.github.godeater;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main extends Game {
    public static Main INSTANCE;
    private FitViewport viewport;
    private OrthographicCamera orthographicCamera;

    private static final float V_WIDTH = 960;
    private static final float V_HEIGHT = 640;

    public Main() {
        INSTANCE = this;
    }

    @Override
    public void create() {
        orthographicCamera = new OrthographicCamera();
        viewport = new FitViewport(V_WIDTH, V_HEIGHT, orthographicCamera);
        viewport.apply();
        orthographicCamera.position.set(orthographicCamera.viewportWidth / 2f,
            orthographicCamera.viewportHeight / 2f, 0);
        orthographicCamera.update();

        setScreen(new GameScreen(orthographicCamera));
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
