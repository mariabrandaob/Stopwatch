import java.text.DecimalFormat;

import javax.swing.JTextField;

public class Counter implements Runnable{
    DecimalFormat fmt = new DecimalFormat("00");
    DecimalFormat fmt2 = new DecimalFormat("000");
    private JTextField miliseconds;
    private JTextField seconds;
    private JTextField minutes;
    private boolean isSuspense; //boolean flag to identify a pause
    private String sec;
    private String min;
    private String mil;
    private long current = 0;
    private long beggining = 0;
    private long aux = 0;
       

    public Counter(JTextField mil, JTextField sec, JTextField min){
        this.miliseconds = mil;
        this.seconds = sec;
        this.minutes = min;
        this.isSuspense = false;
    }

    @Override
    public void run() {
        //beggining = when the tread starts
        beggining = System.currentTimeMillis();

        while(true){

            synchronized(this){
                while(isSuspense){ //the tread freezes if it's paused
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            //current = time since the tread was started
            current = (System.currentTimeMillis() - beggining) + aux;

            if(!isSuspense){
                mil = fmt2.format(current % 1000);
                sec = fmt.format((current/1000) % 60);
                min = fmt.format((current/60000) % 60);

                miliseconds.setText(String.valueOf(mil));
                seconds.setText(String.valueOf(sec));
                minutes.setText(String.valueOf(min));
            }
        }
    }
    
    synchronized void pause(){
        this.isSuspense = true;
        aux = current; //aux holds the time in which the stopwatch was paused
        notify();
    }

    synchronized void play(){
        this.isSuspense = false;
        beggining = System.currentTimeMillis();
        notify();
    }

    synchronized void reset(){
        this.isSuspense = true;
        notify();//notify the thread
        beggining = System.currentTimeMillis();//reset the time
        aux = 0;
        mil = "000";
        sec = "00";
        min = "00";
        miliseconds.setText("000");
        seconds.setText("00");
        minutes.setText("00");
    }
}
