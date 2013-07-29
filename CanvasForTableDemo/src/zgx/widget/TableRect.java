/**
 * TableRect.java[V 1.0.0]
 * classes : zgx.widget.TableRect
 * jiangyuchen Create at 2013-7-29 ����3:11:56
 */
package zgx.widget;

import android.graphics.PointF;

/**
 * zgx.widget.TableRect
 * @author jiangyuchen
 *
 * create at 2013-7-29 ����3:11:56
 */
public class TableRect {
    private static final String TAG = "CanvasDemo TableRect";
    
    /** p0���ľ��ο����Ͻǵ�*/
    public PointF p0;
    /** p1���ľ��ο����½ǵ�*/
    public PointF p1;
    
    public TableRect(PointF p0, PointF p1){
        this.p0 = p0;
        this.p1 = p1;
    }
}
