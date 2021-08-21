package work.cn;

import java.util.Random;
import java.util.Scanner;

public class BOSS extends Thread {
    int x = 300;
    int y = 80;
    int interval;
    int width = 30;
    int height = 0;
    int blood = 0;

    /**
     * boss的每次发射火弹的数目，暴走时会增加；
     */
    int bulletNumber = 10;
    int attackSelect = 3;

    /**
     * boss和hero的x轴和y轴坐标差
     */
    double xDistance = 0;
    double yDistance = 0;

    /**
     * boss和hero的距离
     */
    double distance = 0;
    double theta = 0;
    Window JW;
    int number = 1;

    public BOSS(Window JW) {
        this.JW = JW;
        this.start();
    }

    @Override
    public void run() {
        Random random = new Random();
        Scanner in = new Scanner(System.in);
        while (true) {
            try {
                sleep(200);
            } catch (Exception e) {
                //  TODO: handle exception
            }

            if (JW.change) {
                height += 20;
                width += 20;
            }

            if (width >= 160) {
                superAI(random.nextInt(30));
            }

            selectPicture();

            // boss血条空了，游戏结束
            if (JW.boss.blood >= 18) {
                break;
            }
        }

    }

    public void selectPicture() {
        switch (number) {
            case 1:


            case 2:


            case 3:


            case 4:


            case 5:
                number++;

                break;
            case 6:
                number = 1;

                break;
            case 7:

            case 8:
                number++;

                break;

            case 9:
                number = 7;

                break;


            default:
                break;
        }


    }

    public void superAI(int instruction) {
        if (attackSelect == 0) {
            // 攻击————冲撞
            attackByBump();
        } else if (attackSelect == 1) {
            // 攻击喷火
            attackByFire();
        } else if (attackSelect == 4) {
            // 恢复原位
            restoreLocation();
        } else {
            move();// 随机移动
            // attackselect为无效指令，接收instruction随机数为下一个指令
            attackSelect = instruction;
        }
    }

    /**
     * 指令0，冲撞
     */
    public void attackByBump() {

        if (number > 9 || number < 7) {
            // 图片7,8,9为冲撞图片，不在范围内说明此时的行为不是冲撞
            number = 7;
            xDistance = this.x - (JW.hero.x + 30);
            yDistance = (JW.hero.y + 45) - this.y;
            theta = Math.atan(xDistance / yDistance);
            distance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);

        }
        if (this.y < 330) {
            this.y += (int) (Math.cos(theta) * 800 / 10);
            if (this.x > -20 && this.x < 620) this.x -= (int) (Math.sin(theta) * 800 / 10);

            // 检查hero是否受到攻击

            if (Math.pow(this.x - (JW.hero.x + 30), 2) + Math.pow((JW.hero.y + 45) - this.y, 2) < 40 * 40) {

                if (JW.hero.x + 30 > this.x) {
                    JW.hero.xspeed = 30;
                } else {
                    JW.hero.xspeed = -30;
                }
            }


        } else {
            // 4为恢复原位的指令，图片number变为1,选择角度变为0度，即不旋转
            attackSelect = 4;
            number = 1;
            theta = 0;

        }


    }

    /**
     * 指令1，喷火
     */
    public void attackByFire() {
        double theta = 0;
        xDistance = this.x - (JW.hero.x + 30);
        yDistance = (JW.hero.y + 45) - this.y;
        theta = Math.atan(xDistance / yDistance);
        JW.bossBullet.add(new Firebullet(JW, theta));
        // 火弹数减一
        bulletNumber--;
        if (bulletNumber <= 0) {
            // 火弹数耗光，切换为无效指令
            attackSelect = 5;
            // 重新装弹
            bulletNumber = 10;
        }

    }

    /**
     * 指令4，恢复原位
     */
    public void restoreLocation() {
        if (y > 80 || x < 40 || x > 560) {
            if (y > 80) y -= 30;
            if (x < 40) x += 30;
            if (x > 560) x -= 30;
        } else {
            // 已经恢复原位，到达空中，跳出指令4
            // 5为无效指令
            attackSelect = 5;
        }


    }


    public void move() {
        int a = 0;
        Random random = new Random();
        a = random.nextInt(15);
        if (a >= 10) {
            if (this.x < 580) {
                this.x += 40;
            }
        } else if (a >= 5) {
            if (this.x > 20) {
                this.x += -40;
            }
        }

        a = random.nextInt(15);
        if (a >= 10) {
            if (this.y < 250) {
                this.y += 15;
            }
        } else if (a >= 5) {
            if (this.y > 80) {
                this.y += -15;
            }
        }
    }
}
