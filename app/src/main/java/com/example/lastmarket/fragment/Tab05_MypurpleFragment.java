package com.example.lastmarket.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.lastmarket.G;
import com.example.lastmarket.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.utilities.Utilities;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.util.Utility;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;


import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

public class Tab05_MypurpleFragment extends Fragment {

    TextView nickName;
    CircleImageView ivProfile;
    Button kakaoBtn;

    //다른액티비티로보낼 카카오로그인정보 변수들
    //  static String loginNickName;

    Button logBtn;
    Button logOut;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_5_mypurple, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //xml뷰 들에대한 find
        logBtn = view.findViewById(R.id.logBtn);

        nickName = view.findViewById(R.id.nickName);
        ivProfile = view.findViewById(R.id.ivProfile);
        kakaoBtn = view.findViewById(R.id.kakaoBtn);
        logOut = view.findViewById(R.id.logOut);

        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), login_Activity.class);
//                startActivity(intent);
            }
        });


        kakaoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserApiClient.getInstance().loginWithKakaoAccount(getContext(), new Function2<OAuthToken, Throwable, Unit>() {
                    @Override
                    public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                        if (oAuthToken!=null){
                            Toast.makeText(getActivity(), "로그인성공", Toast.LENGTH_SHORT).show();

                            UserApiClient.getInstance().me(new Function2<com.kakao.sdk.user.model.User, Throwable, Unit>() {
                                @Override
                                public Unit invoke(User user, Throwable throwable) {
                                    if (user!= null){
                                        String id=String.valueOf(user.getId());

                                        String nickname = user.getKakaoAccount().getProfile().getNickname();
                                        String profileImage= user.getKakaoAccount().getProfile().getThumbnailImageUrl();

                                        String email = user.getKakaoAccount().getEmail();

                                        nickName.setText(nickname);

                                        G.userVo.name=nickname;
                                        G.userVo.id=id;
                                        G.userVo.imgUrl=profileImage;
                                        FirebaseDatabase.getInstance().getReference("user").child(id).setValue(nickname).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(getContext(), "OK", Toast.LENGTH_SHORT).show();
                                                Glide.with(Tab05_MypurpleFragment.this).load(profileImage).into(ivProfile);
                                            }
                                        });


                                    }else {
                                        Toast.makeText(getContext(), "실패", Toast.LENGTH_SHORT).show();
                                    }

                                    return null;
                                }
                            });
                        }

                        return null;
                    }
                });
            }
        });

        //카카오 로그아웃버튼
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {

                        if (throwable != null) {
                            Toast.makeText(getContext(), "로그아웃 실패", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "로그아웃", Toast.LENGTH_SHORT).show();

                            //화면초기화
                            nickName.setText("닉네임");
                            Glide.with(Tab05_MypurpleFragment.this).load(R.drawable.profile_image).into(ivProfile);
                        }


                        return null;
                    }
                });


            }
        });

    }
}
