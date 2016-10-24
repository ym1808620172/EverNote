package come.evernote.evernote.view;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

/**
 * Created by dllo on 16/10/22.
 */
public class ViewAnimation extends Animation {
    int width, height, duration;
    Camera camera = new Camera();

    public ViewAnimation(int width, int height, int duration) {
        this.width = width;
        this.height = height;
        this.duration = duration;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        //动画持续时间
        setDuration(duration);
        //设置动画结束时的效果
        setFillAfter(true);
        setInterpolator(new LinearInterpolator());
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);

        camera.save();
//        camera.translate(100.0f - 100.0f * interpolatedTime, 150.0f
//                - 150.0f * interpolatedTime, 80.0f - 80.0f * interpolatedTime);
//         camera.rotateX(180 * interpolatedTime);
        camera.rotateY(180 * (1 + interpolatedTime));

        Matrix matrix = t.getMatrix();
        camera.getMatrix(matrix);
         matrix.preTranslate(-width,-height);
        matrix.postTranslate(width, height);
        camera.restore();

    }
}
