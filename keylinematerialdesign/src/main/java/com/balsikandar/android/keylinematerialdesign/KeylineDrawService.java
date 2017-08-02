package com.balsikandar.android.keylinematerialdesign;


import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.IBinder;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by bal sikandar on 30/07/17.
 */

/**
 * Draws 8dp square baseline grid on android screen following android Material Design guideLines.
 * All components align to an 8dp square baseline grid, Check in your app it follows the principle.
 * Keyline GridView Usage Behaviours
 * <p>
 * call setColor(int color) to choose color for grid lines
 * <p>
 * Use setTransParency(Transparency.FIFTY_PERCENT) to change transparency
 * of gridlines to 10%, 20%.....100% Default 50%
 * <p>
 * call setGridSize(KeylineUtil.GRIDSIZE.FIFTYDP) to change square grid bars size
 * <p>
 * will release next version soon with touch interactions
 */
public class KeylineDrawService extends IntentService {
    private WindowManager windowManager;

    Bitmap gridLinesBitmap;
    ImageView imageView;
    Paint paint;
    Canvas canvas;

    private int keylineColor;
    private int alpha;
    private int gridSizeInDp;
    private int lineType;
    int deviceHeight;
    int deviceWidth;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public KeylineDrawService() {
        super("KeylineService");
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            this.keylineColor = intent.getIntExtra("keylineColor", Color.BLACK);
            this.gridSizeInDp = (intent.getIntExtra("squareSize", 2) + 1) * 4;
            this.alpha = intent.getIntExtra("alpha", 5) * 25;
            this.lineType = intent.getIntExtra("lines", KeylineUtil.LINE.BOTH.ordinal());
        } else {
            //show error msg here
            clearBars();
        }

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(keylineColor);
        paint.setAlpha(alpha);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(KeylineUtil.dpToPx(1, this));

        draw();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (windowManager != null) {
            if (imageView != null && imageView.isShown()) {
                windowManager.removeView(imageView);
            }
        }
        super.onDestroy();
    }

    public void draw() {

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display deviceDisplay = windowManager.getDefaultDisplay();
        Point point = new Point();
        deviceDisplay.getSize(point);
        deviceWidth = point.x;
        deviceHeight = point.y;

        imageView = new ImageView(this);
        imageView.setClickable(false);
        imageView.setFocusable(false);
        imageView.setMinimumHeight(deviceHeight);
        imageView.setMinimumWidth(deviceWidth);

        drawKeylines();

    }

    private void drawKeylines() {

        if (gridLinesBitmap == null) {
            createBitmap();
        }

        int pixelInEightDp = KeylineUtil.dpToPx(gridSizeInDp, this);

        if (lineType == KeylineUtil.LINE.BOTH.ordinal() || lineType == KeylineUtil.LINE.VERTICAL.ordinal()) {
            for (int index = 0; index < deviceWidth; index += pixelInEightDp) {
                canvas.drawLine(index, 0, index, deviceHeight, paint);
            }
        }

        if (lineType == KeylineUtil.LINE.BOTH.ordinal() || lineType == KeylineUtil.LINE.HORIZONTAL.ordinal()) {
            for (int index = 0; index < deviceHeight; index += pixelInEightDp) {
                canvas.drawLine(0, index, deviceWidth, index, paint);
            }
        }

        imageView.setImageBitmap(gridLinesBitmap);

        attachImageToWindow();

        imageView.invalidate();
    }

    /**
     * attach image to window manager
     */
    private void attachImageToWindow() {
        if (imageView != null && imageView.isShown()) {
            windowManager.removeView(imageView);
        }
        windowManager.addView(imageView, new WindowManager.
                LayoutParams(deviceWidth, deviceHeight, WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT));
    }

    private void createBitmap() {
        gridLinesBitmap = Bitmap.createBitmap(deviceWidth, deviceHeight, Bitmap.Config.ARGB_8888);
        canvas = new Canvas();
        canvas.setBitmap(gridLinesBitmap);
    }

    //Configure API at runtime
    public void setColor(int color) {
        paint.setColor(color);
        drawKeylines();
    }

    public void setSquareSizeInDp(int width) {
        paint.setStrokeWidth(width);
        drawKeylines();
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
        drawKeylines();
    }

    /**
     * stop keyline draw service
     */
    private void clearBars() {
        stopSelf();
    }

}