package src;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {
  public static int[] randomPair(int min, int max) {
    int a;
    int b;
    int[] pair = new int[2];
    a = ThreadLocalRandom.current().nextInt(min, max);
    b = ThreadLocalRandom.current().nextInt(min, max);
    while (a == b) {
      a = ThreadLocalRandom.current().nextInt(min, max);;
      b = ThreadLocalRandom.current().nextInt(min, max);;
    }
    pair[0] = a;
    pair[1] = b;
    return pair;
  }
}
