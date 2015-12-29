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
package nl.littlebluefrog.ld34.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import nl.littlebluefrog.ld34.logic.ButtonType;
import nl.littlebluefrog.ld34.logic.Direction;

/**
 * @author Johan Hutting
 */
public class Button extends Actor {
    private static final String TAG = "Button";

    private ButtonType mType;
    private Texture mDiceImage;
    private Texture mLeftImage;
    private Texture mRightImage;
    private Texture mUpImage;
    private Texture mDownImage;
    private Texture mOkImage;
    private Texture mEscapeImage;
    private Texture mAtkImage;

    public Button() {
        mDiceImage = new Texture(Gdx.files.internal("gfx/dice_button.png"));
        mLeftImage = new Texture(Gdx.files.internal("gfx/direction_left.png"));
        mRightImage = new Texture(Gdx.files.internal("gfx/direction_right.png"));
        mUpImage = new Texture(Gdx.files.internal("gfx/direction_up.png"));
        mDownImage = new Texture(Gdx.files.internal("gfx/direction_down.png"));
        mOkImage = new Texture(Gdx.files.internal("gfx/ok_button.png"));
        mEscapeImage = new Texture(Gdx.files.internal("gfx/escape_button.png"));
        mAtkImage = new Texture(Gdx.files.internal("gfx/atk_button.png"));

        setTouchable(Touchable.enabled);
        setWidth(mDiceImage.getWidth());
        setHeight(mDiceImage.getHeight());
    }

    public void setType(ButtonType type) {
        Gdx.app.debug(TAG, "buttontype set to " + type.name());
        mType = type;
    }

    public void setTypeByDirection(Direction direction) {
        switch (direction) {
            case LEFT:
                mType = ButtonType.LEFT;
                break;
            case RIGHT:
                mType = ButtonType.RIGHT;
                break;
            case UP:
                mType = ButtonType.UP;
                break;
            case DOWN:
                mType = ButtonType.DOWN;
                break;
        }
    }

    @Override
    public void draw(Batch batch, float alpha){
        switch (mType) {
            case ROLL:
                batch.draw(mDiceImage, getX(), getY());
                break;
            case LEFT:
                batch.draw(mLeftImage, getX(), getY());
                break;
            case RIGHT:
                batch.draw(mRightImage, getX(), getY());
                break;
            case DOWN:
                batch.draw(mDownImage, getX(), getY());
                break;
            case UP:
                batch.draw(mUpImage, getX(), getY());
                break;
            case OK:
                batch.draw(mOkImage, getX(), getY());
                break;
            case ESCAPE:
                batch.draw(mEscapeImage, getX(), getY());
                break;
            case ATTACK:
                batch.draw(mAtkImage, getX(), getY());
                break;
        }
    }

    public void dispose() {
        mDiceImage.dispose();
        mLeftImage.dispose();
        mRightImage.dispose();
        mUpImage.dispose();
        mDownImage.dispose();
        mOkImage.dispose();
        mEscapeImage.dispose();
        mAtkImage.dispose();
    }
}
