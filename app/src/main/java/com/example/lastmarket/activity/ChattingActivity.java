package com.example.lastmarket.activity;

import static com.example.lastmarket.R.id.btnsend;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.lastmarket.G;
import com.example.lastmarket.R;
import com.example.lastmarket.adapter.Chat_Adapter;
import com.example.lastmarket.item.MessageItem;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class ChattingActivity extends AppCompatActivity {

    ArrayList<MessageItem> messageItems= new ArrayList<>();

    ListView listView;
    Chat_Adapter chatAdapter;

    EditText etMsg;
    Button btn;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference chatRef;

    DatabaseReference puupleChatRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        btn=findViewById(btnsend);
        etMsg=findViewById(R.id.chat_Et);

        listView = findViewById(R.id.listview_chat);
        chatAdapter = new Chat_Adapter(this, messageItems);
        listView.setAdapter(chatAdapter);

        firebaseDatabase= FirebaseDatabase.getInstance();
        //'chat'노드에 MessageItem들을 저장['chat'이라는 이름만 별도로 지정하면 여러 채팅방 개설도 가능함]
        chatRef= firebaseDatabase.getReference("chat");//
        puupleChatRef = chatRef.child(G.selectedItem.id + "&" + G.userVo.id);
        if (getIntent().getStringExtra("otherName") != null){
            puupleChatRef = chatRef.child(G.userVo.id + "&" + getIntent().getStringExtra("otherName"));
        }
        puupleChatRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //새로 추가된 데이터값( DataSnapshot이 촬영한 값)
                MessageItem item=snapshot.getValue(MessageItem.class);

                //읽어들인 메세지를 리스트뷰가 보여주는 대량의 데이터에 추가
                messageItems.add(item);

                //리스트뷰 갱신-리스트뷰가 보여줄 뷰를 만들어내는 아답터에게 요청
                chatAdapter.notifyDataSetChanged();
                listView.setSelection(messageItems.size()-1);//리스트뷰의 마지막 위치로 스크롤 이동
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void clickSend() {
        //firebase DB에 저장할 데이터들(닉네임, 메세지, 프로필이미지URL, 작성시간)
        String nickName=G.userVo.name;
        String message=etMsg.getText().toString();
        String profileUrl=G.userVo.imgUrl;

//        Toast.makeText(this, "" + G.userVo.imgUrl, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "" + G.userVo.name, Toast.LENGTH_SHORT).show();

        //메세지 작성 시간을 문자열...(시:분)
        Calendar calendar=Calendar.getInstance();
        String time= calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE);

        //firebase DB에 MessageItem 객체를 통으로 저장하기..
        MessageItem item= new MessageItem(nickName,message,time,profileUrl);

        //'chat' 노드에 MessageItem 통째로 값 추가(push)
        puupleChatRef.push().setValue(item);

        //다음 메세지 입력이 수월하도록..
        etMsg.setText("");

        //소프트 키패드 안보이도록...
        InputMethodManager imm= (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(listView.getWindowToken(), 0);
    }
}