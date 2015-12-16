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
import nl.littlebluefrog.ld34.LD34;
import nl.littlebluefrog.ld34.actors.Button;
import nl.littlebluefrog.ld34.logic.statemachine.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * @author Johan Hutting
 */
public class GameLogicController {

    private static final String TAG = "GameLogicController";

    private LD34 mParent;
    private StateMachine mStateMachine = new StateMachine();
    private nl.littlebluefrog.ld34.logic.Character mPlayer;
    private nl.littlebluefrog.ld34.logic.Character mMonster;
    private Button mLeftButton;
    private Button mRightButton;
    private Board mBoard;
    private Random mRng = new Random();
    private GameStatistics mGameStatistics = new GameStatistics();

    // for now keep the entire gamecontext in this controller
    private int mStepsLeft;

    public GameLogicController(LD34 parent) {
        mParent = parent;
        mLeftButton = new Button();
        mRightButton = new Button();
    }

    public void newGame() {
        mParent.hideGameOver();

        CharacterGenerator characterGenerator = new CharacterGenerator();
        mPlayer = characterGenerator.getRandomPlayer();

        mStateMachine.setState(new RollTheDice(this));

        mGameStatistics.incLevels();
        mParent.nextLevel();
    }

    public void button1Pressed() {
        mStateMachine.button1Pressed(this);
    }

    public void button2Pressed() {
        mStateMachine.button2Pressed(this);
    }

    public int getStepsLeft() {
        return mStepsLeft;
    }

    public void setSteps(int stepsLeft) {
        mStepsLeft = stepsLeft;
        mGameStatistics.incStepsTaken(stepsLeft);
        mGameStatistics.incDiceRolls();
        mGameStatistics.incDiceTotal(stepsLeft);
    }

    public Character getPlayer() {
        return mPlayer;
    }

    public Button getLeftButton() {
        return mLeftButton;
    }

    public Button getRightButton() {
        return mRightButton;
    }

    public void setBoard(Board board) {
        mBoard = board;
    }

    public Board getBoard() {
        return mBoard;
    }

    public void dispose() {
        mBoard.dispose();
        mLeftButton.dispose();
        mRightButton.dispose();
    }

    public boolean move() {
        if(mBoard.isExitReached()) {
            // yay. Rejoice.
            Gdx.app.debug(TAG, "end of level");
            return false;
        }

        if (mBoard.isStartField() && mBoard.getDirection() == Direction.DOWN) {
            mBoard.setDirection(Direction.UP);
        }

        mStepsLeft--;
        switch (mBoard.getDirection()) {
            case UP:
                mParent.moveUp();
                mBoard.setCurrentLocation(mBoard.getCurrentX(), mBoard.getCurrentY() - 1);
                break;
            case DOWN:
                mParent.moveDown();
                mBoard.setCurrentLocation(mBoard.getCurrentX(), mBoard.getCurrentY() + 1);
                break;
            case LEFT:
                mParent.moveLeft();
                mBoard.setCurrentLocation(mBoard.getCurrentX() - 1, mBoard.getCurrentY());
                break;
            case RIGHT:
                mParent.moveRight();
                mBoard.setCurrentLocation(mBoard.getCurrentX() + 1, mBoard.getCurrentY());
                break;
        }

        return true;
    }

    public StateMachineNode acquireField() {
        FieldType fieldType = mBoard.getCurrentField();
        Gdx.app.debug(TAG, "ended on field: " + fieldType);
        mBoard.markTileAsUsed(mBoard.getCurrentX(), mBoard.getCurrentY());
        switch (fieldType) {
            case GREEN:
                return rewardRandomGreen();
            case RED:
                return rewardRandomRed();
            case BLUE:
                return rewardRandomBlue();
            case DAMAGE:
                return rewardDamage();
            case DEF_UP:
                return rewardDefUP();
            case STR_UP:
                return rewardStrUP();
            case INT_UP:
                return rewardIntUP();
            case RES_UP:
                return rewardResUP();
            case HEALTH:
                return rewardHealth();
            case HP_UP:
                return rewardHPUP();
            case USED:
                return new RollTheDice(this);
        }
        throw new IllegalStateException("should not arrive here, fieldtype:" + fieldType);
    }

    private StateMachineNode rewardHPUP() {
        long originalHp = mPlayer.getMaxHitpoints();
        if (mRng.nextInt(2) == 0) {
            mPlayer.setMaxHitpoints(Math.round(mPlayer.getMaxHitpoints() * 1.2));
        } else {
            mPlayer.setMaxHitpoints(Math.round(mPlayer.getMaxHitpoints() * 1.4));
        }

        mParent.displayReward(FieldType.HP_UP, mPlayer.getMaxHitpoints() - originalHp);
        return new RollTheDice(this);
    }

    private StateMachineNode rewardHealth() {
        long originalHp = mPlayer.getHitpoints();
        switch(mRng.nextInt(4)) {
            case 0:
                mPlayer.incHitpoints(Math.round(mPlayer.getMaxHitpoints() * 1.2));
                break;
            case 1:
                mPlayer.incHitpoints(Math.round(mPlayer.getMaxHitpoints() * 1.4));
                break;
            case 2:
                mPlayer.incHitpoints(Math.round(mPlayer.getMaxHitpoints() * 1.6));
                break;
            case 3:
                mPlayer.incHitpoints(Math.round(mPlayer.getMaxHitpoints() * 1.8));
                break;
        }

        mParent.displayReward(FieldType.HEALTH, mPlayer.getHitpoints() - originalHp);
        return new RollTheDice(this);
    }

    private StateMachineNode rewardResUP() {
        long originalRes = mPlayer.getResistance();
        if (mRng.nextInt(2) == 0) {
            mPlayer.setResistance(Math.round(mPlayer.getResistance() * 1.2));
        } else {
            mPlayer.setResistance(Math.round(mPlayer.getResistance() * 1.4));
        }
        mParent.displayReward(FieldType.RES_UP, mPlayer.getResistance() - originalRes);
        return new RollTheDice(this);
    }

    private StateMachineNode rewardIntUP() {
        long originalInt = mPlayer.getIntelligence();
        if (mRng.nextInt(2) == 0) {
            mPlayer.setIntelligence(Math.round(mPlayer.getIntelligence() * 1.2));
        } else {
            mPlayer.setIntelligence(Math.round(mPlayer.getIntelligence() * 1.4));
        }
        mParent.displayReward(FieldType.INT_UP, mPlayer.getIntelligence() - originalInt);
        return new RollTheDice(this);
    }

    private StateMachineNode rewardStrUP() {
        long originalStr = mPlayer.getStrength();
        if (mRng.nextInt(2) == 0) {
            mPlayer.setStrength(Math.round(mPlayer.getStrength() * 1.2));
        } else {
            mPlayer.setStrength(Math.round(mPlayer.getStrength() * 1.4));
        }
        mParent.displayReward(FieldType.STR_UP, mPlayer.getStrength() - originalStr);
        return new RollTheDice(this);
    }

    private StateMachineNode rewardDefUP() {
        long originalDef = mPlayer.getDefense();
        if (mRng.nextInt(2) == 0) {
            mPlayer.setDefense(Math.round(mPlayer.getDefense() * 1.2));
        } else {
            mPlayer.setDefense(Math.round(mPlayer.getDefense() * 1.4));
        }
        mParent.displayReward(FieldType.DEF_UP, mPlayer.getDefense() - originalDef);
        return new RollTheDice(this);
    }

    private StateMachineNode rewardResDown() {
        long originalRes = mPlayer.getResistance();
        if (mRng.nextInt(2) == 0) {
            mPlayer.setResistance(Math.round(mPlayer.getResistance() * 0.8));
        } else {
            mPlayer.setResistance(Math.round(mPlayer.getResistance() * 0.6));
        }
        mParent.displayReward(FieldType.RES_DOWN, mPlayer.getResistance() - originalRes);
        return new RollTheDice(this);
    }

    private StateMachineNode rewardIntDown() {
        long originalInt = mPlayer.getIntelligence();
        if (mRng.nextInt(2) == 0) {
            mPlayer.setIntelligence(Math.round(mPlayer.getIntelligence() * 0.8));
        } else {
            mPlayer.setIntelligence(Math.round(mPlayer.getIntelligence() * 0.6));
        }
        mParent.displayReward(FieldType.INT_DOWN, mPlayer.getIntelligence() - originalInt);
        return new RollTheDice(this);
    }

    private StateMachineNode rewardStrDown() {
        long originalStr = mPlayer.getStrength();
        if (mRng.nextInt(2) == 0) {
            mPlayer.setStrength(Math.round(mPlayer.getStrength() * 0.8));
        } else {
            mPlayer.setStrength(Math.round(mPlayer.getStrength() * 0.6));
        }
        mParent.displayReward(FieldType.STR_DOWN, mPlayer.getStrength() - originalStr);
        return new RollTheDice(this);
    }

    private StateMachineNode rewardDefDown() {
        long originalDef = mPlayer.getDefense();
        if (mRng.nextInt(2) == 0) {
            mPlayer.setDefense(Math.round(mPlayer.getDefense() * 0.8));
        } else {
            mPlayer.setDefense(Math.round(mPlayer.getDefense() * 0.6));
        }
        mParent.displayReward(FieldType.DEF_DOWN, mPlayer.getDefense() - originalDef);
        return new RollTheDice(this);
    }

    private StateMachineNode rewardDamage() {
        long originalHp = mPlayer.getHitpoints();
        long damage = Math.round(mPlayer.getMaxHitpoints() * ((mRng.nextInt(2) + 1) * 0.2));
        if (damage > mPlayer.getMainDefense()) {
            mPlayer.decHitpoints(damage - mPlayer.getMainDefense());
        }
        mParent.displayReward(FieldType.DAMAGE, mPlayer.getHitpoints() - originalHp);

        if (mPlayer.isDead()) {
            displayEndGame();
            return new EndGame();
        }
        return new RollTheDice(this);
    }

    private StateMachineNode rewardRandomBlue() {
        return mRng.nextInt(2) == 1 ? rewardRandomGreen() : rewardRandomRed();
    }

    private StateMachineNode rewardRandomRed() {
        int randomReward = mRng.nextInt(9);
        Gdx.app.debug(TAG, "random RED reward: " + randomReward);
        switch(randomReward) {
            case 0:
                return rewardDamage();
            case 1:
                return rewardStrDown();
            case 2:
                return rewardDefDown();
            case 3:
                return rewardIntDown();
            case 4:
                return rewardResDown();
            case 5:
            case 6:
            case 7:
            case 8:
                return new Battle(this, true);
        }
        throw new IllegalStateException("should not arrive here");
    }

    private StateMachineNode rewardRandomGreen() {
        switch(mRng.nextInt(6)) {
            case 0:
                return rewardHPUP();
            case 1:
                return rewardStrUP();
            case 2:
                return rewardDefUP();
            case 3:
                return rewardIntUP();
            case 4:
                return rewardResUP();
            case 5:
                return rewardHealth();
        }
        throw new IllegalStateException("should not arrive here");
    }

    public void setButtonsForDirections(ArrayList<Direction> directions) {
        Collections.sort(directions);
        mLeftButton.setTypeByDirection(directions.get(0));
        mRightButton.setTypeByDirection(directions.get(1));
        mParent.act();
    }

    public void setButtonsForDice() {
        mLeftButton.setType(ButtonType.ROLL);
        mRightButton.setType(ButtonType.ROLL);
    }

    public void setButtonsForOk() {
        mLeftButton.setType(ButtonType.OK);
        mRightButton.setType(ButtonType.OK);
    }

    public void hideReward() {
        mParent.hideReward();
    }

    public void setButtonsForEndLevel() {
        mLeftButton.setType(ButtonType.OK);
        mRightButton.setType(ButtonType.OK);
    }

    public void endLevel() {
        mGameStatistics.incLevels();
        mParent.nextLevel();
    }

    public void initiateBattle() {
        CharacterGenerator generator = new CharacterGenerator();
        mMonster = generator.getRandomMonster(mPlayer);

        mParent.displayBattle(mPlayer, mMonster);

        mLeftButton.setType(ButtonType.ESCAPE);
        mRightButton.setType(ButtonType.ATTACK);
    }

    public StateMachineNode battleResult(int dicePlayer, int diceMonster) {
        mGameStatistics.incDiceRolls();
        mGameStatistics.incDiceTotal(dicePlayer);

        long damagePlayer = Math.max(1, dicePlayer * mPlayer.getMainAttack() - mMonster.getMainDefense());
        long damageMonster = Math.max(1, diceMonster * mMonster.getMainAttack() - mPlayer.getMainDefense());

        mMonster.decHitpoints(damagePlayer);
        mPlayer.decHitpoints(damageMonster);
        mParent.updateBattle(dicePlayer, diceMonster, damagePlayer, damageMonster);
        Gdx.app.log(TAG, "battleResult " + dicePlayer + " vs " + diceMonster + " " + mPlayer + " -> " + mMonster + ". Damage: " + damagePlayer + " -> " + damageMonster);
        if (mPlayer.isDead()) {
            displayEndGame();
            return new EndGame();
        }

        if (mMonster.isDead()) {
            mGameStatistics.incBattlesWon();
            return endBattle(false);
        }
        return new Battle(this, false);
    }

    public StateMachineNode endBattle(boolean escaped) {
        if (mPlayer.isDead()) {
            displayEndGame();
            return new EndGame();
        }

        // report battle won/escaped and get the user to click 'ok', then roll the dice
        mParent.displayBattleResult(escaped);
        setButtonsForOk();
        return new BattleResult();
    }

    public StateMachineNode escape(int dicePlayer, int diceMonster) {
        long damageMonster = diceMonster * mMonster.getMainAttack() - mPlayer.getMainDefense();
        mPlayer.decHitpoints(Math.round(damageMonster * 0.5));
        mParent.updateBattle(dicePlayer, diceMonster, 0, damageMonster);

        mGameStatistics.incDiceRolls();
        mGameStatistics.incDiceTotal(dicePlayer);
        mGameStatistics.incBattlesFled();

        // we're lazy and we don't require the player's dice score to determine whether we can escape or not
        return endBattle(true);
    }

    public void displayEndGame() {
        mParent.hideBattle();
        setButtonsForOk();
        mParent.displayGameOver(mGameStatistics);
    }

    public void hideBattle() {
        mParent.hideBattle();
    }
}
