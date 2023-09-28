import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Stopwatch extends JFrame{
    //the displays and buttons are in two Jpanels
    private JPanel displays = new JPanel();
    private JPanel buttons = new JPanel();

    private JButton start = new JButton("start");
    private JButton reset = new JButton("reset");
    private JButton close = new JButton("close");
    private JTextField miliseconds = new JTextField();
    private JTextField seconds = new JTextField();
    private JTextField minutes = new JTextField();

    //boolean flags to organize the start button behavior
    private boolean wasPaused = false;
    private boolean first = true;

    Counter counter = new Counter(miliseconds, seconds, minutes);
    Thread t = new Thread(counter);

    public Stopwatch(){
        super("Stopwatch");

        //organizing the interface
        this.setLayout(new BorderLayout());
        displays.setLayout(new GridLayout(1,3));
        buttons.setLayout(new GridLayout(1,3));

        buttons.add(start);
        buttons.add(reset);
        buttons.add(close);

        displays.add(minutes);
        minutes.setEditable(false);
        displays.add(seconds);
        seconds.setEditable(false);
        displays.add(miliseconds);
        miliseconds.setEditable(false);
        miliseconds.setText("000");
        seconds.setText("00");
        minutes.setText("00");

        this.add(displays, BorderLayout.NORTH);
        this.add(buttons, BorderLayout.SOUTH);

        this.pack();
        this.setResizable(false);

        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }
        });

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {

                //if the program isn't paused and it's the first execution
                if(!wasPaused && first){
                    start.setText("stop");
                    t.start();
                    first = false;
                }

                //if the program isn't paused and isn't the first execution
                else if(!wasPaused){
                    start.setText("start");
                    counter.pause();
                    wasPaused = true;
                }

                //if the program is paused
                else if(wasPaused && !first){
                    start.setText("stop");
                    counter.play();
                    wasPaused = false;
                }
            }
        });

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                start.setText("start");
                counter.reset();
                wasPaused = true;
                miliseconds.setText("000");
                seconds.setText("00");
                minutes.setText("00");
            }
        });
    }

    
    public static void main(String[] args) throws Exception {
        Stopwatch app = new Stopwatch();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
    }
}
