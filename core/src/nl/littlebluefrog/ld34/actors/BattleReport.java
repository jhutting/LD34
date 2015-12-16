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
import com.badlogic.gdx.utils.Align;
import nl.littlebluefrog.ld34.logic.Character;

/**
 * @author Johan Hutting
 */
public class BattleReport extends Actor {
    private Texture mBackground;
    private BitmapFont mFont;
    private String mResultPlayer;
    private String mResultMonster;
    private Character mPlayer;
    private Character mMonster;

    public BattleReport() {
        mBackground = new Texture(Gdx.files.internal("gfx/battle_bg.png"));
        mFont = new BitmapFont();
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(mBackground, getX(), getY());
        mFont.setColor(1f, 1f, 0.8f, 1);
        mFont.draw(batch, mResultPlayer, getX() + 16, getY() + 120, 150, Align.left, true);
        mFont.draw(batch, mResultMonster, getX() + 130 + 16, getY() + 120, 130, Align.right, true);
    }

    public void dispose() {
        mBackground.dispose();
        mFont.dispose();
    }

    public void setPlayers(Character player, Character monster) {
        mPlayer = player;
        mMonster = monster;

        StringBuilder sbPlayer = new StringBuilder();
        sbPlayer.append(mPlayer.getName()).append(" [").append(mPlayer.getType().name()).append("]\n");
        sbPlayer.append(mPlayer.getHitpoints()).append("/").append(mPlayer.getMaxHitpoints()).append("\n\n");
        mResultPlayer = sbPlayer.toString();

        StringBuilder sbMonster = new StringBuilder();
        sbMonster.append(mMonster.getName()).append(" [").append(mMonster.getType().name()).append("]\n");
        sbMonster.append(mMonster.getHitpoints()).append("/").append(mMonster.getMaxHitpoints()).append("\n\n");
        mResultMonster = sbMonster.toString();
    }

    public void updateBattleStats(int dicePlayer, int diceMonster, long damagePlayer, long damageMonster) {
        StringBuilder sbPlayer = new StringBuilder();
        sbPlayer.append(mPlayer.getName()).append(" [").append(mPlayer.getType().name()).append("]\n");
        sbPlayer.append(mPlayer.getHitpoints()).append("/").append(mPlayer.getMaxHitpoints()).append("\n\n");
        sbPlayer.append(dicePlayer).append(" -> ").append(damagePlayer).append(" dmg");
        mResultPlayer = sbPlayer.toString();

        StringBuilder sbMonster = new StringBuilder();
        sbMonster.append(mMonster.getName()).append(" [").append(mMonster.getType().name()).append("]\n");
        sbMonster.append(mMonster.getHitpoints()).append("/").append(mMonster.getMaxHitpoints()).append("\n\n");
        sbMonster.append(diceMonster).append(" -> ").append(damageMonster).append(" dmg");
        mResultMonster = sbMonster.toString();
    }

    public void displayResult(boolean escaped) {
        StringBuilder sbPlayer = new StringBuilder();
        sbPlayer.append(mResultPlayer).append("\n\n");
        sbPlayer.append(escaped ? "Escaped!" : "Won!");
        mResultPlayer = sbPlayer.toString();
    }
}
