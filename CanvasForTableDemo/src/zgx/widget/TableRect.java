/**
 * TableRect.java[V 1.0.0]
 * classes : zgx.widget.TableRect
 * jiangyuchen Create at 2013-7-29 下午3:11:56
 */
package zgx.widget;

import android.graphics.PointF;

/**
 * zgx.widget.TableRect
 * @author jiangyuchen
 *
 * create at 2013-7-29 下午3:11:56
 */
public class TableRect {
    private static final String TAG = "CanvasDemo TableRect";
    
    /** p0表格的矩形框左上角点*/
    public PointF p0;
    /** p1表格的矩形框右下角点*/
    public PointF p1;
    
    public TableRect(PointF p0, PointF p1){
        this.p0 = p0;
        this.p1 = p1;
    }
}
