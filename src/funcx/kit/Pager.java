package funcx.kit;

/**
 * 分页模型
 * 
 * <p>
 * 放着算个纪念吧...
 * 
 * @author Sylthas
 *
 */
public class Pager {

    /** 总记录条数 **/
    private int totalRows = 0;

    /** 每页记录条数 **/
    private int pageSize = 30;

    /** 起始记录行 **/
    private int startRow = 0;

    /** 当前页编号 **/
    private int currentPage = 1;

    /** 总页数 * */
    private int totalPages = 0;

    private Pager() {
    }

    /**
     * 默认pageSize构造
     * 
     * @param totalRows  总记录条数
     */
    private Pager(int totalRows) {
        this.totalRows = totalRows;
        this.totalPages = this.totalRows / this.pageSize;
        if (this.totalRows % pageSize != 0) {
            this.totalPages++;
        }
    }

    /**
     * 指定pageSize构造
     * 
     * @param totalRows 总记录条数
     * @param pageSize 分页大小
     */
    private Pager(int totalRows, int pageSize) {
        this.totalRows = totalRows;
        this.pageSize = pageSize;
        this.totalPages = this.totalRows / this.pageSize;
        if (this.totalRows % pageSize != 0) {
            this.totalPages++;
        }
    }

    /**
     * 获取分页模型
     * 
     * @param pageNo 页码
     * @param totalRows 总记录数
     * @return 分页模型
     */
    public static Pager getPager(int pageNo, int totalRows) {
        Pager pager = new Pager(totalRows);
        return initPager(pageNo, pager);
    }

    /**
     * 获取分页模型
     * 
     * @param pageNo 页码
     * @param pageSize 页面显示记录数
     * @param totalRows 总记录数
     * @return 分页模型
     */
    public static Pager getPager(int pageNo, int pageSize, int totalRows) {
        Pager pager = new Pager(totalRows, pageSize);
        return initPager(pageNo, pager);
    }

    /**
     * 初始化 分页模型
     * 
     * @param pageNo 页码
     * @param pager 分页模型
     * @return 分页模型
     */
    private static Pager initPager(int pageNo, Pager pager) {
        if (pageNo < 1) {
            pageNo = 1;
        } else if (pageNo > pager.getTotalPages()) {
            pageNo = pager.getTotalPages() > 0 ? pager.getTotalPages() : 1;
        }
        pager.gotoPage(pageNo);
        return pager;
    }

    /**
     * 判断是否有上一页
     * 
     * @return boolean
     */
    public boolean isPrevious() {
        return this.currentPage > 1;
    }

    /**
     * 判断是否有下一页
     * 
     * @return boolean
     */
    public boolean isNext() {
        return this.currentPage < this.totalPages;
    }

    /**
     * 执行翻页
     * 
     * @param pageNo 页码
     */
    public void gotoPage(int pageNo) {
        this.currentPage = pageNo;
        this.startRow = (this.currentPage - 1) * this.pageSize;
    }
    
    /* getter setter methods */
    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

}
