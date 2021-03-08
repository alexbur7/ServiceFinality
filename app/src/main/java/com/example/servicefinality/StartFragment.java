package com.example.servicefinality;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.servicefinality.database.ApplicationPath;
import com.example.servicefinality.database.RoomDB;
import com.google.firebase.iid.FirebaseInstanceId;

public class StartFragment extends Fragment {

    private EditText mPackageEditText;
    private Button mStartButton;
    private RoomDB mRoomDB;
    private TextView mTextView;

    public StartFragment(RoomDB db) {
        this.mRoomDB=db;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.start_fragment,container,false);
        mPackageEditText=v.findViewById(R.id.package_edit_text);
        mStartButton=v.findViewById(R.id.start_btn);

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mPackageEditText.getText().toString().isEmpty()) mRoomDB.dao().updatePath(new ApplicationPath(mPackageEditText.getText().toString()));
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
        });

        mTextView=v.findViewById(R.id.id_text_view);
        mTextView.setText(FirebaseInstanceId.getInstance().getId());
        return v;
    }
}
