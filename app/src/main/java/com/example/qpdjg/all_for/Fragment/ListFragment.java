package com.example.qpdjg.all_for.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.qpdjg.all_for.Adater.CommentAdapter;
import com.example.qpdjg.all_for.Custom.ImageViewer;
import com.example.qpdjg.all_for.Custom.StarBar;
import com.example.qpdjg.all_for.Item.CommentItem;
import com.example.qpdjg.all_for.R;
import com.example.qpdjg.all_for.Util.NoScrollBarListview;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ListFragment extends Fragment {

    CommentAdapter commentAdapter;
    ImageViewer imageViewer;
    EditText editText;
    LinearLayout commentBtn;
    NoScrollBarListview noScrollBarListview;
    ArrayList<CommentItem> comemnts = new ArrayList<CommentItem>();
    private FirebaseAuth firebaseAuth;
    StarBar starBar;
    private DatabaseReference mDatabase;
//    ViewAppFragment viewAppFragment = new ViewAppFragment();
    String Category;
    String Appcall;
    int count = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_content, container, false);
        commentAdapter = new CommentAdapter(getContext(), R.layout.item_comment, comemnts);
        imageViewer = (ImageViewer) linearLayout.findViewById(R.id.contentImageViewer);
        starBar = (StarBar)linearLayout.findViewById(R.id.contentStarbar);
        editText = (EditText) linearLayout.findViewById(R.id.contentEdittext);
        commentBtn = (LinearLayout) linearLayout.findViewById(R.id.contentComment);
        noScrollBarListview = (NoScrollBarListview) linearLayout.findViewById(R.id.contentCommentView);

        noScrollBarListview.setAdapter(commentAdapter);

        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = editText.getText().toString();
                if (txt.length() == 0) {
                    Toast.makeText(getContext(), "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    //커멘트들 DB에 넣기(rank,date,name,text)
                    firebaseAuth = FirebaseAuth.getInstance();
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    String text = editText.getText().toString().trim();
                    String date = doYearMonthDay();
                    String name = user.getEmail();
                    int howstar = starBar.getStar();
                    String rank = String.valueOf(howstar);
                    count++;
                    comemnts.add(new CommentItem(text,date,name,rank));
                    CommentItem comment = new CommentItem(rank,date,name,text);
                         System.out.println("안뇨오오오오오옹"+Category);
                         System.out.println("안뇨오오오오오옹2222"+Appcall);
                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference category_Ref = rootRef.child("app_category");
                    DatabaseReference delivery_Ref = category_Ref.child(Category);
                    DatabaseReference delivery_apps_Ref = delivery_Ref.child("apps");
                    DatabaseReference delivery_apps_Ref_what = delivery_apps_Ref.child("hi");
                    DatabaseReference app_final = delivery_apps_Ref_what.child("comments");
                    System.out.println("aaaaaaaaaa"+app_final);
                   // app_final.child(name.toString().trim()+text.toString().trim()).setValue(comment);

                }
            }
        });

        return linearLayout;
    }

    public void get_now(String appcall,String category){
        this.Category =category;
        this.Appcall = appcall;
    }

    public String doYearMonthDay() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        Date date = new Date();
        String currentDate = formatter.format(date);

        return currentDate;

    }


    public void setData(ArrayList<String> url, ArrayList<CommentItem> data) {
        imageViewer.setUrl(url);
        commentAdapter.setData(data);
    }
}
