package activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.padster.simplepicross.MyIntro;
import com.example.padster.simplepicross.R;

import java.lang.reflect.Array;


public class MainActivity extends AppCompatActivity {

    //First We Declare Titles And Icons For Our Navigation Drawer List View
    //This Icons And Titles Are holded in an Array as you can see

    String TITLES[] = {"Home","Achievements","Tutorial","Settings","About"};
    String[] FragNames = {"Profile Header","Simple Picross","Achievements","Tutorial","Settings","About"};
    //TODO - change to fragments, array?
    int currFrag = 1;
    int ICONS[] = {R.drawable.ic_action_home,R.drawable.ic_action_achievements,R.drawable.ic_action_help,R.drawable.ic_action_settings,R.drawable.ic_action_about};

    //Similarly we Create a String Resource for the name and email in the header view
    //And we also create a int resource for profile picture in the header view

    String NAME = "Pad Ster";
    String EMAIL = "p4d573r@gmail.com";
    int PROFILE = R.drawable.ic_profile;

    private Toolbar toolbar;                              // Declaring the Toolbar Object

    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    DrawerLayout Drawer;                                  // Declaring DrawerLayout

    ActionBarDrawerToggle mDrawerToggle;                  // Declaring Action Bar Drawer Toggle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment = new main_menu();

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.content_frame, fragment).commit();

    /* Assigning the toolbar object ot the view
    and setting the the Action bar to our toolbar
     */
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to the xml View
        mRecyclerView.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size
        mAdapter = new MyAdapter(TITLES,ICONS,NAME,EMAIL,PROFILE);       // Creating the Adapter of MyAdapter class(which we are going to see in a bit)
        // And passing the titles,icons,header view name, header view email,
        // and header view profile picture

        mRecyclerView.setAdapter(mAdapter);                              // Setting the adapter to RecyclerView

        final GestureDetector mGestureDetector = new GestureDetector(MainActivity.this, new GestureDetector.SimpleOnGestureListener() {

            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });


        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());


                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    Drawer.closeDrawers();

                    onTouchDrawer(recyclerView.getChildPosition(child));

                    return true;
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            }
        });

        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager
        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager

        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);        // Drawer object Assigned to the view
        mDrawerToggle = new ActionBarDrawerToggle(this,Drawer,toolbar,R.string.drawer_open,R.string.drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }
        }; // Drawer Toggle Object Made
        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State

         /* TODO - this catches the icon presses, but it won't get back to*/
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "navIconClicked", Toast.LENGTH_SHORT).show();

                //if at menu
                if (getSupportActionBar().getTitle() == "Simple Picross")
                    Drawer.openDrawer(GravityCompat.START); //open drawer
                else //load menu
                {
                    //getSupportActionBar().setDisplayHomeAsUpEnabled(false);

                    onBackPressed();
                }
                // TODO - need this somehow?
                // mActionBarDrawerToggle.syncState();
            }
        });
    }

    /* TODO - don't need menu, but for completeness... the R.menu.menu_main isn't found...
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
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
    */

    //needs View v to be run from onclick
    public void launchGenerate(View v){
        /*
        Fragment fragment = new generate_screen();
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        */

        //only run on lollipop
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            findViewById(R.id.tool_bar).setElevation(0);

        //TODO - can get NumberPicker working on activity, not fragment
        Intent intent = new Intent(this, GenerateActivity.class);// Create the intent
        startActivity(intent);// Start activity

        getSupportActionBar().setTitle("Generate");
    }

    public int getCurrFrag(){
        return currFrag;
    }

    public void setCurrFrag(int currFrag){
        this.currFrag = currFrag;
    }

    public void onTouchDrawer(final int position){
        if (getSupportActionBar().getTitle() != FragNames[position]) { //TODO - re-tool logic here, make it grab it from active fragment somehow

            Fragment fragment = null;
            Class fragmentClass = null;

            //tags for fragments, possible helpful in the future
            String whichFragment = "";

            switch (position)
            {
                case 1:
                    //fragment = new main_menu(); //can do it this way, or with class thing
                    whichFragment = "main_menu";
                    fragmentClass = main_menu.class;
                    break;
                case 3:
                    Intent intent3 = new Intent(this, MyIntro.class);// Create the intent
                    startActivity(intent3);// Start activity
                    break;
                case 5:
                    whichFragment = "about_screen";
                    fragmentClass = about_screen.class;
                    //TODO - make toolbar icon into back icon
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    //TODO - make it function to take you back to main_menu
                    break;
                default:
                    //TODO - this is disgusting, only set intent and start activity once
                    Toast.makeText(MainActivity.this, "The item clicked is: " + position, Toast.LENGTH_SHORT).show();
                    whichFragment = "main_menu";
                    fragmentClass = main_menu.class;
                    break;
            }

        try { //TODO - this is bad somehow?
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

            FragmentManager fragmentManager = this.getSupportFragmentManager();

            if (position != 3){ //TODO - for tutorial, change later, tweak the addToBackStack stuff
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, whichFragment).addToBackStack(null).commit();

                //only run on lollipop
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Resources r = getResources(); //convert dp to px, TODO - set this once, reference 1dp value for device
                    float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, r.getDisplayMetrics());
                    findViewById(R.id.tool_bar).setElevation(px);
                }

                // TODO - this has been changed to reading the Toolbar title for now
                // TODO - this doesn't trigger "already on page" when clicking unset button
                // setCurrFrag(position);
            }
        }
        else
        {
           Toast.makeText(MainActivity.this, "Already on: " + FragNames[position], Toast.LENGTH_SHORT).show();
        }
    }

    @Override //TODO - tweak this, make it work all the time
    public void onBackPressed() {
        if (Drawer.isDrawerOpen(GravityCompat.START))
            Drawer.closeDrawers();
        else if (getFragmentManager().getBackStackEntryCount()>0) {  //TODO - set title back, too

            //getFragmentManager().popBackStack();

            onTouchDrawer(1);

            // TODO - only keep BackStack layer 1 or just go to menu
            // TODO - otherwise if you go onback and forth via the drawer it builds a big list of repeats

            //TODO - doesn't seem to do any of this for some reason
            Toast.makeText(this, "onBackPressed", Toast.LENGTH_SHORT).show();

            String title = (String) getSupportActionBar().getTitle();
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.content_frame);
            if (f instanceof main_menu)
            {
                Toast.makeText(MainActivity.this, "onBackPressed, main_menu open" , Toast.LENGTH_SHORT).show();
            }
            else if (f == null)
                Toast.makeText(MainActivity.this, "onBackPressed, f == null" , Toast.LENGTH_SHORT).show();
        }
        else
            super.onBackPressed();
    }


}