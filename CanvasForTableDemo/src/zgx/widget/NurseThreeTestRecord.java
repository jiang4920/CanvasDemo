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
			Element root = document.getRootElement().element("三测表");//从三测表开始获取数据
			//遍历所有三测表数据
			for(Iterator i = root.elementIterator(); i.hasNext();){
				Element el = (Element)i.next();
				ThreeTestItem item = new ThreeTestItem();
				Log.v(TAG, item.测量时间 = el.elementText("测量时间"));
				Log.v(TAG, item.体温 = el.elementText("体温"));
				Log.v(TAG, item.脉搏 = el.elementText("脉搏"));
				Log.v(TAG, item.呼吸 = el.elementText("呼吸"));
				Log.v(TAG, item.体重 = el.elementText("体重"));
				Log.v(TAG, item.血压高压 = el.elementText("血压高压"));
				Log.v(TAG, item.血压低压 = el.elementText("血压低压"));
				Log.v(TAG, item.大便次数 = el.elementText("大便次数"));
				Log.v(TAG, item.小便次数 = el.elementText("小便次数"));
				Log.v(TAG, item.尿量 = el.elementText("尿量"));
				Log.v(TAG, item.引流量 = el.elementText("引流量"));
				Log.v(TAG, item.呕吐量 = el.elementText("呕吐量"));
				Log.v(TAG, item.总排出量 = el.elementText("总排出量"));
				Log.v(TAG, item.总入量 = el.elementText("总入量"));
				Log.v(TAG, item.术后天数 = el.elementText("术后天数"));
				Log.v(TAG, item.其它 = el.elementText("其它"));
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
