> ������ϵͳ
>
>  html+ajax+java+mysqlʵ��
> ������ajaxʵ�����ݴ������ȡ
> ��Ը��˻���֯�û�
> ��¼�˺�:xtmxzzb����:xtmxzzb
>
> ���ݿ�������ļ���WEB-INF��db.properties(�ֶκ������¼)

[![img](https://github.com/Dreamlu/askforleave/raw/master/askForLeave/p1.png)](https://github.com/Dreamlu/askforleave/blob/master/askForLeave/p1.png)

�ص㣺

***1.���ŷ���(��ٻ�������ɺ��Ͷ���)***

***2.���Żظ�(���͸���ϵͳ)�������***

***3.���ģ�����߼���ʵ��(��Ա��Ϣ��¼�롢��ɾ�Ĳ顢��ˡ���ʷ�ȵ�)***

***4.��Ϣexcel����¼�룬��ѯ�������excel***

***5.��ѡ��������(��ͬ�쵼�����)��ɾ(��Ա����)��������˵�***

***6.����¼�����Ϣ����������(��������)��չʾ�����������ٵ����������ж�(����û�кܺõĳ�ȡ��������ͬ������ٹ��������ͬ)����ٽ����ӡ��pdf***

***7.��ҳ�ȵ� ~�Լ������´����õģ���Ҫ����(ʡ��д����Ŀ��ɾ���˲�����)***
***8.����һ��С����ÿ�ε�½ʱ��ʾ"����Ӧ���ٵ�δ���ٵ���Ա��Ϣ"���綼�Ѿ����٣�����ʾ***

ע�⣺

1.���ж��ŷ����õİ�����

2.�Լ�����Ҫ�滻����Ӧ��������AK��Ϣ(�������ӿ�)��������ŷ�����com.leave.service����leavePasswdService.java�� ��ʱ������̶��ŷ��ͺͼ������̶��Ž���(���ж���)�ֱ���com.leave.util���е�TimerMessage.java��ReceiveDemo.java��

3.���ŷ����л�Ҫ�滻��Ӧ����ǩ����ģ�壬����鿴�����ƶ��ŷ�������ĵ�

4.�������ݿ��ļ�(navicat������leave.sql�ļ�������ʧ�ܿ�������ֱ������Դ��)��leave_setup��ǰ��������,����ͨ������ɾ����������

�޸���Ŀ��ע�����(���¼���¼)
~~1.config����LoginFilter.java��response.sendRedirect("/askForLeave/login.html");��Ŀ���޸�~~

 ~~2.util����DBToExcel.java��rout.write("/askForLeave/upload/"+name);//����һ��·���ṩ��������,��Ŀ�����޸�~~ 

~~3.util����Find_History_DBToExcel.java��rout.write("/askForLeave/upload/"+name);//����һ��·���ṩ��������,��Ŀ�����޸�~~

��¼��

db.properties

> `db.drivername=com.mysql.jdbc.Driver`
>
> `db.url=jdbc:mysql://127.0.0.1:3306/leave?userUnicode=true&characterEncoding=utf-8`
>
> `db.username=root`//���ݿ��˺�
>
> `db.password=`//����
>
> `db.pageListNum=6`//��ҳ��ÿҳ��¼��������
>
> `db.name1=xtmxzzb`//��Ŀ��¼��
>
> `db.progectName=askForLeave`//��Ŀ�����޸���Ŀ��ʱ�滻
>
> `db.accessKeyId=`//AK��Ϣ�����ŷ����õ�
>
> `db.accessKeySecret=`
>
> `db.queueName=Alicom-Queue-xxxxxxxx-SmsUp`//����������Ϣ���գ�xxxxx�滻���Լ�����Ϣ���������շ���ϵͳ����Ϣ
>
> `db.signName=`//����ǩ��