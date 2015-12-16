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

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Johan Hutting
 */
public class BoardTest {

    private TiledMap mTiledMap;
    private Board mBoard;

    @Before
    public void init() {
        // TODO tiledmap load has nullpointer.
//        mTiledMap = new TmxMapLoader().load("./android/assets/tmx/level.tmx");
        mBoard = new Board((TiledMapTileLayer) mTiledMap.getLayers().get(0));
    }

    @Test
    @Ignore("nullpointer at Gdx.files.internal")
    public void testStartField(){
        mBoard.setCurrentLocation(5, 19);
        assertTrue(mBoard.isStartField());
    }
}
