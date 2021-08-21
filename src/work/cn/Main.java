package work.cn;


public class Main {
    public static void main(String[] args) {
        Window jW = new Window();

        GameBeginListener lisener = new GameBeginListener();
        lisener.setCreatWindow(jW);
        jW.entranceButton.addActionListener(lisener);
        jW.setVisible(true);

        while (true) {
            jW.repaint();
        }
    }
}
