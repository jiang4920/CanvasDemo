package zgx.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @ClassName : CoordinatesView
 * @Description : TODO
 * @author : ZGX zhangguoxiao_happy@163.com
 * @date : 2011-10-9 ����09:06:38
 * 
 */
public class Coordinates extends View {
	private static final String TAG = "Coordinates";
	/*
	 * ����
	 */
	private Paint mPaint;
	/*
	 * ���ݼ���
	 */
	private List<PointF[]> mPointsList;
	private List<Paint> mPaintList;
	private int mTitleHeight;
	private int mFontSize;
	
	/*
	 * �߾�
	 */
	private int mLeftPad, mRightPad, mBottomPad, mTopPad;
	
	/**
	 * ʱ����ÿ��ĸ߶�
	 */
	private int mTableItemHeight; 
	
	
	/**
	 * ����������ĸ߶�
	 */
	private int mTableTopDateHeight; 
	
	/**
     * �����·����ĸ߶�
     */
    private int mTableBottomDataHeight; 
	
	/**
	 * ����ȣ�Ĭ�Ͽ��Ϊ����ϵ���
	 */
	private int mTableWidth;
	
	/*
	 * ���������ܶȡ����Ⱥͱ�����
	 */
	private float mXValuePerPix, mYValuePerPix;
	private int mXLen, mYLen;
	private float mXScale, mYScale;

	/**
	 * �������������
	 */
	private int mXCount, mYCount;
	/**
	 * �ο�����������������
	 */
	private PointF mPointBase = new PointF();
	/**
	 * �ο������߼���������
	 */
	private PointF mPointBaseValue = new PointF();
	/*
	 * ������������ĵ�����ԭ�����������
	 */
	private PointF mPointOrigin = new PointF();

	/*
	 * �Զ���ؼ�һ��д�������췽�� CoordinatesView(Context context)����javaӲ���봴���ؼ�
	 * �����Ҫ���Լ��Ŀؼ��ܹ�ͨ��xml�������ͱ����е�2�����췽�� CoordinatesView(Context context,
	 * AttributeSet attrs) ��Ϊ��ܻ��Զ����þ���AttributeSet������������췽���������̳���View�Ŀؼ�
	 */
	public Coordinates(Context context) {
		super(context, null);
		init();
	}

	public Coordinates(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		// ��������
		mPaint = new Paint();
		mPaint.setColor(Color.BLACK);
		mPaint.setAntiAlias(true);//����ȥ�����
		// ���ñ߾�
		setCoordinatesPadding(0, 0, 0, 0);
		// ���ñ���
		setTableItemHeight(30);
		
		new PointF(mLeftPad, mTitleHeight);
		// �����ܶ�
		mXValuePerPix = 0.5f;
		mYValuePerPix = 0.5f;
		// �������ű���
		mXScale = 1;
		mYScale = 1;
	}

	
	/**
	 * ��������ϵ��@�����ȫ�ֵ������С
	 * @param fontSize
	 */
	public void setFontSize(int fontSize){
	    mFontSize = fontSize;
	}
	

	/**
	 * ���÷Ŵ���С����
	 */
	public void setScaleXY(float xScale, float yScale) {
		mXScale = xScale;
		mYScale = yScale;
	}

	/**
	 * ���ñ����ÿ�еĸ߶�
	 * @param height
	 */
	public void setTableItemHeight(int height){
		mTableItemHeight = height;
	}
	
	/**
	 * ��������ϵ�ı߾�
	 */
	public void setCoordinatesPadding(int leftPad, int rightPad, int topPad,
			int bottomPad) {
		mLeftPad = leftPad + 40;
		mRightPad = rightPad + 20;
		mTopPad = topPad + 10 ;
		mBottomPad = bottomPad + 40;
	}

	/**
	 * <p>Title: addPoints</p>
	 * <p>Description: ���һ������</p>
	 * @param points һ�������
	 * @param paint ���ƻ��ʣ�Ŀ���ǻ��ƶ����������߿���ͨ����ɫ��������
	 */
	public void addPoints(PointF[] points, Paint paint) {
		if (points == null)
			return;
		if (mPointsList == null)
			mPointsList = new ArrayList<PointF[]>();
		mPointsList.add(points);
		if (mPaintList == null)
			mPaintList = new ArrayList<Paint>();
		if (paint != null)
			mPaintList.add(paint);
		else {
			mPaintList.add(mPaint);
		}
	}


	/**
	 * �����ܶȣ�����xy������ʾ��������ȷ��xy������ܶ�
	 * 
	 * @param xCount
	 *            ����x������ĵ�ĸ���
	 * @param yCount
	 *            ����y������ĵ�ĸ���
	 */
	public void setPerpix(int xCount, int yCount) {
		Log.v(TAG, "setPerpix");
		mXCount = xCount;
		mYCount = yCount;

	}

	// private int centerX, centerY;
	/*
	 * �ؼ��������֮������ʾ֮ǰ������������������ʱ���Ի�ȡ�ؼ��Ĵ�С ���õ����������������Բ�����ڵĵ㡣
	 */
	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		// centerX = w / 2;
		// centerY = h / 2;
		Log.v(TAG, "onSizeChanged W:" + w + " H:" + h);
		mXLen = w - mLeftPad - mRightPad;
		mYLen = h - mBottomPad - mTopPad - mTableTopDateHeight - mTableBottomDataHeight - mTitleHeight;
		
		mTableWidth = mXLen;
		
		mXValuePerPix = ((float) mXLen) / (float) mXCount;
		mYValuePerPix = ((float) mYLen) / (float) mYCount;
		Log.v(TAG, "onSizeChanged mXValuePerPix:" + mXValuePerPix
				+ " mYValuePerPix:" + mYValuePerPix);
		mPointOrigin.set(mLeftPad, h - mBottomPad - mTableBottomDataHeight);
		mPointBase.set(mXLen / 2 + mPointOrigin.x, mPointOrigin.y - mYLen / 2);
		mPointBaseValue.set(mXLen / 2 * mXValuePerPix / mXScale, mYLen / 2
				* mYValuePerPix / mYScale);
		super.onSizeChanged(w, h, oldw, oldh);
	}

	/*
	 * �Զ���ؼ�һ�㶼������onDraw(Canvas canvas)�������������Լ���Ҫ��ͼ��
	 */
	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (canvas == null) {
			return;
		}
		/*
		 * ������ɫ
		 */
		canvas.drawColor(Color.WHITE);
		//������ϵ
		drawCoordinate(canvas);

		/*
		 * ������ �����ⲿ��Ҫ���������ϻ������ݣ�����������л���
		 */
		drawMultiLines(canvas, mPointsList, mPaintList);
		drawTable(canvas, mPaint);
	}
	
	/**
	 * ������ϵ����������ϵ�ı߿������
	 * @param canvas
	 */
	private void drawCoordinate(Canvas canvas){
				// ��ֱ��
//				canvas.drawLine(mPointOrigin.x, mPointOrigin.y, mPointOrigin.x + mXLen,
//						mPointOrigin.y, mPaint);// ��X��
				canvas.drawLine(mPointOrigin.x, mPointOrigin.y, mPointOrigin.x,
						mPointOrigin.y - mYLen, mPaint);// ��Y��
				canvas.drawLine(mPointOrigin.x + mXLen, mPointOrigin.y, mPointOrigin.x + mXLen,
						mPointOrigin.y - mYLen, mPaint);// ������ϵ���ұߺ���
				//������ϵ���������
				drawMultiDashLines(canvas, mXCount, mYCount);
	}
	
	/**
	 * ��������
	 * @param canvas
	 * @param xCount ����������
	 * @param yCount ����������
	 */
	private void drawMultiDashLines(Canvas canvas, int xCount, int yCount) {
		// ��������
		for (int i = 0; i < xCount; i++) {
			PointF pointFrom = new PointF();
			pointFrom.set(i+1, 0);
			PointF pointTo = new PointF();
			pointTo.set(i+1, yCount);
			drawDashLines(canvas, point2Physical(pointFrom),
					point2Physical(pointTo));
		}
		// ��������
		for (int i = 0; i < yCount; i++) {
			PointF pointFrom = new PointF();
			pointFrom.set(0, i+1);
			PointF pointTo = new PointF();
			pointTo.set(xCount, i+1);
			drawDashLines(canvas, point2Physical(pointFrom),
					point2Physical(pointTo));
		}
	}

	/**
	 * ��������
	 * 
	 * @param canvas
	 */
	public void drawDashLines(Canvas canvas, PointF pointFrom, PointF pointTo) {
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.GRAY);
		Path path = new Path();
		path.moveTo(pointFrom.x, pointFrom.y);
		path.lineTo(pointTo.x, pointTo.y);
		PathEffect effects = new DashPathEffect(new float[] { 1, 5, 1, 5 }, 1);
		paint.setPathEffect(effects);
		canvas.drawPath(path, paint);
	}

	/**
	 * ����С��
	 */
	private float Round(float f) {
		return (float) (Math.round(f * 10)) / 10;
	}

	/**
	 * ����
	 */
	private void drawPoint(Canvas canvas, PointF p, Paint paint) {
		canvas.drawCircle(p.x, p.y, 2, paint);
	}

	/**
	 * ����
	 */
	private void drawLine(Canvas canvas, PointF pa, PointF pb, Paint paint) {
		canvas.drawLine(pa.x, pa.y, pb.x, pb.y, paint);
	}

	/**
	 * �߼�����ת��Ϊ��Ļ���� ���߼�����logPointF��ת��Ϊ��������
	 */
	private PointF point2Physical(PointF logPointF) {
		PointF physicalPointF = new PointF();
		physicalPointF.set(logPointF.x * mXValuePerPix + mPointOrigin.x,
				mPointOrigin.y - logPointF.y * mYValuePerPix);

		Log.v(TAG, "mPointOrigin x:" + mPointBase.x + " mPointOrigin y:"
				+ mPointBase.y);
		Log.v(TAG, "logit x:" + logPointF.x + " logit y:" + logPointF.y
				+ " phy x:" + physicalPointF.x + " phy y:" + physicalPointF.y);
		return physicalPointF;
	}

	/**
	 * ��������ת��Ϊ�߼����� ����������phyPointF��ת��Ϊ�߼�����
	 */
	private PointF point2Logical(PointF phyPointF) {
		float x = (phyPointF.x - mPointBase.x) * mXValuePerPix / mXScale
				+ mPointBaseValue.x;
		float y = (mPointBase.y - phyPointF.y) * mYValuePerPix / mYScale
				+ mPointBaseValue.y;
		PointF logicalPointF = new PointF(x, y);
		return logicalPointF;
	}

	/**
	 * �������� ���ڻ�������ļ�ͷ
	 */
	private void drawTriangle(Canvas canvas, PointF p1, PointF p2, PointF p3) {
		Path path = new Path();
		path.moveTo(p1.x, p1.y);
		path.lineTo(p2.x, p2.y);
		path.lineTo(p3.x, p3.y);
		path.close();

		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.FILL);
		// ������������
		canvas.drawPath(path, paint);
	}

	/**
	 * ��������
	 */
	private void drawLines(Canvas canvas, PointF[] point, Paint paint) {
		int len = (point == null) ? 0 : point.length;
		if (len > 0) {
			PointF pa = point[0];
			PointF pb;
			drawPoint(canvas, point2Physical(pa), paint);
			for (int i = 1; i < len; i++) {
				pb = point[i];
				drawLine(canvas, point2Physical(pa), point2Physical(pb), paint);
				drawPoint(canvas, point2Physical(pb), paint);
				pa = pb;
			}
			pb = point[len - 1];
			drawLine(canvas, point2Physical(pa), point2Physical(pb), paint);
			drawPoint(canvas, point2Physical(pb), paint);
		}
	}

	/**
	 * ����������
	 */
	private void drawMultiLines(Canvas canvas, List<PointF[]> pointList,
			List<Paint> paintList) {
		int len = (pointList == null) ? 0 : pointList.size();
		for (int i = 0; i < len; i++) {
			drawLines(canvas, pointList.get(i), paintList.get(i));
		}
	}

	private String[][] mTopTable;
	private String[][] mBottomTable;
	
	
	/**
	 * <p>Title: drawTable</p>
	 * <p>Description: �������еı��,Ŀǰ�Ѿ�ʵ�ֻ��ƶ����ı��͵ײ��ı��</p>
	 * @param canvas
	 * @param paint
	 * @param isCenter 
	 */
	private void drawTable(Canvas canvas, Paint paint){
		if(mFontSize != 0){
		    paint.setTextSize(mFontSize);
		}
		PointF tp0 = new PointF(); 	//�������Ϊ������Ͻǵ�
		PointF tp1 = new PointF();		//�������Ϊ������½ǵ㣬�����������ȷ�������ο����״
		tp0.set(mLeftPad, mTopPad  +mTitleHeight);
		tp1.set(mLeftPad + mXLen, mTopPad+ mTableTopDateHeight +mTitleHeight);
		TableRect topTableRect = new TableRect(tp0, tp1);
		drawTable(canvas, mTopTable, topTableRect, paint, true);
		PointF bp0 = new PointF(); 	//�������Ϊ������Ͻǵ�
		PointF bp1 = new PointF();		//�������Ϊ������½ǵ㣬�����������ȷ�������ο����״
		bp0.set(mLeftPad, mTopPad +mTableTopDateHeight +mTitleHeight + mYLen);
		bp1.set(mLeftPad + mXLen, mTopPad+ mTableTopDateHeight +mTitleHeight+mYLen + mTableBottomDataHeight);
		TableRect bottomTableRect = new TableRect(bp0, bp1);
		drawTable(canvas, mBottomTable, bottomTableRect, paint, true);
	}
	
	/**
	 * <p>Title: drawTable</p>
	 * <p>Description: ���Ʊ�񣬸��ݶ�ά����ֱ��ת���ɱ��</p>
	 * @param canvas ��������
	 * @param table ��ά���飬ԭʼ����
	 * @param tableRect ���Ʊ��Ĳο��㣬���ľ��ο�����ϽǺ����½������㣬������ȷ���˱��ĳߴ��λ��
	 * @param paint ���ʣ��������û��Ƶ���ɫʲô��
	 * @param isCenter ���Ƶ������Ƿ������ʾ
	 */
	private void drawTable(Canvas canvas, String[][] table, TableRect tableRect, Paint paint, boolean isCenter){
	  //����Table�Ĳο���
        drawPoint(canvas, tableRect.p0, paint);
        drawPoint(canvas, tableRect.p1, paint);
        
        int horLinesCount = table.length;
        int verLinesCount = table[0].length;
        
        float tableItemHeight = mTableItemHeight;
        //��������
        for(int i = 0; i <= horLinesCount; i++){
            PointF from = new PointF(tableRect.p0.x, tableRect.p0.y+i*tableItemHeight);
            PointF to = new PointF(tableRect.p1.x, from.y);
            drawLine(canvas, from, to, paint);
        }
        
        float tableItemWidth = (float)mXLen/(float)verLinesCount;
        //���������
        for(int i = 0; i <= verLinesCount; i++){
            PointF from = new PointF(tableRect.p0.x + i*tableItemWidth , tableRect.p0.y);
            PointF to = new PointF(from.x, tableRect.p1.y);
            drawLine(canvas, from, to, paint);
        }
        if(isCenter){
            for(int i = 0; i<table.length; i ++){   //i��ʾ��i������
                for(int j = 0;j< table[i].length; j++ ){    //j��ʾ��i�еĵ�j������
                    String tmp = table[i][j];
                    float textWidth = getTextWidth(paint, tmp);
                    float centerPointXOffset = (tableItemWidth - textWidth)/2f;
                    float textHeight = paint.getTextSize();
                    
                    float centerPointYOffset = (tableItemHeight - textHeight)/2f +2;//�������2����Ϊ�ӽ�����ʾ�Ͽ���Ҫ�������ƶ�һ��
                    Log.v(TAG, "textHeight:"+textHeight + " tableItemHeight:" + tableItemHeight);
                    Log.v(TAG, "centerPointYOffset:"+centerPointYOffset);
                    canvas.drawText(table[i][j], tableRect.p0.x +j*tableItemWidth + centerPointXOffset, tableRect.p0.y -centerPointYOffset + (i+1)*tableItemHeight, paint);
                }
            }
        }
	}
	
	public void setTopTable(String[][] table) {
	    mTopTable = table;
		mTableTopDateHeight = mTableItemHeight*table.length;
	}
	
	public void setBottomTable(String[][] table) {
	    mBottomTable = table;
	    mTableBottomDataHeight = mTableItemHeight*table.length;
	}
	
	
	/**
	 * �������ֿ��
	 * @param paint
	 * @param str
	 * @return
	 */
	public static float getTextWidth(Paint paint, String str) {  
        float iRet = 0;  
        if (str != null && str.length() > 0) {  
            int len = str.length();  
            float[] widths = new float[len];  
            paint.getTextWidths(str, widths);  
            for (int j = 0; j < len; j++) {  
                iRet += (float) Math.ceil(widths[j]);  
            }  
        }  
        return iRet;  
    }
	
	/**
	 * ��ȡ����ߴ��Ӧ������߶�
	 * @param fontSize
	 * @return
	 */
	public float getFontHeight(Paint paint) 
	{ 
	   FontMetrics fm = paint.getFontMetrics(); 
	   return (float)Math.ceil(fm.descent - fm.ascent); 
	}
	
}
