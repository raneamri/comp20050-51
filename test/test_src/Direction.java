package test_src;

/**
 * Rays only ever have one of six slopes in Blackbox+
 * To simplify the logic, it uses this enum type instead of six decimal values
 * for slope
 * This reduces error, simplifies a lot of methods and makes the program more
 * efficient
 */
public enum Direction {
  LEFT_RIGHT,
  RIGHT_LEFT,
  UP_RIGHT,
  UP_LEFT,
  DOWN_RIGHT,
  DOWN_LEFT
}
