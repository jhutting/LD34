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
package nl.littlebluefrog.ld34;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import nl.littlebluefrog.ld34.actors.*;
import nl.littlebluefrog.ld34.logic.*;
import nl.littlebluefrog.ld34.logic.Character;

import java.util.Random;

/**
 * @author Johan Hutting
 */
public class LD34 extends ApplicationAdapter {

    private static final float TILE_WIDTH = 72f;
    private static final String TAG = "LD34";

    private OrthographicCamera tiledCam;
    private OrthographicCamera hudCam;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private GameLogicController mGamelogic;
    private InputHandler mInputHandler;
    private Stage mStage;
    private PlayerStats mPlayerStats;
    private StepStats mStepStats;
    private Player mPlayer;
    private RewardReport mRewardReport;
    private GameOver mGameOver;
    private BattleReport mBattleReport;

    private final String[] mLevels = {"tmx/level.tmx", "tmx/level2.tmx", "tmx/level3.tmx"};
	
	@Override
	public void create () {
        Gdx.app.setLogLevel(Application.LOG_INFO);

        tiledCam = new OrthographicCamera();
        int worldWidth = Gdx.graphics.getWidth();
        int worldHeight = Gdx.graphics.getHeight();
        Gdx.app.debug(TAG, "world size = " + worldWidth + "x" + worldHeight);

        hudCam = new OrthographicCamera();
        hudCam.setToOrtho(false, worldWidth, worldHeight);
        mStage = new Stage(new ExtendViewport(worldWidth, worldHeight, hudCam));

        mGameOver = new GameOver();
        mGameOver.setPosition(worldWidth / 2 - 150, worldHeight / 2 - 100);
        mGameOver.setVisible(false);

        mBattleReport = new BattleReport();
        mBattleReport.setPosition(worldWidth / 2 - 150, worldHeight / 2 - 100);
        mBattleReport.setVisible(false);

        mRewardReport = new RewardReport();
        mRewardReport.setPosition(0, 0);
        mRewardReport.setVisible(false);

        mGamelogic = new GameLogicController(this);
        mGamelogic.newGame();

        mPlayer = new Player(mGamelogic);
        mPlayer.setPosition(tiledCam.viewportWidth / 2, tiledCam.viewportHeight / 2 - TILE_WIDTH * 2);

        mPlayerStats = new PlayerStats(mGamelogic);
        mPlayerStats.setPosition(new Vector2(0, worldHeight));

        mStepStats = new StepStats(mGamelogic);
        mStepStats.setPosition(0, worldHeight - 168 );

        Button buttonLeft = mGamelogic.getLeftButton();
        buttonLeft.setPosition(mPlayer.getX() - (TILE_WIDTH * 2) - 8, mPlayer.getY() - (TILE_WIDTH * 1.5f));

        Button buttonRight = mGamelogic.getRightButton();
        buttonRight.setPosition(mPlayer.getX() + (TILE_WIDTH * 2) - 8, mPlayer.getY() - (TILE_WIDTH * 1.5f));

        mStage.addActor(mRewardReport);
        mStage.addActor(mPlayer);
        mStage.addActor(mBattleReport);
        mStage.addActor(mPlayerStats);
        mStage.addActor(mStepStats);
        mStage.addActor(buttonLeft);
        mStage.addActor(buttonRight);
        mStage.addActor(mGameOver);

        mInputHandler = new InputHandler(mGamelogic, mStage);
        mStage.act();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0.6f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tiledMapRenderer.setView(tiledCam);
        tiledMapRenderer.render();

        mStage.draw();
	}

    @Override
    public void dispose() {
        mGamelogic.dispose();
        tiledMapRenderer.dispose();
        mStage.dispose();
        mPlayerStats.dispose();
        mStepStats.dispose();
        mPlayer.dispose();
        mRewardReport.dispose();
        mGameOver.dispose();
        mBattleReport.dispose();
    }

    public void moveLeft() {
        tiledCam.translate(-TILE_WIDTH, 0, 0);
        tiledCam.update();
        mStage.act();
    }

    public void moveRight() {
        tiledCam.translate(TILE_WIDTH, 0, 0);
        tiledCam.update();
        mStage.act();
    }

    public void moveUp() {
        tiledCam.translate(0, TILE_WIDTH, 0);
        tiledCam.update();
        mStage.act();
    }

    public void moveDown() {
        tiledCam.translate(0, -TILE_WIDTH, 0);
        tiledCam.update();
        mStage.act();
    }

    public void displayReward(FieldType type, long amount) {
        Gdx.app.debug(TAG, "display Reward " + amount + " for " + type.name());
        mRewardReport.setTypeAndAmount(type, amount);
        mRewardReport.setVisible(true);
        mStage.act();
    }

    public void hideReward() {
        mRewardReport.setVisible(false);
    }

    public void act() {
        mStage.act();
    }

    public void nextLevel() {
        tiledCam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        tiledCam.translate(-272, -TILE_WIDTH *3);
        tiledCam.update();

        Random rng = new Random();
        String level = mLevels[rng.nextInt(mLevels.length)];
        Gdx.app.log(TAG, "random level: " + level);
        TiledMap tileMap = new TmxMapLoader().load(level);
        mGamelogic.setBoard(new Board((TiledMapTileLayer) tileMap.getLayers().get(0)));
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tileMap);

        act();
    }

    public void displayBattle(Character mPlayer, Character mMonster) {
        mBattleReport.setPlayers(mPlayer, mMonster);
        mBattleReport.setVisible(true);
    }

    public void updateBattle(int dicePlayer, int diceMonster, long damagePlayer, long damageMonster) {
        mBattleReport.updateBattleStats(dicePlayer, diceMonster, damagePlayer, damageMonster);
        act();
    }

    public void hideBattle() {
        mBattleReport.setVisible(false);
    }

    public void displayGameOver(GameStatistics stats) {
        mGameOver.setStatistics(stats);
        mGameOver.setVisible(true);
    }

    public void hideGameOver() {
        mGameOver.setVisible(false);
    }

    public void displayBattleResult(boolean escaped) {
        mBattleReport.displayResult(escaped);
    }
}
