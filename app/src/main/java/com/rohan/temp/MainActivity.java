package com.rohan.temp;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

import static android.R.attr.data;
import static android.R.attr.mimeType;
import static android.R.attr.path;
import static android.R.attr.thumbnail;
import static android.app.Activity.RESULT_OK;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button button;

    APIService apiService;

    public static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);

        RetrofitAdapter restClient = new RetrofitAdapter("http://54.210.107.169");

        String temp = "temptemptemptemptemptemptemptemptemp";
        final byte[] bytesArray = temp.getBytes();

        apiService = restClient.getApiService();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectImage();

            }
        });


    }

    // DONE: 27-Oct-16 Select image from gallery
    // TODO: 27-Oct-16 Select video from gallery
    // TODO: 27-Oct-16 Record Audio
    // TODO: 27-Oct-16 Record Video
    // DONE: 27-Oct-16 Capture Image

    private void selectImage() {

        Intent intent = new Intent();
        intent.setType("video/mp4");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select media"), 3);

//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select media"), 2);

//        Intent captureImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(captureImageIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // From Camera
        if (resultCode == RESULT_OK && requestCode == 1) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");

            File imageFileFolder = new File(getCacheDir(), "Avatar");
            if (!imageFileFolder.exists()) {
                imageFileFolder.mkdir();
            }

            FileOutputStream out = null;

            File imageFileName = new File(imageFileFolder, "avatar-" + System.currentTimeMillis() + ".jpg");
            try {
                out = new FileOutputStream(imageFileName);
                photo.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
            } catch (IOException e) {
                Log.e(TAG, "Failed to convert image to JPEG", e);
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Failed to close output stream", e);
                }
            }

            TypedFile typedFile = new TypedFile("image/jpeg", imageFileName);

            apiService.uploadRequirementsMedia("d658b3088a0511e6bee550e549216e14", 1, 2, "12345", typedFile, new Callback<ResponseRequirements>() {
                @Override
                public void success(ResponseRequirements responseRequirements, Response response) {
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    Log.e("Status ", response.getStatus() + "");
                    textView.setText(response.getStatus() + "");
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                    Log.e("Status ", error.getMessage());
                    textView.setText(error.getMessage());
                }
            });
        }

        // From Gallery (Image)
        if (resultCode == RESULT_OK && requestCode == 2) {

            Uri pickedImage = data.getData();
            Bitmap photo = null;
            try {
                photo = MediaStore.Images.Media.getBitmap(getContentResolver(), pickedImage);
            } catch (IOException e) {
                Log.e(TAG, "getBitmap()");
            }

            File imageFileFolder = new File(getCacheDir(), "Avatar");
            if (!imageFileFolder.exists()) {
                imageFileFolder.mkdir();
            }

            FileOutputStream out = null;

            File imageFileName = new File(imageFileFolder, "avatar-" + System.currentTimeMillis() + ".jpg");
            try {
                out = new FileOutputStream(imageFileName);

                if (photo == null) {
                    Log.e(TAG, "NULL");
                    return;
                }

                photo.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
            } catch (IOException e) {
                Log.e(TAG, "Failed to convert image to JPEG", e);
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Failed to close output stream", e);
                }
            }

            TypedFile typedFile = new TypedFile("image/jpeg", imageFileName);

            apiService.uploadRequirementsMedia("d658b3088a0511e6bee550e549216e14", 1, 2, "12345", typedFile, new Callback<ResponseRequirements>() {
                @Override
                public void success(ResponseRequirements responseRequirements, Response response) {
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    Log.e("Status ", response.getStatus() + "");
                    textView.setText(response.getStatus() + "");
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                    Log.e("Status ", error.getMessage());
                    textView.setText(error.getMessage());
                }
            });

        }

        // From Gallery (Video)
        if (resultCode == RESULT_OK && requestCode == 3) {

            //test commit

            Uri pickedVideo = data.getData();
            TypedFile typedFile = getTypedFileFromUri(this, pickedVideo);
//            TypedFile typedFile = new TypedFile("video/mp4", getFile(pickedVideo));

            apiService.uploadRequirementsMedia("d658b3088a0511e6bee550e549216e14", 2, 2, "12345", typedFile, new Callback<ResponseRequirements>() {
                @Override
                public void success(ResponseRequirements responseRequirements, Response response) {
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    Log.e("Status ", response.getStatus() + "");
                    textView.setText(response.getStatus() + "");
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                    Log.e("Status ", error.getMessage());
                    textView.setText(error.getMessage());
                }
            });

        }

    }

    private File getFile(Uri uri) {
        Cursor cursor =
                getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int fileNameIndex = cursor.getColumnIndex(MediaStore.Video.Media.DATA);

        return new File(cursor.getString(fileNameIndex));
    }


    private TypedFile getTypedFileFromUri(Context context, Uri uri) {
        String mimeType;

        if ("content".equalsIgnoreCase(uri.getScheme())) {
            Log.e(TAG, "true");
//            mimeType = context.getContentResolver().getType(uri);
            mimeType = getContentResolver().getType(uri);
            Log.e(TAG, "mimeType = " + mimeType);
        } else {
            Log.e(TAG, "false");
            String extension = MimeTypeMap.getFileExtensionFromUrl(uri.getPath());
            Log.e(TAG, "extension = " + extension);
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
            Log.e(TAG, "mimeType = " + mimeType);
//        mimeType = "video/mp4";
        }

        if (mimeType == null) {
            return null;
        }

        String path = uri.getPath();
        // File path and mimeType are required
        if (path == null)
            return null;

        File videoFileFolder = new File(getCacheDir(), "Avatar2");
        if (!videoFileFolder.exists()) {
            videoFileFolder.mkdirs();
        }

        File file = new File(path);
//        File videoFileName = new File(videoFileFolder, "avatar-" + System.currentTimeMillis() + ".mp4");
//        Log.e(TAG, videoFileName.getAbsolutePath());
        Log.e(TAG, file.getAbsolutePath());

//        return new TypedFile(mimeType, videoFileName);
        return new TypedFile(mimeType, file);
    }

}
