package com.alfred.androidstudy;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.barteksc.pdfviewer.PDFView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alfred on 2018/1/29.
 */

public class PdfPreviewActivity extends Activity {

    @BindView(R.id.view_pdf)
    PDFView mPdfView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_preview);

        ButterKnife.bind(this);

    }
}
