import java.util.ArrayList;
import java.util.List;


public class Ram{
        private List<PageTable> processesInRam;
        private List<Integer> framesAvailable;


        public Ram(){
            //Most recently used is first
            processesInRam = new ArrayList<PageTable>();
            framesAvailable = new ArrayList<Integer>();
            for(int counter = 0; counter < 12; counter++){
                framesAvailable.add(counter);
            }
        }
        // Execution instruction

        //Start instruction
        public void addNewProcessToRam(PageTable newProcess, Integer pageNumber) {
            System.out.println("Start for process " + newProcess.getProcessId());
            //remove 1 process from Ram. Decide which process was last accessed
            if (processesInRam.size() == 4) {
                PageTableEntry lastAccessedPage = null;
                PageTable tableToRemove = null;
                for (PageTable currentPageTable : processesInRam) {
                    for (PageTableEntry currentPage : currentPageTable.getEntries()) {
                        if (lastAccessedPage == null) {
                            lastAccessedPage = currentPage;
                            tableToRemove = currentPageTable;
                        } else if (currentPage.getLastAccessTime().isBefore(lastAccessedPage.getLastAccessTime())) {
                            lastAccessedPage = currentPage;
                            if (tableToRemove != currentPageTable) {
                                tableToRemove = currentPageTable;
                            }
                        }
                    }
                }
                this.clearPageFromRam(tableToRemove); //4 frames will be free
            }
            else if(processesInRam.size() != 0){
                clearSpaceForProcess(); //totalframes divided by currentprocess + newprocess will be made free.
            }


            //
            //Distribute frames for the new process
            int framesPerProcess = 12/(processesInRam.size()+1); //calculate how many frames we can assign
            System.out.println("Frames to add for process "+ newProcess.getProcessId() + ": " + framesPerProcess);
            processesInRam.add(newProcess);
            for(int counter = 0; counter < framesPerProcess; counter ++){
                if(counter == 0 && pageNumber != null){
                    loadPageToRam(newProcess, newProcess.getSpecificPage(pageNumber));
                }
                else{
                    loadPageToRam(newProcess, newProcess.getNextNotLoadedPage());
                }
            }
        }
                //Read instruction
        public void readInstruction(Integer processId, Integer pageNumber, ArrayList<PageTable> allPageTables){
            System.out.println("Read for process " + processId + " For pagenumber " + pageNumber);
            PageTable currentProcess = null;
            PageTableEntry readPage = null;
            //Search process in existing processes in Ram
            for(PageTable process: processesInRam){
                if(process.getProcessId() == processId){
                    currentProcess = process;
                }
            }
            if(currentProcess == null){
                //Find process in list of all PageTables
                for(PageTable process: allPageTables){
                    if(process.getProcessId() == processId){
                        currentProcess = process;
                    }
                }
                if(currentProcess == null){
                    System.out.println("ERROR: current process is null.");
                }
                addNewProcessToRam(currentProcess, pageNumber);
                readPage = currentProcess.getSpecificPage(pageNumber);
            }
            else{
                //Found process in ram
                readPage = currentProcess.getSpecificPage(pageNumber);
                //Check if page in memory
                if(!readPage.getPresentBit()){
                    removeFrameFromRam(currentProcess, currentProcess.getLastAccessedPage());
                    readPage.setFrameNumber(getNextAvailableFrameNumber());
                    readPage.setPresentBit(true);
                }
            }
            readPage.setLastAccessTime();
            currentProcess.addReadCounter();;
        }
        //Write instruction
        public void writeInstruction(Integer processId, Integer pageNumber, ArrayList<PageTable> allPageTables){
            System.out.println("Write for process " + processId + " For pagenumber " + pageNumber);

            PageTable currentProcess = null;
            PageTableEntry writePage;
            for(PageTable process: processesInRam){
                if(process.getProcessId() == processId){
                    currentProcess = process;
                }
            }
            if(currentProcess == null){
                for(PageTable process: allPageTables){
                    if(process.getProcessId() == processId){
                        currentProcess = process;
                    }
                }
                if(currentProcess == null){
                    System.out.println("ERROR: current process is null.");
                }
                this.addNewProcessToRam(currentProcess, pageNumber);
                writePage = currentProcess.getSpecificPage(pageNumber);
            }
            else{
                writePage = currentProcess.getSpecificPage(pageNumber);
                if(!writePage.getPresentBit()){
                    removeFrameFromRam(currentProcess, currentProcess.getLastAccessedPage());
                    loadPageToRam(currentProcess, writePage);
                }
            }
            writePage.setLastAccessTime();
            writePage.setModifyBit(true);
            currentProcess.addWrite();
        }

        //Terminate Instruction
        public void terminateInstruction(Integer processId){
            System.out.println("Terminate process " + processId);

            for(PageTable process: processesInRam){
                if(process.getProcessId() == processId){
                    this.removePageTableFromRam(process);
                    redistributeFramesAmongCurrentProcess();
                    System.out.println("Redistributed frames to pages");
                    break;
                }
            }
        }

        //Used to free space for new process in ram
        public void clearPageFromRam(PageTable pageTableToRemove){
            processesInRam.remove(pageTableToRemove);
            ArrayList<Integer> availableFrameNumbers = pageTableToRemove.terminateProcess();
            this.framesAvailable.addAll(availableFrameNumbers);
        }

        //General functions
        //Used for terminating a Process
        public void removePageTableFromRam(PageTable tableToRemove){
            processesInRam.remove(tableToRemove);
            System.out.println("Removing from RAM page "+ tableToRemove.getProcessId());
            for(PageTableEntry currentPage : tableToRemove.getEntries()) {
                if(currentPage.getPresentBit()){
                    removeFrameFromRam(tableToRemove, currentPage);
                }
            }
            System.out.println("Following "+ framesAvailable.size()+ " frames are available :");
            for(int i: framesAvailable){
                System.out.println(i);
            }
        }
        //Used for terminating a process
        public void removeFrameFromRam(PageTable process, PageTableEntry page){
            System.out.println("Removing page "+ page.getPageNumber()+ " from frame "+ page.getFrameNumber());
            if (page.getPresentBit()) {
                page.setPresentBit(false);
                framesAvailable.add(page.getFrameNumber());
                page.setFrameNumber(null);
                if(page.getModifyBit()){
                    //Add write if frame is modified
                    process.addFrameToMemoryCounter();
                    page.setModifyBit(false);
                }
            }
        }

        public List<PageTable> getProcessesInRam() {
            return processesInRam;
        }

        //Used after termination of progress
        public void redistributeFramesAmongCurrentProcess(){
            if(framesAvailable.size() != 0 && processesInRam.size() != 0){
                if(framesAvailable.size() % processesInRam.size() == 0){
                    int framesPerProcess = framesAvailable.size()/processesInRam.size();
                    System.out.println("Adding " + framesPerProcess + " pages to ram for process");
                    for(PageTable process : processesInRam){
                        System.out.println("Adding processes to frames for process " + process.getProcessId());
                        for(int counter = 0; counter < framesPerProcess; counter ++){
                            loadPageToRam(process, process.getNextNotLoadedPage());
                        }
                    }
                }
                else{
                    System.out.println("ERROR: amount of frames available not divisible by the amount of processes in ram");
                }
            }
        }

        public void loadPageToRam(PageTable process, PageTableEntry page){
            page.setPresentBit(true);
            int frameNumber = getNextAvailableFrameNumber();
            System.out.println(frameNumber);
            page.setFrameNumber(frameNumber);
            process.addPageToRamCounter();;
            System.out.println("Adding to frame page" + page.getPageNumber() + " as frame " + page.getFrameNumber());
        }
        //Used for maintaining  the frame numbers
        public Integer getNextAvailableFrameNumber(){
            System.out.println("Using next framenumber available " + framesAvailable.get(0));
            Integer frameNumber = framesAvailable.get(0);
            framesAvailable.remove(0);
            return frameNumber;
        }

        //Clears frames so a new process can be added
        public void clearSpaceForProcess(){
            int framesToClearPerProcess = (12/(processesInRam.size()+1))/processesInRam.size();
            System.out.println("frames to clear per process: " + framesToClearPerProcess);
            for(PageTable currentProcess : processesInRam){
                System.out.println("Clearing frames for process: "+ currentProcess.getProcessId());
                List<PageTableEntry> framesToRemove = new ArrayList<>();
                for(PageTableEntry currentPage: currentProcess.getEntries()){
                    if(currentPage.getPresentBit()){
                        if(framesToRemove.size() < framesToClearPerProcess){
                            framesToRemove.add(currentPage);
                        }
                        else{
                            for(int counter = 0; counter < framesToRemove.size(); counter++){
                                if(framesToRemove.get(counter).getLastAccessTime().isAfter(currentPage.getLastAccessTime())){
                                    framesToRemove.set(counter, currentPage);
                                    break;
                                }
                            }
                        }
                    }
                }
                for(PageTableEntry frameToRemove : framesToRemove){
                    System.out.println("Removing frame "+ frameToRemove.getFrameNumber());
                    removeFrameFromRam(currentProcess, frameToRemove);
                }
            }
            System.out.println("Following "+ framesAvailable.size()+ " frames are available :");
            for(int i: framesAvailable){
                System.out.println(i);
            }
        }
    }