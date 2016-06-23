package com.stablekernel.standardlib;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import rx.Subscription;

public class BlockingProgressFragment extends DialogFragment {
    public static final String TAG = BlockingProgressFragment.class.getSimpleName();
    private static final String ARGS_IS_CANCELABLE = "ARGS_IS_CANCELABLE";
    private Subscription subscription;
    private boolean isCancelable;

    public static BlockingProgressFragment newInstance() {
        return new BlockingProgressFragment();
    }

    public static BlockingProgressFragment newInstance(boolean isCancelable) {
        BlockingProgressFragment blockingProgressFragment = new BlockingProgressFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARGS_IS_CANCELABLE, isCancelable);
        blockingProgressFragment.setArguments(args);
        return blockingProgressFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        if(getArguments() != null) {
            setCancelable(getArguments().getBoolean(ARGS_IS_CANCELABLE));
        }
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blocking_progress, container, false);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (subscription != null) {
            subscription.unsubscribe();
            subscription = null;
        }
    }

    public void setOnCancelListener(Subscription subscription) {
        this.subscription = subscription;
    }
}
