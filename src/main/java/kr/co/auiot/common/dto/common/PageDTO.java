package kr.co.auiot.common.dto.common;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class PageDTO {
	private Integer displayRowCount;           // 출력할 데이터 개수
    private Integer rowStart, rowEnd;            // 시작행번호, 종료행 번호
    private Integer totPage, totRow=0;           // 전체 페이수, 전체 데이터 수
    private Integer page, pageStart, pageEnd;    // 현재 페이지, 시작페이지, 종료페이지

    public void pageCalculate(Integer total,  Integer displayRowCount , Integer curPage) {
    	setDisplayRowCount(displayRowCount);
    	setPage(curPage);
        totRow  = total;
        totPage    = (int) ( total / displayRowCount );
        
        if ( total % displayRowCount > 0 ) totPage++;

        pageStart = (page - (page - 1) % 10) ;
        pageEnd = pageStart + 9;
        if (pageEnd > totPage) pageEnd = totPage;
        
        rowStart = ((page - 1) * displayRowCount) + 1 ;
        rowEnd = rowStart + displayRowCount -1;
    } 

}


