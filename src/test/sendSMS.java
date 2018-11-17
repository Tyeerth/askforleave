package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/*
 *
 *
 * 
 * 
 * username  �û���
 * password_md5   ����
 * mobile  �ֻ���
 * apikey  apikey��Կ
 * content  ��������
 * startTime  UNIXʱ�������дΪ���̷��ͣ�http://tool.chinaz.com/Tools/unixtime.aspx ��UNIXʱ�����վ��
 * 
 * success:msgid  �ύ�ɹ���
error:msgid  �ύʧ��  
error:Missing username  �û���Ϊ��
error:Missing password  ����Ϊ��
error:Missing apikey  APIKEYΪ��
error:Missing recipient  �ֻ�����Ϊ��
error:Missing message content  ��������Ϊ��
error:Account is blocked  �ʺű�����
error:Unrecognized encoding  ����δ��ʶ��
error:APIKEY or password_md5 error  APIKEY���������
error:Unauthorized IP address  δ��Ȩ IP ��ַ
error:Account balance is insufficient  ����
 * */
 
 /**
 * �������⴦��
 * 1��GBK�����ύ��
    ����urlencode�������ݣ�content����Ȼ����API����ʱ������encode=gbk

    2��UTF-8�����

    ��content ��urlencode����󣬴���encode=utf8��utf-8
    http://m.5c.com.cn/api/send/index.php?username=XXX&password_md5=XXX&apikey=XXX&mobile=XXX&content=%E4%BD%A0%E5%A5%BD%E6%89%8D%E6%94%B6%E7%9B%8A%E9%9F%A6&encode=utf8

    ʾ��
 * 
 */

public class sendSMS {
	public static void main(String[] args) {
		
		//���ӳ�ʱ����ȡ��ʱ����
		System.setProperty("sun.net.client.defaultConnectTimeout", "30000"); //���ӳ�ʱ��30��
		System.setProperty("sun.net.client.defaultReadTimeout", "30000");	//��ȡ��ʱ��30��

		//�½�һ��StringBuffer����
		StringBuffer buffer = new StringBuffer();
		
		
		//String encode = "GBK"; //ҳ�����Ͷ������ݱ���ΪGBK����Ҫ˵�������ύ���ź��յ����룬�뽫GBK��ΪUTF-8���ԡ��籾����ҳ��Ϊ�����ʽΪ��ASCII/GB2312/GBK��ô�ΪGBK���籾ҳ�����ΪUTF-8����Ҫ֧�ַ��壬�������ĵ�Unicode���뽫�˴�дΪ��UTF-8

		String encode = "UTF-8";
		
		String username = "";  //�û���
		
		String password_md5 = "";  //����
		
		String mobile = "15869190407";  //�ֻ���,ֻ��һ�����룺13800000001����������룺13800000001,13800000002,...N ��ʹ�ð�Ƕ��ŷָ���
		
		String apikey = "";  //apikey��Կ�����¼ http://m.5c.com.cn ����ƽ̨-->�˺Ź���-->�ҵ���Ϣ �и���apikey��
		
		String content = "���ã�������֤���ǣ�12345��������";  //Ҫ���͵Ķ������ݣ��ر�ע�⣺ǩ���������ã���ҳ��֤��Ӧ����Ҫ����ӡ�ͼ��ʶ���롿��
		

		
		try {
			
			
			String contentUrlEncode = URLEncoder.encode(content,encode);  //�Զ���������Urlencode���������ע�⣺��
			
			//�ѷ������Ӵ���buffer�У������ӳ�ʱ������������������֧�������������뽫���������еģ���m.5c.com.cn���޸�ΪIP����115.28.23.78��
			buffer.append("http://m.5c.com.cn/api/send/index.php?username="+username+"&password_md5="+password_md5+"&mobile="+mobile+"&apikey="+apikey+"&content="+contentUrlEncode+"&encode="+encode);
			
			//System.out.println(buffer); //���Թ��ܣ���������������URL��ַ
			
			//��buffer���Ӵ����½���URL��
			URL url = new URL(buffer.toString());

			//��URL����
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			
			//ʹ��POST��ʽ����
			connection.setRequestMethod("POST");

			//ʹ�ó����ӷ�ʽ
			connection.setRequestProperty("Connection", "Keep-Alive");
			
			//���Ͷ�������
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			
			//��ȡ����ֵ
			String result = reader.readLine();
			
			//���result���ݣ��鿴����ֵ���ɹ�Ϊsuccess������Ϊerror��������ĵ���ʼע��
			System.out.println(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}