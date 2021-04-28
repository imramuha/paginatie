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

        // -- for every instruction we look if its read or write then we do that
        int[] virtualAddressSpace = new int[16];
        System.out.println(virtualAddressSpace);


        // for every instruction that is present we, populate our virtual address space
        // there are 4 operations: start, read, write and terminate
        // at start -> we make a new process
        // virtualaddress only used during read/write
        // at terminate we change our processes data?
        for(int i = 0; i < instructions.size(); i++) {

            if(instructions.get(i).getOperation().equals("Start")){
                System.out.println(i + "START");
            }
            // this only happens when read/write aka vitualspaceaddress.
            else if (instructions.get(i).getOperation().equals("Write") || instructions.get(i).getOperation().equals("Read")) {
                //System.out.println(i + " " + instructions.get(i).getAddress());
                System.out.println(i + "READ OR WRITE");
                // we know that there are
                // 4096 bits -> 65536 / 16 frames
                // [0, 15] -> dus afhankelijk waar het address zal vallen, nemen wij dat frame
                int address = instructions.get(i).getAddress();
                if(address >= 0 && address <= 4096) {
                    virtualAddressSpace[0] = address;
                } else  if(address >= 4097 && address <= 8192) {
                    virtualAddressSpace[1] = address;
                } else  if(address >= 8192 && address <= 12288) {
                    virtualAddressSpace[2] = address;
                } else  if(address >= 12289 && address <= 16384) {
                    virtualAddressSpace[3] = address;
                } else  if(address >= 16385 && address <= 20480) {
                    virtualAddressSpace[4] = address;
                } else  if(address >= 20481 && address <= 24576) {
                    virtualAddressSpace[5] = address;
                } else  if(address >= 24577 && address <= 28672) {
                    virtualAddressSpace[6] = address;
                } else  if(address >= 28673 && address <= 32768) {
                    virtualAddressSpace[7] = address;
                } else  if(address >= 32769 && address <= 36864) {
                    virtualAddressSpace[8] = address;
                } else  if(address >= 36865 && address <= 40960) {
                    virtualAddressSpace[9] = address;
                } else  if(address >= 40961 && address <= 45056) {
                    virtualAddressSpace[10] = address;
                } else  if(address >= 45057 && address <= 49152) {
                    virtualAddressSpace[11] = address;
                } else  if(address >= 49153 && address <= 53248) {
                    virtualAddressSpace[12] = address;
                } else  if(address >= 53249 && address <= 57344) {
                    virtualAddressSpace[13] = address;
                } else  if(address >= 57345 && address <= 61440) {
                    virtualAddressSpace[14] = address;
                } else  if(address >= 61441 && address <= 65536) {
                    virtualAddressSpace[15] = address;
                }
            }
            else if(instructions.get(i).getOperation().equals("Terminate")) {
                System.out.println(i + "TERMINATE");
            }
        }

        // show the addresses in virtualaddressspace
        for(int i = 0; i < virtualAddressSpace.length; i++) {
            System.out.println(i + " " + virtualAddressSpace[i]);
        }

        // this is for interface -- for later
        Userinterface userinterface = new Userinterface();
        userinterface.main(instructions, virtualAddressSpace);

    }


    // een process:
    /// een pagetable
    /// kan meerdere instructies hebben
    // kan nooit groter zijn dan 4KByte*16pages [64KByte]
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

    // per process bevat een pagetable die max 16 [0,15] entries kan bevatten
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
    static void writeVM(){

    }
    static void main() {

    }
}

// for 2nd button
class RunInstructions {
    static void main() {

    }
}