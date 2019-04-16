package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;

public class MainMenuScreen implements Screen {
    private final Drop game;
    private Sprite startButtonSprite;
    private Sprite exitButtonSprite;
    private OrthographicCamera camera;
    private Texture startButtonTexture;
    private Texture exitButtonTexture;
    private Vector3 vectorTouch = new Vector3(); // временный вектор для "захвата" входных координат

    public MainMenuScreen(Drop game) {
        this.game = game;
        float height = Gdx.graphics.getHeight();
        float width = Gdx.graphics.getWidth();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);
        startButtonTexture = new Texture(Gdx.files.internal("start.jpeg"));
        exitButtonTexture = new Texture(Gdx.files.internal("exit.png"));

        startButtonSprite = new Sprite(startButtonTexture);
        exitButtonSprite = new Sprite(exitButtonTexture);
        // устанавливаем размер и позиции
        startButtonSprite.setSize(startButtonSprite.getWidth(), startButtonSprite.getHeight());

        exitButtonSprite.setSize(exitButtonSprite.getWidth(), exitButtonSprite.getHeight());

        exitButtonSprite.setX(100);
        exitButtonSprite.setY(100);
        startButtonSprite.setX(100);
        startButtonSprite.setY(200);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(startButtonTexture, 100, 200);
        game.batch.draw(exitButtonTexture, 100, 100);
        game.batch.end();

        handleTouch();

    }

    void handleTouch() {
        // Проверяем были ли касание по экрану?
        if (Gdx.input.justTouched()) {
            // Получаем координаты касания и устанавливаем эти значения в временный вектор
            vectorTouch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            // получаем координаты касания относительно области просмотра нашей камеры
            camera.unproject(vectorTouch);
            float touchX = vectorTouch.x;
            float touchY = vectorTouch.y;
            // обработка касания по кнопке Stare
            if ((touchX >= startButtonSprite.getX())
                && touchX <= (startButtonSprite.getX() + startButtonSprite.getWidth())
                && (touchY >= startButtonSprite.getY())
                && touchY <= (startButtonSprite.getY() + startButtonSprite.getHeight())) {
                game.setScreen(new GameScreen(game)); // Переход к экрану игры
            }
            // обработка касания по кнопке Exit
            else if ((touchX >= exitButtonSprite.getX())
                && touchX <= (exitButtonSprite.getX() + exitButtonSprite.getWidth())
                && (touchY >= exitButtonSprite.getY())
                && touchY <= (exitButtonSprite.getY() + exitButtonSprite.getHeight())) {
                Gdx.app.exit(); // выход из приложения
            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        startButtonTexture.dispose();
        exitButtonTexture.dispose();
        game.batch.dispose();
    }
}
