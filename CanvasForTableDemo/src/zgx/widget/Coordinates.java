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
 * @date : 2011-10-9 上午09:06:38
 * 
 */
public class Coordinates extends View {
	private static final String TAG = "Coordinates";
	/*
	 * 颜料
	 */
	private Paint mPaint;
	/*
	 * 数据集合
	 */
	private List<PointF[]> mPointsList;
	private List<Paint> mPaintList;
	private int mTitleHeight;
	private int mFontSize;
	
	/*
	 * 边距
	 */
	private int mLeftPad, mRightPad, mBottomPad, mTopPad;
	
	/**
	 * 时间表格每项的高度
	 */
	private int mTableItemHeight; 
	
	
	/**
	 * 坐标上面表格的高度
	 */
	private int mTableTopDateHeight; 
	
	/**
     * 坐标下方表格的高度
     */
    private int mTableBottomDataHeight; 
	
	/**
	 * 表格宽度，默认宽度为坐标系相等
	 */
	private int mTableWidth;
	
	/*
	 * 横轴纵轴密度、长度和比例。
	 */
	private float mXValuePerPix, mYValuePerPix;
	private int mXLen, mYLen;
	private float mXScale, mYScale;

	/**
	 * 横轴纵轴点数量
	 */
	private int mXCount, mYCount;
	/**
	 * 参考坐标物理中心坐标
	 */
	private PointF mPointBase = new PointF();
	/**
	 * 参考坐标逻辑中心坐标
	 */
	private PointF mPointBaseValue = new PointF();
	/*
	 * 交叉点坐标中心点坐标原点物理坐标点
	 */
	private PointF mPointOrigin = new PointF();

	/*
	 * 自定义控件一般写两个构造方法 CoordinatesView(Context context)用于java硬编码创建控件
	 * 如果想要让自己的控件能够通过xml来产生就必须有第2个构造方法 CoordinatesView(Context context,
	 * AttributeSet attrs) 因为框架会自动调用具有AttributeSet参数的这个构造方法来创建继承自View的控件
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
		// 设置颜料
		mPaint = new Paint();
		mPaint.setColor(Color.BLACK);
		mPaint.setAntiAlias(true);//画笔去掉锯齿
		// 设置边距
		setCoordinatesPadding(0, 0, 0, 0);
		// 设置标题
		setTableItemHeight(30);
		
		new PointF(mLeftPad, mTitleHeight);
		// 设置密度
		mXValuePerPix = 0.5f;
		mYValuePerPix = 0.5f;
		// 设置缩放比例
		mXScale = 1;
		mYScale = 1;
	}

	
	/**
	 * 设置坐标系和@表格中全局的字体大小
	 * @param fontSize
	 */
	public void setFontSize(int fontSize){
	    mFontSize = fontSize;
	}
	

	/**
	 * 设置放大缩小倍数
	 */
	public void setScaleXY(float xScale, float yScale) {
		mXScale = xScale;
		mYScale = yScale;
	}

	/**
	 * 设置表格中每行的高度
	 * @param height
	 */
	public void setTableItemHeight(int height){
		mTableItemHeight = height;
	}
	
	/**
	 * 设置坐标系的边距
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
	 * <p>Description: 添加一条曲线</p>
	 * @param points 一组坐标点
	 * @param paint 定制画笔，目的是绘制多条坐标连线可以通过颜色进行区分
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
	 * 设置密度，根据xy坐标显示的数量来确定xy坐标的密度
	 * 
	 * @param xCount
	 *            设置x坐标轴的点的个数
	 * @param yCount
	 *            设置y坐标轴的点的个数
	 */
	public void setPerpix(int xCount, int yCount) {
		Log.v(TAG, "setPerpix");
		mXCount = xCount;
		mYCount = yCount;

	}

	// private int centerX, centerY;
	/*
	 * 控件创建完成之后，在显示之前都会调用这个方法，此时可以获取控件的大小 并得到中心坐标和坐标轴圆心所在的点。
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
	 * 自定义控件一般都会重载onDraw(Canvas canvas)方法，来绘制自己想要的图形
	 */
	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (canvas == null) {
			return;
		}
		/*
		 * 画背景色
		 */
		canvas.drawColor(Color.WHITE);
		//画坐标系
		drawCoordinate(canvas);

		/*
		 * 画数据 所有外部需要在坐标轴上画的数据，都在这里进行绘制
		 */
		drawMultiLines(canvas, mPointsList, mPaintList);
		drawTable(canvas, mPaint);
	}
	
	/**
	 * 画坐标系，包括坐标系的边框和虚线
	 * @param canvas
	 */
	private void drawCoordinate(Canvas canvas){
				// 画直线
//				canvas.drawLine(mPointOrigin.x, mPointOrigin.y, mPointOrigin.x + mXLen,
//						mPointOrigin.y, mPaint);// 画X轴
				canvas.drawLine(mPointOrigin.x, mPointOrigin.y, mPointOrigin.x,
						mPointOrigin.y - mYLen, mPaint);// 画Y轴
				canvas.drawLine(mPointOrigin.x + mXLen, mPointOrigin.y, mPointOrigin.x + mXLen,
						mPointOrigin.y - mYLen, mPaint);// 画坐标系的右边黑线
				//画坐标系上面的虚线
				drawMultiDashLines(canvas, mXCount, mYCount);
	}
	
	/**
	 * 绘制虚线
	 * @param canvas
	 * @param xCount 竖虚线数量
	 * @param yCount 横虚线数量
	 */
	private void drawMultiDashLines(Canvas canvas, int xCount, int yCount) {
		// 画竖虚线
		for (int i = 0; i < xCount; i++) {
			PointF pointFrom = new PointF();
			pointFrom.set(i+1, 0);
			PointF pointTo = new PointF();
			pointTo.set(i+1, yCount);
			drawDashLines(canvas, point2Physical(pointFrom),
					point2Physical(pointTo));
		}
		// 画横虚线
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
	 * 绘制虚线
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
	 * 保留小数
	 */
	private float Round(float f) {
		return (float) (Math.round(f * 10)) / 10;
	}

	/**
	 * 画点
	 */
	private void drawPoint(Canvas canvas, PointF p, Paint paint) {
		canvas.drawCircle(p.x, p.y, 2, paint);
	}

	/**
	 * 画线
	 */
	private void drawLine(Canvas canvas, PointF pa, PointF pb, Paint paint) {
		canvas.drawLine(pa.x, pa.y, pb.x, pb.y, paint);
	}

	/**
	 * 逻辑坐标转化为屏幕坐标 将逻辑坐标logPointF点转换为物理坐标
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
	 * 物理坐标转化为逻辑坐标 将物理坐标phyPointF点转换为逻辑坐标
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
	 * 画三角形 用于画坐标轴的箭头
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
		// 绘制这个多边形
		canvas.drawPath(path, paint);
	}

	/**
	 * 数据连线
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
	 * 画多条曲线
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
	 * <p>Description: 绘制所有的表格,目前已经实现绘制顶部的表格和底部的表格</p>
	 * @param canvas
	 * @param paint
	 * @param isCenter 
	 */
	private void drawTable(Canvas canvas, Paint paint){
		if(mFontSize != 0){
		    paint.setTextSize(mFontSize);
		}
		PointF tp0 = new PointF(); 	//此坐标点为表格左上角点
		PointF tp1 = new PointF();		//此坐标点为表格右下角点，这两个点可以确定表格矩形框的形状
		tp0.set(mLeftPad, mTopPad  +mTitleHeight);
		tp1.set(mLeftPad + mXLen, mTopPad+ mTableTopDateHeight +mTitleHeight);
		TableRect topTableRect = new TableRect(tp0, tp1);
		drawTable(canvas, mTopTable, topTableRect, paint, true);
		PointF bp0 = new PointF(); 	//此坐标点为表格左上角点
		PointF bp1 = new PointF();		//此坐标点为表格右下角点，这两个点可以确定表格矩形框的形状
		bp0.set(mLeftPad, mTopPad +mTableTopDateHeight +mTitleHeight + mYLen);
		bp1.set(mLeftPad + mXLen, mTopPad+ mTableTopDateHeight +mTitleHeight+mYLen + mTableBottomDataHeight);
		TableRect bottomTableRect = new TableRect(bp0, bp1);
		drawTable(canvas, mBottomTable, bottomTableRect, paint, true);
	}
	
	/**
	 * <p>Title: drawTable</p>
	 * <p>Description: 绘制表格，根据二维数组直接转化成表格</p>
	 * @param canvas 画布对象
	 * @param table 二维数组，原始数据
	 * @param tableRect 绘制表格的参考点，表格的矩形框的左上角和右下角两个点，这两点确定了表格的尺寸和位置
	 * @param paint 画笔，可以设置绘制的颜色什么的
	 * @param isCenter 绘制的文字是否居中显示
	 */
	private void drawTable(Canvas canvas, String[][] table, TableRect tableRect, Paint paint, boolean isCenter){
	  //绘制Table的参考点
        drawPoint(canvas, tableRect.p0, paint);
        drawPoint(canvas, tableRect.p1, paint);
        
        int horLinesCount = table.length;
        int verLinesCount = table[0].length;
        
        float tableItemHeight = mTableItemHeight;
        //画表格横线
        for(int i = 0; i <= horLinesCount; i++){
            PointF from = new PointF(tableRect.p0.x, tableRect.p0.y+i*tableItemHeight);
            PointF to = new PointF(tableRect.p1.x, from.y);
            drawLine(canvas, from, to, paint);
        }
        
        float tableItemWidth = (float)mXLen/(float)verLinesCount;
        //画表格竖线
        for(int i = 0; i <= verLinesCount; i++){
            PointF from = new PointF(tableRect.p0.x + i*tableItemWidth , tableRect.p0.y);
            PointF to = new PointF(from.x, tableRect.p1.y);
            drawLine(canvas, from, to, paint);
        }
        if(isCenter){
            for(int i = 0; i<table.length; i ++){   //i表示有i行数据
                for(int j = 0;j< table[i].length; j++ ){    //j表示第i行的第j个数据
                    String tmp = table[i][j];
                    float textWidth = getTextWidth(paint, tmp);
                    float centerPointXOffset = (tableItemWidth - textWidth)/2f;
                    float textHeight = paint.getTextSize();
                    
                    float centerPointYOffset = (tableItemHeight - textHeight)/2f +2;//多出来的2是因为从界面显示上看需要再往上移动一点
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
	 * 计算文字宽度
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
	 * 获取字体尺寸对应的字体高度
	 * @param fontSize
	 * @return
	 */
	public float getFontHeight(Paint paint) 
	{ 
	   FontMetrics fm = paint.getFontMetrics(); 
	   return (float)Math.ceil(fm.descent - fm.ascent); 
	}
	
}
