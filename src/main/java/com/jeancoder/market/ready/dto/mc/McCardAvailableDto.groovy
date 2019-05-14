package com.jeancoder.market.ready.dto.mc

import java.util.List


class McCardAvailableDto {
	
	BigInteger id;
	
	String title;
		
	String info;
		
	Integer mcLevel;
		
	boolean outer;
		
	String prefix;
		
	Integer aMcPrefix;
		
	Integer aMchPrefix;
		
	Integer startCn;
		
	Integer endCn;
		
	Integer ctCn;
		
	boolean suppJf = true;
		
	String defJfAddRatio;
		
	String defJfConRatio;
	
	List<McHierchHiDto>  hierarchyList;
}
