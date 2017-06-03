package com.list.asus.forsignt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private CheckFragment checkFragment;
    private RecordFragment recordFragment;
    private CurriculumFragment curriculumFragment;
    private MineFragment mineFragment;



    //主界面toolbar中左右边的button
    public Button buToolbarLeft;
    public Button buToolbarRight;

    //主界面底部item的button，即打卡,课表，我的
    public Button bottomCheck;
    public Button bottomRecord;
    public Button bottomCurriculum;
    public Button bottomMine;

    // Fragment管理器
    private android.support.v4.app.FragmentManager manager;
    android.support.v4.app.FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化 SDK信息
        Bmob.initialize(getApplicationContext(),"a16ec96b707da5d0f2a404b0ce9d755b");




        BmobUser bmobUser = BmobUser.getCurrentUser();
        if(bmobUser != null){
            // 允许用户使用应用
            initView();
            initActivityView();
        }else{
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }





    }

    private void initView() {
        buToolbarLeft=(Button)findViewById(R.id.toolbal_left);
        buToolbarRight=(Button)findViewById(R.id.toolbal_right);

        bottomCheck=(Button)findViewById(R.id.bottom_check);
        bottomRecord=(Button)findViewById(R.id.bottom_record);
        bottomCurriculum=(Button)findViewById(R.id.bottom_curriculum);
        bottomMine=(Button)findViewById(R.id.bottom_mine) ;


        bottomCheck.setOnClickListener(this);
        bottomRecord.setOnClickListener(this);
        bottomCurriculum.setOnClickListener(this);
        bottomMine.setOnClickListener(this);
    }

    //默认加载第一个布局
    private void initActivityView() {

        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

        changeItemColor(bottomCheck);
        buToolbarLeft.setText("考勤");
        buToolbarRight.setText("帮助");
        checkFragment = new CheckFragment();
        transaction.replace(R.id.main_fragment, checkFragment);
        transaction.commit();
//        buToolbarRight.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @Override
    public void onClick(View v) {

        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

        switch (v.getId()){
            //点击底部item的打卡时
            case R.id.bottom_check:
                changeItemColor(bottomCheck);
                buToolbarLeft.setText("考勤");
                buToolbarRight.setText("帮助");
                checkFragment = new CheckFragment();
                transaction.replace(R.id.main_fragment, checkFragment);
                transaction.commit();
                buToolbarRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent=new Intent(MainActivity.this,CheckFragment_Help.class);
                        startActivity(intent);
                    }
                });
                break;
            case R.id.bottom_record:
                changeItemColor(bottomRecord);
                buToolbarLeft.setText("考勤记录");
                buToolbarRight.setText("导出全部");
                recordFragment = new RecordFragment();
                transaction.replace(R.id.main_fragment, recordFragment);
                transaction.commit();
                buToolbarRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast
                             .makeText(getBaseContext(),"此功能还在完善中",Toast.LENGTH_SHORT)
                             .show();
                    }
                });
                break;
            case R.id.bottom_curriculum:
                changeItemColor(bottomCurriculum);
                buToolbarLeft.setText("课表详情");
                buToolbarRight.setText("");
                curriculumFragment = new CurriculumFragment();
                transaction.replace(R.id.main_fragment, curriculumFragment);
                transaction.commit();
                break;
            case R.id.bottom_mine:
                changeItemColor(bottomMine);
                buToolbarLeft.setText("我的");
                buToolbarRight.setText("修改");
                mineFragment = new MineFragment();
                buToolbarRight.setTextColor(this.getResources().getColor(R.color.colorBlue));
                transaction.replace(R.id.main_fragment, mineFragment);
                transaction.commit();
                //在第三个fragment中点击toolbar右边按钮（“修改”按钮）
                buToolbarRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                break;
//            case R.id.check:
//                Log.d("经过了这里","1");
//
//                click_check();
//                break;
            default:
                break;
        }
    }



    public void changeItemColor(Button item){
        bottomCheck.setTextColor(this.getResources().getColor(R.color.colorBlack));
        bottomRecord.setTextColor(this.getResources().getColor(R.color.colorBlack));
        bottomCurriculum.setTextColor(this.getResources().getColor(R.color.colorBlack));
        bottomMine.setTextColor(this.getResources().getColor(R.color.colorBlack));
        item.setTextColor(this.getResources().getColor(R.color.colorAccent));
    }
}