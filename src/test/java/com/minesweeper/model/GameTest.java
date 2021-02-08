package com.minesweeper.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GameTest {

  private static final String TEST_USER = "Test User";

  @Test
  void startTest() {
    // Arrange
    final Game newGame = Game.builder().status(GameStatus.RUNNING).user(TEST_USER).rows(8).columns(8).mines(10).build();

    // Act
    newGame.start();

    // Assert
    assertNotNull(newGame.getCells());
    assertEquals(64, newGame.getCells().size());
    assertEquals(10l, newGame.getCells().stream().filter((cell) -> cell.isMine()).count());
    assertEquals(0, newGame.getMovements());
    assertEquals(TEST_USER, newGame.getUser());
    assertEquals(GameStatus.RUNNING, newGame.getStatus());
  }

  @Test
  void revealCellTestAMine() {
    // Arrange
    final Game newGame = Game.builder().status(GameStatus.RUNNING).user(TEST_USER).rows(8).columns(8).mines(10).build();
    newGame.start();
    Cell mine = newGame.getCells().stream().filter((cell) -> cell.isMine()).findFirst().get();

    // Act
    newGame.revealCell(mine.getX(), mine.getY());

    // Assert
    assertNotNull(newGame.getCells());
    assertEquals(64, newGame.getCells().size());
    assertEquals(10l, newGame.getCells().stream().filter((cell) -> cell.isMine()).count());
    assertEquals(1, newGame.getMovements());
    assertEquals(TEST_USER, newGame.getUser());
    assertEquals(GameStatus.OVER, newGame.getStatus());
  }

  @Test
  void revealCellTestNotAMine() {
    // Arrange
    final Game newGame = Game.builder().status(GameStatus.RUNNING).user(TEST_USER).rows(8).columns(8).mines(10).build();
    newGame.start();
    Cell regularCell = newGame.getCells().stream().filter((cell) -> !cell.isMine()).findFirst().get();

    // Act
    newGame.revealCell(regularCell.getX(), regularCell.getY());

    // Assert
    assertNotNull(newGame.getCells());
    assertEquals(64, newGame.getCells().size());
    assertEquals(10l, newGame.getCells().stream().filter((cell) -> cell.isMine()).count());
    assertEquals(1, newGame.getMovements());
    assertEquals(TEST_USER, newGame.getUser());
    assertEquals(GameStatus.RUNNING, newGame.getStatus());
  }

  @Test
  void revealCellTestFlagged() {
    // Arrange
    final Game newGame = Game.builder().status(GameStatus.RUNNING).user(TEST_USER).rows(8).columns(8).mines(10).build();
    newGame.start();
    Cell regularCell = newGame.getCells().get(0);
    regularCell.setFlagged(true);

    // Act
    newGame.revealCell(regularCell.getX(), regularCell.getY());

    // Assert
    assertNotNull(newGame.getCells());
    assertEquals(64, newGame.getCells().size());
    assertEquals(10l, newGame.getCells().stream().filter((cell) -> cell.isMine()).count());
    assertEquals(1l, newGame.getCells().stream().filter((cell) -> cell.isFlagged()).count());
    assertEquals(0, newGame.getMovements());
    assertEquals(TEST_USER, newGame.getUser());
    assertEquals(GameStatus.RUNNING, newGame.getStatus());
  }

  @Test
  void revealCellTestWinGame() {
    // Arrange
    final Game newGame = Game.builder().status(GameStatus.RUNNING).user(TEST_USER).rows(8).columns(8).mines(10).build();
    newGame.start();

    // Act
    newGame.getCells().stream().filter((cell) -> !cell.isMine()).forEach((cell) -> newGame.revealCell(cell.getX(), cell.getY()));

    // Assert
    assertNotNull(newGame.getCells());
    assertEquals(64, newGame.getCells().size());
    assertEquals(10l, newGame.getCells().stream().filter((cell) -> cell.isMine()).count());
    assertEquals(54l, newGame.getCells().stream().filter((cell) -> cell.isOpen()).count());
    assertEquals(54, newGame.getMovements());
    assertEquals(TEST_USER, newGame.getUser());
    assertEquals(GameStatus.WON, newGame.getStatus());
  }

  @Test
  void flagCellTest() {
    // Arrange
    final Game newGame = Game.builder().status(GameStatus.RUNNING).user(TEST_USER).rows(8).columns(8).mines(10).build();
    newGame.start();

    // Act
    newGame.flagCell(0, 0);

    // Assert
    assertNotNull(newGame.getCells());
    assertEquals(64, newGame.getCells().size());
    assertEquals(1l, newGame.getCells().stream().filter(Cell -> Cell.isFlagged()).count());
  }

  @Test
  void markCellTest() {
    // Arrange
    final Game newGame = Game.builder().status(GameStatus.RUNNING).user(TEST_USER).rows(8).columns(8).mines(10).build();
    newGame.start();

    // Act
    newGame.markCell(0, 0);

    // Assert
    assertNotNull(newGame.getCells());
    assertEquals(64, newGame.getCells().size());
    assertEquals(1l, newGame.getCells().stream().filter(Cell -> Cell.isMarked()).count());
  }

  @Test
  void isGameOverTestGameOver() {
    // Arrange
    final Game newGame = Game.builder().status(GameStatus.RUNNING).user(TEST_USER).rows(8).columns(8).mines(10).build();
    newGame.start();
    Cell mine = newGame.getCells().stream().filter((cell) -> cell.isMine()).findFirst().get();
    newGame.revealCell(mine.getX(), mine.getY());

    // Act
    boolean isGameOver = newGame.isGameOver();

    // Assert
    assertTrue(isGameOver);
  }

  @Test
  void isGameOverTestGameWon() {
    // Arrange
    final Game newGame = Game.builder().status(GameStatus.RUNNING).user(TEST_USER).rows(8).columns(8).mines(10).build();
    newGame.start();
    newGame.getCells().stream().filter((cell) -> !cell.isMine()).forEach((cell) -> newGame.revealCell(cell.getX(), cell.getY()));

    // Act
    boolean isGameOver = newGame.isGameOver();

    // Assert
    assertTrue(isGameOver);
  }

  @Test
  void togglePauseTest() {
    // Arrange
    final Game newGame = Game.builder().status(GameStatus.RUNNING).user(TEST_USER).rows(8).columns(8).mines(10).build();
    newGame.start();

    // Act
    newGame.togglePause();

    // Assert
    assertEquals(GameStatus.PAUSED, newGame.getStatus());
  }

  @Test
  void togglePauseTestResume() {
    // Arrange
    final Game newGame = Game.builder().status(GameStatus.RUNNING).user(TEST_USER).rows(8).columns(8).mines(10).build();
    newGame.start();
    newGame.togglePause();

    // Act
    newGame.togglePause(); // Resume

    // Assert
    assertEquals(GameStatus.RUNNING, newGame.getStatus());
  }

  @Test
  void isPausedTest() {
    // Arrange
    final Game newGame = Game.builder().status(GameStatus.RUNNING).user(TEST_USER).rows(8).columns(8).mines(10).build();
    newGame.start();
    newGame.togglePause();

    // Act
    boolean isPaused = newGame.isPaused();

    // Assert
    assertTrue(isPaused);
  }
}
