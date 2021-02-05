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

package com.minesweeper.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.minesweeper.mock.CellMockTestData;

class CellTest {

  @Test
  void openTestAutoOpenCornerMines() {
    // Arrange
    List<Cell> cells = CellMockTestData.cornerCellsWithMines();
    Cell centerCell = cells.get(28);

    // Act
    centerCell.open(cells);

    // Assert
    long opencells = cells.stream().filter(cell -> cell.isOpen()).count();
    assertTrue(opencells > 0);
    assertEquals(60L, opencells);
  }

  @Test
  void openTestAutoOpenInnerCornerWallOfMines() {
    // Arrange
    List<Cell> cells = CellMockTestData.wallOfMines();
    Cell cornerCell = cells.get(0);

    // Act
    cornerCell.open(cells);

    // Assert
    long opencells = cells.stream().filter(cell -> cell.isOpen()).count();
    assertTrue(opencells > 0);
    assertEquals(4L, opencells); // only 4 can be auto open.
  }

  @Test
  void openTestAutoOpenInnerCornerWallOfFlags() {
    // Arrange
    List<Cell> cells = CellMockTestData.wallOfFlags();
    Cell cornerCell = cells.get(0);

    // Act
    cornerCell.open(cells);

    // Assert
    long opencells = cells.stream().filter(cell -> cell.isOpen()).count();
    assertTrue(opencells > 0);
    assertEquals(4L, opencells); // only 4 can be auto open.
  }

  @Test
  void openTestAutoOpenOuterCornerWallOfMines() {
    // Arrange
    List<Cell> cells = CellMockTestData.wallOfMines();
    Cell centerCell = cells.get(28);

    // Act
    centerCell.open(cells);

    // Assert
    long opencells = cells.stream().filter(cell -> cell.isOpen()).count();
    assertTrue(opencells > 0);
    assertEquals(55L, opencells); // only 55 can be auto open.
  }

  @Test
  void chechAdjacencyXTest() {
    // Arrange
    Cell firstCell = new Cell(0, 0);
    Cell secondCell = new Cell(0, 1);

    // Act
    boolean adjacent = firstCell.chechAdjacency(secondCell);

    // Assert
    assertTrue(adjacent);
  }

  @Test
  void chechAdjacencyYTest() {
    // Arrange
    Cell firstCell = new Cell(0, 0);
    Cell secondCell = new Cell(1, 0);

    // Act
    boolean adjacent = firstCell.chechAdjacency(secondCell);

    // Assert
    assertTrue(adjacent);
  }

  @Test
  void chechAdjacencyXTestFail() {
    // Arrange
    Cell firstCell = new Cell(0, 0);
    Cell secondCell = new Cell(0, 2);

    // Act
    boolean adjacent = firstCell.chechAdjacency(secondCell);

    // Assert
    assertFalse(adjacent);
  }

  @Test
  void chechAdjacencyYTestFail() {
    // Arrange
    Cell firstCell = new Cell(0, 0);
    Cell secondCell = new Cell(2, 0);

    // Act
    boolean adjacent = firstCell.chechAdjacency(secondCell);

    // Assert
    assertFalse(adjacent);
  }

}
