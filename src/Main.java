/*
 *
 *  author:  Imran Muhammad & Else Goethals
 *  date:    07/04/2021
 *  files:   Main
 *
 * */

// Imports
import java.io.File;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


abstract class Main {
    public static void main(String[] args) throws Exception {

        // reading xml-file and making the data usable
        File xmlFile = new File("Instructions_30_3.xml");
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        org.w3c.dom.Document document = documentBuilder.parse(xmlFile);

        NodeList list = document.getElementsByTagName("instruction");

        List<Instruction> instructions = new ArrayList<>();

        for (int i = 0; i < list.getLength(); i++) {

            Node node = list.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element element = (Element) node;

                Instruction instruction = new Instruction (
                        Integer.parseInt(element.getElementsByTagName("processID").item(0).getTextContent()),
                        element.getElementsByTagName("operation").item(0).getTextContent(),
                        Integer.parseInt(element.getElementsByTagName("address").item(0).getTextContent())
                );
                instructions.add(instruction);
            }
        }

        // lists of our accessible data
        int amountInstructions = instructions.size();
        System.out.println(amountInstructions);

        System.out.println("Current data");
        System.out.printf("instruction -- operation -- address\n");


        for (int i = 0; i < amountInstructions; i++) {
            // params per instructions
            System.out.printf("     %-12s", instructions.get(i).getInstructionId());
            System.out.printf(" %-13s", instructions.get(i).getOperation());
            System.out.printf(" %-15s \n", instructions.get(i).getAddress());
        }

        // button 1
        RunInstruction.main();

        // button 2
        RunInstructions.main();


        Userinterface userinterface = new Userinterface();
        userinterface.main(instructions);
    }


    // een process:
    /// een pagetable
    /// kan meerdere instructies hebben
    public static class Process {

    }

    // elke instructie is behoort tot een process - kan je achter van de PID
    public static class Instruction {

        private int pid;
        private String operation;
        private int address;

        public Instruction(int instructionId, String operation, int address) {
            this.pid = instructionId;
            this.operation = operation;
            this.address = address;
        }

        public int getInstructionId(){
            return this.pid;
        }

        public String getOperation(){
            return this.operation;
        }

        public int getAddress(){
            return this.address;
        }
    }

    // per process 1 pagetable en kan 12 (0,11) entries bevatted. Daarnaast
    class pageTable{
        private int frameId;
        private int presentBit;
        private int modifyBit;
        private int lastAccessTime;
    }

    // RAM - bevat 12 frames, elke van max 4KByte ruimte voor de pages. Daarom zijn pages ook 4KByte. Normaal zit hier
    // ook de geheugensharing en page tables in bewaard.
    class mainGeheugen {
        // bestaat uit 12 frames
        // en elke frame kan een page bevatten
        // page tables
    }

    // aka virtuele adresruimte
    class virtueleGeheugen {
        // bevat de pages die niet gebruikt worden
    }
}

// te doen:
// 01. address vertaling
// 02. clock incrementering
// 03. least recently used replace strategy als er nieuwe proces bijkoment ingv er geen plaats meer is
//     voo zij instructies en er plaats gemaakt moet worden.

// for 1st button
class RunInstruction {
    static void main() {

    }
}

// for 2nd button
class RunInstructions {
    static void main() {

    }
}