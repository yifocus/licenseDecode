package com.ejiahe.eim.lisence.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <strong>使用前提条件： 压缩的字符串为：  数字和小写字母组成</strong><p>
 * 思路： <br>
 *  <li> 1 计算出字符串中 字符最小 Amin 和字符最大Amax 的ASCII码
 *	<li> 2   通过当前的 Amin 和 Amax 取中间值  Aavg   
 *  <li> 3 分别判断 Amin   Aavg   Amax 的范围 ：  2 4  8 16 32 64 128  
 *	<li> 4   如果字符小于Aavg 的则使用临近 Aavg 的作为位数补零， 
 *		         如： Aavg = 18 则 16 < Aavg < 32  则如果ASCII码小于 18 的使用  32 化作二进制 进行计算不够补零
 *	<li> 5  如果字符大于Aavg 的则使用 最临近 Amax 的化作二进制进行计算补零 比如： Amax = 30  则以32 作为补零计算
 *  <li> 6 如果字符大于 为 33 34 35 则直接取出来另做计算做为特殊计算： 字典为： 33：-，34：+，35：= 进行补位
 *  <li> 7 把字符串特殊字符取出记录坐标，再对其他字符进行压缩之后，通过 化作6位二进制进行转换成为 base64 压缩完成
 *
 *  压缩之后的字符串为： Aavg,最大值得位数二进制,特殊字符位置a分隔,特殊字符位置a分隔 ,特殊字符位置a分隔 ,压缩字符串
 *  
 * @author focus
 * @date 2015年10月30日
 * @time 下午4:01:34
 */
@SuppressWarnings("unused")
public class NonceCompress {

	// 特殊字符的map  key: 特殊字符， value:坐标的集合
	Map<String,List<Integer>> specialStrIndex = new HashMap<String, List<Integer>>();
	private int Amax = 0;
	private int Amin = 'z'; // 默认值 为z
	private int Aavg = 0;
	public String compress(String str){
		
		// 1 取特殊字符
		String exceptSpecialStr = filterSpeicalStrIndex(str);
		// 2 压缩除了特殊字符之外的字符串
		String compressStr = compressExceptSpecialStr(exceptSpecialStr);
		// 3 拼装压缩之后的字符串
		String result = genCompressStr(compressStr);
		
		return null;
	}
	
	/**
	 *  返回取出特殊字符之后的值
	 *
	 * @author focus
	 * @date 2015年10月30日
	 * @time 下午4:58:18
	 */
	private String filterSpeicalStrIndex(String str){
		
		// 1  取得特殊字符的坐标放到  specialStrIndex
		// 2 特殊字符过滤
		
		// w  的坐标位置
		List<Integer> wIndex = new ArrayList<Integer>();
		// x  的坐标位置
		List<Integer> xIndex = new ArrayList<Integer>();
		// y 的坐标位置
		List<Integer> yIndex = new ArrayList<Integer>();
		// z 的坐标位置
		List<Integer> zIndex = new ArrayList<Integer>();
		
		for(int i = 0,len = str.length(); i < len; i ++){
			
			char c = str.charAt(i);
			
			if(c == 'x'){
				xIndex.add(i);
			}else if(c == 'y'){
				yIndex.add(i);
			}else if(c == 'z'){
				zIndex.add(i);
			}else if(c == 'w'){
				wIndex.add(i);
			}
			
			// 计算字符串中的最大字符的ASCII 值
			if(c >= Amax){
				Amax = i;
			}
			
			// 计算字符中的最小字符的ASCII值
			if(c <= Amin){
				Amin = c;
			}
			
		}
		
		Aavg = (Amax + Amin) / 2;
		// 字典为： 32 : w 33：x，34：y，35：z 进行补位
		specialStrIndex.put("w", wIndex);
		specialStrIndex.put("x", xIndex);
		specialStrIndex.put("y", yIndex);
		specialStrIndex.put("z", zIndex);
		
		return str.replaceAll("[w-z]+", ""); 
	}
	
	/**
	 * 压缩除了特殊字符串之外的字符串
	 *
	 * @author focus
	 * @date 2015年10月30日
	 * @time 下午5:03:01
	 */
	private String compressExceptSpecialStr(String exceptSpecialStr){

		List<Integer> intList = new ArrayList<Integer>();
		// 转换成的数值为 0 - 35
		for(int i = 0,len = exceptSpecialStr.length(); i < len; i ++){
			
			char c = exceptSpecialStr.charAt(i);
			if('0' < c && '9' > c){
				// '0' < c < '9'
				// 从  0  开始
				intList.add((c - '0'));
			}else if('a' < c && 'z' > c){
				// 'a' < c < 'z'
				// 从 10 开始
				intList.add((c - 'a') + 10);
			}
		}
		return null;
	}
	
	/**
	 * 压缩数值： 其中intList 中的数值全部都小于 32
	 * @author focus
	 * @date 2015年10月30日
	 * @time 下午5:43:20
	 */
	private String comparessNumber(List<Integer> intList){
		
		return null;
	}
	
	// 计算Amax 在二进制中的区间来判断当前数值划分为几位的二进制
	private int getBinaryNum(){
		
		for(int i = 0; i < 100; i ++){
			int start = 1 << i;
			int end = 1 << (i + 1);
			if(start < Amax && end >= Amax){
				return Integer.toBinaryString(end).length();
			}
		}
		return 0;
	}
	
	/**
	 * 拼装压缩字符串
	 * @author focus
	 * @date 2015年10月30日
	 * @time 下午5:04:31
	 */
	private String genCompressStr(String compressStr){
		
		// 压缩之后的字符串为： Aavg,最大值得位数二进制,特殊字符位置a分隔,特殊字符位置a分隔 ,特殊字符位置a分隔 ,压缩字符串
		
		return null;
	}
	public static void main(String[] args) {
		int a = 4;
		System.out.println(1 << a);
		
		
		NonceCompress c = new NonceCompress();
		c.Amax = 3;
		System.out.println(c.getBinaryNum());
	}
	
	
}
