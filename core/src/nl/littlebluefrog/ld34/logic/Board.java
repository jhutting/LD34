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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

import java.util.ArrayList;

/**
 * @author Johan Hutting
 */
public class Board {
    private static final String TAG = "Board";

    private FieldType[][] mBoard;
    private int mHeight;
    private int mWidth;
    private TiledMapTileLayer mMapTileLayer;
    private Texture mUsedTile;
    private int mCurrentX;
    private int mCurrentY;
    private int mStartX;
    private int mStartY;
    private int mExitX;
    private int mExitY;
    private Direction mDirection;

    public Board(TiledMapTileLayer layer) {
        mMapTileLayer = layer;
        mWidth = layer.getWidth();
        mHeight = layer.getHeight();
        mBoard = new FieldType[mHeight][mWidth];

        for (int y = 0; y < mHeight; y++) {
            for (int x = 0; x < mWidth; x++) {
                TiledMapTileLayer.Cell cell = getCell(x, y);
                String type = cell != null ? (String) cell.getTile().getProperties().get("type") : null;
                if (type == null) {
                    mBoard[y][x] = FieldType.NONE;
                } else
                {
                    FieldType fieldType = FieldType.valueOf(type.toUpperCase());
                    mBoard[y][x] = fieldType;
                    if (fieldType == FieldType.START) {
                        mCurrentX = mStartX = x;
                        mCurrentY = mStartY = y;
                        Gdx.app.debug(TAG, "start location set to " + x + "," + y);
                    }
                    if (fieldType == FieldType.EXIT) {
                        mExitX = x;
                        mExitY = y;
                        Gdx.app.debug(TAG, "exit location set to " + x + "," + y);
                    }
                }
            }
        }

        mDirection = Direction.UP;

        mUsedTile = new Texture(Gdx.files.internal("gfx/used_tile.png"));
    }

    public boolean isExitReached() {
        return mCurrentX == mExitX && mCurrentY == mExitY;
    }

    public boolean isStartField() {
        return mCurrentX == mStartX && mCurrentY == mStartY;
    }

    public void markTileAsUsed(int x, int y) {
        TiledMapTileLayer.Cell cell = getCell(x, y);
        cell.setTile(new StaticTiledMapTile(new TextureRegion(mUsedTile)));
        cell.getTile().getProperties().put("type", FieldType.USED.name());
        mMapTileLayer.setCell(x, mHeight - y - 1, cell);
        mBoard[y][x] = FieldType.USED;
    }

    private TiledMapTileLayer.Cell getCell(int x, int y) {
        int cellY = mHeight - y - 1; // yuck.
        return mMapTileLayer.getCell(x, cellY);
    }

    public ArrayList<Direction> chooseDirection(int x, int y, Direction direction) {
        ArrayList<Direction> result = new ArrayList<Direction>();

        int cntFieldsConnected = 0;
        if (x > 0 && direction != Direction.RIGHT && mBoard[y][x - 1] != FieldType.NONE) {
            cntFieldsConnected++;
            result.add(Direction.LEFT);
        }
        if (y > 0 && direction != Direction.DOWN && mBoard[y - 1][x] != FieldType.NONE) {
            cntFieldsConnected++;
            result.add(Direction.UP);
        }
        if (x < mWidth - 1 && direction != Direction.LEFT && mBoard[y][x + 1] != FieldType.NONE) {
            cntFieldsConnected++;
            result.add(Direction.RIGHT);
        }
        if (y < mHeight - 1 && direction != Direction.UP && mBoard[y + 1][x] != FieldType.NONE) {
            cntFieldsConnected++;
            result.add(Direction.DOWN);
        }

        if (result.size() < 3) {
            return result;
        }
        throw new IllegalStateException("impossible board, fields connected: " + cntFieldsConnected + " on " + x + "," + y + " -> " + direction.name());
    }

    public String toString() {
        StringBuilder sbResult = new StringBuilder();
        for (int y = 0; y < mHeight; y++) {
            if (sbResult.length() > 0) {
                sbResult.append("\n");
            }
            for (int x = 0; x < mWidth; x++) {
                switch (mBoard[y][x]) {
                    case NONE:
                        sbResult.append(".");
                        break;
                    default:
                        sbResult.append(mBoard[y][x].ordinal());
                }
            }
        }
        return sbResult.toString();
    }

    public void dispose() {
        mUsedTile.dispose();
    }


    public int getCurrentX() {
        return mCurrentX;
    }

    public int getCurrentY() {
        return mCurrentY;
    }

    public void setCurrentLocation(int x, int y) {
        mCurrentX = x;
        mCurrentY = y;
    }

    public void setDirection(Direction direction) {
        mDirection = direction;
    }

    public Direction getDirection() {
        return mDirection;
    }

    public FieldType getCurrentField() {
        return mBoard[mCurrentY][mCurrentX];
    }
}
