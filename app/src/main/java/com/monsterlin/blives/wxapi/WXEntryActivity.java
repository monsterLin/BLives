package com.monsterlin.blives.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.monsterlin.blives.constants.KEY;
import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        api = WXAPIFactory.createWXAPI(this, KEY.APPID, false);
        api.handleIntent(getIntent(), this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onReq(BaseReq arg0) {
    }

    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                //分享成功
                showToast("分享成功");
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //分享取消
                showToast("分享失败");
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //分享拒绝
                showToast("分享被拒绝");
                break;
        }
    }

    private void showToast(String msg){
        Toast.makeText(this,""+msg,Toast.LENGTH_SHORT).show();
    }
}
