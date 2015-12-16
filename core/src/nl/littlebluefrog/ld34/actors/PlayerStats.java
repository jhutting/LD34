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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import nl.littlebluefrog.ld34.logic.*;

/**
 * @author Johan Hutting
 */
public class PlayerStats extends Actor {

    private static final float LINE = 16f;

    private GameLogicController mGamelogic;
    private BitmapFont mFont;
    private Texture mBackground;
    private Texture mIcon;
    private Vector2 mPosition = new Vector2();
    private String mHitpointString;
    private String mStrengthString;
    private String mDefenseString;
    private String mIntelligenceString;
    private String mResistanceString;

    public PlayerStats(GameLogicController gamelogic) {
        mGamelogic = gamelogic;

        mFont = new BitmapFont();
        mBackground = new Texture(Gdx.files.internal("gfx/playerstats_bg.png"));
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        nl.littlebluefrog.ld34.logic.Character player = mGamelogic.getPlayer();

        // update player data
        StringBuilder sbHitpoints = new StringBuilder();
        sbHitpoints.append("HP:    ").append(player.getHitpoints()).append("/").append(player.getMaxHitpoints());
        mHitpointString = sbHitpoints.toString();

        StringBuilder sbStrength = new StringBuilder();
        sbStrength.append("STR:  ").append(player.getStrength());
        mStrengthString = sbStrength.toString();

        StringBuilder sbDef = new StringBuilder();
        sbDef.append("DEF:  ").append(player.getDefense());
        mDefenseString = sbDef.toString();

        StringBuilder sbInt = new StringBuilder();
        sbInt.append("INT:    ").append(player.getIntelligence());
        mIntelligenceString = sbInt.toString();

        StringBuilder sbRes = new StringBuilder();
        sbRes.append("RES:  ").append(player.getResistance());
        mResistanceString = sbRes.toString();
    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(mBackground, mPosition.x, mPosition.y);
//        batch.draw(mIcon, mPosition.x + 8, mPosition.y + 8 + 16 + 8 + 16 + 8);
        mFont.setColor(0.2f, 0.4f, 0.2f, 1);
        mFont.draw(batch, mGamelogic.getPlayer().getName(), mPosition.x + 8, mPosition.y + 8 + (LINE * 6));
        mFont.setColor(0.5f, 0.4f, 0, 1);
        mFont.draw(batch, mHitpointString, mPosition.x + 8, mPosition.y + 8 + (LINE * 5));
        mFont.draw(batch, mStrengthString, mPosition.x + 8, mPosition.y + 8 + (LINE * 4));
        mFont.draw(batch, mDefenseString, mPosition.x + 8, mPosition.y + 8 + (LINE * 3));
        mFont.draw(batch, mIntelligenceString, mPosition.x + 8, mPosition.y + 8 + (LINE * 2));
        mFont.draw(batch, mResistanceString, mPosition.x + 8, mPosition.y + 8 + (LINE * 1));
    }

    public void setPosition(Vector2 position) {
        mPosition.x = position.x;
        mPosition.y = position.y - LINE * 7;
    }

    public void dispose() {
        mFont.dispose();
        mBackground.dispose();
//        mIcon.dispose();
    }
}
