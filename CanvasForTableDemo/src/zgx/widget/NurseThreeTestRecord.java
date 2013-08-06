package zgx.widget;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.DOMReader;
import org.dom4j.io.SAXReader;

import android.util.Log;

public class NurseThreeTestRecord extends ArrayList<ThreeTestItem>  {
	private static final String TAG = "CoordinatesTest NurseThreeTestRecord.java";
	
	public NurseThreeTestRecord domParse(InputStream is){
		SAXReader reader = new SAXReader(); 
        try {
			Document document = reader.read(is);
			Element element = document.getRootElement();
			
		} catch (DocumentException e1) {
			e1.printStackTrace();
		} 
		return this;
	}
	
}
