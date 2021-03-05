package com.pawel;

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;

public class MiniMusicPlayer3 extends MiniMusicPlayer {

    private static JFrame f = new JFrame("My First Music Video");
    private static MyDrawPanel3 ml;

    public static void main(String[] args) {
        MiniMusicPlayer3 mini = new MiniMusicPlayer3();
        mini.go();
    }

    private void setUpGui(){
        ml = new MyDrawPanel3();
        f.setContentPane(ml);
        f.setBounds(30,30,800,600);
        f.setVisible(true);
    }

    private void go(){
        setUpGui();

        try {
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.addControllerEventListener(ml, new int[] {127});
            Sequence seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack();

            int r = 0;
            for(int i=0; i<60; i+=4){

                r = (int) ((Math.random() * 100) + 1);
                track.add(makeEvent(144, 1, r, 100, i));
                track.add(makeEvent(176, 1, 127, 0, i));
                track.add(makeEvent(128, 1, r, 100, i+2));
            }

            sequencer.setSequence(seq);
            sequencer.start();
            sequencer.setTempoInBPM(150);
        } catch(MidiUnavailableException | InvalidMidiDataException e){
            e.printStackTrace();
        }
    }

    private class MyDrawPanel3 extends JPanel implements ControllerEventListener{
        boolean msg = false;

        @Override
        public void controlChange(ShortMessage event) {
            msg = true;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            if(msg){
                Graphics2D g2 = (Graphics2D) g;

                int r = (int) (Math.random() * 255);
                int gr = (int) (Math.random() * 255);
                int b = (int) (Math.random() * 255);

                g.setColor(new Color(r, gr, b));

                int ht = (int) (Math.random() * 200 + 10);
                int wd = (int) (Math.random() * 200 + 10);

                int x = (int) (Math.random() * 400 + 10);
                int y = (int) (Math.random() * 400 + 10);

                g.fillRect(x,y,ht,wd);
                msg = false;
            }
        }
    }


}
