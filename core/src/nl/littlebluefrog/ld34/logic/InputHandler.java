/**
 *   Copyright 2015, Little Blue Frog
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package nl.littlebluefrog.ld34.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * @author Johan Hutting
 */
public class InputHandler implements InputProcessor {
    private GameLogicController mGamelogic;
    private Stage mStage;

    public InputHandler(GameLogicController gameLogicController, Stage stage) {
        mGamelogic = gameLogicController;
        mStage = stage;
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.LEFT) {
            mGamelogic.button1Pressed();
            return true;
        }

        if(keycode == Input.Keys.RIGHT) {
            mGamelogic.button2Pressed();
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector2 location = mStage.screenToStageCoordinates(new Vector2(screenX, screenY));
        Actor touched = mStage.hit(location.x, location.y, false);
        if (touched == mGamelogic.getLeftButton()) {
            mGamelogic.button1Pressed();
            return true;
        }

        if (touched == mGamelogic.getRightButton()) {
            mGamelogic.button2Pressed();
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
