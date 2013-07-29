package zgx.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * @ClassName : CoordinatesView
 * @Description : TODO
 * @author : ZGX zhangguoxiao_happy@163.com
 * @date : 2011-10-9 ����09:06:38
 * 
 */
public class CopyOfCoordinates extends View {
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
	/*
	 * ����
	 */
	private boolean mHasTitle;
	private String mTitle;
	private int mTitleHeight;
	private PointF mTitlePoint;
	/*
	 * �߾�
	 */
	private int mLeftPad, mRightPad, mBottomPad, mTopPad;
	/*
	 * ���������ܶȡ����Ⱥͱ�����
	 */
	private float mXValuePerPix, mYValuePerPix;
	private int mXLen, mYLen;
	private float mXScale, mYScale;
	
	/**
	 * �������������
	 */
	private float mXCount, mYCount;
	/*
	 * ���������ʶ�͵�λ
	 */
	private String mXAxisPrickle, mYAxisPrickle;
	private String mXAxisName = "X", mYAxisName = "Y";
	/*
	 * Բ�ģ�����ֵ�������ؼ������Ͻǵģ�
	 */
	// private PointF mPointZero = new PointF();
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
	public CopyOfCoordinates(Context context) {
		super(context, null);
		init();
	}

	public CopyOfCoordinates(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		// ��������
		mPaint = new Paint();
		mPaint.setColor(Color.BLACK);
		// ���ñ߾�
		setCoordinatesPadding(0, 0, 0, 0);
		// ���ñ���
		setTitleHeight(20);
		setTitleName("����");

		mHasTitle = true;
		mTitlePoint = new PointF(mLeftPad, mTitleHeight);
		// �����ܶ�
		mXValuePerPix = 0.5f;
		mYValuePerPix = 0.5f;
		// �������ű���
		mXScale = 1;
		mYScale = 1;
	}

	/**
	 * ���ñ���߶�
	 */
	public void setTitleHeight(int height) {
		mTitleHeight = height;
	}

	/**
	 * ����ͼ�����
	 */
	public void setTitleName(String titleName) {
		mTitle = titleName;
	}

	/**
	 * ���÷Ŵ���С����
	 */
	public void setScaleXY(float xScale, float yScale) {
		mXScale = xScale;
		mYScale = yScale;
	}

	/**
	 * ���ñ߾�
	 */
	public void setCoordinatesPadding(int leftPad, int rightPad, int topPad,
			int bottomPad) {
		mLeftPad = leftPad + 40;
		mRightPad = rightPad + 20;
		mTopPad = topPad + 10;
		mBottomPad = bottomPad + 40;
	}

	public void addTempreturePoints(){
		
	}
	
	/**
	 * ���һ������
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
	 * �����������ƺ͵�λ
	 */
	public void setAxisNamePrickleXY(String xName, String xPrickle,
			String yName, String yPrickle) {
		mXAxisName = xName;
		mXAxisPrickle = xPrickle;
		mYAxisName = yName;
		mYAxisPrickle = yPrickle;
	}

	/**
	 * �����ܶȣ�����xy������ʾ��������ȷ��xy������ܶ�
	 * @param xCount ����x������ĵ�ĸ��� 
	 * @param yCount ����y������ĵ�ĸ���
	 */
	public void setPerpix(int xCount, int yCount){
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
		Log.v(TAG, "onSizeChanged W:"+w + " H:"+h);
		mXLen = w - mLeftPad - mRightPad;
		mYLen = h - mBottomPad - mTopPad - (mHasTitle ? mTitleHeight : 0);
//		mXValuePerPix = (float)mXLen/(float)mXCount;
//		mYValuePerPix = (float)mYLen/(float)mYCount;
		Log.v(TAG, "onSizeChanged mXValuePerPix:"+mXValuePerPix + " mYValuePerPix:"+mYValuePerPix);
		mPointOrigin.set(mLeftPad, h - mBottomPad);
		mPointBase.set(mXLen / 2 + mPointOrigin.x, mPointOrigin.y - mYLen / 2);
		mPointBaseValue.set(mXLen / 2 * mXValuePerPix / mXScale, mYLen / 2
				* mYValuePerPix / mYScale);
		// mPointZero.set(mLeftPad, h - mBottomPad);
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
		/*
		 * ���ο���
		 */
		Log.i("base point", "(" + Round(mPointBaseValue.x) + ","
				+ Round(mPointBaseValue.y) + ")");
		drawPoint(canvas, point2Physical(mPointBaseValue), mPaint);
		canvas.drawText("(" + Round(mPointBaseValue.x) + ","
				+ Round(mPointBaseValue.y) + ")", mPointBase.x - 15,
				mPointBase.y + 18 + 12, mPaint);
		mPaint.setColor(Color.MAGENTA);
		drawPoint(canvas, point2Physical(mPointBase), mPaint);
		mPaint.setColor(Color.BLACK);
		/*
		 * ������
		 */
		canvas.drawText(mTitle, mTitlePoint.x, mTitlePoint.y, mPaint);
		/*
		 * ��������
		 */
		// ��ֱ��
		canvas.drawLine(mPointOrigin.x, mPointOrigin.y, mPointOrigin.x + mXLen,
				mPointOrigin.y, mPaint);// ��X��
		canvas.drawLine(mPointOrigin.x, mPointOrigin.y, mPointOrigin.x,
				mPointOrigin.y - mYLen, mPaint);// ��Y��
		// ��X���ͷ
		float x = mPointOrigin.x + mXLen, y = mPointOrigin.y;
		drawTriangle(canvas, new PointF(x, y), new PointF(x - 10, y - 5),
				new PointF(x - 10, y + 5));
		canvas.drawText(mXAxisName, x - 15, y + 18, mPaint);
		canvas.drawText("��" + mXAxisPrickle + "��", x - 15, y + 18 + 12, mPaint);
		// ��Y���ͷ
		x = mPointOrigin.x;
		y = mPointOrigin.y - mYLen;
		drawTriangle(canvas, new PointF(x, y), new PointF(x - 5, y + 10),
				new PointF(x + 5, y + 10));
		canvas.drawText(mYAxisName + " ��" + mYAxisPrickle + "��", x + 12,
				y + 15, mPaint);
		/*
		 * �����������
		 */
		// �ȼ��������ǰ����������ֵ
		PointF po = point2Logical(mPointOrigin);
		String centerString = "(" + Round(po.x) + "," + Round(po.y) + ")";
		// Ȼ����ʾ����
		canvas.drawText(centerString, mPointOrigin.x - 25, mPointOrigin.y + 15,
				mPaint);

		/*
		 * ������ �����ⲿ��Ҫ���������ϻ������ݣ�����������л���
		 */
		drawMultiLines(canvas, mPointsList, mPaintList);
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
	 * �߼�����ת��Ϊ��Ļ����
	 * ���߼�����logPointF��ת��Ϊ��������
	 */
	private PointF point2Physical(PointF logPointF) {
		PointF physicalPointF = new PointF();
		physicalPointF.set((logPointF.x - mPointBaseValue.x) * mXScale
				/ mXValuePerPix + mPointBase.x,
				-(logPointF.y - mPointBaseValue.y) * mYScale / mYValuePerPix
						+ mPointBase.y);
		
		Log.v(TAG, "mPointBase x:"+mPointBase.x + " mPointBase y:"+ mPointBase.y);
		Log.v(TAG, "logit x:"+logPointF.x + " logit y:"+ logPointF.y + " phy x:"+physicalPointF.x+ " phy y:"+physicalPointF.y);
		return physicalPointF;
	}

	/**
	 * ��������ת��Ϊ�߼�����
	 * ����������phyPointF��ת��Ϊ�߼�����
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

	/*
	 * ���ڱ����϶�ʱ����һ�����λ��
	 */
}
