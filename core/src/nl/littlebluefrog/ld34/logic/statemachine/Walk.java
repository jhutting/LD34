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
import nl.littlebluefrog.ld34.logic.*;
import nl.littlebluefrog.ld34.logic.Direction;

import java.util.ArrayList;

/**
 * @author Johan Hutting
 */
public class Walk implements StateMachineNode {
    private static final String TAG = "Walk";

    @Override
    public StateMachineNode next(GameLogicController gamelogic) {
        Board board = gamelogic.getBoard();
        Gdx.app.debug(TAG, "walking from: " + board.getCurrentX() + "," + board.getCurrentY());
        ArrayList<Direction> directions = board.chooseDirection(board.getCurrentX(), board.getCurrentY(), board.getDirection());
        Gdx.app.debug(TAG, "possible directions: " + directions.size());
        if (directions.size() == 2) {
            return new PickDirection(gamelogic, directions);
        }

        if (directions.size() > 0 && directions.get(0) != gamelogic.getBoard().getDirection()) {
            Gdx.app.debug(TAG, "direction " + gamelogic.getBoard().getDirection().name() + " no longer possible, changing to " + directions.get(0).name());
            gamelogic.getBoard().setDirection(directions.get(0));
        }

        if (!gamelogic.move()) {
            gamelogic.setButtonsForEndLevel();
            return new EndLevel();
        }

        if (gamelogic.getStepsLeft() > 0) {
            return new Walk();
        }
        return gamelogic.acquireField();
    }

    @Override
    public StateMachineNode next(int buttonIndex, GameLogicController gamelogic) {
        throw new IllegalStateException("shouldn't arrive here");
    }
}
