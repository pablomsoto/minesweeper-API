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

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;

@Data
public class Cell {

  private int x;
  private int y;
  private int nearbyMines;
  private boolean isOpen;
  private boolean isMine;
  private boolean flagged;
  private boolean marked;

  public Cell(final int x, final int y) {
    this.x = x;
    this.y = y;
  }

  // Opens a Cell and, if it is not a mine, automatically opens nearby cells if the current has 0 nearby mines
  public void open(final List<Cell> cells) {
    this.setOpen(true);
    List<Cell> adjacentCells = cells.stream().filter(cell -> this.chechAdjacency(cell) && cell.autoOpen()).collect(Collectors.toList());

    if (!adjacentCells.isEmpty()) {
      adjacentCells.forEach(cell -> cell.open(cells));
    }
  }

  private boolean autoOpen() {
    return !this.isOpen() && !this.isMine() && !this.isFlagged() && this.getNearbyMines() == 0;
  }

  public boolean chechAdjacency(final Cell otherCell) {
    return notSameCell(otherCell) && calculateAxisAdjacency(this.getX(), otherCell.getX()) && calculateAxisAdjacency(this.getY(), otherCell.getY());
  }

  private boolean notSameCell(final Cell otherCell) {
    return this != otherCell;
  }

  private boolean calculateAxisAdjacency(int first, int second) {
    return Math.abs(first - second) <= 1;
  }
}
