package gomez.mauro.ptdma.fib.upc.imagefiltering;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Locale;


public class MainActivity extends ActionBarActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView imgSelected;
    private Button btnSelect;
    private Button btnRestore;
    private DrawerLayout dwLayout;

    private String[] filterNames;
    private ListView lvFilters;

    private Bitmap originalImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSelect = (Button) findViewById(R.id.buttonSelect);
        btnRestore = (Button) findViewById(R.id.buttonRestore);
        imgSelected = (ImageView) findViewById(R.id.image);

        dwLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        filterNames = getResources().getStringArray(R.array.filters);
        lvFilters = (ListView) findViewById(R.id.lvFilters);

        lvFilters.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, filterNames));

        lvFilters.setOnItemClickListener(new MyDrawerItemClickListener());

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, PICK_IMAGE_REQUEST);
            }
        });

        btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgSelected.setImageResource(android.R.color.transparent);
            }
        });

    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if( requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null ) {
            Uri selectedImage = data.getData();
            String[] filePathProjectionColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathProjectionColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex (filePathProjectionColumn[0]);
            String imgPath = cursor.getString(columnIndex);
            cursor.close();

            originalImage = BitmapFactory.decodeFile(imgPath);

            imgSelected.setImageBitmap(originalImage);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        //boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
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

    private class MyDrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // update selected item and title, then close the drawer
        lvFilters.setItemChecked(position, true);
        setTitle(filterNames[position]);
        dwLayout.closeDrawer(lvFilters);

        Bitmap filteredImage = doFilter(position);
        if( filteredImage != null )
            imgSelected.setImageBitmap(filteredImage);
    }

    private Bitmap doFilter ( int filter ) {
        Toast.makeText(getApplicationContext(), Integer.toString(filter), Toast.LENGTH_SHORT).show();
        switch(filter) {
            case 0: //Gaussian
                return Filters.applyGaussianBlurEffect(originalImage);
            case 1: //Gray
                return Filters.applyGreyscaleEffect(originalImage);
            /*case 2:
                return PixelateFilter.changeToPixelate(originalImage, 2);
            case 3:
                return InvertFilter.chageToInvert(originalImage);
            case 4:
                return NeonFilter.changeToNeon(originalImage, 20, 50, 40);
            case 5:
                return OilFilter.changeToOil(originalImage, 40);*/
            default:
                return null;
        }
    }

}
