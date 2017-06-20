package custom.password;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

import com.lcp.datepickertest.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by linchenpeng on 2017/6/14.
 */

public class PasswordView extends View {
    private Mode mode;
    private int passwordLength;
    private long cursorFlashTime;
    private int passwordPadding;
    private int passwordSize = dp2px(40);
    private int borderColor;
    private int borderWidth;
    private int cursorPosition;
    private int cursorWidth;
    private int cursorHeight;
    private int cursorColor;
    private boolean isCursorShowing;
    private boolean isCursorEnable;
    private boolean isInputComplete;
    private int cipherTextSize;
    private boolean cipherEnable;
    private static String CIPHER_TEXT = "*";
    private String[] password;
    private InputMethodManager inputManager;
    private PasswordListener passwordListener;
    private Paint paint;
    private Timer timer;
    private TimerTask timerTask;

    public PasswordView(Context context) {
        this(context, null);
    }

    public PasswordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        readAttributes(attrs);
    }

    private void readAttributes(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.PasswordView);
            mode = Mode.formMode(array.getInt(R.styleable.PasswordView_mode, Mode.UNDERLINE.getMode()));
            passwordLength = array.getInt(R.styleable.PasswordView_passwordLength, 4);
            cursorFlashTime = array.getInt(R.styleable.PasswordView_cursorFlashTime, 500);
            borderWidth = array.getDimensionPixelSize(R.styleable.PasswordView_borderWidth, dp2px(3));
            borderColor = array.getColor(R.styleable.PasswordView_borderColor, Color.BLACK);
            cursorColor = array.getColor(R.styleable.PasswordView_cursorColor, Color.GRAY);
            isCursorEnable = array.getBoolean(R.styleable.PasswordView_isCursorEnable, true);
            if (mode == Mode.UNDERLINE) {
                passwordPadding = array.getDimensionPixelSize(R.styleable.PasswordView_passwordPadding, dp2px(15));
            } else {
                passwordPadding = array.getDimensionPixelSize(R.styleable.PasswordView_passwordPadding, 0);
            }
            cipherEnable = array.getBoolean(R.styleable.PasswordView_cipherEnable, true);
            array.recycle();
        }
        password = new String[passwordLength];
        init();
    }

    private void init() {
        setFocusableInTouchMode(true);
        MyKeyListener keyListener = new MyKeyListener();
        setOnKeyListener(keyListener);
        inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        paint = new Paint();
        paint.setAntiAlias(true);
        timerTask = new TimerTask() {
            @Override
            public void run() {
                isCursorShowing = !isCursorShowing;
                postInvalidate();
            }
        };
        timer = new Timer();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = 0;
        switch (widthMode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                width = passwordSize * passwordLength + passwordPadding * (passwordLength - 1);
                break;
            case MeasureSpec.EXACTLY:
                width = MeasureSpec.getSize(widthMeasureSpec);
                passwordSize = (width - (passwordPadding * (passwordLength - 1))) / passwordLength;
                break;
        }
        setMeasuredDimension(width, passwordSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cipherTextSize = passwordSize / 2;
        cursorWidth = dp2px(2);
        cursorHeight = passwordSize / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mode == Mode.UNDERLINE) {
            drawUnderLine(canvas, paint);
        } else {
            drawRect(canvas, paint);
        }
        drawCursor(canvas, paint);
        drawCipherText(canvas, paint);
    }


    class MyKeyListener implements OnKeyListener {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            int action = event.getAction();
            if (action == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (TextUtils.isEmpty(password[0]))
                        return true;
                    String deleteText = delete();
                    if (passwordListener != null && !TextUtils.isEmpty(deleteText)) {
                        passwordListener.passwordChange(deleteText);
                    }
                    postInvalidate();
                    return true;
                }
                if (keyCode >= KeyEvent.KEYCODE_0 && keyCode <= KeyEvent.KEYCODE_9) {
                    if (isInputComplete) {
                        return true;
                    }
                    String addText = add((keyCode - 7) + "");
                    if (passwordListener != null && !TextUtils.isEmpty(addText))
                        passwordListener.passwordChange(addText);
                    postInvalidate();
                    return true;
                }
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (passwordListener != null)
                        passwordListener.keyEnterPress(getPassword(), isInputComplete);
                    return true;
                }
            }
            return false;
        }
    }

    private String delete() {
        String deleteText = null;
        if (cursorPosition > 0) {
            deleteText = password[cursorPosition - 1];
            password[cursorPosition - 1] = null;
            cursorPosition--;
        } else if (cursorPosition == 0) {
            deleteText = password[cursorPosition];
            password[cursorPosition] = null;
        }
        isInputComplete = false;
        return deleteText;
    }

    private String add(String c) {
        String addText = null;
        if (cursorPosition < passwordLength) {
            addText = c;
            password[cursorPosition] = c;
            cursorPosition++;
            if (cursorPosition == passwordLength) {
                isInputComplete = true;
                if (passwordListener != null)
                    passwordListener.passwordComplete();
            }
        }
        return addText;
    }

    private String getPassword() {
        StringBuffer buffer = new StringBuffer();
        for (String c : password) {
            if (TextUtils.isEmpty(c))
                continue;
            buffer.append(c);
        }
        return buffer.toString();
    }

    private void drawUnderLine(Canvas canvas, Paint paint) {
        paint.setColor(borderColor);
        paint.setStrokeWidth(borderWidth);
        paint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < passwordLength; i++) {
            canvas.drawLine(getPaddingLeft() + (passwordSize + passwordPadding) * i, getPaddingTop() + passwordSize,
                    getPaddingLeft() + (passwordSize + passwordPadding) * i + passwordSize, getPaddingTop() + passwordSize, paint);
        }
    }

    private void drawRect(Canvas canvas, Paint paint) {
        paint.setColor(borderColor);
        paint.setStrokeWidth(0);
        paint.setStyle(Paint.Style.STROKE);
        Rect rect;
        for (int i = 0; i < passwordLength; i++) {
            int startX = getPaddingLeft() + (passwordSize + passwordPadding) * i;
            int startY = getPaddingTop();
            int stopX = getPaddingLeft() + (passwordSize + passwordPadding) * i + passwordSize;
            int stopY = getPaddingTop() + passwordSize;
            rect = new Rect(startX, startY, stopX, stopY);
            canvas.drawRect(rect, paint);
        }
    }

    private void drawCursor(Canvas canvas, Paint paint) {
        paint.setColor(cursorColor);
        paint.setStrokeWidth(cursorWidth);
        paint.setStyle(Paint.Style.FILL);
        if (!isCursorShowing && isCursorEnable && !isInputComplete && hasFocus()) {
            canvas.drawLine((getPaddingLeft() + passwordSize / 2) + (passwordSize + passwordPadding) * cursorPosition,
                    getPaddingTop() + (passwordSize - cursorHeight) / 2,
                    (getPaddingLeft() + passwordSize / 2) + (passwordSize + passwordPadding) * cursorPosition,
                    getPaddingTop() + (passwordSize + cursorHeight) / 2,
                    paint);
        }
    }

    private void drawCipherText(Canvas canvas, Paint paint) {
        paint.setColor(Color.GRAY);
        paint.setTextSize(cipherTextSize);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setStyle(Paint.Style.FILL);
        Rect r = new Rect();
        canvas.getClipBounds(r);
        int cHeight = r.height();
        paint.getTextBounds(CIPHER_TEXT, 0, CIPHER_TEXT.length(), r);
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        for (int i = 0; i < password.length; i++) {
            if (!TextUtils.isEmpty(password[i])) {
                if (cipherEnable) {
                    canvas.drawText(CIPHER_TEXT, (getPaddingLeft() + passwordSize / 2) + (passwordSize + passwordPadding) * i,
                            getPaddingTop() + y, paint);
                }else{
                    canvas.drawText(password[i], (getPaddingLeft() + passwordSize / 2) + (passwordSize + passwordPadding) * i,
                            getPaddingTop() + y, paint);
                }
            }
        }
    }

    public Mode getMode() {
        return mode;
    }

    public enum Mode {
        UNDERLINE(0),
        RECT(1);
        private int mode;

        Mode(int mode) {
            this.mode = mode;
        }

        public int getMode() {
            return mode;
        }

        static Mode formMode(int mode) {
            for (Mode m : values()) {
                if (mode == m.mode) {
                    return m;
                }
            }
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            requestFocus();
            inputManager.showSoftInput(this, InputMethodManager.SHOW_FORCED);
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (!hasWindowFocus) {
            inputManager.hideSoftInputFromWindow(this.getWindowToken(), 0);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        timer.scheduleAtFixedRate(timerTask, 0, cursorFlashTime);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        timer.cancel();
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        outAttrs.inputType = InputType.TYPE_CLASS_NUMBER;
        return super.onCreateInputConnection(outAttrs);
    }

    public void setPasswordLength(int passwordLength) {
        this.passwordLength = passwordLength;
        postInvalidate();
    }

    public void setPasswordSize(int passwordSize) {
        this.passwordSize = passwordSize;
        postInvalidate();
    }

    public void setCursorColor(int cursorColor) {
        this.cursorColor = cursorColor;
        postInvalidate();
    }

    public void setCursorEnable(boolean cursorEnable) {
        isCursorEnable = cursorEnable;
        postInvalidate();
    }

    public void setCipherEnable(boolean cipherEnable) {
        this.cipherEnable = cipherEnable;
        postInvalidate();
    }

    public void setPasswordListener(PasswordListener passwordListener) {
        this.passwordListener = passwordListener;
    }

    private int dp2px(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
    }

    private int sp2px(float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, getResources().getDisplayMetrics());
    }

    public interface PasswordListener {
        void passwordChange(String changeText);

        void passwordComplete();

        void keyEnterPress(String password, boolean isComplete);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("superState", super.onSaveInstanceState());
        bundle.putStringArray("password", password);
        bundle.putInt("cursorPosition", cursorPosition);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            password = bundle.getStringArray("password");
            cursorPosition = bundle.getInt("cursorPosition");
            state = bundle.getParcelable("superState");
        }
        super.onRestoreInstanceState(state);
    }
}
