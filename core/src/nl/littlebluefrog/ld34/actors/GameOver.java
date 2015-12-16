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
import nl.littlebluefrog.ld34.logic.GameStatistics;

/**
 * @author Johan Hutting
 */
public class GameOver extends Actor {

    private Texture mBackground;
    private BitmapFont mFont;
    private String mResultString;

    public GameOver() {
        mBackground = new Texture(Gdx.files.internal("gfx/gameover_bg.png"));
        mFont = new BitmapFont();
    }

    public void setStatistics(GameStatistics statistics) {
        mResultString = statistics.toResultString();
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(mBackground, getX(), getY());
        mFont.setColor(1f, 1f, 0.8f, 1);
        mFont.draw(batch, mResultString, getX() + 16, getY() + 150);
    }

    public void dispose() {
        mBackground.dispose();
        mFont.dispose();
    }
}
