package com.caoweixin.filemanager;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.caoweixin.filemanager.adapter.ListViewAdapter;
import com.caoweixin.filemanager.bean.ItemInfo;
import com.caoweixin.filemanager.util.FileType;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "FolderManager.MainActivity";

    private Button contentBtn;
    private ImageButton back;
    private ListView listView;
    private File sdDir; // sdcard路径
    private String rootPath; // 根目录路径
    private String currPath; // 当前目录路径
    private Context context;
    private List<ItemInfo> listItems = new ArrayList<ItemInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = getApplicationContext();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "hello, Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sdDir = Environment.getExternalStorageDirectory();
        contentBtn = (Button) findViewById(R.id.input);
        contentBtn.setText(sdDir.getPath());
        back = (ImageButton) findViewById(R.id.back);
        listView = (ListView) findViewById(R.id.folder_list);
        rootPath = currPath = sdDir.getPath();
        listView.setAdapter(new ListViewAdapter(this, getData(currPath)));
        contentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(contentBtn);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String backPath = getBackPath(currPath);

                listView.setAdapter(new ListViewAdapter(context, getData(backPath)));
                contentBtn.setText(backPath);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemInfo listItem = new ItemInfo();
                listItem = listItems.get(position);
                currPath = listItem.getPath();
                contentBtn.setText(currPath);
                listView.setAdapter(new ListViewAdapter(context, getData(currPath)));
                Log.i("currPath:", currPath);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showPopupMenu(View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(this, view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.root_content:
                        contentBtn.setText(rootPath);
                        currPath = rootPath;
                        listView.setAdapter(new ListViewAdapter(context, getData(rootPath)));
                        break;
                    case R.id.upper_content:
                        currPath= getBackPath(currPath);
                        contentBtn.setText(currPath);
                        listView.setAdapter(new ListViewAdapter(context, getData(currPath)));
                        break;
                }
                return false;
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {

            }
        });
        popupMenu.show();
    }

    // 获取当前目录下所有文件名称
    private List<ItemInfo> getData(String path){
        listItems.clear();
        ItemInfo itemInfo = null;
        File f = new File(path);
        Log.i("currPath:", currPath);
        File[] files = f.listFiles();
        if (files != null) {
            for (File file : files) {
                itemInfo = new ItemInfo();
                if (FileType.isAudioFileType(file.getPath())) {
                    itemInfo.setImgId(R.drawable.music);
                } else if (FileType.isVideoFileType(file.getPath())) {
                    itemInfo.setImgId(R.drawable.video);
                } else if (FileType.isImageFileType(file.getPath())) {
                    itemInfo.setImgId(R.drawable.image);
                } else if (file.isDirectory()){
                    itemInfo.setImgId(R.drawable.folder);
                } else {
                    itemInfo.setImgId(R.drawable.ufo);
                }
                itemInfo.setTitle(file.getName());
                itemInfo.setPath(file.getPath());
                long time = file.lastModified();
                Date date = new Date(time);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateStr = sdf.format(date);
                itemInfo.setTime(dateStr);
                listItems.add(itemInfo);
            }
        } else {
            Toast.makeText(getApplicationContext(), path + " 子目录无法访问", Toast.LENGTH_SHORT).show();
        }
        return listItems;
    }
    // 返回上层路径
    private String getBackPath(String path) {
        Log.i("path:", path);
        if (path.equals("/storage/emulated/0")) {
            Toast.makeText(context, "已经是根目录了", Toast.LENGTH_SHORT).show();
            return path;
        } else {
            for (int i = path.length() - 1; i >= 0; i--){
                Log.i("charAt", String.valueOf(path.charAt(i)));
                if (String.valueOf(path.charAt(i)).equals("/")) {
                    currPath = path.substring(0, i);
                    break;
                }
            }
            return currPath;
        }

    }
}
