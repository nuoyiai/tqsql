package tpsql.pagesize;

public class PageSizeHelper {

    public static int getLimit(int pagesize, int pagenum) {
        return pagesize * pagenum;
    }

    public static int getOffset(int pagesize, int pagenum) {
        return pagesize * (pagenum - 1);
    }

}
