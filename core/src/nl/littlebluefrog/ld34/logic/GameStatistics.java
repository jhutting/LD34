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

/**
 * @author Johan Hutting
 */
public class GameStatistics {
    private long mSteps;
    private long mCntRolls;
    private long mCntDiceTotal;
    private long mBattlesWon;
    private long mBattlesFled;
    private long mCntLevels;
    private long mHPGained;
    private long mHPLost;
    private long mHealthRestored;
    private long mStrengthGained;
    private long mStrengthLost;


    public void incStepsTaken(long amount) {
        mSteps += amount;
    }

    public void incBattlesWon() {
        mBattlesWon++;
    }

    public void incBattlesFled() {
        mBattlesFled++;
    }

    public void incLevels() {
        mCntLevels++;
    }


    public String toResultString() {
        StringBuilder sbResult = new StringBuilder();
        sbResult.append("Steps taken: ").append(mSteps).append(", Levels: ").append(mCntLevels).append("\n");
        sbResult.append("Average roll: ").append(mCntDiceTotal / mCntRolls).append("\n");
        sbResult.append("Battles won: ").append(mBattlesWon).append(" - fled: ").append(mBattlesFled).append("\n");
        return sbResult.toString();
    }

    public void incDiceRolls() {
        mCntRolls++;
    }

    public void incDiceTotal(int amount) {
        mCntDiceTotal += amount;
    }
}
