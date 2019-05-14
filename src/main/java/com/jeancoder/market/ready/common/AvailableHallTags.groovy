package com.jeancoder.market.ready.common
import   com.jeancoder.market.ready.dto.SelectModel
enum AvailableHallTags  {
	NORMAL("1001", "普通"),
	VIP("1002", "VIP"),
	LOVE("1003", "情侣"),
	DMAX("1004", "中国巨幕"),
	LASER("1005", "激光"),
	DBOX("1006", "DBOX"),
	DOLBY("1007", "杜比"),
	private String code;
	private String name;
	
	private AvailableHallTags(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String key() {
		return this.code;
	}

	public SelectModel toSlectModel() {
		SelectModel sm = new SelectModel();
		sm.setKey(code);
		sm.setName(name);
		return sm;
	}
	
	public static List<SelectModel> toSelectList() {
		List<SelectModel> models = new ArrayList<SelectModel>();
		models.add(NORMAL.toSlectModel());
		models.add(VIP.toSlectModel());
		models.add(LOVE.toSlectModel());
		models.add(DMAX.toSlectModel());
		models.add(LASER.toSlectModel());
		models.add(DBOX.toSlectModel());
		models.add(DOLBY.toSlectModel());
		return models;
	}
	
//	
//	public static List<SelectModel> toDeptList() {
//		List<SelectModel> models = new ArrayList<SelectModel>();
//		models.add(CLINIC.toSlectModel());
//		models.add(SURGICAL.toSlectModel());
//		models.add(INTERNAL.toSlectModel());
//		return models;
//	}
	public static void main(String[] arg) {
		int a =  "6";
		System.out.println(a);
	}
}
