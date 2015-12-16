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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import nl.littlebluefrog.ld34.logic.GameLogicController;

/**
 * @author Johan Hutting
 */
public class StepStats extends Actor {

    private BitmapFont mFont;
    private GameLogicController mGamelogic;
    private Texture mBackground;
    private String mStepsLeft;

    public StepStats(GameLogicController gamelogic) {
        mFont = new BitmapFont();
        mGamelogic = gamelogic;
        mBackground = new Texture(Gdx.files.internal("gfx/steps_bg.png"));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        StringBuilder sbSteps = new StringBuilder();
        int stepsLeft = mGamelogic.getStepsLeft();
        switch (stepsLeft) {
            case 0:
                sbSteps.append("No steps left.");
                break;
            case 1:
                sbSteps.append("1 step left.");
                break;
            default:
                sbSteps.append(stepsLeft).append(" steps left.");
        }
        mStepsLeft = sbSteps.toString();
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(mBackground, getX(), getY());
        mFont.setColor(0.2f, 0.4f, 0.2f, 1);
        mFont.draw(batch, mStepsLeft, getX() + 8, getY() + 8 + 16);
    }

    public void dispose() {
        mFont.dispose();
        mBackground.dispose();
    }
}
