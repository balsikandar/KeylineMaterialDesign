package com.balsikandar.android.keylinematerialdesign;

import android.content.Context;
import android.content.Intent;

/**
 * Created by bal sikandar on 30/07/17.
 */

/**
 * API provides customize color, gridSize, transparency
 * and which bar to be drawn
 */
public class KeylineMaterialDesign {

    public static Builder builder(Context context) {
        return new Builder(context);
    }

    public static class Builder {
        private int keylineColor;
        private KeylineUtil.GRIDSIZE gridSize;
        private KeylineUtil.TRANSPARENCY alpha;
        private KeylineUtil.LINE orientation;
        private Context context;

        private Builder(Context context) {
            this.context = context;
        }

        public Builder setColor(int color) {
            this.keylineColor = color;
            return this;
        }

        public Builder setAlpha(KeylineUtil.TRANSPARENCY alpha) {
            this.alpha = alpha;
            return this;
        }

        public Builder setGridSize(KeylineUtil.GRIDSIZE gridSize) {
            this.gridSize = gridSize;
            return this;
        }

        public Builder setOrientation(KeylineUtil.LINE orientation) {
            this.orientation = orientation;
            return this;
        }

        public void drawKeyLines() {
            if (KeylineUtil.isServiceRunning(KeylineDrawService.class, context)) {
                hideKeyLines();
            }
            Intent intent = new Intent(context, KeylineDrawService.class);
            intent.putExtra("keylineColor", keylineColor);
            intent.putExtra("alpha", KeylineUtil.TRANSPARENCY.valueOf(String.valueOf(alpha)).ordinal());
            intent.putExtra("gridSize", KeylineUtil.GRIDSIZE.valueOf(String.valueOf(gridSize)).ordinal());
            intent.putExtra("lines", KeylineUtil.LINE.valueOf(String.valueOf(orientation)).ordinal());
            context.startService(intent);
        }

        public void hideKeyLines() {
            context.stopService(new Intent(context, KeylineDrawService.class));
        }
    }

}
