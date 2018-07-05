package com.ejiahe.eim.lisence.manager.cipher;

/**
 *	<strong>处理验证，加密，解密： 基于对称算法 和非对称加密	<br></strong>
 *	<p>
 *	<strong> 对称加密</strong>
 *	<br>
 *	<tt>
 *			对称加密算法是应用较早的加密算法，技术成熟。在对称加密算法中，数据发信方将明文（原始数据）和加密密钥（mi yue）
 *		一起经过特殊加密算法处理后，使其变成复杂的加密密文发送出去。收信方收到密文后，若想解读原文，
 *		则需要使用加密用过的密钥及相同算法的逆算法对密文进行解密，才能使其恢复成可读明文。在对称加密算法中
 *		，使用的密钥只有一个，发收信双方都使用这个密钥对数据进行加密和解密，这就要求解密方事先必须知道加密密钥
 *	</tt>
 *	<p>
 *	  <strong>包括：</strong>基于“对称密钥”的加密算法主要有DES、3DES（TripleDES）、AES、RC2、RC4、RC5和Blowfish等
 * 	<p>
 * 	<p>
 * 	<strong>非对称加密:</strong>
 * 	<br>
 * 		<tt>
 * 			1976年，美国学者Dime和Henman为解决信息公开传送和密钥管理问题，
 * 		       提出一种新的密钥交换协议，允许在不安全的媒体上的通讯双方交换信息，安全地达成一致的密钥，这就是“公开密钥系统”。
 * 	 	       与对称加密算法不同，非对称加密算法需要两个密钥：公开密钥（publickey）和私有密钥（privatekey）。
 * 		      公开密钥与私有密钥是一对，如果用公开密钥对数据进行加密，只有用对应的私有密钥才能解密；
 * 		      如果用私有密钥对数据进行加密，那么只有用对应的公开密钥才能解密。因为加密和解密使用的是两个不同的密钥，所以这种算法叫作非对称加密算法。
 * 		</tt>
 *  <p>
 * 	<strong>包括：</strong>RSA、Elgamal、背包算法、Rabin、D-H、ECC（椭圆曲线加密算法）等。
 * 
 * @author focus
 * @date 2015年9月29日
 * @time 下午4:32:22
 */
public interface ICipher {

	
	/**
	 * 加密
	 * @param msg			   原始消息 
	 * @param privateKey	   秘钥
	 * @return 				   返回加密之后的字符串
	 * @author focus
	 * @date 2015年9月29日
	 * @time 下午4:20:56
	 */
	public String encrypt(String msg,String privateKey);
	
	/**
	 * 解密
	 * @param encode_encrypt_msg  加密之后的消息
	 * @param privateKey	                  秘钥
	 * @return 					      返回解密之后的字符串
	 * @author focus
	 * @date 2015年9月29日
	 * @time 下午4:21:08
	 */
	public String decrypt(String encode_encrypt_msg,String privateKey);
	
	/**
	 * 创建秘钥
	 * @return
	 * @author focus
	 * @date 2015年10月10日
	 * @time 上午11:26:05
	 */
	public String createPrivateKey();
	
	/**
	 * 算法
	 * @return
	 * @author focus
	 * @date 2015年10月13日
	 * @time 下午4:17:17
	 */
	public String getAlgorithm();
	
}
