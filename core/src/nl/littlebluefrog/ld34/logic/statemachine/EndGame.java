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

import nl.littlebluefrog.ld34.logic.GameLogicController;

/**
 * @author Johan Hutting
 */
public class EndGame implements StateMachineNode {

    @Override
    public StateMachineNode next(GameLogicController gamelogic) {
        return null;
    }

    @Override
    public StateMachineNode next(int buttonIndex, GameLogicController gamelogic) {
        gamelogic.newGame();
        return new RollTheDice(gamelogic);
    }
}