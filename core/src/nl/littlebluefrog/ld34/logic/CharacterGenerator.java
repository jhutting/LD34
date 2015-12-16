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

import java.util.Random;

/**
 * @author Johan Hutting
 */
public class CharacterGenerator {
    public static final long MAINSTAT_DEFAULT = 25l;
    public static final long SECONDARYSTAT_DEFAULT = 10l;
    public static float MULTIPLY_STATS = 0.66f;

    private final String[] names = { "Maxwell", "Sean", "Kai", "Ben", "Hank", "John", "Sylvester", "Arnold", "William",
            "Sofia", "Lucy", "Eva", "Zoe", "Audrey", "Scarlett", "Elizabeth", "Layla", "Victoria", "Doorknob",
            "Hamburger", "Mouse", "Tile", "Bottle of beer", "Cable", "Photograph", "Vacuum cleaner", "Fridge" };

    private Random mRng = new Random();

    public Character getRandomPlayer() {
        CharacterType type = CharacterType.values()[mRng.nextInt(2)];
        Character result = new Character(getRandomName(), type);
        result.setMaxHitpoints(100l);
        result.setHitpoints(100l);
        result.setDefense(type == CharacterType.WARRIOR ? MAINSTAT_DEFAULT : SECONDARYSTAT_DEFAULT);
        result.setStrength(type == CharacterType.WARRIOR ? MAINSTAT_DEFAULT : SECONDARYSTAT_DEFAULT);
        result.setIntelligence(type == CharacterType.MAGE ? MAINSTAT_DEFAULT : SECONDARYSTAT_DEFAULT);
        result.setResistance(type == CharacterType.MAGE ? MAINSTAT_DEFAULT : SECONDARYSTAT_DEFAULT);

        return result;
    }

    public Character getRandomMonster(Character player) {
        CharacterType type = CharacterType.values()[2 + mRng.nextInt(2)];
        Character result = new Character(getRandomName(), type);
        result.setMaxHitpoints(Math.round(player.getMaxHitpoints() * MULTIPLY_STATS));
        result.setHitpoints(result.getMaxHitpoints());

        long maxOffensive = Math.max(player.getStrength(), player.getIntelligence());
        long minOffensive = Math.min(player.getStrength(), player.getIntelligence());
        long maxDefensive = Math.max(player.getDefense(), player.getResistance());
        long minDefensive = Math.min(player.getDefense(), player.getResistance());
        result.setDefense(type == CharacterType.GOAT ? Math.round(maxDefensive * MULTIPLY_STATS) : Math.round(minDefensive * MULTIPLY_STATS));
        result.setStrength(type == CharacterType.GOAT ? Math.round(maxOffensive * MULTIPLY_STATS) : Math.round(minOffensive * MULTIPLY_STATS));
        result.setIntelligence(type == CharacterType.BAT ? Math.round(maxOffensive * MULTIPLY_STATS) : Math.round(minOffensive * MULTIPLY_STATS));
        result.setResistance(type == CharacterType.BAT ? Math.round(maxDefensive * MULTIPLY_STATS) : Math.round(minDefensive * MULTIPLY_STATS));

        return result;
    }

    private String getRandomName() {
        return names[mRng.nextInt(names.length)];
    }
}
