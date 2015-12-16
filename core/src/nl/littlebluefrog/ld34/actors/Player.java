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
import nl.littlebluefrog.ld34.logic.CharacterType;
import nl.littlebluefrog.ld34.logic.GameLogicController;

/**
 * @author Johan Hutting
 */
public class Player extends Actor {

    private GameLogicController mGamelogic;
    private Texture mWarrior;
    private Texture mMage;

    public Player(GameLogicController gamelogic) {
        mGamelogic = gamelogic;
        mWarrior = new Texture(Gdx.files.internal("gfx/warrior.png"));
        mMage = new Texture(Gdx.files.internal("gfx/mage.png"));
    }

    @Override
    public void draw(Batch batch, float alpha) {
        if (mGamelogic.getPlayer().getType() == CharacterType.WARRIOR) {
            batch.draw(mWarrior, getX(), getY());
        } else {
            batch.draw(mMage, getX(), getY());
        }
    }

    public void dispose() {
        mWarrior.dispose();
        mMage.dispose();
    }
}
