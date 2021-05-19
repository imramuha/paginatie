import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Userinterface extends JPanel {

    public static void main(List<Main.Instruction> instructions, ArrayList<PageTable> pageTables, Ram ram) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI(instructions, pageTables, ram);
            }
        });
    }

    public Userinterface(List<Main.Instruction> instructions, ArrayList<PageTable> pageTables, Ram ram) {
        super(new GridLayout(1, 1));

        JTabbedPane tabbedPane = new JTabbedPane();


        // layouts for our layout components/grids
        SpringLayout layout = new SpringLayout();

        // main panels for 1st and second option
        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(layout);
        jPanel1.setOpaque(false);


        // HEADER
        // timer Label + a field?
        // instructie [ ][ ] [ ] [ ]
        // reële adres in het RAM geheugen die aangesproken wordt
        JPanel jPanel1Header = new JPanel();
        jPanel1Header.setLayout(layout);
        jPanel1Header.setOpaque(false);
        jPanel1Header.setPreferredSize(new Dimension(340, 100));

        // all the components panels that need to be added to each option pannel
        // 1st option
        JComponent timerLabel = new JTextArea("Klok: ");
        timerLabel.setMaximumSize(new Dimension(20, 50));
        timerLabel.setOpaque(false);
        JComponent timer = new JTextArea("START");
        timer.setOpaque(false);

        JComponent instructieLabel = makeTextPanel("Instructie - [ Virtuele A. ][ V.A. next ][ Reële A. ][ Frame ][ Offset ]");
        instructieLabel.setOpaque(false);
        JComponent instructie = makeTextPanel("[][][][][][]");
        instructie.setOpaque(false);

        JComponent reëleAdresLabel = makeTextPanel("Reële adres:");
        reëleAdresLabel.setOpaque(false);
        JComponent reëleAdres = makeTextPanel("[]");
        reëleAdres.setOpaque(false);

        jPanel1Header.add(timerLabel);
        jPanel1Header.add(timer);

        jPanel1Header.add(instructieLabel);
        jPanel1Header.add(instructie);

        jPanel1Header.add(reëleAdresLabel);
        jPanel1Header.add(reëleAdres);

        layout.putConstraint(SpringLayout.NORTH, timerLabel, 10, SpringLayout.NORTH, jPanel1Header);
        layout.putConstraint(SpringLayout.WEST, timerLabel, 5, SpringLayout.WEST, jPanel1Header);

        layout.putConstraint(SpringLayout.NORTH, timer, 10, SpringLayout.NORTH, jPanel1Header);
        layout.putConstraint(SpringLayout.WEST, timer, 80, SpringLayout.WEST, jPanel1Header);

        layout.putConstraint(SpringLayout.NORTH, instructieLabel, 40, SpringLayout.NORTH, jPanel1Header);
        layout.putConstraint(SpringLayout.WEST, instructieLabel, 5, SpringLayout.WEST, jPanel1Header);

        layout.putConstraint(SpringLayout.NORTH, instructie, 55, SpringLayout.NORTH, jPanel1Header);
        layout.putConstraint(SpringLayout.WEST, instructie, 5, SpringLayout.WEST, jPanel1Header);

        layout.putConstraint(SpringLayout.NORTH, reëleAdresLabel, 85, SpringLayout.NORTH, jPanel1Header);
        layout.putConstraint(SpringLayout.WEST, reëleAdresLabel, 5, SpringLayout.WEST, jPanel1Header);

        layout.putConstraint(SpringLayout.NORTH, reëleAdres, 85, SpringLayout.NORTH, jPanel1Header);
        layout.putConstraint(SpringLayout.WEST, reëleAdres, 80, SpringLayout.WEST, jPanel1Header);

        // BODY
        // page table
        // RAM
        JPanel jPanel1Body = new JPanel();
        jPanel1Body.setLayout(layout);
        jPanel1Body.setOpaque(false);
        jPanel1Body.setBackground(Color.red);
        jPanel1Body.setPreferredSize(new Dimension(340, 650));
        layout.putConstraint(SpringLayout.NORTH, jPanel1Body, 25, SpringLayout.NORTH, jPanel1);

        // page table
        JComponent pageTableLabel = makeTextPanel("Page table - [ Last acces ][ Present bit ][ Modify bit ][ Frame ]");
        pageTableLabel.setOpaque(false);
        layout.putConstraint(SpringLayout.NORTH, pageTableLabel, 90, SpringLayout.NORTH, jPanel1Body);
        layout.putConstraint(SpringLayout.WEST,pageTableLabel, 5, SpringLayout.WEST, jPanel1Body);
        jPanel1Body.add(pageTableLabel);

        int counter = 5;
        // 16 frame entries per process
        for(int i = 0; i < 16; i++) {
            JComponent pageTable = makeTextPanel("[ ] [ ] [ ] [ ]");
            pageTable.setOpaque(false);
            layout.putConstraint(SpringLayout.WEST,pageTable, 5, SpringLayout.WEST, jPanel1Body);
            layout.putConstraint(SpringLayout.NORTH, pageTable, (100+counter), SpringLayout.NORTH, jPanel1Body);
            counter+=15;
            jPanel1Body.add(pageTable);
        }

        // RAM
        JComponent ramLabel = makeTextPanel("RAM - [[ Pid ] [ Page ]]");
        ramLabel.setOpaque(false);
        layout.putConstraint(SpringLayout.NORTH, ramLabel, 360, SpringLayout.NORTH, jPanel1Body);
        layout.putConstraint(SpringLayout.WEST, ramLabel, 5, SpringLayout.WEST, jPanel1Body);
        jPanel1Body.add(ramLabel);

        int xCounter= 5;
        // 16 frame entries per process
        for(int i = 0; i < 12; i++) {
            JComponent ramPages = makeTextPanel("page [" +String.valueOf(i) + "]");
            ramPages.setOpaque(false);

            if(i >= 0 && i <= 3) {
                // counter moet hier ook gezet worden?
                layout.putConstraint(SpringLayout.WEST, ramPages, xCounter, SpringLayout.WEST, jPanel1Body);
                layout.putConstraint(SpringLayout.NORTH, ramPages, 375, SpringLayout.NORTH, jPanel1Body);
            } else if(i >= 4 && i <= 7) {
                layout.putConstraint(SpringLayout.WEST, ramPages, xCounter, SpringLayout.WEST, jPanel1Body);
                layout.putConstraint(SpringLayout.NORTH, ramPages, 390, SpringLayout.NORTH, jPanel1Body);
            } else if(i >= 8 && i <= 11) {
                layout.putConstraint(SpringLayout.WEST, ramPages, xCounter, SpringLayout.WEST, jPanel1Body);
                layout.putConstraint(SpringLayout.NORTH, ramPages, 405, SpringLayout.NORTH, jPanel1Body);
            }

            jPanel1Body.add(ramPages);
            xCounter+=75;

            if(i == 3 || i == 7) {
                xCounter = 5;
            }
        }

        // FOOTER
        JPanel jPanel1Footer = new JPanel();
        layout.putConstraint(SpringLayout.WEST, jPanel1Footer, 0, SpringLayout.WEST, jPanel1);
        layout.putConstraint(SpringLayout.SOUTH, jPanel1Footer, 0, SpringLayout.SOUTH, jPanel1);

        // buttons
        JButton nextButton = new JButton("Volgende");
        nextButton.setPreferredSize(new Dimension(328,30));
        jPanel1Footer.add(nextButton);

        // buttonhandlers
        nextButton.addActionListener(new ActionListener() {
            int instructionCounter = 0;
            public void actionPerformed(ActionEvent e) {

                if((instructionCounter +1) > instructions.size()) {
                    System.out.println("Geen instructies meer over");
                    return;
                }

                // voer de instructie uit
                Main.optionOne(instructionCounter, instructions, pageTables, ram);
                PageTable currentTable = null;
                for(PageTable temp : pageTables){
                    if(temp.getProcessId() == instructions.get(instructionCounter).getProcessId()){
                        currentTable = temp;
                        break;
                    }
                }

                // data required per process
                int virtualAdres = Main.getPageCorrespondingToAddress(instructions.get(instructionCounter).getAddress());
                int virtualAdresNext = -1;
                int offset = -1;
                if((instructionCounter +1) < instructions.size()) {
                    virtualAdresNext = Main.getPageCorrespondingToAddress(instructions.get(instructionCounter+1).getAddress());
                    offset = instructions.get(instructionCounter+1).getAddress() - 4096 * Main.getPageCorrespondingToAddress(instructions.get(instructionCounter+1).getAddress());
                }
               int physicalAdres = virtualAdres+offset;
                int frame = -1;
                int reëleAdresRam = -1;
                if(currentTable.getSpecificPage(virtualAdres).getModifyBit()){
                    frame = currentTable.getSpecificPage(virtualAdres).getFrameNumber();
                    reëleAdresRam = frame * 4096 + offset;
                }


                // update timer
                jPanel1Header.remove(1);
                JComponent timer = new JTextArea(String.valueOf(instructionCounter));
                timer.setOpaque(false);
                layout.putConstraint(SpringLayout.NORTH, timer, 10, SpringLayout.NORTH, jPanel1Header);
                layout.putConstraint(SpringLayout.WEST, timer, 80, SpringLayout.WEST, jPanel1Header);
                jPanel1Header.add(timer, 1);

                // instructie update
                jPanel1Header.remove(3);

                JComponent instructie = new JTextArea(String.valueOf("[[ " + virtualAdres + " ] [ " + virtualAdresNext + " ] [ " + physicalAdres + " ] [ " + frame + " ] [ " + offset + " ]]"));
                instructie.setOpaque(false);
                layout.putConstraint(SpringLayout.NORTH, instructie, 55, SpringLayout.NORTH, jPanel1Header);
                layout.putConstraint(SpringLayout.WEST, instructie, 5, SpringLayout.WEST, jPanel1Header);
                jPanel1Header.add(instructie, 3);

                // instructie update
                jPanel1Header.remove(5);
                if(reëleAdresRam != -1){
                    JComponent reëleAdres = new JTextArea("[ " + reëleAdresRam + " ]");
                }
                else{
                    JComponent reëleAdres = new JTextArea("[ " + "N/A" + " ]");
                }
                reëleAdres.setOpaque(false);
                layout.putConstraint(SpringLayout.NORTH, reëleAdres, 85, SpringLayout.NORTH, jPanel1Header);
                layout.putConstraint(SpringLayout.WEST, reëleAdres, 80, SpringLayout.WEST, jPanel1Header);
                jPanel1Header.add(reëleAdres, 5);

                // updating the page table data
                jPanel1Body.remove(0);
                JComponent pageTableLabel = makeTextPanel("Page Table - [Last access][Present bit][Modify bit][Frame]");
                pageTableLabel.setOpaque(false);
                layout.putConstraint(SpringLayout.NORTH, pageTableLabel, 115, SpringLayout.NORTH, jPanel1Body);
                layout.putConstraint(SpringLayout.WEST,pageTableLabel, 5, SpringLayout.WEST, jPanel1Body);
                jPanel1Body.add(pageTableLabel, 0);

                // updating the pagetables - there are max 16 entries current process
                int c = 15;

                // 16 frame entries per process
                for(int i = 0; i < 16; i++) {
                    jPanel1Body.remove(i+1);

                    LocalDateTime lastAcces = pageTables.get(0).getSpecificPage(i).getLastAccessTime();
                    Boolean presentBit = pageTables.get(0).getSpecificPage(i).getPresentBit();
                    Boolean modifyBit = pageTables.get(0).getSpecificPage(i).getModifyBit();
                    String frameNumber = "EMPTY";

                    if(presentBit) {
                        frameNumber = String.valueOf(pageTables.get(0).getSpecificPage(i).getFrameNumber());
                    }

                    JComponent pageTable = makeTextPanel("[[ " + lastAcces + " ] [ " + presentBit + " ] [ " +  modifyBit + " ] [ " + frameNumber + " ]]");
                    pageTable.setOpaque(false);
                    layout.putConstraint(SpringLayout.WEST, pageTable, 5, SpringLayout.WEST, jPanel1Body);
                    layout.putConstraint(SpringLayout.NORTH, pageTable, (115+c), SpringLayout.NORTH, jPanel1Body);
                    jPanel1Body.add(pageTable, i+1);
                    c+=15;
                }

                // updating the ram
                jPanel1Body.remove(17);
                JComponent ramLabel = makeTextPanel("RAM - [[pid][page]]");
                ramLabel.setOpaque(false);
                layout.putConstraint(SpringLayout.NORTH,  ramLabel, 385, SpringLayout.NORTH, jPanel1Body);
                layout.putConstraint(SpringLayout.WEST, ramLabel, 5, SpringLayout.WEST, jPanel1Body);
                jPanel1Body.add(ramLabel, 17);

                int xCounter= 5;
                int amountProcessesInRam = ram.getProcessesInRam().size();
                List<Integer> visualRam = new ArrayList<>();
                List<Integer> visualProcessesInRam = new ArrayList<>();

                // for each process
                for(int i = 0; i < amountProcessesInRam; i++) {
                    PageTableEntry[] entries = ram.getProcessesInRam().get(i).getEntries();

                    int pid = ram.getProcessesInRam().get(i).getProcessId();

                    // for each entry in th pagetable for processes in visualRam
                    for(int j = 0; j < 16; j++) {
                        Boolean presentBit = entries[j].getPresentBit();

                        // if the ...  is in ram, get the framenumber it's at
                        if(presentBit == true) {
                            visualRam.add(entries[j].getFrameNumber());
                            visualProcessesInRam.add(pid);
                        }
                    }
                }


                // 16 frame entries per process
                for(int i = 0; i < 12; i++) {
                    jPanel1Body.remove(18+i);
                    JComponent ramPage = makeTextPanel("[[ " + visualProcessesInRam.get(i) + " ] [ " + visualRam.get(i)+ " ]]");
                    ramPage .setOpaque(false);

                    if(i >= 0 && i <= 3) {
                        // counter moet hier ook gezet worden?
                        layout.putConstraint(SpringLayout.WEST, ramPage , xCounter, SpringLayout.WEST, jPanel1Body);
                        layout.putConstraint(SpringLayout.NORTH, ramPage , 400, SpringLayout.NORTH, jPanel1Body);
                    } else if(i >= 4 && i <= 7) {
                        layout.putConstraint(SpringLayout.WEST, ramPage , xCounter, SpringLayout.WEST, jPanel1Body);
                        layout.putConstraint(SpringLayout.NORTH, ramPage , 415, SpringLayout.NORTH, jPanel1Body);
                    } else if(i >= 8 && i <= 11) {
                        layout.putConstraint(SpringLayout.WEST, ramPage , xCounter, SpringLayout.WEST, jPanel1Body);
                        layout.putConstraint(SpringLayout.NORTH, ramPage , 430, SpringLayout.NORTH, jPanel1Body);
                    }

                    jPanel1Body.add(ramPage , 18+i);
                    xCounter+=75;

                    if(i == 3 || i == 7) {
                        xCounter = 5;
                    }
                }

                // updating the panels
                jPanel1.repaint();
                jPanel1.add(jPanel1Header);
                jPanel1.add(jPanel1Body);
                instructionCounter+=1;
            }
        });

        // adding all the components to the main jPanel
        jPanel1.add(jPanel1Header);
        jPanel1.add(jPanel1Body);
        jPanel1.add(jPanel1Footer);


        tabbedPane.addTab("Optie 1", null, jPanel1,"Visualisatie optie 1");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        /*
         *
         * start of 2nd tab
         *
         */
        // layouts for our layout components/grids
        SpringLayout layout2 = new SpringLayout();
        JPanel jPanel2 = new JPanel();
        jPanel2.setBackground(Color.GRAY);
        jPanel2.setLayout(layout2);
        jPanel2.setSize(340,7000);
        jPanel2.setOpaque(false);

        JPanel jPanel2Header = new JPanel();
        jPanel2Header.setLayout(layout2);

     //   jPanel2Header.setOpaque(false);
        jPanel2Header.setPreferredSize(new Dimension(340, 1550));
        JScrollPane scrollPane = new JScrollPane(jPanel2Header);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        // all the components panels that need to be added to each option pannel
        // 1st option
        JComponent titleLabel2 = new JTextArea("Aantal schrijfopdrachten per process ");
        titleLabel2.setOpaque(false);

        jPanel2Header.add(titleLabel2);

        layout2.putConstraint(SpringLayout.NORTH, titleLabel2, 10, SpringLayout.NORTH, jPanel2Header);
        layout2.putConstraint(SpringLayout.WEST, titleLabel2, 5, SpringLayout.WEST, jPanel2Header);

        // footer 2
        JPanel jPanel2Footer = new JPanel();
        layout2.putConstraint(SpringLayout.WEST, jPanel2Footer, 0, SpringLayout.WEST, jPanel2);
        layout2.putConstraint(SpringLayout.SOUTH, jPanel2Footer, 0, SpringLayout.SOUTH, jPanel2);
        // buttons
        JButton runButton = new JButton("Run");
        runButton.setPreferredSize(new Dimension(328,30));
        jPanel2Footer.add(runButton);

        // adding the components panels to the main panel
        jPanel2.add(jPanel2Header);
        jPanel2.add(jPanel2Footer);

        // buttonhandlers
        runButton.addActionListener(new ActionListener() {
            int instructionCounter = 0;
            public void actionPerformed(ActionEvent e) {

                // voer de instructie uit
                Main.optionTwo(instructionCounter, instructions, pageTables, ram);

                int yCounter = 0;

                for(PageTable currentPageTable : pageTables){

                    // body update
                    JComponent process = new JTextArea("Process: [ " + String.valueOf(currentPageTable.getProcessId()) +" ]");
                    process.setOpaque(false);
                    layout2.putConstraint(SpringLayout.NORTH,process, (35+yCounter), SpringLayout.NORTH, jPanel2Header);
                    layout2.putConstraint(SpringLayout.WEST, process, 5, SpringLayout.WEST, jPanel2Header);


                    JComponent readCounter = new JTextArea("Readcount: [ " + String.valueOf(currentPageTable.getReadCounter()) +" ]");
                    readCounter.setOpaque(false);
                    layout2.putConstraint(SpringLayout.NORTH,readCounter, (50+yCounter), SpringLayout.NORTH, jPanel2Header);
                    layout2.putConstraint(SpringLayout.WEST, readCounter, 5, SpringLayout.WEST, jPanel2Header);

                    JComponent writeCounter = new JTextArea("Writecount: [ " + String.valueOf(currentPageTable.getWriteCounter()) +" ]");
                    writeCounter.setOpaque(false);
                    layout2.putConstraint(SpringLayout.NORTH, writeCounter, (65+yCounter), SpringLayout.NORTH, jPanel2Header);
                    layout2.putConstraint(SpringLayout.WEST, writeCounter, 5, SpringLayout.WEST, jPanel2Header);

                    JComponent pageToRamCounter = new JTextArea("PagesToRAM: [ " + String.valueOf(currentPageTable.getPageToRamCounter()) +" ]");
                    pageToRamCounter.setOpaque(false);
                    layout2.putConstraint(SpringLayout.NORTH,pageToRamCounter, (80+yCounter), SpringLayout.NORTH, jPanel2Header);
                    layout2.putConstraint(SpringLayout.WEST, pageToRamCounter, 5, SpringLayout.WEST, jPanel2Header);

                    JComponent frameToMemoryCounter = new JTextArea("FramesToMemory: [ " + String.valueOf(currentPageTable.getFrameToMemoryCounter()) +" ]");
                    frameToMemoryCounter.setOpaque(false);
                    layout2.putConstraint(SpringLayout.NORTH, frameToMemoryCounter, (95+yCounter), SpringLayout.NORTH, jPanel2Header);
                    layout2.putConstraint(SpringLayout.WEST, frameToMemoryCounter, 5, SpringLayout.WEST, jPanel2Header);

                    JComponent line = new JTextArea("[ ----------------------- ]");
                    line.setOpaque(false);
                    layout2.putConstraint(SpringLayout.NORTH, line, (115+yCounter), SpringLayout.NORTH, jPanel2Header);
                    layout2.putConstraint(SpringLayout.WEST, line, 5, SpringLayout.WEST, jPanel2Header);

                    //data.setOpaque(false);
                    jPanel2Header.add(process);
                    jPanel2Header.add(readCounter);
                    jPanel2Header.add(writeCounter);
                    jPanel2Header.add(pageToRamCounter);
                    jPanel2Header.add(frameToMemoryCounter);
                    jPanel2Header.add(line);
                    yCounter+=110;
                }

                // updating the panels
                jPanel2.repaint();
                jPanel2.add(jPanel2Header);
            }
        });


        tabbedPane.addTab("Optie 2", null, jPanel2,"Visualisatie optie 2");

        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        //Add the tabbed pane to this panel.
        add(tabbedPane);

        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

    protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(10, 1));
        panel.add(filler);
        return panel;
    }

    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Userinterface.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private static void createAndShowGUI(List<Main.Instruction> instructions, ArrayList<PageTable> pageTables, Ram ram) {
        //Create and set up the window.
        JFrame frame = new JFrame("Paging");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Add content to the window.

        frame.add(new Userinterface(instructions, pageTables, ram), BorderLayout.CENTER);

        frame.setMinimumSize(new Dimension(357, 650));
        frame.setMaximumSize(new Dimension(357, 650));
        frame.setSize(new Dimension(357, 650));

        // Display the window.
        frame.pack();

        // Center the window
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}