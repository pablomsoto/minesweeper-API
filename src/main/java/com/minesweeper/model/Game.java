package com.minesweeper.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Document
@Builder
public class Game {

  @Id
  private String id;

  @CreatedDate
  private LocalDateTime created;

  @LastModifiedDate
  private LocalDateTime updated;

  private String user;
  private int movements;
  private int rows;
  private int columns;
  private int mines;
  private GameStatus status;
  private List<Cell> cells;

  public void start() {
    createCellsGrid();
    shuffleCells();
    insertMines();
    calculateNearbyMines();
  }

  private void calculateNearbyMines() {
    final Stream<Cell> cells = this.getCells().stream().filter((cell) -> !cell.isMine());
    cells.forEach((cell) -> cell.setNearbyMines(this.countNearbyMines(cell)));
  }

  private void insertMines() {
    this.getCells().stream().limit(this.getMines()).forEach(cellMine -> cellMine.setMine(true));
  }

  private void shuffleCells() {
    Collections.shuffle(this.getCells());
  }

  private void createCellsGrid() {
    this.cells = new ArrayList<>();
    for (int i = 0; i < this.getRows(); i++) {
      for (int j = 0; j < this.getColumns(); j++) {
        this.getCells().add(new Cell(i, j));
      }
    }
  }

  public void revealCell(final int x, final int y) {
    final Cell currentCell = this.getCellFromGrid(x, y);
    if (currentCell.isFlagged()) {
      return;
    }
    movements++;
    if (currentCell.isMine()) {
      this.setStatus(GameStatus.OVER);
      return;
    }
    currentCell.open(this.getCells());
    if (this.checkGameComplete()) {
      this.status = GameStatus.WON;
    }
  }

  public void flagCell(final int x, final int y) {
    final Cell currentCell = this.getCellFromGrid(x, y);
    currentCell.setFlagged(true);
  }

  public void markCell(final int x, final int y) {
    final Cell currentCell = this.getCellFromGrid(x, y);
    currentCell.setMarked(true);
  }

  public boolean isGameOver() {
    return GameStatus.WON.equals(this.status) || GameStatus.OVER.equals(this.status);
  }

  public boolean isPaused() {
    return GameStatus.PAUSED.equals(this.status);
  }

  public void togglePause() {
    if (this.isPaused()) {
      this.setStatus(GameStatus.RUNNING);
    } else {
      this.setStatus(GameStatus.PAUSED);
    }
  }

  private int countNearbyMines(final Cell aCell) {
    List<Cell> filtered = this.getCells().stream().filter(other -> aCell.chechAdjacency(other) && other.isMine()).collect(Collectors.toList());
    return filtered.size();
  }

  private Cell getCellFromGrid(final int x, final int y) {
    final Stream<Cell> streamCell = this.getCells().stream().filter(cell -> cell.getX() == x && cell.getY() == y);
    return streamCell.findFirst().orElseThrow(() -> new RuntimeException("Cell coordinates are not valid"));
  }

  private boolean checkGameComplete() {
    return this.getCells().stream().filter(cell -> !cell.isMine()).allMatch(Cell::isOpen);
  }
}
