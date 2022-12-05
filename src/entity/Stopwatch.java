package entity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Stopwatch extends JDialog {
    /**
     * created panel manager
     */
    private JPanel jPanel = new JPanel(new GridBagLayout());
    /**
     * timer field created with default value "00:00:00"
     */
    private JTextField jTextFieldStopwatch = new JTextField("00:00:00");
    /**
     * created button start
     */
    private JButton jButtonStart = new JButton("Start");
    /**
     * created button stop
     */
    private JButton jButtonStop = new JButton("Stop");
    /**
     * created button restart
     */
    private JButton jButtonRestart = new JButton("Restart");
    /**
     * created second
     */
    private int second;
    /**
     * created minute
     */
    private int minute;
    /**
     * created hour
     */
    private int hour;
    /**
     * created a reference of thread
     */
    private Thread thread;

    /**
     * created an instance runnable
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                second++;

                if (second == 60) {
                    second = 0;
                    minute++;
                }
                if (minute == 60) {
                    minute = 0;
                    hour++;
                }

                jTextFieldStopwatch.setText((String.valueOf(hour).length() > 1 ? hour : "0" + hour) + ":" + (String.valueOf(minute).length() > 1 ? minute : "0" + minute) + ":" + (String.valueOf(second).length() > 1 ? second : "0" + second));

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    };


    public Stopwatch() {
        setTitle("My Stopwatch"); // title name tela
        setSize(new Dimension(380, 200)); // dimension tela
        setLocationRelativeTo(null); // center screen
        setResizable(false); // redirection

        GridBagConstraints gridBagConstraints = new GridBagConstraints(); // position controller and layout manager
        gridBagConstraints.gridx = 0; // starting position x
        gridBagConstraints.gridy = 0; // starting position y
        gridBagConstraints.insets = new Insets(5, 10, 5, 5); // configuration of position
        gridBagConstraints.anchor = GridBagConstraints.CENTER; // border center
        gridBagConstraints.gridwidth = 3; // staring the position

        add(jPanel); // adding panel in JDialog

        jTextFieldStopwatch.setPreferredSize(new Dimension(340, 70)); // size of JTextFiled
        jTextFieldStopwatch.setFont(new Font(Font.DIALOG, Font.BOLD, 80)); // font personalized
        jTextFieldStopwatch.setEnabled(false); // allow edit
        jTextFieldStopwatch.setHorizontalAlignment(JTextField.CENTER); // centralize the component inside field
        jPanel.add(jTextFieldStopwatch, gridBagConstraints); // adding in panel the JTextField

        /* -------- Adding JButtonStart --------------*/
        gridBagConstraints.gridwidth = 1; // return to starting position

        jButtonStart.setPreferredSize(new Dimension(100, 35)); // size of JButton start
        gridBagConstraints.gridy++; // adding position in layout
        jButtonStart.setFont(new Font(Font.DIALOG, Font.PLAIN, 16)); // font personalized
        jPanel.add(jButtonStart, gridBagConstraints); // adding in panel the JButton

        /* -------- Adding JButtonStop --------------*/
        jButtonStop.setPreferredSize(new Dimension(100, 35)); // size of JButton stop
        gridBagConstraints.gridx++; // adding position in layout
        jButtonStop.setFont(new Font(Font.DIALOG, Font.PLAIN, 16)); // font personalized
        jPanel.add(jButtonStop, gridBagConstraints); // adding in panel the JButton

        /* -------- Adding JButtonRestart --------------*/
        jButtonRestart.setPreferredSize(new Dimension(100, 35)); // size of JButton restart
        gridBagConstraints.gridx++; // adding position in layout
        jButtonRestart.setFont(new Font(Font.DIALOG, Font.PLAIN, 16)); // font personalized
        jPanel.add(jButtonRestart, gridBagConstraints); // adding in panel the JButton


        /*----------------adding action in JButton--------------**/
        jButtonStop.setEnabled(false); // disable JButton stop
        jButtonRestart.setEnabled(false); // disable JButton restart

        jButtonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thread = new Thread(runnable);
                thread.start();

                jButtonStart.setEnabled(false); // disable JButton start
                jButtonStop.setEnabled(true); //active JButton stop
                jButtonRestart.setEnabled(false); // disable JButton restart
            }
        });

        jButtonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thread.stop();

                jButtonStop.setEnabled(false); // disable JButton stop
                jButtonStart.setEnabled(true); // active JButton start
                jButtonRestart.setEnabled(true); // active JButton restart
            }
        });

        jButtonRestart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jTextFieldStopwatch.setText("00:00:00"); // restore display default value
                hour = 0; //reset time
                minute = 0; // restart minute
                second = 0; // restart second

                jButtonRestart.setEnabled(false); // disable JButton restart
                jButtonStop.setEnabled(false); // disable JButton stop
                jButtonStart.setEnabled(true); // active JButton start
            }
        });


        setVisible(true); // visible tela
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // close window and free memory
    }

}
