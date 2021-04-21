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

        Userinterface userinterface = new Userinterface();

        userinterface.main(null);
    }

    public static class Instruction {

        private int instructionId;
        private String operation;
        private int address;

        public Instruction(int instructionId, String operation, int address) {
            this.instructionId = instructionId;
            this.operation = operation;
            this.address = address;
        }

        public int getInstructionId(){
            return this.instructionId;
        }

        public String getOperation(){
            return this.operation;
        }

        public int getAddress(){
            return this.address;
        }

    }
}