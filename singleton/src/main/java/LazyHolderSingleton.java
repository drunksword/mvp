public class LazyHolderSingleton {
  private LazyHolderSingleton(){}

  private static class LazyHolder {
    static final LazyHolderSingleton INSTANCE = new LazyHolderSingleton();

    static {
      System.out.println("LazyHolder.<clint>");
    }
  } 

  public static Object getInstance(boolean flag){
    if (flag) {
      return new LazyHolder[2];
    }
    return LazyHolder.INSTANCE;
  }

  public static void main(String... args){
    getInstance(true);
    System.out.println("---");
    getInstance(false);
  }
}