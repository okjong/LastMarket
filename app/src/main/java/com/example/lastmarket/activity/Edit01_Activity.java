package com.example.lastmarket.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lastmarket.G;
import com.example.lastmarket.R;
import com.example.lastmarket.activity.RetrofitHelper;
import com.example.lastmarket.RetrofitService;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Edit01_Activity extends AppCompatActivity {

    EditText p_name01, p_price01;
    ImageView p_iv01;
    String imgPath; //업로드할 이미지의 절대경로
    Button btn;

    //로그인통한 정보들
    TextView userNickName;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit01);

        p_name01=findViewById(R.id.p_name01);
        p_price01=findViewById(R.id.p_price01);
        p_iv01=findViewById(R.id.p_iv01);
        btn=findViewById(R.id.btn_complete);

        btn.setOnClickListener(v->clickComplete());

        userNickName=findViewById(R.id.userNickName);

        userNickName.setText(G.userVo.name);

        userId= G.userVo.id;

        String[] permissions= new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_MEDIA_LOCATION};
        if(checkSelfPermission(permissions[0]) == PackageManager.PERMISSION_DENIED){
            requestPermissions(permissions, 100);
        }

    }

    public void clickComplete(){
        String name= p_name01.getText().toString();
        String price= p_price01.getText().toString();

        String nickName=userNickName.getText().toString();
        String id=userId;

        Retrofit retrofit=RetrofitHelper.getRetrofitInstanceScalars();
        RetrofitService retrofitService=retrofit.create(RetrofitService.class);

        MultipartBody.Part filePart=null;
        if(imgPath!=null){
            File file= new File(imgPath);
            RequestBody requestBody=RequestBody.create(MediaType.parse("image/*"),file);
            filePart= MultipartBody.Part.createFormData("img", file.getName(),requestBody);
        }

        Map<String, String> dataPart= new HashMap<>();
        dataPart.put("name", name);
        dataPart.put("price", price);
        dataPart.put("nickName",nickName);
        dataPart.put("id",id);

        Call<String> call= retrofitService.postDataToServer(dataPart,filePart);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                String s= response.body();
                Toast.makeText(Edit01_Activity.this, ""+s, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(Edit01_Activity.this, "error"+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        finish();

    }

    public void clickSelectImage(View view) {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        resultLauncher.launch(intent);

    }

    ActivityResultLauncher<Intent> resultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode()!=RESULT_CANCELED){
                Intent intent=result.getData();
                Uri uri=intent.getData();
                Glide.with(Edit01_Activity.this).load(uri).into(p_iv01);
                imgPath= getRealPathFromUri(uri);
                new AlertDialog.Builder(Edit01_Activity.this).setMessage(imgPath).create().show();
            }
        }
    });

    //Uri -- > 절대경로로 바꿔서 리턴시켜주는 메소드
    String getRealPathFromUri(Uri uri){
        String[] proj= {MediaStore.Images.Media.DATA};
        CursorLoader loader= new CursorLoader(this,uri,proj,null,null,null);
        Cursor cursor=loader.loadInBackground();
        int column_index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result= cursor.getString(column_index);
        cursor.close();
        return result;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


}