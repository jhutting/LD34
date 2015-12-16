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

/**
 * @author Johan Hutting
 */
public class StateMachine {
    private static final String TAG = "StateMachine";
    private StateMachineNode mCurrentState;

    public void setState(StateMachineNode newState) {
        mCurrentState = newState;
    }

    public void button1Pressed(GameLogicController gamelogic) {
        if (mCurrentState instanceof Walk) {
            return;
        }

        Gdx.app.debug(TAG, "button 1 pressed, state: " + mCurrentState);
        setState(mCurrentState.next(0, gamelogic));
        next(gamelogic);
    }

    public void button2Pressed(GameLogicController gamelogic) {
        if (mCurrentState instanceof Walk) {
            return;
        }

        Gdx.app.debug(TAG, "button 2 pressed, state: " + mCurrentState);
        setState(mCurrentState.next(1, gamelogic));
        next(gamelogic);
    }

    public void next(GameLogicController gamelogic) {
        if (mCurrentState instanceof Walk) {
            setState(mCurrentState.next(gamelogic));
            next(gamelogic);
        }
    }

}
