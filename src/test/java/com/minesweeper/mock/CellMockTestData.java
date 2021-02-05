/*
 * Copyright (C) Intraway Corporation - All Rights Reserved (2015)
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 *
 * Proprietary and confidential
 *
 * This file is subject to the terms and conditions defined in file LICENSE.txt, which is part of this source code
 * package.
 */

package com.minesweeper.mock;

import java.util.ArrayList;
import java.util.List;

import com.minesweeper.model.Cell;

public class CellMockTestData {

  public static List<Cell> generateGrid8x8() {
    List<Cell> cells = new ArrayList<>();
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        cells.add(new Cell(i, j));
      }
    }
    return cells;
  }

  public static List<Cell> cornerCellsWithMines() {
    List<Cell> cells = generateGrid8x8();
    Cell topLeftCornerCell = cells.get(0);
    topLeftCornerCell.setMine(true);
    Cell topRightCornerCell = cells.get(7);
    topRightCornerCell.setMine(true);
    Cell bottomLeftCornerCell = cells.get(56);
    bottomLeftCornerCell.setMine(true);
    Cell bottomRightCornerCell = cells.get(63);
    bottomRightCornerCell.setMine(true);
    return cells;
  }

  public static List<Cell> wallOfMines() {
    List<Cell> cells = generateGrid8x8();
    Cell cell1 = cells.get(2);
    cell1.setMine(true);
    Cell cell2 = cells.get(10);
    cell2.setMine(true);
    Cell cell3 = cells.get(16);
    cell3.setMine(true);
    Cell cell4 = cells.get(17);
    cell4.setMine(true);
    Cell cell5 = cells.get(18);
    cell5.setMine(true);
    return cells;
  }

  public static List<Cell> wallOfFlags() {
    List<Cell> cells = generateGrid8x8();
    Cell cell1 = cells.get(2);
    cell1.setFlagged(true);
    Cell cell2 = cells.get(10);
    cell2.setFlagged(true);
    Cell cell3 = cells.get(16);
    cell3.setFlagged(true);
    Cell cell4 = cells.get(17);
    cell4.setFlagged(true);
    Cell cell5 = cells.get(18);
    cell5.setFlagged(true);
    return cells;
  }

}
