package com.joyue.tech.gankio.ui;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.joyue.tech.core.ui.activity.RapidActivity;
import com.joyue.tech.core.utils.StrKit;
import com.joyue.tech.core.utils.TLog;
import com.joyue.tech.core.utils.ToastUtils;
import com.joyue.tech.gankio.MainActivity;
import com.joyue.tech.gankio.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends RapidActivity implements OnItemClickListener, OnDismissListener {
    @BindView(R.id.ll_login)
    LinearLayout ll_login; // 登录内容的容器
    @BindView(R.id.ll_userid)
    LinearLayout ll_userid; // 将下拉弹出窗口在此容器下方显示
    @BindView(R.id.et_userid)
    EditText et_userid; // 用户名
    @BindView(R.id.et_password)
    EditText et_password; // 密码
    @BindView(R.id.iv_user_drop)
    ImageView iv_user_drop; // 下拉图标
    @BindView(R.id.btn_login)
    Button btn_login; // 登录按钮
    @BindView(R.id.tv_register)
    TextView tv_register; // 注册
    @BindView(R.id.tv_forgot_password)
    TextView tv_forgot_password; // 忘记密码

    String mUserid;
    String mPassword;

    ArrayList<User> mUsers; // 用户列表
    Animation mTranslate; // 位移动画
    Dialog mLoginDialog; // 显示正在登录的Dialog
    ListView mUserListView; // 下拉弹出窗显示的ListView对象
    PopupWindow mPopupWindow; // 下拉弹出窗
    UserAapter mUserLvAapter; // ListView的监听器

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mTranslate = AnimationUtils.loadAnimation(this, R.anim.anim_y_translate); // 初始化动画对象
        ll_login.startAnimation(mTranslate); // Y轴水平移动

        initLoginDialog();
        initListener();

		/* 获取已经保存好的用户密码 */
        mUsers = Utils.getUserList(LoginActivity.this);
        if (mUsers.size() > 0) {
            /* 将列表中的第一个user显示在编辑框 */
            et_userid.setText(mUsers.get(0).getId());
            et_password.setText(mUsers.get(0).getPwd());
        }

        LinearLayout parent = (LinearLayout) getLayoutInflater().inflate(R.layout.listview_userinfo, null);
        mUserListView = (ListView) parent.findViewById(android.R.id.list);
        parent.removeView(mUserListView); // 必须脱离父子关系 不然会报错

        mUserListView.setOnItemClickListener(this); // 设置点击事
        mUserLvAapter = new UserAapter(mUsers);
        mUserListView.setAdapter(mUserLvAapter);
    }

    private void initListener() {
        et_userid.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUserid = s.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });

        et_password.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPassword = s.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void initPopupWindow() {
        int width = ll_userid.getWidth() - 4;
        int height = LayoutParams.WRAP_CONTENT;
        mPopupWindow = new PopupWindow(mUserListView, width, height, true);
        mPopupWindow.setOnDismissListener(this);// 设置弹出窗口消失时监听器

        // 注意要加这句代码，点击弹出窗口其它区域才会让窗口消失
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0xffffffff));
    }

    /* 初始化正在登录对话框 */
    private void initLoginDialog() {
        mLoginDialog = new Dialog(this, R.style.dialog_logining);
        mLoginDialog.setContentView(R.layout.dialog_logining);

        Window window = mLoginDialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();

        // 获取和mLoginingDlg关联的当前窗口的属性，从而设置它在屏幕中显示的位置

        // 获取屏幕的高宽
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int cxScreen = dm.widthPixels;
        int cyScreen = dm.heightPixels;

        int height = (int) getResources().getDimension(R.dimen.logining_dialog_height);// 高42dp
        int lrMargin = (int) getResources().getDimension(R.dimen.logining_dialog_lr_margin); // 左右边沿10dp
        int topMargin = (int) getResources().getDimension(R.dimen.logining_dialog_top_margin); // 上沿20dp

        params.y = (-(cyScreen - height) / 2) + topMargin; // -199
        /* 对话框默认位置在屏幕中心,所以x,y表示此控件到"屏幕中心"的偏移量 */

        params.width = cxScreen;
        params.height = height;
        // width,height表示mLoginingDlg的实际大小

        mLoginDialog.setCanceledOnTouchOutside(true); // 设置点击Dialog外部任意区域关闭Dialog
    }

    /* 显示正在登录对话框 */
    private void showLoginDialog() {
        if (mLoginDialog != null) {
            mLoginDialog.show();
        }
    }

    /* 关闭正在登录对话框 */
    private void hideLoginDialog() {
        if (mLoginDialog != null && mLoginDialog.isShowing()) {
            mLoginDialog.dismiss();
        }
    }

    @OnClick({R.id.btn_login, R.id.iv_user_drop, R.id.tv_register, R.id.tv_forgot_password})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                // 启动登录
                showLoginDialog();
                TLog.i(TAG, mUserid + "  " + mPassword);
                if (StrKit.isEmpty(mUserid)) {
                    ToastUtils.show("请输入账号");
                } else if (StrKit.isEmpty(mPassword)) {
                    ToastUtils.show("请输入密码");
                } else {// 账号和密码都不为空时
                    boolean mIsSave = true;
                    try {
                        TLog.i(TAG, "保存用户列表");
                        for (User user : mUsers) { // 判断本地文档是否有此用户
                            if (user.getId().equals(mUserid)) {
                                mIsSave = false;
                                break;
                            }
                        }
                        if (mIsSave) { // 将新用户加入Users
                            User user = new User(mUserid, mPassword);
                            mUsers.add(user);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    hideLoginDialog();// 关闭对话框
                    ToastUtils.show("登录成功");
                    startActivityWithoutExtras(MainActivity.class);
                }
                break;
            case R.id.iv_user_drop: // 当点击下拉栏
                if (mPopupWindow == null) {
                    initPopupWindow();
                }
                if (!mPopupWindow.isShowing() && mUsers.size() > 0) {
                    iv_user_drop.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black_24dp); // 切换图标
                    mPopupWindow.showAsDropDown(ll_userid, 1, 1); // 显示弹出窗口
                }
                break;
            case R.id.tv_register:
                ToastUtils.show("注册");
                break;
            case R.id.tv_forgot_password:
                ToastUtils.show("忘记密码");
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        et_userid.setText(mUsers.get(position).getId());
        et_password.setText(mUsers.get(position).getPwd());
        mPopupWindow.dismiss();
    }

    /* PopupWindow对象dismiss时的事件 */
    @Override
    public void onDismiss() {
        // TLog.i(TAG, "切换为角向下图标");
        iv_user_drop.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_grey_24dp);
    }

    /* ListView的适配器 */
    class UserAapter extends ArrayAdapter<User> {
        public UserAapter(ArrayList<User> users) {
            super(LoginActivity.this, 0, users);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_login_user, null);
            }

            TextView tv_userid = (TextView) convertView.findViewById(R.id.tv_userid);
            tv_userid.setText(getItem(position).getId());

            ImageView iv_user_delete = (ImageView) convertView.findViewById(R.id.iv_user_delete);
            iv_user_delete.setOnClickListener(new OnClickListener() {
                // 点击删除 iv_user_delete 时,在 mUsers 中删除选中的元素
                @Override
                public void onClick(View v) {
                    if (getItem(position).getId().equals(mUserid)) {
                        // 如果要删除的用户名和编辑框当前值相等则清空
                        mUserid = "";
                        mPassword = "";
                        et_userid.setText(mUserid);
                        et_password.setText(mPassword);
                    }
                    mUsers.remove(getItem(position));
                    mUserLvAapter.notifyDataSetChanged(); // 更新ListView
                }
            });
            return convertView;
        }
    }

    /* 退出此Activity时保存users */
    @Override
    public void onPause() {
        super.onPause();
        try {
            Utils.saveUserList(LoginActivity.this, mUsers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
