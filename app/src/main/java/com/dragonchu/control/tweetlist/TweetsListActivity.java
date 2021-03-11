package com.dragonchu.control.tweetlist;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.shrey.task1sample.R;
import com.dragonchu.model.UserLab;
import com.dragonchu.util.SingleFragmentActivity;
import com.dragonchu.control.writetweet.WriteTweetActivity;

public class TweetsListActivity extends SingleFragmentActivity{
    //intent信息
    private static final String EXTRA_USERNAME = "com.dragonchu.control.tweetlist.username";
    private static final String EXTRA_EMAIL = "com.dragonchu.control.tweetlist.email";

    public static Intent newIntent(Context packageContext,String username,String email){
        Intent intent = new Intent(packageContext,TweetsListActivity.class);
        intent.putExtra(EXTRA_USERNAME,username);
        intent.putExtra(EXTRA_EMAIL,email);
        return intent;
    }

    private String username = "tt";
    private String email = "tt";

    ImageView toggleProf;
    TextView mScreenTitle;
    FloatingActionButton fab;
    //查看关注和全部
    private ImageView ALL;
    private ImageView Mentions;
    @Override
    protected Fragment createFragment() {
        return new TweetsListFragment();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = getIntent().getStringExtra(EXTRA_USERNAME);
        email = getIntent().getStringExtra(EXTRA_EMAIL);
        UserLab.get().setEmail(email);
        UserLab.get().setUsername(username);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toggleProf = findViewById(R.id.togglePic);
        mScreenTitle = findViewById(R.id.screen_title);

        ALL = findViewById(R.id.allBtn);
        Mentions = findViewById(R.id.Mentions);
        ALL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Mentions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        // The tweet now floating button

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = WriteTweetActivity.newIntent(TweetsListActivity.this,username,email);
                startActivity(intent);
            }
        });

        // The Account Profiles
        Resources res = this.getResources();
        ProfileDrawerItem profile1 = new ProfileDrawerItem().withName(username).withEmail(email).withIcon(ResourcesCompat.getDrawable(res,  R.drawable.button_circle_shape, null));

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(false)
                .withHeaderBackground(R.drawable.header)
                .withAccountHeader(R.layout.custom_header)
                .addProfiles(
                        // Adding the account profiles to the Account Header
                        profile1
                )
                .withOnAccountHeaderListener((View view, IProfile profile, boolean currentProfile) -> true)
                .build();



        final Drawer result = new DrawerBuilder()
                .withAccountHeader(headerResult)
                .withActivity(this)
                .withToolbar(toolbar)
                .withTranslucentStatusBar(true)
                .withActionBarDrawerToggle(false)
                .addDrawerItems(

                        // Adding different options to th side navigation drawer
                        new PrimaryDrawerItem().withIdentifier(2).withName(R.string.item_profile).withIcon(FontAwesome.Icon.faw_user).withSelectable(false),
                        new PrimaryDrawerItem().withIdentifier(3).withName(R.string.item_lists).withIcon(FontAwesome.Icon.faw_list_alt).withSelectable(false),
                        new PrimaryDrawerItem().withIdentifier(4).withName(R.string.item_moments).withIcon(FontAwesome.Icon.faw_bolt).withSelectable(false),
                        new PrimaryDrawerItem().withIdentifier(5).withName(R.string.item_highlights).withIcon(FontAwesome.Icon.faw_clone).withSelectable(false),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withIdentifier(6).withName(R.string.item_sap).withSelectable(false),
                        new PrimaryDrawerItem().withIdentifier(7).withName(R.string.item_help).withSelectable(false)

                ).withOnDrawerItemClickListener((view, position, drawerItem) -> true)
                .addStickyDrawerItems(

                        // Adding options to the footer of the side navigation drawer
                        new SecondaryDrawerItem().withName(R.string.item_night).withIcon(FontAwesome.Icon.faw_moon).withIconColorRes(R.color.colorAccent).withTextColorRes(R.color.colorAccent),
                        new SecondaryDrawerItem().withName(R.string.item_qr).withIcon(FontAwesome.Icon.faw_qrcode).withIconColorRes(R.color.colorAccent).withTextColorRes(R.color.colorAccent)
                )
                .withSelectedItem(-1)
                .build();

        toggleProf.setOnClickListener((view) -> {

            if (result.isDrawerOpen()) {
                result.closeDrawer();
            } else {
                result.openDrawer();
            }

        });

        mScreenTitle.setText("Home");
        fab.setVisibility(View.VISIBLE);
        // Logic for the navigation tabs

        TabLayout tabLayout = findViewById(R.id.tabs);

        TabLayout.Tab home = tabLayout.newTab().setIcon(new IconicsDrawable(this).icon(FontAwesome.Icon.faw_home).colorRes(R.color.colorAccent));
        TabLayout.Tab search = tabLayout.newTab().setIcon(new IconicsDrawable(this).icon(Ionicons.Icon.ion_ios_search).colorRes(R.color.draw_description));
        TabLayout.Tab notif = tabLayout.newTab().setIcon(new IconicsDrawable(this).icon(FontAwesome.Icon.faw_bell).colorRes(R.color.draw_description));
        TabLayout.Tab msg = tabLayout.newTab().setIcon(new IconicsDrawable(this).icon(FontAwesome.Icon.faw_envelope).colorRes(R.color.draw_description));

        tabLayout.addTab(home);
        tabLayout.addTab(search);
        tabLayout.addTab(notif);
        tabLayout.addTab(msg);


        // Logic for list view
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
