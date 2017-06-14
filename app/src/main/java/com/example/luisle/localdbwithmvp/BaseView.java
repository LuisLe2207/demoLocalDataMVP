package com.example.luisle.localdbwithmvp;

/**
 * Created by LuisLe on 6/12/2017.
 */

public interface BaseView<T> {
    void setPresenter(T presenter);

    interface ViewProgress {
        void showProgressDlg();
        void hideProgressDlg();
    }
}
