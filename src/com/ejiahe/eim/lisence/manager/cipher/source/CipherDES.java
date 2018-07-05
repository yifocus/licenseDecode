package com.ejiahe.eim.lisence.manager.cipher.source;

import com.ejiahe.eim.lisence.manager.cipher.AbstractSymmetricCipher;

/**
 * 
 * <strong>DES</strong> 算法<br><p>
 * 	<h3>1  概述</h3>
 *		<tt>DES算法全称为Data Encryption Standard，即数据加密算法，
 *			它是IBM公司于1975年研究成功并公开发表的。DES算法的入口参数有三个：
 *			Key、Data、Mode。其中Key为8个字节共64位，是DES算法的工作密钥；
 *			Data也为8个字节64位，是要被加密或被解密的数据；Mode为DES的工作方式,有两种：加密或解密。
 *		</tt>
 *	<h2>2 算法原理</h2>
 *		DES算法把64位的明文输入块变为64位的密文输出块，它所使用的密钥也是64位，其算法主要分为两步：
 *	<h3>（1）初始置换</h3>
 *		<tt>其功能是把输入的64位数据块按位重新组合,并把输出分为L0、R0两部分，每部分各长32位，
 *			其置换规则为将输入的第58位换到第一位，第50位换到第2位……依此类推,最后一位是原来的第7位。
 *			L0、R0则是换位输出后的两部分，L0是输出的左32位，R0是右32位，例：设置换前的输入值为D1D2D3……D64，
 *			则经过初始置换后的结果为:L0=D58D50……D8；R0=D57D49……D7。
 *		</tt>
 *	
 *	<h3>（2）逆置换</h3>
 *		<tt>经过16次迭代运算后，得到L16、R16,将此作为输入，进行逆置换，逆置换正好是初始置换的逆运算，由此即得到密文输出。</tt>
 * @author focus
 * @date 2015年10月13日
 * @time 下午5:02:13
 */
public class CipherDES extends AbstractSymmetricCipher {

	@Override
	public String getAlgorithm() {
		return "DES";
	}

	public static void main(String[] args) {
		CipherDES cipher = new CipherDES();
		String key = cipher.createPrivateKey();
		String src = "focusdfadsfaewfasdfewfasdfraewfdasgqg";
		String encrypt = cipher.encrypt(src, key);
		System.out.println(encrypt);
		String decrypt = cipher.decrypt(encrypt, key);
		System.out.println(decrypt);
		
				
	}

	@Override
	public String getAlgorithmCipher() {
		return "DES";
		//return "DES/CBC/PKCS5Padding";
	}
}
