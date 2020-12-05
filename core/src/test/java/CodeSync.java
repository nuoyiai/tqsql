import tpsql.core.util.FileUtil;

public class CodeSync {

    public static void main(String[] args){

        Context ctx = new Context();
        ctx.setSourcePath("D:\\svn\\webapp\\huatong-lcp\\platform-server\\src\\framework\\tpsql-framework-core\\src");
        ctx.setTargetPath("D:\\tpsql");
        ctx.setOldPackage("tpsql");
        ctx.setNewPackage("tpsql");
        ctx.setFileFixs(new String[]{".xml",".java"});

        copyAndChange(ctx);
    }

    /**
     * 拷贝代码并改变包名
     * @param context
     */
    private static void copyAndChange(Context context){
        String[] files = FileUtil.getFilesByPath(context.getSourcePath(),context.getFileFixs());
        String rootPath = FileUtil.getParentPath(context.getSourcePath());
        for(String file : files){
            String fileName = FileUtil.getFileName(file);
            String filePath = FileUtil.getParentPath(file);
            String path = filePath.replace(rootPath,"");
            String savePath = FileUtil.contact(context.getTargetPath(),path.replace(context.oldPackage,context.getNewPackage()));
            FileUtil.createDirectory(savePath);
            String saveFile = FileUtil.contact(savePath,fileName.replace(context.oldPackage,context.getNewPackage()));
            String fileContent = FileUtil.getFileString(file,"utf-8");
            String saveContent = fileContent.replaceAll(context.oldPackage,context.getNewPackage());
            FileUtil.writeFile(saveFile,saveContent,"utf-8");
            System.out.println(saveFile);
        }
    }

    static class Context{
        private String oldPackage;
        private String newPackage;
        private String sourcePath;
        private String targetPath;
        private String[] fileFixs;

        public String getOldPackage() {
            return oldPackage;
        }

        public void setOldPackage(String oldPackage) {
            this.oldPackage = oldPackage;
        }

        public String getNewPackage() {
            return newPackage;
        }

        public void setNewPackage(String newPackage) {
            this.newPackage = newPackage;
        }

        public String getSourcePath() {
            return sourcePath;
        }

        public void setSourcePath(String sourcePath) {
            this.sourcePath = sourcePath;
        }

        public String getTargetPath() {
            return targetPath;
        }

        public void setTargetPath(String targetPath) {
            this.targetPath = targetPath;
        }

        public String[] getFileFixs() {
            return fileFixs;
        }

        public void setFileFixs(String[] fileFixs) {
            this.fileFixs = fileFixs;
        }
    }

}
