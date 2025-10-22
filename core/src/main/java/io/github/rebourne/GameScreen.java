package io.github.rebourne;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import static io.github.rebourne.helper.Constants.PPM;

public class GameScreen extends ScreenAdapter {
    private final OrthographicCamera camera;
    private final SpriteBatch spriteBatch;
    private final World world;
    private final Box2DDebugRenderer debugRenderer;

    private static final float TIME_STEP = 1f / 60f;
    private float acceleration = 0f;

    public GameScreen(OrthographicCamera camera) {
        this.camera = camera;
        this.spriteBatch = new SpriteBatch();
        this.world = new World(new Vector2(0, -9.8f), true);
        this.debugRenderer = new Box2DDebugRenderer();
    }

    private void updateInputs() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    private void cameraUpdate() {
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
    }

    private void doStep(float delta) {
        acceleration += Math.min(delta, 0.25f);
        while (acceleration >= TIME_STEP) {
            world.step(TIME_STEP, 6, 2);
            acceleration -= TIME_STEP;
        }
    }

    @Override
    public void render(float delta) {
        doStep(delta);
        updateInputs();

        cameraUpdate();
        spriteBatch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        spriteBatch.end();

        debugRenderer.render(world, camera.combined.cpy().scl(1f / PPM));
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        world.dispose();
        debugRenderer.dispose();
    }
}
