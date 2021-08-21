package work.cn;

public class Firebullet extends Thread {
    Window JW;
    double theta;
    int x;
    int y;
    int width = 20;
    int height = 40;
    int xspeed;
    int yspeed;
    int number = 0;

    public Firebullet(Window JW, double theta) {
        this.JW = JW;
        this.theta = theta;
        // 确定火弹刚出现时的中心点初始位置
        x = JW.boss.x - 7 + 10;
        y = JW.boss.y + 20;
        xspeed = -(int) (600 * Math.sin(theta) / 30);
        yspeed = (int) (600 * Math.cos(theta) / 30);
        this.start();
    }

    @Override
    public void run() {
        while (true) {
            if (number > 5) {
                break;
            }
            try {
                sleep(100);
            } catch (Exception e) {
                //  TODO: handle exception
            }

            x += xspeed;
            y += yspeed;
            burnt();

        }

    }


    public void burnt() {
        // X轴上距离差
        int x = JW.hero.x + 30 - this.x;
        int y = JW.hero.y + 40 - this.y;
        // hero中心和火弹中心的距离
        double distance = Math.sqrt(x * x + y * y);
        if (number == 0) {
            if (this.y > 300 || distance < 30) {

                number = 1;
                // 爆炸后不再移动
                xspeed = 0;
                yspeed = 0;
                // 爆炸后变大
                width = 100;
                height = 80;
                // 爆炸后不用选择
                theta = 0;

            }
        } else {
            if (distance < 55) {
                if (x < 0) {
                    JW.hero.xspeed = -20;
                } else {
                    JW.hero.xspeed = 20;
                }
            }
            number++;
        }

    }

}
