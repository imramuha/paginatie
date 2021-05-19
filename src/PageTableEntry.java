import java.time.LocalDateTime;

public class PageTableEntry{
        private boolean presentBit;
        private boolean modifyBit;
        private LocalDateTime lastAccessTime;
        private Integer frameNumber;
        private Integer pageNumber;
        public PageTableEntry(int pageNumber){
            this.frameNumber = null;
            this.pageNumber = pageNumber;
            this.presentBit = false;
            this.modifyBit = true;
            this.lastAccessTime = LocalDateTime.now();
        }
        public boolean getPresentBit(){
            return this.presentBit;
        }
        public void setPresentBit(Boolean newState){
            this.presentBit = newState;
        }
        public boolean getModifyBit(){
            return this.modifyBit;
        }
        public void setModifyBit(Boolean newState){
            this.modifyBit = newState;
        }
        public int getFrameNumber(){
            return this.frameNumber;
        }
        public void setFrameNumber(Integer newFrameNumber){
            this.frameNumber = newFrameNumber;
        }
        public int getPageNumber(){
            return this.pageNumber;
        }
        public LocalDateTime getLastAccessTime(){
            return this.lastAccessTime;
        }
        public void setLastAccessTime(){
            lastAccessTime = LocalDateTime.now();
        }
    }