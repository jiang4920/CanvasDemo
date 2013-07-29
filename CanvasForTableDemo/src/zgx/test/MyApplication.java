package zgx.test;

import android.app.Application;

/**
 * @ClassName : MyApplication
 * @Description : TODO
 * @author : ZGX zhangguoxiao_happy@163.com
 * @date : 2011-9-24 обнГ03:01:00
 * 
 */
public class MyApplication extends Application {
	private int num;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		setNum(10);
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
}
