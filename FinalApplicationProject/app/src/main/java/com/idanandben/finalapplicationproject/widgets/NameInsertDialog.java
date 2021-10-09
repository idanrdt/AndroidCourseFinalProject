package com.idanandben.finalapplicationproject.widgets;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.idanandben.finalapplicationproject.R;

public class NameInsertDialog extends Dialog {

    private ContinueListener doneListener;
    private TextInputEditText nameEditText;
    private MaterialButton doneButton;
    private MaterialButton cancelButton;

    public interface ContinueListener {
        void onDoneClicked(String name);
    }

    public NameInsertDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.name_insert_dialog);
        nameEditText = findViewById(R.id.name_text_input);
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                doneButton.setEnabled(validateName(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        doneButton = findViewById(R.id.done_button);
        doneButton.setEnabled(false);
        doneButton.setOnClickListener(v -> doneListener.onDoneClicked(nameEditText.getText().toString()));
    }

    private boolean validateName(String text) {
        return !text.isEmpty();
    }

    public void setDoneButtonListener(ContinueListener listener) {
        this.doneListener = listener;
    }
}
