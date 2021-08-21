package work.cn;

public class FlySword extends Thread {
    Window JW;
    int x;
    int y;
    double theta;
    Enemy fEnemy;
    boolean death = false;
    int a = 0;

    public FlySword(Window JW, int x, int y, double theta) {
        this.JW = JW;
        this.x = x;
        this.y = y;
        this.theta = theta;
    }

    @Override
    public void run() {
        try {
            sleep(600);
        } catch (Exception e) {
            //  TODO: handle exception
        }
        double xdistance;
        double ydistance;
        selectObject();
        if (JW.boss.width > 120) {
            xdistance = JW.boss.x - this.x;
            ydistance = this.y - JW.boss.y;
            if (xdistance == 0) {
                xdistance = 1;
            }
        } else if (fEnemy != null) {
            xdistance = fEnemy.x - this.x;
            ydistance = this.y - ((fEnemy.y - JW.mp.upLimited) * 15 + 20 + 30);
            if (xdistance == 0) {
                xdistance = 1;
            }
        } else {
            xdistance = 1;
            ydistance = 0;

        }
        while (true) {
            attack();
            try {
                Thread.sleep(15);
            } catch (Exception e) {
                //  TODO: handle exception
            }

            double b = Math.atan(ydistance / xdistance);
            if (xdistance < 0) {
                if (b > 0) {
                    b = b - Math.PI;
                } else {
                    b = Math.PI + b;
                }
            }
            theta = Math.PI / 2 - b;
            this.x += 400 * Math.cos(Math.PI / 2 - theta) / 20;
            this.y -= 400 * Math.sin(Math.PI / 2 - theta) / 20;

            if (this.death || this.y > 360 || this.y < 20 || this.x < 20 || this.x > 580) {
                this.death = true;
                break;
            }
        }


    }

    public void attack() {
        if (JW.boss.width > 120) {
            int xdistance = this.x - JW.boss.x;
            int ydistance = this.y - JW.boss.y;
            if (Math.sqrt(ydistance * ydistance + xdistance * xdistance) < 20) {
                JW.boss.blood += 1;
                this.death = true;
            }
        } else if (fEnemy != null) {
            int xdistance = this.x - fEnemy.x;
            int ydistance = this.y - fEnemy.y;
            if (Math.sqrt(ydistance * ydistance + xdistance * xdistance) < 60) {
                fEnemy.blood += 1;
                this.death = true;
            }
        } else {
            this.death = true;
        }

    }

    public void selectObject() {
        if (JW.boss.width < 120) {
            for (int i = 0; i < JW.firer.size(); i++) {
                if (JW.firer.get(i).y > JW.mp.upLimited && JW.firer.get(i).y < JW.mp.downLimited) {

                    if (fEnemy != null) {
                        if (Math.abs(fEnemy.x - this.x) > Math.abs(JW.firer.get(i).x - this.x)) {
                            fEnemy = JW.firer.get(i);
                        }
                    } else {
                        fEnemy = JW.firer.get(i);
                    }


                }
            }


        }

    }
}
