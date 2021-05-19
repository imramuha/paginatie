/*
 *
 *  author:  Imran Muhammad & Else Goethals
 *  date:    07/04/2021
 *  files:   Main
 *
 * */

// Imports
import java.io.File;
import java.time.LocalDateTime;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


abstract class Main {
    public static void main(String[] args) throws Exception {

        Ram ram = new Ram();

        // reading xml-file and making the data usable
        File xmlFile = new File("Instructions_30_3.xml");
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        org.w3c.dom.Document document = documentBuilder.parse(xmlFile);

        NodeList list = document.getElementsByTagName("instruction");
        //Collecting all instructionsii in an array
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

        //List containing all known pageTables/Process
        ArrayList<PageTable> pageTables = new ArrayList<>();
        int amountInstructions = instructions.size();
        System.out.println(amountInstructions);

        Userinterface.main(instructions, pageTables, ram);

        //Timer
        int timer = 0;

        // button will increment and show the next instruction everytime i press it.

        //Methodes hieronder moeten geactiveerd worden afhankelijk van de knop in de interface die ingedrukt wordt.
        //Laat triggerOneInstruction een stringbuilder terugsturen ofzo met al de gegevens die je nodig hebt en die na het uitvoeren van de functie in je interface zetten.
        //code voor button 1:
        timer ++;


        //code voor button 2:
        /*
        executeAllInstruction(instructions, pageTables, timer, ram);
        */
    }

    public static void optionOne(int timer, List<Instruction> instructions, ArrayList<PageTable> pageTables, Ram ram){
        if(timer < instructions.size()-1) {
            executeInstruction(instructions.get(timer), pageTables, ram);
        } else {
            System.out.println("Geen instructies meer over");
        }
    }

    public static void optionTwo(int timer, List<Instruction> instructions, ArrayList<PageTable> pageTables, Ram ram) {
        executeAllInstruction(timer, instructions, pageTables, ram);
    }

    public static void executeInstruction(Instruction currentInstruction, ArrayList<PageTable> pageTables, Ram ram){
        // params per instructions
        System.out.printf("     %-12s", currentInstruction.getProcessId());
        System.out.printf(" %-13s", currentInstruction.getOperation());
        System.out.printf(" %-15s \n", currentInstruction.getAddress());
        switch (currentInstruction.getOperation()) {
            case Start :
                PageTable currentTable = new PageTable(currentInstruction.getProcessId());
                pageTables.add(currentTable);
                ram.addNewProcessToRam(currentTable, null);
                break;
            case Read : ram.readInstruction(currentInstruction.getProcessId(), getPageCorrespondingToAddress(currentInstruction.getAddress()), pageTables); break;
            case Write : ram.writeInstruction(currentInstruction.getProcessId(), getPageCorrespondingToAddress(currentInstruction.getAddress()), pageTables); break;
            case Terminate : ram.terminateInstruction(currentInstruction.getProcessId()); break;
        }
    }

    public static void executeAllInstruction(int timer, List<Instruction> instructions, ArrayList<PageTable> pageTables, Ram ram){ System.out.println("0");
        System.out.println(timer);
        for(;timer < instructions.size(); timer ++){
            executeInstruction(instructions.get(timer), pageTables, ram);
        }
    }
    //Method to find the corresponding
    public static Integer getPageCorrespondingToAddress(Integer address){
        if(address >= 0 && address <= 4096) {
            return 0;
        } else  if(address >= 4097 && address <= 8192) {
            return 1;
        } else  if(address >= 8192 && address <= 12288) {
            return 2;
        } else  if(address >= 12289 && address <= 16384) {
            return 3;
        } else  if(address >= 16385 && address <= 20480) {
            return 4;
        } else  if(address >= 20481 && address <= 24576) {
            return 5;
        } else  if(address >= 24577 && address <= 28672) {
            return 6;
        } else  if(address >= 28673 && address <= 32768) {
            return 7;
        } else  if(address >= 32769 && address <= 36864) {
            return 8;
        } else  if(address >= 36865 && address <= 40960) {
            return 9;
        } else  if(address >= 40961 && address <= 45056) {
            return 10;
        } else  if(address >= 45057 && address <= 49152) {
            return 11;
        } else  if(address >= 49153 && address <= 53248) {
            return 12;
        } else  if(address >= 53249 && address <= 57344) {
            return 13;
        } else  if(address >= 57345 && address <= 61440) {
            return 14;
        } else{
            return 15;
        }
    }

    public static class Instruction {

        private Integer processId;
        private Operation operation;
        private Integer address;

        public Instruction(int instructionId, String operation, int address) {
            this.processId = instructionId;
            this.operation = Operation.valueOf(operation);
            this.address = address;
        }

        public Integer getProcessId(){
            return this.processId;
        }

        public Operation getOperation(){
            return this.operation;
        }

        public Integer getAddress(){
            return this.address;
        }
    }

    enum Operation{
        Start,
        Read,
        Write,
        Terminate
    }

}

