package come.evernote.evernote.controler.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import come.evernote.evernote.R;

public class RemendPopActivity extends Activity implements View.OnClickListener  {

    private ImageView dataImg;
    private LinearLayout rooLl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remend_pop);
        rooLl = (LinearLayout) findViewById(R.id.remend_pop_root);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dataImg = (ImageView) findViewById(R.id.data_pop_img);
        dataImg.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.data_pop_img:
                Intent intent = new Intent(RemendPopActivity.this, RemendDataActivity.class);
                startActivity(intent);
                break;
        }
    }

}
