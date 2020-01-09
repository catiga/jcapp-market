package com.jeancoder.market

ClassLoader classLoader = this.getClass().getClassLoader();
URL data_uri = classLoader.getResource('youchu_mobiles_11');

FileReader fr = new FileReader(data_uri.getFile());
BufferedReader bf = new BufferedReader(fr);

/**
INSERT INTO `ma_market_limit` (`market_id`, `rule_id`, `mobile`, `buy_num`, `a_time`, `c_time`, `flag`) VALUES (5, NULL, '18601902957', 1, NULL, '2020-01-06 18:40:00', 0);
 */

def result = [];
String str;
// 按行读取字符串
def index = 1;
FileOutputStream out=new FileOutputStream("/Users/jackielee/Desktop/example.txt");
while ((str = bf.readLine()) != null) {
	def arr_str = str.split('	');
	def mobile = arr_str[0];
	def buy_num = arr_str[arr_str.length - 1];
	if(buy_num=='0') {
		continue;
	}
	index++;
	def sql = """INSERT INTO `ma_market_limit` (`market_id`, `rule_id`, `mobile`, `buy_num`, `a_time`, `c_time`, `flag`) VALUES (6, NULL, '${mobile}', ${buy_num}, NULL, '2020-01-08 18:40:00', 0);"""
	//println sql;
	sql += '\n';
	out.write(sql.getBytes());
}
println index;
out.close();
bf.close();
fr.close();
return result;