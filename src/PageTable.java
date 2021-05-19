import java.util.ArrayList;

public class PageTable{
        private PageTableEntry[] entries;
        private Integer processId;
        private Integer writeCounter;
        private Integer readCounter;
        private Integer pageToRamCounter;
        private Integer frameToMemoryCounter;
        public PageTable(int processId){
            this.processId = processId;
            this.writeCounter = 0;
            this.readCounter = 0;
            this.pageToRamCounter = 0;
            this.frameToMemoryCounter = 0;

            entries = new PageTableEntry[16];
            for(int counter = 0; counter < entries.length; counter ++){
                entries[counter] = new PageTableEntry(counter);
            }
        }

        public int getProcessId(){
            return this.processId;
        }

        public PageTableEntry[] getEntries(){
            return this.entries;
        }
        public ArrayList<Integer> terminateProcess(){
            ArrayList<Integer> availableFrameNumbers = new ArrayList<>();
            for(PageTableEntry entry: entries){
                if(entry.getPresentBit()){
                    entry.setPresentBit(false);
                    availableFrameNumbers.add(entry.getFrameNumber());
                    entry.setFrameNumber(null);
                    if(entry.getModifyBit()){
                        writeCounter++;
                        entry.setModifyBit(false);
                    }
                }
            }
            return availableFrameNumbers;
        }
        public void addWrite(){
            writeCounter ++;
        }
        public Integer getWriteCounter(){
            return writeCounter;
        }
        public void addReadCounter(){
            readCounter ++;
        }
        public Integer getReadCounter(){
            return readCounter;
        }
        public void addPageToRamCounter(){
            pageToRamCounter ++;
        }
        public Integer getPageToRamCounter(){
            return pageToRamCounter;
        }
        public void addFrameToMemoryCounter(){
            frameToMemoryCounter ++;
        }
        public Integer getFrameToMemoryCounter(){
            return frameToMemoryCounter;
        }
        public PageTableEntry getSpecificPage(Integer pageNumber){
            return entries[pageNumber];
        }

        public PageTableEntry getNextNotLoadedPage(){
            for(PageTableEntry page: entries){
                if(!page.getPresentBit()){
                    return page;
                }
            }
            //Should never happen
            System.out.println("ERROOOOOOOOOOOR");
            return new PageTableEntry(17);
        }
        public PageTableEntry getLastAccessedPage(){
            PageTableEntry lastAccessedPage = null;
            for(PageTableEntry process: entries){
                if(process.getPresentBit()){
                    if(lastAccessedPage == null){
                        lastAccessedPage = process;
                    }
                    else if(process.getLastAccessTime().isBefore(lastAccessedPage.getLastAccessTime())){
                        lastAccessedPage = process;
                    }
                }
            }
            return lastAccessedPage;
        }
    }
