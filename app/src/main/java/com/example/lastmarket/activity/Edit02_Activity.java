package com.example.lastmarket.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lastmarket.R;
import com.example.lastmarket.RetroService02;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Edit02_Activity extends AppCompatActivity {

    ImageView p_iv02;
    String imgPath;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit02);

        p_iv02=findViewById(R.id.p_iv02);
        btn=findViewById(R.id.clickComplete);
        btn.setOnClickListener(v -> clickComplete());
    }

    public void clickComplete(){
        Retrofit retrofit= RetrofitHelper.getRetrofitInstanceScalars();
        RetroService02 retroService02=retrofit.create(RetroService02.class);
        MultipartBody.Part filepart=null;
        if (imgPath!=null){
            File file=new File(imgPath);
            RequestBody requestBody= RequestBody.create(MediaType.parse("image/*"),file);
            filepart=MultipartBody.Part.createFormData("img",file.getName(),requestBody);
        }


        Call<String> call=retroService02.postDataToServer(filepart);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String s=response.body();
                Toast.makeText(Edit02_Activity.this, ""+s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(Edit02_Activity.this, "error"+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        finish();

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void clickSelectImage(View view) {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        resultLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> resultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {

            if(result.getResultCode()!=RESULT_CANCELED){
                Intent intent=result.getData();
                Uri uri=intent.getData();
                if(uri != null){
                    Glide.with(Edit02_Activity.this).load(uri).into(p_iv02);
                    //이미지 uri를 절대주소로 변경해야 파일 업로드가 가능함
                    //uri -->절대경로
                    imgPath=getRealPathFromUri(uri);
                    //경로잘되어있는지 확인!
                    new AlertDialog.Builder(Edit02_Activity.this).setMessage(imgPath).show();
                }
            }

        }
    });

    String getRealPathFromUri(Uri uri){
        String[] proj={MediaStore.Images.Media.DATA};
        CursorLoader loader= new CursorLoader(this,uri,proj,null,null,null);
        Cursor cursor= loader.loadInBackground();
        int column_index= cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result= cursor.getString(column_index);
        cursor.close();
        return result;
    }

}