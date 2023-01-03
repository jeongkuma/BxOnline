package kr.co.scp.ccp.common.dto;

import java.math.BigInteger;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class PaginationDto {
	
	private Integer displayRowCount;           // 출력할 데이터 개수
    private Integer rowStart, rowEnd;            // 시작행번호, 종료행 번호
    private BigInteger totPage, totRow;           // 전체 페이수, 전체 데이터 수
    private Integer page, pageStart, pageEnd;    // 현재 페이지, 시작페이지, 종료페이지

    public void pageCalculate(BigInteger total,  Integer displayRowCount , Integer curPage) {
    	setDisplayRowCount(displayRowCount);
    	setPage(curPage);
        totRow  = total;
        totPage = total.divide(BigInteger.valueOf(displayRowCount));
        
        BigInteger[] tmp = total.divideAndRemainder(BigInteger.valueOf(displayRowCount));
        BigInteger tmp2 = tmp[1];
        if ( tmp2.floatValue() > 0 ) totPage.add(BigInteger.valueOf(1));

        pageStart = (page - (page - 1) % 10) ;
        pageEnd = pageStart + 9;
        if (pageEnd > totPage.longValue()) pageEnd = totPage.intValue();
        
        rowStart = ((page - 1) * displayRowCount) + 1 ;
        rowEnd = rowStart + displayRowCount -1;
    } 
}
