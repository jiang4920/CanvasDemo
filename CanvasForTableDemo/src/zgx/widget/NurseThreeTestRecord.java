package zgx.widget;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import android.util.Log;

@SuppressWarnings("serial")
public class NurseThreeTestRecord extends ArrayList<ThreeTestItem>  {
	private static final String TAG = "CoordinatesTest NurseThreeTestRecord.java";
	
	public NurseThreeTestRecord domParse(String url){
		SAXReader reader = new SAXReader(); 
        try {
			Document document = reader.read(new URL(url));
			Element root = document.getRootElement().element("�����");//�������ʼ��ȡ����
			//�����������������
			for(Iterator i = root.elementIterator(); i.hasNext();){
				Element el = (Element)i.next();
				ThreeTestItem item = new ThreeTestItem();
				Log.v(TAG, item.����ʱ�� = el.elementText("����ʱ��"));
				Log.v(TAG, item.���� = el.elementText("����"));
				Log.v(TAG, item.���� = el.elementText("����"));
				Log.v(TAG, item.���� = el.elementText("����"));
				Log.v(TAG, item.���� = el.elementText("����"));
				Log.v(TAG, item.Ѫѹ��ѹ = el.elementText("Ѫѹ��ѹ"));
				Log.v(TAG, item.Ѫѹ��ѹ = el.elementText("Ѫѹ��ѹ"));
				Log.v(TAG, item.������ = el.elementText("������"));
				Log.v(TAG, item.С����� = el.elementText("С�����"));
				Log.v(TAG, item.���� = el.elementText("����"));
				Log.v(TAG, item.������ = el.elementText("������"));
				Log.v(TAG, item.Ż���� = el.elementText("Ż����"));
				Log.v(TAG, item.���ų��� = el.elementText("���ų���"));
				Log.v(TAG, item.������ = el.elementText("������"));
				Log.v(TAG, item.�������� = el.elementText("��������"));
				Log.v(TAG, item.���� = el.elementText("����"));
				this.add(item);
			}
		} catch (DocumentException e1) {
			e1.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return this;
	}
	
}
