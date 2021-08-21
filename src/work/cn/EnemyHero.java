package work.cn;

public class EnemyHero extends Thread {
    int x;
    int y;
    Window JW;

    public EnemyHero(Window JW) {
        this.JW = JW;
        this.start();
    }
    @Override
	public void run() {

        while (true) {
            SBAI();
        }
    }

    public void SBAI() {


    }


}
