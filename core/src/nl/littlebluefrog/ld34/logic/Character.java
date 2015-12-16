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
public class Character {

    private String mName;
    private CharacterType mType;
    private long mHitpoints;
    private long mMaxHitpoints;
    private long mStrength;
    private long mIntelligence;
    private long mDefense;
    private long mResistance;

    public Character(String name, CharacterType type) {
        mName = name;
        mType = type;
    }

    public long getDefense() {
        return mDefense;
    }

    public void setDefense(long defense) {
        this.mDefense = defense;
    }

    public long getHitpoints() {
        return mHitpoints;
    }

    public void setHitpoints(long hitpoints) {
        this.mHitpoints = hitpoints;
    }

    public void incHitpoints(long restore) {
        mHitpoints = mHitpoints + restore;
        if (mHitpoints > mMaxHitpoints) {
            mHitpoints = mMaxHitpoints;
        }
    }

    public void decHitpoints(long damage) {
        mHitpoints = mHitpoints - damage;
    }

    public long getIntelligence() {
        return mIntelligence;
    }

    public void setIntelligence(long intelligence) {
        this.mIntelligence = intelligence;
    }

    public long getMaxHitpoints() {
        return mMaxHitpoints;
    }

    public void setMaxHitpoints(long maxHitpoints) {
        this.mMaxHitpoints = maxHitpoints;
    }

    public String getName() {
        return mName;
    }

    public long getResistance() {
        return mResistance;
    }

    public void setResistance(long resistance) {
        this.mResistance = resistance;
    }

    public long getStrength() {
        return mStrength;
    }

    public void setStrength(long strength) {
        this.mStrength = strength;
    }

    public CharacterType getType() {
        return mType;
    }

    public boolean isDead() {
        return mHitpoints <= 0;
    }

    public long getMainAttack() {
        switch (mType) {
            case WARRIOR:
            case GOAT:
                return mStrength;
            case MAGE:
            case BAT:
                return mIntelligence;
        }
        return 0;
    }

    public long getMainDefense() {
        switch (mType) {
            case WARRIOR:
            case GOAT:
                return mDefense;
            case MAGE:
            case BAT:
                return mResistance;
        }
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder sbResult = new StringBuilder();
        sbResult.append(mName).append(" [").append(mType.name()).append("] ");
        sbResult.append("(").append(mHitpoints).append("/").append(mMaxHitpoints).append(") ");
        if (isDead()) {
            sbResult.append("...Dead...");
        }
        return sbResult.toString();
    }
}
