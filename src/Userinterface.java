import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Userinterface {

    public static void main(String[ ] args ) {

        System.out.println("44");

        JFrame f = new JFrame("A JFrame");

        f.setSize(550, 750);
        f.setLocation(500,100);

        final JTextArea textArea = new JTextArea(10, 40);
        f.getContentPane().add(BorderLayout.CENTER, textArea);

        final JButton button = new JButton("Button 1");
        final JButton button2 = new JButton("Button 2");

        f.getContentPane().add(BorderLayout.WEST, button);
        f.getContentPane().add(BorderLayout.EAST, button2);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.append("Button 1 was clicked\n");
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.append("Button 2 was clicked\n");
            }
        });

        f.setVisible(true);

    }

}