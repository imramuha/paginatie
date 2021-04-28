import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Userinterface {

    public static void main(List<Main.Instruction> instructions) {

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

        int counter = 0;

        // shows only one
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.append("Button 1 was pressed \n");
                textArea.append(instructions.get(counter).getInstructionId() + "   " + instructions.get(counter).getOperation() + "   " + instructions.get(counter).getAddress());
                textArea.append(" \n");
            }

        });

        // shows all of them
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.append("Button 2 was pressed \n");
                for(int i = 0; i < instructions.size(); i++) {

                    textArea.append(instructions.get(i).getInstructionId() + "   " + instructions.get(i).getOperation() + "   " + instructions.get(i).getAddress() + "\n");
                }
                textArea.append(" \n");
            }
        });

        f.setVisible(true);

    }

}