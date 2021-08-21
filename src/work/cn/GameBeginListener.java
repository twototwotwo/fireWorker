package work.cn;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author ËÄšð
 * @date
 */
public class GameBeginListener implements ActionListener {
    Window jwWindow;
    void setCreatWindow(Window jw) {
        jwWindow = jw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        jwWindow.yNot = true;
        jwWindow.removeAll();
        jwWindow.setGame();
    }
}
