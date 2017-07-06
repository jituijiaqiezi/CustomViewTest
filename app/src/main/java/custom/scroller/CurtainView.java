package custom.scroller;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.lcp.customviewtest.R;


public class CurtainView extends RelativeLayout implements OnTouchListener{
	private static String TAG = "CurtainView";
	private Context mContext;
	/** Scroller �϶��� */
	private Scroller mScroller;
	/** ��Ļ�߶�  */
	private int mScreenHeigh = 0;
	/** ��Ļ���  */
	private int mScreenWidth = 0;
	/** ���ʱ��Y������*/
	private int downY = 0;
	/** �϶�ʱ��Y������*/
	private int moveY = 0;
	/** �϶�ʱ��Y�ķ������*/
	private int scrollY = 0;
	/** �ɿ�ʱ��Y������*/
	private int upY = 0;
	/** ���Ļ���ĸ߶�*/
	private int curtainHeigh = 0;
	/** �Ƿ� ��*/
	private boolean isOpen = false;
	/** �Ƿ��ڶ��� */
	private boolean isMove = false;
	/** ���ӵ�ͼƬ*/
	private ImageView img_curtain_rope;
	/** ����ͼƬ*/
	private ImageView img_curtain_ad;
	/** ��������ʱ�� */
	private int upDuration = 1000;
	/** ���䶯��ʱ�� */
	private int downDuration = 1000;
	
	public CurtainView(Context context) {
		super(context);
		init(context);
	}

	public CurtainView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public CurtainView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		this.mContext = context;

		Interpolator interpolator = new BounceInterpolator();
		mScroller = new Scroller(context, interpolator);
		mScreenHeigh = getResources().getDisplayMetrics().heightPixels;
		mScreenWidth = getResources().getDisplayMetrics().widthPixels;

		this.setBackgroundColor(Color.argb(0, 0, 0, 0));
		final View view = LayoutInflater.from(mContext).inflate(R.layout.curtain, null);
		img_curtain_ad = (ImageView)view.findViewById(R.id.img_ad);
		img_curtain_rope = (ImageView)view.findViewById(R.id.img_rope);
		addView(view);
		img_curtain_ad.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				curtainHeigh  = img_curtain_ad.getHeight();
				CurtainView.this.scrollTo(0, curtainHeigh);
			}
		});
		img_curtain_rope.setOnTouchListener(this);
	}

	/**
	 * �϶�����
	 * @param startY  
	 * @param dy  ��ֱ����, ������y����
	 * @param duration ʱ��
	 */
	public void startMoveAnim(int startY, int dy, int duration) {
		isMove = true;
		mScroller.startScroll(0, startY, 0, dy, duration);
		invalidate();//֪ͨUI�̵߳ĸ���
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
	}
	
	@Override
	public void computeScroll() {
		//�ж��Ƿ��ڹ��������ڹ���Ϊtrue
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			//���½���
			postInvalidate();
			isMove = true;
		} else {
			isMove = false;
		}
		super.computeScroll();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if (!isMove) {
			int offViewY = 0;//��Ļ�����͸ò��ֶ����ľ���
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				downY = (int) event.getRawY();
				offViewY = downY - (int)event.getX();
				return true;
			case MotionEvent.ACTION_MOVE:
				moveY = (int) event.getRawY();
				scrollY = moveY - downY;
				if (scrollY < 0) {
					// ���ϻ���
					if(isOpen){
						if(Math.abs(scrollY) <= img_curtain_ad.getBottom() - offViewY){
							scrollTo(0, -scrollY);
						}
					}
				} else {
					// ���»���
					if(!isOpen){
						if (scrollY <= curtainHeigh) {
							scrollTo(0, curtainHeigh - scrollY);
						}
					}
				}
				break;
			case MotionEvent.ACTION_UP:
				upY = (int) event.getRawY();
				if(Math.abs(upY - downY) < 10){
					onRopeClick();
					break;
				}
				if (downY > upY) {
					// ���ϻ���
					if(isOpen){
						if (Math.abs(scrollY) > curtainHeigh / 2) {
							// ���ϻ������������Ļ�ߵ�ʱ�� ����������ʧ����
							startMoveAnim(this.getScrollY(),
									(curtainHeigh - this.getScrollY()), upDuration);
							isOpen = false;
						} else {
							startMoveAnim(this.getScrollY(), -this.getScrollY(),upDuration);
							isOpen = true;
						}
					}
				} else {
					// ���»���
					if (scrollY > curtainHeigh / 2) {
						// ���ϻ������������Ļ�ߵ�ʱ�� ����������ʧ����
						startMoveAnim(this.getScrollY(), -this.getScrollY(),upDuration);
						isOpen = true;
					} else {
						startMoveAnim(this.getScrollY(),(curtainHeigh - this.getScrollY()), upDuration);
						isOpen = false;
					}
				}
				break;
			default:
				break;
			}
		}
		return false;
	}
	/**
	 * ����������أ���չ���ر�
	 * ��onToch��ʹ������еķ�����������¼��������˵��ʱ����ӦonTouch���νӲ�������Ӱ��
	 */
	public void onRopeClick(){
		if(isOpen){
			CurtainView.this.startMoveAnim(0, curtainHeigh, upDuration);
		}else{
			CurtainView.this.startMoveAnim(curtainHeigh,-curtainHeigh, downDuration);
		}
		isOpen = !isOpen;
	}
}
