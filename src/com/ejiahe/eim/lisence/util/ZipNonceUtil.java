package com.ejiahe.eim.lisence.util;

/**
 * 思路： 
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
 * @author focus
 * @date 2015年10月30日
 * @time 下午4:01:34
 */
public class ZipNonceUtil {

	/**
	 * 压缩字符串： 仅仅是指小写的 字母， 和 整形的数字字符串的压缩
	 *
	 * @author focus
	 * @date 2015年10月30日
	 * @time 下午3:57:19
	 */
	public String compress(String str){
		
		return null;
	}
	
	/**
	 * 解压缩
	 * @author focus
	 * @date 2015年10月30日
	 * @time 下午3:58:20
	 */
	public String unCompress(){
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		 System.out.println((int)'A');
		System.out.println(LicenseUtil.encryptBASE64("adf".getBytes()));
		System.out.println(Integer.toBinaryString(127));
		System.out.println(Integer.toBinaryString(128));
		System.out.println(Integer.toBinaryString(32));
		System.out.println(Byte.valueOf("1111111",2));
		System.out.println((int)',');
	}
}
