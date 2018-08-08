package com.example.administrator.myapplication.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.example.administrator.myapplication.Adapter.UserAdapter;
import com.example.administrator.myapplication.Etity.Common;
import com.example.administrator.myapplication.Etity.Live;
import com.example.administrator.myapplication.Etity.Token;
import com.example.administrator.myapplication.Etity.User;
import com.example.administrator.myapplication.Player.MainActivity2;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Tools.CallBackUtil;
import com.example.administrator.myapplication.Tools.DensityUtils;
import com.example.administrator.myapplication.Tools.OkhttpUtil;
import com.example.administrator.myapplication.Tools.TimeUtil;
import com.example.administrator.myapplication.Tools.ToastUtils;
import com.example.administrator.myapplication.Tools.url;
import com.example.administrator.myapplication.xlistview.XListView;
import com.google.gson.Gson;


import okhttp3.Call;

import android.os.Handler;
import android.os.Message;

import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;


import static android.widget.Toast.LENGTH_LONG;
import static com.example.administrator.myapplication.Tools.TimeUtil.getTime;

public class MainActivity extends Activity {
    private RadioGroup radiogroup;
    private XListView mListView;
    private List<User.Data> items = new ArrayList<User.Data>();
    private Handler mHandler = new Handler();
    private Handler handler = new Handler();
    private Message message = null;
    private int mIndex = 0;
    private int mRefreshIndex = 0;
    public UserAdapter userAdapter;
    private String token = "";
    private List<String> getListSize = new ArrayList<>();
    private int subjectId = 1;
    private int page = 1;
   private int ID;
   public static String src;
private TextView back;

    HashMap<String, String> paramsMap = new HashMap<>();
    HashMap<String, String> paramsMap2 = new HashMap<>();
    HashMap<String, String> paramsMap3 = new HashMap<>();
    HashMap<String, String> paramsMap4 = new HashMap<>();
    HashMap<String, String> paramsMap5 = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        back = (TextView) findViewById(R.id.back);
        radiogroup = (RadioGroup) findViewById(R.id.rgroup);//头部单选框
        mListView = (XListView) findViewById(R.id.list_view);
        mListView.setPullRefreshEnable(false);
        mListView.setPullLoadEnable(true);
        mListView.setAutoLoadEnable(true);
//        mListView.setRefreshTime(getTime());

        geneItems();//数据
        userAdapter = new UserAdapter(this, R.layout.vw_list_item, items);
        mListView.setAdapter(userAdapter);



        handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        items = (List<User.Data>) msg.obj;
                        if (items != null && items.size() != 0) {

                            for (User.Data product : items) {
                                userAdapter.addAll(product);

                            }
                        }
                        break;

                }
            }
        };

        //列表滑动监听
        mListView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            // 下拉刷新
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIndex = ++mRefreshIndex;
                        items.clear();
                        page = 1;
                        geneItems1();
                        userAdapter = new UserAdapter(MainActivity.this, R.layout.vw_list_item, items);
                        mListView.setAdapter(userAdapter);
                        onLoad();
                    }
                }, 2500);
            }

            @Override
            //上拉加载
            public void onLoadMore() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = page + 1;
                        geneItems1();
                        userAdapter.notifyDataSetChanged();
                        onLoad();
                    }
                }, 2500);
            }
        });


        //list监听 点击事件   播放视频
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override//这里需要注意的是第三个参数int position，这是代表单击第几个选项

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //通过单击事件，获得单击选项的内容
                 ID = userAdapter.getItem(position).getId();
                geneItems3();


            }
        });


    }


    //动态添加单选按钮
    public void addview(RadioGroup radiogroup) {

        int index = 0;
        for (String ss : getListSize) {

            RadioButton button = new RadioButton(this);
            setRaidBtnAttribute(button, ss, index);

            radiogroup.addView(button);

            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button
                    .getLayoutParams();
            layoutParams.setMargins(0, 0, DensityUtils.dp2px(this, 0), 0);//4个参数按顺序分别是左上右下
            button.setLayoutParams(layoutParams);
            index++;
        }


    }


    private void setRaidBtnAttribute(final RadioButton codeBtn, String btnContent, int id) {
        if (null == codeBtn) {
            return;
        }
        codeBtn.setBackgroundResource(R.drawable.lan_hei_select);
//        codeBtn.setButtonDrawable(R.drawable.lvxian_baixian_select);
        codeBtn.setTextColor(this.getResources().getColorStateList(R.color.lan_hei_text_color_selector));
        codeBtn.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
        codeBtn.setTextSize(16);

        //codeBtn.setTextSize( ( textSize > 16 )?textSize:24 );
        codeBtn.setId(id);
        if (codeBtn.getId() == 0) {
            codeBtn.setChecked(true);
            codeBtn.getId();
            geneItems1();
        }
        codeBtn.setText(btnContent);
        ;
        codeBtn.setPadding(40, 0, 40, 0);

        codeBtn.setGravity(Gravity.CENTER);
        codeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, codeBtn.getId()+"", Toast.LENGTH_SHORT).show();
                subjectId = codeBtn.getId();
                subjectId = subjectId + 1;
                page = 1;
                items.clear();
                geneItems1();
                userAdapter = new UserAdapter(MainActivity.this, R.layout.vw_list_item, items);
                mListView.setAdapter(userAdapter);
                onLoad();
            }
        });
        //DensityUtilHelps.Dp2Px(this,40)
        LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, DensityUtils.dp2px(this, 30));

        codeBtn.setLayoutParams(rlp);
    }

    //单选数据源
    private void geneItems() {
        //一级请求获取token值
        paramsMap.put("secretId", url.secretId + "");
        paramsMap.put("secretKey", url.secretKey + "");
        OkhttpUtil.okHttpGet(url.urlapi + "Auth/getAuth", paramsMap, new CallBackUtil.CallBackString() {

            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                System.out.println("返回值是什么" + response);
                Gson gson = new Gson();
                Token t = gson.fromJson(response, Token.class);
                System.out.println("尸体" + t);
                token = t.getToken();
                if (t.getCode().equals("0")) {
                } else {
                    ToastUtils.showToast(MainActivity.this, t.getMsg().toString());
                }
                //二级请求获取头部按钮列表
                paramsMap2.put("secretId", url.secretId + "");

                String stime = TimeUtil.getTime(TimeUtil.getTime());

                System.out.println("时间" + TimeUtil.getTime());
                paramsMap2.put("timestamp", String.valueOf(stime));

                paramsMap2.put("token", token + "");

                System.out.println("请求类容" + paramsMap2);
                System.out.println("请求地址" + url.urlapi + "MicroCourse/getMcourseSubject");
                OkhttpUtil.okHttpGet(url.urlapi + "MicroCourse/getMcourseSubject", paramsMap2, new CallBackUtil.CallBackString() {

                    @Override
                    public void onFailure(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        System.out.println("2级返回值是什么" + response);
                        Gson gson = new Gson();
                        Common t = gson.fromJson(response, Common.class);
                        System.out.println("尸体" + t);
                        if (t.getCode() == 0) {
                        } else {
                            ToastUtils.showToast(MainActivity.this, t.getMsg().toString());
                        }


                        for (int i = 0; i < t.getData().size(); i++) {
                            getListSize.add(t.getData().get(i).getName());
                        }
                        ;

                        //动态添加单选按钮
                        addview(radiogroup);
                    }
                });
            }
        });


    }

    //单选数据源
    private void geneItems1() {
        //3级请求获取token值
        paramsMap.put("secretId", url.secretId + "");
        paramsMap.put("secretKey", url.secretKey + "");
        OkhttpUtil.okHttpGet(url.urlapi + "Auth/getAuth", paramsMap, new CallBackUtil.CallBackString() {

            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                System.out.println("3返回值是什么" + response);
                Gson gson = new Gson();
                Token t = gson.fromJson(response, Token.class);
                System.out.println("尸体" + t);
                token = t.getToken();
                if (t.getCode().equals("0")) {
                    //4级请求获课程列表
                    paramsMap3.put("subjectId", String.valueOf(subjectId));
                    System.out.println("subjectId=" + subjectId);

                    paramsMap3.put("page", String.valueOf(page));
                    System.out.println("page=" + page);

                    paramsMap3.put("secretId", url.secretId + "");
                    System.out.println("secretId=" + url.secretId);

                    String stime = TimeUtil.getTime(TimeUtil.getTime());
                    paramsMap3.put("timestamp", String.valueOf(stime));
                    System.out.println("timestamp=" + stime);

                    paramsMap3.put("token", token + "");
                    System.out.println("token=" + token);
                    OkhttpUtil.okHttpGet(url.urlapi + "MicroCourse/getMcourseLesson", paramsMap3, new CallBackUtil.CallBackString() {

                        @Override
                        public void onFailure(Call call, Exception e) {

                        }

                        @Override
                        public void onResponse(String response) {
                            System.out.println("4级返回值是什么" + response);
                            Gson gson = new Gson();
                            User t = gson.fromJson(response, User.class);
                            System.out.println("尸体" + t.getData());
                            if (t.getCode() == 0) {

                                message = handler.obtainMessage();
                                message.what = 0;
                                message.obj = t.getData();
                                handler.sendMessage(message);

                            } else {
                                ToastUtils.showToast(MainActivity.this, t.getMsg().toString());
                            }

                        }
                    });
                } else {
                    ToastUtils.showToast(MainActivity.this, t.getMsg().toString());
                }

            }
        });


    }

    //单选数据源
    private void geneItems3() {
        //一级请求获取token值
        paramsMap4.put("secretId", url.secretId + "");
        paramsMap4.put("secretKey", url.secretKey + "");
        OkhttpUtil.okHttpGet(url.urlapi + "Auth/getAuth", paramsMap4, new CallBackUtil.CallBackString() {

            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                System.out.println("4返回值是什么" + response);
                Gson gson = new Gson();
                Token t = gson.fromJson(response, Token.class);
                System.out.println("尸体" + t);
                token = t.getToken();
                if (t.getCode().equals("0")) {
                    //二级请求获取头部按钮列表
                    paramsMap5.put("lessonId", String.valueOf(ID));

                    paramsMap5.put("secretId", url.secretId);

                    String stime = getTime(getTime());
                    System.out.println("时间" + getTime());
                    paramsMap5.put("timestamp", String.valueOf(stime));

                    paramsMap5.put("token", token);

                    System.out.println("请求类容" + paramsMap5);
                    System.out.println("请求地址" + url.urlapi + "MicroCourse/getMcourseLessonVod");
                    OkhttpUtil.okHttpGet(url.urlapi + "MicroCourse/getMcourseLessonVod", paramsMap5, new CallBackUtil.CallBackString() {

                        @Override
                        public void onFailure(Call call, Exception e) {

                        }

                        @Override
                        public void onResponse(String response) {
                            System.out.println("5级返回值是什么" + response);
                            Gson gson = new Gson();
                            Live t = gson.fromJson(response, Live.class);
                            System.out.println("尸体" + t);
                            if (t.getCode() == 0) {
                                //播放视频
                                src = t.getSrc();
                                System.out.println("播放地址是" + src);

                                Intent i = new Intent(MainActivity.this, MainActivity2.class);
                                startActivity(i);

                            } else {
                                ToastUtils.showToast(MainActivity.this, t.getMsg().toString());
                            }
                        }
                    });
                } else {
                    ToastUtils.showToast(MainActivity.this, t.getMsg().toString());
                }

            }
        });
    }



    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime(TimeUtil.getTime());
    }


//
//    private void initWindow() {//初始化，将状态栏和标题栏设为透明
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//
//        }
    }


