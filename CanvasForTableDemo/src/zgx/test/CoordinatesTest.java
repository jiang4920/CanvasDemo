package zgx.test;

import zgx.widget.Coordinates;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.widget.ZoomControls;

/**
 * @ClassName : TestCoordinates
 * @Description : TODO
 * @author : ZGX zhangguoxiao_happy@163.com
 * @date : 2011-10-9 下午02:38:02
 * 
 */
public class CoordinatesTest extends Activity {
	private int mXScale=2, mYScale=2;
	private Coordinates mCoordinates;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coordinates);

		
		mCoordinates = (Coordinates) findViewById(R.id.coordinates);
		Paint paint1=new Paint();
		paint1.setColor(Color.RED);
		mCoordinates.addPoints(getTest1Points(),paint1);
		mCoordinates.addTable(get());
		Paint paint2=new Paint();
		paint2.setColor(Color.BLUE);
		mCoordinates.addPoints(getTest2Points(),paint2);
		mCoordinates.setScaleXY(mXScale, mYScale);
		mCoordinates.setPerpix(42, 15);
		// 设置标题
		mCoordinates.setTitleHeight(20);
		mCoordinates.setTitleName("身高记录表");
		// 设置横轴纵轴单位
		mCoordinates.setAxisNamePrickleXY("年龄", "天", "身高", "厘米");
		// 设置边距
		mCoordinates.setCoordinatesPadding(0,0,0,0);

	}
	
	private String [][] get(){
		String [][] date = {
				{"a","b","c","d","e"},
				{"a","b","c","d","e"},
				{"a","b","c","d","e"}};
		return date;
	}
	
	private PointF[] getTest1Points(){
		PointF[] points = new PointF[15];
		for(int i = 0; i< points.length; i++){
			points[i] = new PointF((42f/15f)*(float)i, i);
		}
//		points[0] = new PointF(0, 0);
//		points[1] = new PointF(5, 5);
//		points[2] = new PointF(15, 10);
//		points[3] = new PointF(20, 20);
//		points[4] = new PointF(60, 40);
//		points[5] = new PointF(60, 40);
//		points[6] = new PointF(60, 40);
//		points[7] = new PointF(60, 40);
//		points[8] = new PointF(50, 40);
		return points;
	}
	
	private PointF[] getTest2Points(){
		PointF[] points = new PointF[4];
		points[0] = new PointF(0, 40);
		points[1] = new PointF(20, 5);
		points[2] = new PointF(30, 30);
		points[3] = new PointF(50, 20);
		return points;
	}
	
}
