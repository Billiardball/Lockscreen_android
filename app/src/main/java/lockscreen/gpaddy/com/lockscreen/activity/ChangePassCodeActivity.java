package lockscreen.gpaddy.com.lockscreen.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import lockscreen.gpaddy.com.lockscreen.R;
import lockscreen.gpaddy.com.lockscreen.view.TextViewRobotoLight;

/**
 * Created by Admin on 17/09/2015.
 */
public class ChangePassCodeActivity extends Activity implements View.OnClickListener {
    private ImageView btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    private StringBuilder code, code2;
    private TextViewRobotoLight tvTitle;
    private ImageView imgPass;
    private boolean first = false;
    private TextViewRobotoLight btnCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_lock_passcode);
        btn0 = (ImageView) findViewById(R.id.btn0);
        btn0.setOnClickListener(this);
        btn1 = (ImageView) findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        btn2 = (ImageView) findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        btn3 = (ImageView) findViewById(R.id.btn3);
        btn3.setOnClickListener(this);
        btn4 = (ImageView) findViewById(R.id.btn4);
        btn4.setOnClickListener(this);
        btn5 = (ImageView) findViewById(R.id.btn5);
        btn5.setOnClickListener(this);
        btn6 = (ImageView) findViewById(R.id.btn6);
        btn6.setOnClickListener(this);
        btn7 = (ImageView) findViewById(R.id.btn7);
        btn7.setOnClickListener(this);
        btn8 = (ImageView) findViewById(R.id.btn8);
        btn8.setOnClickListener(this);
        btn9 = (ImageView) findViewById(R.id.btn9);
        btn9.setOnClickListener(this);
        tvTitle = (TextViewRobotoLight) findViewById(R.id.tvTitle);
        imgPass = (ImageView) findViewById(R.id.imgPass);
        btnCancel = (TextViewRobotoLight) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);

        code = new StringBuilder();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel:
                if (code.length() > 0) {
                    code.deleteCharAt(code.length() - 1);
                    setImageCode();
                } else
                    finish();
                break;

            case R.id.btn0:
                if (code.length() < 6) {
                    code.append(0);
                    setImageCode();
                }

                break;
            case R.id.btn1:
                if (code.length() < 6) {
                    code.append(1);
                    setImageCode();
                }
                break;
            case R.id.btn2:
                if (code.length() < 6) {
                    code.append(2);
                    setImageCode();
                }
                break;
            case R.id.btn3:
                if (code.length() < 6) {
                    code.append(3);
                    setImageCode();
                }
                break;
            case R.id.btn4:
                if (code.length() < 6) {
                    code.append(4);
                    setImageCode();
                }
                break;
            case R.id.btn5:
                if (code.length() < 6) {
                    code.append(5);
                    setImageCode();
                }
                break;
            case R.id.btn6:
                if (code.length() < 6) {
                    code.append(6);
                    setImageCode();
                }
                break;
            case R.id.btn7:
                if (code.length() < 6) {
                    code.append(7);
                    setImageCode();
                }
                break;
            case R.id.btn8:
                if (code.length() < 6) {
                    code.append(8);
                    setImageCode();
                }
                break;
            case R.id.btn9:
                if (code.length() < 6) {
                    code.append(9);
                    setImageCode();
                }

                break;
        }
    }

    private void setImageCode() {
        int size = code.length();

        switch (size) {
            case 0:
                imgPass.setImageResource(R.drawable.pas7);
                break;
            case 1:
                imgPass.setImageResource(R.drawable.pas1);
                break;
            case 2:
                imgPass.setImageResource(R.drawable.pas2);
                break;
            case 3:
                imgPass.setImageResource(R.drawable.pas3);
                break;
            case 4:
                imgPass.setImageResource(R.drawable.pas4);
                break;
            case 5:
                imgPass.setImageResource(R.drawable.pas5);
                break;
            case 6:
                imgPass.setImageResource(R.drawable.pas6);
                if (first) {
                    if (code.toString().equals(code2.toString())) {
//                        Toast.makeText(this, "ok", Toast.LENGTH_LONG).show();
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("result", code.toString());
                        setResult(RESULT_OK, returnIntent);
                        finish();

                    } else {
                        code = new StringBuilder();
                        setImageCode();
                    }
                } else {
                    code2 = code;
                    repeatePass();
                    code = new StringBuilder();
                    setImageCode();
                }
                break;

        }
    }

    public void repeatePass() {
        if (code.length() == 6) {
            if (!first) {
                tvTitle.setText(getString(R.string.repeat_enter_passcode));
                first = true;
            }

        }
    }
}
