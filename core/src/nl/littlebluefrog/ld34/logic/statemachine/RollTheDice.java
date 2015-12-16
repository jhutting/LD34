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
package nl.littlebluefrog.ld34.logic.statemachine;

import com.badlogic.gdx.Gdx;
import nl.littlebluefrog.ld34.logic.GameLogicController;

import java.util.Random;

/**
 * @author Johan Hutting
 */
public class RollTheDice implements StateMachineNode {
    private static final int MAX_DICE = 6;
    private static final String TAG = "RollTheDice";

    private Random mRng = new Random();

    public RollTheDice(GameLogicController gamelogic) {
        gamelogic.setButtonsForDice();
    }

    @Override
    public StateMachineNode next(GameLogicController gamelogic) {
        int diceroll = 1 + mRng.nextInt(MAX_DICE);
        Gdx.app.debug(TAG, "dice rolled: " + diceroll);
        gamelogic.setSteps(diceroll);
        return new Walk();
    }

    @Override
    public StateMachineNode next(int buttonIndex, GameLogicController gamelogic) {
        return next(gamelogic);
    }
}
